package com.neuro.neuroharmony.ui.login

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.neuro.neuroharmony.data.model.NewMatchedUsersData.DataGetMatchedUsersResponse
import com.neuro.neuroharmony.data.model.NewMatchedUsersData.GetMatchedUsersResponse
import com.neuro.neuroharmony.data.model.NewMatchedUsersData.ItemMatchedUsers
import com.neuro.neuroharmony.data.remote.GetMatchedUsersApiService
import com.neuro.neuroharmony.utils.PrefsHelper


class MatchedUsersDataSource: PageKeyedDataSource<Int, ItemMatchedUsers?>() {
    val group_name = PrefsHelper().getPref<String>("group")

    //we will start from the first page which is 1
    val FIRST_PAGE = 1
    //this will be called once to load the initial data

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, ItemMatchedUsers?>
    ) {
        if (group_name!=null) {
            GetMatchedUsersApiService.instance.getmatchedusersapi(
                FIRST_PAGE,
                group_name,
                PrefsHelper().getPref("filtered")
            )
                .enqueue(object : retrofit2.Callback<GetMatchedUsersResponse?> {
                    override fun onResponse(
                        call: retrofit2.Call<GetMatchedUsersResponse?>?,
                        response: retrofit2.Response<GetMatchedUsersResponse?>
                    ) {
                        if (response != null && response.isSuccessful) {
                            PrefsHelper().savePref("filtered",response.body()!!.data[0].filtered)
                            callback.onResult(
                                response.body()!!.data[0].items,
                                null,
                                FIRST_PAGE + 1
                            )
                        }
                    }

                    override fun onFailure(
                        call: retrofit2.Call<GetMatchedUsersResponse?>?,
                        t: Throwable?) {}
                })
        }

    }

    //this will load the previous page
    override fun loadBefore(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, ItemMatchedUsers?>
    ) {
        if (group_name!=null) {
            GetMatchedUsersApiService.instance.getmatchedusersapi(
                params.key,
                group_name,
                PrefsHelper().getPref("filtered")
            )
                .enqueue(object : retrofit2.Callback<GetMatchedUsersResponse> {
                    override fun onResponse(
                        call: retrofit2.Call<GetMatchedUsersResponse?>?,
                        response: retrofit2.Response<GetMatchedUsersResponse?>
                    ) { //if the current page is greater than one
                        //we are decrementing the page number
                        //else there is no previous page
                        val adjacentKey = if (params.key > 1) params.key - 1 else null
                        if (response.body() != null) { //passing the loaded data
                            PrefsHelper().savePref("filtered",response.body()!!.data[0].filtered)
                            //and the previous page key
                            callback.onResult(response.body()!!.data[0].items, adjacentKey)
                        }
                    }

                    override fun onFailure(
                        call: retrofit2.Call<GetMatchedUsersResponse?>?,
                        t: Throwable?
                    ) {
                    }
                })
        }
    }

    //this will load the next page
    override fun loadAfter(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, ItemMatchedUsers?>
    ) {
        if (group_name!=null) {
            GetMatchedUsersApiService.instance.getmatchedusersapi(
                params.key,
                group_name,
                PrefsHelper().getPref("filtered")
            )
                .enqueue(object : retrofit2.Callback<GetMatchedUsersResponse?> {
                    override fun onResponse(
                        call: retrofit2.Call<GetMatchedUsersResponse?>?,
                        response: retrofit2.Response<GetMatchedUsersResponse?>
                    ) {
                        if (response.body() != null) { //if the response has next page
                            PrefsHelper().savePref("filtered",response.body()!!.data[0].filtered)
                            //incrementing the next page number
                            val key =
                                if (response.body()!!.data[0].items.isNotEmpty()) {
                                    params.key + 1
                                } else null
                            //passing the loaded data and next page value
                            callback.onResult(response.body()!!.data[0].items, key)
                        }
                    }

                    override fun onFailure(
                        call: retrofit2.Call<GetMatchedUsersResponse?>?,
                        t: Throwable?
                    ) {
                    }
                })
        }
    }

}