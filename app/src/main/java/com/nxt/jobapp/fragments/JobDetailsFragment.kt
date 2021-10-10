package com.nxt.jobapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebSettings
import android.webkit.WebViewClient
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import com.nxt.jobapp.MainActivity
import com.nxt.jobapp.R
import com.nxt.jobapp.databinding.FragmentJobDetailsBinding
import com.nxt.jobapp.model.FavoriteJob
import com.nxt.jobapp.model.Job
import com.nxt.jobapp.viewmodel.RemoteJobViewModel


class JobDetailsFragment : Fragment(R.layout.fragment_job_details) {

    private var _binding: FragmentJobDetailsBinding? = null
    private val binding get() = _binding!!
    private lateinit var currentJob: Job
    private val args: JobDetailsFragmentArgs by navArgs()
    private lateinit var viewModel: RemoteJobViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentJobDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = (activity as MainActivity).viewModel

        currentJob = args.job!!

        setUpWebView()

        binding.fabAddFavorite.setOnClickListener {
            addFavJob(view)
        }
    }

    private fun addFavJob(view: View) {
        val favJob = FavoriteJob(
            0,
            currentJob.candidateRequiredLocation,
            currentJob.category,
            currentJob.companyLogoUrl,
            currentJob.companyName,
            currentJob.description,
            currentJob.id,
            currentJob.jobType,
            currentJob.publicationDate,
            currentJob.salary,
            currentJob.title,
            currentJob.url
        )

        viewModel.addFavoriteJob(favJob)
        Snackbar.make(view, "job saves successfully", Snackbar.LENGTH_LONG).show()
    }

    private fun setUpWebView() {
        binding.webView.apply {
            webViewClient = WebViewClient()
            currentJob.url?.let { loadUrl(it) }
        }

            //cl url inside webview
        binding.webView.settings.apply {
            javaScriptEnabled = true
            setAppCacheEnabled(true)
            cacheMode = WebSettings.LOAD_DEFAULT
            setSupportZoom(false)
            builtInZoomControls = false
            displayZoomControls = false
            textZoom = 100
            blockNetworkImage = false
            loadsImagesAutomatically = true
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}