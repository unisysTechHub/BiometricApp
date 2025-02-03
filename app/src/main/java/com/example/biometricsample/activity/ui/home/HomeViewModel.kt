package com.example.biometricsample.activity.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
sealed class NavigationEvent {
    object NavigateToFragmentA : NavigationEvent()
    object NavigateToFragmentB : NavigationEvent()
    object NavigateToFragmentC : NavigationEvent()
}
class HomeViewModel : ViewModel() {
    private val _navigationEvent = MutableLiveData<NavigationEvent>()
    val navigationEvent: LiveData<NavigationEvent> get() = _navigationEvent

    fun navigateToFragmentA() {
        _navigationEvent.value = NavigationEvent.NavigateToFragmentA
    }

    fun navigateToFragmentB() {
        _navigationEvent.value = NavigationEvent.NavigateToFragmentB
    }

    fun navigateToFragmentC() {
        _navigationEvent.value = NavigationEvent.NavigateToFragmentC
    }
    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text
}