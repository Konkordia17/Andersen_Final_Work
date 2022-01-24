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
data class Heroes(
    val info: Info,
    val results: List<CharacterEntity>
) : Parcelable

@Parcelize
@JsonClass(generateAdapter = true)
data class Info(
    val next: String?,
    val pages: Int?
) : Parcelable

@Entity(tableName = Contract.TABLE_NAME)
@Parcelize
@JsonClass(generateAdapter = true)
data class CharacterEntity(
    @PrimaryKey
    @ColumnInfo(name = Contract.Column.ID)
    val id: Int,
    @ColumnInfo(name = Contract.Column.NAME)
    val name: String,
    @ColumnInfo(name = Contract.Column.SPECIES)
    val species: String,
    @ColumnInfo(name = Contract.Column.STATUS)
    val status: String,
    @ColumnInfo(name = Contract.Column.GENDER)
    val gender: String,
    @ColumnInfo(name = Contract.Column.IMAGE)
    val image: String,
    @ColumnInfo(name = Contract.Column.TYPE)
    val type: String,
    @ColumnInfo(name = Contract.Column.ORIGIN)
    val origin: Origin,
    @ColumnInfo(name = Contract.Column.LOCATION)
    val location: Location,
    @ColumnInfo(name = Contract.Column.EPISODE)
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