package com.example.andersen_final_work.domain.usecase.locations_usecase

import com.example.andersen_final_work.domain.repository.LocationsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class UpdateLocationsAfterSwipeUseCase
@Inject constructor(private val repository: LocationsRepository) {
    fun updateAfterSwipe(scope: CoroutineScope, setCurrentPage: () -> Unit) {
        scope.launch {
            repository.updateLocations(0) {
                setCurrentPage.invoke()
            }
        }
    }
}