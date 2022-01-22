package com.example.andersen_final_work.presentation.ui.characters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.andersen_final_work.databinding.ItemCharacterBinding
import com.example.andersen_final_work.domain.models.Character

class CharactersAdapter(private val onItemClicked: (character: Character) -> Unit) :
    ListAdapter<Character, CharactersAdapter.CharacterHolder>(DiffUtilCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemCharacterBinding.inflate(inflater, parent, false)
        return CharacterHolder(binding, onItemClicked)
    }

    override fun onBindViewHolder(holder: CharacterHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class CharacterHolder(
        private val binding: ItemCharacterBinding,
        val onItemClicked: (character: Character) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(character: Character) {
            with(binding) {
                nameCharacter.text = character.name
                speciesCharacter.text = character.species
                statusCharacter.text = character.status
                genderCharacter.text = character.gender
                Glide.with(itemView)
                    .load(character.image)
                    .into(imageCharacter)
                root.setOnClickListener { onItemClicked(character) }
            }
        }
    }

    class DiffUtilCallback : DiffUtil.ItemCallback<Character>() {
        override fun areItemsTheSame(oldItem: Character, newItem: Character): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: Character, newItem: Character): Boolean {
            return oldItem == newItem
        }
    }
}