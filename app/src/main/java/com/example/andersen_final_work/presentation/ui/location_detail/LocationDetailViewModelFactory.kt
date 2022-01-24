package com.example.andersen_final_work.presentation.ui.location_detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.andersen_final_work.domain.usecase.locations_usecase.GetLocationUseCase
import com.example.andersen_final_work.domain.usecase.characters_usecase.GetResidentUseCase
import com.example.andersen_final_work.domain.usecase.characters_usecase.GetResidentsUseCase
import com.example.andersen_final_work.domain.usecase.characters_usecase.SetResidentsUseCase
import javax.inject.Inject

class LocationDetailViewModelFactory @Inject constructor(
    private val getLocationUseCase: GetLocationUseCase,
    private val getResidentsUseCase: GetResidentsUseCase,
    private val getResidentUseCase: GetResidentUseCase,
    private val setResidentUseCase: SetResidentsUseCase
) : ViewModelProvider.Factory {


    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return LocationDetailsViewModel(
            getLocationUseCase,
            getResidentsUseCase,
            getResidentUseCase,
            setResidentUseCase
        ) as T
    }
}