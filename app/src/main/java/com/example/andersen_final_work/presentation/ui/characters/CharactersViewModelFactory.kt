package com.example.andersen_final_work.presentation.ui.characters

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.andersen_final_work.domain.usecase.characters_usecase.GetCharactersFromDBUseCase
import com.example.andersen_final_work.domain.usecase.characters_usecase.GetCharactersUseCase
import com.example.andersen_final_work.domain.usecase.characters_usecase.UpdateCharactersAfterSwipeUseCase
import javax.inject.Inject

class CharactersViewModelFactory @Inject constructor(
    private val getCharactersUseCase: GetCharactersUseCase,
    private val getCharactersFromDBUseCase: GetCharactersFromDBUseCase,
    private val updateCharactersAfterSwipeUseCase: UpdateCharactersAfterSwipeUseCase
) : ViewModelProvider.Factory {


    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return CharactersViewModel(
            getCharactersUseCase,
            getCharactersFromDBUseCase,
            updateCharactersAfterSwipeUseCase,
        ) as T
    }
}