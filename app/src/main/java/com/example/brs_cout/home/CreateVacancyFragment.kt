package com.example.brs_cout.home

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.brs_cout.base.BaseFragment
import com.example.brs_cout.databinding.FragmentCreateVacancyBinding
import com.example.brs_cout.models.ListItem
import com.example.brs_cout.models.Vacancy
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import java.sql.Time
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

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
        "Firebase",
        "Room",
        "XML",
        "Retrofit",
        "Hilt",
        "Dagger",
        "Coroutines",
        "LiveData",
        "ViewModel",
        "Navigation Component",
        "JavaScript",
        "TypeScript",
        "React",
        "React Native",
        "Next.js",
        "Angular",
        "Vue.js",
        "HTML",
        "CSS",
        "SASS",
        "Bootstrap",
        "Tailwind CSS",
        "Node.js",
        "Express.js",
        "NestJS",
        "Spring Boot",
        "Flask",
        "Django",
        "SQL",
        "PostgreSQL",
        "MySQL",
        "SQLite",
        "MongoDB",
        "Redis",
        "Git",
        "GitHub",
        "GitLab",
        "CI/CD",
        "Docker",
        "Kubernetes",
        "Jenkins",
        "CircleCI",
        "AWS",
        "Azure",
        "Google Cloud",
        "Firebase Hosting",
        "REST API",
        "GraphQL",
        "gRPC",
        "WebSockets",
        "Python",
        "Pandas",
        "NumPy",
        "Scikit-learn",
        "TensorFlow",
        "PyTorch",
        "OpenCV",
        "Keras",
        "Data Analysis",
        "Data Visualization",
        "Matplotlib",
        "Seaborn",
        "Power BI",
        "Tableau",
        "Figma",
        "Adobe XD",
        "UI/UX Design",
        "Wireframing",
        "Prototyping",
        "Agile",
        "Scrum",
        "Jira",
        "Confluence",
        "Trello",
        "C#",
        ".NET",
        "Unity",
        "C++",
        "Rust",
        "Go",
        "Ruby",
        "Rails",
        "Linux",
        "Bash",
        "Nginx",
        "Apache",
        "SSL",
        "OAuth2",
        "Cybersecurity",
        "Penetration Testing",
        "OWASP",
        "JWT",
        "Encryption",
        "Mobile Testing",
        "JUnit",
        "Mockito",
        "Espresso",
        "Selenium",
        "Appium",
        "Clean Architecture",
        "MVVM",
        "MVP",
        "SOLID Principles",
        "Design Patterns",
        "System Design",
        "Software Architecture"
    )
    private val allSoftSkills = listOf(
        "Communication",
        "Teamwork",
        "Problem-solving",
        "Critical thinking",
        "Time management",
        "Adaptability",
        "Creativity",
        "Attention to detail",
        "Conflict resolution",
        "Leadership",
        "Emotional intelligence",
        "Decision-making",
        "Negotiation",
        "Self-motivation",
        "Work ethic",
        "Accountability",
        "Empathy",
        "Collaboration",
        "Stress management",
        "Flexibility",
        "Interpersonal skills",
        "Public speaking",
        "Presentation skills",
        "Patience",
        "Active listening",
        "Open-mindedness",
        "Constructive feedback",
        "Giving feedback",
        "Receiving feedback",
        "Organizational skills",
        "Self-awareness",
        "Initiative",
        "Resilience",
        "Growth mindset",
        "Goal-setting",
        "Positive attitude",
        "Curiosity",
        "Resourcefulness",
        "Integrity",
        "Confidence",
        "Persuasion",
        "Professionalism",
        "Honesty",
        "Respectfulness",
        "Punctuality",
        "Remote collaboration",
        "Cross-cultural communication",
        "Team building",
        "Diplomacy",
        "Clarity",
        "Crisis management",
        "Strategic thinking",
        "Vision",
        "Mentoring",
        "Facilitation",
        "Learning agility",
        "Active engagement",
        "Multitasking",
        "Delegation",
        "Focus",
        "Reliability",
        "Judgment",
        "Sense of humor",
        "Assertiveness",
        "Initiative-taking",
        "Customer focus",
        "Stakeholder management",
        "Process improvement",
        "Prioritization",
        "Meeting facilitation",
        "Adaptability to change",
        "Openness to feedback",
        "Tolerance for ambiguity",
        "Result-oriented thinking",
        "Empowerment",
        "Trust-building",
        "Conflict management",
        "Responsiveness",
        "Storytelling",
        "Written communication",
        "Collaboration tools proficiency",
        "Respect for diversity",
        "Work-life balance",
        "Proactive learning",
        "Self-discipline",
        "Ownership",
        "Ethical mindset",
        "Self-reflection",
        "Learning from failure",
        "Cultural sensitivity",
        "Networking",
        "Motivating others",
        "Team alignment",
        "Continuous improvement",
        "Personal branding",
        "Being coachable",
        "Clarity under pressure",
        "Process-oriented mindset",
        "Task ownership",
        "Value-driven thinking"
    )
    private val allLanguages = listOf(
        "English", "Mandarin Chinese", "Hindi", "Spanish", "French",
        "Arabic", "Bengali", "Russian", "Portuguese", "Urdu",
        "Indonesian", "German", "Japanese", "Swahili", "Marathi",
        "Telugu", "Turkish", "Korean", "Vietnamese", "Tamil",
        "Italian", "Persian (Farsi)", "Polish", "Ukrainian", "Dutch",
        "Romanian", "Thai", "Gujarati", "Czech", "Greek",
        "Hungarian", "Hebrew", "Kannada", "Malayalam", "Punjabi",
        "Serbian", "Swedish", "Bulgarian", "Finnish", "Slovak",
        "Norwegian", "Lithuanian", "Latvian", "Estonian", "Croatian",
        "Bosnian", "Slovenian", "Armenian", "Georgian", "Azerbaijani",
        "Albanian", "Macedonian", "Malay", "Pashto", "Sinhala",
        "Khmer", "Lao", "Nepali", "Tagalog (Filipino)", "Burmese",
        "Amharic", "Tigrinya", "Somali", "Yoruba", "Hausa",
        "Zulu", "Xhosa", "Igbo", "Kurdish", "Mongolian",
        "Tajik", "Uzbek", "Kazakh", "Turkmen", "Dari",
        "Basque", "Catalan", "Galician", "Welsh", "Irish",
        "Scottish Gaelic", "Icelandic", "Esperanto", "Haitian Creole", "Luxembourgish",
        "Sundanese", "Javanese", "Maori", "Fijian", "Samoan",
        "Tongan", "Tswana", "Chewa", "Bambara", "Quechua",
        "Aymara", "Inuktitut", "Ojibwe", "Guarani", "Hmong"
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
