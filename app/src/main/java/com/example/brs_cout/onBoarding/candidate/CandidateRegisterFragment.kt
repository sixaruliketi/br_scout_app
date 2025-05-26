package com.example.brs_cout.onBoarding.candidate

import android.util.Patterns
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import com.example.brs_cout.R
import com.example.brs_cout.base.BaseFragment
import com.example.brs_cout.databinding.FragmentCandidateBinding
import com.example.brs_cout.onBoarding.company.CompanyRegisterFragment
import com.google.firebase.auth.FirebaseAuth

class CandidateRegisterFragment : BaseFragment<FragmentCandidateBinding>() {

    private val auth = FirebaseAuth.getInstance()

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentCandidateBinding {
        return FragmentCandidateBinding.inflate(inflater, container, false)
    }

    override fun init() = with(binding) {
        nextBtn.setOnClickListener {
            val email = registerCandidateEmailET.text.toString().trim()
            val password = registerCandidatePasswordET.text.toString().trim()
            val repeatPassword = registerCandidateRepeatPasswordET.text.toString().trim()

            if (validatePasswordInput(email, password, repeatPassword)) {
                auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        parentFragmentManager.beginTransaction().replace(
                            R.id.main,
                            CandidatePersonalInformationFragment()
                        ).commit()
                    }
                }.addOnFailureListener { e ->
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