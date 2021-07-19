package com.example.vasektomato.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.vasektomato.db.dao.TomatoDao
import com.example.vasektomato.db.model.Tomato
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = [Tomato::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun tomatoDao(): TomatoDao

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): AppDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "tomato_database"
                ).addCallback(TomatoDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }

    private class TomatoDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch {
                    populateDatabase(database.tomatoDao())
                }
            }
        }

        fun populateDatabase(tomatoDao: TomatoDao) {
            // Delete all content here.
            //tomatoDao.deleteAll()

            // Add sample tomatoes.
            // TODO: Add your own words!
        }
    }
}

