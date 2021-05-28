package com.neuro.neuroharmony.ui.login

import android.app.AlertDialog
import android.content.DialogInterface
import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.neuro.neuroharmony.R
import com.neuro.neuroharmony.utils.PrefsHelper
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class FixAsDefaultDesireRecyclerView(
    val userlist1: ArrayList<FixTestAsDefault.User_CoreBelief>,
    viewModel2: ScoreNeuroDesireViewModel,
    test: String,
    val viewModel4: BaseLineViewModel,
    val type: Int,
    val fixTestAsDefault: FixTestAsDefault
) : RecyclerView.Adapter<FixAsDefaultDesireRecyclerView.ViewHolder>() {

    private var viewModel2 = viewModel2
    private val test_type = test
//    private var selectedPosition = -1

    inner class ViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView){

//        private val clickHandler: (View) -> Unit = {
//            selectedPosition = adapterPosition
//            notifyDataSetChanged()
//        }

        val recyler_view_text = itemView.findViewById(R.id.session_Id) as TextView
        val relative_layout = itemView.findViewById<RelativeLayout>(R.id.fix_as_default_relative_layout)
        val test_indicator = itemView.findViewById<ImageView>(R.id.test_number)
        val image_view_desire= itemView.findViewById(R.id.test_number) as ImageView
        val button_fix_text = itemView.findViewById<Button>(R.id.fix_as_default_button)
        val checkBox = itemView.findViewById<RadioButton>(R.id.check_box)
        val created_date = itemView.findViewById<TextView>(R.id.test_status)

//        init {
//            itemView.apply {
//                checkBox.setOnClickListener(clickHandler)
//            }
//        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FixAsDefaultDesireRecyclerView.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.fix_as_defalut_test, parent, false )
        return ViewHolder(v)

    }


    override fun getItemCount(): Int {
        return userlist1.size
    }

    override fun onBindViewHolder(holder: FixAsDefaultDesireRecyclerView.ViewHolder, position: Int) {
        val user: FixTestAsDefault.User_CoreBelief = userlist1[position]

        holder.setIsRecyclable(false)
//        holder.itemView.check_box.isChecked = position == selectedPosition
        //holder.recyler_view_text.text = user.sessionId

//        holder.run {
//            if (type==1){
//                test_indicator.visibility=View.INVISIBLE
//                checkBox.visibility=View.VISIBLE
//            }
//        }

        holder.run {
            test_indicator.setImageResource(R.drawable.take_desire_resize)
        }
        holder.image_view_desire.setImageResource(R.drawable.take_desire_new)

        if (user.name.isNotEmpty()) {
            holder.recyler_view_text.text = user.name
        }else{
            holder.recyler_view_text.text = "Name not available"
        }

        if (user.created_date.toString()!="NA") {
            val parser = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
            val formatter = SimpleDateFormat("dd MMM yyyy, hh:mm aaa")
            parser.timeZone = TimeZone.getTimeZone("UTC")
            try {
                val output: String = formatter.format(parser.parse(user.created_date.toString()))
                holder.created_date.text = output
            } catch (e: ParseException) { // TODO Auto-generated catch block
                e.printStackTrace()
            }

        }else{
            holder.created_date.text = "NA"
        }



        holder.relative_layout.setOnClickListener {
            PrefsHelper().savePref("sessionId", user.sessionId)
            PrefsHelper().savePref("test_type", test_type)
            viewModel2.getscoreneurodesire()
        }

        if (PrefsHelper().getPref<Int>("DesireBaselineValue") == 1) {
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