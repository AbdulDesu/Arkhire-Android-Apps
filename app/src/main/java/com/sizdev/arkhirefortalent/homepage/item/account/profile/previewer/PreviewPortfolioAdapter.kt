package com.sizdev.arkhirefortalent.homepage.item.account.profile.previewer

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.sizdev.arkhirefortalent.R
import com.sizdev.arkhirefortalent.databinding.ItemPortfolioBinding
import com.sizdev.arkhirefortalent.homepage.item.account.profile.portfolio.PortfolioModel
import com.squareup.picasso.Picasso

class PreviewPortfolioAdapter : RecyclerView.Adapter<PreviewPortfolioAdapter.ShowPortfolioHolder>() {
    private var items = mutableListOf<PortfolioModel>()

    fun addList(list: List<PortfolioModel>) {
        items.clear()
        items.addAll(list)
        notifyDataSetChanged()
    }

    class ShowPortfolioHolder(val binding: ItemPortfolioBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShowPortfolioHolder {
        return ShowPortfolioHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_portfolio, parent, false))
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ShowPortfolioHolder, position: Int) {
        val item = items[position]
        when (item.portfolioImage) {
            null -> holder.binding.ivPortfolio.setImageResource(R.drawable.ic_project_bg)
            else -> {
                Picasso.get()
                        .load("http://54.82.81.23:911/image/${item.portfolioImage}")
                        .resize(1280, 720)
                        .centerCrop()
                        .into(holder.binding.ivPortfolio)
            }
        }
    }

    override fun getItemCount(): Int = items.size
}