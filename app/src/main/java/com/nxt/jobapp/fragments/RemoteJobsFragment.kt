package com.nxt.jobapp.fragments

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.nxt.jobapp.MainActivity
import com.nxt.jobapp.R
import com.nxt.jobapp.adapter.RemoteJobAdapter
import com.nxt.jobapp.databinding.FragmentRemoteJobsBinding
import com.nxt.jobapp.utils.Constants
import com.nxt.jobapp.viewmodel.RemoteJobViewModel


class RemoteJobsFragment : Fragment(R.layout.fragment_remote_jobs),
    SwipeRefreshLayout.OnRefreshListener {

    private var _binding: FragmentRemoteJobsBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: RemoteJobViewModel
    private lateinit var remoteJonAdapter: RemoteJobAdapter
    private lateinit var swipeLayout: SwipeRefreshLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentRemoteJobsBinding.inflate(inflater, container, false)


        swipeLayout = binding.swipe
        swipeLayout.setOnRefreshListener(this)
        swipeLayout.setColorSchemeColors(
            Color.GREEN, Color.RED,
            Color.BLUE, Color.DKGRAY
        )

        swipeLayout.post {
            swipeLayout.isRefreshing = true
            setUpRecyclerView()
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = (activity as MainActivity).viewModel

        setUpRecyclerView()
    }

    private fun setUpRecyclerView() {
        remoteJonAdapter = RemoteJobAdapter()

        binding.rvRemoteJobs.apply {
            layoutManager = LinearLayoutManager(activity)
            setHasFixedSize(true)
            addItemDecoration(object : DividerItemDecoration(activity, LinearLayout.VERTICAL) {})
            adapter = remoteJonAdapter
        }

        fetchingData()
    }

    private fun fetchingData() {

        if (Constants.checkInternetConnection(requireContext())) {
            viewModel.remoteResult().observe(viewLifecycleOwner, { remoteJob ->

                if (remoteJob != null) {
                    remoteJonAdapter.differ.submitList(remoteJob.jobs)
                    swipeLayout.isRefreshing = false
                }
            })
        } else {
            Toast.makeText(activity, "no internet", Toast.LENGTH_LONG).show()
            swipeLayout.isRefreshing = false
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onRefresh() {
        setUpRecyclerView()
    }
}