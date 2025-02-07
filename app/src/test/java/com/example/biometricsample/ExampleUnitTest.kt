package com.example.biometricsample

import android.util.Log
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.biometricsample.biometric.LoginBiometricViewModel
import com.example.biometricsample.biometric.LoginUUid
import com.example.biometricsample.biometric.LoginUiState
import com.example.biometricsample.biometric.UserRecord
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import java.util.UUID

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()
    @Mock
    lateinit var blankViewModel:LoginBiometricViewModel
    private val viewModel = LoginBiometricViewModel()

    @Before
    fun setup(){
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun `test login function updates userRecordLiveData`() {
        val observer = Observer<UserRecord> {}
        try {
            viewModel.userRecordLiveData.observeForever(observer)

            viewModel.login()

            val expectedUserRecord = UserRecord(userId = "Ramesb", jwt = "Jwt")
            assertEquals(expectedUserRecord, viewModel.userRecordLiveData.value)
        } finally {
            viewModel.userRecordLiveData.removeObserver(observer)
        }
    }



}