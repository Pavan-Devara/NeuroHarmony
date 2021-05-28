package com.neuro.neuroharmony.ui.login

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.neuro.neuroharmony.NeuroHarmonyApp
import com.neuro.neuroharmony.data.model.ProfileInfoResponse
import com.neuro.neuroharmony.data.repository.ProfileInfoRepository

class ProfileInfoViewModel: ViewModel() {

    var loginAPICallStatus = MutableLiveData<ResourceStatus>() // livedata for observing login API call status
    var loginResponseLiveData = MutableLiveData<ProfileInfoResponse>() // live data for getting Login response

    /**
     * Signup in API call
     */
    fun profileinfo(
        alternative_mobile_number: String,
        dob: String, first_name: String, gender: Int,
        height: String, last_name: String,
        middle_name: String, mother_tounge: String,
        nationality: Int, native_place: String,
        present_residence: String, status: Int,
        weight: String, fateher_name: String,country_code_picker_alt_num: Int,
        native_country: String, native_country_id: Int,
        native_state: String, native_state_id: Int,
        native_city: String, native_city_id: Int,
        present_country: String, present_country_id: Int,
        present_state: String, present_state_id: Int,
        present_city: String, present_city_id: Int) {
        if (NeuroHarmonyApp.getInstance()!!.isConnected!!) {
            loginAPICallStatus.value = ResourceStatus.loading()
            ProfileInfoRepository.profileinfo(alternative_mobile_number,dob,
                first_name,gender,height,last_name,middle_name, mother_tounge,
                nationality,native_place, present_residence,status,weight,
                fateher_name,country_code_picker_alt_num,
                native_country, native_country_id,
                native_state, native_state_id,
                native_city, native_city_id,
                present_country, present_country_id,
                present_state, present_state_id,
                present_city, present_city_id) { isSuccess, response ->

                if (isSuccess) {
                    loginAPICallStatus.value =
                        ResourceStatus.success("")
                    loginResponseLiveData.postValue(response)

                } else {
                    if (response?. message== "Sucsess") {
                        loginAPICallStatus.value =
                            ResourceStatus.sessionexpired()
                    } else {
                        loginAPICallStatus.value =
                            ResourceStatus.error("")
                    }

                }

            }


        } else {
            Log.e("SingupView","No netwrok")
            loginAPICallStatus.value = ResourceStatus.nonetwork()
        }

    }

}