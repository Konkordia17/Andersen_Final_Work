package com.example.andersen_final_work.domain.usecase.locations_usecase

import com.example.andersen_final_work.domain.models.Character
import javax.inject.Inject

class SetLocationUseCase @Inject constructor() {
    fun setLocation(character: Character): String {
        return if (character.location.url == "") {
            character.location.url
        } else character.location.url.substringAfterLast('/')
    }
}