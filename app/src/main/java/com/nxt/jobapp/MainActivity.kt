package com.nxt.jobapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.nxt.jobapp.databinding.ActivityMainBinding
import com.nxt.jobapp.db.FavoriteJobDatabase
import com.nxt.jobapp.repository.RemoteJobRepository
import com.nxt.jobapp.viewmodel.RemoteJobViewModel
import com.nxt.jobapp.viewmodel.RemoteViewModelFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    lateinit var viewModel: RemoteJobViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.title = "Main"

        setViewModel()
    }

    private fun setViewModel() {

        val repository = RemoteJobRepository(
            FavoriteJobDatabase(this)
        )

        val viewModelProviderFactory = RemoteViewModelFactory(
            application,
            repository
        )

        viewModel = ViewModelProvider(
            this,
            viewModelProviderFactory
        ).get(RemoteJobViewModel::class.java)
    }
}