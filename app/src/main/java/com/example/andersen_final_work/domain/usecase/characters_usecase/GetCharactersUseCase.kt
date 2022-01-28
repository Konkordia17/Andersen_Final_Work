package com.example.andersen_final_work.domain.usecase.characters_usecase

import com.example.andersen_final_work.domain.repository.CharactersRepository
import javax.inject.Inject

class GetCharactersUseCase
@Inject constructor(val charactersRepository: CharactersRepository) {

   suspend fun getCharacters(
        page: Int,
        onSuccess: (Int?) -> Unit,
        onError: () -> Unit
    ) {
            charactersRepository.getCharacters(
                page = page,
                onSuccess = onSuccess,
                onError = onError
            )
        }
    }