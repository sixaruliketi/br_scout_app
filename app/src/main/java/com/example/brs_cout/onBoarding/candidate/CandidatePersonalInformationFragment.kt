package com.example.brs_cout.onBoarding.candidate

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.brs_cout.R
import com.example.brs_cout.adapters.SkillSelectionAdapter
import com.example.brs_cout.base.BaseFragment
import com.example.brs_cout.databinding.FragmentCandidatePersonalInformationBinding
import com.example.brs_cout.models.ListItem
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent

class CandidatePersonalInformationFragment :
    BaseFragment<FragmentCandidatePersonalInformationBinding>() {

    private lateinit var adapter: SkillSelectionAdapter

    // This list holds ALL available skills, with their current selection state
    private val fullSkillList: MutableList<ListItem.SkillItem> = mutableListOf()

    // This tracks how many skills are currently displayed
    private var currentLoadedCount = 0
    private val ITEMS_PER_LOAD = 10 // Number of skills to load each time

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

        //tech skills
        skillsRVInit(candidateTechSkillsRV,techSkills)

        loadAllPossibleSkills(techSkills)

        //soft skills
        skillsRVInit(candidateSoftSkillsRv,softSkills)

        loadAllPossibleSkills(softSkills)

        //languages
        skillsRVInit(candidateLanguagesRV,allLanguages)

        loadAllPossibleSkills(allLanguages)

        // Setup button click listener
        nextBtn.setOnClickListener {
            val selectedSkills = fullSkillList.filter { it.isSelected }
//            displaySelectedSkills(selectedSkills) // TODO("ამისი ფუნქცია ქვემოთ წერია და შესაცვლელია")

            parentFragmentManager.beginTransaction().replace(
                R.id.main,
                CandidatePersonalInfo2Fragment()
            ).commit()

            Toast.makeText(requireContext(), "ეს ინფო შეიყრება პროფაილზე", Toast.LENGTH_SHORT).show()

            // Here you would typically save 'selectedSkills' to your user's profile
            // e.g., to a database, shared preferences, or send to a server.
        }
    }

    private fun skillsRVInit(rv:RecyclerView,list: List<ListItem.SkillItem>){
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

    private fun loadAllPossibleSkills(list: List<ListItem.SkillItem>) {
        fullSkillList.addAll(list)

        // Initialize the count to 0 and then call loadMoreSkills to show the first batch
        currentLoadedCount = 0
        loadMoreSkills(list) // Loads the first 'ITEMS_PER_LOAD' skills
        updateRecyclerViewDisplay(list) // Updates the adapter
    }

    private fun loadMoreSkills(list: List<ListItem.SkillItem>) {
        val nextLoadAmount = ITEMS_PER_LOAD
        val endIndex = (currentLoadedCount + nextLoadAmount).coerceAtMost(list.size)
        // Simply update the count of how many items are now "loaded"
        currentLoadedCount = endIndex
    }

    // This method constructs the list to be shown in RecyclerView and submits it to the adapter
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