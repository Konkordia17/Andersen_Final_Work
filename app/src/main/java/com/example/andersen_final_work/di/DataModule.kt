package com.example.andersen_final_work.di

import android.content.Context
import com.example.andersen_final_work.data.repository.CharactersRepositoryImpl
import com.example.andersen_final_work.data.repository.EpisodesRepositoryImpl
import com.example.andersen_final_work.data.repository.LocationsRepositoryImpl
import com.example.andersen_final_work.data.storage.api.Api
import com.example.andersen_final_work.data.storage.db.RickAndMortyDB
import com.example.andersen_final_work.domain.repository.CharactersRepository
import com.example.andersen_final_work.domain.repository.EpisodesRepository
import com.example.andersen_final_work.domain.repository.LocationsRepository
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class DataModule {
    @Provides
    @Singleton
    fun provideDB(context: Context): RickAndMortyDB {
        return RickAndMortyDB.getInstance(context)
    }

    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(3, TimeUnit.SECONDS)
            .addNetworkInterceptor(loggingInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://rickandmortyapi.com/")
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideApi(retrofit: Retrofit): Api {
        return retrofit.create()
    }

    @Singleton
    @Provides
    fun provideCharactersRepository(api: Api, dataBase: RickAndMortyDB): CharactersRepository {
        return CharactersRepositoryImpl(api, dataBase)
    }

    @Singleton
    @Provides
    fun provideEpisodeRepository(api: Api, dataBase: RickAndMortyDB): EpisodesRepository {
        return EpisodesRepositoryImpl(api, dataBase)
    }

    @Singleton
    @Provides
    fun provideLocationRepository(api: Api, dataBase: RickAndMortyDB): LocationsRepository {
        return LocationsRepositoryImpl(api, dataBase)
    }
}