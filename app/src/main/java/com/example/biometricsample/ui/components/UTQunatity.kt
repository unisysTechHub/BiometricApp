package com.example.biometricsample.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.biometricsample.R
import kotlinx.coroutines.flow.MutableStateFlow


@Composable
fun  UTQuantity(state : MutableStateFlow<Int>,modifer : Modifier){

}
@Composable
fun UTQuanityContent(count : MutableStateFlow<Int>,modifer : Modifier ){
//    var count by remember {
//        mutableStateOf(initQty)
//    }

            val qty = count.collectAsState()
    Row(modifier = modifer, verticalAlignment = Alignment.CenterVertically) {
        Image(
            painter = painterResource(id = R.drawable.ic_launcher_background),
            contentDescription = "decerement quantity",
            modifier = Modifier
                .weight(1f)
                .clickable {
                    if (count.value != 1) {
                        count.value = count.value - 1
                    }
                }
        )
        Text(text = "${qty.value}", modifier = Modifier.weight(1f), style = TextStyle(fontSize = 50.sp), textAlign = TextAlign.Center)
        Image(
            painter = painterResource(id = R.drawable.ic_launcher_background),
            contentDescription = "decerement quantity",
            modifier = Modifier
                .weight(1f)
                .clickable { count.value = count.value + 1 }
        )
    }
}




@Preview
@Composable
fun UTQuantitiy_preview() {
    UTQuanityContent(count = MutableStateFlow(1), modifer = Modifier
        .width(300.dp)
        .height(100.dp))
}