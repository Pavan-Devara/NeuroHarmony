package com.neuro.neuroharmony.ui.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.gson.Gson
import com.neuro.neuroharmony.FullProfilePic
import com.neuro.neuroharmony.R
import com.neuro.neuroharmony.ui.login.Reports.Reports
import com.neuro.neuroharmony.ui.login.Reports.ReportsNeuroViewModel
import com.neuro.neuroharmony.utils.CommonUtils
import com.neuro.neuroharmony.utils.PrefsHelper
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_desire_graph_result.*
import kotlinx.android.synthetic.main.activity_neuro_graph_result.*

class DesireGraphResult : BaseActivity() {

    private lateinit var viewModelNeuroReports: ReportsNeuroViewModel
    var gson = Gson()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_desire_graph_result)

        viewModelNeuroReports = ViewModelProviders.of(this)[ReportsNeuroViewModel::class.java]
        setupObserversNeuroReport()
        username_desire_graphical_result.text = PrefsHelper().getPref("user_name")



        val profile_pic = PrefsHelper().getPref<String>("user_pic")
        if (profile_pic!=""){
            Picasso.get()
                .load(profile_pic)
                .resize(200,200)
                .transform(CircleTransform())
                .placeholder(R.mipmap.profile_pic_placeholder)
                .centerCrop()
                .into(imageViewNeuroGraph)}


        imageViewNeuroGraph.setOnClickListener{
            if (profile_pic != "") {
                val intent = Intent(this, FullProfilePic::class.java)
                intent.putExtra(
                    "profile_pic_url",
                    profile_pic
                )
                startActivity(intent)
            }
            else{
                Toast.makeText(this
                    , "You haven't uploaded the profile picture", Toast.LENGTH_LONG).show()
            }
        }

        val intent = intent
        val desireCScore = intent.getStringExtra("desireCScore")
        val desireIScore = intent.getStringExtra("desireIScore")
        val desirePScore = intent.getStringExtra("desirePScore")
        val neuroCScore = intent.getStringExtra("neuroCScore")
        val neuroIScore = intent.getStringExtra("neuroIScore")
        val neuroPScore = intent.getStringExtra("neuroPScore")
        val Description = intent.getStringExtra("description")
        val DistanceReport = intent.getStringExtra("distance")
        val NeuroReport = intent.getStringExtra("neuro_report")
        Log.d("vasi",NeuroReport)

        desire_neuro_report.setOnClickListener {
            val sessionId = PrefsHelper().getPref<String>("sessionId")
            PrefsHelper().savePref("sessionId_reports", sessionId)
            PrefsHelper().savePref("report_type", 2.toString())
            viewModelNeuroReports.getreportsvm()
        }

        back_desire_graph_menu.setOnClickListener {
            onBackPressed()
        }

        CGraph(desireCScore, "desire")
        CGraph(neuroCScore, "neuro")
        PGraph(desirePScore, "desire")
        PGraph(neuroPScore, "neuro")
        IGraph(desireIScore, "desire")
        IGraph(neuroIScore, "neuro")

    }

    override fun onBackPressed() {
        if (intent != null && intent.getExtras() != null && intent.getExtras()!!.containsKey("fixAsDefaultScreen")) {
            super.onBackPressed()
        }else {
            val intent = Intent(this, DesiredProfileScreen::class.java)
            startActivity(intent)
        }
    }
    private fun IGraph(IScore: String?, type: String) {
        if (IScore != null) {
            if (type == "desire") {
                when (IScore) {
                    "I11" -> plot_i11_desire.visibility = View.VISIBLE
                    "I12" -> plot_i12_desire.visibility = View.VISIBLE
                    "I21" -> plot_i21_desire.visibility = View.VISIBLE
                    "I22" -> plot_i22_desire.visibility = View.VISIBLE
                    else -> Log.d("iscore", IScore)
                }
            }
             else {
                when (IScore) {
                    "I11" -> plot_i11_neuro.visibility = View.VISIBLE
                    "I12" -> plot_i12_neuro.visibility = View.VISIBLE
                    "I21" -> plot_i21_neuro.visibility = View.VISIBLE
                    "I22" -> plot_i22_neuro.visibility = View.VISIBLE
                    else -> Log.d("iscore", IScore)
                }

            }
        }else Toast.makeText(this, "isocre is null", Toast.LENGTH_SHORT).show()
    }

    private fun PGraph(PScore: String?, type: String) {
        if (PScore != null) {
            if (type == "desire") {
                when (PScore) {
                    "P11" -> plot_p11_desire.visibility = View.VISIBLE
                    "P12" -> plot_p12_desire.visibility = View.VISIBLE
                    "P21" -> plot_p21_desire.visibility = View.VISIBLE
                    "P22" -> plot_p22_desire.visibility = View.VISIBLE
                    else -> Log.d("iscore", PScore)
                }
            }
            else {
                when (PScore) {
                    "P11" -> plot_p11_neuro.visibility = View.VISIBLE
                    "P12" -> plot_p12_neuro.visibility = View.VISIBLE
                    "P21" -> plot_p21_neuro.visibility = View.VISIBLE
                    "P22" -> plot_p22_neuro.visibility = View.VISIBLE
                    else -> Log.d("iscore", PScore)
                }

            }
        }else Toast.makeText(this, "psocre is null", Toast.LENGTH_SHORT).show()

    }

    private fun CGraph(Cscore: String?, type: String) {
        if (Cscore != null) {
            if (type == "desire") {
                when (Cscore) {
                    "C11" -> plot_c11_desire.visibility = View.VISIBLE
                    "C12" -> plot_c12_desire.visibility = View.VISIBLE
                    "C21" -> plot_c21_desire.visibility = View.VISIBLE
                    "C22" -> plot_c22_desire.visibility = View.VISIBLE
                    else -> Log.d("iscore", Cscore)
                }
            }
            else {
                when (Cscore) {
                    "C11" -> plot_c11_neuro.visibility = View.VISIBLE
                    "C12" -> plot_c12_neuro.visibility = View.VISIBLE
                    "C21" -> plot_c21_neuro.visibility = View.VISIBLE
                    "C22" -> plot_c22_neuro.visibility = View.VISIBLE
                    else -> Log.d("iscore", Cscore)
                }

            }
        }else Toast.makeText(this, "csocre is null", Toast.LENGTH_SHORT).show()
    }

    private fun setupObserversNeuroReport() {

        viewModelNeuroReports.getreportsResponseLiveData.observe(this, Observer {
            if (it!= null){
                if (it.message== "Success"){
                    val intent = Intent(this, Reports::class.java)
                    intent.putExtra("response_type", 2)
                    intent.putExtra("neuro_report",gson.toJson(it.data))
                    startActivity(intent)
                }
            }
        })

        viewModelNeuroReports.getreportsAPICallStatus.observe(this, Observer {
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
                CommonUtils().showSnackbar(desire_neuro_report.rootView, "Please try again. Server error")
                dismissDialog()


            }
            StatusType.LOADING_MORE -> {
                // CommonUtils().showSnackbar(binding.root, "Loading more..")
            }
            StatusType.NO_NETWORK -> {
                Toast.makeText(this, "Please check internet connection", Toast.LENGTH_SHORT).show()
            }
            StatusType.SESSION_EXPIRED -> {
                CommonUtils().showSnackbar(desire_neuro_report.rootView,"session expired")
            }
        }

    }
}
