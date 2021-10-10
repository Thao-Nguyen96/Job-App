package com.nxt.jobapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.nxt.jobapp.model.FavoriteJob
import com.nxt.jobapp.repository.RemoteJobRepository
import kotlinx.coroutines.launch

class RemoteJobViewModel(app: Application, private val repository: RemoteJobRepository) :
    AndroidViewModel(app) {

    fun remoteResult() = repository.remoteJobResult()

    fun addFavoriteJob(job: FavoriteJob) = viewModelScope.launch {
        repository.addFavoriteJob(job)
    }

    fun deleteFavoriteJob(job: FavoriteJob) = viewModelScope.launch {
        repository.deleteJob(job)
    }

    fun getAllFavoriteJob() = repository.getAllFavoriteJob()

    fun searchRemoteJob(query: String?)= repository.searchJobResponse(query)
    fun searchResult() = repository.searchJobResult()

}