package com.nxt.jobapp.fragments

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.nxt.jobapp.MainActivity
import com.nxt.jobapp.R
import com.nxt.jobapp.adapter.FavoriteJobAdapter
import com.nxt.jobapp.databinding.FragmentSavedJobsBinding
import com.nxt.jobapp.model.FavoriteJob
import com.nxt.jobapp.viewmodel.RemoteJobViewModel

class SavedJobsFragment : Fragment(R.layout.fragment_saved_jobs), FavoriteJobAdapter.OnItemClickListener {

    private var _binding: FragmentSavedJobsBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: RemoteJobViewModel
    private lateinit var favAdapter: FavoriteJobAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentSavedJobsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = (activity as MainActivity).viewModel

        setUpRecyclerview()
    }

    private fun setUpRecyclerview() {
        favAdapter = FavoriteJobAdapter(this)

        binding.rvJobsSaved.apply {
            layoutManager = LinearLayoutManager(activity)
            setHasFixedSize(true)
            addItemDecoration(object : DividerItemDecoration(
                activity, LinearLayout.HORIZONTAL) {})
            adapter = favAdapter
        }
        viewModel.getAllFavoriteJob().observe(viewLifecycleOwner,{favJob ->
            favAdapter.differ.submitList(favJob)

            upDateUi(favJob)
        })
    }

    private fun upDateUi(job: List<FavoriteJob>){

        if (job.isNotEmpty()){
            binding.rvJobsSaved.visibility = View.VISIBLE
            binding.cardNoAvailable.visibility = View.GONE
        }else{
            binding.rvJobsSaved.visibility = View.GONE
            binding.cardNoAvailable.visibility = View.VISIBLE
        }
    }

    override fun onItemClick(job: FavoriteJob, view: View, position: Int) {
        deleteJob(job)
    }

    private fun deleteJob(job: FavoriteJob){
        AlertDialog.Builder(activity).apply {
            setTitle("Delete Job")
            setMessage("Are you sure you want delete job?")
            setPositiveButton("DELETE"){_,_->
                viewModel.deleteFavoriteJob(job)
                Toast.makeText(activity,"job deleted",Toast.LENGTH_LONG).show()
            }
            setNegativeButton("CANCEL", null)
        }.create().show()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}