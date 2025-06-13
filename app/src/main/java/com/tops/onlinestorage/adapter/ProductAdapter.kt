package com.tops.onlinestorage.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tops.onlinestorage.databinding.ProductRowItemBinding

class ProductAdapter(private val products: String): RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProductViewHolder {
        val binding = ProductRowItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: ProductViewHolder,
        position: Int
    ) {
        val product = products[position]
        holder.binding.text1.setText("ID = ${product.id}")
        holder.binding.text2.setText("Name = ${product.name}")
    }

    override fun getItemCount(): Int = products.length

    class ProductViewHolder(val binding: ProductRowItemBinding): RecyclerView.ViewHolder(binding.root)
}