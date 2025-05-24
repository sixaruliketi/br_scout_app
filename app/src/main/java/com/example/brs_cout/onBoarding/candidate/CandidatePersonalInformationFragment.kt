package com.example.brs_cout.onBoarding.candidate

import android.graphics.pdf.models.ListItem
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.brs_cout.R
import com.example.brs_cout.adapters.SkillSelectionAdapter
import com.example.brs_cout.base.BaseFragment
import com.example.brs_cout.databinding.FragmentCandidatePersonalInformationBinding
import com.example.brs_cout.models.SkillItem
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent

class CandidatePersonalInformationFragment : BaseFragment<FragmentCandidatePersonalInformationBinding>() {

    private lateinit var adapter : SkillSelectionAdapter

    private val allSkills: MutableList<SkillItem> = mutableListOf()

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentCandidatePersonalInformationBinding {
        return FragmentCandidatePersonalInformationBinding.inflate(inflater,container,false)
    }

    override fun init() {
        techSkillsRVInit()
    }

    private fun techSkillsRVInit() = with(binding){
        val layoutManager = FlexboxLayoutManager(requireContext()).apply {
            flexDirection = FlexDirection.ROW // Item-ები გვერდიგვერდ, შემდეგ ქვემოთ
            justifyContent = JustifyContent.FLEX_START // Item-ები იწყება მარცხნიდან

        }
        CandidateTechSkillsRV.layoutManager = layoutManager

        adapter = SkillSelectionAdapter { skill, isChecked ->
            val index = allSkills.indexOfFirst { it.id == skill.id }
            if (index != -1) {
                allSkills[index].isSelected = isChecked
            }
        }
        CandidateTechSkillsRV.adapter = adapter

        loadInitialSkills()

        nextBtn.setOnClickListener {
            val selectedSkills = allSkills.filter { it.isSelected }
            displaySelectedSkills(selectedSkills)
            // Here you would typically save 'selectedSkills' to your user's profile
            // e.g., to a database, shared preferences, or send to a server.
        }

    }
    private fun loadInitialSkills() {
        // Simulate loading skills from a data source
        val skills = listOf(
            SkillItem("1", "Kotlin"),
            SkillItem("2", "Java"),
            SkillItem("3", "Android Development", isSelected = true), // Pre-selected example
            SkillItem("4", "UI/UX Design"),
            SkillItem("5", "REST APIs"),
            SkillItem("6", "Databases (SQL/NoSQL)"),
            SkillItem("7", "Version Control (Git)"),
            SkillItem("8", "Problem Solving"),
            SkillItem("9", "Communication"),
            SkillItem("10", "Project Management"),
            SkillItem("11", "Testing (Unit/Integration)")
        )
        allSkills.addAll(skills)
        adapter.submitList(allSkills.toList()) // Submit a copy to ListAdapter
    }

    private fun displaySelectedSkills(selectedSkills: List<SkillItem>) {
        if (selectedSkills.isEmpty()) {
            Toast.makeText(requireContext(), "No skills selected.", Toast.LENGTH_SHORT).show()
            return
        }
        val skillNames = selectedSkills.joinToString("\n") { it.name }
        Toast.makeText(requireContext(), "Selected Skills:\n$skillNames", Toast.LENGTH_LONG).show()
        // In a real app, you might navigate to a profile screen and pass this list
    }
}