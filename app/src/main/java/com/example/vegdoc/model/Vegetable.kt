package com.example.vegdoc.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "vegetable")
data class Vegetable(
    val name: String,

    val description: String,

    @ColumnInfo(name = "image_name")
    val imageName: String,

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int,

    @ColumnInfo(name = "amharic_name")
    val amharicName: String,


)
