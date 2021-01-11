package com.sizdev.arkhirefortalent.homepage.item.explore

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.sizdev.arkhireforcompany.homepage.item.explore.ExploreModel
import com.sizdev.arkhirefortalent.R
import com.sizdev.arkhirefortalent.databinding.ItemExploreProjectBinding
import com.sizdev.arkhirefortalent.homepage.item.explore.contributor.ContributorActivity
import com.squareup.picasso.Picasso

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

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ProjectExploreHolder, position: Int) {
        val item = items[position]
        holder.binding.tvProjectTitle.text = item.projectTitle
        holder.binding.tvProjectOwner.text = item.projectOwnerName
        holder.binding.tvProjectSalary.text = item.projectSallary
        holder.binding.tvPostedAt.text = item.postedAt

        when (item.projectOwnerImage){
            null -> holder.binding.ivProjectCompanyImage.setImageResource(R.drawable.ic_project_bg)
            else -> {
                Picasso.get()
                    .load("http://54.82.81.23:911/image/${item.projectOwnerImage}")
                    .resize(1280, 500)
                    .centerCrop()
                    .into(holder.binding.ivProjectCompanyImage)
            }
        }


        holder.itemView.setOnClickListener {
            val item = items[position]
            val context = holder.binding.itemExploreHolder.context
            val intent = Intent(context, ContributorActivity::class.java)
            intent.putExtra("projectTitle", item.projectTitle)
            intent.putExtra("projectOwner", item.projectOwner)
            intent.putExtra("projectSalary", item.projectSallary)
            intent.putExtra("projectImage", item.projectOwnerImage)
            intent.putExtra("projectOwnerName", item.projectOwnerName)

            context.startActivity(intent)

        }
    }

    override fun getItemCount(): Int = items.size
}