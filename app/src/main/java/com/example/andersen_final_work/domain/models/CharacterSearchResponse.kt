package com.example.andersen_final_work.domain.models

import android.os.Parcelable
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class CharacterSearchResponse(
    val total: Int = 0,
    val items: List<Character> = emptyList(),
    val nextPage: Int? = null
) : Parcelable