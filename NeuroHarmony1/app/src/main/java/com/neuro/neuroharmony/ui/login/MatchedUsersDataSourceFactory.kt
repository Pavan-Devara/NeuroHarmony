package com.neuro.neuroharmony.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.paging.DataSource
import com.neuro.neuroharmony.data.model.NewMatchedUsersData.DataGetMatchedUsersResponse
import com.neuro.neuroharmony.data.model.NewMatchedUsersData.ItemMatchedUsers


class MatchedUsersDataSourceFactory : DataSource.Factory<Int, ItemMatchedUsers>() {
    //creating the mutable live data
    private val itemLiveDataSource =  MutableLiveData<MatchedUsersDataSource>()
    private lateinit var itemDataSource: MatchedUsersDataSource

    override fun create(): MatchedUsersDataSource { //getting our data source object
        itemDataSource = MatchedUsersDataSource()
        //posting the datasource to get the values
        itemLiveDataSource.postValue(itemDataSource)

        //returning the datasource
        return itemDataSource
    }

    //getter for itemlivedatasource
    fun getItemLiveDataSource(): MutableLiveData<MatchedUsersDataSource> {
        return itemLiveDataSource
    }

}
