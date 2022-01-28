package com.example.andersen_final_work.domain.usecase.characters_usecase

import com.example.andersen_final_work.domain.models.Character
import com.example.andersen_final_work.domain.repository.CharactersRepository
import javax.inject.Inject

class GetCharacterForEpisodeDetailUseCase
@Inject constructor(private val charactersRepository: CharactersRepository) {

    suspend fun getCharacter(
        episodeId: Int,
        id: Int,
    ): Character? {
        return charactersRepository.getSingleCharacterForEpisode(
            id = id,
            episodeId = episodeId
        )
    }
}