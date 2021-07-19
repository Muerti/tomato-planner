package com.example.vasektomato.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.vasektomato.db.model.Tomato
import kotlinx.coroutines.flow.Flow

@Dao
interface TomatoDao {
    @Query("SELECT * FROM tomato_table")
    fun getAllTomatoes(): Flow<List<Tomato>>

    @Query("SELECT * FROM tomato_table WHERE name LIKE :name LIMIT 1")
    fun findByName(name: String): Tomato

    @Insert
    suspend fun insertAll(vararg tomatoes: Tomato)

    @Delete
    suspend fun delete(user: Tomato)
}