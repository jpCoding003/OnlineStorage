package com.tops.onlinestorage

import android.content.Intent
import android.opengl.Visibility
import android.os.Bundle
import android.util.Log
import android.view.View
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

        userviewmodel.isLoading.observe(this, Observer{
            isLoading->
            binding.viewProgressbar.visibility =
                if (isLoading) View.VISIBLE else View.GONE
        })

        userviewmodel.getAllData(this)

        adapter = MyAdapter(mutableListOf()){ user ->
            userviewmodel.deleteUser(user.id!!)
        }
        binding.rvUserList.layoutManager = LinearLayoutManager(this)

        binding.rvUserList.adapter = adapter

        userviewmodel.userList.observe(this , Observer{
            list-> adapter.submitList(list)
        })

        binding.btnAddUser.setOnClickListener {
            val intent = Intent(this, AddUserActivity::class.java)
            startActivity(intent)
        }
    }
}