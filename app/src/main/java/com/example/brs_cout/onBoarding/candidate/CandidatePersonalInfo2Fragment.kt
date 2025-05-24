package com.example.brs_cout.onBoarding.candidate

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.brs_cout.R
import com.example.brs_cout.base.BaseFragment
import com.example.brs_cout.databinding.FragmentCandidatePersonalInfo2Binding

class CandidatePersonalInfo2Fragment : BaseFragment<FragmentCandidatePersonalInfo2Binding>() {
    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentCandidatePersonalInfo2Binding {
        return FragmentCandidatePersonalInfo2Binding.inflate(inflater,container,false)
    }

    override fun init() = with(binding){
        nextBtn.setOnClickListener {
            Toast.makeText(requireContext(), "candidate finished registering!", Toast.LENGTH_SHORT).show()
        }
    }
}