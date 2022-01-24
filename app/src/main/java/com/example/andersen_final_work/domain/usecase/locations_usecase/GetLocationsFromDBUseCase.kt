package com.example.andersen_final_work.domain.usecase.locations_usecase

import androidx.lifecycle.LiveData
import com.example.andersen_final_work.domain.models.Locations
import com.example.andersen_final_work.domain.repository.LocationsRepository
import javax.inject.Inject

class GetLocationsFromDBUseCase
@Inject constructor(private val repository: LocationsRepository) {

    fun getLocationsFromDB(): LiveData<List<Locations>> {
        return repository.getLocationsFromDB()
    }
}