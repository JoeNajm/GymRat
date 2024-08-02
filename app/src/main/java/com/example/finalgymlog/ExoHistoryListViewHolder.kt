package com.example.finalgymlog

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.finalgymlog.data.Exo
import com.example.finalgymlog.data.Session
import com.example.finalgymlog.data.SessionViewModel
import com.example.finalgymlog.data.SharedViewModel
import com.example.finalgymlog.databinding.ExoHistoryItemBinding

class ExoHistoryListViewHolder(
    private val exoHistoryBinding: ExoHistoryItemBinding,
    private val clickListener: ExoHistoryFragment,
    private val sessionList: List<Session>

) : RecyclerView.ViewHolder(exoHistoryBinding.root){

    @RequiresApi(Build.VERSION_CODES.N)
    fun bindItem(exo: Exo){
        exoHistoryBinding.exoDate.text = getDateFromSession(sessionList, exo.parentId)
        exoHistoryBinding.exoReps.text =  "Reps: " + exo.reps
        exoHistoryBinding.exoWeight.text = "kg: " +  exo.weights
    }

    fun getDateFromSession(sessionList: List<Session>, parentId: Int): String {
        for (session in sessionList) {
            if (session.id == parentId) {
                return session.date
            }
        }
        return parentId.toString()
    }
}