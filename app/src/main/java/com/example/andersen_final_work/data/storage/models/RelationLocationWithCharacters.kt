package com.example.andersen_final_work.data.storage.models

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Junction
import androidx.room.Relation

@Entity(primaryKeys = ["locationId", "characterId"])
data class LocationCharacterCrossRef(
    val locationId: Int,
    val characterId: Int
)

data class LocationWithCharacters(
    @Embedded val location: LocationEntity,
    @Relation(
        parentColumn = "locationId",
        entityColumn = "characterId",
        associateBy = Junction(LocationCharacterCrossRef::class)
    )
    val characters: List<CharacterEntity>
)