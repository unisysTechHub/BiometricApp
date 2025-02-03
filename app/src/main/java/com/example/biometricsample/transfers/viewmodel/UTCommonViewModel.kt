package com.example.biometricsample.transfers.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.biometricsample.appcomponents.BottomSheetHostState
import com.example.biometricsample.appcomponents.BottomSheetResult
import com.example.biometricsample.transfers.list.ListItem
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

open class UTCommonViewModel : ViewModel(){
    val bottomSheetHostState : BottomSheetHostState = BottomSheetHostState()


}