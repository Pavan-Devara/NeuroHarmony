package com.neuro.neuroharmony.ui.login.PaymentFlow

import android.content.Intent
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.neuro.neuroharmony.R
import com.neuro.neuroharmony.ui.login.CircleTransform
import com.neuro.neuroharmony.ui.login.MakePaymentScreen
import com.squareup.picasso.Picasso
import java.math.RoundingMode
import java.text.DecimalFormat
import kotlin.collections.ArrayList

class PaymentPackagesRecycleAdapter(
    val packages: ArrayList<PaymentPackagesScreen.PackageDetails>,
    val paymentPackagesScreen: PaymentPackagesScreen
):
    RecyclerView.Adapter<PaymentPackagesRecycleAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.packages_list_recycler_view, parent, false )
        return PaymentPackagesRecycleAdapter.ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return  packages.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val package_payment: PaymentPackagesScreen.PackageDetails = packages[position]
        holder.setIsRecyclable(false)


        holder.run {
            name_package.text = package_payment.packagedetails["name"].toString()
        }

        if (package_payment.packagedetails["package_pic"].toString() != "") {
            Picasso.get()
                .load(package_payment.packagedetails["package_pic"].toString())
                .resize(80, 80)
                .transform(CircleTransform())
                .placeholder(R.mipmap.profile_pic_placeholder)
                .centerCrop()
                .into(holder.package_pic)
        }
        holder.run {
            val actual_price_format = package_payment.packagedetails["ActualPrice"]
            val actual_price_format_s = "%.2f".format(actual_price_format)
            actual_price.text= "₹"+actual_price_format_s
            actual_price.paintFlags= actual_price.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
        }
        holder.run {
            val offer_price_format = package_payment.packagedetails["OfferPrice"]
            val offer_price_format_s = "%.2f".format(offer_price_format)

            offer_price.text= "₹"+offer_price_format_s

        }
        holder.run {
            package_description.text= package_payment.packagedetails["Description"].toString()
        }
        holder.run {
            title_package.text= package_payment.packagedetails["title"].toString()
        }
        holder.relativeLayout_package.setOnClickListener {
            val intent = Intent(paymentPackagesScreen,MakePaymentScreen::class.java)
            val gst = package_payment.packagedetails["GST"].toString()
            val rack_price = package_payment.packagedetails["rack_price"].toString()
            val total_price= package_payment.packagedetails["OfferPrice"].toString()
            val package_pic = package_payment.packagedetails["package_pic"].toString()
            val name_packagee = package_payment.packagedetails["name"].toString()
            val purchase_id = package_payment.packagedetails["purchase_id"].toString()
            val discount_price = package_payment.packagedetails["discount_price"].toString()

            intent.putExtra("Description",package_payment.packagedetails["Description"].toString())
            intent.putExtra("purchase_id",purchase_id)
            intent.putExtra("name",name_packagee)
            intent.putExtra("package_pic",package_pic)
            intent.putExtra("OfferPrice",total_price)
            intent.putExtra("rack_price",rack_price)
            intent.putExtra("GST",gst)
            intent.putExtra("discount_price",discount_price)
            startActivity(paymentPackagesScreen,intent,null)
        }

    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val package_pic = itemView.findViewById<ImageView>(R.id.image_view_package)
        val relativeLayout_package = itemView.findViewById<RelativeLayout>(R.id.relative_package_selection)
        val package_description = itemView.findViewById<TextView>(R.id.description_package_buy)
        val actual_price = itemView.findViewById<TextView>(R.id.offer_price)
        val offer_price = itemView.findViewById<TextView>(R.id.price_text_view)
        val name_package = itemView.findViewById<TextView>(R.id.heading_package)
        val title_package = itemView.findViewById<TextView>(R.id.tokens_count)
    }
}