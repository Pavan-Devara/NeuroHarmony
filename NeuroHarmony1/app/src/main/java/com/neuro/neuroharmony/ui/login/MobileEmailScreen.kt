package com.neuro.neuroharmony.ui.login

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.telephony.TelephonyManager
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.neuro.neuroharmony.R
import com.neuro.neuroharmony.utils.CommonUtils
import com.neuro.neuroharmony.utils.PrefsHelper
import kotlinx.android.synthetic.main.activity_mobile_email_screen.*
import kotlinx.android.synthetic.main.activity_mobile_verification.*
import java.util.*


class MobileEmailScreen :BaseActivity() {

    companion object{
        val FACEBOOK = 3
        val BASIC = 2
        val GOOGLE = 1
    }

    private lateinit var viewModel: SignupViewModel
    var password=""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mobile_email_screen)

        password = intent.getStringExtra("password")
        viewModel = ViewModelProviders.of(this)[SignupViewModel::class.java]

        if (PrefsHelper().getPref("signin")){
            email_id_signup.setText(PrefsHelper().getPref<String>("gmail"))
            email_id_signup.setFocusable(false);
            email_id_signup.setTextColor(Color.parseColor("#555555"))
            email_id_signup.setEnabled(false);
            email_id_signup.setCursorVisible(false);
            email_id_signup.setKeyListener(null);
        }
        setupListeners()
        setupObservers()



        email_mobile_continue.setOnClickListener {
            Log.e("SingupView>>","11")
            val email_var = email_id_signup.text.trim()
            val mobile_var = mobile_id_signup.text.trim()
            if(Validator.isValidDigit(mobile_var)) {
                if (!PrefsHelper().getPref<Boolean>("signin")) {
                    if (Validator.isValidEmail(email_var)) {
                        viewModel.signup(
                            BASIC,
                            "",
                            email_id_signup.text.trim().toString(),
                            mobile_id_signup.text.trim().toString(),
                            password,country_code_picker_sign_up.selectedCountryCodeAsInt
                        )

                    } else {
                        Toast.makeText(this, "Please enter a valid email id", Toast.LENGTH_LONG).show()
                    }

                }else {
                    if (Validator.isValidEmail(email_var)) {
                        if (PrefsHelper().getPref<Int>("signin_method")== GOOGLE) {
                            viewModel.signup(
                                GOOGLE,
                                PrefsHelper().getPref("gmail_clientID"),
                                email_id_signup.text.trim().toString(),
                                mobile_id_signup.text.trim().toString(),
                                password,country_code_picker_sign_up.selectedCountryCodeAsInt
                            )
                        }else{
                            viewModel.signup(
                                FACEBOOK,
                                PrefsHelper().getPref("fb_clientID"),
                                email_id_signup.text.trim().toString(),
                                mobile_id_signup.text.trim().toString(),
                                password,country_code_picker_sign_up.selectedCountryCodeAsInt
                            )
                        }

                    } else {

                        Toast.makeText(this, "Please enter a valid email id", Toast.LENGTH_LONG).show()


                    }
                }
            }
            else{

                Toast.makeText(this, "Please enter a valid mobile number", Toast.LENGTH_LONG).show()

            }

        }

        val present_country = getDetectedCountry(this, "IN")
//        val locale: String = this?.resources?.configuration?.locale!!.country
        var locale = present_country
        locale = locale.toUpperCase(Locale.getDefault())
        country_code_picker_sign_up.setDefaultCountryUsingNameCode(locale)
        country_code_picker_sign_up.resetToDefaultCountry()

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

                    val mobile_var1 = mobile_id_signup.text.toString().trim()
                    Log.e("MobileVerification","Mobile:$mobile_var1")
                    val email_var1 = email_id_signup.text.toString().trim()


                   PrefsHelper().savePref("Token",it.token)

                    val intent = Intent(this, SignUpOtpVerification::class.java)
                    intent.putExtra("Mobile",mobile_var1)
                    intent.putExtra("otp",it.data.otp.toString())
                    intent.putExtra("Gmail",email_var1)
                    startActivity(intent)
                    // Success login.  Add the success scenario here ex: Move to next screen
                }
                else{
                    Toast.makeText(this,it.message,Toast.LENGTH_SHORT).show()
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
                CommonUtils().showSnackbar(email_id_signup.rootView,"Signup failed")
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


    override fun onBackPressed() {

    }

}
