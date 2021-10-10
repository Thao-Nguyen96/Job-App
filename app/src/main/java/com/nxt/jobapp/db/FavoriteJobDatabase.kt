package com.nxt.jobapp.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.nxt.jobapp.model.FavoriteJob


@Database(entities = [FavoriteJob::class], version = 1)
abstract class FavoriteJobDatabase : RoomDatabase() {

    abstract fun getFavJobDAO(): FavoriteJobDAO

    companion object {

        @Volatile
        private var instance: FavoriteJobDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: createDatabase(context).also { instance = it }
        }

        private fun createDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                FavoriteJobDatabase::class.java,
                "fav_job_db"
            ).build()
    }
}