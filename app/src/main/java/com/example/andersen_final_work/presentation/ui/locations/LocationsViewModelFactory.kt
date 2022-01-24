package com.example.andersen_final_work.presentation.ui.locations

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.andersen_final_work.domain.usecase.locations_usecase.GetLocationsFromDBUseCase
import com.example.andersen_final_work.domain.usecase.locations_usecase.GetLocationsUseCase
import com.example.andersen_final_work.domain.usecase.locations_usecase.UpdateLocationsAfterSwipeUseCase
import javax.inject.Inject

class LocationsViewModelFactory @Inject constructor(
    private val getLocationsUseCase: GetLocationsUseCase,
    private val updateLocationsAfterSwipeUseCase: UpdateLocationsAfterSwipeUseCase,
    private val getLocationFromDBUseCase: GetLocationsFromDBUseCase
) : ViewModelProvider.Factory {


    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return LocationsViewModel(
            getLocationsUseCase,
            updateLocationsAfterSwipeUseCase,
            getLocationFromDBUseCase
        ) as T
    }
}