package com.sizdev.arkhirefortalent.homepage.item.account.profile.previewer

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.sizdev.arkhirefortalent.R
import com.sizdev.arkhirefortalent.databinding.ItemTalentWorkExperienceBinding
import com.sizdev.arkhirefortalent.homepage.item.account.profile.workexperience.WorkExperienceModel
import com.sizdev.arkhirefortalent.homepage.item.account.profile.workexperience.details.WorkExperienceDetailActivity

class PreviewWorkExperienceAdapter : RecyclerView.Adapter<PreviewWorkExperienceAdapter.WorkExperienceHolder>() {
    private var items = mutableListOf<WorkExperienceModel>()

    fun addList(list: List<WorkExperienceModel>){
        items.clear()
        items.addAll(list)
        notifyDataSetChanged()
    }

    class WorkExperienceHolder(val binding: ItemTalentWorkExperienceBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkExperienceHolder {
        return WorkExperienceHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_talent_work_experience, parent, false))
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: WorkExperienceHolder, position: Int) {
        val item = items[position]
        holder.binding.tvWorkExperiencePosition.text = item.experienceTitle
        holder.binding.tvWorkExperienceCompany.text = item.experienceSource
        holder.binding.tvWorkExperienceYearStart.text = "${item.experienceStart} -"
        holder.binding.tvWorkExperienceYearEnd.text = " ${item.experienceEnd}"

        when (item.experienceDesc){
            "" -> holder.binding.tvWorkExperienceDescription.text = "-"
            else -> holder.binding.tvWorkExperienceDescription.text = item.experienceDesc
        }

    }

    override fun getItemCount(): Int = items.size
}