package com.example.andersen_final_work.domain.usecase.episodes_usecase

import com.example.andersen_final_work.domain.repository.EpisodesRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class GetEpisodesUseCase @Inject constructor(private val episodesRepository: EpisodesRepository) {
    fun getEpisodes(
        page: Int,
        scope: CoroutineScope,
        onSuccess: (Int?) -> Unit,
        onError: () -> Unit
    ) {
        scope.launch {
            episodesRepository.getAllEpisodes(
                page = page,
                onSuccess = onSuccess,
                onError = onError
            )
        }
    }
}