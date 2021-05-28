package com.neuro.neuroharmony.ui.login.BlockList

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.text.Html
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.Observer

import androidx.recyclerview.widget.RecyclerView
import com.neuro.neuroharmony.FullProfilePic
import com.neuro.neuroharmony.R
import com.neuro.neuroharmony.ui.login.*
import com.neuro.neuroharmony.ui.login.PushNotification.PushNotifications
import com.neuro.neuroharmony.utils.PrefsHelper
import com.neuro.neuroharmony.utils.SimpleFunctions
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_feedback_screen.*

class BlockListRecyclerAdapter(
    val blockedList: BlockedList,
    val notifications: ArrayList<PushNotifications.NotificationDetails>,
    val viewModelUnblock: BlockingListViewModel
) : RecyclerView.Adapter<BlockListRecyclerAdapter.ViewHolder>() {

    var tracker = 0

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val user_name = itemView.findViewById<TextView>(R.id.blocked_matched_user_name)

        val button1 = itemView.findViewById<Button>(R.id.unblock_button_chat_window)

        val relativeLayout = itemView.findViewById<RelativeLayout>(R.id.blocked_user_info_relative_layout)

        val profile_pic = itemView.findViewById<ImageView>(R.id.blocked_user_photo)

        val user_details = itemView.findViewById<TextView>(R.id.blocked_matched_details)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BlockListRecyclerAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.blocking_list_recycler, parent, false )

        return ViewHolder(v)

    }

    override fun getItemCount(): Int {
        return  notifications.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user: PushNotifications.NotificationDetails = notifications[position]
        holder.setIsRecyclable(false)
        Log.d("blockedList",user.notificationdetails["first_name"].toString())
        holder.run {
            user_name.text = user.notificationdetails["first_name"].toString()
        }

        if (user.notificationdetails["profile_pic"].toString() != "") {
            Picasso.get()
                .load(user.notificationdetails["profile_pic"].toString())
                .resize(150, 150)
                .transform(CircleTransform())
                .placeholder(R.mipmap.profile_pic_placeholder)
                .centerCrop()
                .into(holder.profile_pic)
        }

        val profession = SimpleFunctions.checkifemptyString(user.notificationdetails["profession"].toString())
        val present_residence = SimpleFunctions.checkifemptyString(user.notificationdetails["present_residence"].toString())
        var age = "NA"
        if(user.notificationdetails["age"].toString().isNotEmpty()) {
            age = user.notificationdetails["age"].toString().replace(".0","")
        }
        val text: String =
            java.lang.String.format(
                "%s | %s | %s <br /><br /> %s | %s <br /><br /> %s | %s",
                age,
                //NeuroMatchedUsers.Status.values().find { it.status == user.notificationdetails["status"] },
                user.notificationdetails["maritial_status"]?.toString(),
                user.notificationdetails["gender_choice"]?.toString(),
                //NeuroMatchedUsers.Gender.values().find { it.status == user.notificationdetails["gender"] },
                user.notificationdetails["religion_name"]?.toString() ?.removeSurrounding("\""),
                user.notificationdetails["caste_name"]?.toString() ?.removeSurrounding("\""),
                profession,
                present_residence
            )
        holder.run {
            user_details.setText(Html.fromHtml(text), TextView.BufferType.SPANNABLE)
        }

        holder.profile_pic.setOnClickListener{
            if (user.notificationdetails["profile_pic"].toString() != "") {
                val intent = Intent(blockedList, FullProfilePic::class.java)
                intent.putExtra(
                    "profile_pic_url",
                    user.notificationdetails["profile_pic"].toString()
                )
                blockedList?.let { it1 -> ContextCompat.startActivity(it1, intent, null) }
            }
            else{
                Toast.makeText(blockedList, "This user hasn't uploaded the profile picture", Toast.LENGTH_LONG).show()
            }
        }

        val cost_for_unblocking = PrefsHelper().getPref<Int>("unblocking_cost")
        holder.button1.setOnClickListener {

            val dialogBuilder = AlertDialog.Builder(blockedList)

            // set message of alert dialog
//                    dialogBuilder.setMessage("Feedback Submitted \n       Successfully")

            dialogBuilder.setMessage("This action will deduct "+cost_for_unblocking.toString()+" tokens from your account")
                // if the dialog is cancelable
                .setCancelable(false)
                // positive button text and action
                .setPositiveButton("Proceed", DialogInterface.OnClickListener {
                        dialog, id -> dialog.cancel()
                    tracker = 1
                    viewModelUnblock.BlockingListSubmitLiveData(user.notificationdetails["user_key"].toString().toInt())
                    setupObserversStartChat(position)
                })
            // negative button text and action
                .setNegativeButton("Cancel", DialogInterface.OnClickListener {
                        dialog, id -> dialog.cancel()
                })
            // create dialog box
            val alert = dialogBuilder.create()
            // set title for alert dialog box
            alert.setTitle("Message")
            // show alert dialog
            alert.show()


        }

    }
    private fun setupObserversStartChat(position: Int) {
        blockedList?.let {
            viewModelUnblock.blockinglistsubmitResponseLiveData.observe(it, Observer {
                if (it != null) {
                    if (it.message == "Success") {
                        if (tracker == 1) {
                            notifications.removeAt(position)
                            notifyDataSetChanged()
                            tracker = 0
                        }
                    }
                    else{
                        Toast.makeText(blockedList, it.message, Toast.LENGTH_SHORT).show()
                    }
                }
            })
            viewModelUnblock.blockinglistsubmitApiCallStatus.observe(blockedList, Observer {
                ChatMenu.processStatus1(it)
            })
        }
    }
}