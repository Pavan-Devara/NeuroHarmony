package com.neuro.neuroharmony.ui.login.PushNotification

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.neuro.neuroharmony.R
import com.neuro.neuroharmony.ui.login.*
import kotlinx.android.synthetic.main.notification_toolbar.*

class PushNotifications : BaseActivity() {

    private lateinit var viewModelNotificationsList: PushNotificationViewModel
    private lateinit var viewModelReadNotifications: PushNotificationViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_push_notifications)

        viewModelNotificationsList = ViewModelProviders.of(this)[PushNotificationViewModel::class.java]
        viewModelReadNotifications = ViewModelProviders.of(this)[PushNotificationViewModel::class.java]

        setupObserversNotificationsList()
        viewModelNotificationsList.notificationlistinfo()

        setupObserversReadNotifications()

        back_notification.setOnClickListener{
            val intent = Intent(this, HomePage1::class.java)
            startActivity(intent)
        }
    }



    private fun setupObserversReadNotifications() {
        viewModelReadNotifications.readnotificationResponseLiveData.observe(this, Observer {
            if (it!=null){
                if (it.message=="Success"){

                }else{
                    Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                }
            }
        })
        viewModelReadNotifications.readnotificationAPICallStatus.observe(this, Observer {
            processStatus(it)
        })
    }


    @SuppressLint("WrongConstant")
    private fun setupObserversNotificationsList() {
        val recyclerView = findViewById(R.id.notification_list_recycler_view) as RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)

        val notifications = ArrayList<NotificationDetails>()
        viewModelNotificationsList.listnotificationResponseLiveData.observe(this, Observer {
            if (it!=null){
                if (it.message=="Success"){
                    for (i in 0 until it.data.size){
                        val userdetails = mutableMapOf<String, Any>()
                        userdetails.put("title" ,it.data[i].title)
                        userdetails.put("status", it.data[i].read_status)
                        userdetails.put("profile_pic", it.data[i].image_url)
                        userdetails.put("message", it.data[i].message)
                        userdetails.put("type", it.data[i].type)
                        userdetails.put("id", it.data[i].id)

                        notifications.add(NotificationDetails(userdetails))
                    }
                    val adapter_recycler = NotificationListRecyclerAdapter(notifications, this, viewModelReadNotifications)
                    recyclerView.adapter = adapter_recycler
                    adapter_recycler.notifyDataSetChanged()
                }else{
                    Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                }
            }
        })
        viewModelNotificationsList.listnotificationAPICallStatus.observe(this, Observer {
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



    data class  NotificationDetails(
        val notificationdetails: MutableMap<String, Any>
    )

}
