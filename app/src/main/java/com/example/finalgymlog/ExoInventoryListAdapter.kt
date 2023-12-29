package com.example.finalgymlog

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.finalgymlog.data.ExoInventory
import com.example.finalgymlog.databinding.ExoinventoryItemBinding

class ExoInventoryListAdapter(
    private var exoList: List<ExoInventory>,
    private val exoInventoryClickListener: ExoInventoryFragment?,
    private val addExoClickListener: AddExoFragment?
) : RecyclerView.Adapter<ExoInventoryListViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExoInventoryListViewHolder {
        val from = LayoutInflater.from(parent.context)
        val binding = ExoinventoryItemBinding.inflate(from, parent, false)
        return ExoInventoryListViewHolder(binding, parent.context, exoInventoryClickListener, addExoClickListener)
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