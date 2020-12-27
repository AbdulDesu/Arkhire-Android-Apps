package com.sizdev.arkhirefortalent.homepage.item.home.project.highlightproject

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sizdev.arkhirefortalent.R
import de.hdodenhof.circleimageview.CircleImageView


class HighLightProjectAdapter : RecyclerView.Adapter<HighLightProjectAdapter.ProjectHolder>() {

    val listProjectName = listOf("Hololive Apps", "Traveloka Apps", "Bukalapak Apps", "Tokopedia Apps", "Shopee Apps", "Cooking Apps")
    val listCompanyName = listOf("Hololive ID", "Traveloka.inc", "BukalapakID", "Tokopedia.com", "Shopee.com", "JapanFood.com")
    val listSallaryProject = listOf("Rp.12.000.000", "Rp.8.000.000", "Rp.5.000.000", "Rp.4.000.000", "Rp.3.000.000", "Rp.2.000.000")
    val listProjectID = listOf("000001", "000002", "000003", "000004", "000005", "000006")
    val listCompanyImage = listOf(R.drawable.ic_hololive, R.drawable.ic_traveloka, R.drawable.ic_bukalapak, R.drawable.ic_tokopedia, R.drawable.ic_shopee, R.drawable.ic_cooking)

    class ProjectHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var highlightProjectName: TextView = itemView.findViewById(R.id.tv_highLightProjectName)
        var highlightCompanyName: TextView = itemView.findViewById(R.id.tv_highlightCompanyName)
        var highlightProjectSallary: TextView = itemView.findViewById(R.id.tv_highlightProjectSallary)
        var highlightProjectId: TextView = itemView.findViewById(R.id.tv_highlightProjectID)
        var highlightCompanyPhoto: CircleImageView = itemView.findViewById(R.id.iv_highLightProjectImage)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProjectHolder {
        return ProjectHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_talent_project_highlight, parent, false))
    }

    override fun onBindViewHolder(holder: ProjectHolder, position: Int) {
        holder.highlightProjectName.text = listProjectName[position]
        holder.highlightCompanyName.text = listCompanyName[position]
        holder.highlightProjectSallary.text = listSallaryProject[position]
        holder.highlightProjectId.text = listProjectID[position]
        holder.highlightCompanyPhoto.setImageResource(listCompanyImage[position])
    }

    override fun getItemCount(): Int = listProjectName.size


}