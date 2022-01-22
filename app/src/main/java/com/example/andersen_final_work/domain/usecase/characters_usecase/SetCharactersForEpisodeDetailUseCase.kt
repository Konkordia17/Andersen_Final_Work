package com.example.andersen_final_work.domain.usecase.characters_usecase

import com.example.andersen_final_work.domain.models.Episode
import javax.inject.Inject

class SetCharactersForEpisodeDetailUseCase @Inject constructor() {

    fun setCharacters(
        episode: Episode,
        getCharacter: (id: Int) -> Unit,
        getCharacters: (ids: String) -> Unit
    ) {
        val listCharacters = episode.characters
        var listId = emptyList<String>()
        if (listCharacters.size == 1) {
            val id = listCharacters[0].substringAfterLast('/').toInt()
            getCharacter(id)
        } else {
            listCharacters.forEach {
                listId = listId + (it.substringAfterLast('/'))
            }
            val ids = listId.joinToString(",")
            getCharacters(ids)
        }
    }
}