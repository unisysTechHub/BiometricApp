package com.example.biometricsample

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class BlankViewModel : ViewModel() {
        // TODO: Implement the ViewModel

    suspend fun getList(){
       val job = viewModelScope.async {
            getAPI()
        }
        job.await()
     }
    suspend fun getAPI() {

    }
}