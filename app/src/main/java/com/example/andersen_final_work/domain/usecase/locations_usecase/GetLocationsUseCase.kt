package com.example.andersen_final_work.domain.usecase.locations_usecase

import com.example.andersen_final_work.domain.repository.LocationsRepository
import javax.inject.Inject

class GetLocationsUseCase @Inject constructor(private val repository: LocationsRepository) {
    suspend fun getLocations(
        page: Int,
        onSuccess: (Int?) -> Unit,
        onError: () -> Unit
    ) {
        repository.getAllLocations(
            page = page,
            onSuccess = onSuccess,
            onError = onError
        )
    }
}