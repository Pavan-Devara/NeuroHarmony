package com.neuro.neuroharmony.ui.login

import android.app.Dialog
import android.content.Intent
import android.preference.PreferenceManager
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.neuro.neuroharmony.utils.CommonUtils
import com.neuro.neuroharmony.utils.PrefsHelper
import com.google.gson.Gson
import com.neuro.neuroharmony.FullProfilePic
import com.neuro.neuroharmony.R
import com.neuro.neuroharmony.utils.SimpleFunctions
import com.squareup.picasso.Picasso

class ConfirmedRequestsRecyclerAdapter(
    activity: FragmentActivity?,
    val user: ArrayList<RequestsFragment.ConfirmUserRequests>,
    val viewModelExpressChatInterest: RequestChatViewModel
): RecyclerView.Adapter<ConfirmedRequestsRecyclerAdapter.ViewHolder>() {

    val REQUEST_CHAT = 0
    val REQUESTED = 1
    val ACCEPTED  = 2
    val ACTIVE = 3
    val RECEIVED = 4
    val activity = activity

    var tracker = 0
    var view_match_tracker = 0
    var gson = Gson()

    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val viewMatch_button = v.findViewById<Button>(R.id.confirmed_request_viewMatch_button)
        val request_chat = v.findViewById<Button>(R.id.request_chat_button)
        val revoke_chat = v.findViewById<Button>(R.id.revoke_chat_button)
        val requested = v.findViewById<TextView>(R.id.requested_chat)
        val accepted = v.findViewById<TextView>(R.id.accepted_chat)
        val user_name = v.findViewById<TextView>(R.id.confirmed_request_user_name)
        val user_details = v.findViewById<TextView>(R.id.confirmed_requests_user_details)
        val profile_pic = v.findViewById<ImageView>(R.id.confirmed_requests_user_photo)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.confirmed_requests, parent, false )
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return user.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user: RequestsFragment.ConfirmUserRequests = user[position]
        holder.setIsRecyclable(false)

        holder.run {
            user_name.text = user.specs[0].toString()
        }

        if (user.specs[10].toString() != ""){
        Picasso.get()
            .load(user.specs[10].toString())
            .resize(150,150)
            .transform(CircleTransform())
            .placeholder(R.mipmap.profile_pic_placeholder)
            .centerCrop()
            .into(holder.profile_pic)}



        var age = "NA"
        if(user.specs[1].toString().isNotEmpty()) {
            age = user.specs[1].toString().replace(".0","")
        }

        var profession = "NA"
        var present_residence = "NA"
        var marital_status = "NA"
        var gender = "NA"
        var religion = "NA"
        var caste = "NA"

        if (user.specs[11] != null) {
            profession = SimpleFunctions.checkifemptyString(user.specs[11].toString())!!
        }
        if (user.specs[2] != null) {
            present_residence =
                SimpleFunctions.checkifemptyString(user.specs[2].toString())!!
        }
        if (user.specs[12] != null){
            marital_status = user.specs[12]?.toString()
        }
        if (user.specs[13] != null){
            gender = user.specs[13]?.toString()
        }
        if (user.specs[3] != null){
            religion = user.specs[3]?.toString()?.removeSurrounding("\"")
        }
        if (user.specs[9] != null){
            caste = user.specs[9]?.toString()?.removeSurrounding("\"")
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
            if (user.specs[10].toString() != "") {
                val intent = Intent(activity, FullProfilePic::class.java)
                intent.putExtra(
                    "profile_pic_url",
                    user.specs[10].toString()
                )
                activity?.let { it1 -> ContextCompat.startActivity(it1, intent, null) }
            }
            else{
                Toast.makeText(activity
                    , "This user hasn't uploaded the profile picture", Toast.LENGTH_LONG).show()
            }
        }

        holder.run {
            if (user.chatStatus == REQUEST_CHAT){
                holder.request_chat.visibility = View.VISIBLE
            }else if (user.chatStatus == REQUESTED){
                holder.requested.visibility = View.VISIBLE
            }else if (user.chatStatus == ACCEPTED){
                holder.accepted.visibility = View.VISIBLE
            }else if (user.chatStatus == ACTIVE) {
                holder.accepted.visibility = View.VISIBLE
                holder.accepted.text = "ACTIVE CHAT"
            }else if (user.chatStatus == RECEIVED) {
                holder.accepted.visibility = View.VISIBLE
                holder.accepted.text = "RECEIVED"
            }
        }

        holder.request_chat.setOnClickListener{
            tracker = 1
            viewModelExpressChatInterest.requestchatrequestvm(user.specs[8] as Int)
            setupObserversRequestChat(holder)
        }

        holder.viewMatch_button.setOnClickListener{
            val mySPrefs = PreferenceManager.getDefaultSharedPreferences(activity)
            val editor = mySPrefs.edit()
            editor.remove("group_match")
            editor.apply()
            PrefsHelper().savePref("matched_user", user.specs[8].toString())
            //PrefsHelper().savePref("user_group", user.details[7])
            PrefsHelper().savePref("matched_user_name", user.specs[0])
            PrefsHelper().savePref("matched_user_pic", user.specs[10])
            PrefsHelper().savePref("matched_user_age", age)
            PrefsHelper().savePref("matched_user_marital_status", marital_status)
            PrefsHelper().savePref("matched_user_gender", gender)

            val intent = Intent(
                activity,
                MatchedUserNeuroDesireGraphResultScreen::class.java
            )
            activity?.let { it1 -> ContextCompat.startActivity(it1, intent, null) }
        }

    }


    private fun setupObserversRequestChat(holder: ViewHolder) {
        activity?.let {
            viewModelExpressChatInterest.acceptInterestrequestResponseLiveData.observe(
                it,
                Observer {
                    if (it != null) {
                        if (it.message == "Success"){
                            if (tracker==1){
                                holder.request_chat.visibility = View.GONE
                                holder.requested.visibility = View.VISIBLE
                                tracker = 0
                            }
                        }
                        else{
                            Toast.makeText(activity, it.message, Toast.LENGTH_SHORT).show()
                        }
                    }
                })
            viewModelExpressChatInterest.acceptInterestrequestAPICallStatus.observe(it, Observer {
                Chat_Requests.processStatus2(it)
            })
        }
    }

    var dialog: Dialog? = null
    private fun processStatus2(
        resource: ResourceStatus,
        holder: ViewHolder
    ){
        when (resource.status) {
            StatusType.SUCCESS -> {
                dialog?.let {
                    it.dismiss()
                }


            }
            StatusType.EMPTY_RESPONSE -> {
                dialog?.let {
                    it.dismiss()
                }

            }
            StatusType.PROGRESSING -> {
                activity?.runOnUiThread {
                    dialog = CommonUtils().showDialog(activity)
                }
            }
            StatusType.SWIPE_RELOADING -> {


            }
            StatusType.ERROR -> {
                CommonUtils().showSnackbar(
                    holder.viewMatch_button.rootView,
                    "Please try again"
                )
                dialog?.let {
                    it.dismiss()
                }


            }
            StatusType.LOADING_MORE -> {
                // CommonUtils().showSnackbar(binding.root, "Loading more..")
            }
            StatusType.NO_NETWORK -> {
                Toast.makeText(activity, "Please check internet connection", Toast.LENGTH_SHORT).show()
            }
            StatusType.SESSION_EXPIRED -> {
                //CommonUtils().showSnackbar(user_info_relative_layout.rootView,"session expired")
            }
        }
    }


}