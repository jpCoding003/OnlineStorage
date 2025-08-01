package com.tops.onlinestorage.Service

import com.tops.onlinestorage.model.UserResponse
import retrofit2.Call
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

   @GET("users")
    fun getUsers(): Call<UserResponse>

    @DELETE("users/{id}")
    fun deleteUser(@Path("id") userId : Int) : Call<Void>
}