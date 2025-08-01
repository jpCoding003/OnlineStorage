package com.tops.onlinestorage.viewmodel

import android.content.Context
import android.util.Log
import android.widget.Toast
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

    // ✅ Added: LiveData to track insert success
    private val _insertSuccess = MutableLiveData<Boolean>()
    val insertSuccess: LiveData<Boolean> = _insertSuccess


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

    fun insertUser(context: Context, user : Users){
        Log.d("InsertDebug", "insertUser() called: $user") // ✅ Debug log

        Client.apiService.insertUser(user).enqueue(object : Callback<UserResponse>{
            override fun onResponse(
                call: Call<UserResponse?>,
                response: Response<UserResponse?>
            ) {
                if (response.isSuccessful && response.body() != null) {
                    val createdUser = response.body()
                    Log.d("Insert", "User inserted: $createdUser")
                    Toast.makeText(context, "User created successfully", Toast.LENGTH_SHORT).show()

                    // Update the user list
                    _userList.value = createdUser!!.users

                    // ✅ Notify success
                    _insertSuccess.postValue(true)
                } else {
                    Log.e("Insert", "Insert failed: ${response.message()}")
                    Toast.makeText(context, "Failed to insert user", Toast.LENGTH_SHORT).show()
                    _insertSuccess.postValue(false)
                }
            }
            override fun onFailure(
                call: Call<UserResponse?>,
                t: Throwable
            ) {
                Log.e("Insert", "Error: ${t.message}")
                Toast.makeText(context, "Insert error: ${t.message}", Toast.LENGTH_SHORT).show()
                _insertSuccess.postValue(false)
            }
        })
    }
}