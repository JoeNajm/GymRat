package com.example.finalgymlog.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "food_table")
data class Food(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val protein: Double,
    val energy: Double,
    val type: String,
    val parentId: Int
)