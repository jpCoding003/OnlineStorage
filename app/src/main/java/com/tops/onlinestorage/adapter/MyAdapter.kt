package com.tops.onlinestorage.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tops.onlinestorage.databinding.RowItemProductsBinding
import com.tops.onlinestorage.model.ProductRoot

class MyAdapter(private var productList: MutableList<ProductRoot>, private val onDeleteClick: (ProductRoot)-> Unit) : RecyclerView.Adapter<MyAdapter.ProductViewHolder>() {

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
        var products = productList[position]
        holder.binding.tvProductTitle.text = products.title
        holder.binding.tvDescription.text = products.description
        holder.binding.tvPrice.text = products.price.toString()
        holder.binding.ratingBar.rating = products.rating.toFloat()

        holder.binding.cardView.setOnClickListener {
            onDeleteClick(products)
        }

    }

    override fun getItemCount(): Int = productList.size

    fun updateData(newList: MutableList<ProductRoot>) {
        productList = newList
        notifyDataSetChanged()
    }

    fun removeItem(productId: Int) {
        val position = productList.indexOfFirst { it.id == productId }
        if (position != -1) {
            productList.removeAt(position)
            notifyItemRemoved(position)
        }
    }

    class ProductViewHolder(val binding: RowItemProductsBinding): RecyclerView.ViewHolder(binding.root)

}