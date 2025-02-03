package com.example.biometricsample.transfers.ui

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.biometricsample.R
import com.example.biometricsample.animations.UTProgress
import com.example.biometricsample.appcomponents.UTScaffold
import com.example.biometricsample.appcomponents.UTSelect
import com.example.biometricsample.transfers.UTBeneficiaryTypes
import com.example.biometricsample.transfers.model.BeneficiaryModel
import com.example.biometricsample.transfers.viewmodel.BeneficiaryViewModel
import com.example.compose.ShowAlertDialog
import com.example.compose.UTTextField
import com.example.compose.rememberViewModel
import java.util.UUID
import kotlin.reflect.full.memberProperties

enum class BeneficiaryRoute {
    AddBeneficiary,
    ReviewBeneficiary
}
val beneficiaryFieldUIDs = BeneficiaryModel::class.memberProperties.associate { property ->
    property.name to UUID.randomUUID().toString()
}


enum class DomesticUIFields {
   accountNumber,accountType,bankName, beneficiaryName,phoneNumber,email,swiftBicCode
}
enum class InternationalRecipientUIFields{
    accountNumber,accountType,bankName, beneficiaryName,phoneNumber,email,iban
}
@Composable
fun AddBeneficiaryScreen(parentViewModel:  AddBeneficiaryViewModel= AddBeneficiaryViewModel(), beneficiaryType: UTBeneficiaryTypes = UTBeneficiaryTypes.DomesticWire){
    val navController = rememberNavController()
    val viewModel = rememberViewModel( BeneficiaryViewModel(beneficiaryType=beneficiaryType))
    viewModel.parentViewModel=parentViewModel
    viewModel.navController=navController
    val showDialog = viewModel.showAlerDialog.observeAsState()
    showDialog.value?.let {
           if(it.isNotEmpty()) {
               ShowAlertDialog(
                   header = "Add Beneficiary",
                   message = it,
                   onDismiss = {
                       viewModel.clearError()
                       viewModel.parentViewModel.popBackStack()

                   })

           }

    }
      //  LocalContext.curent
    NavHost(navController = navController, startDestination = BeneficiaryRoute.AddBeneficiary.name) {
        composable(BeneficiaryRoute.AddBeneficiary.name){
            when(beneficiaryType){
                UTBeneficiaryTypes.Intra -> TODO()
                UTBeneficiaryTypes.Interanl -> TODO()
                UTBeneficiaryTypes.DomesticWire -> {
                    AddBeneficiary(navController = navController, viewModel = viewModel)
                }
                UTBeneficiaryTypes.DomesticACH -> TODO()
                UTBeneficiaryTypes.International -> AddInternationalBeneficiary(navController=navController)
            }

        }
        composable(BeneficiaryRoute.ReviewBeneficiary.name) {
            Log.d("@Ramesh", "REvew")
            ReviewBeneficiary(viewModel = viewModel,navController)
        }

    }
}

