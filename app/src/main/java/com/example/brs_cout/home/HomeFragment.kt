package com.example.brs_cout.home

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.brs_cout.adapters.CandidateAdapter
import com.example.brs_cout.base.BaseFragment
import com.example.brs_cout.databinding.FragmentHomeBinding
import com.example.brs_cout.models.Candidate
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentHomeBinding {
        return FragmentHomeBinding.inflate(inflater, container, false)
    }

    override fun init() = with(binding) {
        loadCandidates(homeRecyclerView)
        homeRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        HomeCreateNewVacancy.setOnClickListener {
            CreateVacancyFragment().show(parentFragmentManager, "CreateVacancyDialog")
        }
    }
    private fun loadCandidates(recyclerView: RecyclerView) {

        getAllCandidatesFromFirebase { allCandidates ->
            recyclerView.adapter = CandidateAdapter(allCandidates)
            Log.d("TAG", "recommended")
        }

    }
    private fun getAllCandidatesFromFirebase(onComplete: (List<Candidate>) -> Unit) {
        val dbRef = FirebaseDatabase.getInstance().getReference("candidates")
        dbRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val candidates = mutableListOf<Candidate>()
                for (candidateSnapshot in snapshot.children) {
                    val candidate = candidateSnapshot.getValue(Candidate::class.java)
                    if (candidate != null) {
                        candidates.add(candidate)
                    }
                }
                onComplete(candidates)
                Log.d("TAG", "Get All Candidates From Firebase")
            }

            override fun onCancelled(error: DatabaseError) {
                onComplete(emptyList())
            }
        })
    }
}