package com.example.brs_cout.recommendationSystem

import com.example.brs_cout.models.Candidate
import com.example.brs_cout.models.Vacancy
import kotlin.math.sqrt

object NewRecommendationSystem {

    /**
     * ითვლის კოსინუსურ მსგავსებას ორ სიას შორის (ID-ების მიხედვით).
     * ეს არის დამხმარე ფუნქცია, რომელიც გამოიყენება სხვა, უფრო კომპლექსური გათვლებისთვის.
     *
     * @param list1 პირველი სია (მაგ. კანდიდატის უნარების ID-ები).
     * @param list2 მეორე სია (მაგ. ვაკანსიის მოთხოვნილი უნარების ID-ები).
     * @return კოსინუსური მსგავსების ქულა 0.0-დან 1.0-მდე.
     */
    private fun calculateCosineSimilarity(
        list1: List<String>,
        list2: List<String>
    ): Double {
        val set1 = list1.toSet()
        val set2 = list2.toSet()
        val intersection = set1.intersect(set2).size.toDouble()

        val norm1 = sqrt(set1.size.toDouble())
        val norm2 = sqrt(set2.size.toDouble())

        return if (norm1 == 0.0 || norm2 == 0.0) 0.0 else intersection / (norm1 * norm2)
    }

    /**
     * ითვლის კანდიდატისა და ვაკანსიის საერთო მსგავსების ქულას
     * ტექნიკური უნარების, რბილი უნარებისა და ენების შეწონილი ჯამის სახით.
     * ეს არის კომპლექსური მსგავსების გამომთვლელი, რომელიც აერთიანებს სხვადასხვა კრიტერიუმებს.
     *
     * @param candidate კანდიდატის ობიექტი.
     * @param vacancy ვაკანსიის ობიექტი.
     * @return საერთო მსგავსების ქულა 0.0-დან 1.0-მდე.
     */
    fun calculateOverallSimilarity(candidate: Candidate, vacancy: Vacancy): Double {
        val techSim = calculateCosineSimilarity(
            candidate.technicalSkills?.mapNotNull { it.id } ?: emptyList(),
            vacancy.requiredTechnicalSkills?.mapNotNull { it.id } ?: emptyList()
        )

        val softSim = calculateCosineSimilarity(
            candidate.softSkills?.mapNotNull { it.id } ?: emptyList(),
            vacancy.requiredSoftSkills?.mapNotNull { it.id } ?: emptyList()
        )

        val langSim = calculateCosineSimilarity(
            candidate.languages?.mapNotNull { it.id } ?: emptyList(),
            vacancy.requiredLanguages?.mapNotNull { it.id } ?: emptyList()
        )

        // საერთო ქულა: წონები — tech 0.5, soft 0.3, lang 0.2
        return (techSim * 0.5) + (softSim * 0.3) + (langSim * 0.2)
    }

    /**
     * ირჩევს ტოპ 20 კანდიდატს ვაკანსიასთან მათი საერთო მსგავსების მიხედვით.
     *
     * @param vacancy ვაკანსიის ობიექტი, რომლისთვისაც ვეძებთ კანდიდატებს.
     * @param candidates კანდიდატების სია, საიდანაც ხდება შერჩევა.
     * @return კანდიდატების სია, დაწყვილებული მათი მსგავსების ქულასთან, დალაგებული კლებადობით.
     */
    fun getTop20Candidates(
        vacancy: Vacancy,
        candidates: List<Candidate>
    ): List<Pair<Candidate, Double>> {
        val scoredCandidates = candidates.map { candidate ->
            val similarity = calculateOverallSimilarity(candidate, vacancy)
            Pair(candidate, similarity)
        }

        return scoredCandidates
            .sortedByDescending { it.second } // დალაგება მსგავსების მიხედვით
            .take(20) // ტოპ 20 კანდიდატის აღება
    }

    /**
     * მონაცემთა კლასი TOPSIS ალგორითმისთვის საჭირო კანდიდატის ატრიბუტების შესანახად.
     * @property candidate ორიგინალური კანდიდატის ობიექტი.
     * @property similarity კანდიდატისა და ვაკანსიის მსგავსების ქულა.
     * @property experience კანდიდატის გამოცდილება (წლებში).
     * @property salary კანდიდატის მოთხოვნილი ხელფასი.
     * @property daysToStart დაწყების დრო (დღეებში).
     */
    data class TopsisCandidate(
        val candidate: Candidate,
        val similarity: Double,
        val experience: Int,
        val salary: Double,
        val daysToStart: Int
    )

