package com.example.andersen_final_work.domain.usecase.episodes_usecase

import com.example.andersen_final_work.domain.models.Character
import javax.inject.Inject

class SetEpisodesUseCase @Inject constructor() {
    fun setEpisodes(
        character: Character,
        getEpisode: (String) -> Unit,
        getEpisodesListForDetailCharacter: (String) -> Unit
    ) {
        val listEpisodes = character.episode
        var listId = emptyList<String>()
        if (listEpisodes.size == 1) {
            val id = listEpisodes[0].substringAfterLast('/')
            getEpisode(id)
        } else {
            listEpisodes.forEach {
                listId = listId + (it.substringAfterLast('/'))
            }
            val ids = listId.joinToString(",")
            getEpisodesListForDetailCharacter(ids)
        }
    }
}