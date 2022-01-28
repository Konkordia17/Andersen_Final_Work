package com.example.andersen_final_work.presentation.ui.locations

import androidx.lifecycle.*
import com.example.andersen_final_work.domain.models.Locations
import com.example.andersen_final_work.domain.usecase.locations_usecase.GetLocationsFromDBUseCase
import com.example.andersen_final_work.domain.usecase.locations_usecase.GetLocationsUseCase
import com.example.andersen_final_work.domain.usecase.locations_usecase.UpdateLocationsAfterSwipeUseCase
import kotlinx.coroutines.launch

class LocationsViewModel(
    private val getLocationsUseCase: GetLocationsUseCase,
    private val updateLocationsAfterSwipeUseCase: UpdateLocationsAfterSwipeUseCase,
    private val getLocationFromDBUseCase: GetLocationsFromDBUseCase
) : ViewModel() {

    private val allLocations = getLocationFromDBUseCase.getLocationsFromDB()
    private val enteredText = MutableLiveData("")
    private val type = MutableLiveData("")
    private val dimension = MutableLiveData("")
    private val showLoader = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean>
        get() = showLoader

    private val isVisibleRetryButton = MutableLiveData(false)
    val isLoadingData: LiveData<Boolean>
        get() = isVisibleRetryButton

    private val locationsLiveData = MediatorLiveData<List<Locations>>().apply {
        addSource(allLocations) { setLocations() }
        addSource(enteredText) { setLocations() }
        addSource(type) { setLocations() }
        addSource(dimension) { setLocations() }
    }

    val locations: LiveData<List<Locations>>
        get() = locationsLiveData

    private var currentPage = Pair(1, false)
    private var pages = 0

    private fun setLocations() {
        val query = enteredText.value.orEmpty()
        val type = type.value.orEmpty()
        val dimension = dimension.value.orEmpty()
        val locations = allLocations.value.orEmpty()
        locationsLiveData.value = locations.filter {
            it.name.toLowerCase()
                .contains(query.toLowerCase())
                    && it.type.contains(type)
                    && it.dimension.contains(dimension)
        }
    }

    fun getLocations(page: Int? = null) {
        isVisibleRetryButton.value = false
        showLoader.value = true
        if (page != null) currentPage = Pair(page, false)
        viewModelScope.launch {
            getLocationsUseCase.getLocations(
                page = currentPage.first,
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
    }

    fun updateAfterSwipe() {
        if (allLocations.value?.size ?: 0 == locationsLiveData.value?.size ?: 0) {
            viewModelScope.launch {
                updateLocationsAfterSwipeUseCase.updateAfterSwipe(
                    setCurrentPage = {
                        currentPage = Pair(1, false)
                    })
            }
        }
    }

    fun onPositionChanged(position: Int) {
        val locationsSize = allLocations.value?.size ?: 0
        val sizeAllLocationsList = locationsLiveData.value?.size ?: 0
        if (position >= locationsSize - 10 && currentPage.first <= pages
            && locationsSize == sizeAllLocationsList && !currentPage.second
        ) {
            getLocations()
            currentPage = Pair(currentPage.first, true)
        }
    }

    fun findByName(enteredText: String) {
        this.enteredText.value = enteredText
    }

    fun findBySeason(type: String) {
        this.type.value = type
    }

    fun findBySeries(dimension: String) {
        this.dimension.value = dimension
    }
}