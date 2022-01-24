package com.example.andersen_final_work.data.storage.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.andersen_final_work.Contract
import com.example.andersen_final_work.data.storage.models.CharacterEntity
import com.example.andersen_final_work.data.storage.models.CharacterEpisodeCrossRef
import com.example.andersen_final_work.data.storage.models.CharacterWithEpisodes

@Dao
interface CharactersDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCharacters(characters: List<CharacterEntity>)

    @Query("DELETE FROM characters")
    suspend fun deleteAllCharacters()

    @Query("SELECT * FROM characters")
    fun getCharacters(): LiveData<List<CharacterEntity>>

    @Query(
        "SELECT * FROM ${Contract.TABLE_NAME} WHERE " +
                "characterId LIKE :id"
    )
    suspend fun getSingleCharacter(id: Int): CharacterEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCharacterEpisodesCrossRef(crossRef: CharacterEpisodeCrossRef)

    @Transaction
    @Query("SELECT * FROM characters WHERE characterId = :characterId")
    suspend fun getCharactersWithEpisodes(characterId: Int): CharacterWithEpisodes
}