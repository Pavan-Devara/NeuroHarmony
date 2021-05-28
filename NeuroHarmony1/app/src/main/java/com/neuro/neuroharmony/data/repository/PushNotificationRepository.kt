package com.neuro.neuroharmony.data.repository

import com.neuro.neuroharmony.data.model.DataProfileInfoRequest
import com.neuro.neuroharmony.data.model.ProfileInfoRequest
import com.neuro.neuroharmony.data.model.ProfileInfoResponse
import com.neuro.neuroharmony.data.model.Push_notifications.DataNotificationRequest
import com.neuro.neuroharmony.data.model.Push_notifications.ListofNotificationsResponse
import com.neuro.neuroharmony.data.model.Push_notifications.NotificationRequest
import com.neuro.neuroharmony.data.model.Push_notifications.NotificationResponse
import com.neuro.neuroharmony.data.model.Social_profile.Lifestyleinfo.LifestyleInfoSubmitResponse
import com.neuro.neuroharmony.data.remote.BioSubmitApiService
import com.neuro.neuroharmony.data.remote.PushNotificationAPIService
import com.neuro.neuroharmony.utils.PrefsHelper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object PushNotificationRepository {

    private val job = Job()
    private val scope = CoroutineScope(Dispatchers.Main + job)

    /**
     * Login
     */
    fun fcmtokenrepo(
        device_token:String, os_type:Int, onResult:  (isSuccess: Boolean, response: NotificationResponse?) -> Unit) {

        scope.launch {
            PushNotificationAPIService.instance.fcmtoken(NotificationRequest(data = DataNotificationRequest(device_token, os_type), secured = true))
                .enqueue(object :
                Callback<NotificationResponse> {
                override fun onResponse(call: Call<NotificationResponse>, response: Response<NotificationResponse>) {
                    if (response != null && response.isSuccessful)
                        onResult(true, response.body()!!)
                    else
                        onResult(false, null)
                }

                override fun onFailure(call: Call<NotificationResponse>, t: Throwable) {
                    onResult(false, null)
                }

            })


        }

    }



    fun notificationlistrepo(onResult:  (isSuccess: Boolean, response: ListofNotificationsResponse?) -> Unit) {

        scope.launch {
            PushNotificationAPIService.instance.notificationlist()
                .enqueue(object :
                    Callback<ListofNotificationsResponse> {
                    override fun onResponse(call: Call<ListofNotificationsResponse>, response: Response<ListofNotificationsResponse>) {
                        if (response != null && response.isSuccessful)
                            onResult(true, response.body()!!)
                        else
                            onResult(false, null)
                    }

                    override fun onFailure(call: Call<ListofNotificationsResponse>, t: Throwable) {
                        onResult(false, null)
                    }

                })


        }

    }


    fun readnotificationrepo(notification_id:String , onResult:  (isSuccess: Boolean, response: LifestyleInfoSubmitResponse?) -> Unit) {

        scope.launch {
            PushNotificationAPIService.instance.readnotification(notification_id)
                .enqueue(object :
                    Callback<LifestyleInfoSubmitResponse> {
                    override fun onResponse(call: Call<LifestyleInfoSubmitResponse>, response: Response<LifestyleInfoSubmitResponse>) {
                        if (response != null && response.isSuccessful)
                            onResult(true, response.body()!!)
                        else
                            onResult(false, null)
                    }

                    override fun onFailure(call: Call<LifestyleInfoSubmitResponse>, t: Throwable) {
                        onResult(false, null)
                    }

                })


        }

    }



    fun deleteTokenrepo(onResult:  (isSuccess: Boolean, response: LifestyleInfoSubmitResponse?) -> Unit) {

        scope.launch {
            PushNotificationAPIService.instance.delete_token(PrefsHelper().getPref("notification_id"))
                .enqueue(object :
                    Callback<LifestyleInfoSubmitResponse> {
                    override fun onResponse(call: Call<LifestyleInfoSubmitResponse>, response: Response<LifestyleInfoSubmitResponse>) {
                        if (response != null && response.isSuccessful)
                            onResult(true, response.body()!!)
                        else
                            onResult(false, null)
                    }

                    override fun onFailure(call: Call<LifestyleInfoSubmitResponse>, t: Throwable) {
                        onResult(false, null)
                    }

                })


        }

    }

}