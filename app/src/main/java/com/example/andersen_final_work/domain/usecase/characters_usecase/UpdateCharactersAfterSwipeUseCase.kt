package com.example.andersen_final_work.domain.usecase.characters_usecase

import com.example.andersen_final_work.domain.repository.CharactersRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class UpdateCharactersAfterSwipeUseCase
@Inject constructor(val charactersRepository: CharactersRepository) {

    fun updateAfterSwipe(scope: CoroutineScope, setCurrentPage: () -> Unit) {
        scope.launch {
            charactersRepository.updateCharacters(0) {
                setCurrentPage.invoke()
            }
        }
    }
}
