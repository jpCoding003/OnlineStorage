package com.tops.onlinestorage.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tops.onlinestorage.Service.Client
import com.tops.onlinestorage.Service.ClientApi
import com.tops.onlinestorage.databinding.ItemRowUserBinding
import com.tops.onlinestorage.model.Users
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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
        holder.binding.tvname.text = list.firstName
        holder.binding.tvemail.setText(list.email)

        holder.binding.tvcardview.setOnClickListener {
         var call = Client.apiService.deleteUser(list.id)
            call.enqueue(object: Callback<Void>{
                override fun onResponse(
                    call: Call<Void?>,
                    response: Response<Void?>
                ) {
                    if (response.isSuccessful){
                        userList.removeAt(holder.adapterPosition)
                        notifyItemRemoved(holder.adapterPosition)
                    }
                }

                override fun onFailure(call: Call<Void?>, t: Throwable) {
                    Log.i(" Error", " Adapter ${t.message}")
                }

            })
        }

    }

    override fun getItemCount(): Int = userList.size

    fun submitList(list: List<Users>){
        userList = list.toMutableList()
        notifyDataSetChanged()
    }

    class UserViewHolder(val binding: ItemRowUserBinding): RecyclerView.ViewHolder(binding.root)
}