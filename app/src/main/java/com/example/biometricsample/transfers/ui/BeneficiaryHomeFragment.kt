package com.example.biometricsample.transfers.ui

import androidx.fragment.app.viewModels
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.example.biometricsample.R
import com.example.biometricsample.transfers.UTBeneficiaryTypes
import com.example.biometricsample.ui.components.UTGridItem

class BeneficiaryHomeFragment : Fragment() {

    companion object {
        fun newInstance() = BeneficiaryHomeFragment()
    }

    private val viewModel: BeneficiaryHomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: Use the ViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        //return inflater.inflate(R.layout.fragment_beneficiary_home, container, false)
        return ComposeView(requireContext()).apply{
            setContent { UTGridBeneficiaryTypeListScreen(onItemClicked) }
        }
    }
    val onItemClicked :(UTGridItem)->Unit = { item ->
        Log.d("@Ramesh", "onItemClicked")
        when(item.title){

        UTBeneficiaryTypes.DomesticWire.name -> {
            findNavController().navigate(R.id.action_beneficiary_home_to_add_Beneficiary,Bundle( bundleOf("beneficiaryType" to UTBeneficiaryTypes.DomesticWire.name )))
        }
    } }
}