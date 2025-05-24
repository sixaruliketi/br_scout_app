package com.example.brs_cout.onBoarding.candidate

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.brs_cout.R
import com.example.brs_cout.adapters.SkillSelectionAdapter
import com.example.brs_cout.base.BaseFragment
import com.example.brs_cout.databinding.FragmentCandidateTechSkillsBinding
import com.example.brs_cout.models.ListItem
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent

class CandidateTechSkillsFragment : BaseFragment<FragmentCandidateTechSkillsBinding>() {

    val techSkills = listOf(
        ListItem.SkillItem("1", "Kotlin"),
        ListItem.SkillItem("2", "Java"),
        ListItem.SkillItem("3", "Android Development", isSelected = true),
        ListItem.SkillItem("4", "UI/UX Design"),
        ListItem.SkillItem("5", "REST APIs"),
        ListItem.SkillItem("6", "Databases (SQL/NoSQL)", isSelected = true),
        ListItem.SkillItem("7", "Version Control (Git)"),
        ListItem.SkillItem("8", "Problem Solving"),
        ListItem.SkillItem("9", "Communication"),
        ListItem.SkillItem("10", "Project Management"),
        ListItem.SkillItem("11", "Testing (Unit/Integration)"),
        ListItem.SkillItem("12", "DevOps"),
        ListItem.SkillItem("13", "Cloud Computing (AWS/Azure/GCP)"),
        ListItem.SkillItem("14", "Machine Learning Fundamentals"),
        ListItem.SkillItem("15", "Data Structures & Algorithms"),
        ListItem.SkillItem("16", "Agile Methodologies"),
        ListItem.SkillItem("17", "Public Speaking"),
        ListItem.SkillItem("18", "Leadership"),
        ListItem.SkillItem("19", "Critical Thinking"),
        ListItem.SkillItem("20", "Cybersecurity Basics"),
        ListItem.SkillItem("21", "Web Development (Frontend)"),
        ListItem.SkillItem("22", "Web Development (Backend)"),
        ListItem.SkillItem("23", "Mobile UI Automation"),
        ListItem.SkillItem("24", "Networking Fundamentals"),
        ListItem.SkillItem("25", "Technical Writing"),
        ListItem.SkillItem("26", "Conflict Resolution"),
        ListItem.SkillItem("27", "Time Management"),
        ListItem.SkillItem("28", "Customer Service"),
        ListItem.SkillItem("29", "Mentoring"),
        ListItem.SkillItem("30", "Blockchain Basics"),
        ListItem.SkillItem("31", "Data Analysis"),
        ListItem.SkillItem("32", "Data Visualization"),
        ListItem.SkillItem("33", "Big Data Technologies (Hadoop/Spark)"),
        ListItem.SkillItem("34", "ETL Processes"),
        ListItem.SkillItem("35", "Data Modeling"),
        ListItem.SkillItem("36", "Statistical Analysis"),
        ListItem.SkillItem("37", "Predictive Modeling"),
        ListItem.SkillItem("38", "NLP (Natural Language Processing)"),
        ListItem.SkillItem("39", "Computer Vision"),
        ListItem.SkillItem("40", "Reinforcement Learning"),
        ListItem.SkillItem("41", "Deep Learning Frameworks (TensorFlow/PyTorch)"),
        ListItem.SkillItem("42", "Model Deployment"),
        ListItem.SkillItem("43", "A/B Testing"),
        ListItem.SkillItem("44", "Experiment Design"),
        ListItem.SkillItem("45", "Containerization (Docker)"),
        ListItem.SkillItem("46", "Orchestration (Kubernetes)"),
        ListItem.SkillItem("47", "CI/CD Pipelines (Jenkins/GitLab CI)"),
        ListItem.SkillItem("48", "Infrastructure as Code (Terraform/Ansible)"),
        ListItem.SkillItem("49", "Monitoring & Logging (Prometheus/Grafana)"),
        ListItem.SkillItem("50", "Scripting (Bash/PowerShell)"),
        ListItem.SkillItem("51", "Virtualization (VMware/VirtualBox)"),
        ListItem.SkillItem("52", "Linux Administration"),
        ListItem.SkillItem("53", "Windows Server Administration"),
        ListItem.SkillItem("54", "Network Security"),
        ListItem.SkillItem("55", "Penetration Testing"),
        ListItem.SkillItem("56", "Incident Response"),
        ListItem.SkillItem("57", "Security Auditing"),
        ListItem.SkillItem("58", "Cryptography"),
        ListItem.SkillItem("59", "Firewall Configuration"),
        ListItem.SkillItem("60", "Identity & Access Management (IAM)"),
        ListItem.SkillItem("61", "Frontend Frameworks (General)"),
        ListItem.SkillItem("62", "Backend Frameworks (General)"),
        ListItem.SkillItem("63", "Database Administration"),
        ListItem.SkillItem("64", "System Architecture"),
        ListItem.SkillItem("65", "Microservices"),
        ListItem.SkillItem("66", "Serverless Architecture"),
        ListItem.SkillItem("67", "Message Queues (Kafka/RabbitMQ)"),
        ListItem.SkillItem("68", "Caching Strategies"),
        ListItem.SkillItem("69", "Load Balancing"),
        ListItem.SkillItem("70", "Distributed Systems"),
        ListItem.SkillItem("71", "Unit Testing"),
        ListItem.SkillItem("72", "Integration Testing"),
        ListItem.SkillItem("73", "End-to-End Testing"),
        ListItem.SkillItem("74", "Test Automation"),
        ListItem.SkillItem("75", "Performance Testing"),
        ListItem.SkillItem("76", "Security Testing"),
        ListItem.SkillItem("77", "Test Case Design"),
        ListItem.SkillItem("78", "Bug Tracking"),
        ListItem.SkillItem("79", "API Testing"),
        ListItem.SkillItem("80", "Mobile Test Automation (Appium/Espresso)"),
        ListItem.SkillItem("81", "Graphic Design Tools (Adobe Photoshop/Illustrator)"),
        ListItem.SkillItem("82", "Video Editing (Adobe Premiere/DaVinci Resolve)"),
        ListItem.SkillItem("83", "3D Modeling (Blender/Maya)"),
        ListItem.SkillItem("84", "Animation"),
        ListItem.SkillItem("85", "Game Development (Unity/Unreal Engine)"),
        ListItem.SkillItem("86", "VR/AR Development"),
        ListItem.SkillItem("87", "Sound Design"),
        ListItem.SkillItem("88", "Technical Support"),
        ListItem.SkillItem("89", "Customer Relationship Management (CRM)"),
        ListItem.SkillItem("90", "Enterprise Resource Planning (ERP)"),
        ListItem.SkillItem("91", "Business Intelligence (BI)"),
        ListItem.SkillItem("92", "Project Management Software (Jira/Asana)"),
        ListItem.SkillItem("93", "Scrum Master"),
        ListItem.SkillItem("94", "Product Ownership"),
        ListItem.SkillItem("95", "Requirements Gathering"),
        ListItem.SkillItem("96", "System Analysis"),
        ListItem.SkillItem("97", "Technical Sales"),
        ListItem.SkillItem("98", "Cloud Security"),
        ListItem.SkillItem("99", "Edge Computing"),
        ListItem.SkillItem("100", "Quantum Computing Fundamentals")
    )

