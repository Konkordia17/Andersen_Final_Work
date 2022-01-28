package com.example.andersen_final_work.domain.usecase.characters_usecase

import com.example.andersen_final_work.domain.models.Character
import com.example.andersen_final_work.domain.repository.CharactersRepository
import javax.inject.Inject

class GetResidentUseCase @Inject constructor(private val repository: CharactersRepository) {
    suspend fun getResident(
        idLocation: Int,
        id: Int,
    ): Character? {
        return repository.getSingleCharacterForLocation(
            id = id,
            idLocation = idLocation
        )
    }
}