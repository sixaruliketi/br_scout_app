package com.example.brs_cout.onBoarding.company

import android.os.Bundle
import android.provider.Settings.Global.putString
import android.util.Patterns
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import com.example.brs_cout.R
import com.example.brs_cout.base.BaseFragment
import com.example.brs_cout.databinding.FragmentCompanyRegisterInfoBinding
import com.google.firebase.auth.FirebaseAuth

class CompanyRegisterInfoFragment : BaseFragment<FragmentCompanyRegisterInfoBinding>() {

    private val auth = FirebaseAuth.getInstance()

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentCompanyRegisterInfoBinding {
        return FragmentCompanyRegisterInfoBinding.inflate(inflater, container, false)
    }

    override fun init() = with(binding) {
        companyRegisterInfoNextBtn.setOnClickListener {
            val email = companyEmailET.text.toString().trim()
            val password = companyPassword.text.toString().trim()
            val repeatPassword = companyRepeatPassword.text.toString().trim()

            if (validatePasswordInput(email, password, repeatPassword)) {
                auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                    if (task.isSuccessful) {

                        parentFragmentManager.beginTransaction()
                            .replace(R.id.main, CompanyRegisterFragment()).commit()
                    }


                }
                    .addOnFailureListener { e ->
                        Toast.makeText(
                            requireContext(),
                            "Registration failed: ${e.message}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
            }


        }
    }

    private fun validatePasswordInput(
        email: String,
        password: String,
        repeatPassword: String
    ): Boolean {
        if (email.isBlank() || password.isBlank() || repeatPassword.isBlank()) {
            Toast.makeText(requireContext(), "All fields are required", Toast.LENGTH_SHORT).show()
            return false
        }

        // Email validation
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(
                requireContext(),
                "Please enter a valid email address",
                Toast.LENGTH_SHORT
            ).show()
            return false
        }

        if (password.length < 6) {
            Toast.makeText(
                requireContext(),
                "Password must be at least 6 characters",
                Toast.LENGTH_SHORT
            ).show()
            return false
        }

        if (password != repeatPassword) {
            Toast.makeText(requireContext(), "Passwords do not match", Toast.LENGTH_SHORT).show()
            return false
        }

        return true
    }

}