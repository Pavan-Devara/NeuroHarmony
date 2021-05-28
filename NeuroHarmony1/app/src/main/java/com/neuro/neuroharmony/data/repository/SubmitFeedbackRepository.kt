package com.neuro.neuroharmony.data.repository

import com.neuro.neuroharmony.data.model.Feedback.DataSubmitFeedbackRequest
import com.neuro.neuroharmony.data.model.Feedback.SubmitFeedbackRequest
import com.neuro.neuroharmony.data.model.Feedback.SubmitFeedbackResponse
import com.neuro.neuroharmony.data.remote.FeatureListApiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object SubmitFeedbackRepository {
    private val job = Job()
    private val scope = CoroutineScope(Dispatchers.Main + job)

    /**
     * Login
     */
    fun submitfeedback(feature_id: String,comment:String,

                     onResult:  (isSuccess: Boolean, response: SubmitFeedbackResponse?) -> Unit) {

        scope.launch {
            FeatureListApiService.instance.submitfeedback(
                SubmitFeedbackRequest(data = DataSubmitFeedbackRequest(feature_id,comment))
            )
                .enqueue(object :
                    Callback<SubmitFeedbackResponse> {

                    override fun onResponse(call: Call<SubmitFeedbackResponse>, response: Response<SubmitFeedbackResponse>) {
                        if (response != null && response.isSuccessful)
                            onResult(true, response.body()!!)
                        else
                            onResult(false, null)
                    }

                    override fun onFailure(call: Call<SubmitFeedbackResponse>, t: Throwable) {
                        onResult(false, null)
                    }

                })

        }

    }
}