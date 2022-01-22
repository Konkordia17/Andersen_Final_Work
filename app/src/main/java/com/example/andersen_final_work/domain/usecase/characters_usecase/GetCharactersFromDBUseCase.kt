package com.example.andersen_final_work.domain.usecase.characters_usecase

import androidx.lifecycle.LiveData
import com.example.andersen_final_work.domain.models.Character
import com.example.andersen_final_work.domain.repository.CharactersRepository
import javax.inject.Inject

class GetCharactersFromDBUseCase
@Inject constructor(val charactersRepository: CharactersRepository) {

    fun getCharactersFromDB(): LiveData<List<Character>> {
        return charactersRepository.getCharactersFromDB()
    }
}