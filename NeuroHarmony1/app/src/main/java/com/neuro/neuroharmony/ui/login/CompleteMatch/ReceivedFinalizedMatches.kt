package com.neuro.neuroharmony.ui.login.CompleteMatch

import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.neuro.neuroharmony.R
import com.neuro.neuroharmony.ui.login.CircleTransform
import com.neuro.neuroharmony.ui.login.manualMatching.PartnerListMenu
import com.neuro.neuroharmony.utils.PrefsHelper
import com.neuro.neuroharmony.utils.SimpleFunctions
import com.squareup.picasso.Picasso

class ReceivedFinalizedMatches(
    val activity: FragmentActivity?,
    val userDetails: ArrayList<FinalizeMatchMenu.UserDetails_Finalize>,
    val viewModelDecline: DeclineCompleteMatchViewModel

    //val viewModelAcceptDecline: AcceptDeclineDelinkViewModel
): RecyclerView.Adapter<ReceivedFinalizedMatches.ViewHolder>() {

    var trackerAccept = 0
    var trackerDecline = 0
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)  {
        val text = itemView.findViewById<TextView>(R.id.finalize_received_request_user_name)
        val profile_pic = itemView.findViewById<ImageView>(R.id.finalize_received_requests_user_photo)
        val details = itemView.findViewById<TextView>(R.id.finalize_received_requests_user_details)
        val received_accept = itemView.findViewById<Button>(R.id.finalize_received_request_accept_button)
        val received_decline = itemView.findViewById<Button>(R.id.finalize_received_request_decline_button)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.finalize_received_recycle_layout, parent, false )
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {

        return userDetails.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user: FinalizeMatchMenu.UserDetails_Finalize = userDetails[position]

        holder.setIsRecyclable(false)

        holder.run {
            text.text = user.userdetails["first_name"].toString()
        }




        if (user.userdetails["profile_pic"].toString() != "") {
            Picasso.get()
                .load(user.userdetails["profile_pic"].toString())
                .resize(150, 150)
                .transform(CircleTransform())
                .placeholder(R.mipmap.profile_pic_placeholder)
                .centerCrop()
                .into(holder.profile_pic)
        }

        var age = "NA"
        if (user.userdetails["age"].toString().isNotEmpty()) {
            age = user.userdetails["age"].toString().replace(".0", "")
        }

        var profession = "NA"
        var present_residence = "NA"
        var marital_status = "NA"
        var gender = "NA"
        var religion = "NA"
        var caste = "NA"

        if (user.userdetails["present_residence"] != null) {
            present_residence = SimpleFunctions.checkifemptyString(user.userdetails["present_residence"].toString())!!
        }
        if (user.userdetails["profession"] != null) {
            profession =
                SimpleFunctions.checkifemptyString(user.userdetails["profession"].toString())!!
        }
        if (user.userdetails["maritial_status"] != null){
            marital_status = user.userdetails["maritial_status"].toString()
        }
        if (user.userdetails["gender_choice"] != null){
            gender = user.userdetails["gender_choice"].toString()
        }
        if (user.userdetails["religion_name"] != null){
            religion = user.userdetails["religion_name"].toString().removeSurrounding("\"")
        }
        if (user.userdetails["caste_name"] != null){
            caste = user.userdetails["caste_name"].toString().removeSurrounding("\"")
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
            details.setText(Html.fromHtml(text), TextView.BufferType.SPANNABLE)
        }
        holder.received_decline.setOnClickListener{

            trackerAccept = 1
            viewModelDecline.declinecompletematchvm(2,user.userdetails["user_key"] as Int)
            setupObserversReceivedAccept(position,2)
        }

        holder.received_accept.setOnClickListener{

            trackerAccept = 1
            viewModelDecline.declinecompletematchvm(1,user.userdetails["user_key"] as Int)
            setupObserversReceivedAccept(position,1)
        }
    }



    private fun setupObserversReceivedAccept(position: Int, i: Int) {
        activity?.let {
            viewModelDecline.DeclineCompleteMatchResponseLiveData.observe(it, Observer {
                if (it!= null){
                    if (it.message== "Success"){

                        if (trackerAccept == 1) {
                            if(i==1){
                                PrefsHelper().savePref("final_match",true)
                            }
                            userDetails.removeAt(position)
                            notifyDataSetChanged()
                            trackerAccept = 0
                        }
                    }
                    else{
                        trackerAccept = 0
                        Toast.makeText(activity, it.message, Toast.LENGTH_SHORT).show()
                    }
                }
            })
        }
        activity?.let {
            viewModelDecline.DeclineCompleteMatchAPICallStatus.observe(it, Observer {
                PartnerListMenu.processStatus4(it)
            })
        }
    }



}