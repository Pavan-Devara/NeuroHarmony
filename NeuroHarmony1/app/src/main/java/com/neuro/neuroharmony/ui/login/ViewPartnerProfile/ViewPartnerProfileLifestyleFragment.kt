package com.neuro.neuroharmony.ui.login.ViewPartnerProfile


import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
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
import com.neuro.neuroharmony.data.model.Social_profile.Lifestyleinfo.Habits
import com.neuro.neuroharmony.data.model.Social_profile.Lifestyleinfo.LifestyleInfoResponse
import com.neuro.neuroharmony.ui.login.ResourceStatus
import com.neuro.neuroharmony.ui.login.SocialProfile.FamilyInfoViewModel
import com.neuro.neuroharmony.ui.login.SocialProfile.LifeStyleInfo
import com.neuro.neuroharmony.ui.login.SocialProfile.LifestyleInfoViewModel
import com.neuro.neuroharmony.ui.login.StatusType
import com.neuro.neuroharmony.utils.CommonUtils
import com.neuro.neuroharmony.utils.PrefsHelper
import kotlinx.android.synthetic.main.activity_life_style_info.*
import kotlinx.android.synthetic.main.fragment_view_partner_profile_lifestyle.*
import kotlin.time.measureTime

/**
 * A simple [Fragment] subclass.
 */
class ViewPartnerProfileLifestyleFragment : Fragment() {

    private lateinit var viewModel: LifestyleInfoViewModel
    var dialog2: Dialog? = null
    var gson = Gson()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view= inflater.inflate(R.layout.fragment_view_partner_profile_lifestyle, container, false)

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val matched_user = PrefsHelper().getPref<String>("matched_user")
        viewModel = ViewModelProviders.of(this)[LifestyleInfoViewModel::class.java]
        setupObserversLifestyleInfo()
        viewModel.lifestyleInfoLiveData(matched_user)
    }


    private fun setupObserversLifestyleInfo(){
        viewModel.lifestyleInfoResponseLiveData.observe(this, Observer {
            if(it!=null){
                if(it.message=="Success"){
                    main_code(it)
                }
                else{
                    Toast.makeText(activity, it.message, Toast.LENGTH_LONG).show()
                }
            }
        })
        viewModel.lifestyleInfoApiCallStatus.observe(this, Observer {
            processStatus2(it)
        })
    }


    private fun main_code(it: LifestyleInfoResponse) {

        lifestyle_function(drinking_habit_partner_profile, it.data.choice.alcohol,it.data.userChoice.alcohol)
        lifestyle_function(dietary_preference_partner_profile, it.data.choice.dietary,it.data.userChoice.dietary)
        lifestyle_function(smoking_habit_partner_profile, it.data.choice.smoke,it.data.userChoice.smoke)
        lifestyle_function(owns_a_pet_partner_profile, it.data.choice.pet,it.data.userChoice.pet)
        lifestyle_function(wear_presciption_glasses_partner_profile, it.data.choice.sunglasses,it.data.userChoice.sunglasses)
        lifestyle_function(intend_to_work_partner_profile, it.data.choice.postWorking,it.data.userChoice.postWorking)
    }

    private fun lifestyle_function(
        textView: TextView,
        lifestyle_full: List<Habits>,
        selected_option: Any
    ) {
        if (selected_option == null) {
            textView.text = "Not Available"
        }
        else{
            val choice = id_to_recieved(lifestyle_full,selected_option)
            textView.text = choice
        }
    }


    private fun id_to_recieved(
        full_list1: List<Habits>,
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
