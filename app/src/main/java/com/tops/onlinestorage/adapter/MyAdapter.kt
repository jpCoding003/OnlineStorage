package com.tops.onlinestorage.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tops.onlinestorage.databinding.ItemRowUserBinding
import com.tops.onlinestorage.model.Users

class MyAdapter(private var userList : MutableList<Users>): RecyclerView.Adapter<MyAdapter.UserViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): UserViewHolder {
        val binding = ItemRowUserBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return UserViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: UserViewHolder,
        position: Int
    ) {
        val list = userList[position]
        holder.binding.tvname.setText("${list?.firestName} ${list.lastName}")
        holder.binding.tvemail.setText(list.email)


    }

    override fun getItemCount(): Int = userList.size

    fun submitList(list: List<Users>){
        userList = list.toMutableList()
        notifyDataSetChanged()
    }

    class UserViewHolder(val binding: ItemRowUserBinding): RecyclerView.ViewHolder(binding.root)
}