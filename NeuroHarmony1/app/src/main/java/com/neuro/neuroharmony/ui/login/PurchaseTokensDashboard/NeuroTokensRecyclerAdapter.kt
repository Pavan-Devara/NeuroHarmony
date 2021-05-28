package com.neuro.neuroharmony.ui.login.PurchaseTokensDashboard

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.neuro.neuroharmony.R
import java.text.SimpleDateFormat

class NeuroTokensRecyclerAdapter(
    val transaction: ArrayList<PurchasePackages.PurchasedPakages>
) :
    RecyclerView.Adapter<NeuroTokensRecyclerAdapter.ViewHolder>() {


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val date = itemView.findViewById<TextView>(R.id.created_date_package_transaction)
        val remarks = itemView.findViewById<TextView>(R.id.package_transaction_remarks)
        val debit = itemView.findViewById<TextView>(R.id.pacakage_token_debits)
        val credit = itemView.findViewById<TextView>(R.id.pacakage_token_credits)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.neuro_token_transaction_recycler_view, parent, false )
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return transaction.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val details = transaction[position]
        holder.setIsRecyclable(false)

        val parser = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
        val formatter = SimpleDateFormat("dd MMM yyyy")
        val output: String = formatter.format(parser.parse(details.purchasedPackages["created_date"].toString()))
        holder.date.text = output
        holder.remarks.text = details.purchasedPackages["description"].toString()

        if (details.purchasedPackages["transaction_type"].toString()=="Cr"){
            holder.credit.text=details.purchasedPackages["neuro_tokens"].toString()
        }else if (details.purchasedPackages["transaction_type"].toString()=="Dr"){
            holder.debit.text=details.purchasedPackages["neuro_tokens"].toString()
        }

    }

}