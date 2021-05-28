package com.neuro.neuroharmony.ui.login

import android.content.Intent
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.neuro.neuroharmony.R
import com.neuro.neuroharmony.utils.CommonUtils
import com.google.gson.Gson
import com.neuro.neuroharmony.ui.login.PaymentFlow.SubmitSelectedPackageViewModel
import com.neuro.neuroharmony.ui.login.SocialProfile.ReligiousInfoVersion2
import com.neuro.neuroharmony.utils.PrefsHelper
import kotlinx.android.synthetic.main.activity_transaction_details_screen.*

class TransactionDetailsScreen : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transaction_details_screen)



        var available_token = PrefsHelper().getPref<Int>("available_token")
        var added_token = PrefsHelper().getPref<Int>("added_token")

        textView11.text = available_token.toString()
        textView10.text = added_token.toString() + " " +"neuro tokens successfully added"




        button2.setOnClickListener{

            val prefs = PreferenceManager.getDefaultSharedPreferences(this)
            if (prefs.contains("Reference")) {
                if (PrefsHelper().getPref<Int>("Reference") == 8) {
                    val intent = Intent(this, HomePage1::class.java)
                    startActivity(intent)

                } else {

                    val intent = Intent(this, ReligiousInfoVersion2::class.java)
                    startActivity(intent)


                }
            }
            else{
                val intent = Intent(this, ReligiousInfoVersion2::class.java)
                startActivity(intent)
            }

        }
    }

    override fun onBackPressed() {

    }




}
