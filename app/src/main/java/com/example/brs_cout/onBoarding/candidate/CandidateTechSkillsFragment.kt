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
import com.google.firebase.database.FirebaseDatabase

class CandidateTechSkillsFragment : BaseFragment<FragmentCandidateTechSkillsBinding>() {

    private val db = FirebaseDatabase.getInstance().getReference("candidates")
    private val user = FirebaseAuth.getInstance().currentUser!!

    private val allTechSkills = listOf(
        "Android", "Kotlin", "Java", "Jetpack Compose", "Firebase", "Room",
        "XML", "Retrofit", "Hilt", "Dagger", "Coroutines", "LiveData", "ViewModel", "Navigation Component",
        "JavaScript", "TypeScript", "React", "React Native", "Next.js", "Angular", "Vue.js",
        "HTML", "CSS", "SASS", "Bootstrap", "Tailwind CSS",
        "Node.js", "Express.js", "NestJS", "Spring Boot", "Flask", "Django",
        "SQL", "PostgreSQL", "MySQL", "SQLite", "MongoDB", "Redis",
        "Git", "GitHub", "GitLab", "CI/CD", "Docker", "Kubernetes",
        "Jenkins", "CircleCI", "AWS", "Azure", "Google Cloud", "Firebase Hosting",
        "REST API", "GraphQL", "gRPC", "WebSockets",
        "Python", "Pandas", "NumPy", "Scikit-learn", "TensorFlow", "PyTorch", "OpenCV", "Keras",
        "Data Analysis", "Data Visualization", "Matplotlib", "Seaborn", "Power BI", "Tableau",
        "Figma", "Adobe XD", "UI/UX Design", "Wireframing", "Prototyping",
        "Agile", "Scrum", "Jira", "Confluence", "Trello",
        "C#", ".NET", "Unity", "C++", "Rust", "Go", "Ruby", "Rails",
        "Linux", "Bash", "Nginx", "Apache", "SSL", "OAuth2",
        "Cybersecurity", "Penetration Testing", "OWASP", "JWT", "Encryption",
        "Mobile Testing", "JUnit", "Mockito", "Espresso", "Selenium", "Appium",
        "Clean Architecture", "MVVM", "MVP", "SOLID Principles",
        "Design Patterns", "System Design", "Software Architecture"
    )

    private val selectedSkills = mutableSetOf<String>() // No duplicates

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentCandidateTechSkillsBinding {
        return FragmentCandidateTechSkillsBinding.inflate(inflater,container,false)
    }

    override fun init() = with(binding){
//        setupSkillSelection()

        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, allTechSkills)
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
                .child("technicalSkills")
                .setValue(skillItems)
                .addOnSuccessListener {
                    Toast.makeText(requireContext(), "Skills uploaded!", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener {
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }

        }

        nextBtn.setOnClickListener {
            parentFragmentManager.beginTransaction().replace(R.id.main,CandidateSoftSkillsFragment()).commit()
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