package com.tops.onlinestorage.Service

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ClientApi {

    val retrofit: Retrofit by lazy{
        Retrofit.Builder().baseUrl("https://dummyjson.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}

object Client{
    val apiService : ApiService by lazy {
        ClientApi.retrofit.create(ApiService::class.java)
    }
}