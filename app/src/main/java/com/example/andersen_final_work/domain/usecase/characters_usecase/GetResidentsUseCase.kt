package com.example.andersen_final_work.domain.usecase.characters_usecase

import com.example.andersen_final_work.domain.models.Character
import com.example.andersen_final_work.domain.repository.CharactersRepository
import javax.inject.Inject

class GetResidentsUseCase @Inject constructor(private val repository: CharactersRepository) {

    suspend fun getResidents(
        idLocation: Int,
        ids: String
    ): List<Character?> {
        return repository.getCharactersForLocation(
            id = ids,
            idLocation = idLocation
        )
    }
}