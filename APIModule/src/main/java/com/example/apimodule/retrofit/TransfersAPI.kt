package com.example.biometricsample.retrofit

import android.util.Log
import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.HeaderMap
import retrofit2.http.POST

interface TransfersAPI {

//    @POST("EBuyBooksList")
//    suspend  fun listOfBooks(@HeaderMap headers: Map<String,String>, @Body body: RequestBody) : BooksListApiResponse

    data class RequestBody(val serachString: String, val maxItems:Int)   {

    }


    companion object {
        private const val BASE_URL = "https://w41v53pmwj.execute-api.us-east-1.amazonaws.com/default/"
        fun create(): TransfersAPI {
            val logger = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger { Log.d("API", it) })
            logger.level = HttpLoggingInterceptor.Level.BASIC

            val client = OkHttpClient.Builder()
                .addInterceptor(logger)
                .build()
            return Retrofit.Builder()
                .baseUrl(BASE_URL.toHttpUrlOrNull()!!)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(TransfersAPI::class.java)
        }
    }
}