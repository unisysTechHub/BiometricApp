package com.example.biometricsample.transfers.viewmodel

import androidx.lifecycle.viewModelScope
import com.example.api.TransfersUsecase
import com.example.api.request.FundTransferRequestModel
import com.example.api.resposne.Account
import com.example.biometricsample.transfers.model.BeneficiaryModel
import com.example.biometricsample.transfers.ui.FundTransferModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.math.BigDecimal

class FundTransferViewModel : TransferListViewModel(){
    val transferUsecase = TransfersUsecase()
     var _uistate : MutableStateFlow<FundTransferModel> = MutableStateFlow<FundTransferModel>(
         FundTransferModel()
     )
    var uistate : StateFlow<FundTransferModel> = _uistate
     val  selectedBeneficiary : MutableStateFlow<BeneficiaryModel> = MutableStateFlow(value = BeneficiaryModel(beneficiaryName = "Select beneficiary"))
    val transferAmmount : MutableStateFlow<String> = MutableStateFlow("0.0")
    val fundTransferModel = FundTransferModel()
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
    fun FundTransferModel.toAPIModel() : FundTransferRequestModel {
        return FundTransferRequestModel(senderAccount = fromAccount.accountNumber,
            senderAccountType = fromAccount.accountType ,
            amount = BigDecimal(this.amount),
            transactionType = "TransferSend",
            senderAccountDetails = transferUsecase.accountList?.filter { it.accountId == fromAccount.accountNumber.toLong() }?.first(),
            receiverAccountDetails = null,
            beneficiary = com.example.api.request.BeneficiaryModel(),
            transferType = this.transferType,
            description = this.purpose
        )
    }

}