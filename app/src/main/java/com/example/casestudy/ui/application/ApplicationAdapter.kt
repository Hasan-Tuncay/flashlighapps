package com.example.casestudy.ui.application

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.Coil
import coil.load
import com.bumptech.glide.Glide
import com.example.casestudy.R
import com.example.casestudy.databinding.AppLayoutBinding
import com.example.casestudy.model.App
import com.google.android.material.card.MaterialCardView

class ApplicationAdapter(val listener: ApplicationClickListener) :
    RecyclerView.Adapter<ApplicationAdapter.ApplicationViewHolder>() {
    var appList = listOf<App>()


    fun updateAppList(list: List<App>) {
        appList = list
        notifyDataSetChanged()
    }

    inner class ApplicationViewHolder(  view: View,val binding: AppLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
//        var iconImageView: ImageView = view.findViewById(R.id.icon)              //RecyclerView.ViewHolder(binding.root)
//        var nameTextView: TextView = view.findViewById(R.id.name)
//        val ratingValueTextView: TextView = view.findViewById(R.id.ratingValue)
//        val ratingCountTextView: TextView = view.findViewById(R.id.ratingCount)
//        val downloadCountsTextView: TextView = view.findViewById(R.id.downloadCounts)
//       val cardView: MaterialCardView = view.findViewById(R.id.app_card_layout)
        var iconImageView: ImageView =binding.icon
        var nameTextView: TextView =binding.name
        val ratingValueTextView: TextView = binding.ratingValue
        val ratingCountTextView: TextView = binding.ratingCount
        val downloadCountsTextView: TextView = binding.downloadCounts
        val cardView: MaterialCardView = binding.appCardLayout


    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ApplicationViewHolder {
//        val binding =AppLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false)
////        val adapterLayout = LayoutInflater.from(parent.context)
////            .inflate(R.layout.app_layout, parent, false)
//
//        return ApplicationViewHolder(binding)
//    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ApplicationViewHolder {
        val binding = AppLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ApplicationViewHolder(binding.root,binding)
    }

    override fun getItemCount(): Int {
        return appList.size
    }


    override fun onBindViewHolder(holder: ApplicationViewHolder, position: Int) {
        val item = appList[position]
//        Glide.with(holder.iconImageView.context).load(item.iconUrl).into(holder.iconImageView)
        holder.iconImageView.load(item.iconUrl)
        holder.binding.name.text = "Name : ${item.name}"
        holder.binding.ratingValue.text = "Rating Value : ${item.ratingValue.toString()}"
        holder.binding.ratingCount.text = "Rating Count : ${item.ratingCount.toString()}"
        holder.binding.downloadCounts.text = "Download Counts : ${item.downloads}"
        holder.cardView.setOnClickListener {
            listener.onApplicationClicked(item)
        }

    }




}


