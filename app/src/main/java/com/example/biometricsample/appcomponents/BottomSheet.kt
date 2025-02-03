package com.example.biometricsample.appcomponents

import android.util.Log
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import com.example.biometricsample.transfers.viewmodel.TransferListViewModel
import com.example.biometricsample.transfers.list.ListItem
import com.example.compose.arrowhead
import com.example.compose.rememberViewModel
import kotlinx.coroutines.CancellableContinuation
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlin.coroutines.resume
@Stable
interface BottomSheetData {
    val list: List<ListItem>
     fun dismiss()
    fun performAction(item: ListItem)
}
 class BottomSheetResult(val listItem: ListItem?)


@Stable
class BottomSheetHostState {
    private val mutex = Mutex()
    var currentBottomData by mutableStateOf<BottomSheetData?>(null)
        private set
    suspend fun showBottomSheet(
        list: List<ListItem>): BottomSheetResult = mutex.withLock {
        try {
            return suspendCancellableCoroutine { continuation ->
                currentBottomData = BottomSheetDataImpl(list=list, continuation)
                Log.d("@Ramesh current bottomdata", (currentBottomData as BottomSheetData).list.size.toString())
            }
        } finally {
            currentBottomData = null
        }
    }

    @Stable
    private class BottomSheetDataImpl(
        override val list: List<ListItem>,
        private val continuation: CancellableContinuation<BottomSheetResult>
    ) : BottomSheetData {

        override fun performAction(item : ListItem) {
            if (continuation.isActive) continuation.resume(BottomSheetResult(item))
        }

        override fun dismiss() {
            if (continuation.isActive) continuation.resume(BottomSheetResult(null))
        }
    }
}
@Composable
fun BottomSheetHost(
    hostState : BottomSheetHostState,
    modifier: Modifier = Modifier
        .fillMaxWidth()
        .background(Color.Red)
    ) {
    val currentBottomSheetData = hostState.currentBottomData

    currentBottomSheetData?.let{data ->
        Popup(
            alignment = Alignment.BottomCenter,
            onDismissRequest = { currentBottomSheetData.dismiss() },
            properties = PopupProperties(focusable = true)
        ) {
            Surface(
                modifier = modifier
                    .wrapContentHeight(),
                shape = MaterialTheme.shapes.medium,
                shadowElevation = 4.dp,
                color = MaterialTheme.colorScheme.surface
            ) {
//                Column {
//                    Text(text = data.list.size.toString())
//                    Text(text = "Test")
//                }
                LazyColumn(
                    modifier = Modifier.padding(8.dp)
                ) {
                    items(count = data.list.size, key = {data.list.get(it).type1.title} ){
                        val item = data.list.get(it)
                        Column() {
                            Divider(color = Color.LightGray, thickness = 1.dp)
                            Row(modifier= Modifier
                                .fillMaxWidth()
                                .height(IntrinsicSize.Min)
                                .clickable { data.performAction(item) }) {
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
            }
        }

    }
}


@Preview
@Composable
fun BottomeSheetPreview(){
    val viewModel = rememberViewModel(viewModel = TransferListViewModel())
    LaunchedEffect(key1 = "test") {
        viewModel.getAccounts()
    }
    val list = viewModel.favaourites.collectAsState()


      val data = object : BottomSheetData{
          override val list: List<ListItem>
              get() = list.value.items

          override fun dismiss() {
              TODO("Not yet implemented")
          }

          override fun performAction(item: ListItem) {
              TODO("Not yet implemented")
          }
      }
    val bottomSheetHostState = viewModel.bottomSheetHostState
    viewModel.showBottomSheet()
    Text(text = (bottomSheetHostState.currentBottomData as BottomSheetData).list.size.toString(), modifier = Modifier.fillMaxSize())
   BottomSheetHost(hostState = viewModel.bottomSheetHostState)
}