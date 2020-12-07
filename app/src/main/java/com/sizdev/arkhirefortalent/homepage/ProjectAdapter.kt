package com.sizdev.arkhirefortalent.homepage

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sizdev.arkhirefortalent.R
import kotlinx.android.synthetic.main.project_image.view.*


class ProjectAdapter(val project: List<Int>) : RecyclerView.Adapter<ProjectAdapter.ProjectViewPagerHolder>() {

    inner class ProjectViewPagerHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProjectViewPagerHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.project_image, parent, false)
        return ProjectViewPagerHolder(view)
    }

    override fun getItemCount(): Int {
        return project.size
    }

    override fun onBindViewHolder(holder: ProjectViewPagerHolder, position: Int) {
        val currentProject = project[position]
        holder.itemView.iv_projectCard.setImageResource(currentProject)
    }


}