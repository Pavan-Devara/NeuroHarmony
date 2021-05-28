package com.neuro.neuroharmony.ui.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.gson.Gson
import com.neuro.neuroharmony.R
import com.neuro.neuroharmony.utils.CommonUtils
import com.neuro.neuroharmony.utils.PrefsHelper
import kotlinx.android.synthetic.main.activity_policy_and_terms_screen.*
import kotlinx.android.synthetic.main.activity_terms_and_conditions.*

class PolicyAndTermsScreen : BaseActivity() {





    private lateinit var viewModel: TermsAndPrivacyViewModel
    private lateinit var viewModel1: TermsAndPrivacyViewModel

    var gson = Gson()
    var flag = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_policy_and_terms_screen)


        viewModel = ViewModelProviders.of(this)[TermsAndPrivacyViewModel::class.java]
        viewModel1 = ViewModelProviders.of(this)[TermsAndPrivacyViewModel::class.java]
        var termsandcondition = PrefsHelper().getPref<Int>("termsandcondition")
        var privatepolicy =PrefsHelper().getPref<Int>("privatepolicy")
        setupObservers1()
        setupObservers(termsandcondition,privatepolicy)
        terms_of_use_button.setOnClickListener {
            flag =1

            viewModel.termsvm()
        }

        privacy_policy.setOnClickListener {
            flag = 2

            viewModel.termsvm()
        }


        verify.setOnClickListener {
            if (termsandcondition == 1) {
                if(privatepolicy ==1) {
                    val intent = Intent(this, BioUpdateActivity::class.java)
                    PrefsHelper().savePref("Account_Status", 3)
                    startActivity(intent)
                }
                else{
                    Toast.makeText(this,"Please agree the Private policy to proceed. Click on the Private policy", Toast.LENGTH_LONG).show()

                }
            }
            else{
                Toast.makeText(this,"Please agree the terms and conditions to proceed. Click on the Terms and Condition", Toast.LENGTH_LONG).show()
            }

        }



    }

    override fun onBackPressed() {

    }





    fun setupObservers(termsandcondition: Int, privatepolicy: Int) {

        this?.let {

            viewModel.loginResponseLiveData.observe(this, Observer {
                if (it != null) {
                    if (it.message == "Success") {



                        if(flag==1){
                            PrefsHelper().savePref("termsandcondition",termsandcondition)
                            val intent  = Intent(this,TermsAndConditions::class.java)
                            intent.putExtra("Terms",it.data.termsConditions.content)


                            startActivity(intent)
                        }
                        else if(flag==2){
                            PrefsHelper().savePref("privatepolicy",privatepolicy)
                            val intent  = Intent(this,PrivacyPolicy::class.java)
                            intent.putExtra("Policies",it.data.privacyPolicy.content)


                            startActivity(intent)
                        }


                    }
                }

            })
        }


        //observe API call status
        this?.let {
            viewModel.loginAPICallStatus.observe(this, Observer {
                processStatus(it)
            })
        }
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
                CommonUtils().showSnackbar(agree_button.rootView,"Please try again")
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

    fun setupObservers1(){

        this?.let {

            viewModel1.loginResponseLiveData.observe(this, Observer {
                if (it != null) {
                    if (it.message == "Success") {


                        val intent  = Intent(this,PrivacyPolicy::class.java)
                        intent.putExtra("Policies",it.data.privacyPolicy.content)
                        Log.d("Policies",it.data.privacyPolicy.content)
                        startActivity(intent)






                    }
                }

            })
        }


        //observe API call status
        this?.let {
            viewModel1.loginAPICallStatus.observe(this, Observer {
                processStatus1(it)
            })
        }
    }

    private fun processStatus1(resource: ResourceStatus) {

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
                CommonUtils().showSnackbar(privacy_policy.rootView,"Please try again")
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
