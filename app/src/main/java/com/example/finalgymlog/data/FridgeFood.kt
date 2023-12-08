package com.example.finalgymlog.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "fridgefood_table")
data class FridgeFood(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val protein: Double,
    val energy: Double,
    val type: String,
)