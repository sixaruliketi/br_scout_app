package com.example.brs_cout.onBoarding.candidate

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.brs_cout.R
import com.example.brs_cout.adapters.SkillSelectionAdapter
import com.example.brs_cout.base.BaseFragment
import com.example.brs_cout.databinding.FragmentCandidateSoftSkillsBinding
import com.example.brs_cout.models.ListItem
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import com.google.android.material.chip.Chip

class CandidateSoftSkillsFragment : BaseFragment<FragmentCandidateSoftSkillsBinding>() {

    private val allSoftSkills = listOf(
        "Communication", "Teamwork", "Problem-solving", "Critical thinking", "Time management",
        "Adaptability", "Creativity", "Attention to detail", "Conflict resolution", "Leadership",
        "Emotional intelligence", "Decision-making", "Negotiation", "Self-motivation", "Work ethic",
        "Accountability", "Empathy", "Collaboration", "Stress management", "Flexibility",
        "Interpersonal skills", "Public speaking", "Presentation skills", "Patience", "Active listening",
        "Open-mindedness", "Constructive feedback", "Giving feedback", "Receiving feedback", "Organizational skills",
        "Self-awareness", "Initiative", "Resilience", "Growth mindset", "Goal-setting",
        "Positive attitude", "Curiosity", "Resourcefulness", "Integrity", "Confidence",
        "Persuasion", "Professionalism", "Honesty", "Respectfulness", "Punctuality",
        "Remote collaboration", "Cross-cultural communication", "Team building", "Diplomacy", "Clarity",
        "Crisis management", "Strategic thinking", "Vision", "Mentoring", "Facilitation",
        "Learning agility", "Active engagement", "Multitasking", "Delegation", "Focus",
        "Reliability", "Judgment", "Sense of humor", "Assertiveness", "Initiative-taking",
        "Customer focus", "Stakeholder management", "Process improvement", "Prioritization", "Meeting facilitation",
        "Adaptability to change", "Openness to feedback", "Tolerance for ambiguity", "Result-oriented thinking", "Empowerment",
        "Trust-building", "Conflict management", "Responsiveness", "Storytelling", "Written communication",
        "Collaboration tools proficiency", "Respect for diversity", "Work-life balance", "Proactive learning", "Self-discipline",
        "Ownership", "Ethical mindset", "Self-reflection", "Learning from failure", "Cultural sensitivity",
        "Networking", "Motivating others", "Team alignment", "Continuous improvement", "Personal branding",
        "Being coachable", "Clarity under pressure", "Process-oriented mindset", "Task ownership", "Value-driven thinking"
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

            selectedSkills.joinToString { "," }

            //ატვირთე კანდიდატის soft skills სტრინგად და წამოღებისას გადააკეთე ლისთადკო

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