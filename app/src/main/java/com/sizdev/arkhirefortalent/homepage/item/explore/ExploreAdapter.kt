package com.sizdev.arkhirefortalent.homepage.item.explore

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.sizdev.arkhirefortalent.R
import com.sizdev.arkhirefortalent.databinding.ItemExploreProjectBinding
import com.sizdev.arkhirefortalent.homepage.item.explore.contributor.ContributorActivity
import com.squareup.picasso.Picasso
import java.text.NumberFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

class ExploreAdapter : RecyclerView.Adapter<ExploreAdapter.ProjectExploreHolder>() {
    private var items = mutableListOf<ExploreModel>()

    fun addList(list: List<ExploreModel>){
        items.clear()
        items.addAll(list)
        notifyDataSetChanged()
    }

    class ProjectExploreHolder(val binding: ItemExploreProjectBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProjectExploreHolder {
        return ProjectExploreHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_explore_project, parent, false))
    }

    @SuppressLint("SetTextI18n", "SimpleDateFormat")
    override fun onBindViewHolder(holder: ProjectExploreHolder, position: Int) {
        val item = items[position]

        // Currency Converter
        val format = NumberFormat.getCurrencyInstance()
        format.maximumFractionDigits = 0
        format.currency = Currency.getInstance("IDR")

        // Timestamp Convert
        val dateSplitter = item.postedAt.split("T")
        val timeSplitter = dateSplitter[1].split(".")

        holder.binding.tvProjectTitle.text = item.projectTitle
        holder.binding.tvProjectOwner.text = item.companyName
        holder.binding.tvProjectSalary.text = format.format(item.projectSalary?.toDouble())
        holder.binding.tvPostedAt.text = "${dateSplitter[0]} - ${timeSplitter[0]}"

        when (item.projectImage){
            null -> holder.binding.ivProjectImage.setImageResource(R.drawable.ic_project_bg)
            else -> {
                Picasso.get()
                    .load("http://54.82.81.23:911/image/${item.projectImage}")
                    .resize(1280, 720)
                    .centerCrop()
                    .into(holder.binding.ivProjectImage)
            }
        }

        holder.itemView.setOnClickListener {
            val item = items[position]
            val context = holder.binding.itemExploreHolder.context
            val intent = Intent(context, ContributorActivity::class.java)
            intent.putExtra("projectID", item.projectID)
            intent.putExtra("projectName", item.projectTitle)
            intent.putExtra("companyID", item.companyID)
            intent.putExtra("companyName", item.companyName)
            intent.putExtra("companyImage", item.companyImage)
            intent.putExtra("projectDuration", item.projectDuration)
            intent.putExtra("projectSalary", item.projectSalary)
            intent.putExtra("projectImage", item.projectImage)
            intent.putExtra("projectDesc", item.projectDesc)

            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = items.size

}