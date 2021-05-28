package com.neuro.neuroharmony.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.gson.Gson
import com.neuro.neuroharmony.R
import com.neuro.neuroharmony.utils.CommonUtils
import com.neuro.neuroharmony.utils.PrefsHelper
import kotlinx.android.synthetic.main.activity_distance_report_matched_user.*
import kotlinx.android.synthetic.main.activity_policy_and_terms_screen.*
import kotlinx.android.synthetic.main.activity_select__user__type__screen.*
import kotlinx.android.synthetic.main.activity_terms_and_conditions.*
import org.json.JSONArray

class TermsAndConditions : BaseActivity() {

    var mobile_var= ""
    var email_var = ""

    var flag = 0





    override fun onStart() {
        super.onStart()
        val termsandcondition = PrefsHelper().getPref<Int>("termsandcondition")
        if (termsandcondition == 1){
            radio_ters.isChecked = true
        }

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_terms_and_conditions)



        /*mobile_var = intent.getStringExtra("Mobile")
        email_var = intent.getStringExtra("Gmail")*/

        val intent = intent

        var terms = intent.getStringExtra("Terms")

        val mimeType: String = "text/html"
        val utfType: String = "UTF-8"


        terms_scroll.loadDataWithBaseURL(null,terms,mimeType,utfType, null)

        terms_back.setOnClickListener {
            val intent = Intent(this,PolicyAndTermsScreen::class.java)
            startActivity(intent)
        }




        agree_button.setOnClickListener {

            if(radio_ters.isChecked)
            {
                PrefsHelper().savePref("termsandcondition",1)
                val intent = Intent(this,PolicyAndTermsScreen::class.java)
                startActivity(intent)
            }
            else{
                Toast.makeText(this,"Please accept the Terms of Use to proceed", Toast.LENGTH_LONG).show()
            }

        }


    }
    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this,PolicyAndTermsScreen::class.java)
        startActivity(intent)
    }




}
