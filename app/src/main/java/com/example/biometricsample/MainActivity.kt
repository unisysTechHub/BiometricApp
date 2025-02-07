package com.example.biometricsample

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.window.Dialog
import androidx.fragment.app.FragmentActivity
import com.example.biometricsample.biometric.BiometricPromptUtils
import com.example.biometricsample.biometric.Login_enroll_biometric
import com.example.biometricsample.ui.theme.BiometricSampleTheme
import android.provider.Settings
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import com.example.biometricsample.animations.AnimateContent
import com.example.biometricsample.animations.AnimationIntOffsetTest
import com.example.biometricsample.animations.AnimationVisibilityTest
import com.example.biometricsample.animations.BoxWithinColumnOffsetTest
import com.example.biometricsample.animations.CrossfadeAnimation
import com.example.biometricsample.animations.ElevationAnimationTest
import com.example.biometricsample.animations.Gesture
import com.example.biometricsample.animations.ScaleTextAnimation
import com.example.biometricsample.transfers.ui.AddBeneficiary
import com.example.biometricsample.transfers.ui.AddBeneficiaryScreen
import com.example.biometricsample.transfers.ui.TransferListScreen
import com.example.biometricsample.ui.components.UTQuanityContent
import com.example.biometricsample.ui.components.UTQuantity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.suspendCancellableCoroutine


const  val CIPHERTEXT_WRAPPER = "ciphertext_wrapper"
const val SHARED_PREFS_FILENAME = "biometric_prefs"
const  val secretKeyName = "biometric_sample_encryption_key"

class MainActivity : FragmentActivity() {
    @RequiresApi(Build.VERSION_CODES.R)
    private lateinit var  cryptographyManager : CryptographyManager
    private lateinit var biometricPrompt: BiometricPrompt
    private val ciphertextWrapper
        @RequiresApi(Build.VERSION_CODES.R)
        get() = cryptographyManager.getCiphertextWrapperFromSharedPrefs(
            applicationContext,
            SHARED_PREFS_FILENAME,
            Context.MODE_PRIVATE,
            CIPHERTEXT_WRAPPER
        )

    private  val qty = MutableStateFlow(1)

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
                super.onCreate(savedInstanceState)
        val canAuthenticate =  BiometricManager.from(applicationContext).canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_STRONG)

//            setContent {
//
//                BiometricSampleTheme{
//                    DialogComposable()
//                }
//
//            }
        setContent {
            BiometricSampleTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                   // TransferListScreen()
                    //Gesture();
//                    UTQuanityContent(count = qty, modifer = Modifier
//                        .fillMaxWidth()
//                        .height(100.dp))
                   // AnimateContent()
                   // CrossfadeAnimation()
                   // AnimationVisibilityTest()
                    //ScaleTextAnimation()

//ElevationAnimationTest()
                  Box(contentAlignment = Alignment.Center) {
                      if (ciphertextWrapper == null) {
                        Button(onClick = {
                             enrollBiometric()

                                                      }) {
                            Text("Login")
                        }
                    }
//                    ShowDialog{
//                        Text(text = "Test ddfdfdfdfdf",modifier = Modifier
//                            .fillMaxSize()
//                            .background(Color.Green))
//                    }
                  }
                }
            }
        }
        cryptographyManager = CryptographyManager()


    }
    @RequiresApi(Build.VERSION_CODES.R)
    override fun onResume() {
        super.onResume()
        Log.d("@Ramesh", "onResume")
        if (ciphertextWrapper != null) {
            if (userRepo.jwt == null) {
                showBiometricPromptForDecryption()
            } else {
                // The user has already logged in, so proceed to the rest of the app
                // this is a todo for you, the developer
              //  updateApp(getString(R.string.already_signedin))
            }
        }else {
            Log.d("@Ramesh", "cipher text wrapper null")
        }
    }
    fun enrollBiometric() {
        val intent =
            Intent(this,Login_enroll_biometric::class.java)
        startActivity(intent)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == 0) {

        }
    }

    // BIOMETRICS SECTION
// BIOMETRICS SECTION


    @Composable
    fun ShowDialog(properties: DialogProperties = DialogProperties(), onDismiss : () -> Unit={}, content : @Composable () -> Unit){
        var show by remember {
            mutableStateOf(true)
        }
        Column(modifier = Modifier
            .fillMaxSize()
            .background(Color.Red)) {


        }

        if (show)  {
            Dialog(onDismissRequest = { show=false }, properties = DialogProperties(usePlatformDefaultWidth = false)) {
                content()
            }
        }

    }
    @RequiresApi(Build.VERSION_CODES.R)
    private fun showBiometricPromptForDecryption() {
        ciphertextWrapper?.let { textWrapper ->
            val cipher = cryptographyManager.getInitializedCipherForDecryption(
                secretKeyName, textWrapper.initializationVector
            )
            biometricPrompt =
                BiometricPromptUtils.createBiometricPrompt(
                    this,
                    ::decryptServerTokenFromStorage
                )
            val promptInfo = BiometricPromptUtils.createPromptInfo(this)
            biometricPrompt.authenticate(promptInfo, BiometricPrompt.CryptoObject(cipher))
        }
    }

    @RequiresApi(Build.VERSION_CODES.R)
    private fun decryptServerTokenFromStorage(authResult: BiometricPrompt.AuthenticationResult) {
        ciphertextWrapper?.let { textWrapper ->
            authResult.cryptoObject?.cipher?.let {
                val plaintext =
                    cryptographyManager.decryptData(textWrapper.ciphertext, it)
                userRepo.jwt = plaintext
                // Now that you have the token, you can query server for everything else
                // the only reason we call this fakeToken is because we didn't really get it from
                // the server. In your case, you will have gotten it from the server the first time
                // and therefore, it's a real token.

             //   updateApp(getString(R.string.already_signedin))
            }
        }
    }

// USERNAME + PASSWORD SECTION


// USERNAME + PASSWORD SECTION
}






























































@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    BiometricSampleTheme {
        Greeting("Android")
    }
}