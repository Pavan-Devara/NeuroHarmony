package com.neuro.neuroharmony.ui.login.AfilliateWorkFlow

import android.content.Intent
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.neuro.neuroharmony.R
import com.neuro.neuroharmony.ui.login.CircleTransform
import com.neuro.neuroharmony.ui.login.PaymentFlow.PaymentPackagesRecycleAdapter
import com.neuro.neuroharmony.ui.login.AfilliateWorkFlow.AffiliateDashboard
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat

class AffiliateDashboardAdapterRecycler (

    val packages: ArrayList<AffiliateDashboard.PackageDetails>,
    val paymentPackagesScreen: AffiliateDashboard
    ):
    RecyclerView.Adapter<AffiliateDashboardAdapterRecycler.ViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val v = LayoutInflater.from(parent.context).inflate(R.layout.affiliate_recycleview_transactions, parent, false )
            return AffiliateDashboardAdapterRecycler.ViewHolder(v)
        }

        override fun getItemCount(): Int {
            return  packages.size
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val package_payment: AffiliateDashboard.PackageDetails = packages[position]
            holder.setIsRecyclable(false)




            holder.run {
                name_affiliater.text= package_payment.packagedetails["referal_name"].toString()

            }


            if (package_payment.packagedetails["date_created"].toString()!="null") {
                val parser = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                val formatter = SimpleDateFormat("dd MMM yyyy")
                val output: String = formatter.format(parser.parse(package_payment.packagedetails["date_created"].toString()))
                holder.created_date.text = output
            }else{
                holder.created_date.text = "NA"
            }

            if (package_payment.packagedetails["credited_date_created"].toString()!="null") {
                val parser = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                val formatter = SimpleDateFormat("dd MMM yyyy")
                val output: String = formatter.format(parser.parse(package_payment.packagedetails["credited_date_created"].toString()))
                holder.credited_date.text = output
            }else{
                holder.credited_date.text = "NA"
            }


            holder.run {
                ammount.text= package_payment.packagedetails["credited_ammount"].toString()
            }



                holder.run {

                    pending_ammount.text=package_payment.packagedetails["Status_key"].toString()

                    }



        }

        class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
            val name_affiliater = itemView.findViewById<TextView>(R.id.referar_name)
            val created_date = itemView.findViewById<TextView>(R.id.created_date_referer)
            val credited_date = itemView.findViewById<TextView>(R.id.credited_date_referer)
            val ammount = itemView.findViewById<TextView>(R.id.ammount_credited_affiliate)
            val credited_ammount_status = itemView.findViewById<TextView>(R.id.credited_ammount_referal)
            val pending_ammount = itemView.findViewById<TextView>(R.id.pending_ammount)

        }
}