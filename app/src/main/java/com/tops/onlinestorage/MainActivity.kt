package com.tops.onlinestorage

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.tops.onlinestorage.adapter.ProductAdapter
import com.tops.onlinestorage.databinding.ActivityMainBinding
import com.tops.onlinestorage.model.Product
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.Request
import org.json.JSONArray

private const val TAG = "MainActivity"
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        lifecycleScope.launch {
            try {
                val result = getdata()

                binding.productRecyclerView.layoutManager = LinearLayoutManager(applicationContext)
                binding.productRecyclerView.adapter = ProductAdapter(result!!)
            }catch (e: Exception){
                Log.e("API_CALL", "Error: ${e.message}")
            }
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