package com.example.biometricsample.transfers.ui

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.window.core.layout.WindowHeightSizeClass
import androidx.window.core.layout.WindowSizeClass
import androidx.window.core.layout.WindowWidthSizeClass
import com.example.biometricsample.animations.UTMoneyServiceTypes
import com.example.biometricsample.animations.cardModel
import com.example.biometricsample.transfers.UTBeneficiaryTypes
import com.example.biometricsample.transfers.cardModel
import com.example.biometricsample.ui.components.UTGridItem
import com.example.biometricsample.ui.components.UTGridVerticalLayout
import kotlin.math.sqrt

@Composable
fun BeneficiaryHomeScreen(types : List<String>, onTypeClicked : () -> Unit = {}){


}

@Composable
fun UTGridBeneficiaryTypeListScreen(onItemClicked : (UTGridItem) -> Unit) {
    val items = UTBeneficiaryTypes.values()
        .map { UTGridItem(title = it.cardModel().title, onItemClick = onItemClicked) }
    BoxWithConstraints(modifier = Modifier
        .fillMaxHeight()
        .fillMaxWidth()) {
          val windowSizeClass = WindowSizeClass.compute( maxWidth.value,maxHeight.value)
        when(Pair(windowSizeClass.windowWidthSizeClass,windowSizeClass.windowHeightSizeClass)) {

            Pair(WindowWidthSizeClass.COMPACT, WindowHeightSizeClass.MEDIUM) ->
                UTGridVerticalLayout(items=items, itemSize = Pair(150,150), columns = 3)

            Pair(WindowWidthSizeClass.EXPANDED, WindowHeightSizeClass.MEDIUM) ->
                UTGridVerticalLayout(items=items, itemSize = Pair(200,300), columns = 3)

            //UTGridHorizontalLayout(items = items, itemSize = Pair(400,400) )
        }
    }
}

