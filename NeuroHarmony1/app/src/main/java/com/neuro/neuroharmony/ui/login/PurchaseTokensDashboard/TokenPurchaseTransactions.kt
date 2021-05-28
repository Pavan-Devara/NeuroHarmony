package com.neuro.neuroharmony.ui.login.PurchaseTokensDashboard

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.neuro.neuroharmony.R
import com.neuro.neuroharmony.ui.login.BaseActivity
import com.neuro.neuroharmony.ui.login.HomePage1
import com.neuro.neuroharmony.ui.login.PaymentFlow.GetPackagesViewModel
import com.neuro.neuroharmony.ui.login.ResourceStatus
import com.neuro.neuroharmony.ui.login.StatusType
import com.neuro.neuroharmony.utils.CommonUtils
import com.neuro.neuroharmony.utils.PrefsHelper
import kotlinx.android.synthetic.main.activity_token_purchase_transactions.*
import kotlinx.android.synthetic.main.fix_as_defalut_test.*

class TokenPurchaseTransactions : BaseActivity() {

    private lateinit var viewModelUserPackages: GetPackagesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_token_purchase_transactions)

        viewModelUserPackages = ViewModelProviders.of(this)[GetPackagesViewModel::class.java]

        viewModelUserPackages.getuserpackagesvm()
        setupObserversUserPackages()

        back_transactions.setOnClickListener {
            onBackPressed()
        }

        if (PrefsHelper().getPref<Int>("userType")==2){
            relative_availabletokens.visibility= View.GONE
        }

    }



    @SuppressLint("WrongConstant")
    private fun setupObserversUserPackages() {
        viewModelUserPackages.getuserpackagesResponseLiveData.observe(this, Observer {
            if (it!=null){
                if (it.message=="Success"){
                    available_tokens_number.text = it.data.available_neuro_tokens.toString()
                    val bundle = Bundle()
                    bundle.putSerializable("mydata", it)
                    val viewPager: ViewPager = findViewById(R.id.transactions_viewPager)
                    val adapter = TokenTransactionViewPager(supportFragmentManager, bundle)
                    viewPager.adapter = adapter

                    val tabLayout = findViewById<TabLayout>(R.id.transactions_tabs)
                    tabLayout.setupWithViewPager(viewPager)

                }else{
                    Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                }
            }
        })
        viewModelUserPackages.getuserpackagesAPICallStatus.observe(this, Observer {
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
                Toast.makeText(this, "Please try again", Toast.LENGTH_SHORT).show()
                dismissDialog()


            }
            StatusType.LOADING_MORE -> {
                Toast.makeText(this, "Loading more", Toast.LENGTH_SHORT).show()
            }
            StatusType.NO_NETWORK -> {
                Toast.makeText(this, "Please check internet connection", Toast.LENGTH_SHORT).show()
            }
            StatusType.SESSION_EXPIRED -> {
                Toast.makeText(this, "Session Expired", Toast.LENGTH_SHORT).show()
            }
        }

    }

    override fun onBackPressed() {
        val intent = Intent(this, HomePage1::class.java)
        startActivity(intent)
    }
}


data class Transaction_details(
    val date: String,
    val remarks: String,
    val debit: Int,
    val credit: Int
)
