package com.tops.onlinestorage

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.tops.onlinestorage.adapter.MyAdapter
import com.tops.onlinestorage.databinding.ActivityMainBinding
import com.tops.onlinestorage.model.ProductRoot
import com.tops.onlinestorage.viewmodel.ProductViewModel
import kotlinx.coroutines.launch

// API :  https://dummyjson.com/products

private const val TAG = "MainActivity"
class MainActivity : AppCompatActivity() {

    private  lateinit var binding: ActivityMainBinding
    private  val productviewmodel: ProductViewModel by viewModels()
    private lateinit var adapter: MyAdapter
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

        adapter = MyAdapter(mutableListOf()){ product->
            deleteProduct(product)
        }
        binding.rvProductList.layoutManager = LinearLayoutManager(this)
        binding.rvProductList.adapter = adapter

        productviewmodel.loadData()

        productviewmodel.productData.observe(this, Observer{
            products-> adapter.updateData(products as MutableList<ProductRoot>)
        })

    }

    private fun deleteProduct(product: ProductRoot) {
        lifecycleScope.launch {
            try {
                val response = ApiClient.apiService.deleteProduct(product.id)
                if (response.isSuccessful) {
                    adapter.removeItem(product.id)

                } else {

                }
            } catch (e: Exception) {

            }
        }
    }
}