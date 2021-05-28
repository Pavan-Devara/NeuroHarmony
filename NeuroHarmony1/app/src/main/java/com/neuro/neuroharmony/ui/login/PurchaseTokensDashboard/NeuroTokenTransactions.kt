package com.neuro.neuroharmony.ui.login.PurchaseTokensDashboard


import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.neuro.neuroharmony.R
import com.neuro.neuroharmony.data.model.PaymentPackages.UserPurchasedPackagesResponse
import kotlinx.android.synthetic.main.fragment_neuro_token_transactions.*

/**
 * A simple [Fragment] subclass.
 */
class NeuroTokenTransactions(val bundle: Bundle) : Fragment() {

    @SuppressLint("WrongConstant")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_neuro_token_transactions, container, false)

        // Inflate the layout for this fragment
        return view
    }



    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val it: UserPurchasedPackagesResponse? = bundle!!.getSerializable("mydata") as UserPurchasedPackagesResponse?
        if (it!!.data.available_neuro_tokens>=0 && it.data.transaction_data.size>0) {
            setupObserversNeuroTokens()
        }else{
            transfer_token_continue.visibility=View.GONE
            token_transaction_history.visibility=View.GONE
            token_calculations_layout.visibility=View.GONE
            balance_tokens_layout.visibility=View.GONE
            transaction_detail_titles_layout.visibility=View.GONE
            transaction_detail_titles_layout_view.visibility=View.GONE
            view_token_calculation_layout.visibility=View.GONE
            balance_tokens_layout_view.visibility=View.GONE
        }

        val bundle: UserPurchasedPackagesResponse? = bundle!!.getSerializable("mydata") as UserPurchasedPackagesResponse?
        val available_tokens = bundle!!.data.available_neuro_tokens
        transfer_token_continue.setOnClickListener {
            val intent = Intent(activity, TransferTokensUsers::class.java)
            intent.putExtra("available_tokens", available_tokens)
            startActivity(intent)
        }
    }



    @SuppressLint("WrongConstant")
    private fun setupObserversNeuroTokens() {
        val recyclerView = view!!.findViewById(R.id.token_transaction_history) as RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(activity, LinearLayout.VERTICAL, false)


        val tokenDetails = ArrayList<PurchasePackages.PurchasedPakages>()
        val it: UserPurchasedPackagesResponse? = bundle!!.getSerializable("mydata") as UserPurchasedPackagesResponse?
        var total_credit = 0
        var total_debit = 0
        if (it!=null){
            if (it.message=="Success"){
                for (i in 0 until it.data.transaction_data.size){
                    val details = mutableMapOf<String, Any>()
                    details.put("neuro_tokens", it.data.transaction_data[i].neuro_tokens)
                    details.put("description", it.data.transaction_data[i].description)
                    details.put("created_date", it.data.transaction_data[i].created_date)
                    details.put("transaction_type", it.data.transaction_data[i].transaction_type)
                    if (it.data.transaction_data[i].transaction_type=="Cr"){
                        total_credit=total_credit+it.data.transaction_data[i].neuro_tokens
                    }else if (it.data.transaction_data[i].transaction_type=="Dr"){
                        total_debit=total_debit+it.data.transaction_data[i].neuro_tokens
                    }

                    tokenDetails.add(PurchasePackages.PurchasedPakages(details))
                }

                val adapter_recycler = NeuroTokensRecyclerAdapter(tokenDetails)
                recyclerView.adapter = adapter_recycler
                adapter_recycler.notifyDataSetChanged()
            }else{
                Toast.makeText(activity, it.message, Toast.LENGTH_SHORT).show()
            }
        }

        val total_credited = view!!.findViewById<TextView>(R.id.credit_tokens_number)
        val total_debited = view!!.findViewById<TextView>(R.id.debit_tokens_number)
        val balance_tokens = view!!.findViewById<TextView>(R.id.balance_tokens_calculation_number)

        total_credited.text = total_credit.toString()
        total_debited.text = total_debit.toString()
        balance_tokens.text = (total_credit-total_debit).toString()
    }

}