    private lateinit var adapter: SkillSelectionAdapter

    private val fullSkillList: MutableList<ListItem.SkillItem> = mutableListOf()

    // This tracks how many skills are currently displayed
    private var currentLoadedCount = 0
    private val ITEMS_PER_LOAD = 10 // Number of skills to load each time

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentCandidateTechSkillsBinding {
        return FragmentCandidateTechSkillsBinding.inflate(inflater,container,false)
    }

    override fun init() {
        setupSkillSelection()
    }

    private fun setupSkillSelection() = with(binding){
        techSkillsRVInit()
        nextBtn.setOnClickListener {
            val selectedSkills = fullSkillList.filter { it.isSelected }
//            displaySelectedSkills(selectedSkills) // TODO("ამისი ფუნქცია ქვემოთ წერია და შესაცვლელია")

            parentFragmentManager.beginTransaction().replace(
                R.id.main,
                CandidateSoftSkillsFragment()
            ).commit()

            // Here you would typically save 'selectedSkills' to your user's profile
            // e.g., to a database, shared preferences, or send to a server.
        }
    }

    private fun techSkillsRVInit() = with(binding){
        skillsRVInit(candidateTechSkillsRV,techSkills)

        loadAllPossibleSkills(techSkills)
    }

    private fun loadAllPossibleSkills(list: List<ListItem.SkillItem>) {
        fullSkillList.addAll(list)

        // Initialize the count to 0 and then call loadMoreSkills to show the first batch
        currentLoadedCount = 0
        loadMoreSkills(list) // Loads the first 'ITEMS_PER_LOAD' skills
        updateRecyclerViewDisplay(list) // Updates the adapter
    }
    private fun updateRecyclerViewDisplay(list: List<ListItem.SkillItem>) {
        val itemsToDisplayInRv = mutableListOf<ListItem>()

        // Add the currently loaded skill items
        for (i in 0 until currentLoadedCount) {
            itemsToDisplayInRv.add(list[i])
        }

        // Check if there are still more skills in the full list that haven't been displayed
        val hasMoreSkillsToLoad = currentLoadedCount < list.size

        if (hasMoreSkillsToLoad) {
            // If yes, add the "Show More" button at the end
            itemsToDisplayInRv.add(ListItem.ShowMoreItem)
        }

        // Submit the newly constructed list to the ListAdapter
        adapter.submitList(itemsToDisplayInRv.toList())
    }

