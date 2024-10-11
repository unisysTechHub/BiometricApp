package com.example.biometricsample.appcomponents

import android.content.Context
import android.content.SharedPreferences
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.platform.LocalContext

@Composable
fun SettingsScreen(darkModeEnabled: Boolean, context: Context= LocalContext.current) {
    SideEffect {
        // Store the dark mode state in shared preferences
        savePreference(context, "false",false)
    }

    // UI Content
    Text("Dark mode is ${if (darkModeEnabled) "enabled" else "disabled"}")
}
fun savePreference(context: Context, key: String, value: Boolean) {
    val sharedPreferences: SharedPreferences = context.getSharedPreferences("app_preferences", Context.MODE_PRIVATE)
    val editor = sharedPreferences.edit()
    editor.putBoolean(key, value)
    editor.apply() // Asynchronously saves the value
}
