package com.neuro.neuroharmony.ui.login

import android.content.Intent
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.neuro.neuroharmony.utils.PrefsHelper
import com.neuro.neuroharmony.R
import kotlinx.android.synthetic.main.activity_main.*
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T







class MainActivity : AppCompatActivity() {
    private var mIshowPass = false
    private lateinit var viewModel: SignupViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        ivShowHidePass.setOnClickListener {
            mIshowPass = !mIshowPass
            showPassword(mIshowPass)
        }
        showPassword(mIshowPass)


        signinhere.setOnClickListener {
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
        }

        signup.setOnClickListener{
            val password1 = signuppassword.text.toString().trim()
            val confirmpassword1 = signupconfirmpassword.text.toString().trim()

            if(Validator.isValidPassword(password1)){
                if(password1 == confirmpassword1)
                {
                    Log.e("password",">>$confirmpassword1")
                    PrefsHelper().savePref("signin", false)
                    val intent = Intent(this,MobileEmailScreen::class.java )
                    intent.putExtra("password",password1)
                    startActivity(intent)
                }
                else{

                    Toast.makeText(this, "Confirm password doesn't match new password", Toast.LENGTH_LONG).show()

                }
            }else{

                Toast.makeText(this, "Please choose an 8 character password containing atleast one uppercase, lowercase, a digit and a special character", Toast.LENGTH_LONG).show()

            }
        }
    }

    private fun showPassword(isShow: Boolean){
        if(isShow) {
            signuppassword.transformationMethod = HideReturnsTransformationMethod.getInstance()
            ivShowHidePass.setImageResource(R.drawable.visibility_off_black_24dp)

        } else {
            signuppassword.transformationMethod = PasswordTransformationMethod.getInstance()
            ivShowHidePass.setImageResource(R.drawable.password_visibility_24dp)

        }
        signuppassword.setSelection(signuppassword.text.toString().length)
    }



}
