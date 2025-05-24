package com.example.brs_cout.adapters // დარწმუნდი, რომ პაკეტის სახელი სწორია

import android.util.Log // <-- დაამატე ეს იმპორტი
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.brs_cout.R
import com.example.brs_cout.models.ListItem

// ... დანარჩენი იმპორტები

class SkillSelectionAdapter(
    private val onSkillSelected: (ListItem.SkillItem, Boolean) -> Unit,
    private val onShowMoreClicked: () -> Unit
) : ListAdapter<ListItem, RecyclerView.ViewHolder>(ListItemDiffCallback()) {

    companion object {
        private const val VIEW_TYPE_SKILL_ITEM = 0
        private const val VIEW_TYPE_SHOW_MORE = 1
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is ListItem.SkillItem -> VIEW_TYPE_SKILL_ITEM
            ListItem.ShowMoreItem -> VIEW_TYPE_SHOW_MORE
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_SKILL_ITEM -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.skill_item, parent, false)
                SkillViewHolder(view)
            }
            VIEW_TYPE_SHOW_MORE -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.item_show_more_button, parent, false)
                // !!! დაამატე ეს Log შეტყობინებები !!!
                Log.d("AdapterDebug", "onCreateViewHolder: Inflating VIEW_TYPE_SHOW_MORE. View is null: ${view == null}")
                if (view != null) {
                    Log.d("AdapterDebug", "Inflated view: ${view.javaClass.simpleName} (ID: ${view.id}), parent: ${view.parent?.javaClass?.simpleName}")
                    if (view is ViewGroup) {
                        Log.d("AdapterDebug", "Inflated view has children: ${view.childCount}")
                        for (i in 0 until view.childCount) {
                            val child = view.getChildAt(i)
                            Log.d("AdapterDebug", "Child $i: ${child.javaClass.simpleName}, ID: ${child.id}, text: ${if (child is Button) child.text else "N/A"}")
                        }
                    }
                }
                ShowMoreViewHolder(view)
            }
            else -> throw IllegalArgumentException("Unknown view type: $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is SkillViewHolder -> {
                val skill = getItem(position) as ListItem.SkillItem
                holder.bind(skill, onSkillSelected)
            }
            is ShowMoreViewHolder -> {
                holder.bind(onShowMoreClicked)
            }
        }
    }

    class SkillViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val skillName: TextView = itemView.findViewById(R.id.text_skill_name)
        val skillCheckbox: CheckBox = itemView.findViewById(R.id.checkbox_skill)

        fun bind(skill: ListItem.SkillItem, onSkillSelected: (ListItem.SkillItem, Boolean) -> Unit) {
            skillName.text = skill.name
            skillCheckbox.isChecked = skill.isSelected

            skillCheckbox.setOnCheckedChangeListener { _, isChecked ->
                onSkillSelected(skill, isChecked)
            }

            itemView.setOnClickListener {
                skillCheckbox.isChecked = !skillCheckbox.isChecked
            }
        }    }

    class ShowMoreViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val showMoreButton: Button

        init {
            // !!! დაამატე ეს Log შეტყობინებები !!!
            Log.d("ShowMoreVH", "ShowMoreViewHolder constructor called for itemView: ${itemView.javaClass.simpleName}, ID: ${itemView.id}")
            showMoreButton = itemView.findViewById(R.id.btn_show_more) // <-- ეს არის ხაზი, სადაც ხდება NullPointerException
            if (showMoreButton == null) {
                Log.e("ShowMoreVH", "ERROR: btn_show_more not found in the inflated view!")
            } else {
                Log.d("ShowMoreVH", "Successfully found btn_show_more.")
            }
        }

        fun bind(onShowMoreClicked: () -> Unit) {
            showMoreButton.setOnClickListener {
                onShowMoreClicked()
            }
        }
    }

    class ListItemDiffCallback : DiffUtil.ItemCallback<ListItem>() {
        override fun areItemsTheSame(oldItem: ListItem, newItem: ListItem): Boolean {
            return when {
                oldItem is ListItem.SkillItem && newItem is ListItem.SkillItem ->
                    oldItem.id == newItem.id
                oldItem is ListItem.ShowMoreItem && newItem is ListItem.ShowMoreItem ->
                    true // ShowMoreItem ყოველთვის იგივეა
                else -> false
            }
        }

        override fun areContentsTheSame(oldItem: ListItem, newItem: ListItem): Boolean {
            return oldItem == newItem // Data class equals() უზრუნველყოფს ამას
        }
    }
}