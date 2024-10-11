package com.example.biometricsample.reflection

import kotlin.reflect.KClass
import kotlin.reflect.KFunction
import kotlin.reflect.KTypeParameter

data class Reflection(val name: String)

val kClass = Reflection::class

fun inspect(kClass: KClass<Reflection>) {
    val kFunctionList = kClass.constructors
    val list = listOf("test")

}