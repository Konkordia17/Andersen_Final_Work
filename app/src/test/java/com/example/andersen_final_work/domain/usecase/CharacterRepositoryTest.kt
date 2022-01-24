package com.example.andersen_final_work.domain.usecase

import com.example.andersen_final_work.data.repository.CharactersRepositoryImpl
import com.example.andersen_final_work.data.storage.api.Api
import com.example.andersen_final_work.data.storage.db.CharactersDao
import com.example.andersen_final_work.data.storage.db.RickAndMortyDB
import com.example.andersen_final_work.data.storage.models.CharacterEntity
import com.example.andersen_final_work.data.storage.models.Location
import com.example.andersen_final_work.data.storage.models.Origin
import com.example.andersen_final_work.domain.models.Character
import io.mockk.*
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test


const val DEFAULT_ID = 5
val character = CharacterEntity(
    5, "",
    "", "", "", "", "", Origin("", ""),
   Location("", ""),
    listOf("")
)

val characterExpected = Character(
    5, "",
    "", "", "", "", "",
    com.example.andersen_final_work.domain.models.Origin("", ""),
    com.example.andersen_final_work.domain.models.Location("", ""),
    listOf("")
)

class RepositoryTest {

    private lateinit var actualCharacter :Character
    @Test
    fun test_when_api_works_successfully_and_db_is_not_called() {
        val api = mockk<Api> {
            coEvery {
                getCharacter(DEFAULT_ID)
            } returns character
        }
        val dataBase = mockk<RickAndMortyDB>()
        val characterDao = mockk<CharactersDao>()

        val repository = CharactersRepositoryImpl(api, dataBase)

        runBlocking { repository.getSingleCharacter(DEFAULT_ID) {} }

        coVerify(exactly = 1) {
            api.getCharacter(DEFAULT_ID)

            listOf(characterDao) wasNot Called
        }
    }

    @Test
    fun test_when_get_single_character_from_db() {
        val dataBase = mockk<RickAndMortyDB>()
            coEvery {
                dataBase.charactersDao().getSingleCharacter(DEFAULT_ID)
            } returns character

        val api = mockk<Api>()

        val repository = CharactersRepositoryImpl(api, dataBase)

        runBlocking { actualCharacter = repository.getSingleCharacterFromDB(DEFAULT_ID) }
        Assertions.assertEquals(characterExpected, actualCharacter)
    }


    @Test
    fun test_when_api_works_successfully_and_called_map_fun() = runBlocking {
        val api = mockk<Api> {
            coEvery {
                getCharacter(DEFAULT_ID)
            } returns character
        }
        val dataBase = mockk<RickAndMortyDB>()
        val characterDao = mockk<CharactersDao>()

        val repository = spyk(CharactersRepositoryImpl(api, dataBase), recordPrivateCalls = true)

        repository.getSingleCharacter(DEFAULT_ID) {}

        coVerify(exactly = 1) {
            api.getCharacter(DEFAULT_ID)
            repository["mapCharacterEntityToCharacterDomain"](character)
            listOf(characterDao) wasNot Called
        }
    }
}
