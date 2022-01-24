package com.example.andersen_final_work.data.storage.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.andersen_final_work.Contract
import com.example.andersen_final_work.data.storage.models.LocationCharacterCrossRef
import com.example.andersen_final_work.data.storage.models.LocationEntity
import com.example.andersen_final_work.data.storage.models.LocationWithCharacters
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface LocationDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertLocation(locations: LocationEntity): Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLocations(locations: List<LocationEntity>)

    @Query("DELETE FROM locations")
    suspend fun deleteAllLocations()

    @Query("SELECT * FROM locations")
    fun getLocations(): LiveData<List<LocationEntity>>

    @Query(
        "SELECT * FROM ${Contract.TABLE_NAME_LOCATION} WHERE " +
                "locationId LIKE :id"
    )
    fun getLocation(id: String): Single<LocationEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLocationCharactersCrossRef(crossRef: LocationCharacterCrossRef)

    @Transaction
    @Query("SELECT * FROM locations WHERE locationId = :locationId")
    suspend fun getLocationWithCharacters(locationId: Int): LocationWithCharacters
}