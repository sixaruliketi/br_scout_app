package com.example.brs_cout.profile

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.brs_cout.R
import com.example.brs_cout.adapters.CandidateAdapter
import com.example.brs_cout.models.Candidate
import com.example.brs_cout.models.ListItem

class RecommendedCandidatesDialogFragment : DialogFragment() {

    private var vacancyId: String? = null

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

        // TODO: Load candidates from Firebase or local list
        loadRecommendedCandidates(vacancyId ?: "", recyclerView)

        builder.setView(view)
            .setTitle("რეკომენდირებული კანდიდატები")
            .setNegativeButton("დახურვა") { dialog, _ ->
                dialog.dismiss()
            }

        return builder.create()
    }

    private fun loadRecommendedCandidates(vacancyId: String, recyclerView: RecyclerView) {
        // შენ შეიძლება გამოიყენო Firebase Query აქ
        // მაგალითად — ყველა კანდიდატის წამოღება და ფილტრი ვაკანსიის მოთხოვნების მიხედვით

        val dummyCandidates = listOf(
            Candidate("1", "ნინო ქავთარაძე", "nino@gmail.com", "555123456", "Android Dev", null, null, 3, "Senior Android Dev", 4000.0, true, true,
                technicalSkills = listOf(ListItem.SkillItem("Android"), ListItem.SkillItem("Kotlin")),
                softSkills = listOf(ListItem.SkillItem("Teamwork")),
                languages = listOf(ListItem.SkillItem("English"))
            ),
            Candidate("2", "გიორგი ლიპარტელიანი", "giorgi@gmail.com", "555654321", "Backend Dev", null, null, 5, "Tech Lead", 5000.0, true, false,
                technicalSkills = listOf(ListItem.SkillItem("Node.js"), ListItem.SkillItem("Docker")),
                softSkills = listOf(ListItem.SkillItem("Leadership")),
                languages = listOf(ListItem.SkillItem("English"), ListItem.SkillItem("Georgian"))
            )
        )

        recyclerView.adapter = CandidateAdapter(dummyCandidates)
    }
}
