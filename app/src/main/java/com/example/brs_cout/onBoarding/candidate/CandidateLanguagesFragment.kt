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
import com.example.brs_cout.models.ListItem
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import com.google.android.material.chip.Chip
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class CandidateLanguagesFragment : BaseFragment<FragmentCandidateLanguagesBinding>() {

    private val db = FirebaseDatabase.getInstance().getReference("candidates")
    private val user = FirebaseAuth.getInstance().currentUser!!

    private val allLanguages = listOf(
        "English", "Mandarin Chinese", "Hindi", "Spanish", "French",
        "Arabic", "Bengali", "Russian", "Portuguese", "Urdu",
        "Indonesian", "German", "Japanese", "Swahili", "Marathi",
        "Telugu", "Turkish", "Korean", "Vietnamese", "Tamil",
        "Italian", "Persian (Farsi)", "Polish", "Ukrainian", "Dutch",
        "Romanian", "Thai", "Gujarati", "Czech", "Greek",
        "Hungarian", "Hebrew", "Kannada", "Malayalam", "Punjabi",
        "Serbian", "Swedish", "Bulgarian", "Finnish", "Slovak",
        "Norwegian", "Lithuanian", "Latvian", "Estonian", "Croatian",
        "Bosnian", "Slovenian", "Armenian", "Georgian", "Azerbaijani",
        "Albanian", "Macedonian", "Malay", "Pashto", "Sinhala",
        "Khmer", "Lao", "Nepali", "Tagalog (Filipino)", "Burmese",
        "Amharic", "Tigrinya", "Somali", "Yoruba", "Hausa",
        "Zulu", "Xhosa", "Igbo", "Kurdish", "Mongolian",
        "Tajik", "Uzbek", "Kazakh", "Turkmen", "Dari",
        "Basque", "Catalan", "Galician", "Welsh", "Irish",
        "Scottish Gaelic", "Icelandic", "Esperanto", "Haitian Creole", "Luxembourgish",
        "Sundanese", "Javanese", "Maori", "Fijian", "Samoan",
        "Tongan", "Tswana", "Chewa", "Bambara", "Quechua",
        "Aymara", "Inuktitut", "Ojibwe", "Guarani", "Hmong"
    )

    private val selectedSkills = mutableSetOf<String>() // No duplicates


    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentCandidateLanguagesBinding {
        return FragmentCandidateLanguagesBinding.inflate(inflater, container, false)
    }

    override fun init() = with(binding) {
        val adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_dropdown_item_1line,
            allLanguages
        )
        skillSearchView.setAdapter(adapter)
        skillSearchView.setOnItemClickListener { parent, _, position, _ ->
            val selectedSkill = parent.getItemAtPosition(position).toString()

            if (selectedSkills.contains(selectedSkill)) {
                Toast.makeText(requireContext(), "Already selected", Toast.LENGTH_SHORT).show()
            } else if (selectedSkills.size >= 10) {
                Toast.makeText(requireContext(), "Enter maximum 10 languages", Toast.LENGTH_SHORT)
                    .show()
            } else {
                addSkillChip(selectedSkill)
                selectedSkills.add(selectedSkill)
            }
            skillSearchView.setText("") // Clear input

            val skillItems = selectedSkills.map { skill ->
                ListItem.SkillItem(id = skill)
            }

            db.child(user.uid)
                .child("languages")
                .setValue(skillItems)
                .addOnSuccessListener {
                    Toast.makeText(requireContext(), "Skills uploaded!", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener {
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }
        }

        registerCandidateBtn.setOnClickListener {
            Toast.makeText(
                requireContext(),
                "congratulations!\nYou are registered as a candidate",
                Toast.LENGTH_SHORT
            ).show()
            val intent = Intent(requireContext(), StartActivity::class.java)
            startActivity(intent)
            requireActivity().finish()

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