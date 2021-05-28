package com.neuro.neuroharmony.ui.login.CompleteMatch

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
import androidx.core.content.ContextCompat.startActivity
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.neuro.neuroharmony.FullProfilePic
import com.neuro.neuroharmony.R
import com.neuro.neuroharmony.ui.login.CircleTransform
import com.neuro.neuroharmony.ui.login.MatchedUserNeuroDesireGraphResultScreen
import com.neuro.neuroharmony.ui.login.ViewPartnerProfile.ViewPartnerProfile
import com.neuro.neuroharmony.ui.login.manualMatching.PartnerListMenu
import com.neuro.neuroharmony.ui.login.manualMatching.PartnerListRecycleConfirmed
import com.neuro.neuroharmony.utils.PrefsHelper
import com.neuro.neuroharmony.utils.SimpleFunctions
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_matched_user_neuro_desire_graph_result_screen.*
import kotlinx.android.synthetic.main.activity_view_partner_profile.*

class ConfirmedFinalizedMatches(
    val activity: FragmentActivity?,
    val userDetails: ArrayList<FinalizeMatchMenu.UserDetails_Finalize>

    ) : RecyclerView.Adapter<ConfirmedFinalizedMatches.ViewHolder>(){


    var tracker = 0
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)  {
        val text = itemView.findViewById<TextView>(R.id.user_name_finalize_confirmed)
        val profile_pic = itemView.findViewById<ImageView>(R.id.pic_confirmed_finalize_match)
        val details = itemView.findViewById<TextView>(R.id.details_finalize_confirmed)
        val match_report_final = itemView.findViewById<TextView>(R.id.match_report_final)
        val partner_profile = itemView.findViewById<Button>(R.id.view_partner_profile_complete_match)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.finalize_confirmed_recycle_layout, parent, false )
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {

        return userDetails.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user: FinalizeMatchMenu.UserDetails_Finalize = userDetails[position]


        holder.setIsRecyclable(false)
        holder.run {

            if (user.userdetails["middle_name"].toString() != ""){
                text.text = user.userdetails["first_name"].toString()+" " +
                        user.userdetails["middle_name"].toString()+" " + user.userdetails["last_name"].toString()
            }
            else{
                text.text = user.userdetails["first_name"].toString()+" " + user.userdetails["last_name"].toString()
            }

        }


        holder.match_report_final.setOnClickListener {
            val mySPrefs = PreferenceManager.getDefaultSharedPreferences(activity)
            val editor = mySPrefs.edit()
            editor.remove("group_match")
            editor.apply()

            PrefsHelper().savePref("matched_user", user.userdetails["user_key"].toString())
            //PrefsHelper().savePref("user_group", user.details[7])
            PrefsHelper().savePref("matched_user_name", user.userdetails["first_name"])
            PrefsHelper().savePref("matched_user_pic", user.userdetails["profile_pic"])

            val intent = Intent(
                activity,
                MatchedUserNeuroDesireGraphResultScreen::class.java
            )
            activity?.let { it1 -> ContextCompat.startActivity(it1, intent, null) }
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

        holder.profile_pic.setOnClickListener{
            if (user.userdetails["profile_pic"].toString()  != "") {
                val intent = Intent(activity, FullProfilePic::class.java)
                intent.putExtra(
                    "profile_pic_url",
                    user.userdetails["profile_pic"].toString()
                )
                activity?.let { startActivity(it, intent, null) }
            }
            else{
                Toast.makeText(activity
                    , "You haven't uploaded the profile picture", Toast.LENGTH_LONG).show()
            }
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


        holder.partner_profile.setOnClickListener {
            val intent = Intent(activity, ViewPartnerProfile::class.java)
            intent.putExtra("profile_pic", user.userdetails["profile_pic"].toString())
            intent.putExtra("user_name", user.userdetails["first_name"].toString())
            intent.putExtra("user_age", age)
            activity?.let { it1 -> startActivity(it1, intent, null) }
        }

        PrefsHelper().savePref("matched_user_age", age)
        PrefsHelper().savePref("matched_user_marital_status", marital_status)
        PrefsHelper().savePref("matched_user_gender", gender)


        val text: String =
            java.lang.String.format(
                "%s | %s | %s <br /><br /> %s | %s <br /><br /> %s | %s",
                age,
                marital_status,
                gender,
                religion,
                caste,
                present_residence,
                profession
            )
        holder.run {
            details.setText(Html.fromHtml(text), TextView.BufferType.SPANNABLE)
        }
    }
}