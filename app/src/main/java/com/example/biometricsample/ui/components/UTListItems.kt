package com.example.biometricsample.ui.components
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.window.core.layout.WindowHeightSizeClass
import androidx.window.core.layout.WindowSizeClass
import androidx.window.core.layout.WindowWidthSizeClass
import com.example.biometricsample.R
import com.example.biometricsample.transfers.UTBeneficiaryTypes
import kotlin.math.roundToInt
import kotlin.math.sqrt

data class UTGridItem(val iamgeUrl : String?="", val title : String,
                      val price : String?="",val onItemClick : ((UTGridItem) -> Unit))

//val windowSizeClass = currentWindowAdaptiveInfo().windowSizeClass
@Composable
fun UTGridItemBoxMaxSize() {

}
@Composable
fun UTGridItemsBox(modifier : Modifier, maxSize:Pair<Dp, Dp>, itemSize: Pair<Int,Int> = Pair(200,200), items : List<UTGridItem> ){
        Box(modifier = modifier) {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(16.dp))
                    .background(color = Color.Red)
                    .align(Alignment.BottomEnd)
            ) {
                UTGridItemsContent( items = items,maxSize =maxSize,itemSize = itemSize)
            }
        }
}

@Composable
fun UTGridItemsContent(modifier: Modifier=Modifier,maxSize:Pair<Dp, Dp>,itemSize: Pair<Int,Int> = Pair(200,200),items : List<UTGridItem>) {
    val windowSizeClass = WindowSizeClass.compute( maxSize.first.value,maxSize.second.value)
    when(Pair(windowSizeClass.windowWidthSizeClass,windowSizeClass.windowHeightSizeClass)) {

            Pair(WindowWidthSizeClass.COMPACT,WindowHeightSizeClass.MEDIUM) ->
                UTGridVerticalLayout(items=items, itemSize = itemSize)

           Pair(WindowWidthSizeClass.EXPANDED,WindowHeightSizeClass.MEDIUM) ->
               UTGridHorizontalLayout(items = items, itemSize = itemSize )
        }

}

@Composable
fun UTGridVerticalLayout(items : List<UTGridItem>,modifier:Modifier= Modifier,itemSize: Pair<Int,Int> = Pair(200,200),
                columns : Int = 2 ) {

    LazyVerticalGrid(
        columns = GridCells.Fixed(columns), // 2 items per row
        modifier = modifier.padding(32.dp)
    ) {
        itemsIndexed(items) { index, item ->
            UTGridItemCard(item = item,itemSize,item.onItemClick)
        }
    }
}
@Composable
fun UTGridHorizontalLayout(modifier:Modifier= Modifier,itemSize: Pair<Int,Int> = Pair(200,200),
                 items : List<UTGridItem>) {

    LazyHorizontalGrid(
        rows = GridCells.Fixed(2), // 2 items per row
        modifier = modifier.padding(top = 32.dp)
    ) {
        itemsIndexed(items) { index, item ->
            UTGridItemCard(item = item,itemSize,item.onItemClick)
        }
    }

}

@Composable
fun UTGridItemCard(item : UTGridItem,itemSize: Pair<Int,Int> = Pair(200,200), onItemClick: ((UTGridItem) -> Unit)){
    Column(modifier = Modifier.width(itemSize.first.dp).height(itemSize.second.dp)) {
        Box(modifier = Modifier
            .weight(1f)
            .fillMaxWidth().clickable { onItemClick(item) }.testTag(item.title), contentAlignment = Alignment.Center){
            Image(
                modifier = Modifier.fillMaxSize()
                    .align(alignment = Alignment.Center),
                painter = painterResource(id = R.drawable.ic_launcher_background),
                contentDescription = "grid item image"
            )
        }
        Column(modifier=Modifier.fillMaxWidth()) {
            Text(text = "${item.title}", modifier = Modifier.fillMaxWidth(), style = MaterialTheme.typography.titleLarge, textAlign = TextAlign.Center)
            Text(text = "${item.price}",modifier = Modifier.fillMaxWidth(), style = MaterialTheme.typography.titleLarge, textAlign = TextAlign.Center)

        }
            }
}

//@Preview
//@Composable
//fun UTLIstitemsPreview() {
//    UTLisItems(modifier = Modifier, items = listOf() )
//}

//@Preview
//@Composable
//fun UTGridItemPreview() {
//    UTGridItem(item = UTListItem(title = "Cheery smoothy 80ml",
//        price = "$17.99", iamgeUrl = ""){
//})
//}

@Preview(device = Devices.TABLET)
@Composable
fun UTGridLayoutPreview(){
    var x=0; var y =0
    val gridLayoutArea = (600 -16) * (900-32)

    val itemSize = sqrt((gridLayoutArea /6).toDouble()).toInt()
    val itemSize1=250
    val rows =  ((900-32)/itemSize1).toInt()
    val columns = 5/rows

    UTGridVerticalLayout(modifier = Modifier.size(600.dp,900.dp).padding(top = 16.dp, bottom = 16.dp), itemSize = Pair(200,300
    ),items = listOf(UTGridItem(title = "Cheery smoothy 80ml",
        price = "$17.99", iamgeUrl = ""){},UTGridItem(title = "Cheery smoothy 80ml",
        price = "$17.99", iamgeUrl = ""){},UTGridItem(title = "Cheery smoothy 80ml",
        price = "$17.99", iamgeUrl = ""){},UTGridItem(title = "Cheery smoothy 80ml",
        price = "$17.99", iamgeUrl = ""){},UTGridItem(title = "Cheery smoothy 80ml",
        price = "$17.99", iamgeUrl = ""){}), columns = 2 )
}

@Preview
@Composable
fun UTListItemsPreview(){

    UTGridVerticalLayout(modifier = Modifier.fillMaxSize(),  items = listOf(UTGridItem(title = "Cheery smoothy 80ml",
        price = "$17.99", iamgeUrl = ""){},UTGridItem(title = "Cheery smoothy 80ml",
        price = "$17.99", iamgeUrl = ""){}) )
}
