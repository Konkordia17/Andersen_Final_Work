package com.example.andersen_final_work.presentation.ui.character_detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.andersen_final_work.domain.models.Character
import com.example.andersen_final_work.domain.models.Episode
import com.example.andersen_final_work.domain.usecase.characters_usecase.GetCharacterUseCase
import com.example.andersen_final_work.domain.usecase.episodes_usecase.GetEpisodeUseCase
import com.example.andersen_final_work.domain.usecase.episodes_usecase.GetEpisodesListForDetailCharacterUseCase
import com.example.andersen_final_work.domain.usecase.episodes_usecase.SetEpisodesUseCase
import com.example.andersen_final_work.domain.usecase.locations_usecase.SetLocationUseCase
import com.example.andersen_final_work.domain.usecase.locations_usecase.SetOriginUseCase
import io.reactivex.disposables.CompositeDisposable

class CharacterDetailViewModel(
    private val getSingleCharacterUseCase: GetCharacterUseCase,
    private val getEpisodesListForDetailCharacterUseCase
    : GetEpisodesListForDetailCharacterUseCase,
    private val getEpisodeUseCase: GetEpisodeUseCase,
    private val setEpisodesUseCase: SetEpisodesUseCase,
    private val setLocationUseCase: SetLocationUseCase,
    private val setOriginUseCase: SetOriginUseCase
) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    private var character = MutableLiveData<Character?>()
    val singleCharacter: LiveData<Character?>
        get() = character

    private var listEpisodes = MutableLiveData<List<Episode?>>()
    val episodes: LiveData<List<Episode?>>
        get() = listEpisodes

    private var showLoader = MutableLiveData<Boolean>()
    val loader: LiveData<Boolean>
        get() = showLoader

    private var unknownEpisodes = MutableLiveData<String>()
    val unknown: LiveData<String>
        get() = unknownEpisodes

    private var idLocation = MutableLiveData<String>()
    val location: LiveData<String>
        get() = idLocation

    private var idOrigin = MutableLiveData<String>()
    val origin: LiveData<String>
        get() = idOrigin

    fun getSingleCharacter(id: Int) {
        showLoader.value = true
        getSingleCharacterUseCase.getSingleCharacter(
            id = id,
            scope = viewModelScope
        ) {
            it?.let { character.value = it } ?: kotlin.run {
                unknownEpisodes.value = "Нет данных о персонаже"
            }
            showLoader.value = false
        }
    }

    private fun getEpisodesListForDetailCharacter(ids: String, characterId: Int) {
        getEpisodesListForDetailCharacterUseCase.getEpisodesListForDetailCharacter(
            ids = ids,
            scope = viewModelScope,
            onSuccess = {
                if (it.isEmpty()) {
                    unknownEpisodes.value = "Нет данных об эпизодах"
                } else {
                    listEpisodes.value = it
                }
            }, characterId = characterId
        )
    }
    private fun getEpisode(id: String) {
        compositeDisposable.add(
            getEpisodeUseCase.getEpisode(id = id)
                .subscribe({
                    listEpisodes.value = listOf(it)
                }, {
                    unknownEpisodes.value = "Нет данных об эпизодах"
                })
        )
    }

    fun setEpisodes(character: Character) {
        setEpisodesUseCase.setEpisodes(character = character, getEpisode = {
            getEpisode(it)
        }, getEpisodesListForDetailCharacter = {
            getEpisodesListForDetailCharacter(it, characterId = character.id)
        }
        )
    }

    fun setLocation(character: Character) {
        idLocation.value = setLocationUseCase.setLocation(character)
        idOrigin.value = setOriginUseCase.setOrigin(character)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}