package com.example.brs_cout.models

sealed class ListItem {
    data class SkillItem(
        val id: String,
        val name: String,
        var isSelected: Boolean = false) : ListItem()

    object ShowMoreItem : ListItem() // ეს იქნება ჩვენი "Show More" ღილაკი
}