package com.example.finalgymlog

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.finalgymlog.SessionListFragment
import com.example.finalgymlog.data.Session
import com.example.finalgymlog.databinding.SessionItemBinding


class SessionListAdapter(
    private var sessionList: List<Session>,
//    private val context: Context,
    private val clickListener: SessionListFragment
) : RecyclerView.Adapter<SessionListViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SessionListViewHolder {
        val from = LayoutInflater.from(parent.context)
        val binding = SessionItemBinding.inflate(from, parent, false)
        return SessionListViewHolder(binding, parent.context, clickListener)
    }

    override fun onBindViewHolder(holder: SessionListViewHolder, position: Int) {
        holder.bindItem(sessionList[position])
    }

    override fun getItemCount(): Int = sessionList.size

    fun setData(sessions: List<Session>) {
        this.sessionList = sessions
        notifyDataSetChanged()
    }
}