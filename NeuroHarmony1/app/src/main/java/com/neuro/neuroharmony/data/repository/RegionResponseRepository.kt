package com.neuro.neuroharmony.data.repository

import com.neuro.neuroharmony.data.model.Cities.CitiesApiResponse
import com.neuro.neuroharmony.data.model.CountriesApi.CountriesApiResponse
import com.neuro.neuroharmony.data.model.ReligiousInfoResponse
import com.neuro.neuroharmony.data.model.States.StatesApiResponse
import com.neuro.neuroharmony.data.remote.RegionApiService
import com.neuro.neuroharmony.data.remote.ReligiousInfoApiService
import com.neuro.neuroharmony.utils.PrefsHelper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object RegionResponseRepository {

    private val job = Job()
    private val scope = CoroutineScope(Dispatchers.Main + job)

    /**
     * Login
     */
    fun countriesRepo(onResult:  (isSuccess: Boolean, response: CountriesApiResponse?) -> Unit) {

        scope.launch {
            RegionApiService.instance.getCountries(

            )
                .enqueue(object :
                    Callback<CountriesApiResponse> {

                    override fun onResponse(call: Call<CountriesApiResponse>, response: Response<CountriesApiResponse>) {
                        if (response != null && response.isSuccessful)
                            onResult(true, response.body()!!)
                        else
                            onResult(false, null)
                    }

                    override fun onFailure(call: Call<CountriesApiResponse>, t: Throwable) {
                        onResult(false, null)
                    }

                })

        }

    }



    fun statesRepo(onResult:  (isSuccess: Boolean, response: StatesApiResponse?) -> Unit) {

        scope.launch {
            RegionApiService.instance.getStates(
                PrefsHelper().getPref("country_id")
            )
                .enqueue(object :
                    Callback<StatesApiResponse> {

                    override fun onResponse(call: Call<StatesApiResponse>, response: Response<StatesApiResponse>) {
                        if (response != null && response.isSuccessful)
                            onResult(true, response.body()!!)
                        else
                            onResult(false, null)
                    }

                    override fun onFailure(call: Call<StatesApiResponse>, t: Throwable) {
                        onResult(false, null)
                    }

                })

        }

    }



    fun citiesRepo(onResult:  (isSuccess: Boolean, response: CitiesApiResponse?) -> Unit) {

        scope.launch {
            RegionApiService.instance.getCitites(
                PrefsHelper().getPref("state_id")
            )
                .enqueue(object :
                    Callback<CitiesApiResponse> {

                    override fun onResponse(call: Call<CitiesApiResponse>, response: Response<CitiesApiResponse>) {
                        if (response != null && response.isSuccessful)
                            onResult(true, response.body()!!)
                        else
                            onResult(false, null)
                    }

                    override fun onFailure(call: Call<CitiesApiResponse>, t: Throwable) {
                        onResult(false, null)
                    }

                })

        }

    }


    fun statesRepoPresent(onResult:  (isSuccess: Boolean, response: StatesApiResponse?) -> Unit) {

        scope.launch {
            RegionApiService.instance.getStates(
                PrefsHelper().getPref("country_id_present")
            )
                .enqueue(object :
                    Callback<StatesApiResponse> {

                    override fun onResponse(call: Call<StatesApiResponse>, response: Response<StatesApiResponse>) {
                        if (response != null && response.isSuccessful)
                            onResult(true, response.body()!!)
                        else
                            onResult(false, null)
                    }

                    override fun onFailure(call: Call<StatesApiResponse>, t: Throwable) {
                        onResult(false, null)
                    }

                })

        }

    }



    fun citiesRepoPresent(onResult:  (isSuccess: Boolean, response: CitiesApiResponse?) -> Unit) {

        scope.launch {
            RegionApiService.instance.getCitites(
                PrefsHelper().getPref("state_id_present")
            )
                .enqueue(object :
                    Callback<CitiesApiResponse> {

                    override fun onResponse(call: Call<CitiesApiResponse>, response: Response<CitiesApiResponse>) {
                        if (response != null && response.isSuccessful)
                            onResult(true, response.body()!!)
                        else
                            onResult(false, null)
                    }

                    override fun onFailure(call: Call<CitiesApiResponse>, t: Throwable) {
                        onResult(false, null)
                    }

                })

        }

    }

}