package com.example.andersen_final_work

object Contract {
    const val TABLE_NAME = "characters"
    const val TABLE_NAME_LOCATION = "locations"
    const val TABLE_NAME_EPISODES = "episodes"
    const val NOT_DATA = "Нет соединения с интернетом"
    const val NOT_DATA_ABOUT_LOCATION = "Нет данных о локации"
    const val NOT_DATA_ABOUT_EPISODE = "Нет данных об эпизоде"
    const val NOT_DATA_ABOUT_CHARACTER = "Нет данных о персонажах"

    object Column {
        const val ID = "characterId"
        const val ID_EPISODE = "episodeId"
        const val ID_LOCATION = "locationId"
        const val NAME = "name"
        const val SPECIES = "species"
        const val STATUS = "status"
        const val GENDER = "gender"
        const val IMAGE = "image"
        const val TYPE = "type"
        const val ORIGIN = "origin"
        const val LOCATION = "location"
        const val EPISODE = "episode"
        const val AIR_DATE = "air_date"
        const val CHARACTERS = "characters"
        const val CREATED = "created"
        const val DIMENSION = "dimension"
        const val RESIDENTS = "residents"
    }
}