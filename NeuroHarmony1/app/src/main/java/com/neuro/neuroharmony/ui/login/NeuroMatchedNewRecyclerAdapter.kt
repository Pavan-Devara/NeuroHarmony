package com.neuro.neuroharmony.ui.login

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.neuro.neuroharmony.FullProfilePic
import com.neuro.neuroharmony.R
import com.neuro.neuroharmony.data.model.NewMatchedUsersData.ItemMatchedUsers
import com.neuro.neuroharmony.data.model.RevokeInterestResponse
import com.neuro.neuroharmony.utils.CommonUtils
import com.neuro.neuroharmony.utils.PrefsHelper
import com.neuro.neuroharmony.utils.SimpleFunctions
import com.squareup.picasso.Picasso


class NeuroMatchedNewRecyclerAdapter internal constructor(
    mCtx: FragmentActivity,
    viewModelExpressInterest: ExpressInterestViewModel,
    viewModelRevokeInterest: RevokeInterestRequestViewModel,
    val viewLifecycleOwner: LifecycleOwner
) :
    PagedListAdapter<ItemMatchedUsers, NeuroMatchedNewRecyclerAdapter.ViewHolder?>(DIFF_CALLBACK) {

    var dialog2: Dialog? = null
    var dialog1: Dialog? = null

    var holderPresent: ViewHolder? = null

    companion object {
        private val DIFF_CALLBACK: DiffUtil.ItemCallback<ItemMatchedUsers> =
            object : DiffUtil.ItemCallback<ItemMatchedUsers>() {
                override fun areItemsTheSame(oldItem: ItemMatchedUsers, newItem: ItemMatchedUsers): Boolean {
                    return oldItem.user_key === newItem.user_key
                }

                override fun areContentsTheSame(
                    oldItem: ItemMatchedUsers,
                    newItem: ItemMatchedUsers
                ): Boolean {
                    return oldItem.equals(newItem)
                }
            }
    }

    val viewModel = viewModelExpressInterest
    val viewModelRevokeInterest = viewModelRevokeInterest
    val activity = mCtx
    val EXPRESS_INTEREST = 0
    val REVOKE_INTEREST = 1
    val ACCEPT = 2
    val RECEIVED = 4
    var counterRevoke = 0
    var counterExpress = 0
    var counterReports = 0
    var gson = Gson()
    var user_now = ""
    private val mCtx: FragmentActivity
    val cost_for_express_interest = PrefsHelper().getPref<Int>("express_request_debit_action")

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        val user_name = itemView.findViewById<TextView>(com.neuro.neuroharmony.R.id.matched_user_name)
        val user_details = itemView.findViewById<TextView>(com.neuro.neuroharmony.R.id.matched_user_details)
        val button1 = itemView.findViewById<Button>(com.neuro.neuroharmony.R.id.express_interest_button)
        val button2 = itemView.findViewById<Button>(com.neuro.neuroharmony.R.id.revoke_interest_button)
        val interested = itemView.findViewById<TextView>(com.neuro.neuroharmony.R.id.requestAcceptedText)
        val relativeLayout = itemView.findViewById<RelativeLayout>(com.neuro.neuroharmony.R.id.user_info_relative_layout)
        val single_direction = itemView.findViewById<ImageView>(com.neuro.neuroharmony.R.id.single_directional)
        val bidirection = itemView.findViewById<ImageView>(com.neuro.neuroharmony.R.id.bidirectional)
        val profile_pic = itemView.findViewById<ImageView>(com.neuro.neuroharmony.R.id.matched_user_photo)
        val new_user = itemView.findViewById<ImageView>(com.neuro.neuroharmony.R.id.new_user_tag)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val v = LayoutInflater.from(parent.context).inflate(com.neuro.neuroharmony.R.layout.matched_users_recycler_view, parent, false )

        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return super.getItemCount()
    }


    init {
        this.mCtx = mCtx
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = getItem(position)

        holder.setIsRecyclable(false)

        if (user!!.is_new_user==1){
            holder.new_user.visibility = View.VISIBLE
        }


        if(user!!.profile_pic!="") {
            Picasso.get()
                .load(user.profile_pic)
                .resize(150, 150)
                .transform(CircleTransform())
                .placeholder(R.mipmap.profile_pic_placeholder)
                .centerCrop()
                .into(holder.profile_pic)
        }

        holder.user_name.text = user!!.first_name

        if (user.direction==0){
            holder.single_direction.visibility = View.VISIBLE
        }else{
            holder.bidirection.visibility = View.VISIBLE
        }

        var age = "NA"
        if(user.age.toString().isNotEmpty()) {
            age = user.age.toString().replace(".0","")
        }
        var profession = "NA"
        var present_city = "NA"
        var present_state = "NA"
        var marital_status = "NA"
        var gender = "NA"
        var religion = "NA"
        var caste = "NA"
        var present_residence = "NA"
        if (user.profession != null) {
            profession = SimpleFunctions.checkifemptyString(user.profession.toString())!!
        }
        if (user.present_city != null) {
            present_residence =
                SimpleFunctions.checkifemptyString(user.present_city.toString())!!
        }
        if (user.marital_status != null){
            marital_status = user.marital_status
        }
        if (user.religion_name != null){
            religion = user.religion_name?.toString().removeSurrounding("\"")
        }
        if (user.caste_name != null){
            caste = user.caste_name?.toString().removeSurrounding("\"")
        }
        if (user.gender != null){
            gender = user.gender
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

        holder.run {
            if (user.interest_status == EXPRESS_INTEREST){
                holder.button1.visibility = View.VISIBLE
                holder.button2.visibility = View.INVISIBLE
            }else if (user.interest_status==REVOKE_INTEREST){
                holder.button1.visibility = View.INVISIBLE
                holder.button2.visibility = View.VISIBLE
            }else if (user.interest_status==ACCEPT) {
                holder.button1.visibility = View.INVISIBLE
                holder.button2.visibility = View.INVISIBLE
                holder.interested.visibility = View.VISIBLE
            } else if (user.interest_status==RECEIVED){
                holder.button1.visibility = View.INVISIBLE
                holder.button2.visibility = View.INVISIBLE
                holder.interested.text = "Interest Received"
                holder.interested.visibility = View.VISIBLE
            }else{
                holder.button1.visibility = View.INVISIBLE
                holder.button2.visibility = View.INVISIBLE
                holder.interested.visibility = View.VISIBLE
            }
        }
        holder.profile_pic.setOnClickListener{
            if (user!!.profile_pic!="") {
                val intent = Intent(activity, FullProfilePic::class.java)
                intent.putExtra(
                    "profile_pic_url",
                    user.profile_pic
                )
                activity?.let { it1 -> ContextCompat.startActivity(it1, intent, null) }
            }
            else{
                Toast.makeText(activity
                    , "This user hasn't uploaded the profile picture", Toast.LENGTH_LONG).show()
            }
        }
        holder.button1.setOnClickListener {

            val dialogBuilder = AlertDialog.Builder(mCtx)

            // set message of alert dialog
//                    dialogBuilder.setMessage("Feedback Submitted \n       Successfully")

            dialogBuilder.setMessage("This action will deduct "+cost_for_express_interest.toString()+" tokens from your account")
                // if the dialog is cancelable
                .setCancelable(false)
                // positive button text and action
                .setPositiveButton("Proceed", DialogInterface.OnClickListener {
                        dialog, id -> dialog.cancel()

            counterExpress = 1
            holderPresent = holder


            viewModel.expressInterestvm(user.user_key)
            setupObserversExpressInterest(holder, user)
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

        holder.button2.setOnClickListener{
            counterRevoke = 1
            holderPresent = holder
            viewModelRevokeInterest.revokeInterestrequestvm(user.user_key.toInt(), 3)
            setupObserversRevokeInterest(holder, user)
        }

        holder.relativeLayout.setOnClickListener{

            user_now = user.user_key
            PrefsHelper().savePref("matched_user", user.user_key)

            //PrefsHelper().savePref("user_group", user.details[7])
            PrefsHelper().savePref("matched_user_name", user.first_name)
            PrefsHelper().savePref("matched_user_pic", user.profile_pic)
            PrefsHelper().savePref("matched_user_age", age)
            PrefsHelper().savePref("matched_user_marital_status", marital_status)
            PrefsHelper().savePref("matched_user_gender", gender)
            counterReports = 1
            val intent = Intent(
                activity,
                MatchedUserNeuroDesireGraphResultScreen::class.java
            )
            mCtx?.let { it1 -> ContextCompat.startActivity(it1, intent, null) }
        }


    }

    private fun setupObserversRevokeInterest(
        holder: ViewHolder,
        user: ItemMatchedUsers?
    ) {

        viewLifecycleOwner?.let { it1 ->
            viewModelRevokeInterest.revokeInterestrequesResponseLiveData.observe(it1, Observer {
                if (it != null) {
                    if (it.message == "Success") {
                        if (counterRevoke == 1) {
                            holderPresent?.button2!!.visibility = View.INVISIBLE
                            holderPresent?.button1!!.visibility = View.VISIBLE
                            Toast.makeText(activity, "Interest Revoked", Toast.LENGTH_SHORT)
                                .show()
                            counterRevoke = 0
                            user!!.interest_status = EXPRESS_INTEREST
                        }
                    } else {
                        Toast.makeText(activity, it.message, Toast.LENGTH_SHORT).show()
                    }
                }
            })
        }
        //observe API call status
        viewLifecycleOwner?.let { it1 ->
            viewModelRevokeInterest.revokeInterestrequesAPICallStatus.observe(it1, Observer {
                processStatus2(it)
            })
        }

    }



    private fun setupObserversExpressInterest(
        holder: ViewHolder,
        user: ItemMatchedUsers?
    ) {
        viewLifecycleOwner?.let {
            viewModel.loginResponseLiveData.observe(it, Observer {
                if (it != null) {
                    if (it.message == "Success") {
                        if (counterExpress==1) {
                            holderPresent?.button2!!.visibility = View.VISIBLE
                            holderPresent?.button1!!.visibility = View.INVISIBLE
                            Toast.makeText(
                                activity,
                                "Interest request Sent",
                                Toast.LENGTH_SHORT
                            ).show()
                            counterExpress = 0
                            user!!.interest_status = REVOKE_INTEREST
                            viewModel.loginResponseLiveData.removeObservers(viewLifecycleOwner)
                        }
                    }else{
                        Toast.makeText(activity, it.message, Toast.LENGTH_SHORT).show()
                    }
                }
            })
        }
        //observe API call status
        viewLifecycleOwner?.let { it1 ->
            viewModel.loginAPICallStatus.observe(it1, Observer {
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



    private fun processStatus2(
        resource: ResourceStatus
    ){
        when (resource.status) {
            StatusType.SUCCESS -> {
                dialog2?.let {
                    it.dismiss()
                }
            }
            StatusType.EMPTY_RESPONSE -> {
                dialog2?.let {
                    it.dismiss()
                }

            }
            StatusType.PROGRESSING -> {
                if (dialog2 != null && dialog2!!.isShowing()) {
                    dialog2!!.dismiss();
                }
                activity?.runOnUiThread {
                    dialog2 = CommonUtils().showDialog(activity!!)
                }
            }
            StatusType.SWIPE_RELOADING -> {


            }
            StatusType.ERROR -> {
                Toast.makeText(activity, "Please try again. Server error", Toast.LENGTH_SHORT).show()
                dialog2?.let {
                    it.dismiss()
                }


            }
            StatusType.LOADING_MORE -> {
                // CommonUtils().showSnackbar(binding.root, "Loading more..")
            }
            StatusType.NO_NETWORK -> {

                Toast.makeText(activity, "Please check internet connection", Toast.LENGTH_SHORT).show()
                dialog2?.dismiss()
            }
            StatusType.SESSION_EXPIRED -> {
                //CommonUtils().showSnackbar(revoke_interest_button.rootView,"session expired")
            }
        }
    }
}

