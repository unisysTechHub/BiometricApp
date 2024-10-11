package com.example.biometricsample

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.biometric.BiometricViewModel
import androidx.compose.ui.platform.ComposeView
import com.example.biometricsample.biometric.LoginBiometricViewModel
import com.example.biometricsample.biometric.LoginScreen

class BlankFragment : Fragment() {

    companion object {
        fun newInstance() = BlankFragment()
    }


    private val viewModel: BlankViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: Use the ViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply { 
            setContent { 
                LoginScreen(viewModel = LoginBiometricViewModel())
            }
        }

    }
}