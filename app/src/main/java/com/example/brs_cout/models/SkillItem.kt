package com.example.brs_cout.models

data class SkillItem(
    val id: String, // Unique identifier for the skill
    val name: String,
    var isSelected: Boolean = false // Track selection state
) {
    // You might want a unique identifier for DiffUtil if ID alone isn't enough,
    // though ID is usually sufficient for data classes.
    // For simplicity, we'll use 'id' in DiffUtil.
}