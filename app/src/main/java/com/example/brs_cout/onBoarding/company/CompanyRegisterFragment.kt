package com.example.brs_cout.onBoarding.company

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.brs_cout.base.BaseFragment
import com.example.brs_cout.databinding.FragmentCompanyBinding

class CompanyRegisterFragment : BaseFragment<FragmentCompanyBinding>() {
    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentCompanyBinding {
        return FragmentCompanyBinding.inflate(inflater,container,false)
    }

    override fun init() {
        TODO("Not yet implemented")
    }
}