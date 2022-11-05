package com.suleymanuren.shoppingapp.ui.productDetail.adapter

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.suleymanuren.shoppingapp.data.model.BasketProduct
import com.suleymanuren.shoppingapp.data.model.ProductListItem
import com.suleymanuren.shoppingapp.databinding.ItemDetailLayoutBinding
import com.suleymanuren.shoppingapp.ui.productDetail.ProductDetailFragment
import com.suleymanuren.shoppingapp.util.invisible
import com.suleymanuren.shoppingapp.util.visible


class HomeProductDetailAdapter(private val listener: ProductDetailFragment) :
    ListAdapter<BasketProduct, HomeProductDetailAdapter.ProductDetailViewHolder>(
        ProductDetailDiffUtil()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductDetailViewHolder {
        return ProductDetailViewHolder(
            ItemDetailLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ProductDetailViewHolder, position: Int) {
        holder.bind(getItem(position), listener)
    }

    class ProductDetailViewHolder(private val binding: ItemDetailLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: BasketProduct, listener: OnProductDetailClickListener) {


            binding.dataHolder = data
            binding.listener = listener
            binding.executePendingBindings()
            readMoreLess()
            productCount()
            binding.productAddToCart.setOnClickListener {
                val productCount = binding.quantity.text.toString().toInt()
                val totalPrice = productCount.toDouble()*data.price
                listener.productAddBasketClick(data, count = productCount, totalPrice = totalPrice)
            }
        }
        private fun productCount(){
            binding.increase.setOnClickListener {
                if(binding.quantity.text.toString().toInt() <= 9)
                {
                    binding.quantity.text = (binding.quantity.text.toString().toInt() + 1).toString()
                    binding.increase.visible()

                }
                else {
                    Toast.makeText(binding.root.context,"You can't add more than 10",Toast.LENGTH_SHORT).show()
                }
            }
            binding.decrease.setOnClickListener {
                if (binding.quantity.text.toString().toInt() >= 2) {
                    binding.quantity.text = (binding.quantity.text.toString().toInt() - 1).toString()
                    binding.increase.visible()
                    val count = binding.quantity.text.toString().toInt()
                    Bundle().apply{
                        putInt("productCount",count)
                    }
                } else {
                    Toast.makeText(binding.root.context, "You can't add less than 1", Toast.LENGTH_SHORT).show()
                }
            }
        }


        private fun readMoreLess() {
        if (binding.productDescription.text.toString().length > 100) {
           binding.readMore.visible()
           binding.productDescription.maxLines = 2
           binding.readMore.setOnClickListener {
               if (binding.readMore.text.toString() == "Read More") {
                   binding.productDescription.maxLines = 100
                   binding.readMore.text = "Read Less"
               } else {
                   binding.productDescription.maxLines = 2
                   binding.readMore.text = "Read More"
               }
            }
        }
        else {
            binding.readMore.invisible()
         }
            }
        }
    }


    class ProductDetailDiffUtil : DiffUtil.ItemCallback<BasketProduct>() {
        override fun areItemsTheSame(oldItem: BasketProduct, newItem: BasketProduct): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: BasketProduct, newItem: BasketProduct): Boolean {
            return oldItem == newItem
        }


    }

    interface OnProductDetailClickListener {
            fun productAddBasketClick(data: BasketProduct,count:Int,totalPrice: Double)
    }
