package com.example.biometricsample.transfers.viewmodel

import androidx.lifecycle.viewModelScope
import com.example.biometricsample.transfers.model.BeneficiaryModel
import com.example.biometricsample.transfers.ui.FundTransferModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class FundTransferViewModel : TransferListViewModel(){
     var _uistate : MutableStateFlow<FundTransferModel> = MutableStateFlow<FundTransferModel>(
         FundTransferModel()
     )
    var uistate : StateFlow<FundTransferModel> = _uistate
     val  selectedBeneficiary : MutableStateFlow<BeneficiaryModel> = MutableStateFlow(value = BeneficiaryModel(beneficiaryName = "Select beneficiary"))
    val transferAmmount : MutableStateFlow<String> = MutableStateFlow("0.0")
    // onTexfield
    // on next
    // on Submit
    //show bottom sheet for beneficiary
    //showbottom sheet for accounts
    //showbotton sheet for frequency
    //showbottom sheet for purpose
    fun showBeneficiariesBottomSheet(){
        viewModelScope.launch {
              val selectedBeneficiaryItem=   bottomSheetHostState.showBottomSheet(beneficiaries.value.items).listItem
        selectedBeneficiaryItem?.type1?.title.let {
            if (it != null) {
                selectedBeneficiary.value = BeneficiaryModel(accountNumber = it)
            }
          }
        }
    }
    fun onTextfieldValueChange(value : String){
        transferAmmount.value = value
    }
}