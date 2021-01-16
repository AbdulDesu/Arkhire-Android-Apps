package com.sizdev.arkhirefortalent.homepage.item.home.project.highlightproject

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.sizdev.arkhirefortalent.R
import com.sizdev.arkhirefortalent.databinding.ItemTalentProjectHighlightBinding
import com.sizdev.arkhirefortalent.homepage.item.home.project.ProjectModel
import com.sizdev.arkhirefortalent.homepage.item.home.project.detailedproject.DetailOfProjectActivity
import com.squareup.picasso.Picasso
import java.text.NumberFormat
import java.util.*


class HighLightProjectAdapter : RecyclerView.Adapter<HighLightProjectAdapter.ProjectHolder>() {
    private var items = mutableListOf<ProjectModel>()

    fun addList(list: List<ProjectModel>){
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
        val format = NumberFormat.getCurrencyInstance()
        format.maximumFractionDigits = 0
        format.currency = Currency.getInstance("IDR")

        holder.binding.tvHighLightProjectName.text = item.projectTittle
        holder.binding.tvHighlightProjectDeadline.text = "${item.projectDuration?.capitalize(Locale.ROOT)} DEADLINE"
        holder.binding.tvHighlightProjectSallary.text = format.format(item.offeredSalary?.toDouble())
        holder.binding.tvHighlightHiringStatus.text = "(${item.hiringStatus})"

        when(item.hiringStatus){
            "Approved" -> holder.binding.lnCard.setBackgroundResource(R.drawable.bg_card_approved)
            "Declined" -> holder.binding.lnCard.setBackgroundResource(R.drawable.bg_card_declined)
            else -> holder.binding.lnCard.setBackgroundResource(R.drawable.bg_card_waiting)
        }

        when(item.companyImage){
            null -> holder.binding.ivHighLightProjectImage.setImageResource(R.drawable.arkhireicon)
            else -> {
                Picasso.get()
                        .load("http://54.82.81.23:911/image/${item.companyImage}")
                        .resize(512, 512)
                        .centerCrop()
                        .into(holder.binding.ivHighLightProjectImage)
            }
        }

        holder.itemView.setOnClickListener {
            val context = holder.binding.itemProjectHighlightHolder.context
            val intent = Intent(context, DetailOfProjectActivity::class.java)

            intent.putExtra("offeringID", item.offeringID)
            intent.putExtra("companyID", item.companyID)
            intent.putExtra("companyName", item.companyName)
            intent.putExtra("companyImage", item.companyImage)
            intent.putExtra("projectTitle", item.projectTittle)
            intent.putExtra("projectSalary", item.projectSalary)
            intent.putExtra("projectDesc", item.projectDesc)
            intent.putExtra("projectDuration", item.projectDuration)
            intent.putExtra("projectImage", item.projectImage)
            intent.putExtra("projectStatus", item.hiringStatus)
            intent.putExtra("offeredSalary", item.offeredSalary)
            intent.putExtra("msgReply", item.replyMsg)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = items.size

}