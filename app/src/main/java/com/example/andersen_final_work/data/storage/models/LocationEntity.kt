package com.example.andersen_final_work.data.storage.models

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.andersen_final_work.Contract
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class AllLocations(
    val info: LocationsInfo,
    val results: List<LocationEntity>
) : Parcelable

@Parcelize
@JsonClass(generateAdapter = true)
data class LocationsInfo(
    val count: Int?,
    val next: String?,
    val pages: Int?
) : Parcelable

@Entity(tableName = Contract.TABLE_NAME_LOCATION)
@Parcelize
@JsonClass(generateAdapter = true)
data class LocationEntity(
    @PrimaryKey
    @ColumnInfo(name = Contract.Column.ID_LOCATION)
    val id: Int,
    @ColumnInfo(name = Contract.Column.NAME)
    val name: String,
    @ColumnInfo(name = Contract.Column.TYPE)
    val type: String,
    @ColumnInfo(name = Contract.Column.DIMENSION)
    val dimension: String,
    @ColumnInfo(name = Contract.Column.RESIDENTS)
    val residents: List<String>,
    @ColumnInfo(name = Contract.Column.CREATED)
    val created: String,
) : Parcelable
