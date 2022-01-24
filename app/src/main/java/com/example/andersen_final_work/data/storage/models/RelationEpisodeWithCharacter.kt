package com.example.andersen_final_work.data.storage.models

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Junction
import androidx.room.Relation

@Entity(primaryKeys = ["episodeId", "characterId"])
data class EpisodeCharacterCrossRef(
    val episodeId: Int,
    val characterId: Int
)

data class EpisodeWithCharacters(
    @Embedded val episode:EpisodeEntity,
    @Relation(
        parentColumn = "episodeId",
        entityColumn = "characterId",
        associateBy = Junction(EpisodeCharacterCrossRef::class)
    )
    val characters: List<CharacterEntity>
)