package com.example.andersen_final_work.domain.usecase.characters_usecase

import com.example.andersen_final_work.domain.models.Character
import com.example.andersen_final_work.domain.repository.CharactersRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class GetResidentsUseCase @Inject constructor(private val repository: CharactersRepository) {

    fun getResidents(
        scope: CoroutineScope,
        onSuccess: (List<Character?>) -> Unit,
        idLocation: Int,
        ids: String
    ) {
        scope.launch {
            repository.getCharactersForLocation(
                id = ids,
                onSuccess = onSuccess,
                idLocation = idLocation
            )
        }
    }
}