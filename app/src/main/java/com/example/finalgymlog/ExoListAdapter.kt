package com.example.finalgymlog

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.finalgymlog.data.Exo
import com.example.finalgymlog.data.ExoInventory
import com.example.finalgymlog.databinding.ExoItemBinding

class ExoListAdapter(
    private var exoList: List<Exo>,
    private val clickListener: ExoListFragment,
    private var savedExoList: List<ExoInventory>?,
) : RecyclerView.Adapter<ExoListViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExoListViewHolder {
        val from = LayoutInflater.from(parent.context)
        val binding = ExoItemBinding.inflate(from, parent, false)
        return ExoListViewHolder(binding, parent.context, clickListener)
    }

    override fun onBindViewHolder(holder: ExoListViewHolder, position: Int) {
        holder.bindItem(exoList[position], savedExoList)
    }

    override fun getItemCount(): Int = exoList.size

    fun setData(exos: List<Exo>) {
        this.exoList = exos
        notifyDataSetChanged()
    }
}