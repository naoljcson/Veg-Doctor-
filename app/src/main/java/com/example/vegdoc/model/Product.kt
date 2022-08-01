package com.example.vegdoc.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "product")
data class Product(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int?,

    @ColumnInfo(name = "problem_id")
    val problemId: Int?,

    val name: String,

    @ColumnInfo(name = "trade_name")
    val tradeName: String,

    @ColumnInfo(name = "trade_name_amh")
    val amharicTradeName: String,

    @ColumnInfo(name = "ingridient_name")
    val ingridientName: String,

    @ColumnInfo(name = "ingridient_name_amh")
    val amharicIngridientName: String,

    @ColumnInfo(name = "class_no")
    val classNo: String,


    @ColumnInfo(name = "class_no_amh")
    val classNoAmh: String?,
)
