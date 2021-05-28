package com.neuro.neuroharmony.ui.login

import android.app.AlertDialog
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.neuro.neuroharmony.R
import com.neuro.neuroharmony.utils.PrefsHelper
import org.w3c.dom.Text
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class FixAsDefaultRecylerView (
    val userlist1: ArrayList<FixTestAsDefault.User_CoreBelief>,
    val viewModel2: GetCoreBeliefScoreViewModel,
    test: String,
    val viewModel4: BaseLineViewModel,
    val type: Int,
    val fixTestAsDefault: FixTestAsDefault
) : RecyclerView.Adapter<FixAsDefaultRecylerView.ViewHolder>() {

    private val test_type = test

    class ViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView){
        val recyler_view_text = itemView.findViewById(R.id.session_Id) as TextView
        val image_view_core= itemView.findViewById(R.id.test_number) as ImageView
        val test_indicator = itemView.findViewById<ImageView>(R.id.test_number)
        val relative_layout = itemView.findViewById<RelativeLayout>(R.id.fix_as_default_relative_layout)
        val button_fix_text = itemView.findViewById<Button>(R.id.fix_as_default_button)
        val created_date = itemView.findViewById<TextView>(R.id.test_status)

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FixAsDefaultRecylerView.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.fix_as_defalut_test, parent, false )
        return ViewHolder(v)
    }


    override fun getItemCount(): Int {
        return userlist1.size
    }


    override fun onBindViewHolder(holder: FixAsDefaultRecylerView.ViewHolder, position: Int) {
        val user: FixTestAsDefault.User_CoreBelief = userlist1[position]

        holder.setIsRecyclable(false)
        if (user.name.isNotEmpty()) {
            holder.recyler_view_text.text = user.name
        }else{
            holder.recyler_view_text.text = "Name not available"
        }

        if (user.created_date.toString()!="NA") {
            val parser = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
            parser.timeZone = TimeZone.getTimeZone("UTC")
            val formatter = SimpleDateFormat("dd MMM yyyy, hh:mm aaa")
            try {
                val output: String = formatter.format(parser.parse(user.created_date.toString()))
                holder.created_date.text = output
            } catch (e: ParseException) { // TODO Auto-generated catch block
                e.printStackTrace()
            }

        }else{
            holder.created_date.text = "NA"
        }

        holder.image_view_core.setImageResource(R.drawable.take_core_new)


        holder.relative_layout.setOnClickListener {
            PrefsHelper().savePref("sessionId", user.sessionId)
            PrefsHelper().savePref("test_type", test_type)
            viewModel2.getcorebeliefscore()
        }


        if (PrefsHelper().getPref<Int>("CoreBaselineValue") == 1) {
            holder.button_fix_text.visibility = View.INVISIBLE
        }


        holder.button_fix_text.setOnClickListener {

            val dialogBuilder = AlertDialog.Builder(fixTestAsDefault)

            // set message of alert dialog
            dialogBuilder.setMessage("Once you set a test as default, you wont be able to change it later. Do you want to proceed?")
                // if the dialog is cancelable
                .setCancelable(true)
                // positive button text and action
                .setPositiveButton(
                    "Proceed",
                    DialogInterface.OnClickListener { dialog, id ->
                        viewModel4.baselinevm(user.sessionId,test_type.toInt())
                        dialog.cancel()
                    })
            // negative button text and action
                .setNegativeButton("Cancel",
                    { dialog, id ->
                        dialog.cancel()
                    })

            // create dialog box
            val alert = dialogBuilder.create()
            // set title for alert dialog box
            alert.setTitle("Message")
            // show alert dialog
            alert.show()

        }


    }
}