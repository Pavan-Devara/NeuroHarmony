package com.neuro.neuroharmony.ui.login.manualMatching

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.app.PendingIntent
import android.content.*
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.telephony.SmsManager
import android.telephony.TelephonyManager
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.neuro.neuroharmony.R
import com.neuro.neuroharmony.ui.login.*
import com.neuro.neuroharmony.utils.CommonUtils
import com.neuro.neuroharmony.utils.PrefsHelper
import kotlinx.android.synthetic.main.activity_add_apartner.*
import kotlinx.android.synthetic.main.activity_chat_window.*
import kotlinx.android.synthetic.main.activity_mobile_verification.*
import java.util.*


class AddAPartner : BaseActivity() {
    private lateinit var viewModel: AddPartnerViewModel
    val PERMISSION_CODE = 121

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_apartner)
        viewModel = ViewModelProviders.of(this)[AddPartnerViewModel::class.java]

        setupObservers()

        val present_country = getDetectedCountry(this, "IN")
//        val locale: String = this?.resources?.configuration?.locale!!.country
        var locale = present_country
        locale = locale.toUpperCase(Locale.getDefault())
        country_code_picker_manual_match.setDefaultCountryUsingNameCode(locale)
        country_code_picker_manual_match.resetToDefaultCountry()

        button_okay_add_partner.setOnClickListener{

            if(PrefsHelper().getPref<String>("Mobile")==edit_mobile_add_partner.text.trim().toString()){

                Log.d("number", PrefsHelper().getPref<String>("Mobile"))
                Toast.makeText(this, "You can't invite yourself!", Toast.LENGTH_LONG).show()
            }
            else{
                val Add_partner_cost = PrefsHelper().getPref<Int>("request_manual_match_debit_action")

                val dialogBuilder = AlertDialog.Builder(this)

                // set message of alert dialog
//                    dialogBuilder.setMessage("Feedback Submitted \n       Successfully")

                dialogBuilder.setMessage("This action will deduct "+Add_partner_cost.toString()+" tokens from your account")
                    // if the dialog is cancelable
                    .setCancelable(false)
                    // positive button text and action
                    .setPositiveButton("Proceed", DialogInterface.OnClickListener {
                            dialog, id -> dialog.cancel()
                        viewModel.addpartnervm(edit_mobile_add_partner.text.trim().toString(),country_code_picker_manual_match.selectedCountryCodeAsInt)
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

        }



    }


    fun getDetectedCountry(context: Context, defaultCountryIsoCode: String): String {
        detectSIMCountry(context)?.let {
            return it
        }

        detectNetworkCountry(context)?.let {
            return it
        }

        detectLocaleCountry(context)?.let {
            return it
        }

        return defaultCountryIsoCode
    }

    private fun detectSIMCountry(context: Context): String? {
        try {
            val telephonyManager = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
            Log.d(TAG, "detectSIMCountry: ${telephonyManager.simCountryIso}")
            return telephonyManager.simCountryIso
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    private fun detectNetworkCountry(context: Context): String? {
        try {
            val telephonyManager = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
            Log.d(TAG, "detectNetworkCountry: ${telephonyManager.simCountryIso}")
            return telephonyManager.networkCountryIso
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    private fun detectLocaleCountry(context: Context): String? {
        try {
            val localeCountryISO = context.getResources().getConfiguration().locale.getCountry()
            Log.d(TAG, "detectNetworkCountry: $localeCountryISO")
            return localeCountryISO
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun setupObservers() {
        viewModel.addpartnerResponseLiveData.observe(this, Observer {
            if (it != null) {
                if (it.message == "Success") {

                    if (it.data.isInvited) {

                        if (checkSelfPermission(Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_DENIED ){
                            val permission = arrayOf(Manifest.permission.SEND_SMS)

                            requestPermissions(permission, PERMISSION_CODE)
                        }else{
                            dialogToSendSms()
                        }
                    }else{
                        if (PrefsHelper().getPref<Int>("userType")==1){
                            val dialogBuilder = AlertDialog.Builder(this)

                            // set message of alert dialog
                            dialogBuilder.setMessage("An invitation has been sent to "+ edit_mobile_add_partner.text.trim().toString() + " for accepting manual match")
                                // if the dialog is cancelable
                                .setCancelable(false)
                                // positive button text and action
                                .setPositiveButton(
                                    "Okay",
                                    DialogInterface.OnClickListener { dialog, id ->

                                        edit_mobile_add_partner.setText("")
                                        dialog.cancel()
                                    })
                            // negative button text and action

                            // create dialog box
                            val alert = dialogBuilder.create()
                            // set title for alert dialog box
                            alert.setTitle("Message")
                            // show alert dialog
                            alert.show()
                        }
                        else {

                            val dialogBuilder = AlertDialog.Builder(this)

                            // set message of alert dialog
                            dialogBuilder.setMessage("An invitation has been sent to " + edit_mobile_add_partner.text.trim().toString() + " for accepting couple match")
                                // if the dialog is cancelable
                                .setCancelable(false)
                                // positive button text and action
                                .setPositiveButton(
                                    "Okay",
                                    DialogInterface.OnClickListener { dialog, id ->

                                        edit_mobile_add_partner.setText("")
                                        dialog.cancel()
                                    })
                            // negative button text and action

                            // create dialog box
                            val alert = dialogBuilder.create()
                            // set title for alert dialog box
                            alert.setTitle("Message")
                            // show alert dialog
                            alert.show()
                        }


                    }
                }
                else{
                    Toast.makeText(this,"Please enter a valid mobile number", Toast.LENGTH_LONG).show()
                }
            }
        })
        viewModel.addpartnerAPICallStatus.observe(this, Observer {
            processStatus(it)
        })
    }




    private fun dialogToSendSms() {


        val message = "Hi, I have added you as a partner on Neuro Harmony. Please sign up to accept my invite."
        val countrycode = country_code_picker_manual_match.selectedCountryCode
        val number = "+"+countrycode+edit_mobile_add_partner.text.trim().toString()
        sendSMS(number, message)
        val dialogBuilder = AlertDialog.Builder(this)

        // set message of alert dialog
        dialogBuilder.setMessage("An invitation has been sent to "+ edit_mobile_add_partner.text.trim().toString() + " to signup for NeuroHarmony")
            // if the dialog is cancelable
            .setCancelable(false)
            // positive button text and action
            .setPositiveButton(
                "Okay",
                DialogInterface.OnClickListener { dialog, id ->
                    edit_mobile_add_partner.setText("")
                    dialog.cancel()
                })
        // negative button text and action

        // create dialog box
        val alert = dialogBuilder.create()
        // set title for alert dialog box
        alert.setTitle("Message")
        // show alert dialog
        alert.show()

        // Success login.  Add the success scenario here ex: Move to next screen
    }


    private fun sendSMS(phoneNumber: String, message: String) {
        val SENT = "SMS_SENT"
        val DELIVERED = "SMS_DELIVERED"
        val sentPI = PendingIntent.getBroadcast(
            this, 0, Intent(
                SENT
            ), 0
        )
        val deliveredPI = PendingIntent.getBroadcast(
            this, 0,
            Intent(DELIVERED), 0
        )
        // ---when the SMS has been sent---
        registerReceiver(object : BroadcastReceiver() {
            override fun onReceive(arg0: Context?, arg1: Intent?) {
                when (resultCode) {
                    Activity.RESULT_OK -> {

                        if (PrefsHelper().getPref<Int>("userType")==1){
                            val values = ContentValues()
                            val MessageText = "Hi, "+ edit_mobile_add_partner.text.trim().toString() +" invited you for a manual match on NeuroHarmony."
                            values.put("body", MessageText)

                            contentResolver.insert(
                                Uri.parse("content://sms/sent"), values
                            )
                            Toast.makeText(
                                baseContext, "SMS sent",
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                        else
                        {
                            val values = ContentValues()
                            val MessageText = "Hi, "+ edit_mobile_add_partner.text.trim().toString() +" invited you for a couple match on NeuroHarmony."
                            values.put("body", MessageText)

                            contentResolver.insert(
                                Uri.parse("content://sms/sent"), values
                            )
                            Toast.makeText(
                                baseContext, "SMS sent",
                                Toast.LENGTH_SHORT
                            ).show()
                        }


                    }
                    SmsManager.RESULT_ERROR_GENERIC_FAILURE -> Toast.makeText(
                        baseContext, "Generic failure",
                        Toast.LENGTH_SHORT
                    ).show()
                    SmsManager.RESULT_ERROR_NO_SERVICE -> Toast.makeText(
                        baseContext, "No service",
                        Toast.LENGTH_SHORT
                    ).show()
                    SmsManager.RESULT_ERROR_NULL_PDU -> Toast.makeText(
                        baseContext, "Null PDU",
                        Toast.LENGTH_SHORT
                    ).show()
                    SmsManager.RESULT_ERROR_RADIO_OFF -> Toast.makeText(
                        baseContext, "Radio off",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }, IntentFilter(SENT))
        // ---when the SMS has been delivered---
        registerReceiver(object : BroadcastReceiver() {
            override fun onReceive(arg0: Context?, arg1: Intent?) {
                when (resultCode) {
                    Activity.RESULT_OK -> Toast.makeText(
                        baseContext, "SMS delivered",
                        Toast.LENGTH_SHORT
                    ).show()
                    Activity.RESULT_CANCELED -> Toast.makeText(
                        baseContext, "SMS not delivered",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }, IntentFilter(DELIVERED))
        val sms: SmsManager = SmsManager.getDefault()
        sms.sendTextMessage(phoneNumber, null, message, sentPI, deliveredPI)

    }

    private fun processStatus(resource: ResourceStatus) {

        when (resource.status) {
            StatusType.SUCCESS -> {
                dismissDialog()



            }
            StatusType.EMPTY_RESPONSE -> {
                dismissDialog()

            }
            StatusType.PROGRESSING -> {
                showDialog()

            }
            StatusType.SWIPE_RELOADING -> {

            }
            StatusType.ERROR -> {
                CommonUtils().showSnackbar(button_okay_add_partner.rootView,"Please Try again")
                dismissDialog()



            }
            StatusType.LOADING_MORE -> {
                // CommonUtils().showSnackbar(binding.root, "Loading more..")
            }
            StatusType.NO_NETWORK -> {
                Toast.makeText(this, "Please check internet connection", Toast.LENGTH_SHORT).show()
            }
            StatusType.SESSION_EXPIRED -> {
                //CommonUtils().showSnackbar(login_button_login.rootView,"session expired")
            }
        }
    }



    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when(requestCode){
            PERMISSION_CODE -> {
                if (grantResults.size > 0 && grantResults[0]== PackageManager.PERMISSION_GRANTED ){
                    dialogToSendSms()
                }else{
                    Toast.makeText(this,"Permission Denied", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
