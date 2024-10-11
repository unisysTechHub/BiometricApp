package com.example.biometricsample.animations

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color

class TransitionData(color : State<Color>) {
    val color by color
}