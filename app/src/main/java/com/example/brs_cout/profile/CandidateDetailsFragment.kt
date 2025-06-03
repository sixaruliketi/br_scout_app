package com.example.brs_cout.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.brs_cout.R
import com.example.brs_cout.databinding.FragmentCandidateDetailsBinding
import com.example.brs_cout.models.Candidate

class CandidateDetailsFragment : Fragment() {

    lateinit var binding: FragmentCandidateDetailsBinding
    private var candidate: Candidate? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        candidate = arguments?.getSerializable(ARG_CANDIDATE) as? Candidate
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCandidateDetailsBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?): Unit = with(binding){
        super.onViewCreated(view, savedInstanceState)
        candidate?.let {
            nameTextView.text = it.fullName ?: "-"
            emailTextView.text = it.email ?: "-"
            jobTitleTextView.text = it.currentJobTitle ?: "-"
            bioTextView.text = it.bio ?: "-"
            experienceTextView.text = "${it.yearsOfExperience ?: 0} years"
            Glide.with(requireContext()).load(it.profilePictureUrl).into(profileImage)
        }
    }

    companion object {
        private const val ARG_CANDIDATE = "candidate"

        fun newInstance(candidate: Candidate): CandidateDetailsFragment {
            val fragment = CandidateDetailsFragment()
            val bundle = Bundle()
            bundle.putSerializable(ARG_CANDIDATE, candidate)
            fragment.arguments = bundle
            return fragment
        }
    }
}

