package com.neuro.neuroharmony.ui.login.PurchaseTokensDashboard

import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.neuro.neuroharmony.R
import com.neuro.neuroharmony.ui.login.CircleTransform
import com.neuro.neuroharmony.ui.login.NeuroMatchedUsers
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat

class PurchasePackageRecyclerAdapter(
    val packageDetails: ArrayList<PurchasePackages.PurchasedPakages>,
    val activity: FragmentActivity?
) :
    RecyclerView.Adapter<PurchasePackageRecyclerAdapter.ViewHolder>() {


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val sampleText = itemView.findViewById<TextView>(R.id.heading_package_transaction)
        val number_of_tokens = itemView.findViewById<TextView>(R.id.tokens_count_transaction)
        val created_date_package = itemView.findViewById<TextView>(R.id.created_date)
        val price_package = itemView.findViewById<TextView>(R.id.price_text_view_transaction)
        val image_package = itemView.findViewById<ImageView>(R.id.image_view_package_transaction)
        val success_status =itemView.findViewById<TextView>(R.id.success_status)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.package_purchase_recycler_view, parent, false )
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return  packageDetails.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val package_details = packageDetails[position]

        holder.setIsRecyclable(false)
        holder.run {
            if (package_details.purchasedPackages["image"].toString() != "") {
                Picasso.get()
                    .load(package_details.purchasedPackages["image"].toString())
                    .resize(150, 150)
                    .transform(CircleTransform())
                    .placeholder(R.drawable.sample)
                    .centerCrop()
                    .into(image_package)
            }

        }
        holder.success_status.text =package_details.purchasedPackages["payment_status"].toString()

        holder.sampleText.text = package_details.purchasedPackages["plan_type"].toString()

        val parser = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
        val formatter = SimpleDateFormat("dd MMM yyyy")
        val output: String = formatter.format(parser.parse(package_details.purchasedPackages["created_date"].toString()))
        holder.created_date_package.text = output
        //holder.price_package.text = "₹"+package_details.purchasedPackages["offer_price"].toString()

//        val text: String =
//            java.lang.String.format(
//                "%s | %s",
//                package_details.purchasedPackages["neuro_tokens"].toString(),
//                "Neuro Tokens"
//            )
        holder.run {
            number_of_tokens.text = package_details.purchasedPackages["name"].toString()
        }

        holder.run {
            val actual_price_format = package_details.purchasedPackages["offer_price"]
            val actual_price_format_sp = "%.2f".format(actual_price_format)
                price_package.text= "₹"+actual_price_format_sp
        }

    }

}
