package com.example.andersen_final_work.domain.usecase

import androidx.lifecycle.MutableLiveData
import com.example.andersen_final_work.domain.models.Character
import com.example.andersen_final_work.domain.models.Location
import com.example.andersen_final_work.domain.models.Origin
import com.example.andersen_final_work.domain.repository.CharactersRepository
import com.example.andersen_final_work.domain.usecase.characters_usecase.GetCharactersFromDBUseCase
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.kotlin.mock

class GetCharactersFromDBUseCaseTest {

    val repository = mock<CharactersRepository>()

    @Test
    fun test_should_return_character_as_in_db() {
        val testCharacter = MutableLiveData(
            listOf(
                Character(
                    1, "",
                    "", "", "", "", "", Origin("", ""), Location("", ""),
                    listOf("")
                )
            )
        )


        Mockito.`when`(repository.getCharactersFromDB())
            .thenReturn(testCharacter)

        val characters = GetCharactersFromDBUseCase(repository)
        val actual = characters.getCharactersFromDB().value
        val expected = listOf(
            Character(
                1, "",
                "", "", "", "", "", Origin("", ""), Location("", ""),
                listOf("")
            )
        )

        Assertions.assertEquals(expected, actual)
    }
}