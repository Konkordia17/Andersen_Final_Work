package com.example.andersen_final_work.domain.models

import android.os.Parcelable
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class Heroes(
    val info: Info,
    val results: List<Character>
) : Parcelable

@Parcelize
@JsonClass(generateAdapter = true)
data class Info(
    val next: String?,
    val pages: Int?
) : Parcelable

@Parcelize
@JsonClass(generateAdapter = true)
data class Character(
    val id: Int,
    val name: String,
    val species: String,
    val status: String,
    val gender: String,
    val image: String,
    val type: String,
    val origin: Origin,
    val location: Location,
    val episode: List<String>
) : Parcelable

@Parcelize
@JsonClass(generateAdapter = true)
data class Origin(
    val name: String,
    val url: String
) : Parcelable

@Parcelize
@JsonClass(generateAdapter = true)
data class Location(
    val name: String,
    val url: String
) : Parcelable