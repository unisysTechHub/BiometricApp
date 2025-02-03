package com.example.biometricsample.transfers.list

import androidx.annotation.DrawableRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.api.AccountModel
import com.example.api.resposne.json.accountAPIJSon
import com.example.biometricsample.R
import com.google.gson.Gson

class TransfersListModel {
}
data class ListHeader(val title : String="Favs", val buttonText : String="Add", @DrawableRes val expandIcon : Int = R.drawable.ic_launcher_background, val onButtonClicked : (Boolean) -> Unit ={})
data class Type1(val title :String = "12323232")
data class Type2(val subtitle1 :String="Checking", val subtitle2:  String="jpmorgan")
data class ListModel(val header : ListHeader= ListHeader(), val items : List<ListItem> = mutableListOf(ListItem()), )
data class ListItem( val type1 : Type1 = Type1(), val type2: Type2 = Type2(),val onItemClicked : () -> Unit = {})
@Composable
fun ListView(modifier:Modifier,header: ListHeader, content: @Composable () -> Unit) {
    var expand by remember { mutableStateOf(true) }
//    val transition = updateTransition(targetState = expand, label = "Expand Rotation")
//
//    // Animated angle based on `expand` state
//    val angle by transition.animateFloat(
//        transitionSpec = { tween(durationMillis = 300) }, // Adjust animation duration as needed
//        label = "Rotate Animation"
//    ) { expanded ->
//        if (expanded) 180f else 0f
//    }

    Box(
        modifier = modifier
    ) {
        Column(modifier=modifier.clickable { header.onButtonClicked(true) }) {
            // Header Box
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp) // Adjust header height
                    .clickable {
                        expand = !expand


                    }, // Toggle on header click
                contentAlignment = Alignment.TopStart
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .align(Alignment.TopStart),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = header.title,
                        modifier = Modifier.weight(1f),
                        style = MaterialTheme.typography.displaySmall
                    )
                    Image(
                        painter = painterResource(id = header.expandIcon),
                        contentDescription = "Expand/Collapse",
                        modifier = Modifier
                           // .rotate(angle)
                            .size(24.dp).clickable { header.onButtonClicked(true) } // Adjust icon size
                    )
                }
            }
            showListCotent(modiifer = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(horizontal = 16.dp), expand = expand,content=content )


        }
    }
}

@Composable
 fun showListCotent(modiifer:Modifier,expand: Boolean,content : @Composable  () -> Unit){
     AnimatedVisibility(visible = expand) {
         Box(modifier=modiifer){
             content()
         }
     }
 }

@Preview
@Composable
fun ListViewPreview(){
    val account : AccountModel = Gson().fromJson(accountAPIJSon, AccountModel::class.java)
    val list = listOf(account).map { accountModel ->
        ListItem(type1 = Type1(accountModel.accountNumber),
            type2 = Type2(subtitle1 = accountModel.accountType,
                subtitle2 = accountModel.bankName)
        ) }
    ListView(modifier = Modifier.fillMaxSize(), header = ListHeader(title = "fav") ) {
               Text(text = "test")
    }
}