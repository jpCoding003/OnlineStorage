package com.tops.onlinestorage.Service

import com.tops.onlinestorage.model.UserResponse
import com.tops.onlinestorage.model.Users
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {

   @GET("users")
    fun getUsers(): Call<UserResponse>
}