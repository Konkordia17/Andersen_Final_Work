package com.example.andersen_final_work.domain.usecase.characters_usecase

import com.example.andersen_final_work.domain.repository.CharactersRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class GetCharactersUseCase
@Inject constructor(val charactersRepository: CharactersRepository) {

    fun getCharacters(
        page: Int,
        scope: CoroutineScope,
        onSuccess: (Int?) -> Unit,
        onError: () -> Unit
    ) {
        scope.launch {
            charactersRepository.getCharacters(
                page = page,
                onSuccess = onSuccess,
                onError = onError
            )
        }
    }
}