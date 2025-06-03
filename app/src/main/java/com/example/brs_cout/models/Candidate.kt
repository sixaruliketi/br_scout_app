package com.example.brs_cout.models

import java.io.Serializable

data class Candidate(
    val id: String? = null, // კანდიდატის უნიკალური ID
    val fullName: String? = null, // სახელი
    val email: String? = null, // ელ. ფოსტა (უნიკალური, გამოიყენება ავთენტიფიკაციისთვის)
    val phoneNumber: String? = null, // ტელეფონის ნომერი (არასავალდებულოა, ამიტომ nullable)
    val currentJobTitle: String? = null,
    val profilePictureUrl: String? = null, // პროფილის სურათის URL (არასავალდებულოა)
    val bio: String? = null, // მოკლე ბიოგრაფია/შესავალი (არასავალდებულოა)
    val yearsOfExperience: Int? = null, // გამოცდილების წლები (არასავალდებულოა)
    val desiredJobTitle: String? = null, // სასურველი პოზიცია/სამსახური (არასავალდებულოა)
    val salaryExpectation: Double? = null, // ხელფასის მოლოდინი (არასავალდებულოა)
    val isAvailableForRemote: Boolean? = false, // ხელმისაწვდომია თუ არა დისტანციური სამუშაოსთვის
    val isLookingForJob: Boolean? = false, // აქტიურად ეძებს თუ არა სამსახურს

    // უნარების სიები
    val technicalSkills: List<ListItem.SkillItem> ? = null, // არჩეული ტექნიკური უნარები
    val softSkills: List<ListItem.SkillItem>? = null,     // არჩეული soft უნარები
    val languages: List<ListItem.SkillItem>? = null,

    //for me
    val verified: Boolean ?= false
): Serializable
