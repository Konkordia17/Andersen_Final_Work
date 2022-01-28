package com.example.andersen_final_work.presentation.ui.location_detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.andersen_final_work.Contract
import com.example.andersen_final_work.domain.models.Character
import com.example.andersen_final_work.domain.models.Locations
import com.example.andersen_final_work.domain.usecase.characters_usecase.GetResidentUseCase
import com.example.andersen_final_work.domain.usecase.characters_usecase.GetResidentsUseCase
import com.example.andersen_final_work.domain.usecase.characters_usecase.SetResidentsUseCase
import com.example.andersen_final_work.domain.usecase.locations_usecase.GetLocationUseCase
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.launch

class LocationDetailsViewModel(
    private val getLocationUseCase: GetLocationUseCase,
    private val getResidentsUseCase: GetResidentsUseCase,
    private val getResidentUseCase: GetResidentUseCase,
    private val setResidentUseCase: SetResidentsUseCase
) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    private var locationLD = MutableLiveData<Locations?>()
    val location: LiveData<Locations?>
        get() = locationLD

    private var listResidents = MutableLiveData<List<Character?>>()
    val residents: LiveData<List<Character?>>
        get() = listResidents

    private var unknownResidents = MutableLiveData<String>()
    val unknown: LiveData<String>
        get() = unknownResidents

    private var showLoader = MutableLiveData<Boolean>()
    val loader: LiveData<Boolean>
        get() = showLoader

    fun getLocation(id: String) {
        if (id.isNotBlank()) {
            viewModelScope
            showLoader.value = true
            compositeDisposable.add(
                getLocationUseCase.getLocation(id = id)
                    .subscribe({
                        locationLD.value = it
                        showLoader.value = false
                    }, {
                        showLoader.value = false
                        unknownResidents.value = Contract.NOT_DATA_ABOUT_LOCATION
                        Log.d("TAGF", "getLocation: ")
                    })
            )
        }
    }

    private fun getResidents(idLocation: Int, ids: String) {
        viewModelScope.launch {
            val residents = getResidentsUseCase.getResidents(
                ids = ids,
                idLocation = idLocation
            )
            if (residents.isEmpty()) {
                unknownResidents.value = Contract.NOT_DATA_ABOUT_CHARACTERS
            }
            listResidents.value = residents
        }
    }

    private fun getResident(idLocation: Int, id: Int) {
        viewModelScope.launch {
            val resident = getResidentUseCase.getResident(
                idLocation = idLocation,
                id = id,
            )
            resident?.let { listResidents.value = listOf(it) }
                ?: kotlin.run { unknownResidents.value = Contract.NOT_DATA_ABOUT_CHARACTERS }
        }
    }

    fun setResidents(location: Locations) {
        setResidentUseCase.setResidents(
            location = location,
            getResident = {
                getResident(idLocation = location.id, id = it)
            },
            getResidents = {
                getResidents(idLocation = location.id, ids = it)
            }
        )
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}