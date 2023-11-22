package com.example.finalgymlog.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "food_table")
data class Food(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val protein: Float,
    val energy: Float,
    val type: String,
    val parentId: Int
)