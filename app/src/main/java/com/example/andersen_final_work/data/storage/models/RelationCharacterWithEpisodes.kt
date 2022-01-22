package com.example.andersen_final_work.data.storage.models

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Junction
import androidx.room.Relation

@Entity(primaryKeys = ["characterId", "episodeId"])
data class CharacterEpisodeCrossRef(
    val characterId: Int,
    val episodeId: Int
)

data class CharacterWithEpisodes(
    @Embedded val character: CharacterEntity,
    @Relation(
        parentColumn = "characterId",
        entityColumn = "episodeId",
        associateBy = Junction(CharacterEpisodeCrossRef::class)
    )
    val episodes: List<EpisodeEntity>
)
