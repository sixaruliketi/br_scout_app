package com.example.brs_cout.profile

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.brs_cout.R
import com.example.brs_cout.adapters.CandidateAdapter
import com.example.brs_cout.models.Candidate
import com.example.brs_cout.models.Vacancy
import com.example.brs_cout.recommendationSystem.NewRecommendationSystem
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class RecommendedCandidatesDialogFragment : DialogFragment() {

    private var vacancyId: String? = null
    private val companyID = FirebaseAuth.getInstance().currentUser!!.uid

    companion object {
        fun newInstance(vacancyId: String): RecommendedCandidatesDialogFragment {
            val fragment = RecommendedCandidatesDialogFragment()
            val args = Bundle()
            args.putString("vacancyId", vacancyId)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vacancyId = arguments?.getString("vacancyId")
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireContext())
        val view = LayoutInflater.from(context).inflate(R.layout.dialog_recommended_candidates, null)

        val recyclerView = view.findViewById<RecyclerView>(R.id.candidatesRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(context)

        loadRecommendedCandidates(vacancyId ?: "", recyclerView)

        builder.setView(view)
            .setTitle("Recommended Candidates")
            .setNegativeButton("close") { dialog, _ ->
                dialog.dismiss()
            }

        return builder.create()
    }

    private fun loadRecommendedCandidates(vacancyId: String, recyclerView: RecyclerView) {
        getVacancyById(companyID, vacancyId) { vacancy ->
            if (vacancy != null) {
                getAllCandidatesFromFirebase { allCandidates ->

                    // Step 1: Filter top 20 using Cosine Similarity
                    val topPairs = NewRecommendationSystem.getTop20Candidates(vacancy,allCandidates)
                    Log.d("TAG", "top pairs size: ${topPairs.size}\n$topPairs")

                    if (topPairs.isEmpty()) {
                        recyclerView.adapter = CandidateAdapter(emptyList()) { }
                        Log.d("TAG", "No matching candidates found")
                        return@getAllCandidatesFromFirebase
                    }

                    // Step 2: Prepare data for TOPSIS
                    val topsisInput = topPairs.map { (candidate, similarity) ->
                        NewRecommendationSystem.TopsisCandidate(
                            candidate = candidate,
                            similarity = similarity,
                            experience = candidate.yearsOfExperience ?: 0,
                            salary = candidate.salaryExpectation ?: Double.MAX_VALUE,
                            daysToStart = 0 // ამ ველს რეალური მონაცემები სჭირდება
                        )
                    }
                    Log.d("TAG", "data for TOPSIS: $topsisInput")

                    // Step 3: Run TOPSIS
                    val top5 = NewRecommendationSystem.topsisRank(topsisInput)
                    Log.d("TAG", "top5: $top5")

                    // Step 4: Map back to Candidate objects
                    val recommended = top5.mapNotNull { (candidate, _) ->
                        allCandidates.find { it.id == candidate.id }
                    }
                    Log.d("TAG", "recommended: $recommended")

                    recyclerView.adapter = CandidateAdapter(recommended) { candidate ->
                        val fragment = CandidateDetailsFragment.newInstance(candidate)
                        requireActivity().supportFragmentManager.beginTransaction()
                            .replace(R.id.fragment_container, fragment)
                            .addToBackStack(null)
                            .commit()
                        dismiss()
                    }

                    Log.d("TAG", "Recommended candidates loaded")
                }
            } else {
                recyclerView.adapter = CandidateAdapter(emptyList()) { }
                Log.d("TAG", "Vacancy not found")
            }
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
            }

            override fun onCancelled(error: DatabaseError) {
                onComplete(emptyList())
            }
        })
    }

    private fun getVacancyById(companyUid: String, vacancyId: String, onComplete: (Vacancy?) -> Unit) {
        val dbRef = FirebaseDatabase.getInstance()
            .getReference("companies")
            .child(companyUid)
            .child("vacancies")
            .child(vacancyId)

        dbRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val vacancy = snapshot.getValue(Vacancy::class.java)
                onComplete(vacancy)
            }

            override fun onCancelled(error: DatabaseError) {
                onComplete(null)
            }
        })
    }
}
