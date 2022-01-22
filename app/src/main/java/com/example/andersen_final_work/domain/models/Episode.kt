package com.example.andersen_final_work.domain.models

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class AllEpisodes(
    val info: EpisodesInfo,
    val results: List<Episode>
) : Parcelable

@Parcelize
@JsonClass(generateAdapter = true)
data class EpisodesInfo(
    val count: Int?,
    val next: String?,
    val pages: Int?
) : Parcelable

@Parcelize
@JsonClass(generateAdapter = true)
data class Episode(
    @Json(name = "id")
    val episodeId: Int,
    val name: String,
    val air_date: String,
    val episode: String,
    val characters: List<String>,
    val created: String,
) : Parcelable
