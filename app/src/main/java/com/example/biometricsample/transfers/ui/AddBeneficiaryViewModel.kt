package com.example.biometricsample.transfers.ui

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.api.TransfersUsecase
import com.example.api.transfersRepo.Result
import com.example.biometricsample.transfers.model.BeneficiaryModel
import com.example.biometricsample.transfers.model.toApiModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class AddBeneficiaryViewModel (val coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.IO+ Job())): ViewModel() {
    // TODO: Implement the ViewModel
    var transferUsecase = TransfersUsecase()
    val popBackStack : MutableLiveData<Unit?> = MutableLiveData(null)
    fun onSumbit(benenficaryModel: BeneficiaryModel, onError : (String) -> Unit,onSuccess : (String) -> Unit){
        Log.d("Ramesh", "onSubmit")

        val defferred = viewModelScope.async { transferUsecase.addBeneficiary(benenficaryModel.toApiModel()).collect(){
            when(it){
                is Result.Error -> onError(it.message)
                is Result.Success<*> -> {
                    onSuccess("Beneficiary added Success fully ")
                    it.data as com.example.api.request.BeneficiaryModel
                }
            }
        }
        }

    }
    fun popBackStack(){
        Log.d("Ramesh","popbackstack called")
        popBackStack.value = Unit
    }
}