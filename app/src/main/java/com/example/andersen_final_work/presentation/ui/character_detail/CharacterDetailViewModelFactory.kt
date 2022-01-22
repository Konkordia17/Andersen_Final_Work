package com.example.andersen_final_work.presentation.ui.character_detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.andersen_final_work.domain.usecase.characters_usecase.GetCharacterUseCase
import com.example.andersen_final_work.domain.usecase.episodes_usecase.GetEpisodeUseCase
import com.example.andersen_final_work.domain.usecase.episodes_usecase.GetEpisodesListForDetailCharacterUseCase
import com.example.andersen_final_work.domain.usecase.episodes_usecase.SetEpisodesUseCase
import com.example.andersen_final_work.domain.usecase.locations_usecase.SetLocationUseCase
import com.example.andersen_final_work.domain.usecase.locations_usecase.SetOriginUseCase
import javax.inject.Inject

class CharacterDetailViewModelFactory @Inject constructor(
    private val getSingleCharacterUseCase: GetCharacterUseCase,
    private val getEpisodesListForDetailCharacterUseCase
    : GetEpisodesListForDetailCharacterUseCase,
    private val getEpisodeUseCase: GetEpisodeUseCase,
    private val setEpisodesUseCase: SetEpisodesUseCase,
    private val setLocationUseCase: SetLocationUseCase,
    private val setOriginUseCase: SetOriginUseCase
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return CharacterDetailViewModel(
            getSingleCharacterUseCase, getEpisodesListForDetailCharacterUseCase,
            getEpisodeUseCase,
            setEpisodesUseCase,
            setLocationUseCase,
            setOriginUseCase
        ) as T
    }
}