package com.example.biometricsample.transfers.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.example.biometricsample.R
import com.example.biometricsample.transfers.UTBeneficiaryTypes
import com.example.biometricsample.transfers.list.ListItem
import com.example.biometricsample.transfers.list.Type1
import com.example.biometricsample.transfers.model.BeneficiaryModel
import com.example.biometricsample.transfers.ui.AddBeneficiaryViewModel
import com.example.biometricsample.transfers.ui.BeneficiaryRoute
import com.example.biometricsample.transfers.ui.DomesticUIFields
import com.example.biometricsample.transfers.ui.InternationalRecipientUIFields
import com.example.biometricsample.transfers.validation.ValidationDelegate
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlin.reflect.KProperty1
import kotlin.reflect.full.memberProperties

interface APICallBack<T>{
    fun onError(message : String)
    fun OnSuccess(result :T)
}
data class AddBeneficiaryState(val beneficiaryModel: BeneficiaryModel)
class BeneficiaryViewModel(val beneficiaryType: UTBeneficiaryTypes = UTBeneficiaryTypes.DomesticWire) : UTCommonViewModel() {

   var parentViewModel : AddBeneficiaryViewModel = AddBeneficiaryViewModel()
   private val _beneficiary = MutableStateFlow(BeneficiaryModel(transferType = beneficiaryType.name, accountType = "Checking"))
    val beneficiary : StateFlow<BeneficiaryModel> = _beneficiary
    val errors = MutableStateFlow(mapOf<String, String?>())
    val beneficiaryProperties = BeneficiaryModel::class.memberProperties
    val accountTypes : List<ListItem> get() =
        when(beneficiaryType){
            UTBeneficiaryTypes.DomesticWire -> listOf(ListItem(type1 = Type1("Savings"))
                ,ListItem(type1 = Type1("Checking")))

            UTBeneficiaryTypes.Intra -> TODO()
            UTBeneficiaryTypes.Interanl -> TODO()
            UTBeneficiaryTypes.DomesticACH -> TODO()
            UTBeneficiaryTypes.International -> listOf(ListItem(type1 = Type1("Savings"))
                ,ListItem(type1 = Type1("Checking")))
        }
    val title : String get() =
        when(beneficiaryType){
            UTBeneficiaryTypes.DomesticWire -> "Domestic Wire Beneficiary"

            UTBeneficiaryTypes.Intra -> TODO()
            UTBeneficiaryTypes.Interanl -> TODO()
            UTBeneficiaryTypes.DomesticACH -> TODO()
            UTBeneficiaryTypes.International -> "International Beneficiary"
        }
    lateinit var navController : NavHostController
    //val navHostController:NavHostController
    val _showAlerDialog = MutableLiveData<String?>(null)
    val showAlerDialog : LiveData<String?> = _showAlerDialog
    val isLoading : MutableLiveData<Boolean> = MutableLiveData(false)
    private val validator = ValidationDelegate()
    private val beneficiaryLabels = mapOf(
        "name" to R.string.accountNumber,
        "accountNumber" to R.string.accountNumber
    )
    internal fun getFieldLabels(): Map<String, Int> = beneficiaryLabels
    fun onFieldChange(property: String, value: String) {
        // Update property value

        val updatedBeneficiary = when (property) {
            "accountNumber" -> beneficiary.value.copy(accountNumber = value)
            "bankName" -> beneficiary.value.copy(bankName = value)
            "beneficiaryName" -> beneficiary.value.copy(beneficiaryName = value)
            "accountType" -> beneficiary.value.copy(accountType = value)
            "transferType" -> beneficiary.value.copy(transferType = value)
            "phoneNumber" -> beneficiary.value.copy(phoneNumber = value)
            "email" -> beneficiary.value.copy(email = value)
            "iban" -> beneficiary.value.copy(iban = value)
            "swiftBicCode" -> beneficiary.value.copy(swiftBicCode = value)
            else -> beneficiary.value // Return the original value if no property matches
        }

        _beneficiary.value = updatedBeneficiary

        // Validate and update errors
        val error = validator.validate(property, value)
        errors.value = errors.value.toMutableMap().apply { this[property] = error }
    }
    val hasEmptyField : () -> Boolean = {
        BeneficiaryModel::class.members
            .filterIsInstance<kotlin.reflect.KProperty1<BeneficiaryModel, *>>() // Get only properties
            .filter { property ->  !(DomesticUIFields.entries.find { it.name.equals(property.name) } == null) }
            .map { property ->
                val value = property.get(_beneficiary.value)
                if (value is String && value.isEmpty()) {
                    errors.value = errors.value.toMutableMap()
                        .apply { this[property.name] = "Enter ${property.name}" }

                }
                value is String && value.isEmpty() // Check if it's a String and empty
            }.any{it}
    }

    val hasInternationalRecipientUIEmptyField : () -> Boolean = {
        BeneficiaryModel::class.members
            .filterIsInstance<kotlin.reflect.KProperty1<BeneficiaryModel, *>>() // Get only properties
            .filter { property ->  !(InternationalRecipientUIFields.entries.find { it.name.equals(property.name) } == null) }
            .map { property ->
                val value = property.get(_beneficiary.value)
                if (value is String && value.isEmpty()) {
                    errors.value = errors.value.toMutableMap()
                        .apply { this[property.name] = "Enter ${property.name}" }

                }
                value is String && value.isEmpty() // Check if it's a String and empty
            }.any{it}
    }
    fun errorsNone() = errors.value.values.none { !it.isNullOrEmpty();}
//        if (errors.value.values.none { !it.isNullOrEmpty() }) {
//            val beneficiaryModel = beneficiary.value.toApiModel()
//          //  parentViewModel.navigateToFragmentA()
//        }
   val propertyByname : (String) -> KProperty1<BeneficiaryModel, *>? = { propertyName ->
                                BeneficiaryModel::class.memberProperties.find { it.name == propertyName }
                                                }
    fun isPropertyValueNotEmpty(name : String) : Boolean{
       return propertyByname(name)?.get(beneficiary.value).toString().isNotEmpty()
    }
   val onNext = {
      // parentViewModel.popBackStack()
       val isValid = when(beneficiaryType){
           UTBeneficiaryTypes.Intra -> TODO()
           UTBeneficiaryTypes.Interanl -> TODO()
           UTBeneficiaryTypes.DomesticWire -> errorsNone() && !hasEmptyField()
           UTBeneficiaryTypes.DomesticACH -> TODO()
           UTBeneficiaryTypes.International -> errorsNone() && !hasInternationalRecipientUIEmptyField()
       }
       if ( isValid ) navController.navigate(BeneficiaryRoute.ReviewBeneficiary.name)
   }
  val onSubmit = {
      isLoading.value = true
      parentViewModel.onSumbit(beneficiary.value,
          onError = {
           isLoading.value = false
          _showAlerDialog.value = it},

          onSuccess = {
          isLoading.value = false
          _showAlerDialog.value = it

      })
  }
  val clearError = { _showAlerDialog.value = null}
    fun showAccountTypes(){
        viewModelScope.launch {
            val selectedAccountType = bottomSheetHostState.showBottomSheet(accountTypes)
            Log.d("@Ramesh slected account type",selectedAccountType.listItem?.type1?.title.orEmpty())
            _beneficiary.value = _beneficiary.value.copy(
                accountType = selectedAccountType.listItem?.type1?.title.orEmpty()
            )
        }

    }
}

fun BeneficiaryViewModel.getLabelId(fieldName: String): Int? = this.getFieldLabels()[fieldName]