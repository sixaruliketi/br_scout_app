package com.example.brs_cout.search // Replace with your actual package name

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import com.example.brs_cout.R
import com.example.brs_cout.databinding.FragmentFilterBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.chip.Chip

// Define constants for Fragment Result API keys
const val REQUEST_KEY_FILTERS = "request_key_filters"
const val BUNDLE_KEY_LOCATION = "bundle_key_location"
const val BUNDLE_KEY_EXPERIENCE = "bundle_key_experience"
const val BUNDLE_KEY_INDUSTRY = "bundle_key_industry"
const val BUNDLE_KEY_REMOTE = "bundle_key_remote"
const val BUNDLE_KEY_CLEAR_ALL = "bundle_key_clear_all" // For clearing all filters

class FilterBottomSheetFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentFilterBottomSheetBinding? = null
    private val binding get() = _binding!!

    // To hold the currently selected filter values
    private var selectedLocation: String? = null
    private var selectedExperience: String? = null
    private var selectedIndustry: String? = null
    private var selectedRemote: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFilterBottomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 1. Initialize Views and Set Initial State (if filters were previously applied)
        // Retrieve any existing filters passed from SearchFragment (if applicable)
        arguments?.let { args ->
            selectedLocation = args.getString(BUNDLE_KEY_LOCATION)
            selectedExperience = args.getString(BUNDLE_KEY_EXPERIENCE)
            selectedIndustry = args.getString(BUNDLE_KEY_INDUSTRY)
            selectedRemote = args.getBoolean(BUNDLE_KEY_REMOTE)

            // Populate the UI with existing filter values
            selectedLocation?.let { binding.editTextFilterLocation.setText(it) }

            // Set the correct experience chip
            selectedExperience?.let { experience ->
                for (i in 0 until binding.chipGroupExperienceLevel.childCount) {
                    val chip = binding.chipGroupExperienceLevel.getChildAt(i) as Chip
                    if (chip.text.toString() == experience) {
                        chip.isChecked = true
                        break
                    }
                }
            }

            // Set the correct industry in the spinner
            val industries = resources.getStringArray(R.array.industries_array)
            selectedIndustry?.let { industry ->
                val spinnerPosition = industries.indexOf(industry)
                if (spinnerPosition != -1) {
                    binding.spinnerIndustry.setSelection(spinnerPosition)
                }
            }

            binding.switchRemoteWork.isChecked = selectedRemote
        }


        // 2. Set up Spinner for Industry
        val industriesAdapter = ArrayAdapter.createFromResource(
            requireContext(),
            R.array.industries_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }
        binding.spinnerIndustry.adapter = industriesAdapter

        // 3. Set up Event Listeners

        // Location EditText (value will be read on Apply)

        // Experience Level ChipGroup
        binding.chipGroupExperienceLevel.setOnCheckedStateChangeListener { chipGroup, checkedIds ->
            if (checkedIds.isNotEmpty()) {
                val selectedChipId = checkedIds[0]
                val selectedChip = chipGroup.findViewById<Chip>(selectedChipId)
                selectedExperience = selectedChip?.text.toString()
            } else {
                selectedExperience = null // No chip selected
            }
        }

        // Industry Spinner (value will be read on Apply)
        // Can add a listener if you want immediate updates, but often read on Apply.
        // binding.spinnerIndustry.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
        //     override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        //         selectedIndustry = parent?.getItemAtPosition(position).toString()
        //     }
        //     override fun onNothingSelected(parent: AdapterView<*>?) { selectedIndustry = null }
        // }


        // Remote Work Switch
        binding.switchRemoteWork.setOnCheckedChangeListener { _, isChecked ->
            selectedRemote = isChecked
        }

        // Clear All Filters Button
        binding.buttonClearFilters.setOnClickListener {
            // Reset all internal state variables
            selectedLocation = null
            selectedExperience = null
            selectedIndustry = null
            selectedRemote = false

            // Visually reset UI elements
            binding.editTextFilterLocation.text.clear()
            binding.chipGroupExperienceLevel.clearCheck() // Deselects all chips
            binding.spinnerIndustry.setSelection(0) // Selects the first item ("Select Industry")
            binding.switchRemoteWork.isChecked = false

            // Send a signal back to SearchFragment to clear all filters
            setFragmentResult(REQUEST_KEY_FILTERS, bundleOf(BUNDLE_KEY_CLEAR_ALL to true))
            dismiss() // Close the bottom sheet
        }

        // Apply Filters Button
        binding.buttonApplyFilters.setOnClickListener {
            // Capture current values from UI (important for EditText, Spinner)
            selectedLocation = binding.editTextFilterLocation.text.toString().takeIf { it.isNotBlank() }
            selectedIndustry = binding.spinnerIndustry.selectedItem?.toString().takeIf { it != "Select Industry" } // Avoid sending "Select Industry"

            // 4. Communicate Filters back to SearchFragment using Fragment Result API
            val resultBundle = bundleOf(
                BUNDLE_KEY_LOCATION to selectedLocation,
                BUNDLE_KEY_EXPERIENCE to selectedExperience,
                BUNDLE_KEY_INDUSTRY to selectedIndustry,
                BUNDLE_KEY_REMOTE to selectedRemote
            )
            setFragmentResult(REQUEST_KEY_FILTERS, resultBundle)
            dismiss() // Close the bottom sheet
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // Clear binding reference
    }
}