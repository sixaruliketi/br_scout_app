package com.example.brs_cout.models

data class Candidate(
    val id: String, // კანდიდატის უნიკალური ID
    val fullName: String, // სახელი
    val email: String, // ელ. ფოსტა (უნიკალური, გამოიყენება ავთენტიფიკაციისთვის)
    val phoneNumber: String?, // ტელეფონის ნომერი (არასავალდებულოა, ამიტომ nullable)
//    val country: String?, // ქვეყანა (არასავალდებულოა)
//    val city: String?, // ქალაქი (არასავალდებულოა)
    val currentJobTitle: String?,
    val profilePictureUrl: String?, // პროფილის სურათის URL (არასავალდებულოა)
    val bio: String?, // მოკლე ბიოგრაფია/შესავალი (არასავალდებულოა)
    val yearsOfExperience: Int?, // გამოცდილების წლები (არასავალდებულოა)
    val desiredJobTitle: String?, // სასურველი პოზიცია/სამსახური (არასავალდებულოა)
    val salaryExpectation: Double?, // ხელფასის მოლოდინი (არასავალდებულოა)
    val isAvailableForRemote: Boolean, // ხელმისაწვდომია თუ არა დისტანციური სამუშაოსთვის
    val isLookingForJob: Boolean, // აქტიურად ეძებს თუ არა სამსახურს

    // უნარების სიები
    val technicalSkills: List<ListItem.SkillItem> ? = null, // არჩეული ტექნიკური უნარები
    val softSkills: List<ListItem.SkillItem>? = null,     // არჩეული soft უნარები
    val languages: List<ListItem.SkillItem>? = null
)
