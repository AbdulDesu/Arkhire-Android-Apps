package com.sizdev.arkhirefortalent.homepage.project.declinedproject

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.sizdev.arkhirefortalent.R
import com.sizdev.arkhirefortalent.databinding.ItemShowingProjectBinding
import com.sizdev.arkhirefortalent.homepage.project.detailedproject.DetailOfProjectActivity

class ShowDeclinedProjectAdapter : RecyclerView.Adapter<ShowDeclinedProjectAdapter.ShowDeclinedProjectHolder>() {
    private var items = mutableListOf<ShowDeclinedProjectModel>()

    fun addList(list: List<ShowDeclinedProjectModel>){
        items.clear()
        items.addAll(list)
        notifyDataSetChanged()
    }

    class ShowDeclinedProjectHolder(val binding: ItemShowingProjectBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShowDeclinedProjectHolder {
        return ShowDeclinedProjectHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_showing_project, parent, false))
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ShowDeclinedProjectHolder, position: Int) {
        val item = items[position]
        holder.binding.offeringID.text = item.offeringID
        holder.binding.tvProjectTitle.text = item.projectTitle
        holder.binding.tvProjectSalary.text = item.projectSallary
        holder.binding.tvProjectHiringStatus.text = "(${item.hiringStatus})"

        holder.itemView.setOnClickListener {
            val item = items[position]
            val context = holder.binding.showAllProjectItem.context
            val intent = Intent(context, DetailOfProjectActivity::class.java)
            val projectTitle = item.projectTitle.toString()
            val projectSalary = item.projectSallary.toString()
            val projectDesc = item.projectDesc.toString()
            val projectDuration = item.projectDuration.toString()
            val projectStatus = item.hiringStatus.toString()
            val msgForTalent = item.msgForTalent.toString()
            val msgReply = item.replyMsg.toString()
            val repliedAt = item.repliedAt.toString()

            intent.putExtra("projectTitle", projectTitle)
            intent.putExtra("projectSalary", projectSalary)
            intent.putExtra("projectDesc", projectDesc)
            intent.putExtra("projectDuration", projectDuration)
            intent.putExtra("projectStatus", projectStatus)
            intent.putExtra("msgForTalent", msgForTalent)
            intent.putExtra("msgReply", msgReply)
            intent.putExtra("repliedAt", repliedAt)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = items.size
}