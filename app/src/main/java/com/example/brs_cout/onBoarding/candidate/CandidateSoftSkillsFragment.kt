package com.example.brs_cout.onBoarding.candidate

import android.view.LayoutInflater
import android.view.ViewGroup
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

class CandidateSoftSkillsFragment : BaseFragment<FragmentCandidateSoftSkillsBinding>() {

    val softSkills = listOf(
        ListItem.SkillItem("1", "აქტიური მოსმენა"),
        ListItem.SkillItem("2", "ეფექტური ზეპირი კომუნიკაცია"),
        ListItem.SkillItem("3", "წერილობითი კომუნიკაცია"),
        ListItem.SkillItem("4", "არავერბალური კომუნიკაცია"),
        ListItem.SkillItem("5", "პრეზენტაციის უნარები"),
        ListItem.SkillItem("6", "უკუკავშირის მიცემა/მიღება"),
        ListItem.SkillItem("7", "დარწმუნების უნარი"),
        ListItem.SkillItem("8", "მოლაპარაკების უნარი"),
        ListItem.SkillItem("9", "ისტორიების მოყოლა (Storytelling)"),
        ListItem.SkillItem("10", "ცხადი და ლაკონური საუბარი"),
        ListItem.SkillItem("11", "თანამშრომლობა"),
        ListItem.SkillItem("12", "ემპათია"),
        ListItem.SkillItem("13", "კონფლიქტების მოგვარება"),
        ListItem.SkillItem("14", "ქსელური კავშირები (Networking)"),
        ListItem.SkillItem("15", "გუნდის მშენებლობა"),
        ListItem.SkillItem("16", "პატივისცემა"),
        ListItem.SkillItem("17", "გაზიარება"),
        ListItem.SkillItem("18", "მხარდაჭერა"),
        ListItem.SkillItem("19", "შეთანხმების უნარი"),
        ListItem.SkillItem("20", "სოციალური ინტელექტი"),
        ListItem.SkillItem("21", "ანალიტიკური აზროვნება"),
        ListItem.SkillItem("22", "ინოვაცია"),
        ListItem.SkillItem("23", "კრეატიულობა"),
        ListItem.SkillItem("24", "ლოგიკური აზროვნება"),
        ListItem.SkillItem("25", "გადაწყვეტილების მიღება"),
        ListItem.SkillItem("26", "პრობლემების იდენტიფიცირება"),
        ListItem.SkillItem("27", "გამომძიებლობა"),
        ListItem.SkillItem("28", "სტრატეგიული აზროვნება"),
        ListItem.SkillItem("29", "დიაგნოსტიკური უნარები"),
        ListItem.SkillItem("30", "ცვლილებების მართვა"),
        ListItem.SkillItem("31", "მოტივაცია"),
        ListItem.SkillItem("32", "დელეგირება"),
        ListItem.SkillItem("33", "მწვრთნელობა/ტრენინგი"),
        ListItem.SkillItem("34", "გავლენა"),
        ListItem.SkillItem("35", "მომთხოვნელობა"),
        ListItem.SkillItem("36", "ეთიკა"),
        ListItem.SkillItem("37", "მისაბაძი მაგალითი"),
        ListItem.SkillItem("38", "მსმენელობა"),
        ListItem.SkillItem("39", "დემონსტრირება"),
        ListItem.SkillItem("40", "მოლაპარაკებების წარმართვა"),
        ListItem.SkillItem("41", "ცვლილებებთან ადაპტაცია"),
        ListItem.SkillItem("42", "მოქნილობა"),
        ListItem.SkillItem("43", "სწრაფი სწავლა"),
        ListItem.SkillItem("44", "გამძლეობა"),
        ListItem.SkillItem("45", "სტრესის მართვა"),
        ListItem.SkillItem("46", "ემოციური ინტელექტი"),
        ListItem.SkillItem("47", "განუსაზღვრელობასთან გამკლავება"),
        ListItem.SkillItem("48", "ახალ სიტუაციებში ორიენტაცია"),
        ListItem.SkillItem("49", "ადაპტური აზროვნება"),
        ListItem.SkillItem("50", "საკუთარი თავის რეგულირება"),
        ListItem.SkillItem("51", "ინიციატივა"),
        ListItem.SkillItem("52", "პასუხისმგებლობა"),
        ListItem.SkillItem("53", "საიმედოობა"),
        ListItem.SkillItem("54", "დროის მართვა"),
        ListItem.SkillItem("55", "პრიორიტეტების განსაზღვრა"),
        ListItem.SkillItem("56", "ორგანიზებულობა"),
        ListItem.SkillItem("57", "დეტალებზე ორიენტაცია"),
        ListItem.SkillItem("58", "მიზანდასახულობა"),
        ListItem.SkillItem("59", "ეფექტურობა"),
        ListItem.SkillItem("60", "პუნქტუალურობა"),
        ListItem.SkillItem("61", "პატიოსნება"),
        ListItem.SkillItem("62", "ეთიკური ქცევა"),
        ListItem.SkillItem("63", "კონფიდენციალურობა"),
        ListItem.SkillItem("64", "ბიზნესის ეტიკეტი"),
        ListItem.SkillItem("65", "პოზიტიური განწყობა"),
        ListItem.SkillItem("66", "კრიტიკის მიღება"),
        ListItem.SkillItem("67", "ნდობა"),
        ListItem.SkillItem("68", "პროფესიული ზრდა"),
        ListItem.SkillItem("69", "თავაზიანობა"),
        ListItem.SkillItem("70", "რეპუტაციის მართვა"),
        ListItem.SkillItem("71", "მომხმარებელთა მომსახურება"),
        ListItem.SkillItem("72", "მომხმარებელზე ორიენტაცია"),
        ListItem.SkillItem("73", "მოთხოვნილებების გაგება"),
        ListItem.SkillItem("74", "პრობლემების გადაჭრა მომხმარებლისთვის"),
        ListItem.SkillItem("75", "გამოხმაურებაზე რეაგირება"),
        ListItem.SkillItem("76", "რისკების შეფასება"),
        ListItem.SkillItem("77", "მონაცემებზე დაფუძნებული გადაწყვეტილებები"),
        ListItem.SkillItem("78", "ინტუიცია"),
        ListItem.SkillItem("79", "მსჯელობა"),
        ListItem.SkillItem("80", "გადაწყვეტილების სისწრაფე"),
        ListItem.SkillItem("81", "თვითშეგნება"),
        ListItem.SkillItem("82", "თვითმოტივაცია"),
        ListItem.SkillItem("83", "თვითდისციპლინა"),
        ListItem.SkillItem("84", "სწავლის სურვილი"),
        ListItem.SkillItem("85", "ცნობისმოყვარეობა"),
        ListItem.SkillItem("86", "მიზნების დასახვა"),
        ListItem.SkillItem("87", "თვითრეფლექსია"),
        ListItem.SkillItem("88", "ჩვევების ფორმირება"),
        ListItem.SkillItem("89", "კრიტიკის კონსტრუქციულად მიღება"),
        ListItem.SkillItem("90", "ცოდნის გაფართოება"),
        ListItem.SkillItem("91", "ეთიკური მსჯელობა"),
        ListItem.SkillItem("92", "კულტურული კომპეტენცია"),
        ListItem.SkillItem("93", "კონტექსტის გაგება"),
        ListItem.SkillItem("94", "ქსელის მშენებლობა"),
        ListItem.SkillItem("95", "პაციენტურობა"),
        ListItem.SkillItem("96", "პოზიტიურობა"),
        ListItem.SkillItem("97", "ყურადღება"),
        ListItem.SkillItem("98", "სიმშვიდე სტრესის დროს"),
        ListItem.SkillItem("99", "ინტელექტუალური ცნობისმოყვარეობა"),
        ListItem.SkillItem("100", "დაგეგმვა")
    )

    private lateinit var adapter: SkillSelectionAdapter

    private val fullSkillList: MutableList<ListItem.SkillItem> = mutableListOf()

    // This tracks how many skills are currently displayed
    private var currentLoadedCount = 0
    private val ITEMS_PER_LOAD = 10 // Number of skills to load each time

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentCandidateSoftSkillsBinding {
        return FragmentCandidateSoftSkillsBinding.inflate(inflater,container,false)
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
                CandidateLanguagesFragment()
            ).commit()

            // Here you would typically save 'selectedSkills' to your user's profile
            // e.g., to a database, shared preferences, or send to a server.
        }
    }

    private fun techSkillsRVInit() = with(binding){
        skillsRVInit(candidateSoftSkillsRv,softSkills)

        loadAllPossibleSkills(softSkills)
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