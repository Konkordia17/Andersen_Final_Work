package com.example.andersen_final_work.domain.usecase.characters_usecase

import com.example.andersen_final_work.domain.models.Character
import com.example.andersen_final_work.domain.repository.CharactersRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class GetResidentUseCase @Inject constructor(private val repository: CharactersRepository) {
    fun getResident(
        scope: CoroutineScope,
        idLocation: Int,
        id: Int,
        onSuccess: (Character?) -> Unit
    ) {
        scope.launch {
            repository.getSingleCharacterForLocation(
                id = id,
                onSuccess = onSuccess, idLocation = idLocation
            )
        }
    }
}