package com.tops.onlinestorage

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Path

interface ProductApiService {

    @GET("products")
    fun getData() : Call<ProductResponse>

    @DELETE("products/{id}")
    suspend fun deleteProduct(@Path("id") id: Int): Response<Unit>

}