package com.example.andersen_final_work.data.storage.db

import androidx.room.TypeConverter
import com.example.andersen_final_work.data.storage.models.Location
import com.example.andersen_final_work.data.storage.models.Origin

class Converter {
    @TypeConverter
    fun convertOriginToString(origin: Origin): String = "${origin.name},${origin.url}"

    @TypeConverter
    fun convertStringToOrigin(string: String): Origin {
        val array = string.split(",")
        return Origin(array[0], array[1])
    }

    @TypeConverter
    fun convertLocationToString(location: Location): String = "${location.name},${location.url}"

    @TypeConverter
    fun convertStringToLocation(string: String): Location {
        val array = string.split(",")
        return Location(array[0], array[1])
    }

    @TypeConverter
    fun convertListToString(list: List<String>): String = list.joinToString(",")

    @TypeConverter
    fun convertStringToList(string: String): List<String> {
        return string.split(",")
    }
}