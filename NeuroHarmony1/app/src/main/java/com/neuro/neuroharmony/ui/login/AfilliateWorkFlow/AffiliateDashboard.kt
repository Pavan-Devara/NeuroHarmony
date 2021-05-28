package com.neuro.neuroharmony.ui.login.AfilliateWorkFlow

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.neuro.neuroharmony.FullProfilePic
import com.neuro.neuroharmony.R
import com.neuro.neuroharmony.ui.login.BaseActivity
import com.neuro.neuroharmony.ui.login.CircleTransform
import com.neuro.neuroharmony.ui.login.PaymentFlow.PaymentPackagesRecycleAdapter
import com.neuro.neuroharmony.ui.login.PaymentFlow.PaymentPackagesScreen
import com.neuro.neuroharmony.ui.login.ResourceStatus
import com.neuro.neuroharmony.ui.login.StatusType
import com.neuro.neuroharmony.utils.PrefsHelper
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_affiliate_dashboard.*
import kotlinx.android.synthetic.main.activity_matched_user_neuro_desire_graph_result_screen.*

class AffiliateDashboard : BaseActivity() {

    private lateinit var viewModel: AffiliateTransactionsViewModel
    var self_profile_pic = ""
    @SuppressLint("WrongConstant")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_affiliate_dashboard)




        terms_affiliate_transactions.setOnClickListener {
            super.onBackPressed()
        }



        viewModel = ViewModelProviders.of(this)[AffiliateTransactionsViewModel::class.java]
        viewModel.getaffiliatetransactionsvm()
        setupObservers6()


        affiliater_image.setOnClickListener{
            if (self_profile_pic != "") {
                val intent = Intent(this, FullProfilePic::class.java)
                intent.putExtra(
                    "profile_pic_url",
                    self_profile_pic
                )
                   startActivity(intent)
            }
            else{
                Toast.makeText(this
                    , "This user hasn't uploaded the profile picture", Toast.LENGTH_LONG).show()
            }
        }


    }

    data class PackageDetails(
        val packagedetails: MutableMap<String, Any>
    )


    @SuppressLint("WrongConstant")
    private fun setupObservers6() {
        val recyclerView = findViewById(R.id.affliate_list_recycler_view) as RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)
        var nothing_here = findViewById(R.id.nothinghere) as TextView
        val packages = ArrayList<AffiliateDashboard.PackageDetails>()



        viewModel.getAffiliateTransactionsResponseLiveData.observe(this, Observer {
            if (it != null) {
                if (it.message == "Success") {

                    self_profile_pic = it.data.profilePic
                    if (self_profile_pic!=""){
                    Picasso.get()
                        .load(self_profile_pic)
                        .resize(120,120)
                        .transform(CircleTransform())
                        .centerCrop()
                        .into(affiliater_image)
                    }

                    total_ammount_affiliate.text = "₹ "+it.data.creditedAmount
                    total_ammount_affiliate_due.text ="₹ "+it.data.dueAmount

                    name_affiliater.text = it.data.firstName+" "+ it.data.lastName

                    if(it.data.orgDetails==null){
                        affilater_id.text = ""
                    }
                    else{
                        affilater_id.text = it.data.orgDetails.name

                    }

                    if (it.data.transactionList.isEmpty()){
                        recyclerView.setVisibility(View.GONE);
                        linear_headings_affiliate.visibility= View.GONE
                        nothing_here.setVisibility(View.VISIBLE);
                    }
                    else{
                        for (i in 0 until it.data.transactionList.size) {
                            val userdetails = mutableMapOf<String, Any>()
                            userdetails.put("referal_name" ,it.data.transactionList[i].referralName)
                            userdetails.put("payment_status" ,it.data.transactionList[i].paymentStatus)
                            userdetails.put("date_created" ,it.data.transactionList[i].createdDate)
                            userdetails.put("credited_ammount" ,it.data.transactionList[i].paymentAmount)
                            userdetails.put("credited_date_created",it.data.transactionList[i].gateway_payout.payout_date)
                            userdetails.put("Status_key",it.data.transactionList[i].gateway_payout.payout_status_name)

                            packages.add(AffiliateDashboard.PackageDetails(userdetails))
                        }


                        val adapter_recycler1 = AffiliateDashboardAdapterRecycler(packages, this)
                        recyclerView.adapter = adapter_recycler1
                        adapter_recycler1.notifyDataSetChanged()
                    }



                }
            }
        })
        //observe API call status
        viewModel.getAffiliateTransactionsAPICallStatus.observe(this, Observer {
            processStatuspackages(it)
        })


    }

    private fun processStatuspackages(resource: ResourceStatus) {

        when (resource?.status) {
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
                // CommonUtils().showSnackbar(binding.root, "Loading more..")
            }
            StatusType.NO_NETWORK -> {
                Toast.makeText(this, "Please check internet connection", Toast.LENGTH_LONG).show()
            }
            StatusType.SESSION_EXPIRED -> {
                //CommonUtils().showSnackbar(find_neuro_match_button.rootView,"session expired")
            }
        }


    }
}
