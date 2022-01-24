package com.example.andersen_final_work.domain.usecase.episodes_usecase

import androidx.lifecycle.LiveData
import com.example.andersen_final_work.domain.models.Episode
import com.example.andersen_final_work.domain.repository.EpisodesRepository
import javax.inject.Inject

class GetEpisodesFromDBUseCase @Inject constructor(val episodesRepository: EpisodesRepository) {

    fun getEpisodesFromDB(): LiveData<List<Episode>> {
        return episodesRepository.getEpisodesFromDB()
    }
}