package com.example.finalgymlog

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.finalgymlog.data.Exo
import com.example.finalgymlog.data.ExoInventory
import com.example.finalgymlog.data.Session
import com.example.finalgymlog.databinding.ExoItemBinding
import com.example.finalgymlog.databinding.ExoinventoryItemBinding
import com.example.finalgymlog.databinding.SessionItemBinding

class ExoInventoryListAdapter(
    private var exoList: List<ExoInventory>,
    private val clickListener: ExoInventoryFragment
) : RecyclerView.Adapter<ExoInventoryListViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExoInventoryListViewHolder {
        val from = LayoutInflater.from(parent.context)
        val binding = ExoinventoryItemBinding.inflate(from, parent, false)
        return ExoInventoryListViewHolder(binding, parent.context, clickListener)
    }

    override fun onBindViewHolder(holder: ExoInventoryListViewHolder, position: Int) {
        holder.bindItem(exoList[position])
    }

    override fun getItemCount(): Int = exoList.size

    fun setData(exos: List<ExoInventory>) {
        this.exoList = exos
        notifyDataSetChanged()
    }
}