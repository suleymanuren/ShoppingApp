package com.suleymanuren.shoppingapp.ui.productDetail.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.suleymanuren.shoppingapp.data.model.ProductListItem
import com.suleymanuren.shoppingapp.databinding.ItemDetailLayoutBinding
import com.suleymanuren.shoppingapp.util.invisible
import com.suleymanuren.shoppingapp.util.visible


class HomeProductDetailAdapter(private val listener: OnProductDetailClickListener) :
    ListAdapter<ProductListItem, HomeProductDetailAdapter.ProductDetailViewHolder>(ProductDetailDiffUtil()) {
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
        fun bind(data: ProductListItem, listener: OnProductDetailClickListener) {
            binding.dataHolder = data
            binding.listener = listener
            binding.executePendingBindings()
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
                } else {
                    Toast.makeText(binding.root.context, "You can't add less than 1", Toast.LENGTH_SHORT).show()
                }

                }
                }

            }
        }


    class ProductDetailDiffUtil : DiffUtil.ItemCallback<ProductListItem>() {
        override fun areItemsTheSame(oldItem: ProductListItem, newItem: ProductListItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ProductListItem, newItem: ProductListItem): Boolean {
            return oldItem == newItem
        }
    }

interface OnProductDetailClickListener {

}
