package com.neuro.neuroharmony.ui.login.manualMatching


import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.neuro.neuroharmony.R
import com.neuro.neuroharmony.ui.login.ResourceStatus
import com.neuro.neuroharmony.ui.login.StatusType
import com.neuro.neuroharmony.utils.CommonUtils
import kotlinx.android.synthetic.main.confirmed_requests.*
import kotlinx.android.synthetic.main.matched_users_recycler_view.*
import kotlinx.android.synthetic.main.received_requests_users.*

/**
 * A simple [Fragment] subclass.
 */
class PartnerMenuFragment(position: Int) : Fragment() {
    val position = position
    var dialog: Dialog? = null
    var dialog2: Dialog? = null
    var dialog1: Dialog? = null

    private lateinit var viewModelConfirmedUsers: GetConfirmedUsersViewModel
    private lateinit var viewModelSentUsers: GetSentUsersViewModel
    private lateinit var viewModelReceivedUsers: GetReceivedUsersViewModel
    private lateinit var viewModelAcceptDecline: AcceptDeclineDelinkViewModel

    @SuppressLint("WrongConstant")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_partner_menu, container, false)

        viewModelConfirmedUsers = ViewModelProviders.of(this)[GetConfirmedUsersViewModel::class.java]
        viewModelReceivedUsers = ViewModelProviders.of(this)[GetReceivedUsersViewModel::class.java]
        viewModelSentUsers = ViewModelProviders.of(this)[GetSentUsersViewModel::class.java]
        viewModelAcceptDecline =  ViewModelProviders.of(this)[AcceptDeclineDelinkViewModel::class.java]

        val recyclerView = view.findViewById(R.id.fragment_menu_recycler_view) as RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(activity, LinearLayout.VERTICAL, false)
        val nothing_here = view?.findViewById(R.id.nothinghere) as TextView

        val user = ArrayList<PartnerListMenu.UserDetails>()


        if(position==0){
            viewModelConfirmedUsers.getreceivedsentconfirmedmanualmatchingvm()
            viewModelConfirmedUsers.getreceivedsentconfirmedmanualmatchingResponseLiveData.observe(this, Observer {
                if (it != null){
                    if (it.message == "Success"){
                        if (it.data.isEmpty()){
                            recyclerView.setVisibility(View.GONE);
                            nothing_here.setVisibility(View.VISIBLE);
                        }
                        for (i in 0 until it.data.size){

                            val mobileNumber = it.data[i].mobileNumber
                            val userdetails = mutableMapOf<String, Any>("updated" to  false)
                            userdetails.put("user_key" ,it.data[i].userKey)
                            userdetails["updated"] = true
                            userdetails.put("first_name", it.data[i].firstName)
                            userdetails.put("present_residence", it.data[i].presentResidence)
                            userdetails.put("status", it.data[i].status)
                            userdetails.put("gender", it.data[i].gender)
                            userdetails.put("profile_pic", it.data[i].profilePic)
                            userdetails.put("age", it.data[i].age)
                            userdetails.put("religion_name", it.data[i].religionName)
                            userdetails.put("caste_name", it.data[i].casteName)
                            userdetails.put("profession", it.data[i].profession)
                            userdetails.put("maritial_status", it.data[i].maritalStatus)
                            userdetails.put("gender_choice",it.data[i].gender_choice)
                            user.add(PartnerListMenu.UserDetails(mobileNumber, userdetails))
                        }
                        val adapter_recycler = PartnerListRecycleConfirmed(activity, user, viewModelAcceptDecline)
                        recyclerView.adapter = adapter_recycler
                        adapter_recycler.notifyDataSetChanged()
                    }else{
                        Toast.makeText(activity, it.message, Toast.LENGTH_SHORT).show()
                    }
                }
            })
            viewModelConfirmedUsers.getreceivedsentconfirmedmanualmatchingAPICallStatus.observe(this, Observer {
                processStatus2(it)
            })
        }

        if(position==1){
            viewModelSentUsers.getreceivedsentconfirmedmanualmatchingvm()
            viewModelSentUsers.getsentmanualmatchingResponseLiveData.observe(this, Observer {
                if (it != null){
                    if (it.message == "Success"){
                        if (it.data.isEmpty()){
                            recyclerView.setVisibility(View.GONE);
                            nothing_here.setVisibility(View.VISIBLE);
                        }
                        for (i in 0 until it.data.size){
                            val mobileNumber = it.data[i].mobileNumber
                            val userdetails = mutableMapOf<String, Any>("updated" to  false)
                            userdetails.put("user_key" ,it.data[i].userKey)
                            if (it.data[i].is_user_data_updated) {
                                userdetails["updated"] = true
                            }
                            userdetails.put("first_name", it.data[i].firstName)
                            userdetails.put("present_residence", it.data[i].presentResidence)
                            userdetails.put("status", it.data[i].status)
                            userdetails.put("gender", it.data[i].gender)
                            userdetails.put("profile_pic", it.data[i].profilePic)
                            userdetails.put("age", it.data[i].age)
                            userdetails.put("religion_name", it.data[i].religionName)
                            userdetails.put("caste_name", it.data[i].casteName)
                            userdetails.put("profession", it.data[i].profession)
                            userdetails.put("maritial_status", it.data[i].maritalStatus)
                            userdetails.put("gender_choice",it.data[i].gender_choice)
                            user.add(PartnerListMenu.UserDetails(mobileNumber, userdetails))
                        }
                        val adapter_recycler = PartnerListRecycleSent(activity, user, viewModelAcceptDecline)
                        recyclerView.adapter = adapter_recycler
                        adapter_recycler.notifyDataSetChanged()
                    }else{
                        Toast.makeText(activity, it.message, Toast.LENGTH_SHORT).show()
                    }
                }
            })
            viewModelSentUsers.getsentmanualmatchingAPICallStatus.observe(this, Observer {
                processStatus1(it)
            })
        }

        if(position==2){
            viewModelReceivedUsers.getreceivedsentconfirmedmanualmatchingvm()
            viewModelReceivedUsers.getreceivedmanualmatchingResponseLiveData.observe(this, Observer {
                if (it != null){
                    if (it.message == "Success"){
                        if (it.data.isEmpty()){
                            recyclerView.setVisibility(View.GONE);
                            nothing_here.setVisibility(View.VISIBLE);
                        }
                        for (i in 0 until it.data.size){
                            val mobileNumber = it.data[i].mobileNumber
                            val userdetails = mutableMapOf<String, Any>("updated" to  false)
                            userdetails.put("user_key" ,it.data[i].userKey)

                            userdetails["updated"] = true
                            userdetails.put("first_name", it.data[i].firstName)
                            userdetails.put("present_residence", it.data[i].presentResidence)
                            userdetails.put("status", it.data[i].status)
                            userdetails.put("gender", it.data[i].gender)
                            userdetails.put("profile_pic", it.data[i].profilePic)
                            userdetails.put("age", it.data[i].age)
                            userdetails.put("religion_name", it.data[i].religionName)
                            userdetails.put("caste_name", it.data[i].casteName)
                            userdetails.put("profession", it.data[i].profession)
                            userdetails.put("maritial_status", it.data[i].maritalStatus)
                            userdetails.put("gender_choice",it.data[i].gender_choice)
                            user.add(PartnerListMenu.UserDetails(mobileNumber, userdetails))
                        }

                        val adapter_recycler = PartnerListRecycleReiceived(activity, user, viewModelAcceptDecline)
                        recyclerView.adapter = adapter_recycler
                        adapter_recycler.notifyDataSetChanged()
                    }else{
                        Toast.makeText(activity, it.message, Toast.LENGTH_SHORT).show()
                    }
                }
            })
            viewModelReceivedUsers.getreceivedmanualmatchingAPICallStatus.observe(this, Observer {
                processStatus(it)
            })

        }

        return view
    }




    private fun processStatus(
        resource: ResourceStatus
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
                    dialog = CommonUtils().showDialog(activity!!)
                }
            }
            StatusType.SWIPE_RELOADING -> {


            }
            StatusType.ERROR -> {
                val dialogBuilder = AlertDialog.Builder(activity)

                // set message of alert dialog
                dialogBuilder.setMessage("Server Error. Please try again")
                    // if the dialog is cancelable
                    .setCancelable(false)
                    // positive button text and action
                    .setPositiveButton(
                        "Okay",
                        DialogInterface.OnClickListener { dialogue, id ->
                            dialog!!.dismiss()
                            dialogue.cancel()
                        })
                // negative button text and action

                // create dialog box
                val alert = dialogBuilder.create()
                // set title for alert dialog box
                alert.setTitle("Message")
                // show alert dialog
                alert.show()
                dialog?.let {
                    it.dismiss()
                }


            }
            StatusType.LOADING_MORE -> {
                // CommonUtils().showSnackbar(binding.root, "Loading more..")
            }
            StatusType.NO_NETWORK -> {

                Toast.makeText(activity, "Please check internet connection", Toast.LENGTH_SHORT).show()
                dialog?.dismiss()

            }
            StatusType.SESSION_EXPIRED -> {
                //CommonUtils().showSnackbar(revoke_interest_button.rootView,"session expired")
            }
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
                activity?.runOnUiThread {
                    dialog1 = CommonUtils().showDialog(activity!!)
                }
            }
            StatusType.SWIPE_RELOADING -> {


            }
            StatusType.ERROR -> {
                val dialogBuilder = AlertDialog.Builder(activity)

                // set message of alert dialog
                dialogBuilder.setMessage("Server Error. Please try again")
                    // if the dialog is cancelable
                    .setCancelable(false)
                    // positive button text and action
                    .setPositiveButton(
                        "Okay",
                        DialogInterface.OnClickListener { dialog, id ->
                            dialog1!!.dismiss()
                            dialog.cancel()
                        })
                // negative button text and action

                // create dialog box
                val alert = dialogBuilder.create()
                // set title for alert dialog box
                alert.setTitle("Message")
                // show alert dialog
                alert.show()
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
                activity?.runOnUiThread {
                    dialog2 = CommonUtils().showDialog(activity!!)
                }
            }
            StatusType.SWIPE_RELOADING -> {


            }
            StatusType.ERROR -> {
                val dialogBuilder = AlertDialog.Builder(activity)

                // set message of alert dialog
                dialogBuilder.setMessage("Server Error. Please try again")
                    // if the dialog is cancelable
                    .setCancelable(false)
                    // positive button text and action
                    .setPositiveButton(
                        "Okay",
                        DialogInterface.OnClickListener { dialogue, id ->
                            dialog2!!.dismiss()
                            dialogue.cancel()
                        })
                // negative button text and action

                // create dialog box
                val alert = dialogBuilder.create()
                // set title for alert dialog box
                alert.setTitle("Message")
                // show alert dialog
                alert.show()
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

    override fun onPause() {
        super.onPause()
        if (dialog != null && dialog!!.isShowing()) {
            dialog!!.dismiss();
        }
        if (dialog1 != null && dialog1!!.isShowing()) {
            dialog1!!.dismiss();
        }
        if (dialog2 != null && dialog2!!.isShowing()) {
            dialog2!!.dismiss();
        }
    }

}
