package com.sizdev.arkhirefortalent.homepage.item.home.project.highlightproject

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.sizdev.arkhirefortalent.R
import com.sizdev.arkhirefortalent.databinding.ItemTalentProjectHighlightBinding
import com.sizdev.arkhirefortalent.homepage.item.home.project.allproject.ShowAllProjectModel
import com.sizdev.arkhirefortalent.homepage.item.home.project.detailedproject.DetailOfProjectActivity
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView
import java.util.*


class HighLightProjectAdapter : RecyclerView.Adapter<HighLightProjectAdapter.ProjectHolder>() {
    private var items = mutableListOf<HighLightProjectModel>()

    fun addList(list: List<HighLightProjectModel>){
        items.clear()
        items.addAll(list)
        notifyDataSetChanged()
    }

    class ProjectHolder(val binding: ItemTalentProjectHighlightBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProjectHolder {
        return ProjectHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_talent_project_highlight, parent, false))
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ProjectHolder, position: Int) {
        val item = items[position]
        holder.binding.tvHighLightProjectName.text = item.projectTitle
        holder.binding.tvHighlightProjectDeadline.text = "${item.projectDuration?.capitalize(Locale.ROOT)} DEADLINE"
        holder.binding.tvHighlightProjectSallary.text = item.projectSallary
        holder.binding.tvHighlightHiringStatus.text = "(${item.hiringStatus})"
        when(item.hiringStatus){
            "Approved" -> holder.binding.lnCard.setBackgroundResource(R.drawable.bg_card_approved)
            "Declined" -> holder.binding.lnCard.setBackgroundResource(R.drawable.bg_card_declined)
            else -> holder.binding.lnCard.setBackgroundResource(R.drawable.bg_card_waiting)
        }
        when(item.projectOwnerImage){
            null -> holder.binding.ivHighLightProjectImage.setImageResource(R.drawable.arkhireicon)
            else -> {
                Picasso.get()
                        .load("http://54.82.81.23:911/image/${item.projectOwnerImage}")
                        .resize(512, 512)
                        .centerCrop()
                        .into(holder.binding.ivHighLightProjectImage)
            }
        }

        holder.itemView.setOnClickListener {
            val context = holder.binding.itemProjectHighlightHolder.context
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