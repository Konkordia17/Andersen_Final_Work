package com.example.andersen_final_work.domain.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Filter(
    val species: String,
    val status: String,
    val gender: String
) : Parcelable

@Parcelize
data class FilterEpisodes(
    val season: String,
    val series: String
) : Parcelable

@Parcelize
data class FilterLocation(
    val type: String,
    val dimension: String
) : Parcelable