package com.example.finalgymlog.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "exoinventory_table")
data class ExoInventory(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val imagepath: String,
    val type: String
)