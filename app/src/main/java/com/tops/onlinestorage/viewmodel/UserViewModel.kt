package com.tops.onlinestorage.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tops.onlinestorage.Service.Client
import com.tops.onlinestorage.model.UserResponse
import com.tops.onlinestorage.model.Users
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserViewModel : ViewModel() {

    private  val _userList = MutableLiveData<List<Users>>()
    val userList: LiveData<List<Users>> = _userList


    fun getAllData(context: Context){

        val call = Client.apiService.getUsers()

        call.enqueue(object : Callback<UserResponse>{
            override fun onResponse(
                call: Call<UserResponse?>,
                response: Response<UserResponse?>
            ) {
                if (response.isSuccessful){
                    val userdata = response.body()
                    _userList.value = userdata!!.users
                }
            }

            override fun onFailure(
                call: Call<UserResponse?>,
                t: Throwable
            ) {
                Log.i("UserviewModel"," Error====== ${t.message}")
            }

        })

    }
}