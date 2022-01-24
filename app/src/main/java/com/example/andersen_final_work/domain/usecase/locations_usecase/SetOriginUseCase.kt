package com.example.andersen_final_work.domain.usecase.locations_usecase

import com.example.andersen_final_work.domain.models.Character
import javax.inject.Inject

class SetOriginUseCase @Inject constructor() {
    fun setOrigin(character: Character): String {
        return if (character.origin.url == "") {
            character.origin.url
        } else {
            character.origin.url.substringAfterLast('/')
        }
    }
}