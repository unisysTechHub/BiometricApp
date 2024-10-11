package com.example.biometricsample.animations

import android.service.controls.DeviceTypes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.window.core.layout.WindowHeightSizeClass
import androidx.window.core.layout.WindowSizeClass
import androidx.window.core.layout.WindowWidthSizeClass
import com.example.biometricsample.R
import com.example.biometricsample.ui.components.UTGridHorizontalLayout
import com.example.biometricsample.ui.components.UTGridItem
import com.example.biometricsample.ui.components.UTGridItemsBox
import com.example.biometricsample.ui.components.UTGridVerticalLayout
import kotlin.math.sqrt

enum class UTMoneyServiceTypes {
    Transfers,
    Zelle,
    BillPay,
    Plaid
}


data class MoneyServiceTypeModel (  val title: String, @DrawableRes val image: Int)

fun UTMoneyServiceTypes.cardModel() : MoneyServiceTypeModel =  when (this) {


        UTMoneyServiceTypes.Transfers -> MoneyServiceTypeModel(
            title = UTMoneyServiceTypes.Transfers.name,
            image = R.drawable.ic_launcher_background
        )
        UTMoneyServiceTypes.Zelle -> MoneyServiceTypeModel(
            title = UTMoneyServiceTypes.Zelle.name,
            image = R.drawable.ic_launcher_background
        )

        UTMoneyServiceTypes.BillPay -> MoneyServiceTypeModel(
        title = UTMoneyServiceTypes.BillPay.name,
        image = R.drawable.ic_launcher_background
    )
        UTMoneyServiceTypes.Plaid ->  MoneyServiceTypeModel(
            title = UTMoneyServiceTypes.Plaid.name,
            image = R.drawable.ic_launcher_background
        )
    }

@Composable
fun UTMoneyServiceTypesScreen(modifier: Modifier) {
   
    val items = UTMoneyServiceTypes.values().map { UTGridItem(title = it.cardModel().title) }
    BoxWithConstraints {
        val windowSizeClass = WindowSizeClass.compute( maxWidth.value,maxHeight.value)
        Column(modifier= modifier) {
            BoxWithConstraints(modifier = Modifier
                .height(400.dp)
                .fillMaxWidth()) {
            //    val windowSizeClass = WindowSizeClass.compute( maxWidth.value,maxHeight.value)

                val gridLayoutArea = (maxWidth.value -16) * (maxHeight.value-32)
                val itemSize = sqrt((gridLayoutArea /items.size).toDouble()).toInt()
                val columns =  (600-16)/itemSize
                // calculate items per row
                // tablet HorizontalLayout
                //    UTGridVerticalLayout(items=items, itemSize = Pair(itemSize,itemSize), columns = columns)

                when(Pair(windowSizeClass.windowWidthSizeClass,windowSizeClass.windowHeightSizeClass)) {

                    Pair(WindowWidthSizeClass.COMPACT, WindowHeightSizeClass.MEDIUM) ->
                        UTGridVerticalLayout(items=items, itemSize = Pair(150,150), columns = 3)

                    Pair(WindowWidthSizeClass.EXPANDED, WindowHeightSizeClass.MEDIUM) ->
                        UTGridVerticalLayout(items=items, itemSize = Pair(200,300), columns = 3)

                    //UTGridHorizontalLayout(items = items, itemSize = Pair(400,400) )
                }
            }
            BoxWithConstraints(modifier = Modifier
                .height(450.dp)
                .fillMaxSize()) {
             //   val windowSizeClass = WindowSizeClass.compute( maxWidth.value,maxHeight.value)
                val gridLayoutArea = (maxWidth.value -16) * (maxHeight.value-32)
                val itemSize = sqrt((gridLayoutArea /items.size).toDouble()).toInt()
                val columns =  (600-16)/itemSize
                // calculate items per row
                // tablet HorizontalLayout
                //    UTGridVerticalLayout(items=items, itemSize = Pair(itemSize,itemSize), columns = columns)

                when(Pair(windowSizeClass.windowWidthSizeClass,windowSizeClass.windowHeightSizeClass)) {

                    Pair(WindowWidthSizeClass.COMPACT, WindowHeightSizeClass.MEDIUM) ->
                        UTGridVerticalLayout(items=items, itemSize = Pair(150,150), columns = 3)

                    Pair(WindowWidthSizeClass.EXPANDED, WindowHeightSizeClass.MEDIUM) ->
                        UTGridVerticalLayout(items=items, itemSize = Pair(200,300), columns = 3)

                    //UTGridHorizontalLayout(items = items, itemSize = Pair(400,400) )
                }
            }

        }

    }

}

@Composable
@Preview(device = Devices.PHONE)
fun UTMoneyServiceTypesScreenPreview(){
    UTMoneyServiceTypesScreen(modifier = Modifier.fillMaxSize())
}
sealed class Result()
object Loading : Result()
object Error : Result()

class  Success(data:String) : Result()
class ScreenViewmodel : ViewModel() {
    val networkState : MutableLiveData<Result> = MutableLiveData(Loading)

    init {
        Thread.sleep(3000)
        networkState.postValue(Error)

    }

}

