package com.nxt.jobapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nxt.jobapp.databinding.JobLayoutAdapterBinding
import com.nxt.jobapp.fragments.MainFragmentDirections
import com.nxt.jobapp.model.FavoriteJob
import com.nxt.jobapp.model.Job

class FavoriteJobAdapter constructor(
    private val itemClick: OnItemClickListener,
) : RecyclerView.Adapter<FavoriteJobAdapter.RemoteJobViewHolder>() {

    private var binding: JobLayoutAdapterBinding? = null

    private val differCallback = object : DiffUtil.ItemCallback<FavoriteJob>() {
        override fun areItemsTheSame(oldItem: FavoriteJob, newItem: FavoriteJob): Boolean {
            return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(oldItem: FavoriteJob, newItem: FavoriteJob): Boolean {
            return oldItem == newItem
        }

    }
    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RemoteJobViewHolder {
        binding = JobLayoutAdapterBinding.inflate(LayoutInflater.from(parent.context),
            parent,
            false)

        return RemoteJobViewHolder(binding!!)
    }

    override fun onBindViewHolder(holder: RemoteJobViewHolder, position: Int) {
        val currentJob = differ.currentList[position]

        holder.itemView.apply {
            Glide.with(this)
                .load(currentJob.companyLogoUrl)
                .into(binding?.ivCompanyLogo!!)

            binding?.tvCompanyName?.text = currentJob.companyName
            binding?.tvJobLocation?.text = currentJob.candidateRequiredLocation
            binding?.tvJobTitle?.text = currentJob.title
            binding?.tvJobType?.text = currentJob.jobType
            binding?.ibDelete?.visibility = View.VISIBLE

            val dateJob = currentJob.publicationDate?.split("T")
            binding?.tvDate?.text = dateJob?.get(0)
        }.setOnClickListener { mView ->

            val tags = arrayListOf<String>()

            val job = Job(
                currentJob.candidateRequiredLocation,
                currentJob.category,
                currentJob.companyLogoUrl,
                currentJob.companyName,
                currentJob.description,
                currentJob.jobId,
                currentJob.jobType,
                currentJob.publicationDate,
                currentJob.salary,
                tags,
                currentJob.title,
                currentJob.url
            )
            val direction = MainFragmentDirections
                .actionMainFragmentToJobDetailsFragment(job)

            mView.findNavController().navigate(direction)
        }

        holder.itemView.apply {
            binding?.ibDelete?.setOnClickListener {
                itemClick.onItemClick(currentJob, binding?.ibDelete!!, position)
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    inner class RemoteJobViewHolder(var binding: JobLayoutAdapterBinding) :
        RecyclerView.ViewHolder(binding.root)

    interface OnItemClickListener {
        fun onItemClick(
            job: FavoriteJob,
            view: View,
            position: Int,
        )
    }
}