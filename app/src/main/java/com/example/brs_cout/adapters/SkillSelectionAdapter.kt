package com.example.brs_cout.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.brs_cout.R
import com.example.brs_cout.models.SkillItem

class SkillSelectionAdapter(
    private val onSkillSelected: (SkillItem, Boolean) -> Unit // Callback for selection changes
) : ListAdapter<SkillItem, SkillSelectionAdapter.SkillViewHolder>(SkillDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SkillViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.skill_item, parent, false)
        return SkillViewHolder(view)
    }

    override fun onBindViewHolder(holder: SkillViewHolder, position: Int) {
        val skill = getItem(position)
        holder.bind(skill, onSkillSelected)
    }

    class SkillViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val skillName: TextView = itemView.findViewById(R.id.text_skill_name)
        val skillCheckbox: CheckBox = itemView.findViewById(R.id.checkbox_skill)

        fun bind(skill: SkillItem, onSkillSelected: (SkillItem, Boolean) -> Unit) {
            skillName.text = skill.name
            skillCheckbox.isChecked = skill.isSelected

            // Set a listener for the checkbox
            skillCheckbox.setOnCheckedChangeListener { _, isChecked ->
                // Update the skill's state (important: update a *copy* or notify parent to update original)
                // In this setup, we notify the activity/fragment to handle the state change
                onSkillSelected(skill, isChecked)
            }

            // Also allow clicking the whole item to toggle the checkbox
            itemView.setOnClickListener {
                skillCheckbox.isChecked = !skillCheckbox.isChecked
                // The OnCheckedChangeListener will then be triggered
            }
        }
    }

    class SkillDiffCallback : DiffUtil.ItemCallback<SkillItem>() {
        override fun areItemsTheSame(oldItem: SkillItem, newItem: SkillItem): Boolean {
            return oldItem.id == newItem.id // Skills are the same if their IDs match
        }

        override fun areContentsTheSame(oldItem: SkillItem, newItem: SkillItem): Boolean {
            // Check if name and selection state are the same
            return oldItem == newItem // Data class equals() handles this automatically
        }
    }
}