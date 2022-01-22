package com.example.andersen_final_work.domain.usecase.characters_usecase

import com.example.andersen_final_work.domain.models.Character
import com.example.andersen_final_work.domain.repository.CharactersRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class GetCharacterUseCase @Inject constructor(
    private val charactersRepository: CharactersRepository
) {

    fun getSingleCharacter(
        id: Int,
        scope: CoroutineScope,
        onSuccess: (Character?) -> Unit
    ) {
        scope.launch {
            charactersRepository.getSingleCharacter(
                id = id,
                onSuccess = onSuccess
            )
        }
    }
}