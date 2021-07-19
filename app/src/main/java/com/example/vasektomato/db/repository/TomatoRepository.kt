package com.example.vasektomato.db.repository

import androidx.annotation.WorkerThread
import com.example.vasektomato.db.dao.TomatoDao
import com.example.vasektomato.db.model.Tomato
import kotlinx.coroutines.flow.Flow

class TomatoRepository (private val tomatoDao: TomatoDao) {

    // Room executes all queries on a separate thread.
    // Observed Flow will notify the observer when the data has changed.
    val allTomatoes: Flow<List<Tomato>> = tomatoDao.getAllTomatoes()

    // By default Room runs suspend queries off the main thread, therefore, we don't need to
    // implement anything else to ensure we're not doing long running database work
    // off the main thread.
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(tomato: Tomato) {
        tomatoDao.insertAll(tomato)
    }

    fun findTomatoByName(name :String) : Tomato {
        return tomatoDao.findByName(name)
    }
}