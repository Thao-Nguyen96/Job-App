package com.nxt.jobapp.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.nxt.jobapp.model.FavoriteJob

@Dao
interface FavoriteJobDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFavorite(job: FavoriteJob)

    @Query("SELECT * FROM fav_job ORDER BY id DESC")
    fun getAllFavJob(): LiveData<List<FavoriteJob>>

    @Delete
    suspend fun deleteFavJob(job: FavoriteJob)
}