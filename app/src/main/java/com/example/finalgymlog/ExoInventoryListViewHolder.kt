package com.example.finalgymlog

import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.finalgymlog.data.Exo
import com.example.finalgymlog.data.ExoInventory
import com.example.finalgymlog.data.Session
import com.example.finalgymlog.databinding.ExoItemBinding
import com.example.finalgymlog.databinding.ExoinventoryItemBinding
import com.example.finalgymlog.databinding.SessionItemBinding
import java.io.File

class ExoInventoryListViewHolder(
    private val exoInventoryListItemBinding: ExoinventoryItemBinding,
    private val context: Context,
    private val exoInventoryClickListener: ExoInventoryFragment?,
    private val addExoClickListener: AddExoFragment?,
) : RecyclerView.ViewHolder(exoInventoryListItemBinding.root){

    @RequiresApi(Build.VERSION_CODES.N)
    fun bindItem(exo: ExoInventory){
        exoInventoryListItemBinding.exoName.text = exo.name

        val PathOfImage = exo.imagepath

        if(PathOfImage.contains("drawable", ignoreCase = true)){
            val intId = PathOfImage.substring(11, PathOfImage.length).toInt()
            exoInventoryListItemBinding.exoImage.setImageDrawable(ContextCompat.getDrawable(context, intId))
        } else{
            val file = File(context.filesDir, PathOfImage)
            val bitmap = BitmapFactory.decodeFile(file.absolutePath)
            exoInventoryListItemBinding.exoImage.setImageBitmap(bitmap)
        }

        exoInventoryListItemBinding.exoListView.setOnClickListener {
            if(exoInventoryClickListener != null){
                exoInventoryClickListener.onClick(exo)
            } else if(addExoClickListener != null){
                addExoClickListener.onClick(exo)
            }
        }

    }

}