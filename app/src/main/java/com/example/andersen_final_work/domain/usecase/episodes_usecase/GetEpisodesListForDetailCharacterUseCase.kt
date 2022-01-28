package com.example.andersen_final_work.domain.usecase.episodes_usecase

import com.example.andersen_final_work.domain.models.Episode
import com.example.andersen_final_work.domain.repository.EpisodesRepository
import javax.inject.Inject

class GetEpisodesListForDetailCharacterUseCase
@Inject constructor(private val episodesRepository: EpisodesRepository) {
    suspend fun getEpisodesListForDetailCharacter(
        ids: String,
        characterId: Int,
    ): List<Episode?> {
        return episodesRepository.getEpisodesList(
            id = ids,
            characterId = characterId
        )
    }
}