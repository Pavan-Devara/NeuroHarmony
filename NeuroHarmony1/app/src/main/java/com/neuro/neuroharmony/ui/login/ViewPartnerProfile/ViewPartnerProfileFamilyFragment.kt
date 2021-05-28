package com.neuro.neuroharmony.ui.login.ViewPartnerProfile


import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.gson.Gson

import com.neuro.neuroharmony.R
import com.neuro.neuroharmony.data.model.Social_profile.Familyinfo.FamilyInfoResponse
import com.neuro.neuroharmony.data.model.Social_profile.Familyinfo.Familystatus
import com.neuro.neuroharmony.ui.login.ResourceStatus
import com.neuro.neuroharmony.ui.login.SocialProfile.FamilyInfoViewModel
import com.neuro.neuroharmony.ui.login.StatusType
import com.neuro.neuroharmony.utils.CommonUtils
import com.neuro.neuroharmony.utils.PrefsHelper
import kotlinx.android.synthetic.main.activity_family_info.*
import kotlinx.android.synthetic.main.fragment_view_partner_profile_family.*

/**
 * A simple [Fragment] subclass.
 */
class ViewPartnerProfileFamilyFragment : Fragment() {

    private lateinit var viewModel: FamilyInfoViewModel
    var dialog2: Dialog? = null
    var gson = Gson()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_view_partner_profile_family, container, false)

        return view
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val matched_user = PrefsHelper().getPref<String>("matched_user")
        viewModel = ViewModelProviders.of(this)[FamilyInfoViewModel::class.java]
        setupObserversFamilyinfo()
        viewModel.familyInfoLiveData(matched_user)
    }



    private fun setupObserversFamilyinfo(){
        viewModel.familyInfoResponseLiveData.observe(this, Observer {
            if(it!=null){
                if(it.message=="Success"){
                    main_code(it)
                }
                else{
                    Toast.makeText(activity, it.message, Toast.LENGTH_LONG).show()
                }
            }
        })
        viewModel.familyInfoApiCallStatus.observe(this, Observer {
            processStatus2(it)
        })
    }


    private fun main_code(it: FamilyInfoResponse) {
        new_function(it.data.choice.fatherStatus,father_status_partner_profile,it.data.userChoice.fatherStatus)
        new_function(it.data.choice.fatherCompany,father_company_partner_profile,it.data.userChoice.fatherCompany)
        new_function(it.data.choice.fatherDesignation,father_designation_partner_profile,it.data.userChoice.fatherDesignation)
        new_function(it.data.choice.motherStatus,mother_status_partner_profile,it.data.userChoice.motherStatus)
        new_function(it.data.choice.motherCompany,mother_company_partner_profile,it.data.userChoice.motherCompany)
        new_function(it.data.choice.motherDesignation,mother_designation_partner_profile,it.data.userChoice.motherDesignation)
        new_function(it.data.choice.siblings,number_of_siblings_partner_profile,it.data.userChoice.siblings)
        new_function(it.data.choice.familyType,family_type_partner_profile,it.data.userChoice.familyType)
        new_function(it.data.choice.familyValue,family_value_partner_profile,it.data.userChoice.familyValue)
        new_function(it.data.choice.familyIncome,family_income_partner_profile,it.data.userChoice.familyIncome)
    }


    private fun new_function(
        familystatus: List<Familystatus>,
        textView: TextView,
        selected_option: Any
    ) {
        if (selected_option == null) {
            textView.text = "Not Available"
        }
        else{
            val choice = id_to_recieved(familystatus,selected_option)
            textView.text = choice
        }
    }


    private fun id_to_recieved(
        full_list1: List<Familystatus>,
        selectedOption: Any
    ): String {
        val  list_choice:MutableList<String> = arrayListOf()
        val list_id:MutableList<String> = arrayListOf()
        for (i in 0 until full_list1.size) {
            val obj = full_list1[i]
            list_choice.add(obj.choice)
            list_id.add(obj.id)
        }
        var position = list_id.indexOf(selectedOption.toString())
        var selected_option = list_choice.get(position)
        return selected_option
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