    private fun loadMoreSkills(list: List<ListItem.SkillItem>) {
        val nextLoadAmount = ITEMS_PER_LOAD
        val endIndex = (currentLoadedCount + nextLoadAmount).coerceAtMost(list.size)
        // Simply update the count of how many items are now "loaded"
        currentLoadedCount = endIndex
    }

    private fun skillsRVInit(rv: RecyclerView, list: List<ListItem.SkillItem>){
        val layoutManager = FlexboxLayoutManager(requireContext()).apply {
            flexDirection = FlexDirection.ROW
            justifyContent = JustifyContent.FLEX_START
        }
        rv.layoutManager = layoutManager

        // Initialize adapter with both callbacks: for skill selection and "Show More"
        adapter = SkillSelectionAdapter(
            onSkillSelected = { skill, isChecked ->
                // Find the skill in our fullSkillList and update its selection state
                val index = list.indexOfFirst { it.id == skill.id }
                if (index != -1) {
                    fullSkillList[index].isSelected = isChecked
                    // No need to call adapter.submitList() here for simple checkbox updates
                }
            },
            onShowMoreClicked = {
                // When "Show More" is clicked, load more skills and update the RecyclerView
                loadMoreSkills(list)
                updateRecyclerViewDisplay(list)
            }
        )
        rv.adapter = adapter

    }


    private fun displaySelectedSkills(selectedSkills: List<ListItem.SkillItem>) {
        if (selectedSkills.isEmpty()) {
            Toast.makeText(requireContext(), "No skills selected.", Toast.LENGTH_SHORT).show()
            return
        }
        val skillNames = selectedSkills.joinToString("\n") { it.name }
        Toast.makeText(requireContext(), "Selected Skills:\n$skillNames", Toast.LENGTH_LONG).show()
        // In a real app, you might navigate to a profile screen and pass this list
    }
}