package com.neuro.neuroharmony.ui.login.manualMatching

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
import com.neuro.neuroharmony.FullProfilePic
import com.neuro.neuroharmony.R
import com.neuro.neuroharmony.ui.login.*
import com.neuro.neuroharmony.utils.CommonUtils
import com.neuro.neuroharmony.utils.PrefsHelper
import com.neuro.neuroharmony.utils.SimpleFunctions
import com.squareup.picasso.Picasso

class PartnerListRecycleConfirmed(
    val activity: FragmentActivity?,
    val userDetails: ArrayList<PartnerListMenu.UserDetails>,
    val viewModelAcceptDecline: AcceptDeclineDelinkViewModel
) : RecyclerView.Adapter<PartnerListRecycleConfirmed.ViewHolder>(){

    var tracker = 0
    var dialog1: Dialog? = null
    var positionPresent: Int? = null

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)  {
        val text = itemView.findViewById<TextView>(R.id.name_of_user_confirmed)
        val profile_pic = itemView.findViewById<ImageView>(R.id.image_of_confirm)
        val details = itemView.findViewById<TextView>(R.id.details_confirmed_users)
        val delink = itemView.findViewById<Button>(R.id.view_manual_match_confirmed_delink_user)
        val view_match = itemView.findViewById<Button>(R.id.view_manual_match_confirmed_user)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.manual_matching_confirmed_list, parent, false )
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {

        return userDetails.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user: PartnerListMenu.UserDetails = userDetails[position]


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
        if(user.userdetails["age"].toString().isNotEmpty()) {
            age = user.userdetails["age"].toString().replace(".0","")
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

        holder.profile_pic.setOnClickListener{
            if (user.userdetails["profile_pic"].toString() != "") {
                val intent = Intent(activity, FullProfilePic::class.java)
                intent.putExtra(
                    "profile_pic_url",
                    user.userdetails["profile_pic"].toString()
                )
                activity?.let { it1 -> ContextCompat.startActivity(it1, intent, null) }
            }
            else{
                Toast.makeText(activity
                    , "This user hasn't uploaded the profile picture", Toast.LENGTH_LONG).show()
            }
        }

        holder.delink.setOnClickListener{
            PrefsHelper().savePref("action_type_manual_match", "4")
            positionPresent = position
            tracker = 1
            viewModelAcceptDecline.acceptdelinmanualmatchvm(user.userdetails["user_key"] as Int)
            setupObserversDelink(position)
        }

        holder.view_match.setOnClickListener{
            val mySPrefs = PreferenceManager.getDefaultSharedPreferences(activity)
            val editor = mySPrefs.edit()
            editor.remove("group_match")
            editor.apply()
            PrefsHelper().savePref("matched_user", user.userdetails["user_key"].toString())
            PrefsHelper().savePref("matched_user_age", age)
            PrefsHelper().savePref("matched_user_marital_status", marital_status)
            PrefsHelper().savePref("matched_user_gender", gender)
            PrefsHelper().savePref("matched_user_name", user.userdetails["first_name"])
            PrefsHelper().savePref("matched_user_pic", user.userdetails["profile_pic"])
            val intent = Intent(
                activity,
                MatchedUserNeuroDesireGraphResultScreen::class.java
            )
            activity?.let { it1 -> ContextCompat.startActivity(it1, intent, null) }
        }

    }

    private fun setupObserversDelink(position: Int) {
        activity?.let {
            viewModelAcceptDecline.acceptdeclinemanualmathcingResponseLiveData.observe(
                it,
                Observer {
                    if (it != null) {
                        if (it.message == "Success") {

                            if (tracker == 1) {
                                positionPresent?.let { it1 -> userDetails.removeAt(it1) }
                                notifyDataSetChanged()
                                tracker = 0
                            }
                        } else {
                            Toast.makeText(activity, it.message, Toast.LENGTH_SHORT).show()
                        }
                    }
                })
        }
        activity?.let {
            viewModelAcceptDecline.acceptdeclinemanualmathcingAPICallStatus.observe(it, Observer {
                processStatus1(it)
            })
        }
    }


    private fun processStatus1(
        resource: ResourceStatus
    ){
        when (resource.status) {
            StatusType.SUCCESS -> {
                dialog1?.let {
                    it.dismiss()
                }
            }
            StatusType.EMPTY_RESPONSE -> {
                dialog1?.let {
                    it.dismiss()
                }

            }
            StatusType.PROGRESSING -> {
                if (dialog1 != null && dialog1!!.isShowing()) {
                    dialog1!!.dismiss();
                }
                activity?.runOnUiThread {
                    dialog1 = CommonUtils().showDialog(activity!!)
                }
            }
            StatusType.SWIPE_RELOADING -> {


            }
            StatusType.ERROR -> {
                Toast.makeText(activity, "Please try again. Server error", Toast.LENGTH_SHORT).show()
                dialog1?.let {
                    it.dismiss()
                }


            }
            StatusType.LOADING_MORE -> {
                // CommonUtils().showSnackbar(binding.root, "Loading more..")
            }
            StatusType.NO_NETWORK -> {
                Toast.makeText(activity, "Please check internet connection", Toast.LENGTH_SHORT).show()
                dialog1?.dismiss()

            }
            StatusType.SESSION_EXPIRED -> {
                //CommonUtils().showSnackbar(revoke_interest_button.rootView,"session expired")
            }
        }
    }

}