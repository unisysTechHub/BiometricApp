package com.example.biometricsample.appcomponents

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.biometricsample.transfers.viewmodel.UTCommonViewModel
import com.example.biometricsample.ui.components.UTGridItem
import com.example.biometricsample.ui.components.UTGridVerticalLayout
import com.example.compose.UTBottomBar
import com.example.compose.UTTopBar

//@Composable
//fun TopAppBar(title: String, onBackClick: () -> Unit) {
//
//        Box(modifier = Modifier.fillMaxWidth()) {
//            // Back Button
//            IconButton(
//                onClick = onBackClick,
//                modifier = Modifier.align(Alignment.CenterStart)
//            ) {
//                Icon(
//                    Icons.Rounded.ArrowBack,
//                    contentDescription = stringResource(id = R.string.arrow_back)
//                )
//            }
//
//            // Centered Title
//            Text(
//                text = title,
//                textAlign = TextAlign.Center,
//                fontSize = MaterialTheme.typography.headlineMedium.fontSize,
//                modifier = Modifier.align(Alignment.Center)
//            )
//        }
//
//
//}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UTScaffold(
    title: String?=null, viewModel: UTCommonViewModel = UTCommonViewModel(),
    onBackClick: (() -> Unit)?=null,
    onbottomBarButtonClick:(()-> Unit)?=null,
    bottomBarButtonTitle: String?=null,
    content: @Composable (paddingValues: PaddingValues) -> Unit){
    Scaffold( modifier = Modifier.fillMaxSize(), topBar = {
       UTTopBar(title = title,onBackClick=onBackClick)
    }, bottomBar = { UTBottomBar(title=bottomBarButtonTitle,onSubmit=onbottomBarButtonClick)
    }) {
        content(it)
        val currentBottomSheetData =
            viewModel.bottomSheetHostState.currentBottomData

        currentBottomSheetData?.let{data -> BottomSheetHost(hostState = viewModel.bottomSheetHostState)}
    }
}
@Composable
fun UTScaffoldMain() {
    val navController = rememberNavController()
    Scaffold(bottomBar = {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
            Text(text = "Test", modifier = Modifier.clickable { navController.navigate("Home") })
            Text(text = "Transfers", modifier = Modifier.clickable { navController.navigate("Transfers") })
            Text(text = "More", modifier = Modifier.clickable { navController.navigate("More") })
        }

    }) {
        MainScreen(paddingValues = it)
    }
}

@Composable
fun MainScreen(paddingValues: PaddingValues){
    NavHost(navController = rememberNavController(), startDestination = "Transfers"){
        composable("Home"){
            Text(text = "Home", modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues), textAlign = TextAlign.Center)
        }
        composable("Transfers"){
            UTGridVerticalLayout(modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),  items = listOf(UTGridItem(title = "Cheery smoothy 80ml",
                price = "$17.99", iamgeUrl = ""){}, UTGridItem(title = "Cheery smoothy 80ml",
                price = "$17.99", iamgeUrl = ""){}) )
        }
    }
}

@Preview
@Composable
fun UTScaffoldPreview(){
    UTScaffold(){
        Box {
            Text(text = "Home", modifier = Modifier.fillMaxSize(), textAlign = TextAlign.Center)
        }
    }
}
@Preview
@Composable
fun TopbarPreview(){

}