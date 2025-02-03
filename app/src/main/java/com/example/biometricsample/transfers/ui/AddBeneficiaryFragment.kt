package com.example.biometricsample.transfers.ui

import androidx.fragment.app.viewModels
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import androidx.navigation.fragment.findNavController
import com.example.biometricsample.R
import com.example.biometricsample.transfers.UTBeneficiaryTypes
import com.example.compose.ShowAlertDialog

class AddBeneficiaryFragment : Fragment() {
    lateinit var beneficiaryType:String
    companion object {
        fun newInstance() = AddBeneficiaryFragment()
    }

    private val viewModel: AddBeneficiaryViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        beneficiaryType = this.arguments?.getString("beneficiaryType").toString()

        // TODO: Use the ViewModel
    }

    private fun popBackStack(pop: Unit?) {
        Log.d("@Ramesh","popBackStack" + pop)
      pop?.let {
          findNavController().popBackStack()
      }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        //return inflater.inflate(R.layout.fragment_add_beneficiary, container, false)
        return ComposeView(requireContext()).apply {
            setContent {
                AddBeneficiaryScreen(parentViewModel =viewModel,beneficiaryType = UTBeneficiaryTypes.valueOf(beneficiaryType))
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.popBackStack.observe(viewLifecycleOwner,this::popBackStack)

    }

}