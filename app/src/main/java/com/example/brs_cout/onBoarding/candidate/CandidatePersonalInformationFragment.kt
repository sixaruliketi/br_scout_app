package com.example.brs_cout.onBoarding.candidate

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import com.example.brs_cout.R
import com.example.brs_cout.base.BaseFragment
import com.example.brs_cout.databinding.FragmentCandidatePersonalInformationBinding
import com.example.brs_cout.models.Candidate
import com.example.brs_cout.onBoarding.UserTypeFragment
import com.google.firebase.database.FirebaseDatabase
import java.util.UUID

class CandidatePersonalInformationFragment :
    BaseFragment<FragmentCandidatePersonalInformationBinding>() {

    private val db = FirebaseDatabase.getInstance().getReference("candidates")

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentCandidatePersonalInformationBinding {
        return FragmentCandidatePersonalInformationBinding.inflate(inflater, container, false)
    }

    override fun init() = with(binding) {
        val uid = UUID.randomUUID().toString()
        nextBtn.setOnClickListener {

            val name = registerCandidateFullNameET.text.toString()
            val email = registerCandidateEmailET.text.toString().trim()
            val number = registerCandidatePhoneET.text.toString().trim()
            val currentJobTitle = currentJobTitleET.text.toString()
            val yearsOfExperience = yearsOfExperienceET.text.toString().trim().toIntOrNull()
            val desiredJobTitle = desiredJobTitleET.text.toString()
            val salaryExpectation = salaryExpectationET.text.toString().trim().toDoubleOrNull()
            val availableForRemote = isAvailableForRemoteCheckBox.isChecked
            val lookingForJob = isLookingForJobCheckBox.isChecked
            val bio = bioET.text.toString()

            val candidate = Candidate(
                uid,
                name,
                email,
                number,
                currentJobTitle,
                null,
                bio,
                yearsOfExperience,
                desiredJobTitle,
                salaryExpectation,
                availableForRemote,
                lookingForJob
            )
            db.child(uid)
                .setValue(candidate)
                .addOnSuccessListener {
                    parentFragmentManager.beginTransaction()
                        .replace(R.id.main, CandidateTechSkillsFragment()).commit()
                    Toast.makeText(requireContext(), "uploaded", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener {
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }
        }
        goBackToUserType.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.main, UserTypeFragment()).commit()
        }
    }
}