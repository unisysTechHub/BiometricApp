package com.example.biometricsample.biometric

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.hardware.biometrics.BiometricManager.Authenticators.BIOMETRIC_STRONG
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.biometricsample.R
import android.provider.Settings
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.biometricsample.CryptographyManager
const  val CIPHERTEXT_WRAPPER = "ciphertext_wrapper"
const val SHARED_PREFS_FILENAME = "biometric_prefs"

class Login_enroll_biometric : AppCompatActivity() {
    val viewModel by viewModels<LoginBiometricViewModel> ()
    lateinit var  cryptographyManager : CryptographyManager
    private val TAG = "EnableBiometricLogin"
    private lateinit var  launcher : ActivityResultLauncher<Int>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        viewModel.apply { }
        setContent {  LoginScreen(viewModel)}
        launcher = registerForActivityResult(Biometric_activity_contract()){
            Log.d("@Ramesh", " " + it)
            showBiometricPromptForEncryption()
            // false = device already enrroled for finger print - enroll biometric activity returns false
            //true - promted for enroll finger print - sucess - return true
//            if (it) {
//                showBiometricPromptForEncryption()
//            } else {
//                finish()
//            }



        }
        viewModel.userRecordLiveData.
        observe(this, this::onLoginSuccess)
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }
    }
    fun onLoginSuccess(userRecord: UserRecord)
    {
        Log.d("@Ramesh", "onLoginSucess")
        launcher.launch(0)

    }


   private fun showBiometricPromptForEncryption() {
        val canAuthenticate = BiometricManager.from(applicationContext).canAuthenticate(
            BiometricManager.Authenticators.BIOMETRIC_STRONG
        )
       Log.d("@Ramesh", " +"+ canAuthenticate)
        if (canAuthenticate == BiometricManager.BIOMETRIC_SUCCESS) {
            val secretKeyName = "biometric_sample_encryption_key"
            cryptographyManager = CryptographyManager()
            val cipher = cryptographyManager.getInitializedCipherForEncryption(secretKeyName)
            val biometricPrompt =
                BiometricPromptUtils.createBiometricPrompt(this, ::encryptAndStoreServerToken)
            val promptInfo = BiometricPromptUtils.createPromptInfo(this)
            biometricPrompt.authenticate(promptInfo, BiometricPrompt.CryptoObject(cipher))
        }
    }

   private fun encryptAndStoreServerToken(authResult: BiometricPrompt.AuthenticationResult) {
        authResult.cryptoObject?.cipher?.apply {

            viewModel.userRecordLiveData.value!!.jwt.let { token ->
                Log.d(TAG, "The token from server is $token")
                val encryptedServerTokenWrapper = cryptographyManager.encryptData(token, this)
                cryptographyManager.persistCiphertextWrapperToSharedPrefs(
                    encryptedServerTokenWrapper,
                    applicationContext,
                    SHARED_PREFS_FILENAME,
                    Context.MODE_PRIVATE,
                    CIPHERTEXT_WRAPPER
                )
            }
        }
        finish()
    }
}
class Biometric_activity_contract: ActivityResultContract<Int,Boolean>() {
    override fun createIntent(context: Context, input: Int): Intent {
        Log.d("@Ramesh", "creaete inent")

        val enrollIntent = Intent(Settings.ACTION_BIOMETRIC_ENROLL).apply {
            putExtra(Settings.EXTRA_BIOMETRIC_AUTHENTICATORS_ALLOWED,
                BIOMETRIC_STRONG )

        }
        return enrollIntent
    }

    override fun parseResult(resultCode: Int, intent: Intent?): Boolean {
        return resultCode == Activity.RESULT_OK
    }
}

@Composable
fun LoginScreen(viewModel: LoginBiometricViewModel) {
    //val uiState = viewModel.uiState.
    val navController = rememberNavController()
NavHost(navController = navController, startDestination = ""){
    composable("Login") {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
            Column(modifier = Modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .border(width = 2.dp, color = Color.Red, shape = RectangleShape)) {
                    TextField(value = "", onValueChange = { viewModel.update(LoginUUid.userId, value = it) })
                }
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .border(width = 2.dp, color = Color.Red, shape = RectangleShape)) {
                    TextField(value = "", onValueChange = { viewModel.update(LoginUUid.userId, value = it) })
                }
                Button(onClick = { viewModel.login() }) {
                    Text(text = "Authenticate", modifier = Modifier.background(Color.Gray), color = Color.Black)
                }
            }

        }
    }
}


}