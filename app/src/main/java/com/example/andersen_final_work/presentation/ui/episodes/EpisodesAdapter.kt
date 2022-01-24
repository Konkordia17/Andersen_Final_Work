package com.example.andersen_final_work.presentation.ui.episodes

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.andersen_final_work.databinding.ItemEpisodesBinding
import com.example.andersen_final_work.domain.models.Episode

class EpisodesAdapter(private val onItemClicked: (episode: Episode) -> Unit) :
    ListAdapter<Episode, EpisodesAdapter.EpisodesHolder>(DiffUtilCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EpisodesHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemEpisodesBinding.inflate(inflater, parent, false)
        return EpisodesHolder(binding, onItemClicked)
    }

    override fun onBindViewHolder(holder: EpisodesHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class EpisodesHolder(
        private val binding: ItemEpisodesBinding,
        val onItemClicked: (episode: Episode) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(episode: Episode) {
            val str = episode.episode.split("E")
            val seasonStr = str[0].substringAfterLast("S")
            val seriesStr = str[1]
            with(binding) {
                nameEpisode.text = episode.name
                seasonNumber.text = seasonStr
                seriesNumber.text = seriesStr
                airDateEpisode.text = episode.air_date
            }
            binding.root.setOnClickListener {
                onItemClicked(episode)
            }
        }
    }

    class DiffUtilCallback : DiffUtil.ItemCallback<Episode>() {
        override fun areItemsTheSame(oldItem: Episode, newItem: Episode): Boolean {
            return oldItem.episodeId == newItem.episodeId
        }

        override fun areContentsTheSame(oldItem: Episode, newItem: Episode): Boolean {
            return oldItem == newItem
        }
    }
}