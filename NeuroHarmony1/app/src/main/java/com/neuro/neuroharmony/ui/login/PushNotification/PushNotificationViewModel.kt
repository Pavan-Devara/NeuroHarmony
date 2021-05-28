package com.neuro.neuroharmony.ui.login.PushNotification

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.neuro.neuroharmony.NeuroHarmonyApp
import com.neuro.neuroharmony.data.model.ProfileInfoResponse
import com.neuro.neuroharmony.data.model.Push_notifications.ListofNotificationsResponse
import com.neuro.neuroharmony.data.model.Push_notifications.NotificationResponse
import com.neuro.neuroharmony.data.model.Social_profile.Lifestyleinfo.LifestyleInfoSubmitResponse
import com.neuro.neuroharmony.data.repository.ProfileInfoRepository
import com.neuro.neuroharmony.data.repository.PushNotificationRepository
import com.neuro.neuroharmony.ui.login.ResourceStatus
import okhttp3.ResponseBody

class PushNotificationViewModel: ViewModel() {

    var loginAPICallStatus = MutableLiveData<ResourceStatus>() // livedata for observing login API call status
    var loginResponseLiveData = MutableLiveData<NotificationResponse>() // live data for getting Login response

    var listnotificationAPICallStatus = MutableLiveData<ResourceStatus>() // livedata for observing login API call status
    var listnotificationResponseLiveData = MutableLiveData<ListofNotificationsResponse>() // live data for getting Login response

    var readnotificationAPICallStatus = MutableLiveData<ResourceStatus>() // livedata for observing login API call status
    var readnotificationResponseLiveData = MutableLiveData<LifestyleInfoSubmitResponse>()
    /**
     * Signup in API call
     */
    fun profileinfo(
        device_token:String, os_type: Int) {
        if (NeuroHarmonyApp.getInstance()!!.isConnected!!) {
            loginAPICallStatus.value = ResourceStatus.loading()
            PushNotificationRepository.fcmtokenrepo(device_token, os_type) { isSuccess, response ->

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




    fun notificationlistinfo() {
        if (NeuroHarmonyApp.getInstance()!!.isConnected!!) {
            listnotificationAPICallStatus.value = ResourceStatus.loading()
            PushNotificationRepository.notificationlistrepo { isSuccess, response ->

                if (isSuccess) {
                    listnotificationAPICallStatus.value =
                        ResourceStatus.success("")
                    listnotificationResponseLiveData.postValue(response)

                } else {
                    if (response?. message== "Sucsess") {
                        listnotificationAPICallStatus.value =
                            ResourceStatus.sessionexpired()
                    } else {
                        listnotificationAPICallStatus.value =
                            ResourceStatus.error("")
                    }

                }

            }


        } else {
            Log.e("SingupView","No netwrok")
            listnotificationAPICallStatus.value = ResourceStatus.nonetwork()
        }

    }


    fun readnotificationinfo(notification_id : String) {
        if (NeuroHarmonyApp.getInstance()!!.isConnected!!) {
            readnotificationAPICallStatus.value = ResourceStatus.loading()
            PushNotificationRepository.readnotificationrepo(notification_id = notification_id) { isSuccess, response ->

                if (isSuccess) {
                    readnotificationAPICallStatus.value =
                        ResourceStatus.success("")
                    readnotificationResponseLiveData.postValue(response)

                } else {
                    if (response?. message== "Sucsess") {
                        readnotificationAPICallStatus.value =
                            ResourceStatus.sessionexpired()
                    } else {
                        readnotificationAPICallStatus.value =
                            ResourceStatus.error("")
                    }

                }

            }


        } else {
            Log.e("SingupView","No netwrok")
            readnotificationAPICallStatus.value = ResourceStatus.nonetwork()
        }

    }




    fun deleteTokenInfo() {
        if (NeuroHarmonyApp.getInstance()!!.isConnected!!) {
            readnotificationAPICallStatus.value = ResourceStatus.loading()
            PushNotificationRepository.deleteTokenrepo { isSuccess, response ->

                if (isSuccess) {
                    readnotificationAPICallStatus.value =
                        ResourceStatus.success("")
                    readnotificationResponseLiveData.postValue(response)

                } else {
                    if (response?. message== "Sucsess") {
                        readnotificationAPICallStatus.value =
                            ResourceStatus.sessionexpired()
                    } else {
                        readnotificationAPICallStatus.value =
                            ResourceStatus.error("")
                    }

                }

            }


        } else {
            Log.e("SingupView","No netwrok")
            readnotificationAPICallStatus.value = ResourceStatus.nonetwork()
        }

    }
}