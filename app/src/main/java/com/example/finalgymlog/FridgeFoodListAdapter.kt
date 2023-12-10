package com.example.finalgymlog

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.finalgymlog.data.Exo
import com.example.finalgymlog.data.Food
import com.example.finalgymlog.data.FridgeFood
import com.example.finalgymlog.databinding.ExoItemBinding
import com.example.finalgymlog.databinding.FoodItemBinding

class FridgeFoodListAdapter(
    private var fridgefoodList: List<FridgeFood>,
    private val clickListenerFridge: FridgeFragment?,
    private val clickListenerAddFood: AddFoodFragment?
) : RecyclerView.Adapter<FridgeFoodListViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FridgeFoodListViewHolder {
        val from = LayoutInflater.from(parent.context)
        val binding = FoodItemBinding.inflate(from, parent, false)
        return FridgeFoodListViewHolder(binding, clickListenerFridge, clickListenerAddFood)
    }

    override fun onBindViewHolder(holder: FridgeFoodListViewHolder, position: Int) {
        holder.bindItem(fridgefoodList[position])
    }

    override fun getItemCount(): Int = fridgefoodList.size

    fun setData(fridgefoods: List<FridgeFood>) {
        this.fridgefoodList = fridgefoods
        notifyDataSetChanged()
    }
}