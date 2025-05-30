package com.example.brs_cout.recommendationSystem

import android.util.Log
import android.widget.Toast
import com.example.brs_cout.models.Candidate
import com.example.brs_cout.models.Vacancy
import com.google.firebase.database.core.Context

object RecommendationEngine {

    fun getRecommendedCandidates(vacancy: Vacancy, candidates: List<Candidate>): List<Candidate> {
        val filteredCandidates = filterCandidatesBySkills(vacancy, candidates)
        val rankedCandidates = rankCandidatesByTOPSIS(vacancy, filteredCandidates)
        Log.d("TAG", "getRecommendedCandidates ")
        if (rankedCandidates.isEmpty()){
            Log.d("TAG", "zeroooooo")
        }
        return rankedCandidates.take(5)
        // აბრუნებს მხოლოდ საუკეთესო 5 კანდიდატს
    }

    private fun filterCandidatesBySkills(vacancy: Vacancy, candidates: List<Candidate>): List<Candidate> {
        val requiredSkills = vacancy.requiredTechnicalSkills?.map { it.id }?.toSet() ?: emptySet()

        return candidates.filter { candidate ->
            val candidateSkills = candidate.technicalSkills?.map { it.id }?.toSet() ?: emptySet()
            val matchCount = candidateSkills.intersect(requiredSkills).size
            if (requiredSkills.isEmpty()) false
            else matchCount.toDouble() / requiredSkills.size >= 0.8
        }
    }

    private fun rankCandidatesByTOPSIS(vacancy: Vacancy, candidates: List<Candidate>): List<Candidate> {
        val weights = mapOf(
            "technicalSkills" to 0.5,
            "softSkills" to 0.2,
            "languages" to 0.2,
            "experience" to 0.1
        )

        Log.d("TAG", "weights ")


        val maxTechSkillsCount = vacancy.requiredTechnicalSkills?.size?.toDouble() ?: 1.0
        val maxSoftSkillsCount = vacancy.requiredSoftSkills?.size?.toDouble() ?: 1.0
        val maxLanguagesCount = vacancy.requiredLanguages?.size?.toDouble() ?: 1.0
        val maxExperience = candidates.maxOfOrNull { it.yearsOfExperience?.toDouble() ?: 0.0 } ?: 1.0

        val scores = candidates.map { candidate ->
            val techMatch = candidate.technicalSkills?.map { it.id }?.intersect(vacancy.requiredTechnicalSkills?.map { it.id }?.toSet() ?: emptySet())?.size?.toDouble() ?: 0.0
            val softMatch = candidate.softSkills?.map { it.id }?.intersect(vacancy.requiredSoftSkills?.map { it.id }?.toSet() ?: emptySet())?.size?.toDouble() ?: 0.0
            val langMatch = candidate.languages?.map { it.id }?.intersect(vacancy.requiredLanguages?.map { it.id }?.toSet() ?: emptySet())?.size?.toDouble() ?: 0.0
            val experience = candidate.yearsOfExperience?.toDouble() ?: 0.0
            Log.d("TAG", "scores ")

            val normTech = techMatch / maxTechSkillsCount
            val normSoft = softMatch / maxSoftSkillsCount
            val normLang = langMatch / maxLanguagesCount
            val normExp = experience / maxExperience
            Log.d("TAG", "normalized ")

            val finalScore = normTech * weights["technicalSkills"]!! +
                    normSoft * weights["softSkills"]!! +
                    normLang * weights["languages"]!! +
                    normExp * weights["experience"]!!
            Log.d("TAG", "finalScore ")

            Pair(candidate, finalScore)
        }

        return scores.sortedByDescending { it.second }.map { it.first }
    }
}
