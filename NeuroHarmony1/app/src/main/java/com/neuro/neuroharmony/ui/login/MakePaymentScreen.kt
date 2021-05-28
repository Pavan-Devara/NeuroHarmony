package com.neuro.neuroharmony.ui.login

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.neuro.neuroharmony.R
import com.neuro.neuroharmony.data.model.PaymentPackages.GatewayOrderIdResponse
import com.neuro.neuroharmony.ui.login.PaymentFlow.*
import com.neuro.neuroharmony.utils.CommonUtils
import com.neuro.neuroharmony.utils.PrefsHelper
import com.razorpay.Checkout
import com.razorpay.PaymentData
import com.razorpay.PaymentResultWithDataListener
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_make_payment_screen.*
import org.json.JSONObject

class MakePaymentScreen : BaseActivity(), PaymentResultWithDataListener {
    val TAG:String = MakePaymentScreen::class.toString()
    private lateinit var viewModel: SubmitSelectedPackageViewModel
    private lateinit var viewModel1: GatewayOrderIdViewModel
    private lateinit var viewModel2: ErrorCodeAndDescriptionViewModel
    var Description = ""
    var gateway_order_id =""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_make_payment_screen)
        Checkout.preload(applicationContext)

        Description = intent.getStringExtra("name")
        buy_select_package_back.setOnClickListener {
            onBackPressed()
        }

        var purchase_id = intent.getStringExtra("purchase_id")

        viewModel = ViewModelProviders.of(this)[SubmitSelectedPackageViewModel::class.java]
        viewModel1 = ViewModelProviders.of(this)[GatewayOrderIdViewModel::class.java]
        viewModel2 = ViewModelProviders.of(this)[ErrorCodeAndDescriptionViewModel::class.java]

        //setupObservers2()
        if (intent.getStringExtra("package_pic") != "") {
            Picasso.get()
                .load(intent.getStringExtra("package_pic"))
                .resize(75, 75)
                .transform(CircleTransform())
                .placeholder(R.mipmap.profile_pic_placeholder)
                .centerCrop()
                .into(package_buy_image)
        }

        val gst_format =  intent.getStringExtra("GST").toDouble()
        val gst_format_s = "%.2f".format(gst_format)

        val actual_cost_format = intent.getStringExtra("rack_price").toDouble()
        val actual_cost_format_s = "%.2f".format(actual_cost_format)

        val total_price_format = intent.getStringExtra("OfferPrice").toDouble()
        val total_price_format_s = "%.2f".format(total_price_format)

        val actual_discount_format = intent.getStringExtra("discount_price").toDouble()
        val actual_discount_format_s = "%.2f".format(actual_discount_format)


        gst_actual.text = "₹" + gst_format_s
        cost_actual.text = "₹" + actual_cost_format_s
        total_price.text= "₹" + total_price_format_s
        discount_actual.text ="₹" + actual_discount_format_s

        type_description_package.text = intent.getStringExtra("name")

        button_continue_payment.setOnClickListener {
            viewModel1.gatewayidorderid(purchase_id)

        }

        setupObservers()
        setupObservers2()

        //setupObservers1()
    }


    private  fun setupObservers(){
        viewModel1.loginResponseLiveData.observe(this, Observer {
            if(it!=null){
                if(it.message=="Success"){

                    /*PrefsHelper().savePref("available_token",it.data.userNeuroTokens)
                    PrefsHelper().savePref("added_token",it.data.packageNeuroTokens)
                    val intent = Intent(this, TransactionDetailsScreen::class.java)
                    startActivity(intent)*/
                    gateway_order_id = it.data.gatewayOrderId

                    startPayment(it)

                }else{
                    Toast.makeText(applicationContext, it.message, Toast.LENGTH_LONG).show()
                }
            }
        })


        //observe API call status
        viewModel1.loginAPICallStatus.observe(this, Observer {
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
                CommonUtils().showSnackbar(button_continue_payment.rootView,"Please try again")
                dismissDialog()


            }
            StatusType.LOADING_MORE -> {
                CommonUtils().showSnackbar(button_continue_payment.rootView, "Loading more..")
            }
            StatusType.NO_NETWORK -> {
                Toast.makeText(this, "Please check internet connection", Toast.LENGTH_SHORT).show()
            }
            StatusType.SESSION_EXPIRED -> {
                //CommonUtils().showSnackbar(login_button_login.rootView,"session expired")
            }
        }
    }

    private fun startPayment(it: GatewayOrderIdResponse) {
        /*
        *  You need to pass current activity in order to let Razorpay create CheckoutActivity
        * */

        val activity: Activity = this
        val co = Checkout()
        //co.setKeyID("rzp_test_q36B31vR2jvsig")
        val  image = R.mipmap.neuro_harmony_logo
        co.setImage(image)





        try {
            val options = JSONObject()

            options.put("name","Neuro Harmony")
            options.put("description",Description)
            //You can omit the image option to fetch the image from dashboard
            options.put("currency","INR")
            //options.put("amount","5000")
            options.put("order_id",it.data.gatewayOrderId);
            options.put("theme", JSONObject("{color: '#3C1A5B'}"))
            val prefill = JSONObject()
            prefill.put("email",PrefsHelper().getPref<String>("Gmail"))
            prefill.put("contact",PrefsHelper().getPref<String>("Mobile"))
            options.put("prefill",prefill)
            co.open(activity,options)

        }
        catch (e: Exception){
            Toast.makeText(activity,"Error in payment: "+ e.message,Toast.LENGTH_LONG).show()
            e.printStackTrace()
        }
    }



    override fun onPaymentSuccess(razorpayPaymentId: String?, p1: PaymentData?) {

        try{
            if (p1 != null) {
                //viewModel.checkoutidsvm(p1.orderId,p1.paymentId,p1.signature)
                val prefs = PreferenceManager.getDefaultSharedPreferences(this)
                if (prefs.contains("Reference")) {
                    if (PrefsHelper().getPref<Int>("Reference") != 8) {
                        PrefsHelper().savePref("Account_Status",12)

                    }
                }
                val intent = Intent(this, PaymentDoNotGoBackScreen::class.java)
                intent.putExtra("razorpay_order_id",p1.orderId)
                intent.putExtra("razorpay_pay_id",p1.paymentId)
                intent.putExtra("razorpay_signature_id",p1.signature)
                startActivity(intent)

                Log.d("Order_id",p1.orderId)
                Log.d("Order_id",p1.paymentId)
                Log.d("Order_id",p1.signature)


            }
            Log.d("RESPONSE", p1.toString())
            Toast.makeText(this,"Payment Success",Toast.LENGTH_SHORT).show()



        }

        catch (e: Exception){

            Log.e(TAG,"Exception in onPaymentSuccess", e)

        }


    }


    override fun onPaymentError(errorCode: Int, errorDescription: String?, p2: PaymentData?) {
        try{

            //Log.d("RESPONSE",errorDescription)
            Log.d("error_code",errorCode.toString())
            Log.d("response",errorDescription)
            Toast.makeText(this, " Payment failed",Toast.LENGTH_LONG).show()
            if (errorDescription != null) {
                viewModel2.logerrorpaymentvm(errorCode,errorDescription,gateway_order_id )
            }


        }catch (e: Exception){
            Log.e(TAG,"Exception in onPaymentError", e)
        }
    }

    private  fun setupObservers2(){
        viewModel2.loginResponseLiveData.observe(this, Observer {
            if(it!=null){
                if(it.message=="Success"){

                    /*PrefsHelper().savePref("available_token",it.data.userNeuroTokens)
                    PrefsHelper().savePref("added_token",it.data.packageNeuroTokens)
                    */
                    //val intent = Intent(this, PaymentPackagesScreen::class.java)
                    //startActivity(intent)


                }else{
                    Toast.makeText(applicationContext, it.message, Toast.LENGTH_LONG).show()
                }
            }
        })


        //observe API call status
        viewModel2.loginAPICallStatus.observe(this, Observer {
            processStatus2(it)
        })
    }

    private fun processStatus2(resource: ResourceStatus) {

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
                Toast.makeText(this, "Please try again. Server error", Toast.LENGTH_LONG).show()
                dismissDialog()


            }
            StatusType.LOADING_MORE -> {
                //CommonUtils().showSnackbar(button_continue_payment.rootView, "Loading more..")
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
