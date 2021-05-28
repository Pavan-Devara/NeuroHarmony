package com.neuro.neuroharmony.ui.login

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.widget.Toast
import androidx.core.text.trimmedLength
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.neuro.neuroharmony.R
import com.neuro.neuroharmony.utils.CommonUtils
import com.neuro.neuroharmony.utils.PrefsHelper

import kotlinx.android.synthetic.main.activity_otp_ret.*
import kotlinx.android.synthetic.main.activity_otpverification.*

class SignUpOtpVerification :BaseActivity() {
    var mobile_var = ""
    var email_var = ""

    private lateinit var viewModel: UserActivateViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_otp_ret)


        viewModel = ViewModelProviders.of(this)[UserActivateViewModel::class.java]

        mobile_var = intent.getStringExtra("Mobile")
        email_var = intent.getStringExtra("Gmail")
        var otp = intent.getStringExtra("otp")
        setupListeners(otp)
        setupObservers()



        signup_otp_1.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                // TODO Auto-generated method stub
                if (signup_otp_1.getText().toString().trimmedLength() === 1)
                //size as per your requirement
                {
                    signup_otp_2.requestFocus()
                    signup_otp_1.inputType = InputType.TYPE_CLASS_NUMBER
                    signup_otp_1.transformationMethod = PasswordTransformationMethod.getInstance()
                }
            }

            override fun beforeTextChanged(
                s: CharSequence, start: Int,
                count: Int, after: Int
            ) {
                // TODO Auto-generated method stub
            }

            override fun afterTextChanged(s: Editable) {
                // TODO Auto-generated method stub
            }
        })
        signup_otp_2.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                // TODO Auto-generated method stub
                if (signup_otp_2.getText().toString().trimmedLength() === 1)
                //size as per your requirement
                {
                    signup_otp_3.requestFocus()
                    signup_otp_2.inputType = InputType.TYPE_CLASS_NUMBER
                    signup_otp_2.transformationMethod = PasswordTransformationMethod.getInstance()
                }
            }

            override fun beforeTextChanged(
                s: CharSequence, start: Int,
                count: Int, after: Int
            ) {
                // TODO Auto-generated method stub
            }

            override fun afterTextChanged(s: Editable) {
                // TODO Auto-generated method stub
            }
        })
        signup_otp_3.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                // TODO Auto-generated method stub
                if (signup_otp_3.getText().toString().trimmedLength() === 1)
                //size as per your requirement
                {
                    signup_otp_4.requestFocus()
                    signup_otp_3.inputType = InputType.TYPE_CLASS_NUMBER
                    signup_otp_3.transformationMethod = PasswordTransformationMethod.getInstance()
                }
            }

            override fun beforeTextChanged(
                s: CharSequence, start: Int,
                count: Int, after: Int
            ) {
                // TODO Auto-generated method stub
            }

            override fun afterTextChanged(s: Editable) {
                // TODO Auto-generated method stub
            }
        })
        signup_otp_4.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                // TODO Auto-generated method stub
                if (signup_otp_4.getText().toString().trimmedLength() === 1)
                //size as per your requirement
                {
                    signup_otp_5.requestFocus()
                    signup_otp_4.inputType = InputType.TYPE_CLASS_NUMBER
                    signup_otp_4.transformationMethod = PasswordTransformationMethod.getInstance()
                }
            }

            override fun beforeTextChanged(
                s: CharSequence, start: Int,
                count: Int, after: Int
            ) {
                // TODO Auto-generated method stub
            }

            override fun afterTextChanged(s: Editable) {
                // TODO Auto-generated method stub
            }
        })




    }

    private fun setupListeners(otp: String?) {

        Log.e("UserActivate","Success")
        verify.setOnClickListener {
            if (otp == signup_otp_1.text.trim().toString() + signup_otp_2.text.trim().toString()+signup_otp_3.text.trim().toString()+signup_otp_4.text.trim().toString()+signup_otp_5.text.trim().toString()){

                viewModel.useractivate()
                Log.d("otp", otp)
            }else {
                Toast.makeText(this, "The OTP entered does not match. Please enter again", Toast.LENGTH_SHORT).show()
            }


        }

    }



    /**
     * Write all LiveData observers in this method
     */
    private  fun setupObservers(){
        viewModel.loginResponseLiveData.observe(this, Observer {
            if(it!=null){
                if( it.message=="Success"){
                    PrefsHelper().savePref("Account_Status",2).toString()
                    Log.d("Account_status",PrefsHelper().getPref("Account_Status",0).toString())
                    PrefsHelper().savePref("Token",it.token)
                    PrefsHelper().savePref("Mobile",mobile_var)
                    PrefsHelper().savePref("Gmail",email_var)
                    Log.d("otp", signup_otp_1.text.trim().toString() + signup_otp_2.text.trim().toString()+signup_otp_3.text.trim().toString()+signup_otp_4.text.trim().toString()+signup_otp_5.text.trim().toString())

                    val intent = Intent(this, SelectUserTypeScreen::class.java)
                    startActivity(intent)
                    // Success login.  Add the success scenario here ex: Move to next screen
                }
                else{
                    Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
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
                CommonUtils().showSnackbar(verify.rootView,"Verification failed")
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



