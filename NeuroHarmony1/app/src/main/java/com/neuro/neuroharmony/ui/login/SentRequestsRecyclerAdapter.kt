package com.neuro.neuroharmony.ui.login

import android.content.Intent
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
import com.neuro.neuroharmony.utils.SimpleFunctions
import com.squareup.picasso.Picasso

class SentRequestsRecyclerAdapter(
    val activity: FragmentActivity?,
    val userDetails: ArrayList<Chat_Requests.Userdetails>,
    val viewModelRevokeInterest: RevokeInterestRequestViewModel
) : RecyclerView.Adapter<SentRequestsRecyclerAdapter.ViewHolder>() {


    var tracker = 0

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val button = itemView.findViewById<Button>(R.id.revoke_interest_button)
        val user_name = itemView.findViewById<TextView>(R.id.matched_user_name)
        val user_details = itemView.findViewById<TextView>(R.id.matched_user_details)
        val profile_pic = itemView.findViewById<ImageView>(R.id.matched_user_photo)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.matched_users_recycler_view, parent, false )

        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return userDetails.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user:Chat_Requests.Userdetails = userDetails[position]
        holder.setIsRecyclable(false)

        holder.run {
            user_name.text = user.specs[0].toString()
        }

       if (user.specs[8].toString()!= ""){
        Picasso.get()
            .load(user.specs[8].toString())
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

        if (user.specs[9] != null) {
            profession = SimpleFunctions.checkifemptyString(user.specs[9].toString())!!
        }
        if (user.specs[2] != null) {
            present_residence =
                SimpleFunctions.checkifemptyString(user.specs[2].toString())!!
        }
        if (user.specs[10] != null){
            marital_status = user.specs[10]?.toString()
        }
        if (user.specs[11] != null){
            gender = user.specs[11]?.toString()
        }
        if (user.specs[3] != null){
            religion = user.specs[3]?.toString()?.removeSurrounding("\"")
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

        holder.run { button.visibility = View.VISIBLE }

        holder.profile_pic.setOnClickListener{
            if (user.specs[8].toString() != "") {
                val intent = Intent(activity, FullProfilePic::class.java)
                intent.putExtra(
                    "profile_pic_url",
                    user.specs[8].toString()
                )
                activity?.let { it1 -> ContextCompat.startActivity(it1, intent, null) }
            }
            else{
                Toast.makeText(activity
                    , "This user hasn't uploaded the profile picture", Toast.LENGTH_LONG).show()
            }
        }
        holder.button.setOnClickListener{
            tracker = 1
            viewModelRevokeInterest.revokeInterestrequestvm(user.specs[6] as Int, 3)
            setupObserversRevokeInterest(position)
        }
    }

    private fun setupObserversRevokeInterest(
        position: Int
    ) {
        activity?.let { it1 ->
            viewModelRevokeInterest.revokeInterestrequesResponseLiveData.observe(it1, Observer {
                if (it!=null){
                    if (it.message=="Success"){
                        if (tracker == 1){
                            userDetails.removeAt(position)
                            notifyDataSetChanged()
                            tracker = 0
                        }
                    }else{
                        Toast.makeText(activity, it.message, Toast.LENGTH_SHORT).show()
                    }
                }
            })
        }
        //observe API call status
        activity?.let { it1 ->
            viewModelRevokeInterest.revokeInterestrequesAPICallStatus.observe(it1, Observer {
                NeuroMatchedUsers.processStatus(it)
            })
        }
    }
}
