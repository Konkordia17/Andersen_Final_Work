package com.example.andersen_final_work.domain.usecase.episodes_usecase

import com.example.andersen_final_work.domain.repository.EpisodesRepository
import javax.inject.Inject

class GetEpisodesUseCase @Inject constructor(private val episodesRepository: EpisodesRepository) {
    suspend fun getEpisodes(
        page: Int,
        onSuccess: (Int?) -> Unit,
        onError: () -> Unit
    ) {
        episodesRepository.getAllEpisodes(
            page = page,
            onSuccess = onSuccess,
            onError = onError
        )
    }
}