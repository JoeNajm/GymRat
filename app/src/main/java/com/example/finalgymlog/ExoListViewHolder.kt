package com.example.finalgymlog

import android.content.Context
import android.graphics.BitmapFactory
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.finalgymlog.data.Exo
import com.example.finalgymlog.data.ExoInventory
import com.example.finalgymlog.databinding.ExoItemBinding
import java.io.File

class ExoListViewHolder(
    private val exoListItemBinding: ExoItemBinding,
    private val context: Context,
    private val clickListener: ExoListFragment,
) : RecyclerView.ViewHolder(exoListItemBinding.root){

    @RequiresApi(Build.VERSION_CODES.N)
    fun bindItem(exo: Exo, savedExoList: List<ExoInventory>?){
        exoListItemBinding.exoName.text = exo.name
        println(exo.name.lowercase() + " qwerr")
        if (exo.name.lowercase() != "running" && exo.name.lowercase() != "cardio"){
            exoListItemBinding.exoReps.text = "Reps: " + exo.reps
            exoListItemBinding.exoWeights.text = "kg: " + exo.weights

        }
        else{
            exoListItemBinding.exoReps.text = "Distance: " + exo.reps
            exoListItemBinding.exoWeights.text = "Speed: " + exo.weights
        }

        if(savedExoList != null){
            val name = exo.name.lowercase()
            var found = false
            for(e in savedExoList!!){
                println(e.name.lowercase())
                if(name == e.name.lowercase()){
                    found = true
                    if(e.imagepath.contains("drawable", ignoreCase = true)){
                        val PathOfImage = e.imagepath
                        val intId = PathOfImage.substring(11, PathOfImage.length).toInt()
                        exoListItemBinding.exoImage.setImageDrawable(ContextCompat.getDrawable(context, intId))
                    } else{
                        val PathOfImage = e.imagepath
                        val file = File(context.filesDir, PathOfImage)
                        val bitmap = BitmapFactory.decodeFile(file.absolutePath)
                        exoListItemBinding.exoImage.setImageBitmap(bitmap)
                    }
                }
            }
            if(found == false){
                exoListItemBinding.exoImage.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.rat))
            }
        } else{
            exoListItemBinding.exoImage.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.rat))
        }

        exoListItemBinding.exoListView.setOnClickListener {
            clickListener.onClick(exo)
        }

        exoListItemBinding.floatingActionButtonRep.setOnClickListener {
            clickListener.onClickRep(exo)
        }
    }

}