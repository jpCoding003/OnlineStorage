package com.tops.onlinestorage

import com.tops.onlinestorage.ClientProductApi.retrofit
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ClientProductApi {

    val retrofit : Retrofit by lazy {
        Retrofit.Builder().baseUrl("https://dummyjson.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}

object  ApiClient{
     val apiService : ProductApiService by lazy {
         ClientProductApi.retrofit.create(ProductApiService::class.java)
     }
}