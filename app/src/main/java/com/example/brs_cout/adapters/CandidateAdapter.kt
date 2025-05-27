package com.example.brs_cout.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.brs_cout.R
import com.example.brs_cout.models.Candidate

class CandidateAdapter(private val candidates: List<Candidate>) :
    RecyclerView.Adapter<CandidateAdapter.CandidateViewHolder>() {

    class CandidateViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(candidate: Candidate) {
            itemView.findViewById<TextView>(R.id.nameTextView).text = candidate.fullName
            itemView.findViewById<TextView>(R.id.titleTextView).text = candidate.currentJobTitle
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CandidateViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_candidate, parent, false)
        return CandidateViewHolder(view)
    }

    override fun onBindViewHolder(holder: CandidateViewHolder, position: Int) {
        holder.bind(candidates[position])
    }

    override fun getItemCount(): Int = candidates.size
}
