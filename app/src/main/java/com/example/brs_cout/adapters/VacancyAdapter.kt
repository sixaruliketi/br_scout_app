package com.example.brs_cout.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.brs_cout.databinding.VacancyItemBinding
import com.example.brs_cout.models.Vacancy

class VacancyAdapter(private val vacancies: MutableList<Vacancy>) : RecyclerView.Adapter<VacancyAdapter.VacancyViewHolder>() {

    inner class VacancyViewHolder(private val binding: VacancyItemBinding) : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(vacancy: Vacancy) = with(binding){
            vacancyJobTitle.text = vacancy.jobTitle
            vacancyJobCompanyName.text = vacancy.companyId
            vacancyLocation.text = vacancy.location
            vacancyPostedDate.text = vacancy.postedDate.toString()
            vacancyIsActive.text = vacancy.isActive.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VacancyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = VacancyItemBinding.inflate(inflater, parent, false)
        return VacancyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: VacancyViewHolder, position: Int) {
        holder.bind(vacancies[position])
    }

    override fun getItemCount(): Int = vacancies.size

    fun addVacancies(newVacancies: List<Vacancy>) {
        val startPos = vacancies.size
        vacancies.addAll(newVacancies)
        notifyItemRangeInserted(startPos, newVacancies.size)
    }
}
