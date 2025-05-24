package com.example.brs_cout.onBoarding.candidate

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.brs_cout.R
import com.example.brs_cout.base.BaseFragment
import com.example.brs_cout.databinding.FragmentCandidateBinding

class CandidateRegisterFragment : BaseFragment<FragmentCandidateBinding>() {
    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentCandidateBinding {
        return FragmentCandidateBinding.inflate(inflater,container,false)
    }

    override fun init() = with(binding) {
        nextBtn.setOnClickListener {
            parentFragmentManager.beginTransaction().replace(R.id.main,CandidatePersonalInformationFragment()).commit()
        }
    }

}