package com.example.biometricsample.appcomponents

import android.text.Layout
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.biometricsample.R
import com.example.biometricsample.transfers.list.ListItem

@Composable
fun UTSelect(modifier: Modifier= Modifier
    .fillMaxWidth()
             ,value:String="Select from List",
             onClick:()->Unit) {
    Box(
        modifier = modifier.border(1.dp, color = Color.Gray, shape = RoundedCornerShape(8.dp)),
            contentAlignment = Alignment.Center
        ,
    ) {
        Column(modifier = Modifier.clickable { onClick() }, horizontalAlignment = Alignment.CenterHorizontally) {
            // Header Box
            Box(
                modifier = Modifier.fillMaxWidth(), // Adjust header height ,, // Toggle on header click
                contentAlignment = Alignment.Center
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .align(Alignment.TopStart),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = value,
                        modifier = Modifier.weight(1f),
                        style = LocalTextStyle.current
                    )
                    Image(
                        painter = painterResource(id = R.drawable.ic_launcher_background),
                        contentDescription = "Expand/Collapse",
                        modifier = Modifier
                            // .rotate(angle)
                            .size(24.dp)
                            .clickable { onClick() } // Adjust icon size
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun UTSelectPreivew(){
    UTSelect {

    }
}