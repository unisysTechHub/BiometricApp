package com.example.biometricsample.reflection

import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine
import kotlin.reflect.KClass
import kotlin.reflect.KFunction
import kotlin.reflect.KTypeParameter

data class Reflection(val name: String)

val kClass = Reflection::class

fun inspect(kClass: KClass<Reflection>) {
    val kFunctionList = kClass.constructors
    val  kProperties = kClass.members
    val list = listOf("test")
suspend fun test ()= suspendCoroutine{continuation ->

        continuation.resume("")
}
}