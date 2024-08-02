package com.example.finalgymlog

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.finalgymlog.data.Exo
import com.example.finalgymlog.data.Session
import com.example.finalgymlog.data.SessionViewModel
import com.example.finalgymlog.databinding.ExoHistoryItemBinding

class ExoHistoryListAdapter(
    private var exoHistoryList: List<Exo>,
    private val clickListener: ExoHistoryFragment,
    private val sessionList: List<Session>
) : RecyclerView.Adapter<ExoHistoryListViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExoHistoryListViewHolder {
        val from = LayoutInflater.from(parent.context)
        val binding = ExoHistoryItemBinding.inflate(from, parent, false)
        return ExoHistoryListViewHolder(binding, clickListener, sessionList)
    }

    override fun onBindViewHolder(holder: ExoHistoryListViewHolder, position: Int) {
        holder.bindItem(exoHistoryList[position])
    }

    override fun getItemCount(): Int = exoHistoryList.size
}