package com.example.brs_cout.onBoarding

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.brs_cout.R
import com.example.brs_cout.base.BaseFragment
import com.example.brs_cout.databinding.FragmentUserTypeBinding
import com.example.brs_cout.onBoarding.candidate.CandidatePersonalInformationFragment
import com.example.brs_cout.onBoarding.company.CompanyRegisterFragment
import com.example.brs_cout.onBoarding.company.CompanyRegisterInfoFragment

class UserTypeFragment : BaseFragment<FragmentUserTypeBinding>(){

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentUserTypeBinding {
        return FragmentUserTypeBinding.inflate(inflater,container,false)
    }

    override fun init() = with(binding){
        alreadyRegisteredGoToLoginTV.setOnClickListener {
            parentFragmentManager.beginTransaction().replace(R.id.main,LoginFragment()).commit()
        }
        chooseCompanyOnBoardingBtn.setOnClickListener {
            parentFragmentManager.beginTransaction().replace(R.id.main,CompanyRegisterInfoFragment()).commit()
        }
        chooseCandidateOnBoardingBtn.setOnClickListener {
            parentFragmentManager.beginTransaction().replace(R.id.main,CandidatePersonalInformationFragment()).commit()
        }
    }


}