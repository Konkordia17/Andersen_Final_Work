package com.example.andersen_final_work.data.storage.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.andersen_final_work.data.storage.models.*

@Database(
    entities = [CharacterEntity::class, EpisodeEntity::class, LocationEntity::class, CharacterEpisodeCrossRef::class,
        LocationCharacterCrossRef::class, EpisodeCharacterCrossRef::class],
    version = RickAndMortyDB.DB_VERSION
)
@TypeConverters(Converter::class)
abstract class RickAndMortyDB : RoomDatabase() {

    abstract fun charactersDao(): CharactersDao
    abstract fun episodeDao(): EpisodesDao
    abstract fun locationDao(): LocationDao

    companion object {
        const val DB_VERSION = 1

        @Volatile
        private var INSTANCE: RickAndMortyDB? = null

        fun getInstance(context: Context): RickAndMortyDB =
            INSTANCE ?: synchronized(this) {
                INSTANCE
                    ?: buildDatabase(context).also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                RickAndMortyDB::class.java, "Rick_and_Morty.db"
            )
                .build()
    }
}