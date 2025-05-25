package com.example.brs_cout.onBoarding.company

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.brs_cout.StartActivity
import com.example.brs_cout.base.BaseFragment
import com.example.brs_cout.databinding.FragmentCompanyBinding

class CompanyRegisterFragment : BaseFragment<FragmentCompanyBinding>() {
    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentCompanyBinding {
        return FragmentCompanyBinding.inflate(inflater,container,false)
    }

    override fun init() = with(binding) {
        nextBtn.setOnClickListener {
            val intent = Intent(requireContext(), StartActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
        }
    }
}