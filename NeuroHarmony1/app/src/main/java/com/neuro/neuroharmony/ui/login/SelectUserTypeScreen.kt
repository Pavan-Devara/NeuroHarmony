package com.neuro.neuroharmony.ui.login


import android.content.Intent
import android.os.Bundle
import android.widget.RadioButton
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.neuro.neuroharmony.R
import com.neuro.neuroharmony.ui.login.AfilliateWorkFlow.AffiliateForms
import com.neuro.neuroharmony.utils.CommonUtils
import com.neuro.neuroharmony.utils.PrefsHelper
import kotlinx.android.synthetic.main.activity_select__user__type__screen.*

class SelectUserTypeScreen :BaseActivity() {
    var mobile_var= ""
    var email_var = ""
    private lateinit var viewModel: UsertypeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select__user__type__screen)





        viewModel = ViewModelProviders.of(this)[UsertypeViewModel::class.java]

        var selected_ans = 0
        if(PrefsHelper().isPrefExists("userType")){
            selected_ans = PrefsHelper().getPref<Int>("userType")
            if (selected_ans == 1){
                individual_select_user_type.isChecked = true
            }
            if (selected_ans == 2){
                couple_select_user_type.isChecked = true
            }
            if (selected_ans == 3){
                NH_affiliate_select_user_type.isChecked = true
            }
        }
        setupListeners(selected_ans)
    }


    /**
     * Write app UI control listeners here
     */
    private fun setupListeners(selected_user_type: Int) {
        var rb1 = findViewById<RadioButton>(R.id.individual_select_user_type)
        var rb2 = findViewById<RadioButton>(R.id.couple_select_user_type)
        var rb3 = findViewById<RadioButton>(R.id.NH_affiliate_select_user_type)

        var selected_user_type =selected_user_type;
        individual_select_user_type.setOnClickListener {
            selected_user_type=1
            rb2.isChecked = false
            rb3.isChecked = false

        }

        couple_select_user_type.setOnClickListener {
            selected_user_type=2
            rb1.isChecked = false
            rb3.isChecked = false

        }

        NH_affiliate_select_user_type.setOnClickListener {
            selected_user_type=3
            rb1.isChecked = false
            rb2.isChecked = false

        }

        submit_select_user_type.setOnClickListener {
            setupObservers(selected_user_type)
            if(selected_user_type==0){
                Toast.makeText(applicationContext, "Please select the user type", Toast.LENGTH_LONG).show()
            }else {
                viewModel.usertype(selected_user_type,referral_mobile.text.trim().toString())
            }
        }
    }

    /**
     * Write all LiveData observers in this method
     */
    private  fun setupObservers(selected_user_type:Int){
        viewModel.loginResponseLiveData.observe(this, Observer {
            if(it!=null){
                if(it.message=="Success" && it.isSuccess== true){
                    val user_key=it.data.userKey.toString()
                    PrefsHelper().savePref("userType",selected_user_type)
                    PrefsHelper().savePref("Account_Status",9)
                    PrefsHelper().savePref("userKey",user_key)
                    PrefsHelper().savePref("Token",it.token)
                    PrefsHelper().savePref("Flag_for_edit_signup",0)
                    PrefsHelper().savePref("termsandcondition",0)
                    PrefsHelper().savePref("privatepolicy",0)
                    if(selected_user_type==1) {

                        val intent = Intent(this, PolicyAndTermsScreen::class.java )
                        intent.putExtra("Mobile",mobile_var)
                        intent.putExtra("Gmail",email_var)
                        startActivity(intent)
                        //  viewModel.usertype(user_type)
                    }
                    if(selected_user_type==2) {
                        val intent = Intent(this, PolicyAndTermsScreen::class.java )
                        intent.putExtra("Mobile",mobile_var)
                        intent.putExtra("Gmail",email_var)
                        startActivity(intent)
                    }
                    if(selected_user_type==3) {
                        PrefsHelper().savePref("Account_Status",3)
                        val intent = Intent(this, AffiliateForms::class.java )
                        intent.putExtra("Mobile",mobile_var)
                        intent.putExtra("Gmail",email_var)
                        startActivity(intent)
                    }
                }
                else{
                    val intent = Intent(this, SelectUserTypeScreen::class.java )

                    Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                    startActivity(intent)

                }
            }

        })


        //observe API call status
        viewModel.loginAPICallStatus.observe(this, Observer {
            processStatus(it)
        })
    }
    override fun onBackPressed() {

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
                CommonUtils().showSnackbar(submit_select_user_type.rootView,"Please try again")
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
