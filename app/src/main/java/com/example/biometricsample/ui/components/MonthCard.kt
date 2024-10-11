package com.example.biometricsample.ui.components

data class MonthCard(val startDate : String, val endDate : String,val currentMonth:String, val nextMonth : String) {
}
//  extract date and month from date
// months in resouce file
// get month name
// store in list starting from current month indexed
// list item - month card.
// list of month card items.
//val monthlySpending = listOf()
// horizontal pager
/// backend
/// get items from transactin history database
//  calcluate sum of amount sepnt between the dates- statement amount
// API - monthlyStatementAmt : { start date , end date, amount}
// API - first time it calss on the hisotry on the screen and cache.