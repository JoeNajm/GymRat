package com.example.finalgymlog

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.finalgymlog.data.Food
import com.example.finalgymlog.data.FridgeFood
import com.example.finalgymlog.databinding.FoodItemBinding

class FridgeFoodListViewHolder(
    private val foodListItemBinding: FoodItemBinding,
    private val clickListener: FridgeFragment,
) : RecyclerView.ViewHolder(foodListItemBinding.root){

    @RequiresApi(Build.VERSION_CODES.N)
    fun bindItem(fridgefood: FridgeFood){
        foodListItemBinding.foodName.text = fridgefood.name
        foodListItemBinding.foodEnergy.text = "Energy: " + fridgefood.energy.toString() + " kcal"
        foodListItemBinding.foodProteins.text = "Proteins: " + fridgefood.protein.toString() + " g"
        if(fridgefood.type != ""){
        foodListItemBinding.foodType.text ="Type: " + fridgefood.type
        }
        else{
        foodListItemBinding.foodType.text ="Type: N/A"
        }

    }

}