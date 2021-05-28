package com.neuro.neuroharmony.ui.login.CompleteMatch

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.telephony.TelephonyManager
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.neuro.neuroharmony.R
import com.neuro.neuroharmony.ui.login.BaseActivity
import com.neuro.neuroharmony.ui.login.ResourceStatus
import com.neuro.neuroharmony.ui.login.StatusType
import kotlinx.android.synthetic.main.activity_login.*
import com.neuro.neuroharmony.utils.PrefsHelper
import kotlinx.android.synthetic.main.activity_search_partner.*
import java.util.*

class SearchPartner : BaseActivity() {
    private lateinit var viewModel: CompleteMatchViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_partner)
        viewModel = ViewModelProviders.of(this)[CompleteMatchViewModel::class.java]
        setupObservers()

        button_okay_final_partner.setOnClickListener {
            viewModel.completematchvm(null,91,edit_mobile_finalize.text.trim().toString())

        }

        back_add_final_match.setOnClickListener {
            onBackPressed()
        }


        val present_country = getDetectedCountry(this, "IN")
//        val locale: String = this?.resources?.configuration?.locale!!.country
        var locale = present_country
        locale = locale.toUpperCase(Locale.getDefault())
        country_code_picker_search_match.setDefaultCountryUsingNameCode(locale)
        country_code_picker_search_match.resetToDefaultCountry()

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
            Log.d(com.neuro.neuroharmony.ui.login.TAG, "detectSIMCountry: ${telephonyManager.simCountryIso}")
            return telephonyManager.simCountryIso
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    private fun detectNetworkCountry(context: Context): String? {
        try {
            val telephonyManager = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
            Log.d(com.neuro.neuroharmony.ui.login.TAG, "detectNetworkCountry: ${telephonyManager.simCountryIso}")
            return telephonyManager.networkCountryIso
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    private fun detectLocaleCountry(context: Context): String? {
        try {
            val localeCountryISO = context.getResources().getConfiguration().locale.getCountry()
            Log.d(com.neuro.neuroharmony.ui.login.TAG, "detectNetworkCountry: $localeCountryISO")
            return localeCountryISO
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    private  fun setupObservers(){
        viewModel.completematchResponseLiveData.observe(this, Observer {
            if(it!=null){
                if( it.message=="Success"){
                    PrefsHelper().savePref("final_match",true)

                    Toast.makeText(this, "Request sent succesfully", Toast.LENGTH_SHORT).show()
                    edit_mobile_finalize.setText("")
                }
                else{
                    Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                }

            }
        })



        //observe API call status
        viewModel.completematchAPICallStatus.observe(this, Observer {
            processStatus(it)
        })

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
                //CommonUtils().showSnackbar(toggle_off.rootView,"Login failed")
                dismissDialog()
            }
            StatusType.LOADING_MORE -> {
                // CommonUtils().showSnackbar(toggle_off.rootView, "Loading more..")
            }
            StatusType.NO_NETWORK -> {
                Toast.makeText(this, "Please check internet connection", Toast.LENGTH_SHORT).show()
            }
            StatusType.SESSION_EXPIRED -> {
                //CommonUtils().showSnackbar(login_button_login.rootView,"session expired")
            }
        }
    }
}
