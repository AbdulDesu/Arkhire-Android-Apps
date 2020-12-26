package com.sizdev.arkhirefortalent.homepage.project.allproject

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.sizdev.arkhirefortalent.R
import com.sizdev.arkhirefortalent.databinding.ItemShowingProjectBinding

class ShowAllProjectAdapter : RecyclerView.Adapter<ShowAllProjectAdapter.ShowAllProjectHolder>() {
    private var items = mutableListOf<ShowAllProjectModel>()

    fun addList(list: List<ShowAllProjectModel>){
        items.clear()
        items.addAll(list)
        notifyDataSetChanged()
    }

    class ShowAllProjectHolder(val binding: ItemShowingProjectBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShowAllProjectHolder {
        return ShowAllProjectHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_showing_project, parent, false))
    }

    override fun onBindViewHolder(holder: ShowAllProjectHolder, position: Int) {
        val item = items[position]
        holder.binding.offeringID.text = item.offeringID
        holder.binding.tvProjectTitle.text = item.projectTitle
        holder.binding.tvProjectSalary.text = item.projectSallary
        holder.binding.tvProjectHiringStatus.text = item.hiringStatus
    }

    override fun getItemCount(): Int = items.size
}