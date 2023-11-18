package com.example.finalgymlog

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.finalgymlog.data.Exo
import com.example.finalgymlog.data.Session
import com.example.finalgymlog.databinding.ExoItemBinding
import com.example.finalgymlog.databinding.SessionItemBinding

class ExoListAdapter(
    private var exoList: List<Exo>,
//    private val context: Context,
    private val clickListener: ExoListFragment
) : RecyclerView.Adapter<ExoListViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExoListViewHolder {
        val from = LayoutInflater.from(parent.context)
        val binding = ExoItemBinding.inflate(from, parent, false)
        return ExoListViewHolder(binding, parent.context, clickListener)
    }

    override fun onBindViewHolder(holder: ExoListViewHolder, position: Int) {
        holder.bindItem(exoList[position])
    }

    override fun getItemCount(): Int = exoList.size

    fun setData(exos: List<Exo>) {
        this.exoList = exos
        notifyDataSetChanged()
    }
}