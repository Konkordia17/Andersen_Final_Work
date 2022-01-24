package com.example.andersen_final_work.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.example.andersen_final_work.data.storage.api.Api
import com.example.andersen_final_work.data.storage.db.RickAndMortyDB
import com.example.andersen_final_work.data.storage.models.CharacterEntity
import com.example.andersen_final_work.data.storage.models.EpisodeCharacterCrossRef
import com.example.andersen_final_work.data.storage.models.LocationCharacterCrossRef
import com.example.andersen_final_work.domain.models.Character
import com.example.andersen_final_work.domain.models.Location
import com.example.andersen_final_work.domain.models.Origin
import com.example.andersen_final_work.domain.repository.CharactersRepository
import javax.inject.Inject

class CharactersRepositoryImpl @Inject constructor(
    private val api: Api,
    private val dataBase: RickAndMortyDB
) :
    CharactersRepository {

    private fun mapCharacterEntityToCharacterDomain(character: CharacterEntity): Character {
        return Character(
            id = character.id,
            name = character.name,
            species = character.species,
            gender = character.gender,
            status = character.status,
            image = character.image,
            type = character.type,
            origin = Origin(
                name = character.origin.name,
                url = character.origin.url
            ),
            location = Location(
                name = character.location.name,
                url = character.location.url
            ),
            episode = character.episode
        )
    }

    override fun getCharactersFromDB(): LiveData<List<Character>> {
        return dataBase.charactersDao().getCharacters()
            .map { it -> it.map { mapCharacterEntityToCharacterDomain(it) } }
    }

    override suspend fun getCharacters(page: Int, onSuccess: (Int?) -> Unit, onError: () -> Unit) {
        try {
            val heroes = api.getCharactersByPage(page)
            dataBase.charactersDao()
                .insertCharacters(heroes.results)
            onSuccess.invoke(heroes.info.pages)
        } catch (e: Exception) {
            onError()
            Log.d("TAG", "getCharacters: asdf")
        }
    }

    override suspend fun getCharactersForLocation(
        idLocation: Int,
        id: String,
        onSuccess: (character: List<Character?>) -> Unit
    ) {
        try {
            val characters = api.getListCharacter(id)
            dataBase.charactersDao()
                .insertCharacters(characters)
            characters.forEach {
                dataBase.locationDao().insertLocationCharactersCrossRef(
                    LocationCharacterCrossRef(
                        locationId = idLocation,
                        characterId = it.id
                    )
                )
            }
            onSuccess(characters.map { mapCharacterEntityToCharacterDomain(it) })
        } catch (e: Exception) {
            val charactersWithDB = dataBase.locationDao().getLocationWithCharacters(idLocation)
            onSuccess(charactersWithDB.characters.map { mapCharacterEntityToCharacterDomain(it) })
            Log.d("TAG", "getEpisodes: asdf")
        }
    }

    override suspend fun getCharactersForEpisodes(
        episodeId: Int,
        id: String,
        onSuccess: (character: List<Character?>) -> Unit
    ) {
        try {
            val characters = api.getListCharacter(id)
            dataBase.charactersDao()
                .insertCharacters(characters)
            characters.forEach {
                dataBase.episodeDao().insertEpisodeCharactersCrossRef(
                    crossRef = EpisodeCharacterCrossRef(
                        episodeId = episodeId,
                        characterId = it.id
                    )
                )
            }
            onSuccess(characters.map { mapCharacterEntityToCharacterDomain(it) })
        } catch (e: Exception) {
            val characters = dataBase.episodeDao().getEpisodeWithCharacters(episodeId)
            onSuccess(characters.characters.map { mapCharacterEntityToCharacterDomain(it) })
            Log.d("TAG", "getEpisodes: asdf")
        }
    }

    override suspend fun updateCharacters(page: Int, callback: () -> Unit) {
        try {
            val heroes = api.getCharactersByPage(page)
            dataBase.charactersDao().deleteAllCharacters()
            dataBase.charactersDao()
                .insertCharacters(heroes.results)
            callback.invoke()
        } catch (e: Exception) {
            Log.d("TAG", "getEpisodes: asdf")
        }
    }

    override suspend fun getSingleCharacterForLocation(
        idLocation: Int,
        id: Int,
        onSuccess: (Character?) -> Unit
    ) {
        try {
            val character = api.getCharacter(id)
            dataBase.locationDao().insertLocationCharactersCrossRef(
                crossRef = LocationCharacterCrossRef(idLocation, id)
            )
            onSuccess(mapCharacterEntityToCharacterDomain(character))
        } catch (e: Exception) {
            val character = getSingleCharacterFromDB(id)
            onSuccess(character)
            Log.d("TAG", "getCharacters: asdf")
        }
    }

    override suspend fun getSingleCharacter(id: Int, onSuccess: (Character?) -> Unit) {
        try {
            val character = api.getCharacter(id)
            onSuccess(mapCharacterEntityToCharacterDomain(character))
        } catch (e: Exception) {
            val character = getSingleCharacterFromDB(id)
            onSuccess(character)
            Log.d("TAG", "getCharacters: asdf")
        }
    }

    override suspend fun getSingleCharacterForEpisode(
        episodeId: Int,
        id: Int,
        onSuccess: (Character?) -> Unit
    ) {
        try {
            val character = api.getCharacter(id)
            dataBase.episodeDao().insertEpisodeCharactersCrossRef(
                crossRef = EpisodeCharacterCrossRef(
                    episodeId = episodeId,
                    characterId = id
                )
            )
            onSuccess(mapCharacterEntityToCharacterDomain(character))
        } catch (e: Exception) {
            val character = getSingleCharacterFromDB(id)
            onSuccess(character)
            Log.d("TAG", "getCharacters: asdf")
        }
    }

    override suspend fun getSingleCharacterFromDB(id: Int): Character {
        val characterEntity = dataBase.charactersDao().getSingleCharacter(id)
        return mapCharacterEntityToCharacterDomain(characterEntity)
    }
}