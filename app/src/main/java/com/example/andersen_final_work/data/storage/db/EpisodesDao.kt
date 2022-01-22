package com.example.andersen_final_work.data.storage.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.andersen_final_work.Contract
import com.example.andersen_final_work.data.storage.models.EpisodeCharacterCrossRef
import com.example.andersen_final_work.data.storage.models.EpisodeEntity
import com.example.andersen_final_work.data.storage.models.EpisodeWithCharacters
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface EpisodesDao {

    @Query("DELETE FROM episodes")
    suspend fun deleteAllEpisodes()

    @Query("SELECT * FROM episodes")
    fun getEpisodes(): LiveData<List<EpisodeEntity>>

    @Query(
        "SELECT * FROM ${Contract.TABLE_NAME_EPISODES} WHERE " +
                "episodeId LIKE :id"
    )
   fun getSingleEpisode(id: Int): Single<EpisodeEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEpisodes(episodes: List<EpisodeEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertEpisode(episode: EpisodeEntity) : Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEpisodeCharactersCrossRef(crossRef: EpisodeCharacterCrossRef)

    @Transaction
    @Query("SELECT * FROM episodes WHERE episodeId = :episodeId")
    suspend fun getEpisodeWithCharacters(episodeId: Int): EpisodeWithCharacters
}