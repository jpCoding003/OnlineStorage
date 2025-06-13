package com.tops.onlinestorage

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.tops.onlinestorage.model.Product
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.Request
import org.json.JSONArray
import java.util.ArrayList

private const val TAG = "MainActivity"
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

    }

    private suspend fun getdata(): String? = withContext(Dispatchers.IO){

        // Created Client for API OkHttp network operation
        // Perform network operation here
        val client = okhttp3.OkHttpClient()

        // A reguest Builder created to requst data(To Get Data From API) from the url OR API
        var request: Request = Request.Builder().url("https://api.restful-api.dev/objects").build()

        val response = client.newCall(request).execute()
        if (response.isSuccessful){
            // we use !! to tell compiler that the varaiable is
            // non-nullable type, even if it's been declared as nullable
            val jsonResponse = response.body!!.toString()

            jsonResponse.let {
                val jsonArray = JSONArray(it)
                // Create list to store data in array
                val productlist = arrayListOf<Product>()
                for (i in 0 until jsonArray.length()){
                    val jsonObject = jsonArray.getJSONObject(i)
                    val objectID = jsonObject.getInt("id")
                    val objectName = jsonObject.getString("name")

                    //Product is objectData in the respone.body
                    // Here "name" & "id" are Objects of data present in response.body  OR can say The data in API
                    val product = Product(objectID, objectName)
                    productlist.add(product)
                }
                return@withContext jsonResponse
            }
        }else{
            null
        }
    }
}