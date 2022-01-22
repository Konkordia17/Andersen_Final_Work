package com.example.andersen_final_work.presentation.ui.locations

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.andersen_final_work.databinding.ItemLocationBinding
import com.example.andersen_final_work.domain.models.Locations

class LocationAdapter(private val onItemClicked: (location: Locations) -> Unit) :
    ListAdapter<Locations, LocationAdapter.LocationsHolder>(DiffUtilCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationsHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemLocationBinding.inflate(inflater, parent, false)
        return LocationsHolder(binding, onItemClicked)
    }

    override fun onBindViewHolder(holder: LocationsHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class LocationsHolder(
        private val binding: ItemLocationBinding,
        val onItemClicked: (location: Locations) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(location: Locations) {
            with(binding) {
                nameLocation.text = location.name
                typeLocation.text = location.type
                dimension.text = location.dimension
            }
            binding.root.setOnClickListener {
                onItemClicked(location)
            }
        }
    }

    class DiffUtilCallback : DiffUtil.ItemCallback<Locations>() {
        override fun areItemsTheSame(oldItem: Locations, newItem: Locations): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Locations, newItem: Locations): Boolean {
            return oldItem == newItem
        }
    }
}