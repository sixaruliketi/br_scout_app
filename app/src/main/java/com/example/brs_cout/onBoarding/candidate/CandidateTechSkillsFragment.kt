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
        "Android",
        "Kotlin",
        "Java",
        "Jetpack Compose",
        "Flutter",
        "Git",
        "REST API",
        "SQL",
        "Firebase",
        "Docker",
        "Kubernetes",
        "AWS",
        "CI/CD",
        "Unit Testing",
        "Agile",
        "Maven",
        "Gradle",
        "JUnit",
        "Mockito",
        "Coroutines",
        "RxJava",
        "HTML",
        "CSS",
        "JavaScript",
        "TypeScript",
        "React",
        "Node.js",
        "Spring Boot",
        "Hibernate",
        "Microservices",
        "GraphQL",
        "NoSQL",
        "MongoDB",
        "Redis",
        "ElasticSearch",
        "Apache Kafka",
        "Apache Spark",
        "Big Data",
        "TensorFlow",
        "PyTorch",
        "Machine Learning",
        "Data Science",
        "Linux",
        "Shell Scripting",
        "Bash",
        "PowerShell",
        "Ansible",
        "Terraform",
        "Prometheus",
        "Grafana",
        "Jenkins",
        "CircleCI",
        "Bitbucket",
        "GitHub",
        "GitLab",
        "Azure",
        "Google Cloud Platform",
        "Firebase Cloud Messaging",
        "OAuth",
        "OpenID Connect",
        "JSON",
        "XML",
        "SOAP",
        "WebSockets",
        "RESTful Services",
        "Android NDK",
        "C++",
        "C#",
        "Python",
        "Dart",
        "Scala",
        "Go",
        "Ruby",
        "PHP",
        "Laravel",
        "Symfony",
        "WordPress",
        "Drupal",
        "Magento",
        "Salesforce",
        "SAP",
        "Oracle Database",
        "MySQL",
        "PostgreSQL",
        "Apache Tomcat",
        "Nginx",
        "Load Balancing",
        "Caching",
        "OAuth2",
        "JWT",
        "Mobile UI/UX Design",
        "Firebase Realtime Database",
        "Firestore",
        "Appium",
        "Selenium",
        "Cucumber",
        "TestNG",
        "SonarQube",
        "Code Review",
        "Pair Programming",
        "Debugging",
        "Profiling",
        "Accessibility",
        "Localization",
        "Continuous Deployment"
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