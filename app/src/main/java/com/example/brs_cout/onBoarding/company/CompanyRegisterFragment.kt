package com.example.brs_cout.onBoarding.company

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import com.example.brs_cout.StartActivity
import com.example.brs_cout.base.BaseFragment
import com.example.brs_cout.databinding.FragmentCompanyBinding
import com.example.brs_cout.models.Company
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class CompanyRegisterFragment : BaseFragment<FragmentCompanyBinding>() {

    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseDatabase.getInstance().getReference("companies")

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentCompanyBinding {
        return FragmentCompanyBinding.inflate(inflater,container,false)
    }

    override fun init() = with(binding) {

        val uid = auth.currentUser?.uid
        val email = auth.currentUser?.email

        registerBtn.setOnClickListener {

            val companyName = binding.companyNameET.text.toString().trim()
            val size = binding.companySizeET.text.toString().trim()
            val address = binding.companyAddressET.text.toString().trim()
            val foundedYearStr = binding.companyFoundedYearET.text.toString().trim()
            val foundedYear = if (foundedYearStr.isNotEmpty()) foundedYearStr.toInt() else null

            val company = Company(
                id = uid.toString(),
                companyName = companyName,
                registrationEmail = email.toString(),
                logoUrl = null,
                description = null,
                industry = null,
                companySize = size,
                address = address,
                foundedYear = foundedYear,
                postedJobIds = emptyList()
            )

            db.child(uid.toString())
                .setValue(company)
                .addOnSuccessListener {
                    val intent = Intent(requireContext(), StartActivity::class.java)
                    startActivity(intent)
                    requireActivity().finish()
                    Toast.makeText(requireContext(), "uploaded", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener {
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }
        }
    }
}