package com.example.casestudy.api

import com.example.casestudy.model.App
import com.example.casestudy.model.Category
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import retrofit2.http.GET




object ApiUtils {
    private val AUTHORIZATION = "JsonStub-User-Key"
    private val API_KEY = "f5e0861a-b53d-4b80-9c28-2233780c3d5d"


    fun getOkHttpClient(): OkHttpClient {

        val client = OkHttpClient.Builder()
            .addInterceptor(Interceptor {
                val requestBuilder: Request.Builder = it.request().newBuilder()
                requestBuilder.header(AUTHORIZATION, API_KEY)
                return@Interceptor it.proceed(requestBuilder.build())
            })
            .build()

return client
    }

    fun getApi(category: Category): Api {
        val gson = GsonBuilder().setLenient().create()
       val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
        val retrofit = Retrofit.Builder()
            .baseUrl(category.baseUrl)
            .client(getOkHttpClient())
     .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(Api::class.java)


        return retrofit
    }
}
// .addConverterFactory(MoshiConverterFactory.create(moshi)) todo buna bak neden sorun verdi karşılaştır.
