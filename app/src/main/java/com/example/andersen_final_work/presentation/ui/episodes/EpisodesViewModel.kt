package com.example.andersen_final_work.presentation.ui.episodes

import androidx.lifecycle.*
import com.example.andersen_final_work.domain.models.Episode
import com.example.andersen_final_work.domain.usecase.episodes_usecase.GetEpisodesFromDBUseCase
import com.example.andersen_final_work.domain.usecase.episodes_usecase.GetEpisodesUseCase
import com.example.andersen_final_work.domain.usecase.episodes_usecase.UpdateEpisodesAfterSwipeUseCase

class EpisodesViewModel(
    private val getEpisodesUseCase: GetEpisodesUseCase,
    private val updateEpisodesUseCase: UpdateEpisodesAfterSwipeUseCase,
    private val getEpisodesFromDB: GetEpisodesFromDBUseCase
) : ViewModel() {
    private val allEpisodes = getEpisodesFromDB.getEpisodesFromDB()
    private val enteredText = MutableLiveData("")
    private val season = MutableLiveData("")
    private val series = MutableLiveData("")
    private val showLoader = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean>
        get() = showLoader

    private val isVisibleRetryButton = MutableLiveData(false)
    val isLoadingData: LiveData<Boolean>
        get() = isVisibleRetryButton

    private val episodesLiveData = MediatorLiveData<List<Episode>>().apply {
        addSource(allEpisodes) { setEpisodes() }
        addSource(enteredText) { setEpisodes() }
        addSource(season) { setEpisodes() }
        addSource(series) { setEpisodes() }
    }

    val episodes: LiveData<List<Episode>>
        get() = episodesLiveData

    private var currentPage = Pair(1, false)
    private var pages = 0

    private fun setEpisodes() {
        val query = enteredText.value.orEmpty()
        val season = season.value.orEmpty()
        val series = series.value.orEmpty()
        val episodes = allEpisodes.value.orEmpty()
        episodesLiveData.value =
            episodes.filter {
                val str = it.episode.split("E")
                val seasonStr = str[0].substringAfterLast("S")
                val seriesStr = str[1]
                it.name.toLowerCase()
                    .contains(query.toLowerCase())
                        && seasonStr.contains(season)
                        && seriesStr.contains(series)
            }
    }

    fun getEpisodes(page: Int? = null) {
        isVisibleRetryButton.value = false
        if (page != null) currentPage = Pair(page, false)
        showLoader.value = true
        getEpisodesUseCase.getEpisodes(
            page = currentPage.first,
            scope = viewModelScope,
            onSuccess = {
                it?.let { pages = it }
                currentPage = Pair(currentPage.first + 1, false)
                showLoader.value = false
            },
            onError = {
                currentPage = Pair(currentPage.first, false)
                isVisibleRetryButton.value = !currentPage.second
                showLoader.value = false
            }
        )
    }

    fun updateAfterSwipe() {
        if (allEpisodes.value?.size ?: 0 == episodesLiveData.value?.size ?: 0) {
            updateEpisodesUseCase.updateAfterSwipe(scope = viewModelScope,
                setCurrentPage = {
                    currentPage = Pair(1, false)
                })
        }
    }

    fun onPositionChanged(position: Int) {
        val episodesSize = allEpisodes.value?.size ?: 0
        val sizeAllEpisodesList = episodesLiveData.value?.size ?: 0
        if (position >= episodesSize - 10 &&
            currentPage.first <= pages
            && episodesSize == sizeAllEpisodesList && !currentPage.second
        ) {
            getEpisodes()
            currentPage = Pair(currentPage.first, true)
        }
    }

    fun findByName(enteredText: String) {
        this.enteredText.value = enteredText
    }

    fun findBySeason(season: String) {
        this.season.value = season
    }

    fun findBySeries(series: String) {
        this.series.value = series
    }
}