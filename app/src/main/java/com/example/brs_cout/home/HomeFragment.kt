package com.example.brs_cout.home

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.brs_cout.base.BaseFragment
import com.example.brs_cout.databinding.FragmentHomeBinding

class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentHomeBinding {
        init()
        return FragmentHomeBinding.inflate(inflater,container,false)
    }

    override fun init(){

    }

}