package com.example.brs_cout.onBoarding

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import com.example.brs_cout.R
import com.example.brs_cout.StartActivity
import com.example.brs_cout.base.BaseFragment
import com.example.brs_cout.databinding.FragmentLoginBinding

class LoginFragment : BaseFragment<FragmentLoginBinding>() {

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentLoginBinding {
        return FragmentLoginBinding.inflate(inflater,container,false)
    }

    override fun init() = with(binding){
        loginBtn.setOnClickListener {

//            val email = loginEmailET.text.toString()
//            val password = loginPasswordET.text.toString()
//
//            if (email.isEmpty() or password.isEmpty()){
//                Toast.makeText(requireContext(), "email or password are empty", Toast.LENGTH_SHORT).show()
//                return@setOnClickListener
//            }

//            TODO("login logic")
            val intent = Intent(requireContext(), StartActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
        }
        loginForgotPasswordTV.setOnClickListener {

        }

        loginGoToRegisterTV.setOnClickListener {
            parentFragmentManager.beginTransaction().replace(R.id.main,UserTypeFragment()).commit()
        }

    }
}