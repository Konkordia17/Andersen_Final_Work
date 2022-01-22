package com.example.andersen_final_work.domain.usecase.episodes_usecase

import com.example.andersen_final_work.domain.models.Episode
import com.example.andersen_final_work.domain.repository.EpisodesRepository
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Inject

class GetEpisodeUseCase @Inject constructor(private val episodesRepository: EpisodesRepository) {

    fun getEpisode(
        id: String
    ): Single<Episode> {
        return episodesRepository.getEpisode(
            id = id,
        ).observeOn(AndroidSchedulers.mainThread())
    }
}
