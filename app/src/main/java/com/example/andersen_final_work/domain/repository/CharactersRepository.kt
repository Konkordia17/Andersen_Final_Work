package com.example.andersen_final_work.domain.repository

import androidx.lifecycle.LiveData
import com.example.andersen_final_work.domain.models.Character

interface CharactersRepository {

    fun getCharactersFromDB(): LiveData<List<Character>>

    suspend fun getCharacters(page: Int, onSuccess: (Int?) -> Unit, onError: () -> Unit)

    suspend fun getCharactersForLocation(
        idLocation: Int,
        id: String,
        onSuccess: (character: List<Character?>) -> Unit
    )

    suspend fun getCharactersForEpisodes(
        episodeId: Int,
        id: String,
        onSuccess: (character: List<Character?>) -> Unit
    )

    suspend fun updateCharacters(page: Int, callback: () -> Unit)

    suspend fun getSingleCharacterForLocation(
        idLocation: Int,
        id: Int,
        onSuccess: (Character?) -> Unit
    )

    suspend fun getSingleCharacter(id: Int, onSuccess: (Character?) -> Unit)

    suspend fun getSingleCharacterForEpisode(
        episodeId: Int,
        id: Int,
        onSuccess: (Character?) -> Unit
    )

    suspend fun getSingleCharacterFromDB(id: Int): Character
}