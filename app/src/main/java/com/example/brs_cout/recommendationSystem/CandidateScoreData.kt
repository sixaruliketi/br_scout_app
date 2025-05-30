package com.example.brs_cout.recommendationSystem

data class CandidateScoreData(
    val id: String,
    val salaryExpectation: Double,   // cost
    val isLookingForJob: Boolean,    // benefit
    val techSkillMatch: Double,      // benefit
    val softSkillMatch: Double,      // benefit
    val experience: Int              // benefit
)