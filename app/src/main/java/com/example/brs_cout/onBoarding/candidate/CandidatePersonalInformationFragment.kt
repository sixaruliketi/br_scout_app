package com.example.brs_cout.onBoarding.candidate

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import com.example.brs_cout.R
import com.example.brs_cout.StartActivity
import com.example.brs_cout.base.BaseFragment
import com.example.brs_cout.databinding.FragmentCandidatePersonalInformationBinding

class CandidatePersonalInformationFragment : BaseFragment<FragmentCandidatePersonalInformationBinding>() {
    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentCandidatePersonalInformationBinding {
        return FragmentCandidatePersonalInformationBinding.inflate(inflater,container,false)
    }

    override fun init() = with(binding){
        nextBtn.setOnClickListener {
            parentFragmentManager.beginTransaction().replace(R.id.main,CandidateTechSkillsFragment()).commit()
        }
    }
}