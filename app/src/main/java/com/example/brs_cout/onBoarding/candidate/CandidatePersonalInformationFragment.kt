package com.example.brs_cout

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import com.example.brs_cout.adapters.SkillSelectionAdapter
import com.example.brs_cout.base.BaseFragment
import com.example.brs_cout.databinding.FragmentCandidatePersonalInformationBinding
import com.example.brs_cout.models.ListItem
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent

class CandidatePersonalInformationFragment : BaseFragment<FragmentCandidatePersonalInformationBinding>() {

    private lateinit var adapter: SkillSelectionAdapter

    // This list holds ALL available skills, with their current selection state
    private val fullSkillList: MutableList<ListItem.SkillItem> = mutableListOf()
    // This tracks how many skills are currently displayed
    private var currentLoadedCount = 0
    private val ITEMS_PER_LOAD = 10 // Number of skills to load each time

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentCandidatePersonalInformationBinding {
        return FragmentCandidatePersonalInformationBinding.inflate(inflater, container, false)
    }

    override fun init() {
        // We'll call the setup logic here, similar to onViewCreated
        setupSkillSelection()
    }

    private fun setupSkillSelection() = with(binding) {
        val layoutManager = FlexboxLayoutManager(requireContext()).apply {
            flexDirection = FlexDirection.ROW
            justifyContent = JustifyContent.FLEX_START
        }
        CandidateTechSkillsRV.layoutManager = layoutManager

        // Initialize adapter with both callbacks: for skill selection and "Show More"
        adapter = SkillSelectionAdapter(
            onSkillSelected = { skill, isChecked ->
                // Find the skill in our fullSkillList and update its selection state
                val index = fullSkillList.indexOfFirst { it.id == skill.id }
                if (index != -1) {
                    fullSkillList[index].isSelected = isChecked
                    // No need to call adapter.submitList() here for simple checkbox updates
                }
            },
            onShowMoreClicked = {
                // When "Show More" is clicked, load more skills and update the RecyclerView
                loadMoreSkills()
                updateRecyclerViewDisplay()
            }
        )
        CandidateTechSkillsRV.adapter = adapter

        // Load all initial skills into 'fullSkillList', then display the first batch
        loadAllPossibleSkills()

        // Setup button click listener
        nextBtn.setOnClickListener {
            val selectedSkills = fullSkillList.filter { it.isSelected }
            displaySelectedSkills(selectedSkills)
            // Here you would typically save 'selectedSkills' to your user's profile
            // e.g., to a database, shared preferences, or send to a server.
        }
    }

    private fun loadAllPossibleSkills() {
        // Simulate loading all possible skills from a data source (e.g., API, database)
        // This 'fullSkillList' should contain ALL skills your user can potentially choose from.
        val skills = listOf(
            ListItem.SkillItem("1", "Kotlin"),
            ListItem.SkillItem("2", "Java"),
            ListItem.SkillItem("3", "Android Development", isSelected = true), // Example pre-selected
            ListItem.SkillItem("4", "UI/UX Design"),
            ListItem.SkillItem("5", "REST APIs"),
            ListItem.SkillItem("6", "Databases (SQL/NoSQL)"),
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
            ListItem.SkillItem("30", "Blockchain Basics")
            // Add many more skills to test pagination
        )
        fullSkillList.addAll(skills)

        // Initialize the count to 0 and then call loadMoreSkills to show the first batch
        currentLoadedCount = 0
        loadMoreSkills() // Loads the first 'ITEMS_PER_LOAD' skills
        updateRecyclerViewDisplay() // Updates the adapter
    }

    private fun loadMoreSkills() {
        val nextLoadAmount = ITEMS_PER_LOAD
        val endIndex = (currentLoadedCount + nextLoadAmount).coerceAtMost(fullSkillList.size)
        // Simply update the count of how many items are now "loaded"
        currentLoadedCount = endIndex
    }

    // This method constructs the list to be shown in RecyclerView and submits it to the adapter
    private fun updateRecyclerViewDisplay() {
        val itemsToDisplayInRv = mutableListOf<ListItem>()

        // Add the currently loaded skill items
        for (i in 0 until currentLoadedCount) {
            itemsToDisplayInRv.add(fullSkillList[i])
        }

        // Check if there are still more skills in the full list that haven't been displayed
        val hasMoreSkillsToLoad = currentLoadedCount < fullSkillList.size

        if (hasMoreSkillsToLoad) {
            // If yes, add the "Show More" button at the end
            itemsToDisplayInRv.add(ListItem.ShowMoreItem)
        }

        // Submit the newly constructed list to the ListAdapter
        adapter.submitList(itemsToDisplayInRv.toList())
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