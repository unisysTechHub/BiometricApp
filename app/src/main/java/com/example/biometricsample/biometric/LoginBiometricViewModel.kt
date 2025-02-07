package com.example.biometricsample.biometric

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import java.util.UUID

data class LoginUiState(val userId : String, val password : String);
object LoginUUid {
    val userId = UUID.fromString("user id")

    val password = UUID.fromString("password")
}
data class UserRecord(val userId: String,val jwt : String)
class LoginBiometricViewModel  :  ViewModel()
{
    val _uitState : MutableLiveData<LoginUiState> = MutableLiveData();
    val uiState : LiveData<LoginUiState> = _uitState
    val userRecordLiveData : MutableLiveData<UserRecord> = MutableLiveData()
    val statFlow :MutableStateFlow<Int> = MutableStateFlow(1);
    fun update(id : UUID, value : String) {
        _uitState.value = uiState.value?.copy(userId = value)
    }
    fun  login() {

        userRecordLiveData.value = UserRecord(userId = "Ramesb", jwt = "Jwt")
    }
    fun enrollBiometric() {

    }
}
