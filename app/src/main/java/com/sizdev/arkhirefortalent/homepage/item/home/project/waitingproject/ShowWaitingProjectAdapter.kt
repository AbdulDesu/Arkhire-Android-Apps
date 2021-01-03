package com.sizdev.arkhirefortalent.homepage.item.home.project.waitingproject

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.sizdev.arkhirefortalent.R
import com.sizdev.arkhirefortalent.databinding.ItemShowingProjectBinding
import com.sizdev.arkhirefortalent.homepage.item.home.project.detailedproject.DetailOfProjectActivity

class ShowWaitingProjectAdapter : RecyclerView.Adapter<ShowWaitingProjectAdapter.ShowWaitingProjectHolder>() {
    private var items = mutableListOf<ShowWaitingProjectModel>()

    fun addList(list: List<ShowWaitingProjectModel>){
        items.clear()
        items.addAll(list)
        notifyDataSetChanged()
    }

    class ShowWaitingProjectHolder(val binding: ItemShowingProjectBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShowWaitingProjectHolder {
        return ShowWaitingProjectHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_showing_project, parent, false))
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ShowWaitingProjectHolder, position: Int) {
        val item = items[position]
        holder.binding.offeringID.text = item.offeringID
        holder.binding.tvProjectTitle.text = item.projectTitle
        holder.binding.tvProjectSalary.text = item.projectSallary
        holder.binding.tvProjectHiringStatus.text = "(${item.hiringStatus})"

        holder.itemView.setOnClickListener {
            val item = items[position]
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