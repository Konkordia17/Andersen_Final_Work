package com.example.andersen_final_work.presentation.ui.episode_detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.andersen_final_work.Contract
import com.example.andersen_final_work.domain.models.Character
import com.example.andersen_final_work.domain.models.Episode
import com.example.andersen_final_work.domain.usecase.characters_usecase.GetCharacterForEpisodeDetailUseCase
import com.example.andersen_final_work.domain.usecase.characters_usecase.GetCharactersListForEpisodeDetailUseCase
import com.example.andersen_final_work.domain.usecase.characters_usecase.SetCharactersForEpisodeDetailUseCase
import com.example.andersen_final_work.domain.usecase.episodes_usecase.GetEpisodeUseCase
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.launch

class EpisodeDetailViewModel(
    private val getEpisodeUseCase: GetEpisodeUseCase,
    private val getSingleCharacterUseCase: GetCharacterForEpisodeDetailUseCase,
    private val getCharactersUseCase: GetCharactersListForEpisodeDetailUseCase,
    private val setCharactersListForEpisodeDetailUseCase: SetCharactersForEpisodeDetailUseCase
) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()
    private var singleEpisode = MutableLiveData<Episode?>()
    val episode: LiveData<Episode?>
        get() = singleEpisode

    private var listHeroes = MutableLiveData<List<Character?>>()
    val characters: LiveData<List<Character?>>
        get() = listHeroes

    private var unknownEpisode = MutableLiveData<String>()
    val unknown: LiveData<String>
        get() = unknownEpisode

    private var showLoader = MutableLiveData<Boolean>()
    val loader: LiveData<Boolean>
        get() = showLoader

    fun getEpisode(id: String) {
        compositeDisposable.add(
            getEpisodeUseCase.getEpisode(id)
                .subscribe({
                    showLoader.value = true
                    singleEpisode.value = it
                    showLoader.value = false
                }, {
                    unknownEpisode.value = Contract.NOT_DATA_ABOUT_EPISODE
                    showLoader.value = false
                })
        )
    }

    private fun getCharacters(episodeId: Int, ids: String) {
        viewModelScope.launch {
            val characters = getCharactersUseCase(
                episodeId = episodeId,
                ids = ids,
            )
            if (characters.isEmpty()) {
                unknownEpisode.value = Contract.NOT_DATA_ABOUT_CHARACTERS
            } else {
                listHeroes.value = characters
            }
        }
    }

    private fun getCharacter(episodeId: Int, id: Int) {
        viewModelScope.launch {
            val character = getSingleCharacterUseCase.getCharacter(
                episodeId = episodeId,
                id = id,
            )
            character?.let { listHeroes.value = listOf(it) } ?: kotlin.run {
                unknownEpisode.value = Contract.NOT_DATA_ABOUT_CHARACTERS
            }
        }
    }

    fun setCharacters(episode: Episode) {
        setCharactersListForEpisodeDetailUseCase.setCharacters(episode = episode,
            getCharacter = {
                getCharacter(episodeId = episode.episodeId, id = it)
            },
            getCharacters = {
                getCharacters(episodeId = episode.episodeId, ids = it)
            }
        )
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}