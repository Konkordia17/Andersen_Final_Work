package com.example.andersen_final_work.data.storage.models

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.andersen_final_work.Contract
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class AllEpisodes(
    val info: EpisodesInfo,
    val results: List<EpisodeEntity>
) : Parcelable

@Parcelize
@JsonClass(generateAdapter = true)
data class EpisodesInfo(
    val count: Int?,
    val next: String?,
    val pages: Int?
) : Parcelable

@Entity(tableName = Contract.TABLE_NAME_EPISODES)
@Parcelize
@JsonClass(generateAdapter = true)
data class EpisodeEntity(
    @PrimaryKey
    @ColumnInfo(name = Contract.Column.ID_EPISODE)
    @Json(name = "id")
    val episodeId: Int,
    @ColumnInfo(name = Contract.Column.NAME)
    val name: String,
    @ColumnInfo(name = Contract.Column.AIR_DATE)
    val air_date: String,
    @ColumnInfo(name = Contract.Column.EPISODE)
    val episode: String,
    @ColumnInfo(name = Contract.Column.CHARACTERS)
    val characters: List<String>,
    @ColumnInfo(name = Contract.Column.CREATED)
    val created: String,
) : Parcelable