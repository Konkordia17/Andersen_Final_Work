package com.example.andersen_final_work.domain.repository

import androidx.lifecycle.LiveData
import com.example.andersen_final_work.domain.models.Locations
import io.reactivex.Single

interface LocationsRepository {

    fun getLocation(id: String): Single<Locations>

    fun getLocationsFromDB(): LiveData<List<Locations>>

    suspend fun getAllLocations(page: Int, onSuccess: (Int?) -> Unit, onError: () -> Unit)

    suspend fun updateLocations(page: Int, callback: () -> Unit)

    fun getLocationFromDB(id: String): Single<Locations>
}