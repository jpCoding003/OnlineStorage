package com.tops.onlinestorage.model

data class Users(
    val id: Int,
    val firstName: String,
    val lastName: String,
    val phone: String,
    val gender: String,
    val email: String
)

data class UserResponse(
    val users: List<Users>
)
