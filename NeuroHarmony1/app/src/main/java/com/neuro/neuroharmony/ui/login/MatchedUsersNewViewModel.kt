package com.neuro.neuroharmony.ui.login

import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.neuro.neuroharmony.data.model.NewMatchedUsersData.DataGetMatchedUsersResponse
import com.neuro.neuroharmony.data.model.NewMatchedUsersData.ItemMatchedUsers
import com.neuro.neuroharmony.utils.PrefsHelper


class MatchedUsersNewViewModel : ViewModel() {
    //creating livedata for PagedList  and PagedKeyedDataSource
    private var networkState: LiveData<ResourceStatus?>? = null
    var itemPagedList: LiveData<PagedList<ItemMatchedUsers?>>
    var liveDataSource: LiveData<MatchedUsersDataSource>
    var loginAPICallStatus = MutableLiveData<NeuroMatchedUsersNewFragment.NetworkState>()

    //constructor
    init { //getting our data source factory
        val itemDataSourceFactory = MatchedUsersDataSourceFactory()

        //getting the live data source from data source factory
        liveDataSource = itemDataSourceFactory.getItemLiveDataSource()
        //Getting PagedList config
        val pagedListConfig = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(PrefsHelper().getPref<Int>("page_size") )
            .build()
        //Building the paged list
        itemPagedList =
            LivePagedListBuilder(itemDataSourceFactory, pagedListConfig)
                .setBoundaryCallback(object: PagedList.BoundaryCallback<ItemMatchedUsers?>() {
                    override fun onZeroItemsLoaded() {
                        super.onZeroItemsLoaded()
                        // Handle empty initial load here
                    }

                    override fun onItemAtEndLoaded(itemAtEnd: ItemMatchedUsers) {
                        super.onItemAtEndLoaded(itemAtEnd)
                        // Here you can listen to last item on list
                    }

                    override fun onItemAtFrontLoaded(itemAtFront: ItemMatchedUsers) {
                        super.onItemAtFrontLoaded(itemAtFront)
                        // Here you can listen to first item on list
                    }
                })
                .build()
    }

}
