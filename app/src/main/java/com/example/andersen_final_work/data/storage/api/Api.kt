package com.example.andersen_final_work.data.storage.api

import com.example.andersen_final_work.data.storage.models.*
import com.example.andersen_final_work.domain.models.Locations
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface Api {
    @GET("/api/character")
    suspend fun getCharactersByPage(@Query("page") page: Int): Heroes

    @GET("/api/episode")
    suspend fun getEpisodesByPage(@Query("page") page: Int): AllEpisodes

    @GET("/api/location")
    suspend fun getLocationsByPage(@Query("page") page: Int): AllLocations

    @GET("/api/character/{id}")
    suspend fun getCharacter(@Path("id") id: Int): CharacterEntity

    @GET("/api/character/{id}")
    suspend fun getListCharacter(@Path("id") id: String): List<CharacterEntity>

    @GET("/api/episode/{id}")
    suspend fun getEpisodes(@Path("id") id: String): List<EpisodeEntity>

    @GET("/api/episode/{id}")
    fun getSingleEpisode(@Path("id") id: String): Single<EpisodeEntity>

    @GET("/api/location/{id}")
    fun getLocation(@Path("id") id: String): Single<Locations>
}