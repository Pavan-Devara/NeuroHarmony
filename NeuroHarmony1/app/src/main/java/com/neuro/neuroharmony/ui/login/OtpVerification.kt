package com.neuro.neuroharmony.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_otpverification.*
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.core.text.trimmedLength
import android.text.InputType
import android.R.attr.password
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.text.method.PasswordTransformationMethod
import android.R
import android.widget.EditText

import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T






class OtpVerification : AppCompatActivity() {

    var mobile_var= ""
    var otp = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.neuro.neuroharmony.R.layout.activity_otpverification)

        mobile_var = intent.getStringExtra("Forgot_Mobile")
        otp = intent.getStringExtra("otp")
        val otp_retry_limit = intent.getIntExtra("otp_retry_limit", 0)
        val otp_expiry_time = intent.getIntExtra("otp_expiry_time", 0)




        forgot_pass_otp_1.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                // TODO Auto-generated method stub
                if (forgot_pass_otp_1.getText().toString().trimmedLength() === 1)
                //size as per your requirement
                {


                    forgot_pass_otp_2.requestFocus()
                    forgot_pass_otp_1.inputType = InputType.TYPE_CLASS_NUMBER
                    forgot_pass_otp_1.transformationMethod = PasswordTransformationMethod.getInstance()

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
        forgot_pass_otp_2.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                // TODO Auto-generated method stub
                if (forgot_pass_otp_2.getText().toString().trimmedLength() === 1)
                //size as per your requirement
                {
                    forgot_pass_otp_3.requestFocus()
                    forgot_pass_otp_2.inputType = InputType.TYPE_CLASS_NUMBER
                    forgot_pass_otp_2.transformationMethod = PasswordTransformationMethod.getInstance()
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
        forgot_pass_otp_3.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                // TODO Auto-generated method stub
                if (forgot_pass_otp_3.getText().toString().trimmedLength() === 1)
                //size as per your requirement
                {
                    forgot_pass_otp_4.requestFocus()
                    forgot_pass_otp_3.inputType = InputType.TYPE_CLASS_NUMBER
                    forgot_pass_otp_3.transformationMethod = PasswordTransformationMethod.getInstance()
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
        forgot_pass_otp_4.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                // TODO Auto-generated method stub
                if (forgot_pass_otp_4.getText().toString().trimmedLength() === 1)
                //size as per your requirement
                {
                    forgot_pass_otp_5.requestFocus()
                    forgot_pass_otp_4.inputType = InputType.TYPE_CLASS_NUMBER
                    forgot_pass_otp_4.transformationMethod = PasswordTransformationMethod.getInstance()
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

        val time1 = System.currentTimeMillis()


        otp_verify.setOnClickListener {
            val time2 = System.currentTimeMillis()
            if(forgot_pass_otp_1.text.trim().isNotEmpty() && forgot_pass_otp_2.text.trim().isNotEmpty() && forgot_pass_otp_3.text.trim().isNotEmpty() && forgot_pass_otp_4.text.trim().isNotEmpty() && forgot_pass_otp_5.text.trim().isNotEmpty()) {
                if (time2-time1>(otp_expiry_time*60*1000)) {
                    if (otp == forgot_pass_otp_1.text.trim()
                            .toString() + forgot_pass_otp_2.text.trim()
                            .toString() + forgot_pass_otp_3.text.trim()
                            .toString() + forgot_pass_otp_4.text.trim()
                            .toString() + forgot_pass_otp_5.text.trim().toString()
                    ) {
                        val intent = Intent(this, ResetPassword::class.java)
                        intent.putExtra("Forgot_Mobile", mobile_var)
                        startActivity(intent)

                    } else {
                        Toast.makeText(
                            this,
                            "The OTP entered does not match. Please enter again",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }else{
                    Toast.makeText(
                        this,
                        "Your otp has been expired",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            else{
                Toast.makeText(
                    this,
                    "Enter the OTP received on you registered mobile number",
                    Toast.LENGTH_SHORT
                ).show()
            }



        }


    }
}
