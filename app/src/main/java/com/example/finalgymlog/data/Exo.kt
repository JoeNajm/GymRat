package com.example.finalgymlog.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "exo_table")
data class Exo(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val reps: String,
    val weights: String,
    val comment: String,
    val parentId: Int
)