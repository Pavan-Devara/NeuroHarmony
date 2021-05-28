package com.neuro.neuroharmony.ui.login.ViewPartnerProfile


import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.text.Editable
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
import com.neuro.neuroharmony.data.model.Social_profile.Educationinfo.EducationProfStatus
import com.neuro.neuroharmony.data.model.Social_profile.Educationinfo.EducationProfessionResponse
import com.neuro.neuroharmony.data.model.Social_profile.Familyinfo.Familystatus
import com.neuro.neuroharmony.ui.login.ResourceStatus
import com.neuro.neuroharmony.ui.login.SocialProfile.EducationAndProfession
import com.neuro.neuroharmony.ui.login.SocialProfile.EducationInfoViewModel
import com.neuro.neuroharmony.ui.login.SocialProfile.FamilyInfoViewModel
import com.neuro.neuroharmony.ui.login.StatusType
import com.neuro.neuroharmony.utils.CommonUtils
import com.neuro.neuroharmony.utils.PrefsHelper
import kotlinx.android.synthetic.main.activity_education_and_profession.*
import kotlinx.android.synthetic.main.fragment_view_partner_profile_education.*

/**
 * A simple [Fragment] subclass.
 */
class ViewPartnerProfileEducationFragment : Fragment() {

    private lateinit var viewModel: EducationInfoViewModel
    var dialog2: Dialog? = null
    var gson = Gson()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_view_partner_profile_education, container, false)

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val matched_user = PrefsHelper().getPref<String>("matched_user")
        viewModel = ViewModelProviders.of(this)[EducationInfoViewModel::class.java]
        setupObserversEducation()
        viewModel.educationProfLiveData(matched_user)
    }


    private fun setupObserversEducation(){
        viewModel.educationprofResponseLiveData.observe(this, Observer {
            if(it!=null){
                if(it.message=="Success"){
                    if (it.data.userChoice.employmentStatusDescription != null) {
                        employement_status_description_partner_profile.text = it.data.userChoice.employmentStatusDescription.toString()
                    }else{
                        employement_status_description_partner_profile.text = "Not Available"
                    }
                    main_code(it)
                }
                else{
                    Toast.makeText(activity, it.message, Toast.LENGTH_LONG).show()
                }
            }
        })
        viewModel.educationprofApiCallStatus.observe(this, Observer {
            processStatus2(it)
        })
    }


    private fun main_code(it: EducationProfessionResponse) {
        new_function(it.data.choice.employmentStatus,employement_status_partner_profile,it.data.userChoice.employmentStatus)
        new_function(it.data.choice.qualification,professional_qualification_partner_profile,it.data.userChoice.qualification)
        new_function(it.data.choice.presentProfession,present_profession_partner_profile,it.data.userChoice.presentProfession)
        new_function(it.data.choice.presentEmployer,present_employer_partner_profile,it.data.userChoice.presentEmployer)
        new_function(it.data.choice.workExperience,total_experience_partner_profile,it.data.userChoice.workExperience)
        new_function(it.data.choice.salaryRange,salary_range_partner_profile,it.data.userChoice.salaryRange)
    }


    private fun new_function(
        full_list_edu: List<EducationProfStatus>,
        textView: TextView,
        selected_option: Any
    ) {
        if (selected_option == null) {
            textView.text = "Not Available"
        } else {
            val choice = id_to_recieved(full_list_edu, selected_option)
            textView.text = choice
        }
    }


    private fun id_to_recieved(
        full_list1: List<EducationProfStatus>,
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
