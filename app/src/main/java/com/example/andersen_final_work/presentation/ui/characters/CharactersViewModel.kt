package com.example.andersen_final_work.presentation.ui.characters

import androidx.lifecycle.*
import com.example.andersen_final_work.domain.models.Character
import com.example.andersen_final_work.domain.usecase.characters_usecase.GetCharactersFromDBUseCase
import com.example.andersen_final_work.domain.usecase.characters_usecase.GetCharactersUseCase
import com.example.andersen_final_work.domain.usecase.characters_usecase.UpdateCharactersAfterSwipeUseCase
import kotlinx.coroutines.launch

class CharactersViewModel(
    private val getCharactersUseCase: GetCharactersUseCase,
    getCharactersFromDBUseCase: GetCharactersFromDBUseCase,
    private val updateCharactersAfterSwipeUseCase: UpdateCharactersAfterSwipeUseCase,
) : ViewModel() {
    private val allCharacters = getCharactersFromDBUseCase.getCharactersFromDB()
    private val enteredText = MutableLiveData("")
    private val species = MutableLiveData("")
    private val status = MutableLiveData("")
    private val gender = MutableLiveData("")
    private val showLoader = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean>
        get() = showLoader

    private val isVisibleRetryButton = MutableLiveData(false)
    val isLoadingData: LiveData<Boolean>
        get() = isVisibleRetryButton

    private val charactersLiveData = MediatorLiveData<List<Character>>().apply {
        addSource(allCharacters) { setCharacters() }
        addSource(enteredText) { setCharacters() }
        addSource(species) { setCharacters() }
        addSource(status) { setCharacters() }
        addSource(gender) { setCharacters() }
    }

    val characters: LiveData<List<Character>>
        get() = charactersLiveData

    private var currentPage = Pair(1, false)
    private var pages = 0

    private fun setCharacters() {
        val query = enteredText.value.orEmpty()
        val species = species.value.orEmpty()
        val status = status.value.orEmpty()
        val gender = gender.value.orEmpty()
        val characters = allCharacters.value.orEmpty()
        charactersLiveData.value =
            characters.filter {
                it.name.toLowerCase()
                    .contains(query.toLowerCase())
                        && it.species.contains(species)
                        && it.status.contains(status)
                        && it.gender.contains(gender)
            }
    }

    fun getCharacters(page: Int? = null) {
        isVisibleRetryButton.value = false
        if (page != null) currentPage = Pair(page, false)
        showLoader.value = true
        viewModelScope.launch {
            getCharactersUseCase.getCharacters(
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
        if (allCharacters.value?.size ?: 0 == charactersLiveData.value?.size ?: 0) {
            viewModelScope.launch {
                updateCharactersAfterSwipeUseCase.updateAfterSwipe(setCurrentPage =
                {
                    currentPage = Pair(1, false)
                }
                )
            }
        }
    }

    fun onPositionChanged(position: Int) {
        val charactersSize = allCharacters.value?.size ?: 0
        val sizeAllCharactersList = charactersLiveData.value?.size ?: 0
        if (position >= charactersSize - 10 && currentPage.first <= pages
            && charactersSize == sizeAllCharactersList && !currentPage.second
        ) {
            getCharacters()
            currentPage = Pair(currentPage.first, true)
        }
    }

    fun findByName(enteredText: String) {
        this.enteredText.value = enteredText
    }

    fun findBySpecies(species: String) {
        this.species.value = species
    }

    fun findByStatus(status: String) {
        this.status.value = status
    }

    fun findByGender(gender: String) {
        this.gender.value = gender
    }
}