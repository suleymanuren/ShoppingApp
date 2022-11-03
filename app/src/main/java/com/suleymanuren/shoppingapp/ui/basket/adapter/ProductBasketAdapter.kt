package com.suleymanuren.shoppingapp.ui.basket.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.suleymanuren.shoppingapp.data.model.BasketProduct
import com.suleymanuren.shoppingapp.data.model.ProductListItem
import com.suleymanuren.shoppingapp.databinding.ItemDetailLayoutBinding
import com.suleymanuren.shoppingapp.databinding.ItemProductBasketBinding
import com.suleymanuren.shoppingapp.ui.basket.ProductBasketFragment
import com.suleymanuren.shoppingapp.ui.productDetail.ProductDetailFragment
import com.suleymanuren.shoppingapp.util.invisible
import com.suleymanuren.shoppingapp.util.visible


class ProductBasketAdapter(private val listener : ProductBasketFragment) :
        ListAdapter<BasketProduct, ProductBasketAdapter.ProductBasketViewHolder>(
        ProductBasketDiffUtil()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductBasketViewHolder {
        return ProductBasketViewHolder(
            ItemProductBasketBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ProductBasketViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ProductBasketViewHolder(private val binding: ItemProductBasketBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: BasketProduct) {


            binding.dataHolder = data
            binding.executePendingBindings()
            binding.productDescription

    }
}

class ProductBasketDiffUtil : DiffUtil.ItemCallback<BasketProduct>() {
    override fun areItemsTheSame(oldItem: BasketProduct, newItem: BasketProduct): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: BasketProduct, newItem: BasketProduct): Boolean {
        return oldItem == newItem
    }
}

interface OnBasketListener {
    fun productAddBasketClick(data: ProductListItem)
    fun productRemoveBasketClick(data: ProductListItem)
} }

