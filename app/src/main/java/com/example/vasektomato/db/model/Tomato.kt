package com.example.vasektomato.db.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tomato_table")
data class Tomato(@PrimaryKey @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "pause_time")
    val pauseTime: Int = 0,
    @ColumnInfo(name = "main_time")
    val mainTime: Int = 0,
    @ColumnInfo(name = "tomato_amount")
    val tomatoAmount: Int = 0,
    @ColumnInfo(name = "time_remained")
    val timeRemained: Int = 0,
    @ColumnInfo(name = "is_active")
    val isActive: Boolean = false)