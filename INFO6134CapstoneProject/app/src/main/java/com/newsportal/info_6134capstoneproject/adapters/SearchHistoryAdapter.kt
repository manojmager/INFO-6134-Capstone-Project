package com.newsportal.info_6134capstoneproject.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.newsportal.info_6134capstoneproject.R

class SearchHistoryAdapter(private var searchHistory: List<String>) :
    RecyclerView.Adapter<SearchHistoryAdapter.SearchHistoryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchHistoryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_search_history, parent, false)
        return SearchHistoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: SearchHistoryViewHolder, position: Int) {
        val historyItem = searchHistory[position]

        holder.historyTextView.text = historyItem
    }

    override fun getItemCount(): Int {
        return searchHistory.size
    }

    inner class SearchHistoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val historyTextView: TextView = itemView.findViewById(R.id.tvSearchHistory)

    }

    fun updateData(newData: List<String>) {
        searchHistory = newData // Create a copy of the list
        notifyDataSetChanged()
    }
}
