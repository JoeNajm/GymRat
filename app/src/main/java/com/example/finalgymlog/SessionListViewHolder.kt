package com.example.finalgymlog

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.finalgymlog.SessionListFragment
import com.example.finalgymlog.data.Session
import com.example.finalgymlog.databinding.SessionItemBinding

class SessionListViewHolder(
    private val sessionListItemBinding: SessionItemBinding,
    private val context: Context,
    private val clickListener: SessionListFragment,
) : RecyclerView.ViewHolder(sessionListItemBinding.root){

    @RequiresApi(Build.VERSION_CODES.N)
    fun bindItem(session: Session){
        sessionListItemBinding.sessionName.text = session.name
        sessionListItemBinding.sessionDate.text = session.date

        val drawable: Drawable?

        if("leg" in session.name.lowercase()){
            drawable = ContextCompat.getDrawable(context, R.drawable.legs)
        }
        else if("upper" in session.name.lowercase()){
            drawable = ContextCompat.getDrawable(context, R.drawable.upper)
        }
        else {
            drawable = ContextCompat.getDrawable(context, R.drawable.dumbbell)
        }

        sessionListItemBinding.sessionImage.setImageDrawable(drawable)

        sessionListItemBinding.sessionListView.setOnClickListener {
            clickListener.onClick(session)
        }
    }

}