package com.example.andersen_final_work.domain.usecase.characters_usecase

import com.example.andersen_final_work.domain.models.Character
import com.example.andersen_final_work.domain.repository.CharactersRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class GetCharactersListForEpisodeDetailUseCase @Inject constructor
    (private val charactersRepository: CharactersRepository) {

    fun getCharacters(
        episodeId: Int,
        ids: String,
        scope: CoroutineScope,
        onSuccess: (List<Character?>) -> Unit
    ) {
        scope.launch {
            charactersRepository.getCharactersForEpisodes(
                id = ids,
                onSuccess = onSuccess,
                episodeId = episodeId
            )
        }
    }
}