package com.example.brs_cout.onBoarding.candidate

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.brs_cout.R
import com.example.brs_cout.base.BaseFragment
import com.example.brs_cout.databinding.FragmentCandidateTechSkillsBinding
import com.example.brs_cout.models.Candidate
import com.example.brs_cout.models.ListItem
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class CandidateTechSkillsFragment : BaseFragment<FragmentCandidateTechSkillsBinding>() {

    private val db = FirebaseDatabase.getInstance().getReference("candidates")

    private val allTechSkills = listOf("Android", "iOS", "Kotlin", "Swift", "Java", "Jetpack Compose",
        "Flutter", "React Native", "Objective-C", "HTML", "CSS", "JavaScript", "TypeScript", "React",
        "Angular", "Vue.js", "Svelte", "Next.js", "Node.js", "Django", "Flask", "Spring Boot", "Express",
        "Ruby on Rails", "ASP.NET", "Go", "Rust", "Docker", "Kubernetes", "Terraform", "Ansible",
        "Jenkins", "GitLab CI", "CircleCI", "Azure DevOps", "AWS", "GCP", "Azure", "DigitalOcean", "Heroku",
        "Cloudflare", "Firebase", "Netlify", "MySQL", "PostgreSQL", "MongoDB", "Redis", "SQLite",
        "Elasticsearch", "Cassandra", "DynamoDB", "Git", "GitHub", "GitLab", "Bitbucket", "Figma",
        "Jira", "Trello", "Postman", "Swagger", "Unit Testing", "Integration Testing", "Selenium",
        "JUnit", "Mockito", "Cypress", "Appium", "TestNG","Python", "TensorFlow", "PyTorch", "Scikit-learn",
        "Keras", "OpenCV", "NLTK", "Hugging Face", "Pandas", "OAuth", "JWT", "SSL/TLS", "Nginx",
        "Apache", "Wireshark", "VPN", "Firewalls", "ZAP", "Adobe XD")

    private val selectedSkills = mutableSetOf<String>() // No duplicates

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentCandidateTechSkillsBinding {
        return FragmentCandidateTechSkillsBinding.inflate(inflater,container,false)
    }

    override fun init() = with(binding){

        val ref = FirebaseDatabase.getInstance().getReference("candidates")

        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (childSnapshot in snapshot.children) {
                    val id = childSnapshot.key  // ეს არის ID
                    val candidate = childSnapshot.getValue(Candidate::class.java)

                    val adapter = ArrayAdapter(
                        requireContext(),
                        android.R.layout.simple_dropdown_item_1line,
                        allTechSkills
                    )
                    skillSearchView.setAdapter(adapter)
                    skillSearchView.setOnItemClickListener { parent, _, position, _ ->
                        val selectedSkill = parent.getItemAtPosition(position).toString()

                        if (selectedSkills.contains(selectedSkill)) {
                            Toast.makeText(requireContext(), "Already selected", Toast.LENGTH_SHORT)
                                .show()
                        } else if (selectedSkills.size >= 10) {
                            Toast.makeText(
                                requireContext(),
                                "Maximum 10 skills",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            addSkillChip(selectedSkill)
                            selectedSkills.add(selectedSkill)
                        }
                        skillSearchView.setText("") // Clear input

                        val skillItems = selectedSkills.map { skill ->
                            ListItem.SkillItem(id = skill)
                        }

                        db.child(id.toString())
                            .child("technicalSkills")
                            .setValue(skillItems)
                            .addOnSuccessListener {
                                Toast.makeText(
                                    requireContext(),
                                    "Skills uploaded!",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                            .addOnFailureListener {
                                Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT)
                                    .show()
                            }

                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(requireContext(), error.message, Toast.LENGTH_SHORT).show()
            }

        })
        nextBtn.setOnClickListener {
            parentFragmentManager.beginTransaction().replace(R.id.main,CandidateSoftSkillsFragment()).commit()
        }
        goBackToCandidateInfo.setOnClickListener {
            parentFragmentManager.beginTransaction().replace(R.id.main,CandidatePersonalInformationFragment()).commit()
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