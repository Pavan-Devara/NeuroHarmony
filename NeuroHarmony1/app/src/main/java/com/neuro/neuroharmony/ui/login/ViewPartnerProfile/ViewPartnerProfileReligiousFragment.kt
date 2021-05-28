package com.neuro.neuroharmony.ui.login.ViewPartnerProfile


import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.gson.Gson

import com.neuro.neuroharmony.R
import com.neuro.neuroharmony.ui.login.ReligiousInfoViewModel
import com.neuro.neuroharmony.ui.login.ResourceStatus
import com.neuro.neuroharmony.ui.login.SocialProfile.PersonalInfoViewModel
import com.neuro.neuroharmony.ui.login.StatusType
import com.neuro.neuroharmony.utils.CommonUtils
import com.neuro.neuroharmony.utils.PrefsHelper
import kotlinx.android.synthetic.main.activity_religious_info_version2.*
import kotlinx.android.synthetic.main.fragment_view_partner_profile_religious.*
import org.json.JSONObject

/**
 * A simple [Fragment] subclass.
 */
class ViewPartnerProfileReligiousFragment : Fragment() {

    lateinit var viewModel: ReligiousInfoViewModel
    var dialog2: Dialog? = null
    var gson = Gson()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_view_partner_profile_religious, container, false)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val matched_user = PrefsHelper().getPref<String>("matched_user")
        viewModel = ViewModelProviders.of(this)[ReligiousInfoViewModel::class.java]
        setupobserversReligiousInfo()
        viewModel.religiosInfoLiveData(matched_user)
    }

    private fun setupobserversReligiousInfo() {
        viewModel.religiousInfoResponseLiveData.observe(this, Observer {
            if (it != null) {
                if (it.message == "Success") {
                    val jsonArray = gson.toJson(it.data)
                    val object_main1 = JSONObject(jsonArray)
                    val obj_main = object_main1.getJSONObject("user_choice")
                    if(obj_main.has("horoscope")) {
                        val full_path = obj_main["horoscope"].toString()
                        val intent =
                            Intent(Intent.ACTION_VIEW).setData(
                                Uri.parse(
                                    full_path))
                        horoscope_partner_profile.text = "Tap to View"

                        horoscope_partner_profile.setOnClickListener {
                            startActivity(intent)
                        }
                    }
                    if (obj_main.has("religion")) {
                        val religion = view!!.findViewById<TextView>(R.id.religion_partner_profile)
                        religion.text = it.data.user_choice.religion_name.toString().replace("\"", "")

                    }else{
                        val religion = view!!.findViewById<TextView>(R.id.religion_partner_profile)
                        religion.text = "Not Available"
                    }
                    if(it.data.user_choice.caste_name!=null) {
                        val state = view!!.findViewById<TextView>(R.id.caste_partner_profile)
                        state.text = it.data.user_choice.caste_name.toString().replace("\"", "")
                    }else{
                        val state = view!!.findViewById<TextView>(R.id.caste_partner_profile)
                        state.text = "Not Available"
                    }
                    if(it.data.user_choice.subCaste_text != null){
                        var subcaste = it.data.user_choice.subCaste_text.toString()
                        subcaste  = subcaste.replace("\"", "")
                        sub_caste_partner_profile.text = subcaste
                    }else{
                        sub_caste_partner_profile.text = "Not Available"
                    }
                    if (it.data.user_choice.worshipPlaceVisitText != null){
                        frequency_of_worship_partner_profile.text = it.data.user_choice.worshipPlaceVisitText.toString()
                    }else{
                        frequency_of_worship_partner_profile.text = "Not Available"
                    }
                    if (it.data.user_choice.deeplyReligiousText != null){
                        deeply_religious_partner_profile.text = it.data.user_choice.deeplyReligiousText.toString()
                    }else{
                        deeply_religious_partner_profile.text = "Not Available"
                    }
                }
                else{
                    Toast.makeText(activity, it.message, Toast.LENGTH_LONG).show()
                }
            }
        })
        //observe API call status
        viewModel.religiousInfoApiCallStatus.observe(this, Observer {
            processStatus2(it)
        })
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
