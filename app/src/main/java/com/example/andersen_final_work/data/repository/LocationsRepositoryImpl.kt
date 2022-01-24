package com.example.andersen_final_work.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.example.andersen_final_work.data.storage.api.Api
import com.example.andersen_final_work.data.storage.db.RickAndMortyDB
import com.example.andersen_final_work.data.storage.models.LocationEntity
import com.example.andersen_final_work.domain.models.Locations
import com.example.andersen_final_work.domain.repository.LocationsRepository
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

class LocationsRepositoryImpl(private val api: Api, private val dataBase: RickAndMortyDB) :
    LocationsRepository {

    private fun mapLocationEntityToLocationDomain(location: LocationEntity): Locations {
        return Locations(
            id = location.id,
            name = location.name,
            type = location.type,
            dimension = location.dimension,
            residents = location.residents,
            created = location.created
        )
    }

    private fun mapLocationDomainToLocationEntity(location: Locations): LocationEntity {
        return LocationEntity(
            id = location.id,
            name = location.name,
            type = location.type,
            dimension = location.dimension,
            residents = location.residents,
            created = location.created
        )
    }

    override fun getLocation(id: String): Single<Locations> {
        return api.getLocation(id)
            .subscribeOn(Schedulers.io())
            .flatMap {
                dataBase.locationDao().insertLocation(mapLocationDomainToLocationEntity(it))
                    .andThen(Single.just(it))
            }
            .onErrorResumeNext {
                dataBase.locationDao().getLocation(id).map { mapLocationEntityToLocationDomain(it) }
            }
    }

    override fun getLocationsFromDB(): LiveData<List<Locations>> {
        return dataBase.locationDao().getLocations().map { it ->
            it.map { mapLocationEntityToLocationDomain(it) }
        }
    }

    override suspend fun getAllLocations(
        page: Int,
        onSuccess: (Int?) -> Unit,
        onError: () -> Unit
    ) {
        try {
            val allLocations = api.getLocationsByPage(page)
            dataBase.locationDao().insertLocations(allLocations.results)
            onSuccess.invoke(allLocations.info.pages)
        } catch (e: Exception) {
            onError()
            Log.d("TAG", "getLocations: asdf")
        }
    }

    override suspend fun updateLocations(page: Int, callback: () -> Unit) {
        try {
            val allLocations = api.getLocationsByPage(page)
            dataBase.locationDao().deleteAllLocations()
            dataBase.locationDao().insertLocations(allLocations.results)
            callback.invoke()
        } catch (e: Exception) {
        }
    }

    override fun getLocationFromDB(id: String): Single<Locations> {
        return dataBase.locationDao().getLocation(id)
            .map { mapLocationEntityToLocationDomain(it) }
    }
}