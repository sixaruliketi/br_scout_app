package com.example.brs_cout.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.brs_cout.R
import com.example.brs_cout.models.Candidate


class CandidateDetailsFragment : Fragment() {

    companion object {
        private const val ARG_CANDIDATE = "arg_candidate"
        fun newInstance(candidate: Candidate): CandidateDetailsFragment {
            val fragment = CandidateDetailsFragment()
            val args = Bundle()
            args.putSerializable(ARG_CANDIDATE, candidate)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_candidate_details, container, false)
        val candidate = arguments?.getSerializable(ARG_CANDIDATE) as? Candidate
        // Populate views
        return view
    }
}
