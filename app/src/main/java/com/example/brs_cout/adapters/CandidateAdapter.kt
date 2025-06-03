package com.example.brs_cout.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.brs_cout.R
import com.example.brs_cout.databinding.ItemCandidateBinding
import com.example.brs_cout.models.Candidate

class CandidateAdapter(
    private val candidates: List<Candidate>,
    private val onItemClick: (Candidate) -> Unit
) : RecyclerView.Adapter<CandidateAdapter.CandidateViewHolder>() {

    class CandidateViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = ItemCandidateBinding.bind(itemView)

        fun bind(candidate: Candidate, onItemClick: (Candidate) -> Unit) = with(binding) {
            nameTextView.text = candidate.fullName
            jobTitleTV.text = candidate.currentJobTitle
            Glide.with(itemView).load(candidate.profilePictureUrl).into(candidateProfileImage)

            root.setOnClickListener {
                onItemClick(candidate)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CandidateViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_candidate, parent, false)
        return CandidateViewHolder(view)
    }

    override fun onBindViewHolder(holder: CandidateViewHolder, position: Int) {
        holder.bind(candidates[position], onItemClick)
    }

    override fun getItemCount(): Int = candidates.size
}
