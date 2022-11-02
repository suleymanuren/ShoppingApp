package com.suleymanuren.shoppingapp.ui.product.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.suleymanuren.shoppingapp.data.model.ProductListItem
import com.suleymanuren.shoppingapp.databinding.ItemProductLayoutBinding

class HomeProductAdapter(private val listener: OnProductClickListener) :
    ListAdapter<ProductListItem, HomeProductAdapter.ProductViewHolder>(ProductDiffUtil()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        return ProductViewHolder(
            ItemProductLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(getItem(position), listener)
    }

    class ProductViewHolder(private val binding: ItemProductLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: ProductListItem, listener: OnProductClickListener) {
            binding.dataHolder = data
            binding.listener = listener
            binding.executePendingBindings()
            binding.cardView.setOnClickListener {
                listener.onProductClick(data,it)
            }
        }
    }

    class ProductDiffUtil : DiffUtil.ItemCallback<ProductListItem>() {
        override fun areItemsTheSame(oldItem: ProductListItem, newItem: ProductListItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ProductListItem, newItem: ProductListItem): Boolean {
            return oldItem == newItem
        }
    }
}

interface OnProductClickListener {
    fun onProductClick(data: ProductListItem, view : View)
}
