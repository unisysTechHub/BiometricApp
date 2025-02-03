package com.example.biometricsample.transfers.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.api.AccountModel
import com.example.api.Callback
import com.example.api.TransfersUsecase
import com.example.api.request.BeneficiaryModel
import com.example.api.transfersRepo.Result
import com.example.biometricsample.transfers.list.ListHeader
import com.example.biometricsample.transfers.list.ListItem
import com.example.biometricsample.transfers.list.ListModel
import com.example.biometricsample.transfers.list.Type1
import com.example.biometricsample.transfers.list.Type2
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

open class TransferListViewModel : UTCommonViewModel(){
    val error : MutableLiveData<String> = MutableLiveData("")
    val isLoading : MutableLiveData<Boolean> = MutableLiveData(true);
    val transfersUsecase = TransfersUsecase()
    var favaourites : MutableStateFlow<ListModel> = MutableStateFlow(ListModel(items=listOf(ListItem())))
    var beneficiaries : MutableStateFlow<ListModel> = MutableStateFlow(ListModel(items=listOf(ListItem())))
    val selectedItem : MutableLiveData<ListItem> = MutableLiveData()
    suspend fun getAccounts() {

        val job = viewModelScope.async { transfersUsecase.getAccounts()
        }

        val accuntsFlow : Flow<Result> = job.await()
        accuntsFlow.collect(){
              when(it){
                  is Result.Error -> {
                      isLoading.value=false
                      error.value = it.message
                      Log.d("@Ramesh", "Accont API Error")}
                  is Result.Success<*> -> {
                      isLoading.value=false
                      //filter accounts based on transfer type
                      favaourites.value =   ListModel( header = ListHeader(title = "Favourites", onButtonClicked = {this.showBottomSheet()}),
                          items= ( it as Result.Success<List<AccountModel>>).data.map { accountModel ->
                          ListItem(type1 = Type1(accountModel.accountNumber),
                              type2 = Type2(subtitle1 = accountModel.accountType,
                                  subtitle2 = accountModel.bankName), onItemClicked = {}
                          ) })
                      //showBottomSheet()
                  }
              }
        }
    }
    suspend fun getBeneficiaries() {

        val job = viewModelScope.async { transfersUsecase.getBeneficiariesList()
        }

        val accuntsFlow : Flow<Result> = job.await()
        accuntsFlow.collect(){
            when(it){
                is Result.Error -> {
                    isLoading.value=false
                    error.value = it.message
                    Log.d("@Ramesh", "Accont API Error")}
                is Result.Success<*> -> {
                    isLoading.value=false
                    //filter beneficiars based on transfer type
                    beneficiaries.value =   ListModel( header = ListHeader(title = "Recipients", onButtonClicked = {}),
                        items= ( it as Result.Success<List<BeneficiaryModel>>).data.map { recipientModel ->
                            ListItem(type1 = Type1(recipientModel.beneficiaryName),
                                type2 = Type2(subtitle1 = recipientModel.accountType,
                                    subtitle2 = recipientModel.bankName), onItemClicked = {}
                            ) })
                    //showBottomSheet()
                }
            }
        }
    }
  suspend fun getAccountsSuspendableCoroutine(){
      isLoading.value=false
      val deferredJob : Deferred<List<AccountModel>> = viewModelScope.async {

          suspendCancellableCoroutine {
              val callback = object : Callback<List<AccountModel>>{
                  override fun onCompleted(value: List<AccountModel>) {
                      it.resume(value)
                  }
                  override fun onApiError(message: String) {
                  }
              }
              transfersUsecase.getAccounts(callback=callback)
          }

      }
      favaourites.value = ListModel( header = ListHeader(title = "Favourites"),items =  formatData(deferredJob.await()))
  }
  val formatData : (List<AccountModel>) -> List<ListItem> = { list -> list.map { accountModel ->
      ListItem(type1 = Type1(accountModel.accountNumber),
          type2 = Type2(subtitle1 = accountModel.accountType,
              subtitle2 = accountModel.bankName)
      ) }}

    fun showBottomSheet() {
         viewModelScope.launch {
          selectedItem.value =   bottomSheetHostState.showBottomSheet(favaourites.value.items).listItem
         }
    }

 // add metods for showing bottom sheets for accounts and befeciaries
}

