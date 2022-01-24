package com.example.andersen_final_work.presentation.ui.episode_detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.andersen_final_work.domain.usecase.characters_usecase.GetCharacterForEpisodeDetailUseCase
import com.example.andersen_final_work.domain.usecase.characters_usecase.GetCharactersListForEpisodeDetailUseCase
import com.example.andersen_final_work.domain.usecase.episodes_usecase.GetEpisodeUseCase
import com.example.andersen_final_work.domain.usecase.characters_usecase.SetCharactersForEpisodeDetailUseCase
import javax.inject.Inject

class EpisodeDetailViewModelFactory @Inject constructor(
    private val getEpisodeUseCase: GetEpisodeUseCase,
    private val getSingleCharacterUseCase: GetCharacterForEpisodeDetailUseCase,
    private val getCharactersUseCase: GetCharactersListForEpisodeDetailUseCase,
    private val setCharactersListForEpisodeDetailUseCase: SetCharactersForEpisodeDetailUseCase

) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return EpisodeDetailViewModel(
            getEpisodeUseCase,
            getSingleCharacterUseCase,
            getCharactersUseCase,
            setCharactersListForEpisodeDetailUseCase
        ) as T
    }
}