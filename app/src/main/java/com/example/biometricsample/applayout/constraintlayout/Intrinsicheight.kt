package com.example.biometricsample.applayout.constraintlayout

import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun UTIntrinscHeight(){

    Row(modifier = Modifier.height(IntrinsicSize.Max)) {
        Text(text = "First one", modifier = Modifier.weight(1f))
        Divider(modifier = Modifier
            .fillMaxHeight()
            .width(2.dp))
        Text(text = "First one", modifier = Modifier.weight(1f))

    }
}


@Preview
@Composable
fun UTIntrinsic_preview() {
    UTIntrinscHeight()
}