# Biometric authentication design

**Owner:** Mobile Platform  
**Status:** Reference implementation; production hardening required  
**Scope:** `com.example.biometricsample.biometric`

## Purpose

This package enables a user to unlock locally stored credential material with a
strong biometric. A successful device biometric does **not** authenticate the
user to the bank by itself. The server remains the authority for session
creation, token validity, revocation, and transaction authorization.

The primary use case is biometric re-entry after a user has already completed
the normal sign-in flow. It should not replace password/PIN sign-in, account
recovery, or step-up authorization for high-risk actions.

## Current implementation

| Component | Responsibility |
| --- | --- |
| `Login_enroll_biometric` | Starts enrollment, checks biometric availability, starts the system prompt, and finishes after storing the result. |
| `BiometricPromptUtils` | Creates the AndroidX `BiometricPrompt` and its user-visible prompt text. |
| `CryptographyManager` | Creates an AES-256-GCM key in Android Keystore, encrypts/decrypts values, and persists `CiphertextWrapper`. |
| `CiphertextWrapper` | Holds ciphertext and the AES-GCM initialization vector (IV). |
| `LoginBiometricViewModel` | Currently supplies a placeholder `UserRecord` and JWT to demonstrate the flow. |

### Authentication sequence

```text
Normal login succeeds and returns a server token
        |
        v
Request biometric enrollment (Settings.ACTION_BIOMETRIC_ENROLL)
        |
        v
Verify BIOMETRIC_STRONG availability with BiometricManager
        |
        v
Create or retrieve Android Keystore AES key (user authentication required)
        |
        v
Pass encryption Cipher to BiometricPrompt as CryptoObject
        |
        v
System biometric succeeds -> encrypt token -> store ciphertext + IV
```

`BiometricPromptUtils.createBiometricPrompt()` executes the supplied success
callback only from `onAuthenticationSucceeded`. Authentication errors and
non-matches are currently logged only; neither grants access.

## Security properties and boundaries

- The AES key is generated in `AndroidKeyStore`; key bytes are not written to
  app storage.
- `setUserAuthenticationRequired(true)` means the key operation requires recent
  user authentication. The implementation does not set a validity duration, so
  each use is expected to require authentication.
- AES/GCM provides confidentiality and integrity for the stored plaintext. The
  IV is intentionally stored alongside ciphertext; it is not secret.
- `BIOMETRIC_STRONG` restricts the flow to Android's stronger biometric class.
- The ciphertext is stored in app-private `SharedPreferences`. This protects it
  from other normal apps but does not make it a secure server-session store,
  and it may be included in backup depending on backup rules.

Out of scope: protecting a compromised/rooted device, server-side fraud
detection, account recovery, and authorization of payments/transfers.

## Product and UX policy

1. Offer biometric sign-in only after a successful password/PIN sign-in and
   explicit user opt-in.
2. Always provide a clear fallback to the app's primary credential. The prompt
   negative button currently says “Use app password”; its click path must route
   to that experience.
3. Do not represent a biometric match as a new server login. Decrypt a valid
   local credential, then validate/refresh it with the backend before entering
   the authenticated area.
4. Require separate server-backed step-up authorization for sensitive actions
   (for example, adding a beneficiary or sending a transfer).
5. When authentication is unavailable, cancelled, locked out, or permanently
   locked out, show an actionable state rather than silently logging it.

## Required production hardening

The package is a useful proof of concept, but it is not production-ready until
the following items are addressed.

| Priority | Gap observed | Required change |
| --- | --- | --- |
| P0 | A JWT is logged in `encryptAndStoreServerToken`. | Remove token logging and ensure analytics/crash reporting never captures credentials. |
| P0 | The sample token and user login are placeholders. | Integrate a real repository, short-lived access token strategy, refresh/revocation handling, and a server validation step. |
| P0 | Prompt errors, failures, and cancellation only log. | Model explicit UI outcomes; offer retry/fallback; rate-limit retries; never continue after an error. |
| P0 | `userRecordLiveData.value!!` can crash. | Use a state machine with a non-null, validated credential input before starting encryption. |
| P1 | Enrollment result is ignored and the prompt is started unconditionally. | On return, rerun `canAuthenticate`; only prompt on `BIOMETRIC_SUCCESS`, otherwise surface the appropriate state. |
| P1 | The negative-button fallback has no handler. | Handle `ERROR_NEGATIVE_BUTTON` and navigate to password/PIN sign-in. |
| P1 | Keystore invalidation and crypto exceptions are not handled. | Catch `KeyPermanentlyInvalidatedException`, `UserNotAuthenticatedException`, and GCM errors; clear only the affected local biometric credential and require normal sign-in. |
| P1 | Encrypted token data may be backed up. | Exclude the biometric preference file from cloud/device-transfer backups, or avoid persisting reusable bearer tokens. |
| P1 | Stored data has no version, account binding, expiry, or migration policy. | Store a versioned record bound to the account and environment; clear it on logout, account switch, password reset, device re-enrollment, and token expiry/revocation. |
| P2 | Key alias is fixed for every account. | Namespace aliases by stable account and environment, or deliberately enforce one active account per installation. |
| P2 | `setConfirmationRequired(false)` may not be honored by all devices. | Treat confirmation as UX only; security must rely on the crypto-bound prompt and server checks. |

## Recommended target architecture

Use a small `BiometricCredentialRepository` as the only owner of the key alias,
ciphertext record, and cleanup policy. The UI should receive a sealed result:
`Success`, `NotEnrolled`, `Unavailable`, `Cancelled`, `LockedOut`, or
`FallbackRequired`. The repository should not return raw tokens to UI code;
instead it should perform the refresh/exchange and return an authenticated
session state.

For each unlock attempt:

1. Check `BiometricManager.canAuthenticate(BIOMETRIC_STRONG)` and map every
   returned status to a user-facing state.
2. Load the versioned encrypted record for the active account.
3. Initialize the decryption cipher using the stored IV.
4. Authenticate with that cipher in `BiometricPrompt.CryptoObject`.
5. Decrypt only after `onAuthenticationSucceeded`, validate/refresh with the
   backend, and securely clear unusable or revoked records.
6. Clear local biometric credential material on logout and all defined security
   lifecycle events.

## Observability and privacy

Record only coarse, non-sensitive events: capability state, prompt shown,
success, cancellation, error code category, fallback chosen, and decrypt/key
invalidation. Do not record biometric data, raw error strings, JWTs, account
identifiers, ciphertext, IVs, or key aliases. Metrics must be sampled and
reviewed under the bank's privacy policy.

## Test plan

- Unit-test result mapping, encryption/decryption round trips, missing/corrupt
  wrappers, account isolation, expiry, logout cleanup, and all exception paths.
- Instrument-test supported, no-hardware, not-enrolled, lockout, cancellation,
  negative-button fallback, rotation/backgrounding, and process recreation.
- Verify that re-enrolling/removing biometrics or invalidating the keystore key
  forces normal sign-in and does not crash.
- Test backup/restore and device-transfer behavior to prove encrypted
  credentials are not restored unexpectedly.
- Perform security review for rooted-device behavior, log scrubbing, token
  lifecycle, and transaction step-up policy before release.

## Code ownership notes

Keep prompt text in `res/values/strings.xml`; it must be localized and reviewed
by product/security. Keep AndroidX biometric dependencies aligned to a supported
stable version. Any change to authenticator class, key-authentication policy,
token persistence, or fallback behavior requires Mobile Security review.
