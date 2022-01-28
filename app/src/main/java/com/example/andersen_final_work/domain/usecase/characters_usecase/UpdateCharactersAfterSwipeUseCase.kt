package com.example.andersen_final_work.domain.usecase.characters_usecase

import com.example.andersen_final_work.domain.repository.CharactersRepository
import javax.inject.Inject

class UpdateCharactersAfterSwipeUseCase
@Inject constructor(val charactersRepository: CharactersRepository) {

    suspend fun updateAfterSwipe(setCurrentPage: () -> Unit) {
        charactersRepository.updateCharacters(0) {
            setCurrentPage.invoke()
        }
    }
}
