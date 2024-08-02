package com.example.finalgymlog

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.finalgymlog.data.Food
import com.example.finalgymlog.databinding.FoodItemBinding

class FoodListViewHolder(
    private val foodListItemBinding: FoodItemBinding,
    private val clickListener: FoodFragment,
) : RecyclerView.ViewHolder(foodListItemBinding.root){

    @RequiresApi(Build.VERSION_CODES.N)
    fun bindItem(food: Food){
        foodListItemBinding.foodName.text = food.name
        foodListItemBinding.foodEnergy.text =  "Energy: " + food.energy.toString() + " kcal"
        foodListItemBinding.foodProteins.text = "Protein: " +  food.protein.toString() + " g"
        foodListItemBinding.foodType.text ="Type: " + food.type
    }
}