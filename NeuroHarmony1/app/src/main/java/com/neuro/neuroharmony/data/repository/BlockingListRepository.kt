package com.neuro.neuroharmony.data.repository

import com.neuro.neuroharmony.data.model.BlockingList.BlockingListResponse
import com.neuro.neuroharmony.data.model.BlockingList.BlockingSubmitRequest
import com.neuro.neuroharmony.data.model.BlockingList.BlockingSubmitResponse
import com.neuro.neuroharmony.data.model.Social_profile.Lifestyleinfo.DataSubmitRequest
import com.neuro.neuroharmony.data.model.Social_profile.Lifestyleinfo.LifeStyleInfoSubmitRequest
import com.neuro.neuroharmony.data.model.Social_profile.Lifestyleinfo.LifestyleInfoSubmitResponse
import com.neuro.neuroharmony.data.remote.ReligiousInfoApiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object BlockingListRepository {
    private val job = Job()
    private val scope = CoroutineScope(Dispatchers.Main + job)

    /**
     * Login
     */
    fun blockListRepo(onResult: (isSuccess: Boolean, response: BlockingListResponse?) -> Unit) {

        scope.launch {
            ReligiousInfoApiService.instance.blockinglist()
                .enqueue(object :
                    Callback<BlockingListResponse> {

                    override fun onResponse(
                        call: Call<BlockingListResponse>,
                        response: Response<BlockingListResponse>
                    ) {
                        if (response != null && response.isSuccessful)
                            onResult(true, response.body()!!)
                        else
                            onResult(false, null)
                    }

                    override fun onFailure(call: Call<BlockingListResponse>, t: Throwable) {
                        onResult(false, null)
                    }

                })

        }
    }

    fun BlockingListSubmitRepo( user_key:Int, onResult:  (isSuccess: Boolean, response: BlockingSubmitResponse?) -> Unit) {
            scope.launch {
            ReligiousInfoApiService.instance.BlockingListSubmit(
                BlockingSubmitRequest(
                    com.neuro.neuroharmony.data.model.BlockingList.DataSubmitRequest(user_key)
            )
            )
                .enqueue(object :
                    Callback<BlockingSubmitResponse> {

                    override fun onResponse(
                        call: Call<BlockingSubmitResponse>,
                        response: Response<BlockingSubmitResponse>
                    ) {
                        if (response != null && response.isSuccessful)
                            onResult(true, response.body()!!)
                        else
                            onResult(false, null)
                    }

                    override fun onFailure(call: Call<BlockingSubmitResponse>, t: Throwable) {
                        onResult(false, null)
                    }

                })


        }
    }
}