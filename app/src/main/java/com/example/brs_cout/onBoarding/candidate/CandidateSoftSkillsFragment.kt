package com.example.brs_cout.onBoarding.candidate

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.brs_cout.R
import com.example.brs_cout.base.BaseFragment
import com.example.brs_cout.databinding.FragmentCandidateSoftSkillsBinding
import com.example.brs_cout.models.ListItem
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import com.google.android.material.chip.Chip
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class CandidateSoftSkillsFragment : BaseFragment<FragmentCandidateSoftSkillsBinding>() {

    private val db = FirebaseDatabase.getInstance().getReference("candidates")
    private val user = FirebaseAuth.getInstance().currentUser!!

    private val allSoftSkills = listOf(
        "Teamwork",
        "Communication",
        "Problem Solving",
        "Adaptability",
        "Time Management",
        "Critical Thinking",
        "Creativity",
        "Attention to Detail",
        "Conflict Resolution",
        "Leadership",
        "Emotional Intelligence",
        "Stress Management",
        "Decision Making",
        "Negotiation",
        "Empathy",
        "Active Listening",
        "Collaboration",
        "Flexibility",
        "Motivation",
        "Positive Attitude",
        "Work Ethic",
        "Self-Motivation",
        "Accountability",
        "Organization",
        "Multitasking",
        "Patience",
        "Open-mindedness",
        "Creativity",
        "Self-confidence",
        "Persuasion",
        "Influence",
        "Mentoring",
        "Coaching",
        "Networking",
        "Public Speaking",
        "Presentation Skills",
        "Conflict Management",
        "Analytical Thinking",
        "Customer Service",
        "Negotiation Skills",
        "Detail Oriented",
        "Goal Setting",
        "Resourcefulness",
        "Self Awareness",
        "Positive Reinforcement",
        "Initiative",
        "Dependability",
        "Tolerance for Ambiguity",
        "Resilience",
        "Cultural Competence",
        "Interpersonal Skills",
        "Collaboration Skills",
        "Listening Skills",
        "Problem Sensitivity",
        "Creativity in Problem Solving",
        "Stress Tolerance",
        "Time Awareness",
        "Influencing Others",
        "Trustworthiness",
        "Self Discipline",
        "Change Management",
        "Conflict Mediation",
        "Diplomacy",
        "Team Building",
        "Innovation",
        "Strategic Thinking",
        "Visionary Thinking",
        "Ethical Judgement",
        "Motivational Skills",
        "Learning Agility",
        "Curiosity",
        "Self Reflection",
        "Adaptability to Change",
        "Cognitive Flexibility",
        "Collaboration Tools Proficiency",
        "Workplace Etiquette",
        "Feedback Acceptance",
        "Stress Reduction Techniques",
        "Cross-functional Teamwork",
        "Remote Work Skills",
        "Digital Communication",
        "Empowerment",
        "Goal Orientation",
        "Humility",
        "Integrity",
        "Listening",
        "Patience",
        "Positive Communication",
        "Social Skills",
        "Tolerance",
        "Willingness to Learn",
        "Time Prioritization",
        "Verbal Communication",
        "Written Communication",
        "Conflict Avoidance",
        "Assertiveness",
        "Emotional Stability",
        "Open Communication",
        "Collaboration",
        "Relationship Building",
        "Self Evaluation",
        "Motivation Techniques",
        "Stress Management Strategies",
        "Change Adaptability"
    )

    private val selectedSkills = mutableSetOf<String>() // No duplicates

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentCandidateSoftSkillsBinding {
        return FragmentCandidateSoftSkillsBinding.inflate(inflater,container,false)
    }
    override fun init() = with(binding){

        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, allSoftSkills)
        skillSearchView.setAdapter(adapter)
        skillSearchView.setOnItemClickListener { parent, _, position, _ ->
            val selectedSkill = parent.getItemAtPosition(position).toString()

            if (selectedSkills.contains(selectedSkill)) {
                Toast.makeText(requireContext(), "Already selected", Toast.LENGTH_SHORT).show()
            } else if (selectedSkills.size >= 10) {
                Toast.makeText(requireContext(), "Maximum 10 skills", Toast.LENGTH_SHORT).show()
            } else {
                addSkillChip(selectedSkill)
                selectedSkills.add(selectedSkill)
            }
            skillSearchView.setText("") // Clear input

            val skillItems = selectedSkills.map { skill ->
                ListItem.SkillItem(id = skill)
            }

            db.child(user.uid)
                .child("softSkills")
                .setValue(skillItems)
                .addOnSuccessListener {
                    Toast.makeText(requireContext(), "Skills uploaded!", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener {
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }
        }

        nextBtn.setOnClickListener {
            parentFragmentManager.beginTransaction().replace(R.id.main,CandidateLanguagesFragment()).commit()
        }
    }

    private fun addSkillChip(skill: String) = with(binding) {
        val chip = Chip(requireContext())
        chip.text = skill
        chip.isCloseIconVisible = true
        chip.setOnCloseIconClickListener {
            selectedSkills.remove(skill)
            selectedSkillsChipGroup.removeView(chip)
        }
        selectedSkillsChipGroup.addView(chip)
    }

    // Optional function if later you want selectedSkills as list
    fun getSelectedSkills(): List<String> = selectedSkills.toList()


}