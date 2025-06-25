package com.example.brs_cout.search

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.brs_cout.R
import com.example.brs_cout.adapters.SearchResultAdapter
import com.example.brs_cout.base.BaseFragment
import com.example.brs_cout.databinding.FragmentSearchBinding
import com.example.brs_cout.models.SearchResultItem
import com.google.android.material.chip.Chip
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SearchFragment : BaseFragment<FragmentSearchBinding>() {

    private lateinit var searchResultAdapter: SearchResultAdapter // Declare it at the class level

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentSearchBinding {
        return FragmentSearchBinding.inflate(layoutInflater,container,false)
    }

    override fun init() {
        TODO("Not yet implemented")
    }

    fun newInit() = with(binding){

        // 1. Search Bar Input
        binding.editTextSearch.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                performSearch(v.text.toString())
                // Hide the keyboard after search
                val imm = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(v.windowToken, 0)
                true // Consume the event
            } else {
                false
            }
        }

        // 2. Search Submit Button
        binding.buttonSearchSubmit.setOnClickListener {
            performSearch(binding.editTextSearch.text.toString())
            // Hide the keyboard
            val imm = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(binding.editTextSearch.windowToken, 0)
        }

        // 3. Filter Button
        binding.buttonFilter.setOnClickListener {
            showFilterOptions() // This function will open your filter UI
        }


        searchResultAdapter = SearchResultAdapter()
        binding.recyclerViewSearchResults.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = searchResultAdapter
        }
        performSearch("") // Load empty or default results

        setFragmentResultListener(REQUEST_KEY_FILTERS) { requestKey, bundle ->
            if (requestKey == REQUEST_KEY_FILTERS) {
                val clearAll = bundle.getBoolean(BUNDLE_KEY_CLEAR_ALL, false)
                if (clearAll) {
                    // Logic to clear all filters in SearchFragment UI and data
                    currentFilters.clear()
                    binding.chipGroupActiveFilters.removeAllViews() // Clear active filter chips
                    binding.editTextSearch.text.clear() // Clear search text too if desired
                    performSearch("") // Re-run search with no filters
                } else {
                    val location = bundle.getString(BUNDLE_KEY_LOCATION)
                    val experience = bundle.getString(BUNDLE_KEY_EXPERIENCE)
                    val industry = bundle.getString(BUNDLE_KEY_INDUSTRY)
                    val remote = bundle.getBoolean(BUNDLE_KEY_REMOTE)

                    // Update your SearchFragment's internal state with these new filters
                    currentFilters["location"] = location.orEmpty()
                    currentFilters["experience"] = experience.orEmpty()
                    currentFilters["industry"] = industry.orEmpty()
                    currentFilters["remote"] = remote.toString() // Store as string for map

                    // Update active filter chips in SearchFragment UI
                    updateActiveFilterChips(currentFilters) // You'll write this function

                    // Re-run the search with the new filters
                    performSearch(binding.editTextSearch.text.toString(), currentFilters)
                }
            }
        }

        // Launch the filter bottom sheet
        binding.buttonFilter.setOnClickListener {
            val filterBottomSheet = FilterBottomSheetFragment()
            // OPTIONAL: Pass current filters to the bottom sheet so it can pre-populate
            filterBottomSheet.arguments = bundleOf(
                BUNDLE_KEY_LOCATION to currentFilters["location"],
                BUNDLE_KEY_EXPERIENCE to currentFilters["experience"],
                BUNDLE_KEY_INDUSTRY to currentFilters["industry"],
                BUNDLE_KEY_REMOTE to (currentFilters["remote"]?.toBoolean() ?: false)
            )
            filterBottomSheet.show(childFragmentManager, filterBottomSheet.tag)
        }
    }
    private val currentFilters = mutableMapOf<String, String>() // Store active filters

    private fun updateActiveFilterChips(filters: Map<String, String>) {
        binding.chipGroupActiveFilters.removeAllViews() // Clear existing chips

        filters.forEach { (key, value) ->
            if (value.isNotBlank() && value != "null" && value != "false") { // Avoid empty/null/false values
                val chip = Chip(requireContext())
                chip.text = "$key: $value"
                chip.isCloseIconVisible = true
                chip.isClickable = true // Allows removal

                chip.setOnCloseIconClickListener {
                    // Logic to remove this specific filter
                    currentFilters.remove(key)
                    binding.chipGroupActiveFilters.removeView(chip)
                    performSearch(binding.editTextSearch.text.toString(), currentFilters)
                }
                binding.chipGroupActiveFilters.addView(chip)
            }
        }
    }
    private fun showFilterOptions() {
        // Option 1: Launch a Bottom Sheet Dialog Fragment
        val filterBottomSheet = FilterBottomSheetFragment()
        filterBottomSheet.show(childFragmentManager, filterBottomSheet.tag)

        // Option 2: Navigate to a new Filter Fragment/Activity
        // findNavController().navigate(R.id.action_searchFragment_to_filterFragment)

        // Option 3: Expand an in-place filter view (less common for complex filters)
        // binding.filterOptionsLayout.visibility = View.VISIBLE
    }

    private fun performSearch(query: String, currentFilters: Map<String, String>? = null) {
        binding.progressBarLoading.visibility = View.VISIBLE
        binding.textViewNoResults.visibility = View.GONE
        binding.recyclerViewSearchResults.visibility = View.GONE // Hide results during load

        // In a real app, you'd make an API call here.
        // For now, let's simulate a delay and some dummy data.
        val combinedQuery = "$query ${currentFilters?.values?.joinToString(" ")}" // Combine query and filters
        Log.d("SearchFragment", "Performing search for: $combinedQuery")

        lifecycleScope.launch { // Requires Kotlin Coroutines and lifecycle-runtime-ktx
            delay(1500) // Simulate network delay

            val results = generateDummyResults(query, currentFilters) // Your data generation logic

            if (results.isEmpty()) {
                binding.textViewNoResults.visibility = View.VISIBLE // Correct: Show "No results found" text
                binding.recyclerViewSearchResults.visibility = View.GONE // Correct: Hide the RecyclerView
            } else {
                searchResultAdapter.submitList(results) // Correct: Update RecyclerView with results
                binding.recyclerViewSearchResults.visibility = View.VISIBLE // Correct: Show the RecyclerView
            }

            binding.progressBarLoading.visibility = View.GONE
        }
    }

    // Dummy data generation
    private fun generateDummyResults(query: String, filters: Map<String, String>?): List<SearchResultItem> {
        val allItems = listOf(
            SearchResultItem("John Doe", "Senior Software Engineer", "Tech Solutions Inc.", "London, UK"),
            SearchResultItem("Jane Smith", "Marketing Manager", "Creative Agency", "Remote"),
            SearchResultItem("Peter Jones", "Data Scientist", "Analytics Corp.", "New York, USA"),
            SearchResultItem("Alice Brown", "UI/UX Designer", "Design Studio", "London, UK"),
            SearchResultItem("Robert White", "Project Manager", "Global Services", "Remote")
        )

        val filteredItems = allItems.filter { item ->
            item.name.contains(query, ignoreCase = true) ||
                    item.title.contains(query, ignoreCase = true) ||
                    item.company.contains(query, ignoreCase = true) ||
                    item.location.contains(query, ignoreCase = true)
        }

        // Apply filters if present (simplified logic)
        filters?.forEach { (key, value) ->
            // Example: if key is "location" and value is "London"
            // You'd refine `filteredItems` further based on actual data structure
        }

        return filteredItems
    }

}