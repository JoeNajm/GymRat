package com.example.finalgymlog.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "session_table")
data class Session(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val date: String,
    val comment: String,
    val body_weight: Double?,
)