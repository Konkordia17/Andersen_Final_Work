package com.example.andersen_final_work.domain.usecase.locations_usecase

import com.example.andersen_final_work.domain.repository.LocationsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class GetLocationsUseCase @Inject constructor(private val repository: LocationsRepository) {
    fun getLocations(
        page: Int,
        scope: CoroutineScope,
        onSuccess: (Int?) -> Unit,
        onError: () -> Unit
    ) {
        scope.launch {
            repository.getAllLocations(
                page = page,
                onSuccess = onSuccess,
                onError = onError
            )
        }
    }
}