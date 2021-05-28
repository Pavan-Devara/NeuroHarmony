package com.neuro.neuroharmony.ui.login.ViewPartnerProfile


import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.text.Editable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.gson.Gson

import com.neuro.neuroharmony.R
import com.neuro.neuroharmony.data.model.Social_profile.PersonalInfo.PersonalInfoResponse
import com.neuro.neuroharmony.ui.login.NeuroMatchedUsers
import com.neuro.neuroharmony.ui.login.ResourceStatus
import com.neuro.neuroharmony.ui.login.SocialProfile.PersonalInfoViewModel
import com.neuro.neuroharmony.ui.login.StatusType
import com.neuro.neuroharmony.utils.CommonUtils
import com.neuro.neuroharmony.utils.PrefsHelper
import kotlinx.android.synthetic.main.activity_bioinfo.*
import kotlinx.android.synthetic.main.fragment_view_partner_profile_personal.*

/**
 * A simple [Fragment] subclass.
 */
class ViewPartnerProfilePersonalFragment : Fragment() {

    private lateinit var viewModel2: PersonalInfoViewModel

    var dialog2: Dialog? = null
    var gson = Gson()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_view_partner_profile_personal, container, false)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val matched_user = PrefsHelper().getPref<String>("matched_user")
        viewModel2 = ViewModelProviders.of(this)[PersonalInfoViewModel::class.java]
        setupobserverspersonalinfo()
        viewModel2.personalInfoLiveData(matched_user)
    }

    private fun setupobserverspersonalinfo(){
        viewModel2.personalInfoResponseLiveData.observe(this, Observer {
            if (it != null) {
                if (it.message == "Success") {
                    main_code(it)

                } else {
                    Toast.makeText(activity, it.message, Toast.LENGTH_LONG).show()
                }
            }
        })
        viewModel2.personalInfoApiCallStatus.observe(this, Observer {
            processStatus2(it)
        })
    }


    private fun main_code(it: PersonalInfoResponse) {

        age_partner_profile.text = PrefsHelper().getPref<String>("matched_user_age")

        if(it.data.userChoice.alternativeMobileNumber != ""){
            alt_mobile_num_partner_profile.text= it.data.userChoice.alternativeMobileNumber.toString()
        }else{
            alt_mobile_num_partner_profile.text= "Not Available"
        }

        if(it.data.userChoice.gender != 0){
            val gender = it.data.userChoice.gender
            gender_partner_profile.text = NeuroMatchedUsers.Gender.values().find {it.status == gender}.toString()
        }

        if (it.data.userChoice.status != 0){
            val status = it.data.userChoice.status
            marital_status_partner_profile.text = NeuroMatchedUsers.Status.values().find {it.status == status}.toString()
        }

        if (it.data.userChoice.mobileNumber != ""){
            primary_mobile_partner_profile.text = it.data.userChoice.mobileNumber
        }else{
            primary_mobile_partner_profile.text = "Not Available"
        }

        if(it.data.userChoice.fathersName != ""){
            father_name_partner_profile.text= it.data.userChoice.fathersName
        }else{
            father_name_partner_profile.text= "Not Available"
        }

        if (it.data.userChoice.height != null){
            height_partner_profile.text = it.data.userChoice.height.toString().replace(".0", "")
        }
        if (it.data.userChoice.weight !=null) {
            weight_partner_profile.text =it.data.userChoice.weight.toString().replace(".0","")
        }


        if(it.data.userChoice.native_country!= null){
            if ( it.data.userChoice.native_country.country!="") {
                hometown_partner_profile.text =
                    it.data.userChoice.native_city.city.toString() + "," +
                            it.data.userChoice.native_state.state.toString() + "," + it.data.userChoice.native_country.country.toString()
            }else{
                hometown_partner_profile.text = "Not Available"
            }
        }

        if(it.data.userChoice.present_country!= null ){
            if (it.data.userChoice.present_country.country!="") {
                current_residence_partner_profile.text =
                    it.data.userChoice.present_city.city.toString() + "," +
                            it.data.userChoice.present_state.state.toString() + "," + it.data.userChoice.present_country.country.toString()
            }else{
                current_residence_partner_profile.text = "Not Available"
            }
        }

        if(it.data.userChoice.motherTongue != ""){
            mother_tongue_partner_profile.text= it.data.userChoice.motherTongue
        }else{
            mother_tongue_partner_profile.text = "Not Available"
        }

        if(it.data.userChoice.dob != null){
            var dob = it.data.userChoice.dob
            date_of_birth_partner_profile.text= dob
        }

        if (it.data.userChoice.email != ""){
            email_id_partner_profile.text = it.data.userChoice.email
        }else{
            email_id_partner_profile.text = "Not Available"
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
                        DialogInterface.OnClickListener { dialog, id ->
                            dialog2!!.dismiss()
                            dialog.cancel()
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
                dialog2?.dismiss()
                Toast.makeText(activity, "Please check internet connection", Toast.LENGTH_SHORT).show()
            }
            StatusType.SESSION_EXPIRED -> {
                //CommonUtils().showSnackbar(revoke_interest_button.rootView,"session expired")
            }
        }
    }

    override fun onPause() {
        super.onPause()
        if (dialog2 != null && dialog2!!.isShowing()) {
            dialog2!!.dismiss();
        }
    }
}
