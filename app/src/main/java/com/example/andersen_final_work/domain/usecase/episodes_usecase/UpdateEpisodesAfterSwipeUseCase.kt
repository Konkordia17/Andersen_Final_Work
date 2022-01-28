package com.example.andersen_final_work.domain.usecase.episodes_usecase

import com.example.andersen_final_work.domain.repository.EpisodesRepository
import javax.inject.Inject

class UpdateEpisodesAfterSwipeUseCase
@Inject constructor(private val repository: EpisodesRepository) {
    suspend fun updateAfterSwipe(setCurrentPage: () -> Unit) {
        repository.updateEpisodes(0) {
            setCurrentPage.invoke()
        }
    }
}
