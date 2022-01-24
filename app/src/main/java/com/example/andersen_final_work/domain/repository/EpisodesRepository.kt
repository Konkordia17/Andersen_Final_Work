package com.example.andersen_final_work.domain.repository

import androidx.lifecycle.LiveData
import com.example.andersen_final_work.domain.models.Episode
import io.reactivex.Single

interface EpisodesRepository {

    fun getEpisodesFromDB(): LiveData<List<Episode>>

    suspend fun getAllEpisodes(page: Int, onSuccess: (Int?) -> Unit, onError: () -> Unit)

    suspend fun getEpisodesList(
        id: String,
        characterId: Int,
        onSuccess: (episode: List<Episode?>) -> Unit
    )

    fun getEpisode(id: String): Single<Episode>

    suspend fun updateEpisodes(page: Int, callback: () -> Unit)

    fun getEpisodeFromDB(id: Int): Single<Episode>
}