@Composable
fun ReviewBeneficiary(viewModel: BeneficiaryViewModel = BeneficiaryViewModel(), navController: NavHostController= rememberNavController()) {
//    val beneficiaryProperties = BeneficiaryModel::class.memberProperties.map {
//        Log.d("@Ramesh REview", it.name)
//        it
//    }
    val showProgress = viewModel.isLoading.observeAsState()
    if (showProgress.value == true) {
        UTProgress()
    } else {

    UTScaffold(
        title = viewModel.title,
        onBackClick = { navController.popBackStack() },
        bottomBarButtonTitle = "Submit",
        onbottomBarButtonClick = viewModel.onSubmit
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
        ) {
            when (viewModel.beneficiaryType) {
                UTBeneficiaryTypes.DomesticWire -> {
                    Column {
                        if (viewModel.beneficiary.value.accountNumber.isNotEmpty()) {
                            Row {
                                Text(
                                    text = stringResource(R.string.accountNumber),
                                    modifier = Modifier.weight(1f),
                                    textAlign = TextAlign.Start,
                                    fontSize = MaterialTheme.typography.headlineSmall.fontSize
                                )
                                Text(
                                    text = viewModel.beneficiary.value.accountNumber,
                                    modifier = Modifier.weight(1f),
                                    fontSize = MaterialTheme.typography.headlineSmall.fontSize
                                )
                            }
                        }
                        if (viewModel.isPropertyValueNotEmpty("transferType")) {
                            Row {
                                Text(
                                    text = "Transfer Type",
                                    modifier = Modifier.weight(1f),
                                    textAlign = TextAlign.Start,
                                    fontSize = MaterialTheme.typography.headlineSmall.fontSize
                                )
                                Text(
                                    text = viewModel.beneficiary.value.transferType,
                                    modifier = Modifier.weight(1f),
                                    fontSize = MaterialTheme.typography.headlineSmall.fontSize
                                )
                            }
                        }


                        if (viewModel.isPropertyValueNotEmpty("beneficiaryName")) {
                            Row {
                                Text(
                                    text = "Beneficiary Name",
                                    modifier = Modifier.weight(1f),
                                    textAlign = TextAlign.Start,
                                    fontSize = MaterialTheme.typography.headlineSmall.fontSize
                                )
                                Text(
                                    text = viewModel.beneficiary.value.beneficiaryName,
                                    modifier = Modifier.weight(1f),
                                    fontSize = MaterialTheme.typography.headlineSmall.fontSize
                                )
                            }
                        }
                        if (viewModel.beneficiary.value.bankName.isNotEmpty()) {
                            Row {
                                Text(
                                    text = "Bank Name",
                                    modifier = Modifier.weight(1f),
                                    textAlign = TextAlign.Start,
                                    fontSize = MaterialTheme.typography.headlineSmall.fontSize
                                )
                                Text(
                                    text = viewModel.beneficiary.value.bankName,
                                    modifier = Modifier.weight(1f),
                                    fontSize = MaterialTheme.typography.headlineSmall.fontSize
                                )
                            }
                        }
                        if (viewModel.isPropertyValueNotEmpty("accountType")) {
                            Row {
                                Text(
                                    text = "Account Type",
                                    modifier = Modifier.weight(1f),
                                    textAlign = TextAlign.Start,
                                    fontSize = MaterialTheme.typography.headlineSmall.fontSize
                                )
                                Text(
                                    text = viewModel.beneficiary.value.accountType,
                                    modifier = Modifier.weight(1f),
                                    fontSize = MaterialTheme.typography.headlineSmall.fontSize
                                )
                            }
                        }

                        if (viewModel.isPropertyValueNotEmpty("email")) {
                            Row {
                                Text(
                                    text = "Email",
                                    modifier = Modifier.weight(1f),
                                    textAlign = TextAlign.Start,
                                    fontSize = MaterialTheme.typography.headlineSmall.fontSize
                                )
                                Text(
                                    text = viewModel.beneficiary.value.email,
                                    modifier = Modifier.weight(1f),
                                    fontSize = MaterialTheme.typography.headlineSmall.fontSize
                                )
                            }
                        }
                        if (viewModel.isPropertyValueNotEmpty("currency")) {
                            Row {
                                Text(
                                    text = "Currency",
                                    modifier = Modifier.weight(1f),
                                    textAlign = TextAlign.Start,
                                    fontSize = MaterialTheme.typography.headlineSmall.fontSize
                                )
                                Text(
                                    text = viewModel.beneficiary.value.currency,
                                    modifier = Modifier.weight(1f),
                                    fontSize = MaterialTheme.typography.headlineSmall.fontSize
                                )
                            }
                        }
                        if (viewModel.isPropertyValueNotEmpty("country")) {
                            Row {
                                Text(
                                    text = "Country",
                                    modifier = Modifier.weight(1f),
                                    textAlign = TextAlign.Start,
                                    fontSize = MaterialTheme.typography.headlineSmall.fontSize
                                )
                                Text(
                                    text = viewModel.beneficiary.value.country,
                                    modifier = Modifier.weight(1f),
                                    fontSize = MaterialTheme.typography.headlineSmall.fontSize
                                )
                            }
                        }
                        if (viewModel.isPropertyValueNotEmpty("swiftBicCode")) {
                            Row {
                                Text(
                                    text = "swiftBicCode",
                                    modifier = Modifier.weight(1f),
                                    textAlign = TextAlign.Start,
                                    fontSize = MaterialTheme.typography.headlineSmall.fontSize
                                )
                                Text(
                                    text = viewModel.beneficiary.value.swiftBicCode,
                                    modifier = Modifier.weight(1f),
                                    fontSize = MaterialTheme.typography.headlineSmall.fontSize
                                )
                            }
                        }
                        if (viewModel.isPropertyValueNotEmpty("phoneNumber")) {
                            Row {
                                Text(
                                    text = "Phone Number",
                                    modifier = Modifier.weight(1f),
                                    textAlign = TextAlign.Start,
                                    fontSize = MaterialTheme.typography.headlineSmall.fontSize
                                )
                                Text(
                                    text = viewModel.beneficiary.value.phoneNumber,
                                    modifier = Modifier.weight(1f),
                                    fontSize = MaterialTheme.typography.headlineSmall.fontSize
                                )
                            }
                        }
                        if (viewModel.isPropertyValueNotEmpty("iban")) {
                            Row {
                                Text(
                                    text = "IBAN",
                                    modifier = Modifier.weight(1f),
                                    textAlign = TextAlign.Start,
                                    fontSize = MaterialTheme.typography.headlineSmall.fontSize
                                )
                                Text(
                                    text = viewModel.beneficiary.value.iban,
                                    modifier = Modifier.weight(1f),
                                    fontSize = MaterialTheme.typography.headlineSmall.fontSize
                                )
                            }
                        }

                    }
                }


                UTBeneficiaryTypes.Intra -> TODO()
                UTBeneficiaryTypes.Interanl -> TODO()
                UTBeneficiaryTypes.DomesticACH -> TODO()
                UTBeneficiaryTypes.International -> TODO()
            }

        }
    }
}
}

