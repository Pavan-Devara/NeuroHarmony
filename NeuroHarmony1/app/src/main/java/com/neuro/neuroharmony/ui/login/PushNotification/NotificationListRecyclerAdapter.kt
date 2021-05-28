package com.neuro.neuroharmony.ui.login.PushNotification

import android.content.Intent
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.neuro.neuroharmony.R
import com.neuro.neuroharmony.ui.login.*
import com.neuro.neuroharmony.ui.login.CompleteMatch.FinalizeMatchMenu
import com.neuro.neuroharmony.ui.login.manualMatching.PartnerListMenu
import com.neuro.neuroharmony.utils.PrefsHelper
import com.squareup.picasso.Picasso
import java.util.ArrayList

class NotificationListRecyclerAdapter(
    val notifications: ArrayList<PushNotifications.NotificationDetails>,
    val pushNotifications: PushNotifications,
    val viewModelReadNotifications: PushNotificationViewModel
) :
    RecyclerView.Adapter<NotificationListRecyclerAdapter.ViewHolder>() {


    var tracker = 0

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val notification_title = itemView.findViewById<TextView>(R.id.notification_name)
        val relativeLayout = itemView.findViewById<RelativeLayout>(R.id.relative_layout_notifications)
        val profile_pic = itemView.findViewById<ImageView>(R.id.user_photo_notifications)
        val notification_details = itemView.findViewById<TextView>(R.id.notification_description)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.notifications_list_recycler_view, parent, false )
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return  notifications.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val notification: PushNotifications.NotificationDetails = notifications[position]
        if(notification.notificationdetails["status"] != true){
            holder.relativeLayout.setBackgroundColor(Color.parseColor("#FCF0B2"))
        }
        holder.setIsRecyclable(false)

        holder.run {
            notification_title.text = notification.notificationdetails["title"].toString()
        }

        holder.run {
            notification_details.text = notification.notificationdetails["message"].toString()
        }

        if (notification.notificationdetails["profile_pic"].toString() != "") {
            Picasso.get()
                .load(notification.notificationdetails["profile_pic"].toString())
                .resize(150, 150)
                .transform(CircleTransform())
                .placeholder(R.mipmap.profile_pic_placeholder)
                .centerCrop()
                .into(holder.profile_pic)
        }
        var screen = Intent(pushNotifications, PushNotifications::class.java)

        val type = notification.notificationdetails["type"].toString()
        when (type) {

            "interest expressed" ->{screen = Intent(pushNotifications, Chat_Requests::class.java)
                                    screen.putExtra("position", 1)}
            "final partner interest express"->{
                screen = Intent(pushNotifications, FinalizeMatchMenu::class.java)
                screen.putExtra("position", 1)
            }

                    "final partner interest accept"->{
                screen = Intent(pushNotifications, FinalizeMatchMenu::class.java)
                screen.putExtra("position", 0)
            }

            "manual_match_request" -> {screen = Intent(pushNotifications, PartnerListMenu::class.java)
                screen.putExtra("position", 2)}

            "manual_match_accept" -> {screen = Intent(pushNotifications, PartnerListMenu::class.java)
                                    screen.putExtra("position", 0)}

            "start_chat" -> {screen = Intent(pushNotifications, ChatMenu::class.java)
                screen.putExtra("position", 0)}
            "couple_match_request" -> {screen = Intent(pushNotifications, PartnerListMenu::class.java)
                screen.putExtra("position", 2)}
            "couple_match_accept" -> {screen = Intent(pushNotifications, PartnerListMenu::class.java)
                screen.putExtra("position", 0)}

            "chat requested" -> {screen = Intent(pushNotifications, ChatMenu::class.java)
                                    screen.putExtra("position", 2)}
            "chat accepted" -> {screen = Intent(pushNotifications, ChatMenu::class.java)
                                screen.putExtra("position", 1)}

        }

        Log.d("screen", screen.toString())
        holder.relativeLayout.setOnClickListener {
            tracker = 1
            viewModelReadNotifications.readnotificationinfo(notification.notificationdetails["id"].toString())
//            pushNotifications?.let { it1 -> ContextCompat.startActivity(it1, screen, null) }
//            Toast.makeText(pushNotifications,notification.notificationdetails["id"].toString(),Toast.LENGTH_LONG).show()
            setupObservers(screen)
        }
    }



    private fun setupObservers(screen: Intent) {
        pushNotifications?.let {
            viewModelReadNotifications.readnotificationResponseLiveData.observe(
                it,
                Observer {
                    if (it != null) {
                        if (it.message == "Success"){
                            if (tracker==1){
                                pushNotifications?.let { it1 -> ContextCompat.startActivity(it1, screen, null) }
                                tracker = 0
                            }
                        }
                        else{
                            Toast.makeText(pushNotifications, it.message, Toast.LENGTH_SHORT).show()
                        }
                    }
                })
            viewModelReadNotifications.readnotificationAPICallStatus.observe(it, Observer {
                Chat_Requests.processStatus2(it)
            })
        }
    }

}
