package com.sizdev.arkhirefortalent.homepage.item.home.project.approvedproject

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.sizdev.arkhirefortalent.R
import com.sizdev.arkhirefortalent.databinding.ItemShowingProjectBinding
import com.sizdev.arkhirefortalent.homepage.item.home.project.detailedproject.DetailOfProjectActivity

class ShowApprovedProjectAdapter : RecyclerView.Adapter<ShowApprovedProjectAdapter.ShowApprovedProjectHolder>() {
    private var items = mutableListOf<ShowApprovedProjectModel>()

    fun addList(list: List<ShowApprovedProjectModel>){
        items.clear()
        items.addAll(list)
        notifyDataSetChanged()
    }

    class ShowApprovedProjectHolder(val binding: ItemShowingProjectBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShowApprovedProjectHolder {
        return ShowApprovedProjectHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_showing_project, parent, false))
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ShowApprovedProjectHolder, position: Int) {
        val item = items[position]
        holder.binding.tvProjectTitle.text = item.projectTitle
        holder.binding.tvProjectSalary.text = item.projectSallary
        holder.binding.tvProjectHiringStatus.text = "(${item.hiringStatus})"

        holder.itemView.setOnClickListener {
            val context = holder.binding.showAllProjectItem.context
            val intent = Intent(context, DetailOfProjectActivity::class.java)
            val offeringID = item.offeringID.toString()
            val projectTitle = item.projectTitle.toString()
            val projectSalary = item.projectSallary.toString()
            val projectDesc = item.projectDesc.toString()
            val projectDuration = item.projectDuration.toString()
            val projectStatus = item.hiringStatus.toString()
            val msgReply = item.replyMsg.toString()
            val repliedAt = item.repliedAt.toString()

            intent.putExtra("offeringID", offeringID)
            intent.putExtra("projectTitle", projectTitle)
            intent.putExtra("projectSalary", projectSalary)
            intent.putExtra("projectDesc", projectDesc)
            intent.putExtra("projectDuration", projectDuration)
            intent.putExtra("projectStatus", projectStatus)
            intent.putExtra("msgReply", msgReply)
            intent.putExtra("repliedAt", repliedAt)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = items.size
}