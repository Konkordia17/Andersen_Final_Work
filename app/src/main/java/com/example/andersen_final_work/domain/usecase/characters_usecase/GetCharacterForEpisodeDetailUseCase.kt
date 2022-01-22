package com.example.andersen_final_work.domain.usecase.characters_usecase

import com.example.andersen_final_work.domain.models.Character
import com.example.andersen_final_work.domain.repository.CharactersRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class GetCharacterForEpisodeDetailUseCase
@Inject constructor(private val charactersRepository: CharactersRepository) {

    fun getCharacter(
        episodeId: Int,
        id: Int,
        scope: CoroutineScope,
        onSuccess: (character: Character?) -> Unit,
    ) {
        scope.launch {
            charactersRepository.getSingleCharacterForEpisode(
                id = id,
                onSuccess = onSuccess, episodeId = episodeId
            )
        }
    }
}