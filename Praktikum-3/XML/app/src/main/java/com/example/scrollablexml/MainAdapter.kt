package com.example.scrollablexml

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.scrollablexml.databinding.AdapterMainBinding
import com.example.scrollablexml.model.KamenRider

class MainAdapter (
    private val listRider: List<KamenRider>,
    private val onImdbClick: (String) -> Unit,
    private val onDetailClick: (Int) -> Unit

) : RecyclerView.Adapter<MainAdapter.ViewHolder>() {
    class ViewHolder(private val binding: AdapterMainBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(rider: KamenRider, onImdbClick: (String) -> Unit, onDetailClick: (Int) -> Unit) {

            binding.riderImage.setImageResource(rider.posterRes)
            binding.riderName.text = rider.name
            binding.riderYear.text = "${rider.year}"
            binding.descBody.text = rider.description
            binding.imdbBtn.setOnClickListener { onImdbClick(rider.imdbUrl) }
            binding.detailBtn.setOnClickListener { onDetailClick(rider.id) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = AdapterMainBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount() = listRider.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val rider = listRider[position]
        holder.bind(rider, onImdbClick, onDetailClick)
    }
}