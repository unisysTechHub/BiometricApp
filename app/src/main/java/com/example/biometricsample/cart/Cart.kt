package com.example.biometricsample.cart

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable

data class Product(val productId : String, val qunitity : Int)
data class Cart(val products : List<Product>, val onItemClicked : () -> Unit)
val cart = Cart(products = listOf(), onItemClicked = {})
//fun test  () : Unit {
//    val product =cart.products.filter { it.productId.equals("") }.get(0)
//
//
//}
data class CartUiState(val products: List<Product>)
       @Composable
        fun Cart( cart: Cart, onItemClicked: () -> Unit){
            LazyColumn {
                items(count = cart.products.size, key = { prodcut -> cart.products[prodcut]}){

                }
            }
        }
        @Composable
        fun CartItem(onItemClicked: () -> Unit){

        }

@Composable
fun Quantity(productId: String, onIncrement : () -> Unit, onDecrerment : () -> Unit) {

}