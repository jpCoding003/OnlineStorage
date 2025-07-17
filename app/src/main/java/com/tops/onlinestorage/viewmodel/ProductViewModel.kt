package com.tops.onlinestorage.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tops.onlinestorage.ApiClient
import com.tops.onlinestorage.ProductResponse
import com.tops.onlinestorage.model.ProductRoot
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductViewModel : ViewModel() {

    private  var _productData = MutableLiveData<List<ProductRoot>>()
     var productData: LiveData<List<ProductRoot>> = _productData


    private var apiProducts = MutableLiveData<ProductRoot>()
    private var dataPresent: Boolean= false

    fun loadData(): LiveData<List<ProductRoot>> {
        if (!dataPresent) {
            val call = ApiClient.apiService.getData()
            call.enqueue(object : Callback<ProductResponse> {
                override fun onResponse(
                    call: Call<ProductResponse?>,
                    response: Response<ProductResponse?>
                ) {
                    if (response.isSuccessful) {
//                        val products = response.body()
//                        _productData.value = products as MutableList<ProductRoot>
//                        dataPresent = true

                        response.body()?.let { productresponse ->
                            _productData.value = productresponse.products
                            dataPresent = true
                        }
                    }
                }

                override fun onFailure(
                    call: Call<ProductResponse?>,
                    t: Throwable
                ) {
                    Log.e(" Api Error", t.message.toString())
                }
            })
        }
        return _productData
    }
}