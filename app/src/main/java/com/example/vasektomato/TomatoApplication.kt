package com.example.vasektomato

import android.app.Application
import com.example.vasektomato.db.AppDatabase
import com.example.vasektomato.db.repository.TomatoRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class TomatoApplication : Application() {
    private val applicationScope = CoroutineScope(SupervisorJob())
    // Using by lazy so the database and the repository are only created when they're needed
    // rather than when the application starts
    private val database by lazy { AppDatabase.getDatabase(this, applicationScope) }
    val repository by lazy { TomatoRepository(database.tomatoDao()) }
}