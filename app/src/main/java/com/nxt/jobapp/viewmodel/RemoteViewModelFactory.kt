package com.nxt.jobapp.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nxt.jobapp.repository.RemoteJobRepository

class RemoteViewModelFactory(val app: Application, private val repository: RemoteJobRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return RemoteJobViewModel(app, repository) as T
    }
}