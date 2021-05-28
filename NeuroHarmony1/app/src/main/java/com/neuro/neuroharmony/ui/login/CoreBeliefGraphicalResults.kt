package com.neuro.neuroharmony.ui.login

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.FragmentActivity
import com.neuro.neuroharmony.R
import com.neuro.neuroharmony.utils.PrefsHelper
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_core_belief_graphical_results.*
import org.json.JSONArray
import java.lang.System.load
import android.provider.MediaStore
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.widget.ImageView
import androidx.core.content.ContextCompat

import androidx.core.net.toUri
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.gson.Gson
import com.neuro.neuroharmony.FullProfilePic
import com.neuro.neuroharmony.ui.login.Reports.Reports
import com.neuro.neuroharmony.ui.login.Reports.ReportsCoreBeliefViewModel
import com.neuro.neuroharmony.utils.CommonUtils
import kotlinx.android.synthetic.main.activity_desire_graph_result.*
import kotlinx.android.synthetic.main.activity_main.*


class MyAppGlideModule // leave empty for now

class CoreBeliefGraphicalResults : BaseActivity() {
    private lateinit var viewModelCoreBeliefReports: ReportsCoreBeliefViewModel
    var gson = Gson()

    val IDEALIST = 1
    val POWERITE = 2
    val GROUPIE = 3
    var report = ""

    @SuppressLint("NewApi", "ResourceAsColor")
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.neuro.neuroharmony.R.layout.activity_core_belief_graphical_results)
        viewModelCoreBeliefReports = ViewModelProviders.of(this)[ReportsCoreBeliefViewModel::class.java]

        setupObserversCoreBeliefReport()

        username_core_belief_graphical_result.text = PrefsHelper().getPref("user_name")

        val profile_pic = PrefsHelper().getPref<String>("user_pic")

        if (profile_pic!=""){

        Picasso.get()
            .load(profile_pic)
            .resize(200,200)
            .transform(CircleTransform())
            .placeholder(com.neuro.neuroharmony.R.mipmap.profile_pic_placeholder)
            .centerCrop()
            .into(pic_view)}

        pic_view.setOnClickListener{
            if (profile_pic.toString() != "") {
                val intent = Intent(this, FullProfilePic::class.java)
                intent.putExtra(
                    "profile_pic_url",
                    profile_pic.toString()
                )
                startActivity(intent)
            }
            else{
                Toast.makeText(this
                    , "This user hasn't uploaded the profile picture", Toast.LENGTH_LONG).show()
            }
        }

        val intent = intent
        val userGroupScore = intent.getStringExtra("userGroupScore")
        val userOrderNumber = intent.getStringExtra("userOrderNumber")
        val jsonArray = intent.getStringExtra("jsonArray")
        val array = JSONArray(jsonArray)

        for (i in 0 until array.length()){
            val obj = array.getJSONObject(i)
            val group_type = obj.getInt("group_type")
            val subItems = obj.getJSONArray("sub_items")
            val button1 = subItems.getJSONObject(0).getString("name")
            val button2 = subItems.getJSONObject(1).getString("name")
            val button3 = subItems.getJSONObject(2).getString("name")
            val order1 = subItems.getJSONObject(0).getInt("order_number")
            val order2 = subItems.getJSONObject(1).getInt("order_number")
            val order3 = subItems.getJSONObject(2).getInt("order_number")
            if(group_type==IDEALIST){
                if (group_type.toString() == userGroupScore) {
                    when (userOrderNumber.toInt()) {
                        order1 -> idealisticCreativity.background = getDrawable(R.drawable.rounded_button_square)
                        order2 -> idealisticPerfectionist.background = getDrawable(R.drawable.rounded_button_square)
                        order3 -> idealisticVisionary.background = getDrawable(R.drawable.rounded_button_square)
                    }
                }
                idealisticCreativity.text = button1
                idealisticPerfectionist.text = button2
                idealisticVisionary.text = button3

            }else if (group_type==POWERITE){

                if (group_type.toString() == userGroupScore) {
                    when (userOrderNumber.toInt()) {
                        order1 -> PoweriteAnalyst.background = getDrawable(R.drawable.rounded_button_square)
                        order2 -> PoweriteHelper.background = getDrawable(R.drawable.rounded_button_square)
                        order3 -> PoweriteBoss.background = getDrawable(R.drawable.rounded_button_square)
                    }
                }
                PoweriteAnalyst.text = button1
                PoweriteHelper.text = button2
                PoweriteBoss.text = button3

            }else if (group_type == GROUPIE){

                if (group_type.toString() == userGroupScore) {
                    when (userOrderNumber.toInt()) {
                        order1 -> GroupiePeacemaker.background = getDrawable(com.neuro.neuroharmony.R.drawable.rounded_button_square)
                        order2 -> GroupieLoyalSkeptie.background = getDrawable(com.neuro.neuroharmony.R.drawable.rounded_button_square)
                        order3 -> GroupieAchiever.background = getDrawable(com.neuro.neuroharmony.R.drawable.rounded_button_square)
                    }
                }
                GroupiePeacemaker.text = button1
                GroupieLoyalSkeptie.text = button2
                GroupieAchiever.text = button3
            }else{
                Toast.makeText(this, "No proper Group", Toast.LENGTH_SHORT).show()
            }
        }
        core_belief_report.setOnClickListener {
            val sessionId = PrefsHelper().getPref<String>("sessionId")
            PrefsHelper().savePref("sessionId_reports", sessionId)
            PrefsHelper().savePref("report_type", 3.toString())
            viewModelCoreBeliefReports.getreportsvm()

            /*val next_intent = Intent(this, CoreBeliefViewReport::class.java)
            next_intent.putExtra("jsonArray",jsonArray)
            next_intent.putExtra("userGroupScore", userGroupScore)
            next_intent.putExtra("userOrderNumber", userOrderNumber)
            startActivity(next_intent)*/
        }
        back_core_graph_menu.setOnClickListener {
            onBackPressed()
        }

    }

    private fun setupObserversCoreBeliefReport() {
        viewModelCoreBeliefReports.getreportsResponseLiveData.observe(this, Observer {
            if (it!= null){
                if (it.message== "Success"){
                    val intent = Intent(this, Reports::class.java)
                    intent.putExtra("response_type", 3)
                    intent.putExtra("corebelief_report",gson.toJson(it.data))
                    startActivity(intent)
                }
            }
        })
        viewModelCoreBeliefReports.getreportsAPICallStatus.observe(this, Observer {
            processStatus(it)
        })
    }



    private fun processStatus(resource: ResourceStatus) {

        when (resource.status) {
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
                CommonUtils().showSnackbar(core_belief_report.rootView, "Please try again. Server error")
                dismissDialog()


            }
            StatusType.LOADING_MORE -> {
                // CommonUtils().showSnackbar(binding.root, "Loading more..")
            }
            StatusType.NO_NETWORK -> {
                Toast.makeText(this, "Please check internet connection", Toast.LENGTH_SHORT).show()
            }
            StatusType.SESSION_EXPIRED -> {
                CommonUtils().showSnackbar(core_belief_report.rootView,"session expired")
            }
        }

    }

    override fun onBackPressed() {
        if (intent != null && intent.getExtras() != null && intent.getExtras()!!.containsKey("fixAsDefaultScreen")) {
            super.onBackPressed()
        }else {
            val intent = Intent(this, CoreBeliefScreen::class.java)
            startActivity(intent)
        }
    }
}

