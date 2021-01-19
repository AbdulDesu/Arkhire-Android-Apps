package com.sizdev.arkhirefortalent.homepage.item.explore.contributor

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.sizdev.arkhirefortalent.R
import com.sizdev.arkhirefortalent.databinding.ItemProjectContributorBinding
import com.sizdev.arkhirefortalent.homepage.item.account.profile.previewer.TalentPreviewActivity
import com.squareup.picasso.Picasso

class ContributorAdapter : RecyclerView.Adapter<ContributorAdapter.ContributorHolder>() {
    private var items = mutableListOf<ContributorModel>()

    fun addList(list: List<ContributorModel>){
        items.clear()
        items.addAll(list)
        notifyDataSetChanged()
    }

    class ContributorHolder(val binding: ItemProjectContributorBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContributorHolder {
        return ContributorHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_project_contributor, parent, false))
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ContributorHolder, position: Int) {
        val item = items[position]
        holder.binding.tvTalentName.text = item.accountName
        holder.binding.tvTalentTitle.text = item.accountTitle

        when(item.talentImage){
            null -> holder.binding.ivTalentImage.setImageResource(R.drawable.ic_empty_image)
            else -> {
                Picasso.get()
                        .load("http://54.82.81.23:911/image/${item.talentImage}")
                        .resize(512, 512)
                        .centerCrop()
                        .into(holder.binding.ivTalentImage)
            }
        }

        holder.itemView.setOnClickListener {
            val item = items[position]
            val context = holder.binding.contributorHolder.context
            val intent = Intent(context, TalentPreviewActivity::class.java)
            intent.putExtra("talentID", item.talentID)
            intent.putExtra("previewCode", "guest")
            intent.putExtra("guestAccountID", item.talentAccountID)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = items.size
}