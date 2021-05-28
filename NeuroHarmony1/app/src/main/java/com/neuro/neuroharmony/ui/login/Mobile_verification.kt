package com.neuro.neuroharmony.ui.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.telephony.TelephonyManager
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.neuro.neuroharmony.R
import com.neuro.neuroharmony.utils.CommonUtils
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_mobile_verification.*
import java.util.*

class Mobile_verification : BaseActivity(){

    private lateinit var viewModel: ResetPassRequestOtpViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mobile_verification)


        viewModel = ViewModelProviders.of(this)[ResetPassRequestOtpViewModel::class.java]

        setupListeners()
        setupObservers()


        val present_country = getDetectedCountry(this, "IN")
//        val locale: String = this?.resources?.configuration?.locale!!.country
        var locale = present_country
        locale = locale.toUpperCase(Locale.getDefault())
        country_code_picker_forgot_pwd.setDefaultCountryUsingNameCode(locale)
        country_code_picker_forgot_pwd.resetToDefaultCountry()

        req_otp_button.setOnClickListener {
            val mobile_var = editText_mob.text.trim()
            if (Validator.isValidDigit(mobile_var)) {

                viewModel.forgototprequest(editText_mob.text.trim().toString(),country_code_picker_forgot_pwd.selectedCountryCodeAsInt)



            }else{

                Toast.makeText(this, "Please enter a valid mobile number", Toast.LENGTH_LONG).show()

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

    private fun setupListeners(){
        /* email_mobile_continue.setOnClickListener {
             //initiate API call using ViewModel method
             Log.e("SingupView","setOnClickListener")
             viewModel.signup( 1, "",signuppassword.text.trim().toString(),email_id_signup.text.trim().toString(),mobile_id_signup.text.trim().toString())
         }
 */
    }

    /**
     * Write all LiveData observers in this method
     */
    private  fun setupObservers(){





        viewModel.loginResponseLiveData.observe(this, Observer {
            if(it!=null){
                if( it.message=="Success"){
                    var otp = it.data.otp.toString()
                    val mobile_var = editText_mob.text.toString().trim()
                    Log.e("MobileVerification","Mobile:$mobile_var")
                    val intent = Intent(this, OtpVerification::class.java)
                    intent.putExtra("Forgot_Mobile",mobile_var)
                    intent.putExtra("otp",otp)
                    intent.putExtra("otp_retry_limit", it.data.otpRetryLimit)
                    intent.putExtra("otp_expiry_time", it.data.otpExpiryTime)
                    startActivity(intent)
                    // Success login.  Add the success scenario here ex: Move to next screen
                }
                else{
                    Toast.makeText(this, "Please enter a valid mobile number", Toast.LENGTH_LONG).show()
                }
            }
        })


        //observe API call status
        viewModel.loginAPICallStatus.observe(this, Observer {
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
                CommonUtils().showSnackbar(req_otp_button.rootView,"Verify again")
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







}
