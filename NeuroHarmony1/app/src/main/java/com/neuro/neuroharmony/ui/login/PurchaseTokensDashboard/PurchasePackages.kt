package com.neuro.neuroharmony.ui.login.PurchaseTokensDashboard


import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.neuro.neuroharmony.R
import com.neuro.neuroharmony.data.model.AllSessionOfUserResponse
import com.neuro.neuroharmony.data.model.PaymentPackages.UserPurchasedPackagesResponse
import com.neuro.neuroharmony.ui.login.PaymentFlow.GetPackagesViewModel

/**
 * A simple [Fragment] subclass.
 */
class PurchasePackages(val bundle: Bundle) : Fragment() {


    @SuppressLint("WrongConstant")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_purchase_packages, container, false)

        // Inflate the layout for this fragment
        return view
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupObserversUserPackages()
    }



    @SuppressLint("WrongConstant")
    private fun setupObserversUserPackages() {
        val recyclerView = view!!.findViewById(R.id.purchase_pakages_transaction_recycler_view) as RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(activity, LinearLayout.VERTICAL, false)

        val packageDetails = ArrayList<PurchasedPakages>()
        val it: UserPurchasedPackagesResponse? = bundle!!.getSerializable("mydata") as UserPurchasedPackagesResponse?
        if (it!=null){
            if (it.message=="Success"){
                for (i in 0 until it.data.purchase_list.size){
                    val packagedetails = mutableMapOf<String, Any>()
                    packagedetails.put("name" ,it.data.purchase_list[i].name)
                    packagedetails.put("description", it.data.purchase_list[i].description)
                    packagedetails.put("image", it.data.purchase_list[i].image)
                    packagedetails.put("created_date", it.data.purchase_list[i].created_date)
                    packagedetails.put("actual_price", it.data.purchase_list[i].actual_price)
                    packagedetails.put("offer_price", it.data.purchase_list[i].offer_price)
                    packagedetails.put("gst", it.data.purchase_list[i].gst)
                    packagedetails.put("plan_type", it.data.purchase_list[i].plan_type)
                    packagedetails.put("rack_price", it.data.purchase_list[i].rack_price)
                    packagedetails.put("discount", it.data.purchase_list[i].discount)
                    packagedetails.put("neuro_tokens", it.data.purchase_list[i].neuro_tokens)
                    packagedetails.put("is_eeg", it.data.purchase_list[i].is_eeg)
                    packagedetails.put("id", it.data.purchase_list[i].id)
                    packagedetails.put("payment_status",it.data.purchase_list[i].payment_status)

                    packageDetails.add(PurchasedPakages(packagedetails))
                }
            val adapter_recycler = PurchasePackageRecyclerAdapter(packageDetails, activity)
            recyclerView.adapter = adapter_recycler
            adapter_recycler.notifyDataSetChanged()
            }else{
                Toast.makeText(activity, it.message, Toast.LENGTH_SHORT).show()
            }
        }
    }



    data class  PurchasedPakages(
        val purchasedPackages: MutableMap<String, Any>
    )
}
