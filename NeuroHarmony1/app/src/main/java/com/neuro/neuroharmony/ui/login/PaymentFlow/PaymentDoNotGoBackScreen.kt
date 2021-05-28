package com.neuro.neuroharmony.ui.login.PaymentFlow

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.neuro.neuroharmony.R
import com.neuro.neuroharmony.ui.login.*
import com.neuro.neuroharmony.ui.login.SocialProfile.ReligiousInfoVersion2
import com.neuro.neuroharmony.utils.CommonUtils
import com.neuro.neuroharmony.utils.PrefsHelper
import kotlinx.android.synthetic.main.activity_make_payment_screen.*

class PaymentDoNotGoBackScreen : BaseActivity() {
    private lateinit var viewModel: SubmitSelectedPackageViewModel

    var razor_pay_id =""
    var razor_order_id=""
    var razor_signature_id=""
    var count = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment_do_not_go_back_screen)

        razor_order_id = intent.getStringExtra("razorpay_order_id")
        razor_pay_id=intent.getStringExtra("razorpay_pay_id")
        razor_signature_id=intent.getStringExtra("razorpay_signature_id")
        viewModel = ViewModelProviders.of(this)[SubmitSelectedPackageViewModel::class.java]


        viewModel.checkoutidsvm(razor_order_id,razor_pay_id,razor_signature_id)
        setupObservers1()
    }

    private fun setupObservers1() {
        viewModel.loginResponseLiveData.observe(this, Observer {
            if (it != null) {

                if (it.status==200){
                    if (it.message=="Success"){
                        PrefsHelper().savePref("available_token", it.data.userNeuroTokens)
                        PrefsHelper().savePref("added_token", it.data.packageNeuroTokens)

                        val intent = Intent(this, TransactionDetailsScreen::class.java)
                        startActivity(intent)
                    }
                    else{

                        Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                        val intent = Intent(this, PaymentPackagesScreen::class.java)
                        startActivity(intent)

                    }


                }
                else{
                    count = adder(count)
                    if (count<3){
                        viewModel.checkoutidsvm(razor_order_id,razor_pay_id,razor_signature_id)
                    }
                    else{
                        val intent = Intent(this, PaymentPackagesScreen::class.java)
                        startActivity(intent)
                    }

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
                //CommonUtils().showSnackbar(button_continue_payment.rootView,"Please try again")
                //dismissDialog()


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

    private fun adder(add :Int): Int {
        val add = add +1
        return add

    }
}