    /**
     * ნორმალიზებას უკეთებს რიცხვების სიას ევკლიდური ნორმის გამოყენებით.
     * @param values რიცხვების სია.
     * @return ნორმალიზებული რიცხვების სია.
     */
    fun normalize(values: List<Double>): List<Double> {
        val sumSquares = values.sumOf { it * it }
        val norm = sqrt(sumSquares)
        // თუ ნორმა 0-ია, ეს ნიშნავს, რომ ყველა მნიშვნელობა 0-ია, ამიტომ ვაბრუნებთ 0-ების სიას.
        return if (norm == 0.0) values.map { 0.0 } else values.map { it / norm }
    }

    /**
     * იყენებს TOPSIS (Technique for Order Preference by Similarity to Ideal Solution) ალგორითმს
     * კანდიდატების რანჟირებისთვის მრავალი კრიტერიუმის მიხედვით.
     *
     * @param candidates TopsisCandidate ობიექტების სია, რომლებიც შეიცავს კანდიდატის დეტალებს და შესაბამის ქულებს.
     * @return კანდიდატების სია, დაწყვილებული მათი TOPSIS ქულასთან, დალაგებული კლებადობით (ტოპ 5).
     */
    fun topsisRank(candidates: List<TopsisCandidate>): List<Pair<Candidate, Double>> {
        if (candidates.isEmpty()) {
            return emptyList()
        }

        val similarityList = candidates.map { it.similarity }
        val experienceList = candidates.map { it.experience.toDouble() }
        val salaryList = candidates.map { it.salary }
        val startList = candidates.map { it.daysToStart.toDouble() }

        // ნორმალიზაცია
        val normSim = normalize(similarityList)
        val normExp = normalize(experienceList)
        val normSal = normalize(salaryList)
        val normStart = normalize(startList)

        // წონები (შესაძლებელია მათი კონფიგურირება)
        val wSim = 0.4    // მსგავსება
        val wExp = 0.3    // გამოცდილება
        val wSal = 0.2    // ხელფასი (დაბალი უკეთესია)
        val wStart = 0.1  // დაწყების დრო (დაბალი უკეთესია)

        val weighted = normSim.indices.map { i ->
            listOf(
                normSim[i] * wSim,
                normExp[i] * wExp,
                normSal[i] * wSal,
                normStart[i] * wStart
            )
        }

        // იდეალური და ანტი-იდეალური გადაწყვეტები
        // აქ უნდა განისაზღვროს, რომელი კრიტერიუმებია "სარგებელი" (მაქსიმიზაცია) და რომელი "ღირებულება" (მინიმიზაცია)
        val ideal = listOf(
            weighted.maxOf { it[0] }, // similarity: უფრო მაღალი უკეთესია
            weighted.maxOf { it[1] }, // experience: უფრო მაღალი უკეთესია
            weighted.minOf { it[2] }, // salary: უფრო დაბალი უკეთესია
            weighted.minOf { it[3] }  // start time: უფრო დაბალი უკეთესია
        )

        val antiIdeal = listOf(
            weighted.minOf { it[0] }, // similarity: უფრო დაბალი უარესია
            weighted.minOf { it[1] }, // experience: უფრო დაბალი უარესია
            weighted.maxOf { it[2] }, // salary: უფრო მაღალი უარესია
            weighted.maxOf { it[3] }  // start time: უფრო მაღალი უარესია
        )

        // გამოთვლა D+ (იდეალურიდან დაშორება) და D- (ანტი-იდეალურიდან დაშორება)
        val scores = weighted.mapIndexed { index, vec ->
            val dPlus = sqrt(vec.indices.sumOf { (vec[it] - ideal[it]) * (vec[it] - ideal[it]) })
            val dMinus = sqrt(vec.indices.sumOf { (vec[it] - antiIdeal[it]) * (vec[it] - antiIdeal[it]) })

            // CI (Closeness Coefficient) გამოთვლა
            val ci = if (dPlus + dMinus == 0.0) 0.0 else dMinus / (dPlus + dMinus)
            Pair(candidates[index].candidate, ci)
        }

        return scores.sortedByDescending { it.second }.take(5) // დაბრუნდეს ტოპ 5 კანდიდატი
    }
}