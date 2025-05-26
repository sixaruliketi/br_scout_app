package com.example.brs_cout.models

sealed class ListItem {
    data class SkillItem(
        var id: String? = null,
    ) : ListItem()
}