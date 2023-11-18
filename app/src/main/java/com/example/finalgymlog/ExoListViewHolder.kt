package com.example.finalgymlog

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.finalgymlog.data.Exo
import com.example.finalgymlog.data.Session
import com.example.finalgymlog.databinding.ExoItemBinding
import com.example.finalgymlog.databinding.SessionItemBinding

class ExoListViewHolder(
    private val exoListItemBinding: ExoItemBinding,
    private val context: Context,
    private val clickListener: ExoListFragment,
) : RecyclerView.ViewHolder(exoListItemBinding.root){

    @RequiresApi(Build.VERSION_CODES.N)
    fun bindItem(exo: Exo){
        exoListItemBinding.exoName.text = exo.name
        exoListItemBinding.exoReps.text = exo.reps
        exoListItemBinding.exoWeights.text = exo.weights

        val drawable_img: Drawable?

        if("abs" in exo.name.lowercase()){
            drawable_img = ContextCompat.getDrawable(context, R.drawable.abs)
        }
        else if("bench" in exo.name.lowercase()){
            drawable_img = ContextCompat.getDrawable(context, R.drawable.bench)
        }
        else if("calves" in exo.name.lowercase()){
            drawable_img = ContextCompat.getDrawable(context, R.drawable.calves)
        }
        else if("pull up" in exo.name.lowercase()){
            drawable_img = ContextCompat.getDrawable(context, R.drawable.pullups)
        }
        else if("running" in exo.name.lowercase()){
            drawable_img = ContextCompat.getDrawable(context, R.drawable.running)
        }
        else if("cardio" in exo.name.lowercase()){
            drawable_img = ContextCompat.getDrawable(context, R.drawable.running)
        }
        else if("tricep" in exo.name.lowercase()){
            drawable_img = ContextCompat.getDrawable(context, R.drawable.triceps)
        }
        else if("bicep" in exo.name.lowercase()){
            drawable_img = ContextCompat.getDrawable(context, R.drawable.dumbbell)
        }
        else {
            drawable_img = ContextCompat.getDrawable(context, R.drawable.rat)
        }
        exoListItemBinding.exoImage.setImageDrawable(drawable_img)


        exoListItemBinding.exoListView.setOnClickListener {
            clickListener.onClick(exo)
        }
    }

}