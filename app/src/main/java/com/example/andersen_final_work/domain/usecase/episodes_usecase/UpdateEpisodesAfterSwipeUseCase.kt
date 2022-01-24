package com.example.andersen_final_work.domain.usecase.episodes_usecase

import com.example.andersen_final_work.domain.repository.EpisodesRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class UpdateEpisodesAfterSwipeUseCase
@Inject constructor(private val repository: EpisodesRepository) {
    fun updateAfterSwipe(scope: CoroutineScope, setCurrentPage: () -> Unit) {
        scope.launch {
            repository.updateEpisodes(0) {
                setCurrentPage.invoke()
            }
        }
    }
}