@Composable
fun AddBeneficiary(viewModel: BeneficiaryViewModel = BeneficiaryViewModel(), navController : NavHostController) {
    val beneficiary by viewModel.beneficiary.collectAsState()
    val errors by viewModel.errors.collectAsState()
    UTScaffold(title = "Domestic Recipient",viewModel=viewModel, bottomBarButtonTitle ="Next", onbottomBarButtonClick = viewModel.onNext){

        Column(modifier = Modifier
            .padding(it)
            ) {
            UTTextField(
                modifier = Modifier
                    .fillMaxWidth(),
                label = "Account Number",
                value = beneficiary.accountNumber,
                onValueChange = { viewModel.onFieldChange("accountNumber", it) },
                error = errors["accountNumber"]
            )
            UTSelect(modifier = Modifier
                .padding(top = 8.dp)
                .fillMaxWidth().testTag("Account Type")
                .height(50.dp), value = beneficiary.accountType, onClick = {viewModel.showAccountTypes()})

            UTTextField(
                label = "Bank Name",
                value = beneficiary.bankName,
                onValueChange = { viewModel.onFieldChange("bankName", it) },
                error = errors["bankName"]
            )
            UTTextField(
                label = "Beneficiary Name",
                value = beneficiary.beneficiaryName,
                onValueChange = { viewModel.onFieldChange("beneficiaryName", it) },
                error = errors["beneficiaryName"]
            )
            UTTextField(
                label = "Phone Number",
                value = beneficiary.phoneNumber,
                onValueChange = { viewModel.onFieldChange("phoneNumber", it) },
                error = errors["phoneNumber"]
            )
            UTTextField(
                label = "Email",
                value = beneficiary.email,
                onValueChange = { viewModel.onFieldChange("email", it) },
                error = errors["email"]
            )
            UTTextField(
                label = "SWIFT/BIC Code",
                value = beneficiary.swiftBicCode,
                onValueChange = { viewModel.onFieldChange("swiftBicCode", it) },
                error = errors["swiftBicCode"]
            )
            Spacer(modifier = Modifier.height(16.dp))

        }
    }

}
@Composable
fun AddInternationalBeneficiary(viewModel: BeneficiaryViewModel = BeneficiaryViewModel(), navController: NavHostController) {
    val beneficiary by viewModel.beneficiary.collectAsState()
    val errors by viewModel.errors.collectAsState()
    UTScaffold(){
        Column(modifier = Modifier.padding(it)) {
            UTTextField(
                label = "Account Number",
                value = beneficiary.accountNumber,
                onValueChange = { viewModel.onFieldChange("accountNumber", it) },
                error = errors["accountNumber"]
            )
            UTSelect(modifier = Modifier
                .padding(top = 8.dp)
                .fillMaxWidth()
                .height(50.dp), value = beneficiary.accountType, onClick = {viewModel.showAccountTypes()})
            UTTextField(
                label = "Bank Name",
                value = beneficiary.bankName,
                onValueChange = { viewModel.onFieldChange("bankName", it) },
                error = errors["bankName"]
            )
            UTTextField(
                label = "Beneficiary Name",
                value = beneficiary.beneficiaryName,
                onValueChange = { viewModel.onFieldChange("beneficiaryName", it) },
                error = errors["beneficiaryName"]
            )
            UTTextField(
                label = "Phone Number",
                value = beneficiary.phoneNumber,
                onValueChange = { viewModel.onFieldChange("phoneNumber", it) },
                error = errors["phoneNumber"]
            )
            UTTextField(
                label = "Email",
                value = beneficiary.email,
                onValueChange = { viewModel.onFieldChange("email", it) },
                error = errors["email"]
            )
            UTTextField(
                label = "IBAN",
                value = beneficiary.iban,
                onValueChange = { viewModel.onFieldChange("iban", it) },
                error = errors["iban"]
            )
            UTTextField(
                label = "SWIFT/BIC Code",
                value = beneficiary.swiftBicCode,
                onValueChange = { viewModel.onFieldChange("swiftBicCode", it) },
                error = errors["swiftBicCode"]
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = { if(viewModel.errorsNone()){
                    navController.navigate(BeneficiaryRoute.ReviewBeneficiary.name)
                } },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Submit")
            }
        }
    }

}


@Preview
@Composable
fun AddBeneficiaryPreview(){

    AddBeneficiaryScreen(AddBeneficiaryViewModel())
}

@Preview
@Composable
fun ReviewBeneficiaryPreview(){
    ReviewBeneficiary()
}