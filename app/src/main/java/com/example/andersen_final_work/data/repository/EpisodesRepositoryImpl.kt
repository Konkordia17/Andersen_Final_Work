package com.example.andersen_final_work.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.example.andersen_final_work.data.storage.api.Api
import com.example.andersen_final_work.data.storage.db.RickAndMortyDB
import com.example.andersen_final_work.data.storage.models.CharacterEpisodeCrossRef
import com.example.andersen_final_work.data.storage.models.EpisodeEntity
import com.example.andersen_final_work.domain.models.Episode
import com.example.andersen_final_work.domain.repository.EpisodesRepository
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

class EpisodesRepositoryImpl(private val api: Api, private val dataBase: RickAndMortyDB) :
    EpisodesRepository {

    private fun mapEpisodeEntityToEpisodeDomain(episode: EpisodeEntity): Episode {
        return Episode(
            episodeId = episode.episodeId,
            name = episode.name,
            air_date = episode.air_date,
            episode = episode.episode,
            characters = episode.characters,
            created = episode.created
        )
    }

    private fun mapEpisodeDomainToEpisodeEntity(episode: Episode): EpisodeEntity {
        return EpisodeEntity(
            episodeId = episode.episodeId,
            name = episode.name,
            air_date = episode.air_date,
            episode = episode.episode,
            characters = episode.characters,
            created = episode.created
        )
    }

    override fun getEpisodesFromDB(): LiveData<List<Episode>> {
        return dataBase.episodeDao().getEpisodes().map { it ->
            it.map { mapEpisodeEntityToEpisodeDomain(it) }
        }
    }

    override suspend fun getAllEpisodes(page: Int, onSuccess: (Int?) -> Unit, onError: () -> Unit) {
        try {
            val allEpisodes = api.getEpisodesByPage(page)
            dataBase.episodeDao().insertEpisodes(allEpisodes.results)
            onSuccess.invoke(allEpisodes.info.pages)
        } catch (e: Exception) {
            onError()
            Log.d("TAG", "getEpisodes: asdf")
        }
    }

    override suspend fun getEpisodesList(
        id: String,
        characterId: Int,
    ): List<Episode?> {
        return try {
            val episodes = api.getEpisodes(id)
            dataBase.episodeDao().insertEpisodes(episodes)
            episodes.forEach {
                dataBase.charactersDao().insertCharacterEpisodesCrossRef(
                    crossRef = CharacterEpisodeCrossRef(
                        characterId = characterId,
                        episodeId = it.episodeId
                    )
                )
            }
            episodes.map { mapEpisodeEntityToEpisodeDomain(it) }
        } catch (e: Exception) {
            val charactersWithEpisodes =
                dataBase.charactersDao().getCharactersWithEpisodes(characterId)
            Log.d("TAG", "getEpisodes: asdf")
            return charactersWithEpisodes.episodes.map { mapEpisodeEntityToEpisodeDomain(it) }
        }
    }

    override fun getEpisode(id: String): Single<Episode> {
        return api.getSingleEpisode(id).map { mapEpisodeEntityToEpisodeDomain(it) }
            .subscribeOn(Schedulers.io())
            .flatMap {
                dataBase.episodeDao().insertEpisode(mapEpisodeDomainToEpisodeEntity(it))
                    .andThen(Single.just(it))
            }
            .onErrorResumeNext {
                getEpisodeFromDB(id.toInt())
            }
    }

    override suspend fun updateEpisodes(page: Int, callback: () -> Unit) {
        try {
            val allEpisodes = api.getEpisodesByPage(page)
            dataBase.episodeDao().deleteAllEpisodes()
            dataBase.episodeDao().insertEpisodes(allEpisodes.results)
            callback.invoke()
        } catch (e: Exception) {
        }
    }

    override fun getEpisodeFromDB(id: Int): Single<Episode> {
        return dataBase.episodeDao().getSingleEpisode(id)
            .map { mapEpisodeEntityToEpisodeDomain(it) }
    }
}