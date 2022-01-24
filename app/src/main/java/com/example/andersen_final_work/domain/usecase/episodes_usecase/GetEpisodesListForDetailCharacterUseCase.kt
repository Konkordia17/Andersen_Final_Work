package com.example.andersen_final_work.domain.usecase.episodes_usecase

import com.example.andersen_final_work.domain.models.Episode
import com.example.andersen_final_work.domain.repository.EpisodesRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class GetEpisodesListForDetailCharacterUseCase
@Inject constructor(private val episodesRepository: EpisodesRepository) {
    fun getEpisodesListForDetailCharacter(
        ids: String,
        scope: CoroutineScope,
        characterId: Int,
        onSuccess: (episode: List<Episode?>) -> Unit
    ) {
        scope.launch {
            episodesRepository.getEpisodesList(
                id = ids,
                onSuccess = onSuccess, characterId = characterId
            )
        }
    }
}