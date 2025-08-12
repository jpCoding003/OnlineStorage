package com.tops.onlinestorage.Service

import com.tops.onlinestorage.model.UserResponse
import com.tops.onlinestorage.model.Users
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiService {

   @GET("users")
    fun getUsers(): Call<UserResponse>

    @DELETE("users/{id}")
    fun deleteUser(@Path("id") userId : Int?) : Call<Void>

    @POST("users/add")
    fun insertUser(@Body newuser: Users) : Call<UserResponse>
}