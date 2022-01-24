package com.example.andersen_final_work.presentation.ui.episodes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.andersen_final_work.domain.usecase.episodes_usecase.GetEpisodesFromDBUseCase
import com.example.andersen_final_work.domain.usecase.episodes_usecase.GetEpisodesUseCase
import com.example.andersen_final_work.domain.usecase.episodes_usecase.UpdateEpisodesAfterSwipeUseCase
import javax.inject.Inject

class EpisodesViewModelFactory @Inject constructor(
    private val getEpisodesUseCase: GetEpisodesUseCase,
    private val updateEpisodesUseCase: UpdateEpisodesAfterSwipeUseCase,
    private val getEpisodesFromDB: GetEpisodesFromDBUseCase
) : ViewModelProvider.Factory {


    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return EpisodesViewModel(getEpisodesUseCase, updateEpisodesUseCase, getEpisodesFromDB) as T
    }
}