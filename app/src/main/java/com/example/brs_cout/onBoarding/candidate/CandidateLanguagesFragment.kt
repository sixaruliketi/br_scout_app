package com.example.brs_cout.onBoarding.candidate

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.brs_cout.R
import com.example.brs_cout.StartActivity
import com.example.brs_cout.base.BaseFragment
import com.example.brs_cout.databinding.FragmentCandidateLanguagesBinding
import com.example.brs_cout.models.Candidate
import com.example.brs_cout.models.ListItem
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import com.google.android.material.chip.Chip
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class CandidateLanguagesFragment : BaseFragment<FragmentCandidateLanguagesBinding>() {

    private val db = FirebaseDatabase.getInstance().getReference("candidates")

    private val allLanguages = listOf(
        "English",
        "Georgian",
        "Russian",
        "Spanish",
        "French",
        "German",
        "Chinese",
        "Japanese",
        "Korean",
        "Italian",
        "Portuguese",
        "Arabic",
        "Hindi",
        "Bengali",
        "Turkish",
        "Dutch",
        "Swedish",
        "Norwegian",
        "Finnish",
        "Danish",
        "Polish",
        "Ukrainian",
        "Romanian",
        "Hungarian",
        "Czech",
        "Greek",
        "Hebrew",
        "Vietnamese",
        "Thai",
        "Indonesian",
        "Malay",
        "Persian",
        "Urdu",
        "Swahili",
        "Zulu",
        "Afrikaans",
        "Catalan",
        "Croatian",
        "Slovak",
        "Bulgarian",
        "Serbian",
        "Lithuanian",
        "Latvian",
        "Estonian",
        "Icelandic",
        "Mongolian",
        "Filipino",
        "Tamil",
        "Telugu",
        "Marathi",
        "Gujarati",
        "Kannada",
        "Malayalam",
        "Punjabi",
        "Sinhala",
        "Nepali",
        "Pashto",
        "Kurdish",
        "Armenian",
        "Georgian",
        "Basque",
        "Galician",
        "Luxembourgish",
        "Maltese",
        "Irish",
        "Scottish Gaelic",
        "Welsh",
        "Corsican",
        "Esperanto",
        "Hawaiian",
        "Maori",
        "Samoan",
        "Tahitian",
        "Fijian",
        "Tongan",
        "Yoruba",
        "Igbo",
        "Hausa",
        "Amharic",
        "Somali",
        "Berber",
        "Tibetan",
        "Uyghur",
        "Kazakh",
        "Uzbek",
        "Turkmen",
        "Tajik",
        "Macedonian",
        "Albanian",
        "Bosnian",
        "Montenegrin",
        "Luxembourgish",
        "Frisian"
    )

    private val selectedSkills = mutableSetOf<String>() // No duplicates

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentCandidateLanguagesBinding {
        return FragmentCandidateLanguagesBinding.inflate(inflater, container, false)
    }

    override fun init() = with(binding) {


        db.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (childSnapshot in snapshot.children) {
                    val id = childSnapshot.key  // ეს არის ID

                    val adapter = ArrayAdapter(
                        requireContext(),
                        android.R.layout.simple_dropdown_item_1line,
                        allLanguages
                    )
                    skillSearchView.setAdapter(adapter)
                    skillSearchView.setOnItemClickListener { parent, _, position, _ ->
                        val selectedSkill = parent.getItemAtPosition(position).toString()

                        if (selectedSkills.contains(selectedSkill)) {
                            Toast.makeText(requireContext(), "Already selected", Toast.LENGTH_SHORT)
                                .show()
                        } else if (selectedSkills.size >= 10) {
                            Toast.makeText(
                                requireContext(),
                                "Enter maximum 10 languages",
                                Toast.LENGTH_SHORT
                            )
                                .show()
                        } else {
                            addSkillChip(selectedSkill)
                            selectedSkills.add(selectedSkill)
                        }
                        skillSearchView.setText("") // Clear input

                        val skillItems = selectedSkills.map { skill ->
                            ListItem.SkillItem(id = skill)
                        }

                        db.child(id.toString())
                            .child("languages")
                            .setValue(skillItems)
                            .addOnSuccessListener {
                                Toast.makeText(
                                    requireContext(),
                                    "Skills uploaded!",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                            .addOnFailureListener {
                                Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT)
                                    .show()
                            }
                    }
                }


            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

        registerCandidateBtn.setOnClickListener {
            Toast.makeText(
                requireContext(),
                "congratulations!\nYou are registered as a candidate",
                Toast.LENGTH_SHORT
            ).show()
            parentFragmentManager.beginTransaction()
                .replace(R.id.main, CandidateRegistrationSuccessPageFragment()).commit()
//            val intent = Intent(requireContext(), StartActivity::class.java)
//            startActivity(intent)
//            requireActivity().finish()

        }

        goBackToCandidateSoftSkills.setOnClickListener {
            parentFragmentManager.beginTransaction().replace(R.id.main,CandidateSoftSkillsFragment()).commit()
        }
    }

    private fun addSkillChip(skill: String) = with(binding) {
        val chip = Chip(requireContext())
        chip.text = skill
        chip.isCloseIconVisible = true
        chip.setOnCloseIconClickListener {
            selectedSkills.remove(skill)
            selectedSkillsChipGroup.removeView(chip)
        }
        selectedSkillsChipGroup.addView(chip)
    }

    // Optional function if later you want selectedSkills as list
    fun getSelectedSkills(): List<String> = selectedSkills.toList()

}