package com.example.casestudy.api

import com.example.casestudy.model.App
import retrofit2.Response
import retrofit2.http.GET

interface Api {
    @GET("flashlights")
    suspend fun getFlashLights():Response<List<App>>
    @GET("colorlights")
    suspend fun getColorlights():Response<List<App>>
    @GET("sosalerts")
    suspend fun getSosalerts():Response<List<App>>
}
