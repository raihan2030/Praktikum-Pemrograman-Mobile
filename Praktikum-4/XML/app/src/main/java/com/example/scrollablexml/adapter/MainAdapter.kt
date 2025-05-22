package com.example.scrollablexml.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.scrollablexml.databinding.AdapterMainBinding
import com.example.scrollablexml.model.KamenRider
import timber.log.Timber
import java.util.Locale

class MainAdapter(
    initialList: List<KamenRider>,
    private val onImdbClick: (String) -> Unit,
    private val onDetailClick: (KamenRider) -> Unit
) : RecyclerView.Adapter<MainAdapter.ViewHolder>() {

    private val riderList = initialList.toMutableList()

    class ViewHolder(private val binding: AdapterMainBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(
            rider: KamenRider,
            onImdbClick: (String) -> Unit,
            onDetailClick: (KamenRider) -> Unit
        ) {
            binding.riderImage.setImageResource(rider.posterRes)
            binding.riderName.text = rider.name
            binding.riderYear.text = String.format(Locale.getDefault(), "%d", rider.year)
            binding.descBody.text = rider.description
            binding.imdbBtn.setOnClickListener {
                Timber.tag("MainAdapter").i("Tombol Eksplisit Intent ditekan untuk: ${rider.name}, URL: ${rider.imdbUrl}")
                onImdbClick(rider.imdbUrl)
            }
            binding.detailBtn.setOnClickListener {
                Timber.tag("MainAdapter").i("Tombol Detail ditekan untuk: ${rider.name}, ID: ${rider.id}")
                onDetailClick(rider)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = AdapterMainBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = riderList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(riderList[position], onImdbClick, onDetailClick)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(newList: List<KamenRider>) {
        riderList.clear()
        riderList.addAll(newList)
        notifyDataSetChanged()
    }
}
