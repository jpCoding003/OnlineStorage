package com.tops.onlinestorage

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import com.tops.onlinestorage.adapter.MyAdapter
import com.tops.onlinestorage.databinding.ActivityMainBinding
import com.tops.onlinestorage.viewmodel.UserViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val userviewmodel : UserViewModel by viewModels()
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

        userviewmodel.getAllData(this)

        adapter = MyAdapter(mutableListOf())
        binding.rvUserList.layoutManager = LinearLayoutManager(this)
        binding.rvUserList.adapter = adapter

        userviewmodel.userList.observe(this , Observer{
            list-> adapter.submitList(list)
        })

    }
}