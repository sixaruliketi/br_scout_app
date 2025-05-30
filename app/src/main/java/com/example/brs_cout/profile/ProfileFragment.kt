package com.example.brs_cout.profile

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.brs_cout.adapters.VacancyAdapter
import com.example.brs_cout.base.BaseFragment
import com.example.brs_cout.databinding.FragmentProfileBinding
import com.example.brs_cout.models.Company
import com.example.brs_cout.models.Vacancy
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ProfileFragment : BaseFragment<FragmentProfileBinding>() {
    private lateinit var adapter: VacancyAdapter
    private val user = FirebaseAuth.getInstance().currentUser!!

    val db = FirebaseDatabase.getInstance().getReference("companies")
    val dbRef = db.child(user.uid).child("vacancies")
    private var isLoading = false
    private var lastKey: String? = null
    private val pageSize = 20

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentProfileBinding {
        return FragmentProfileBinding.inflate(inflater, container, false)
    }

    private fun userProfileInfo() = with(binding){


        profileMinDescription.text = user.uid

        db.child(user.uid).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val userInfo = snapshot.getValue(Company::class.java) ?: return

                Log.d("DEBUG", "Data: ${snapshot.value}")
                profileName.text = userInfo.companyName
                Glide.with(binding.root).load(userInfo.logoUrl).into(profileImage)
                profileBio.text = userInfo.description
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(requireContext(), "error: ${error.message}", Toast.LENGTH_SHORT).show()
            }

        })
    }

    override fun init() {

        userProfileInfo()

        adapter = VacancyAdapter(mutableListOf(),childFragmentManager)
        binding.profileVacancyRV.adapter = adapter
        binding.profileVacancyRV.layoutManager = LinearLayoutManager(requireContext())

        loadVacancies(null)

        binding.profileVacancyRV.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val totalItemCount = layoutManager.itemCount
                val lastVisibleItem = layoutManager.findLastVisibleItemPosition()

                if (!isLoading && lastVisibleItem + 5 >= totalItemCount) {
                    loadVacancies(lastKey)
                }
            }
        })
    }

    private fun loadVacancies(startAfterKey: String?) {
        isLoading = true

        val query = if (startAfterKey == null) {
            dbRef.orderByKey().limitToFirst(pageSize)
        } else {
            dbRef.orderByKey().startAfter(startAfterKey).limitToFirst(pageSize)
        }

        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val newVacancies = mutableListOf<Vacancy>()
                for (child in snapshot.children) {
                    val vacancy = child.getValue(Vacancy::class.java)
                    if (vacancy != null) {
                        newVacancies.add(vacancy)
                    }
                }

                if (newVacancies.isNotEmpty()) {
                    lastKey = snapshot.children.last().key
                    adapter.addVacancies(newVacancies)
                }

                isLoading = false
            }

            override fun onCancelled(error: DatabaseError) {
                isLoading = false
                Toast.makeText(
                    requireContext(),
                    "Failed to load data: ${error.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }
}
