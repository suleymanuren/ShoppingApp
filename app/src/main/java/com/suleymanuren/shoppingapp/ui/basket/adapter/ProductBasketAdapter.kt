package com.suleymanuren.shoppingapp.ui.basket.adapter


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.suleymanuren.shoppingapp.data.model.BasketProduct
import com.suleymanuren.shoppingapp.data.model.ProductListItem
import com.suleymanuren.shoppingapp.databinding.ItemProductBasketBinding
import com.suleymanuren.shoppingapp.ui.basket.ProductBasketFragment
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
            productCount()
            binding.quantity.text = data.count.toString().toDouble().toInt().toString()
    }
        // PRODUCT COUNT AT BASKET
        private fun productCount(){
            binding.increase.setOnClickListener {
                if(binding.quantity.text.toString().toDouble().toInt() <= 9)
                {
                    binding.quantity.text = (binding.quantity.text.toString().toDouble().toInt() + 1).toString()
                    productTotalPriceCalculate()
                    binding.increase.visible()
                }
                else {
                    Toast.makeText(binding.root.context,"You can't add more than 10",Toast.LENGTH_SHORT).show()
                }
            }
            binding.decrease.setOnClickListener {
                if (binding.quantity.text.toString().toDouble().toInt() >= 2) {
                    binding.quantity.text = (binding.quantity.text.toString().toDouble().toInt() - 1).toString()
                    productTotalPriceCalculate()
                } else {
                    Toast.makeText(binding.root.context, "You can't add less than 1", Toast.LENGTH_SHORT).show()
                }
            }
        }

        // PRODUCT TOTAL PRICE CALCULATE
        private fun productTotalPriceCalculate(){
            val productPrice = binding.dataHolder?.price
            val productCount = binding.quantity.text.toString().toDouble()
            val getTotalProductPrice = productPrice?.times(productCount)
            binding.productPrice.text = "$"+getTotalProductPrice.toString()
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
    fun basketTotalPrice(data: ProductListItem,totalPrice: Double)
    fun productRemoveBasketClick(data: ProductListItem)
} }

