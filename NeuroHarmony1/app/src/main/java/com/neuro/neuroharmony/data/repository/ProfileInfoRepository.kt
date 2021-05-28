package com.neuro.neuroharmony.data.repository


import com.neuro.neuroharmony.data.model.Cities.DataCitiesApiResponse
import com.neuro.neuroharmony.data.model.CountriesApi.DataCountriesApiResponse
import com.neuro.neuroharmony.data.model.DataProfileInfoRequest
import com.neuro.neuroharmony.data.model.ProfileInfoRequest
import com.neuro.neuroharmony.data.model.ProfileInfoResponse
import com.neuro.neuroharmony.data.model.States.DataStatesApiResponse
import com.neuro.neuroharmony.data.remote.BioSubmitApiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object ProfileInfoRepository {
    private val job = Job()
    private val scope = CoroutineScope(Dispatchers.Main + job)

    /**
     * Login
     */
    fun profileinfo(
        alternative_mobile_number: String,
        dob: String, first_name: String, gender: Int,
        height: String, last_name: String, middle_name: String,
        mother_tounge: String, nationality: Int,
        native_place: String, present_residence: String,
        status: Int, weight: String, father_name: String,
        country_code_picker_alt_num: Int, native_country: String,
        native_country_id: Int, native_state: String,
        native_state_id: Int, native_city: String,
        native_city_id: Int,  present_country: String,
        present_country_id: Int, present_state: String,
        present_state_id: Int, present_city: String,
        present_city_id: Int,
        onResult:  (isSuccess: Boolean, response: ProfileInfoResponse?) -> Unit) {

        scope.launch {
            BioSubmitApiService.instance.profileinfo(ProfileInfoRequest
                (data = DataProfileInfoRequest(
                alternative_mobile_number,
                dob,
                first_name,
                gender,
                height,
                last_name,
                middle_name,
                mother_tounge,
                nationality,
                native_place,
                present_residence,
                status,weight,
                father_name,
                country_code_picker_alt_num,
                native_country = DataCountriesApiResponse(native_country, native_country_id),
                native_state = DataStatesApiResponse(native_state_id, native_state),
                native_city = DataCitiesApiResponse(native_city, native_city_id),
                present_country = DataCountriesApiResponse(present_country, present_country_id),
                present_state = DataStatesApiResponse(present_state_id, present_state),
                present_city = DataCitiesApiResponse(present_city, present_city_id)
            ))).enqueue(object :Callback<ProfileInfoResponse>{
                    override fun onResponse(call: Call<ProfileInfoResponse>, response: Response<ProfileInfoResponse>) {
                        if (response != null && response.isSuccessful)
                            onResult(true, response.body()!!)
                        else
                            onResult(false, null)
                    }

                    override fun onFailure(call: Call<ProfileInfoResponse>, t: Throwable) {
                        onResult(false, null)
                    }

                })


        }

    }
}