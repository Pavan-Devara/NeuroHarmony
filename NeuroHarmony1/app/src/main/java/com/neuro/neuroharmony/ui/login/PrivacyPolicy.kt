package com.neuro.neuroharmony.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.neuro.neuroharmony.R
import com.neuro.neuroharmony.utils.PrefsHelper
import kotlinx.android.synthetic.main.activity_policy_and_terms_screen.*
import kotlinx.android.synthetic.main.activity_privacy_policy.*
import kotlinx.android.synthetic.main.activity_terms_and_conditions.*

class PrivacyPolicy : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_privacy_policy)

        val intent = intent

        var policies = intent.getStringExtra("Policies")

        val mimeType: String = "text/html"
        val utfType: String = "UTF-8"

        privacy_scroll.loadDataWithBaseURL(null,policies,mimeType,utfType, null)
        var privatepolicy = PrefsHelper().getPref<Int>("privatepolicy")
        if (privatepolicy == 1){
            radio_privacy.isChecked = true
        }

        privacy_back.setOnClickListener {
            val intent = Intent(this,PolicyAndTermsScreen::class.java)
            startActivity(intent)
        }


        agree_button_privacy.setOnClickListener {
            if(radio_privacy.isChecked)
            {
                PrefsHelper().savePref("privatepolicy",1)
                val intent = Intent(this,PolicyAndTermsScreen::class.java)
                startActivity(intent)
            }
            else{
                Toast.makeText(this,"Please accept the Privacy Policy to proceed", Toast.LENGTH_LONG).show()
            }
        }

    }
    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this,PolicyAndTermsScreen::class.java)
        startActivity(intent)
    }
}
