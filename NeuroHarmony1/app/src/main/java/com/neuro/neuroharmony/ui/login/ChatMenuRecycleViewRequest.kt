package com.neuro.neuroharmony.ui.login

import android.content.Intent
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.neuro.neuroharmony.FullProfilePic
import com.neuro.neuroharmony.R
import com.neuro.neuroharmony.utils.SimpleFunctions
import com.squareup.picasso.Picasso

class ChatMenuRecycleViewRequest(
    activity: FragmentActivity?,
    val userDetails: ArrayList<ChatMenu.Userdetails>,
    val viewModelAcceptChat: AcceptChatRequestViewModel
) : RecyclerView.Adapter<ChatMenuRecycleViewRequest.ViewHolder>() {


    val activity = activity
    var trackerAccept = 0
    var trackerDecline = 0
    var positionPresent: Int? = null

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val user_name = itemView.findViewById<TextView>(R.id.chat_matched_user_name_requests)

        val button1 = itemView.findViewById<Button>(R.id.chat_accept_interest_button_requests)
        val button2 = itemView.findViewById<Button>(R.id.chat_decline_interest_button_requests)
        val relativeLayout = itemView.findViewById<RelativeLayout>(R.id.chat_user_info_relative_layout_requests)
        val profile_pic = itemView.findViewById<ImageView>(R.id.chat_matched_user_photo_requests)
        val user_details = itemView.findViewById<TextView>(R.id.chat_matched_details_requests)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatMenuRecycleViewRequest.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.chat_recycler_view_requests, parent, false )

        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return  userDetails.size
    }

    override fun onBindViewHolder(holder: ChatMenuRecycleViewRequest.ViewHolder, position: Int) {
        val user:ChatMenu.Userdetails = userDetails[position]
        holder.setIsRecyclable(false)

        holder.run {
            user_name.text = user.specs[0].toString()
        }

        if (user.specs[2].toString() != ""){
        Picasso.get()
            .load(user.specs[2].toString())
            .resize(150,150)
            .transform(CircleTransform())
            .placeholder(R.mipmap.profile_pic_placeholder)
            .centerCrop()
            .into(holder.profile_pic)
        }

        var age = "NA"
        if(user.specs[3].toString().isNotEmpty()) {
            age = user.specs[3].toString().replace(".0","")
        }

        var profession = "NA"
        var present_residence = "NA"
        var marital_status = "NA"
        var gender = "NA"
        var religion = "NA"
        var caste = "NA"

        if (user.specs[8] != null) {
            profession = SimpleFunctions.checkifemptyString(user.specs[8].toString())!!
        }
        if (user.specs[9] != null) {
            present_residence =
                SimpleFunctions.checkifemptyString(user.specs[9].toString())!!
        }
        if (user.specs[10] != null){
            marital_status = user.specs[10]?.toString()
        }
        if (user.specs[11] != null){
            gender = user.specs[11]?.toString()
        }
        if (user.specs[6] != null){
            religion = user.specs[6]?.toString()?.removeSurrounding("\"")
        }
        if (user.specs[7] != null){
            caste = user.specs[7]?.toString()?.removeSurrounding("\"")
        }

        val text: String =
            java.lang.String.format(
                "%s | %s | %s <br /><br /> %s | %s <br /><br /> %s | %s",
                age,
                marital_status,
                gender,
                religion,
                caste,
                profession,
                present_residence
            )
        holder.run {
            user_details.setText(Html.fromHtml(text), TextView.BufferType.SPANNABLE)
        }

        holder.profile_pic.setOnClickListener{
            if (user.specs[2].toString() != "") {
                val intent = Intent(activity, FullProfilePic::class.java)
                intent.putExtra(
                    "profile_pic_url",
                    user.specs[2].toString()
                )
                activity?.let { it1 -> ContextCompat.startActivity(it1, intent, null) }
            }
            else{
                Toast.makeText(activity
                    , "This user hasn't uploaded the profile picture", Toast.LENGTH_LONG).show()
            }
        }

        holder.button2.setOnClickListener{
            trackerDecline =1
            positionPresent = position
            val key =  user.specs[1] as Int
            viewModelAcceptChat.acceptchatrequestvm(2, key)
            setupObserversDeclineChat(position)
        }


        holder.button1.setOnClickListener{
            trackerAccept =1
            positionPresent = position
            val key =  user.specs[1] as Int
            viewModelAcceptChat.acceptchatrequestvm(1, key)
            setupObserversAcceptChat(position)
        }

    }

    private fun setupObserversDeclineChat(position: Int) {
        activity?.let {
            viewModelAcceptChat.acceptInterestrequestResponseLiveData.observe(it, Observer {
                if (it!=null){
                    if (it.message == "Success"){
                        if (trackerDecline == 1){
                            positionPresent?.let { it1 -> userDetails.removeAt(it1) }
                            notifyDataSetChanged()
                            trackerDecline = 0
                        }
                    }
                    else{
                        Toast.makeText(activity, it.message, Toast.LENGTH_SHORT).show()
                    }
                }
            })
            viewModelAcceptChat.acceptInterestrequestAPICallStatus.observe(activity, Observer {
                ChatMenu.processStatus2(it)
            })
        }
    }

    private fun setupObserversAcceptChat(position: Int) {
        activity?.let {
            viewModelAcceptChat.acceptInterestrequestResponseLiveData.observe(it, Observer {
                if (it!=null){
                    if (it.message == "Success"){
                        if (trackerAccept == 1){
                            positionPresent?.let { it1 -> userDetails.removeAt(it1) }
                            notifyDataSetChanged()
                            trackerAccept = 0
                        }
                    }
                    else{
                        Toast.makeText(activity, it.message, Toast.LENGTH_SHORT).show()
                    }
                }
            })
            viewModelAcceptChat.acceptInterestrequestAPICallStatus.observe(activity, Observer {
                ChatMenu.processStatus2(it)
            })
        }
    }
}