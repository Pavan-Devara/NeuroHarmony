package com.neuro.neuroharmony.ui.login.BlockList

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.neuro.neuroharmony.R
import com.neuro.neuroharmony.ui.login.BaseActivity
import com.neuro.neuroharmony.ui.login.PushNotification.PushNotifications
import com.neuro.neuroharmony.ui.login.ResourceStatus
import com.neuro.neuroharmony.ui.login.StatusType
import kotlinx.android.synthetic.main.activity_blocked_list.*
import kotlinx.android.synthetic.main.activity_blocked_list.nothinghere_block
import kotlinx.android.synthetic.main.fragment_chat_menu_fragmnet.*

class BlockedList : BaseActivity() {
    private lateinit var viewModelBlockingList: BlockingListViewModel
    private lateinit var viewModelUnblock: BlockingListViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_blocked_list)
        viewModelBlockingList = ViewModelProviders.of(this)[BlockingListViewModel::class.java]
        viewModelUnblock = ViewModelProviders.of(this)[BlockingListViewModel::class.java]

        setupObserversblockingList()
        viewModelBlockingList.blockinglistLiveData()

        setupObserversunblock()
        back_blocklist_menu.setOnClickListener {
            onBackPressed()
        }
    }

    private fun setupObserversunblock() {
        viewModelUnblock.blockinglistsubmitResponseLiveData.observe(this, Observer {
            if (it!=null){
                if (it.message=="Success"){

                }else{
                    Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                }
            }
        })
        viewModelUnblock.blockinglistsubmitApiCallStatus.observe(this, Observer {
            processStatus(it)
        })
    }

    //BlockingListSubmitLiveData

    @SuppressLint("WrongConstant")
    private fun setupObserversblockingList() {
        val recyclerView = findViewById(R.id.blocking_list_recycler_view) as RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)

        val notifications = ArrayList<PushNotifications.NotificationDetails>()
        viewModelBlockingList.blockinglistResponseLiveData.observe(this, Observer {
            if (it!=null){
                if (it.message=="Success"){

                    if (it.data.isEmpty()){
                        recyclerView.setVisibility(View.GONE);
                        nothinghere_block.setVisibility(View.VISIBLE);
                    }

                    for (i in 0 until it.data.size){
                        val userdetails = mutableMapOf<String, Any>()
                        userdetails.put("first_name" ,it.data[i].firstName)
                        userdetails.put("user_key", it.data[i].userKey)
                        userdetails.put("profile_pic", it.data[i].profilePic)
                        userdetails.put("age", it.data[i].age)
                        userdetails.put("status", it.data[i].status)
                        userdetails.put("gender", it.data[i].gender)
                        userdetails.put("religion_name", it.data[i].religionName)
                        userdetails.put("caste_name", it.data[i].casteName)
                        userdetails.put("profession", it.data[i].profession)
                        userdetails.put("present_residence", it.data[i].presentResidence)
                        userdetails.put("maritial_status",it.data[i].maritial_Status)
                        userdetails.put("gender_choice",it.data[i].gender_choice)


                        notifications.add(PushNotifications.NotificationDetails(userdetails))
                    }
                    val adapter_recycler = BlockListRecyclerAdapter(this,notifications,viewModelUnblock)
                    recyclerView.adapter = adapter_recycler
                    adapter_recycler.notifyDataSetChanged()
                }else{
                    Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                }
            }
        })
        viewModelBlockingList.blockinglistApiCallStatus.observe(this, Observer {
            processStatus(it)
        })
    }

    private fun processStatus(resource: ResourceStatus) {

        when (resource.status) {
            StatusType.SUCCESS -> {
                dismissDialog()
            }
            StatusType.EMPTY_RESPONSE -> {
                dismissDialog()
            }
            StatusType.PROGRESSING -> {
                showDialog()
            }
            StatusType.SWIPE_RELOADING -> {
            }
            StatusType.ERROR -> {
                Toast.makeText(this, "Please try again. Server error", Toast.LENGTH_LONG).show()
                dismissDialog()
            }
            StatusType.LOADING_MORE -> {
                // CommonUtils().showSnackbar(binding.root, "Loading more..")
            }
            StatusType.NO_NETWORK -> {
                Toast.makeText(this, "Please check internet connection", Toast.LENGTH_LONG).show()
            }
            StatusType.SESSION_EXPIRED -> {
                Toast.makeText(this, "session expired", Toast.LENGTH_LONG).show()
            }
        }
    }
}
