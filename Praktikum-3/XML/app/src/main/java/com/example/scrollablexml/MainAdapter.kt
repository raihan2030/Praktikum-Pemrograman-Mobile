package com.example.scrollablexml

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.scrollablexml.model.KamenRider

class MainAdapter (
    private val listRider: List<KamenRider>,
    private val onImdbClick: (String) -> Unit,
    private val onDetailClick: (Int) -> Unit

) : RecyclerView.Adapter<MainAdapter.ViewHolder>() {
    class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        val riderImage = view.findViewById<ImageView>(R.id.riderImage)
        val riderName = view.findViewById<TextView>(R.id.riderName)
        val riderYear = view.findViewById<TextView>(R.id.riderYear)
        val riderDesc = view.findViewById<TextView>(R.id.descBody)
        val imdbBtn = view.findViewById<Button>(R.id.imdbBtn)
        val detailBtn = view.findViewById<Button>(R.id.detailBtn)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.adapter_main, parent, false))
    }

    override fun getItemCount() = listRider.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val rider = listRider[position]
        holder.riderImage.setImageResource(rider.posterRes)
        holder.riderName.text = rider.name
        holder.riderYear.text = "${rider.year}"
        holder.riderDesc.text = rider.description
        holder.imdbBtn.setOnClickListener { onImdbClick(rider.imdbUrl) }
        holder.detailBtn.setOnClickListener { onDetailClick(rider.id) }
    }
}