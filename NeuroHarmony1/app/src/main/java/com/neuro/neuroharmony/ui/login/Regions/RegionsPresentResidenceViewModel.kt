package com.neuro.neuroharmony.ui.login.Regions

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.neuro.neuroharmony.NeuroHarmonyApp
import com.neuro.neuroharmony.data.model.Cities.CitiesApiResponse
import com.neuro.neuroharmony.data.model.CountriesApi.CountriesApiResponse
import com.neuro.neuroharmony.data.model.States.StatesApiResponse
import com.neuro.neuroharmony.data.repository.RegionResponseRepository
import com.neuro.neuroharmony.ui.login.ResourceStatus

class RegionsPresentResidenceViewModel: ViewModel() {

    var countriesResponseLiveData = MutableLiveData<CountriesApiResponse>() // livedata for observing incoming data
    var countriesAPICallStatus = MutableLiveData<ResourceStatus>() // livedata for observing API call status

    var statesResponseLiveData = MutableLiveData<StatesApiResponse>() // livedata for observing incoming data
    var stateesAPICallStatus = MutableLiveData<ResourceStatus>() // livedata for observing API call status

    var citiesResponseLiveData = MutableLiveData<CitiesApiResponse>() // livedata for observing incoming data
    var citiesAPICallStatus = MutableLiveData<ResourceStatus>() // livedata for observing API call status


    fun countriesvm() {
        if (NeuroHarmonyApp.getInstance()!!.isConnected!!) {
            countriesAPICallStatus.value = ResourceStatus.loading()
            RegionResponseRepository.countriesRepo { isSuccess, response ->

                if (isSuccess) {
                    countriesAPICallStatus.value =
                        ResourceStatus.success("")
                    countriesResponseLiveData.postValue(response)

                } else {
                    if (response?. message== "Sucsess") {
                        countriesAPICallStatus.value =
                            ResourceStatus.sessionexpired()
                    } else {
                        countriesAPICallStatus.value =
                            ResourceStatus.error("")
                    }
                }
            }

        } else {
            Log.e("SingupView","No netwrok")
            countriesAPICallStatus.value = ResourceStatus.nonetwork()
        }

    }


    fun statesvm() {
        if (NeuroHarmonyApp.getInstance()!!.isConnected!!) {
            countriesAPICallStatus.value = ResourceStatus.loading()
            RegionResponseRepository.statesRepoPresent() { isSuccess, response ->

                if (isSuccess) {
                    stateesAPICallStatus.value =
                        ResourceStatus.success("")
                    statesResponseLiveData.postValue(response)

                } else {
                    if (response?. message== "Sucsess") {
                        stateesAPICallStatus.value =
                            ResourceStatus.sessionexpired()
                    } else {
                        stateesAPICallStatus.value =
                            ResourceStatus.error("")
                    }
                }
            }

        } else {
            Log.e("SingupView","No netwrok")
            stateesAPICallStatus.value = ResourceStatus.nonetwork()
        }
    }



    fun citiesvm() {
        if (NeuroHarmonyApp.getInstance()!!.isConnected!!) {
            citiesAPICallStatus.value = ResourceStatus.loading()
            RegionResponseRepository.citiesRepoPresent() { isSuccess, response ->

                if (isSuccess) {
                    citiesAPICallStatus.value =
                        ResourceStatus.success("")
                    citiesResponseLiveData.postValue(response)

                } else {
                    if (response?. message== "Sucsess") {
                        citiesAPICallStatus.value =
                            ResourceStatus.sessionexpired()
                    } else {
                        citiesAPICallStatus.value =
                            ResourceStatus.error("")
                    }
                }
            }

        } else {
            Log.e("SingupView","No netwrok")
            citiesAPICallStatus.value = ResourceStatus.nonetwork()
        }
    }
}