package com.nxt.jobapp.repository

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nxt.jobapp.api.RetroInstance
import com.nxt.jobapp.db.FavoriteJobDatabase
import com.nxt.jobapp.model.FavoriteJob
import com.nxt.jobapp.model.RemoteJob
import retrofit2.Call
import retrofit2.Response

class RemoteJobRepository(private val db: FavoriteJobDatabase) {

    private val remoteJobService = RetroInstance.api
    private val remoteResponseLiveData: MutableLiveData<RemoteJob> = MutableLiveData()
    private val searchJobResponseLiveData: MutableLiveData<RemoteJob> = MutableLiveData()

    init {
        getRemoteJobResponse()
    }


    private fun getRemoteJobResponse() {

        remoteJobService.getRemoteJob().enqueue(object : retrofit2.Callback<RemoteJob> {
            override fun onResponse(call: Call<RemoteJob>, response: Response<RemoteJob>) {
                remoteResponseLiveData.postValue(response.body())
            }

            override fun onFailure(call: Call<RemoteJob>, t: Throwable) {
                Log.d("tag", "error : ${t.message}")
            }

        })
    }


    fun searchJobResponse(query: String?) {
        remoteJobService.searchJob(query).enqueue(object : retrofit2.Callback<RemoteJob> {
            override fun onResponse(call: Call<RemoteJob>, response: Response<RemoteJob>) {
                if (response.body() != null) {
                    searchJobResponseLiveData.postValue(response.body())
                }
            }

            override fun onFailure(call: Call<RemoteJob>, t: Throwable) {
                Log.d("tag", t.message.toString())
            }

        })
    }

    fun remoteJobResult(): LiveData<RemoteJob> {
        return remoteResponseLiveData
    }

    fun searchJobResult():LiveData<RemoteJob>{
        return searchJobResponseLiveData
    }

    suspend fun addFavoriteJob(job: FavoriteJob) = db.getFavJobDAO().addFavorite(job)
    suspend fun deleteJob(job: FavoriteJob) = db.getFavJobDAO().deleteFavJob(job)
    fun getAllFavoriteJob() = db.getFavJobDAO().getAllFavJob()
}