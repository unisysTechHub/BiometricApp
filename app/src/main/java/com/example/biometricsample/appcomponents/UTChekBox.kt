package com.example.biometricsample.appcomponents

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.material3.TriStateCheckbox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.state.ToggleableState


@Composable
fun UTCheckBox() {
    Checkbox(checked = true, onCheckedChange = {})
    var toggleSate by remember {
        mutableStateOf(ToggleableState(false))
    }
    val childCheckedStates = remember { mutableStateListOf(false, false, false) }

    // Compute the parent state based on children's states
    val parentState = when {
        childCheckedStates.all { it } -> ToggleableState.On
        childCheckedStates.none { it } -> ToggleableState.Off
        else -> ToggleableState.Indeterminate
    }
    TriStateCheckbox(state = parentState, onClick = {
        val newState = parentState != ToggleableState.On
        childCheckedStates.forEachIndexed(){index: Int, checked: Boolean ->
                        childCheckedStates[index] = newState
    } })
    childCheckedStates.forEachIndexed { index, checked ->
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text("Option ${index + 1}")
            Checkbox(
                checked = checked,
                onCheckedChange = { isChecked ->
                    // Update the individual child state
                    childCheckedStates[index] = isChecked
                }
            )
        }
    }
}

