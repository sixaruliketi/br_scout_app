package com.example.brs_cout.onBoarding.candidate

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.brs_cout.R
import com.example.brs_cout.base.BaseFragment
import com.example.brs_cout.databinding.FragmentCandidateRegistrationSuccessPageBinding
import com.example.brs_cout.onBoarding.LoginFragment

class CandidateRegistrationSuccessPageFragment : BaseFragment<FragmentCandidateRegistrationSuccessPageBinding>() {
    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentCandidateRegistrationSuccessPageBinding {
        return FragmentCandidateRegistrationSuccessPageBinding.inflate(inflater,container,false)
    }

    override fun init() {
        binding.close.setOnClickListener {
            parentFragmentManager.beginTransaction().replace(R.id.main,LoginFragment()).commit()
        }
    }
}