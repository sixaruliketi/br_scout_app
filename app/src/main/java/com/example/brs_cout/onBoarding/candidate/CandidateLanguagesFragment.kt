package com.example.brs_cout.onBoarding.candidate

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.brs_cout.R
import com.example.brs_cout.StartActivity
import com.example.brs_cout.adapters.SkillSelectionAdapter
import com.example.brs_cout.base.BaseFragment
import com.example.brs_cout.databinding.FragmentCandidateLanguagesBinding
import com.example.brs_cout.models.ListItem
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent

class CandidateLanguagesFragment : BaseFragment<FragmentCandidateLanguagesBinding>() {

    val allLanguages = listOf(
        ListItem.SkillItem("1", "ქართული"),
        ListItem.SkillItem("2", "ინგლისური"),
        ListItem.SkillItem("3", "რუსული"),
        ListItem.SkillItem("4", "გერმანული"),
        ListItem.SkillItem("5", "ფრანგული"),
        ListItem.SkillItem("6", "ესპანური"),
        ListItem.SkillItem("7", "იტალიური"),
        ListItem.SkillItem("8", "ჩინური (მანდარინი)"),
        ListItem.SkillItem("9", "იაპონური"),
        ListItem.SkillItem("10", "არაბული"),
        ListItem.SkillItem("11", "პორტუგალიური"),
        ListItem.SkillItem("12", "ჰინდი"),
        ListItem.SkillItem("13", "ბენგალური"),
        ListItem.SkillItem("14", "ურდუ"),
        ListItem.SkillItem("15", "ინდონეზიური"),
        ListItem.SkillItem("16", "სუაჰილი"),
        ListItem.SkillItem("17", "კორეული"),
        ListItem.SkillItem("18", "თურქული"),
        ListItem.SkillItem("19", "ჰოლანდიური"),
        ListItem.SkillItem("20", "პოლონური"),
        ListItem.SkillItem("21", "უკრაინული"),
        ListItem.SkillItem("22", "რუმინული"),
        ListItem.SkillItem("23", "ბერძნული"),
        ListItem.SkillItem("24", "ჩეხური"),
        ListItem.SkillItem("25", "უნგრული"),
        ListItem.SkillItem("26", "შვედური"),
        ListItem.SkillItem("27", "ნორვეგიული"),
        ListItem.SkillItem("28", "დანიური"),
        ListItem.SkillItem("29", "ფინური"),
        ListItem.SkillItem("30", "სომხური"),
        ListItem.SkillItem("31", "აზერბაიჯანული"),
        ListItem.SkillItem("32", "ყაზახური"),
        ListItem.SkillItem("33", "უზბეკური"),
        ListItem.SkillItem("34", "თურქმენული"),
        ListItem.SkillItem("35", "ტაჯიკური"),
        ListItem.SkillItem("36", "ყირგიზული"),
        ListItem.SkillItem("37", "ებრაული"),
        ListItem.SkillItem("38", "სპარსული (ფარსი)"),
        ListItem.SkillItem("39", "ვიეტნამური"),
        ListItem.SkillItem("40", "ტაილანდური"),
        ListItem.SkillItem("41", "მალაიური"),
        ListItem.SkillItem("42", "ფილიპინური (ტაგალოგი)"),
        ListItem.SkillItem("43", "ბირმული"),
        ListItem.SkillItem("44", "ქართული (მეგრული)"), // რეგიონალური ენები
        ListItem.SkillItem("45", "ქართული (სვანური)"),
        ListItem.SkillItem("46", "ქართული (აჭარული დიალექტი)"), // დიალექტები
        ListItem.SkillItem("47", "ბულგარული"),
        ListItem.SkillItem("48", "სერბული"),
        ListItem.SkillItem("49", "ხორვატიული"),
        ListItem.SkillItem("50", "სლოვენური"),
        ListItem.SkillItem("51", "მაკედონიური"),
        ListItem.SkillItem("52", "ალბანური"),
        ListItem.SkillItem("53", "ლიეტუვური"),
        ListItem.SkillItem("54", "ლატვიური"),
        ListItem.SkillItem("55", "ესტონური"),
        ListItem.SkillItem("56", "ისლანდიური"),
        ListItem.SkillItem("57", "ირლანდიური (გელური)"),
        ListItem.SkillItem("58", "უელსური"),
        ListItem.SkillItem("59", "შოტლანდიური (გელური)"),
        ListItem.SkillItem("60", "ბასკური"),
        ListItem.SkillItem("61", "კატალანური"),
        ListItem.SkillItem("62", "გალიციური"),
        ListItem.SkillItem("63", "ჩეჩნური"),
        ListItem.SkillItem("64", "დაღესტნური ენები (ზოგადი)"),
        ListItem.SkillItem("65", "ოსური"),
        ListItem.SkillItem("66", "აბხაზური"),
        ListItem.SkillItem("67", "ბელორუსული"),
        ListItem.SkillItem("68", "მოლდავური"),
        ListItem.SkillItem("69", "ლათინური"),
        ListItem.SkillItem("70", "ძველბერძნული"),
        ListItem.SkillItem("71", "სანსკრიტი"),
        ListItem.SkillItem("72", "ეგვიპტური (კოპტური)"),
        ListItem.SkillItem("73", "სუდანური"),
        ListItem.SkillItem("74", "ნეპალური"),
        ListItem.SkillItem("75", "სინჰალური"),
        ListItem.SkillItem("76", "ქართული (ლაზური)"),
        ListItem.SkillItem("77", "ფრიზიული"),
        ListItem.SkillItem("78", "ფლამანდური"),
        ListItem.SkillItem("79", "ლუქსემბურგული"),
        ListItem.SkillItem("80", "მალტური"),
        ListItem.SkillItem("81", "სლოვენური"),
        ListItem.SkillItem("82", "კოსოვური (ალბანური დიალექტი)"),
        ListItem.SkillItem("83", "ბოსნიური"),
        ListItem.SkillItem("84", "მონტენეგრული"),
        ListItem.SkillItem("85", "ტამილური"),
        ListItem.SkillItem("86", "ტელუგუ"),
        ListItem.SkillItem("87", "კანადა"),
        ListItem.SkillItem("88", "მალაიალამური"),
        ListItem.SkillItem("89", "გუჯარათი"),
        ListItem.SkillItem("90", "მარათჰი"),
        ListItem.SkillItem("91", "პენჯაბური"),
        ListItem.SkillItem("92", "ბაშკირული"),
        ListItem.SkillItem("93", "თათრული"),
        ListItem.SkillItem("94", "ჩუვაშური"),
        ListItem.SkillItem("95", "უდმურტული"),
        ListItem.SkillItem("96", "მორდვინული"),
        ListItem.SkillItem("97", "კომი"),
        ListItem.SkillItem("98", "ადიღეური"),
        ListItem.SkillItem("99", "ყაბარდოული"),
        ListItem.SkillItem("100", "ჩრდილოეთ კავკასიური ენები (ზოგადი)")
    )

    private lateinit var adapter: SkillSelectionAdapter

    private val fullSkillList: MutableList<ListItem.SkillItem> = mutableListOf()

    // This tracks how many skills are currently displayed
    private var currentLoadedCount = 0
    private val ITEMS_PER_LOAD = 10 // Number of skills to load each time

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentCandidateLanguagesBinding {
        return FragmentCandidateLanguagesBinding.inflate(inflater,container,false)
    }

    override fun init() {
        setupSkillSelection()
    }

    private fun setupSkillSelection() = with(binding){
        techSkillsRVInit()
        nextBtn.setOnClickListener {
            val selectedSkills = fullSkillList.filter { it.isSelected }
//            displaySelectedSkills(selectedSkills) // TODO("ამისი ფუნქცია ქვემოთ წერია და შესაცვლელია")

            parentFragmentManager.beginTransaction().replace(R.id.main,CandidatePersonalInformationFragment()).commit()

            // Here you would typically save 'selectedSkills' to your user's profile
            // e.g., to a database, shared preferences, or send to a server.
        }
    }

    private fun techSkillsRVInit() = with(binding){
        skillsRVInit(candidateLanguagesRV,allLanguages)

        loadAllPossibleSkills(allLanguages)
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