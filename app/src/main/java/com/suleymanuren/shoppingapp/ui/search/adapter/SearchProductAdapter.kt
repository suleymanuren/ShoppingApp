package com.suleymanuren.shoppingapp.ui.search.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.suleymanuren.shoppingapp.data.model.ProductListItem
import com.suleymanuren.shoppingapp.databinding.ItemProductLayoutBinding
import com.suleymanuren.shoppingapp.databinding.ItemProductSearchBinding
import com.suleymanuren.shoppingapp.ui.product.adapter.HomeProductAdapter
import com.suleymanuren.shoppingapp.ui.product.adapter.OnProductClickListener

class SearchProductAdapter(private val listener: OnSearchProductClickListener) :
    ListAdapter<ProductListItem, SearchProductAdapter.SearchProductViewHolder>(SearchProductDiffUtil()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchProductViewHolder {
        return SearchProductViewHolder(
            ItemProductSearchBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: SearchProductViewHolder, position: Int) {
        holder.bind(getItem(position), listener)
    }

    class SearchProductViewHolder(private val binding: ItemProductSearchBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: ProductListItem, listener: OnSearchProductClickListener) {
            binding.dataHolder = data
            binding.listener = listener
            binding.executePendingBindings()
            binding.cardView.setOnClickListener {
                listener.onProductClick(data,it)
            }
        }
    }

    class SearchProductDiffUtil : DiffUtil.ItemCallback<ProductListItem>() {
        override fun areItemsTheSame(oldItem: ProductListItem, newItem: ProductListItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ProductListItem, newItem: ProductListItem): Boolean {
            return oldItem == newItem
        }
    }
}

interface OnSearchProductClickListener {
    fun onProductClick(data: ProductListItem, view : View)
}
