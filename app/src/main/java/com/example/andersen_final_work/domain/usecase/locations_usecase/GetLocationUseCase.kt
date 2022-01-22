package com.example.andersen_final_work.domain.usecase.locations_usecase

import com.example.andersen_final_work.domain.models.Locations
import com.example.andersen_final_work.domain.repository.LocationsRepository
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Inject

class GetLocationUseCase
@Inject constructor(private val repository: LocationsRepository) {
    fun getLocation(id: String): Single<Locations> {
        return repository.getLocation(id)
            .observeOn(AndroidSchedulers.mainThread())
    }
}
