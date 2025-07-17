package com.tops.onlinestorage

import com.tops.onlinestorage.model.ProductRoot
import retrofit2.Call
import retrofit2.http.GET

interface ProductApiService {

    @GET("/products")
    fun getData() : Call<ProductResponse>
}