package com.neuro.neuroharmony.ui.login.PaymentFlow

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.neuro.neuroharmony.R
import com.neuro.neuroharmony.ui.login.*
import com.neuro.neuroharmony.utils.CommonUtils
import com.neuro.neuroharmony.utils.PrefsHelper
import kotlinx.android.synthetic.main.activity_home_page1.*
import kotlinx.android.synthetic.main.activity_payment_packages_screen.*
import kotlinx.android.synthetic.main.activity_select__user__type__screen.*

class PaymentPackagesScreen : BaseActivity() {

    private lateinit var viewModelPackages: GetPackagesViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment_packages_screen)




        back_select_package.setOnClickListener {
            onBackPressed()
        }

        viewModelPackages = ViewModelProviders.of(this)[GetPackagesViewModel::class.java]



        viewModelPackages.getpackagesvm()


        setupObservers6()
    }

    data class  PackageDetails(
        val packagedetails: MutableMap<String, Any>
    )


    @SuppressLint("WrongConstant")
    private  fun setupObservers6() {
        val recyclerView = findViewById(R.id.packages_list_recycler_view) as RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)

        val packages = ArrayList<PaymentPackagesScreen.PackageDetails>()

        viewModelPackages.getpackagesResponseLiveData.observe(this, Observer {
            if(it!=null){
                if( it.message=="Success"){
                    for (i in 0 until it.data.size){
                        val userdetails = mutableMapOf<String, Any>()
                        userdetails.put("title" ,it.data[i].title)
                        userdetails.put("name", it.data[i].name)
                        userdetails.put("package_pic", it.data[i].image)
                        userdetails.put("Description", it.data[i].description)
                        userdetails.put("ActualPrice", it.data[i].actualPrice)
                        userdetails.put("OfferPrice", it.data[i].offerPrice)
                        userdetails.put("discount_price",it.data[i].DiscountPrice)
                        userdetails.put("GST",it.data[i].gst)
                        userdetails.put("rack_price",it.data[i].rackPrice)
                        userdetails.put("purchase_id",it.data[i].id)

                        packages.add(PaymentPackagesScreen.PackageDetails(userdetails))
                    }


                    val adapter_recycler1 = PaymentPackagesRecycleAdapter(packages,this)
                    recyclerView.adapter = adapter_recycler1
                    adapter_recycler1.notifyDataSetChanged()
                }
            }
        })
        //observe API call status
        viewModelPackages.getpackagesAPICallStatus.observe(this, Observer {
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
