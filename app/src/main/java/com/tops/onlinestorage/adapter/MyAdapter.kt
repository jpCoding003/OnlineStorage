package com.tops.onlinestorage.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tops.onlinestorage.databinding.RowItemProductsBinding
import com.tops.onlinestorage.model.ProductRoot

class MyAdapter(private var productList: List<ProductRoot>) : RecyclerView.Adapter<MyAdapter.ProductViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProductViewHolder {
        val binding = RowItemProductsBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: ProductViewHolder,
        position: Int
    ) {
        val products = productList[position]
        holder.binding.tvProductTitle.text = products.title
        holder.binding.tvDescription.text = products.description
        holder.binding.tvPrice.text = products.price.toString()
        holder.binding.ratingBar.rating = products.rating.toFloat()

    }

    override fun getItemCount(): Int = productList.size

    fun updateData(newList: List<ProductRoot>) {
        productList = newList
        notifyDataSetChanged()
    }

    class ProductViewHolder(val binding: RowItemProductsBinding): RecyclerView.ViewHolder(binding.root)


}