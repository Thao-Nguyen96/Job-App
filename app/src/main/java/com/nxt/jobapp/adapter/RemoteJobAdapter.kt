package com.nxt.jobapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nxt.jobapp.databinding.JobLayoutAdapterBinding
import com.nxt.jobapp.fragments.MainFragmentDirections
import com.nxt.jobapp.model.Job

class RemoteJobAdapter : RecyclerView.Adapter<RemoteJobAdapter.RemoteJobViewHolder>() {

    private var binding: JobLayoutAdapterBinding? = null

    private val differCallback = object : DiffUtil.ItemCallback<Job>() {
        override fun areItemsTheSame(oldItem: Job, newItem: Job): Boolean {
            return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(oldItem: Job, newItem: Job): Boolean {
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

            val dateJob = currentJob.publicationDate?.split("T")
            binding?.tvDate?.text = dateJob?.get(0)
        }.setOnClickListener { mView ->
            val direction = MainFragmentDirections
                .actionMainFragmentToJobDetailsFragment(currentJob)

            mView.findNavController().navigate(direction)

        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    inner class RemoteJobViewHolder(var binding: JobLayoutAdapterBinding) :
        RecyclerView.ViewHolder(binding.root)
}