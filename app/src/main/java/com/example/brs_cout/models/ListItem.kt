package com.example.brs_cout.models

sealed class ListItem {
    data class SkillItem(
        val id: String,
    ) : ListItem()

    object ShowMoreItem : ListItem() // ეს იქნება ჩვენი "Show More" ღილაკი
}