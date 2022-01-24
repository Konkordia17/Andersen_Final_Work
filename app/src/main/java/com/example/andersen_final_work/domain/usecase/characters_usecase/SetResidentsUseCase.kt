package com.example.andersen_final_work.domain.usecase.characters_usecase

import com.example.andersen_final_work.domain.models.Locations
import javax.inject.Inject

class SetResidentsUseCase @Inject constructor() {
    fun setResidents(
        location: Locations,
        getResident: (Int) -> Unit,
        getResidents: (String) -> Unit
    ) {
        val listResidents = location.residents
        var listId = emptyList<String>()
        if (listResidents.size == 1) {
            val id = listResidents[0].substringAfterLast('/').toInt()
            getResident(id)
        } else {
            listResidents.forEach {
                listId = listId + (it.substringAfterLast('/'))
            }
            val ids = listId.joinToString(",")
            getResidents(ids)
        }
    }
}