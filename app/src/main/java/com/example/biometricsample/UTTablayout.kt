package com.example.biometricsample

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabPosition
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Yellow
import androidx.compose.ui.layout.VerticalAlignmentLine
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.Collections

class TabloutPractice {
}

object TabLayoutScreenUIId {
    val firstName = "FirstName"
    val lastName =  "LastName"
}


@Stable
data class TabLayouState(val textFieldMap: MutableMap<String, String> = mutableMapOf(Pair("",""))){

}

data class  TabContentData (val firstName : String, val lastName : String )

class TabLayoutScreenViewModel : ViewModel()
{
    private val  _uiState = MutableLiveData(TabLayouState())
    val uiState : LiveData<TabLayouState> = _uiState

    fun update(id : String, value : String) {
//        when (id ){
//            TabLayoutScreenUIId.firstName -> {_uiState.value = _uiState.value?.copy(firstName = value)
//            }
//            TabLayoutScreenUIId.lastName -> {_uiState.value = _uiState.value?.copy(lastName= value)
//            }
//        }
        _uiState.value?.textFieldMap?.set(id, value)
        _uiState.value = _uiState.value?.copy(textFieldMap = _uiState.value?.textFieldMap?.toMutableMap() ?: mutableMapOf())

        //_uiState.value = MutableLiveData(TabLayouState(mutableMapOf(Pair(TabLayoutScreenUIId.firstName,"FurstBame")))).value
    }
    fun onSubmit() {

        val tabContentData = TabContentData(firstName = uiState.value?.textFieldMap?.get(TabLayoutScreenUIId.firstName)!!,
            lastName = uiState.value?.textFieldMap?.get(TabLayoutScreenUIId.lastName)!! )
    }
}
@Composable
fun TablayoutPractice(viewModel : TabLayoutScreenViewModel= TabLayoutScreenViewModel()) {
    var selectedTab by remember { mutableStateOf(SleepTab.Week) }
    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(32.dp)) {
        JetLaggedHeaderTabs(
            onTabSelected = { selectedTab = it },
            selectedTab = selectedTab,
        )
        AnimatedContent(targetState = selectedTab, label = "") {
            Box(modifier = Modifier.fillMaxHeight()){
                Box(modifier = Modifier.align(Alignment.TopStart)) {
                    when (it) {
                        SleepTab.Day -> TablayoutConent(viewModel = viewModel)
                        SleepTab.Week -> TablayoutConent(viewModel = viewModel)
                        SleepTab.Month -> TablayoutConent(viewModel = viewModel)
                        SleepTab.SixMonths -> TablayoutConent(viewModel = viewModel)
                        SleepTab.OneYear -> TablayoutConent(viewModel = viewModel)
                    }
                }

            }



        }
    }
    SideEffect {
        println( "@Ramesh ${selectedTab.name}")
    }



//    Crossfade(targetState = selectedTab, label = "", modifier = Modifier.padding(84.dp)) { selectedTab ->
//        when (selectedTab) {
//            SleepTab.Day -> Text("SleepTab.Day")
//            SleepTab.Week -> Text("SleepTab.Day")
//            SleepTab.Month -> Text("SleepTab.Day")
//            SleepTab.SixMonths -> Text("SleepTab.Day")
//            SleepTab.OneYear -> Text("SleepTab.Day")
//        }
//    }
}
@Composable
fun TablayoutConent(viewModel: TabLayoutScreenViewModel) {
    val firstName1 = viewModel.uiState.observeAsState().value?.textFieldMap?.get(TabLayoutScreenUIId.firstName)
        ?: ""
    val lastName1 = viewModel.uiState.observeAsState().value?.textFieldMap?.get(TabLayoutScreenUIId.lastName)
        ?: ""
//    val firstName = viewModel.uiState.observeAsState().value?.firstName        ?: ""
//    val lastName  = viewModel.uiState.observeAsState().value?.lastName   ?: ""
    Card {
        TextField(value = firstName1  ,
            onValueChange = { viewModel.update(TabLayoutScreenUIId.firstName,it)
            })
        TextField(value = lastName1 ,
            onValueChange = { viewModel.update(TabLayoutScreenUIId.lastName,it)
            })
    }
}


@Composable
fun JetLaggedHeaderTabs(
    onTabSelected: (SleepTab) -> Unit,
    selectedTab: SleepTab,
    modifier: Modifier = Modifier,
) {
    ScrollableTabRow(
        modifier = modifier,
        edgePadding = 12.dp,
        selectedTabIndex = selectedTab.ordinal,
        containerColor = Color.White,
        indicator = { tabPositions: List<TabPosition> ->
            Box(
                Modifier
                    .tabIndicatorOffset(tabPositions[selectedTab.ordinal])
                    .fillMaxSize()
                    .padding(horizontal = 2.dp)
                    .border(BorderStroke(2.dp, Yellow), RoundedCornerShape(10.dp))
            )
        },
        divider = { }
    ) {
        SleepTab.values().forEachIndexed { index, sleepTab ->
            val selected = index == selectedTab.ordinal
            SleepTabText(
                sleepTab = sleepTab,
                selected = selected,
                onTabSelected = onTabSelected,
                index = index
            )
        }
    }
}

private val textModifier = Modifier
    .padding(vertical = 6.dp, horizontal = 4.dp)
@Composable
private fun SleepTabText(
    sleepTab: SleepTab,
    selected: Boolean,
    index: Int,
    onTabSelected: (SleepTab) -> Unit,
) {
    Tab(
        modifier = Modifier
            .padding(horizontal = 2.dp)
            .clip(RoundedCornerShape(16.dp)),
        selected = selected,
        unselectedContentColor = Color.Black,
        selectedContentColor = Color.Black,
        onClick = {
            onTabSelected(SleepTab.values()[index])
        }
    ) {
        Text(
            modifier = textModifier,
            text = stringResource(id = sleepTab.title),
            style = SmallHeadingStyle
        )
    }
}
enum class SleepTab(val title: Int) {
    Day(R.string.sleep_tab_day_heading),
    Week(R.string.sleep_tab_week_heading),
    Month(R.string.sleep_tab_month_heading),
    SixMonths(R.string.sleep_tab_six_months_heading),
    OneYear(R.string.sleep_tab_one_year_heading)
}

val SmallHeadingStyle = TextStyle(
    fontSize = 16.sp,
    fontWeight = FontWeight(600),
    letterSpacing = 0.5.sp

)

@Composable
@Preview
fun TablaoutPractivePreview(){
    TablayoutPractice()
}