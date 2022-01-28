package com.example.andersen_final_work.domain.usecase.characters_usecase

import com.example.andersen_final_work.domain.models.Character
import com.example.andersen_final_work.domain.repository.CharactersRepository
import javax.inject.Inject

class GetCharactersListForEpisodeDetailUseCase @Inject constructor(
    private val charactersRepository: CharactersRepository
) {
    suspend operator fun invoke(
        episodeId: Int,
        ids: String,
    ): List<Character?> {
        return charactersRepository.getCharactersForEpisodes(
            id = ids,
            episodeId = episodeId
        )
    }
}