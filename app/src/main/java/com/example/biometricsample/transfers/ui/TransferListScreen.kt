package com.example.biometricsample.transfers.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.biometricsample.animations.UTProgress
import com.example.biometricsample.appcomponents.UTScaffold
import com.example.biometricsample.transfers.viewmodel.TransferListViewModel
import com.example.biometricsample.transfers.list.ListHeader
import com.example.biometricsample.transfers.list.ListItem
import com.example.biometricsample.transfers.list.ListView
import com.example.compose.arrowhead

@Composable
fun TransferListScreen(){
    NavHost(navController = rememberNavController(), startDestination = "TransferListHome" ){
        composable(route="TransferListHome") {
            TransferList(viewModel = TransferListViewModel())
        }

    }
}
@Composable
fun List<ListItem>.toView() : Unit {
    LazyColumn(modifier=Modifier.fillMaxWidth()) {
        listItems(items = this@toView )
    }
}

fun LazyListScope.listItems(items: List<ListItem>){
    items(count = items.size, key = {items.get(it).type1.title} ){
        val item = items.get(it)
        Column(modifier = Modifier.clickable {item.onItemClicked() }) {

            Divider(color = Color.LightGray, thickness = 1.dp)
            Row(modifier= Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min)) {
                Column(modifier= Modifier.weight(7f)) {
                    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center){
                        Text(text = item.type1.title)
                    }
                    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                        Row {
                            Text(text = item.type2.subtitle1,modifier= Modifier
                                .wrapContentSize()
                                .padding(end = 8.dp), textAlign = TextAlign.End)
                            Text(text = item.type2.subtitle2,modifier= Modifier
                                .wrapContentSize()
                                .padding(start = 8.dp), textAlign = TextAlign.Start)
                        }
                    }
                }
                Box(
                    modifier = Modifier
                        .weight(1f), // Set desired height
                    contentAlignment = Alignment.CenterEnd // Aligns content to the right end
                ) {
                    Icon(imageVector =  arrowhead, contentDescription ="" ,modifier= Modifier
                        .size(24.dp)
                        .scale(3f))

                }

                }
            Divider(color = Color.LightGray, thickness = 1.dp)
        }
    }
}

@Composable
fun TransferList(viewModel : TransferListViewModel){
    LaunchedEffect(key1 = "onLauch") {
        viewModel.getAccounts()
      //  viewModel.getBeneficiaries()
    }
    val favrourtesModel = viewModel.favaourites.collectAsState()
    val beneficiariesModel = viewModel.beneficiaries.collectAsState()
    val error = viewModel.error.observeAsState()
    val loading = viewModel.isLoading.observeAsState()

    UTScaffold(viewModel =viewModel) {
        if (loading.value == true){
            UTProgress()
        }
        else {
            Box(modifier = Modifier.fillMaxSize()) {

                if (error.value.isNullOrEmpty()) {
                    Column(modifier=Modifier.align(Alignment.TopStart)) {
                        ListView(
                            modifier = Modifier
                                .wrapContentSize()
                                , header = favrourtesModel.value.header
                        ) { favrourtesModel.value.items.toView() }
//                        ListView(
//                            modifier = Modifier
//                                .wrapContentSize()
//                            , header = beneficiariesModel.value.header
//                        ) { beneficiariesModel.value.items.toView() }
                    }


                } else {
                    Text(text = error.value!!, modifier = Modifier.fillMaxSize())
                }
            }
        }
    }

}
@Composable
fun ListItemHeaderCard(header: ListHeader){
    Row {
        Box {
            Text(text = header.title, modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopStart), textAlign = TextAlign.Start)
            Text(text = header.title, modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopEnd), textAlign = TextAlign.Start)
        }
    }
}
@Composable
fun LisItemCard(item : ListItem){
    Card {
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(text = item.type1.title,modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Start)
            Text(text = item.type2.subtitle2,modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Start)
        }
    }
}
@Preview
@Composable
fun listView(){
        TransferList(viewModel = TransferListViewModel())

//    ListView(header = ListHeader()){
//        Text(text = "Test")
//    }
    // TransferListViewModel().favaourites.value.toView()
    //   ListView(ListModel())
}
