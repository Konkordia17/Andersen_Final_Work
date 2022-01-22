package com.example.andersen_final_work.domain.models

import android.os.Parcelable
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class AllLocations(
    val info: LocationsInfo,
    val results: List<Locations>
) : Parcelable

@Parcelize
@JsonClass(generateAdapter = true)
data class LocationsInfo(
    val count: Int?,
    val next: String?,
    val pages: Int?
) : Parcelable

@Parcelize
@JsonClass(generateAdapter = true)
data class Locations(
    val id: Int,
    val name: String,
    val type: String,
    val dimension: String,
    val residents: List<String>,
    val created: String,
) : Parcelable