package com.example.andersen_final_work.di

import com.example.andersen_final_work.presentation.ui.MainActivity
import com.example.andersen_final_work.presentation.ui.character_detail.CharacterDetailFragment
import com.example.andersen_final_work.presentation.ui.characters.CharactersFragment
import com.example.andersen_final_work.presentation.ui.episode_detail.EpisodeDetailFragment
import com.example.andersen_final_work.presentation.ui.episodes.EpisodesFragment
import com.example.andersen_final_work.presentation.ui.location_detail.LocationDetailsFragment
import com.example.andersen_final_work.presentation.ui.locations.LocationsFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, DataModule::class])
interface AppComponent {
    fun inject(mainActivity: MainActivity)
    fun inject(charactersFragment: CharactersFragment)
    fun inject(charactersDetailFragment: CharacterDetailFragment)
    fun inject(episodeDetailFragment: EpisodeDetailFragment)
    fun inject(episodesFragment: EpisodesFragment)
    fun inject(locationDetailsFragment: LocationDetailsFragment)
    fun inject(locationsFragment: LocationsFragment)
}