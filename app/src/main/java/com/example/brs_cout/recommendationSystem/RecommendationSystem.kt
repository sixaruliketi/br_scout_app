package com.example.brs_cout.recommendationSystem

import android.util.Log
import com.example.brs_cout.models.Candidate
import com.example.brs_cout.models.Vacancy

object RecommendationSystem {

    fun calculateMatchScore(
        candidate: Candidate,
        vacancy: Vacancy
    ): Double {
        val totalRequiredSkills = listOfNotNull(
            vacancy.requiredTechnicalSkills,
            vacancy.requiredSoftSkills,
            vacancy.requiredLanguages
        ).sumOf { it.size }

        if (totalRequiredSkills == 0) return 0.0

        val candidateSkills = candidate.technicalSkills.orEmpty().map { it.id } +
                candidate.softSkills.orEmpty().map { it.id } +
                candidate.languages.orEmpty().map { it.id }

        val requiredSkills = vacancy.requiredTechnicalSkills.orEmpty().map { it.id } +
                (vacancy.requiredSoftSkills ?: emptyList()).map { it.id } +
                (vacancy.requiredLanguages ?: emptyList()).map { it.id }

        val matchedCount = requiredSkills.count { it in candidateSkills }

        return matchedCount.toDouble() / totalRequiredSkills
    }

    fun findTopCandidates(
        allCandidates: List<Candidate>,
        vacancy: Vacancy,
        threshold: Double = 0.3,
        maxResults: Int = 20
    ): List<Pair<Candidate, Double>> {
        return allCandidates
            .map { it to calculateMatchScore(it, vacancy) }
            .filter { it.second >= threshold }
            .sortedByDescending { it.second }
            .take(maxResults)
    }

//    fun generateTopResults(
//        candidatesList: List<Candidate>,
//        vacancy: Vacancy
//    ) {
//        val filtered = findTopCandidates(candidatesList, vacancy)
//
//        // Convert to CandidateScoreData
//        val scoredCandidates = filtered.map { (candidate, matchScore) ->
//            CandidateScoreData(
//                id = candidate.id ?: "",
//                salaryExpectation = candidate.salaryExpectation ?: Double.MAX_VALUE,
//                isLookingForJob = candidate.isLookingForJob ?: false,
//                techSkillMatch = matchScore * 60, // use match % as tech match
//                softSkillMatch = candidate.softSkills?.size?.toDouble()?.coerceAtMost(10.0)?.times(10) ?: 0.0,
//                experience = candidate.yearsOfExperience ?: 0
//            )
//        }
//        Log.d("TAG",scoredCandidates.size.toString())
//        val weights = mapOf(
//            "salary" to 0.2,
//            "looking" to 0.1,
//            "tech" to 0.3,
//            "soft" to 0.2,
//            "exp" to 0.2
//        )
//
//        val topResults = topsisRank(scoredCandidates, weights)
//
//        topResults.forEachIndexed { index, result ->
//            println("${index + 1}. ${result.candidateId} - Score: ${"%.3f".format(result.score)}")
//        }
//    }

    fun topsisRank(
        candidates: List<CandidateScoreData>,
        weights: Map<String, Double>
    ): List<TopsisResult> {
        val salaryValues = candidates.map { it.salaryExpectation }
        val lookingValues = candidates.map { if (it.isLookingForJob) 1.0 else 0.0 }
        val techMatchValues = candidates.map { it.techSkillMatch }
        val softMatchValues = candidates.map { it.softSkillMatch }
        val experienceValues = candidates.map { it.experience.toDouble() }

        fun normalize(values: List<Double>): List<Double> {
            val norm = kotlin.math.sqrt(values.sumOf { it * it })
            return values.map { it / norm }
        }

        val normSalary = normalize(salaryValues)
        val normLooking = normalize(lookingValues)
        val normTech = normalize(techMatchValues)
        val normSoft = normalize(softMatchValues)
        val normExp = normalize(experienceValues)

        val weighted = candidates.indices.map { i ->
            mapOf(
                "salary" to normSalary[i] * weights.getOrDefault("  salary", 1.0),
                "looking" to normLooking[i] * weights.getOrDefault("looking", 1.0),
                "tech" to normTech[i] * weights.getOrDefault("tech", 1.0),
                "soft" to normSoft[i] * weights.getOrDefault("soft", 1.0),
                "exp" to normExp[i] * weights.getOrDefault("exp", 1.0)
            )
        }

        val ideal = mapOf(
            "salary" to weighted.minOf { it["salary"]!! }, // cost
            "looking" to weighted.maxOf { it["looking"]!! },
            "tech" to weighted.maxOf { it["tech"]!! },
            "soft" to weighted.maxOf { it["soft"]!! },
            "exp" to weighted.maxOf { it["exp"]!! }
        )

        val antiIdeal = mapOf(
            "salary" to weighted.maxOf { it["salary"]!! },
            "looking" to weighted.minOf { it["looking"]!! },
            "tech" to weighted.minOf { it["tech"]!! },
            "soft" to weighted.minOf { it["soft"]!! },
            "exp" to weighted.minOf { it["exp"]!! }
        )

        return candidates.mapIndexed { index, candidate ->
            val w = weighted[index]

            val dPlus = kotlin.math.sqrt(
                listOf("salary", "looking", "tech", "soft", "exp").sumOf { key ->
                    val diff = w[key]!! - ideal[key]!!
                    diff * diff
                }
            )

            val dMinus = kotlin.math.sqrt(
                listOf("salary", "looking", "tech", "soft", "exp").sumOf { key ->
                    val diff = w[key]!! - antiIdeal[key]!!
                    diff * diff
                }
            )

            val score = dMinus / (dPlus + dMinus)
            TopsisResult(candidateId = candidate.id, score = score)
        }.sortedByDescending { it.score }
    }
}
