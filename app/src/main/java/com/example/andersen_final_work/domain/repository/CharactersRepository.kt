package com.example.andersen_final_work.domain.repository

import androidx.lifecycle.LiveData
import com.example.andersen_final_work.domain.models.Character

interface CharactersRepository {

    fun getCharactersFromDB(): LiveData<List<Character>>

    suspend fun getCharacters(page: Int, onSuccess: (Int?) -> Unit, onError: () -> Unit)

    suspend fun getCharactersForLocation(
        idLocation: Int,
        id: String,
    ): List<Character?>

    suspend fun getCharactersForEpisodes(
        episodeId: Int,
        id: String,
    ): List<Character?>

    suspend fun updateCharacters(page: Int, callback: () -> Unit)

    suspend fun getSingleCharacterForLocation(
        idLocation: Int,
        id: Int,
    ):Character?

    suspend fun getSingleCharacter(id: Int,
//                                   onSuccess: (Character?) -> Unit
    ):Character?

    suspend fun getSingleCharacterForEpisode(
        episodeId: Int,
        id: Int,
    ): Character?

    suspend fun getSingleCharacterFromDB(id: Int): Character
}