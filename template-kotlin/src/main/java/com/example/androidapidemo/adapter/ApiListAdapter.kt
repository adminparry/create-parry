package com.example.androidapidemo.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.androidapidemo.databinding.ItemApiBinding
import com.example.androidapidemo.model.ApiItem

class ApiListAdapter(private val onItemClick: (ApiItem) -> Unit) :
    ListAdapter<ApiItem, ApiListAdapter.ApiViewHolder>(ApiDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ApiViewHolder {
        val binding = ItemApiBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ApiViewHolder(binding, onItemClick)
    }

    override fun onBindViewHolder(holder: ApiViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ApiViewHolder(
        private val binding: ItemApiBinding,
        private val onItemClick: (ApiItem) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ApiItem) {
            binding.apply {
                tvApiName.text = item.name
                tvApiCategory.text = item.category
                tvMinSdk.text = "Min SDK: ${item.minSdkVersion}"
                root.setOnClickListener { onItemClick(item) }
            }
        }
    }

    private class ApiDiffCallback : DiffUtil.ItemCallback<ApiItem>() {
        override fun areItemsTheSame(oldItem: ApiItem, newItem: ApiItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ApiItem, newItem: ApiItem): Boolean {
            return oldItem == newItem
        }
    }
} 