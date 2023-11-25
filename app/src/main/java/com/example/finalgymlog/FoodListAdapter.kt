package com.example.finalgymlog

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.finalgymlog.data.Exo
import com.example.finalgymlog.data.Food
import com.example.finalgymlog.databinding.ExoItemBinding
import com.example.finalgymlog.databinding.FoodItemBinding

class FoodListAdapter(
    private var foodList: List<Food>,
    private val clickListener: FoodFragment
) : RecyclerView.Adapter<FoodListViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodListViewHolder {
        val from = LayoutInflater.from(parent.context)
        val binding = FoodItemBinding.inflate(from, parent, false)
        return FoodListViewHolder(binding, clickListener)
    }

    override fun onBindViewHolder(holder: FoodListViewHolder, position: Int) {
        holder.bindItem(foodList[position])
    }

    override fun getItemCount(): Int = foodList.size

    fun setData(foods: List<Food>) {
        this.foodList = foods
        notifyDataSetChanged()
    }
}