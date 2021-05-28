package com.neuro.neuroharmony.data.repository



import com.neuro.neuroharmony.data.model.ActivateResponse
import com.neuro.neuroharmony.data.remote.NeuroHarmonyApiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Call

object UserActivateRepository {

    private val job = Job()
    private val scope = CoroutineScope(Dispatchers.Main + job)

    fun useractivate(onResult:  (isSuccess: Boolean, response: ActivateResponse?) -> Unit) {

        scope.launch {
            NeuroHarmonyApiService.instance.activate().enqueue(object :
                    Callback<ActivateResponse> {

                    override fun onResponse(call: Call<ActivateResponse>, response: Response<ActivateResponse>) {
                        if (response != null && response.isSuccessful)
                            onResult(true, response.body()!!)
                        else
                            onResult(false, null)
                    }

                    override fun onFailure(call: Call<ActivateResponse>, t: Throwable) {
                        onResult(false, null)
                    }

                })

        }

    }
}