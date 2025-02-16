package com.example.biometricsample.transfers.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.api.AccountModel
import com.example.api.request.FundTransferRequestModel
import com.example.api.resposne.Account
import com.example.biometricsample.appcomponents.UTScaffold
import com.example.biometricsample.appcomponents.UTSelect
import com.example.biometricsample.transfers.UTBeneficiaryTypes
import com.example.biometricsample.transfers.model.BeneficiaryModel
import com.example.biometricsample.transfers.viewmodel.FundTransferViewModel
import java.math.BigDecimal

data class FundTransferModel(
    val fromAccount: AccountModel = AccountModel(),
    val toAccount: BeneficiaryModel = BeneficiaryModel(),
    val amount: Double = 0.0,
    val transferType: String = UTBeneficiaryTypes.DomesticWire.name,
    val purpose: String = "",
    val frequency: String ="oneTime"
)


// tranfer view model

//uistate of Transfer screen
@Composable
fun TransferScreen(viewmodel: FundTransferViewModel){
       val beneficiaries = viewmodel.beneficiaries.collectAsState()
       val accounts = viewmodel.favaourites.collectAsState()

        //Recipient - viemodel - selected recipient
        //from accounts - uistate - fromaccounts - listEmpty - network call - transfer usecase
        //
        //amount - onvaluce change update tranfer amount
        //purpose - list of purpose -
        //frequency - liast of frequency

}
@Composable
fun FundTransferScreen(parentViewModel: ViewModel, transferType : UTBeneficiaryTypes){
    val navController : NavHostController= rememberNavController()
    NavHost(navController = navController, startDestination ="FunTransferInput" ) {
        composable(route="FunTransferInput"){
            FundTransfer(viewmodel = FundTransferViewModel())
        }
        composable(route="TransferReview"){

        }
    }
}
@Composable
fun FundTransfer(viewmodel: FundTransferViewModel){
    UTScaffold(title = "Transfer") {
        Column(modifier = Modifier.padding(it)) {
            TransferSelectAccount(viewmodel)
            TransferAmount(viewmodel = viewmodel)
            TransferAdditionalInfo(viewmodel = viewmodel)

        }

    }
}

@Composable
fun TransferSelectAccount(viewmodel: FundTransferViewModel){
        Column(modifier = Modifier
            .padding(8.dp)
        ) {

            UTSelect(modifier = Modifier
                .padding(top = 8.dp)
                .fillMaxWidth()
                .testTag("From Account")
                .height(50.dp),
                value = viewmodel.uistate.value.fromAccount.accountNumber,
                onClick = { viewmodel.showBottomSheet() })
            UTSelect(modifier = Modifier
                .padding(top = 8.dp)
                .fillMaxWidth()
                .testTag("From Account")
                .height(50.dp),
                value = viewmodel.uistate.value.toAccount.accountNumber,
                onClick = { viewmodel.showBeneficiariesBottomSheet() })
        }
    
}

@Composable
fun TransferAmount(viewmodel: FundTransferViewModel){
    val transerAmount = viewmodel.transferAmmount.collectAsState()
   TextField(value = transerAmount.value, modifier = Modifier.padding(16.dp).
                                         fillMaxWidth().height(50.dp).testTag("Transfer Amount"),
       onValueChange = {viewmodel.onTextfieldValueChange(it)})
}
@Composable
fun TransferAdditionalInfo(viewmodel: FundTransferViewModel){
    UTSelect(modifier = Modifier
        .padding(top = 8.dp)
        .fillMaxWidth()
        .testTag("Purpose")
        .height(50.dp),
        value = viewmodel.uistate.value.fromAccount.accountNumber,
        onClick = { viewmodel.showBottomSheet() })
    UTSelect(modifier = Modifier
        .padding(top = 8.dp)
        .fillMaxWidth()
        .testTag("Frequency")
        .height(50.dp),
        value = viewmodel.uistate.value.toAccount.accountNumber,
        onClick = { viewmodel.showBeneficiariesBottomSheet() })
}

@Preview
@Composable
fun TransferInitScreenPreview(){
    FundTransfer(viewmodel = FundTransferViewModel())
}