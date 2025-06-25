package com.example.brs_cout.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.brs_cout.databinding.ItemSearchResultBinding
import com.example.brs_cout.models.SearchResultItem

class SearchResultAdapter : ListAdapter<SearchResultItem, SearchResultAdapter.SearchResultViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchResultViewHolder {
        val binding = ItemSearchResultBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return SearchResultViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: SearchResultViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    class SearchResultViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val binding = ItemSearchResultBinding.bind(itemView)
        fun bind(item: SearchResultItem)  = with(binding){
            textViewName.text = item.name
            textViewTitle.text = item.title
            textViewCompanyLocation.text = "${item.company} - ${item.location}"
        }
    }

    private class DiffCallback : DiffUtil.ItemCallback<SearchResultItem>() {
        override fun areItemsTheSame(oldItem: SearchResultItem, newItem: SearchResultItem): Boolean {
            return oldItem.name == newItem.name // Or a unique ID if you have one
        }

        override fun areContentsTheSame(oldItem: SearchResultItem, newItem: SearchResultItem): Boolean {
            return oldItem == newItem
        }
    }
}