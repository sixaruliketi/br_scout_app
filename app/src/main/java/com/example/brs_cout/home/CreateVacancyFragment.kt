package com.example.brs_cout.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.brs_cout.databinding.FragmentCreateVacancyBinding
import com.example.brs_cout.models.ListItem
import com.example.brs_cout.models.Vacancy
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class CreateVacancyFragment : DialogFragment() {

    private var _binding: FragmentCreateVacancyBinding? = null
    private val binding get() = _binding!!
    private val user = FirebaseAuth.getInstance().currentUser!!
    val dbRef = FirebaseDatabase.getInstance().getReference("companies").child(user.uid).child("vacancies")

    private val selectedTechSkills = mutableSetOf<String>() // No duplicates
    private val selectedSoftSkills = mutableSetOf<String>() // No duplicates
    private val selectedLanguages = mutableSetOf<String>() // No duplicates

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

    private val allLanguages = listOf(
        "English",
        "Georgian",
        "Russian",
        "Spanish",
        "French",
        "German",
        "Chinese",
        "Japanese",
        "Korean",
        "Italian",
        "Portuguese",
        "Arabic",
        "Hindi",
        "Bengali",
        "Turkish",
        "Dutch",
        "Swedish",
        "Norwegian",
        "Finnish",
        "Danish",
        "Polish",
        "Ukrainian",
        "Romanian",
        "Hungarian",
        "Czech",
        "Greek",
        "Hebrew",
        "Vietnamese",
        "Thai",
        "Indonesian",
        "Malay",
        "Persian",
        "Urdu",
        "Swahili",
        "Zulu",
        "Afrikaans",
        "Catalan",
        "Croatian",
        "Slovak",
        "Bulgarian",
        "Serbian",
        "Lithuanian",
        "Latvian",
        "Estonian",
        "Icelandic",
        "Mongolian",
        "Filipino",
        "Tamil",
        "Telugu",
        "Marathi",
        "Gujarati",
        "Kannada",
        "Malayalam",
        "Punjabi",
        "Sinhala",
        "Nepali",
        "Pashto",
        "Kurdish",
        "Armenian",
        "Georgian",
        "Basque",
        "Galician",
        "Luxembourgish",
        "Maltese",
        "Irish",
        "Scottish Gaelic",
        "Welsh",
        "Corsican",
        "Esperanto",
        "Hawaiian",
        "Maori",
        "Samoan",
        "Tahitian",
        "Fijian",
        "Tongan",
        "Yoruba",
        "Igbo",
        "Hausa",
        "Amharic",
        "Somali",
        "Berber",
        "Tibetan",
        "Uyghur",
        "Kazakh",
        "Uzbek",
        "Turkmen",
        "Tajik",
        "Macedonian",
        "Albanian",
        "Bosnian",
        "Montenegrin",
        "Luxembourgish",
        "Frisian"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, android.R.style.Theme_Material_Light_NoActionBar_Fullscreen)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCreateVacancyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()

    }

    private fun init() = with(binding) {

        val uid = user.uid

        setupSkillAdapter(
            autoCompleteTextView = binding.requiredTechnicalSkillsSearchView,
            chipGroup = binding.selectedRequiredTechnicalSkillsChipGroup,
            allSkills = allTechSkills,
            selectedSkills = selectedTechSkills
        )

        setupSkillAdapter(
            autoCompleteTextView = binding.requiredSoftSkillsSearchView,
            chipGroup = binding.selectedRequiredSoftSkillsChipGroup,
            allSkills = allSoftSkills,
            selectedSkills = selectedSoftSkills
        )

        setupSkillAdapter(
            autoCompleteTextView = binding.requiredLanguagesSearchView,
            chipGroup = binding.selectedRequiredLanguagesChipGroup,
            allSkills = allLanguages,
            selectedSkills = selectedLanguages
        )

        postJobBtn.setOnClickListener {

            val jobTitle = vacancyJobTitle.text.toString()
            val jobDescription = jobDescriptionET.text.toString()
            val location = vacancyLocationET.text.toString()
            val deadline = vacancyDeadline.text.toString().trim()
            val requiredTechnicalSkills =
                selectedTechSkills.map { ListItem.SkillItem(it) } // აქ!
            val requiredSoftSkills = selectedSoftSkills.map { ListItem.SkillItem(it) } // აქ!
            val requiredLanguages = selectedLanguages.map { ListItem.SkillItem(it) } // აქ!
            val applicationDeadline = vacancyDeadline.text.toString().trim()

//            val isActive =

            var vacancy = Vacancy(
                id = "", // გენერირდება Firebase-ში push().key-ით
                companyId = uid,
                jobTitle = jobTitle,
                jobDescription = jobDescription,
                location = location,
                requiredTechnicalSkills = requiredTechnicalSkills,
                requiredSoftSkills = requiredSoftSkills,
                requiredLanguages = requiredLanguages,
                applicationDeadline = applicationDeadline,
//                isActive = isActive
            )

            val key = dbRef.push().key ?: return@setOnClickListener

            val vacancyWithId = vacancy.copy(id = key)

            dbRef.child(key)
                .setValue(vacancyWithId)
                .addOnSuccessListener {
                    Toast.makeText(requireContext(), "Vacancy posted successfully!", Toast.LENGTH_SHORT).show()
                    dismiss()
                }
                .addOnFailureListener { e ->
                    Toast.makeText(requireContext(), "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                }
        }
    }

    private fun setupSkillAdapter(
        autoCompleteTextView: AutoCompleteTextView,
        chipGroup: ChipGroup,
        allSkills: List<String>,
        selectedSkills: MutableSet<String>,
        maxCount: Int = 10
    ) {
        val adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_dropdown_item_1line,
            allSkills
        )
        autoCompleteTextView.setAdapter(adapter)

        autoCompleteTextView.setOnItemClickListener { parent, _, position, _ ->
            val selectedSkill = parent.getItemAtPosition(position).toString()

            when {
                selectedSkills.contains(selectedSkill) -> {
                    Toast.makeText(requireContext(), "Already selected", Toast.LENGTH_SHORT).show()
                }

                selectedSkills.size >= maxCount -> {
                    Toast.makeText(requireContext(), "Maximum $maxCount skills", Toast.LENGTH_SHORT)
                        .show()
                }

                else -> {
                    addTechSkillChip(selectedSkill, selectedSkills, chipGroup)
                    selectedSkills.add(selectedSkill)
                }
            }

            autoCompleteTextView.setText("") // Clear input

            // თუ გინდა აქ გააკეთო ცვლილებების დამუშავება (e.g. map to ListItem.SkillItem), შეგიძლია დააბრუნო რაღაც მნიშვნელობაც
            val skillItems = selectedSkills.map { ListItem.SkillItem(id = it) }
            // გამოიყენე ეს list გარე ლოგიკაში საჭიროებისამებრ
        }
    }

    private fun addTechSkillChip(
        skill: String,
        selectedSkills: MutableSet<String>,
        chipGroup: ChipGroup
    ) {
        val chip = Chip(requireContext())
        chip.text = skill
        chip.isCloseIconVisible = true
        chip.setOnCloseIconClickListener {
            selectedSkills.remove(skill)
            chipGroup.removeView(chip)
        }
        chipGroup.addView(chip)
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
