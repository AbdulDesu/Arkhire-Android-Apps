package com.sizdev.arkhirefortalent.homepage.item.company

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.sizdev.arkhirefortalent.R
import com.sizdev.arkhirefortalent.databinding.ItemCompanyListBinding
import com.sizdev.arkhirefortalent.homepage.item.company.profile.CompanyProfileActivity
import com.squareup.picasso.Picasso

class SearchCompanyAdapter : RecyclerView.Adapter<SearchCompanyAdapter.SearchCompanyHolder>() {
    private var items = mutableListOf<SearchCompanyModel>()

    fun addList(list: List<SearchCompanyModel>){
        items.clear()
        items.addAll(list)
        notifyDataSetChanged()
    }

    class SearchCompanyHolder(val binding: ItemCompanyListBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchCompanyHolder {
        return SearchCompanyHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_company_list, parent, false))
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: SearchCompanyHolder, position: Int) {
        val item = items[position]
        holder.binding.tvCompanyName.text = item.companyName
        holder.binding.tvCompanyLocation.text = item.companyLocation
        when(item.companyType){
            null -> holder.binding.showAllCompanyItem.visibility = View.GONE
            else -> {
                holder.binding.showAllCompanyItem.visibility = View.VISIBLE
                holder.binding.tvCompanyType.text = item.companyType
            }
        }
        Picasso.get()
                .load("http://54.82.81.23:911/image/${item.companyImage}")
                .resize(86, 86)
                .centerCrop()
                .into(holder.binding.ivCompanyImage)

        holder.itemView.setOnClickListener {
            val item = items[position]
            val context = holder.binding.showAllCompanyItem.context
            val intent = Intent(context, CompanyProfileActivity::class.java)
            val companyName = item.companyName.toString()
            val companyType = item.companyType.toString()
            val companyImage = item.companyImage.toString()
            val companyLinkedin = item.companyLinkedin.toString()
            val companyInstagram = item.companyInstagram.toString()
            val companyFacebook = item.companyFacebook.toString()
            val companyDesc = item.companyDesc.toString()
            val companyLatitude = item.companyLatitude.toString()
            val companyLongitude = item.companyLongitude.toString()

            intent.putExtra("companyName", companyName)
            intent.putExtra("companyType", companyType)
            intent.putExtra("companyImage", companyImage)
            intent.putExtra("companyLinkedin", companyLinkedin)
            intent.putExtra("companyInstagram", companyInstagram)
            intent.putExtra("companyFacebook", companyFacebook)
            intent.putExtra("companyDesc", companyDesc)
            intent.putExtra("companyLatitude", companyLatitude)
            intent.putExtra("companyLongitude", companyLongitude)

            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = items.size
}