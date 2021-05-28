package com.neuro.neuroharmony.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.neuro.neuroharmony.R
import kotlinx.android.synthetic.main.activity_view_match_report_screen.*

import android.net.Uri
import android.os.Build
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.gson.Gson
import com.neuro.neuroharmony.BuildConfig
import com.neuro.neuroharmony.data.model.ReportsNew.CoreBeliefDistanceReports.ReportsCoreBeliefDistance
import com.neuro.neuroharmony.data.model.ReportsNew.NeuroReports.NeuroDataKeys
import com.neuro.neuroharmony.data.model.ReportsNew.NeuroReports.NeuroReportResponse
import com.neuro.neuroharmony.data.remote.ReportsAPIService
import com.neuro.neuroharmony.data.repository.ReportsCoreBeliefDistanceRepository
import com.neuro.neuroharmony.data.repository.ReportsMatchDistanceRepository
import com.neuro.neuroharmony.ui.login.CompleteMatch.CompleteMatchViewModel
import com.neuro.neuroharmony.ui.login.Reports.*
import com.neuro.neuroharmony.utils.CommonUtils
import com.neuro.neuroharmony.utils.PrefsHelper
import kotlinx.android.synthetic.main.activity_core_belief_graphical_results.*
import kotlinx.android.synthetic.main.activity_matched_user_neuro_desire_graph_result_screen.*
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class  ViewMatchReportScreen : BaseActivity() {
    private lateinit var viewModel: DownloadPdfViewModel
    private lateinit var viewModelNeuroReports: ReportsNeuroViewModel
    private lateinit var viewModelCoreBeliefReports: ReportsCoreBeliefViewModel
    private lateinit var viewModelMatchedDistanceReports: ReportsDistanceMatchedViewModel
    private lateinit var viewModelCoreBeliefDistanceReports: ReportsCoreBeliefDistanceViewModel
    private lateinit var viewModel1: CompleteMatchViewModel
    var gson = Gson()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_match_report_screen)

        back_six_reports.setOnClickListener {
            super.onBackPressed();
        }



        viewModel = ViewModelProviders.of(this)[DownloadPdfViewModel::class.java]
        viewModelNeuroReports = ViewModelProviders.of(this)[ReportsNeuroViewModel::class.java]
        viewModelCoreBeliefReports = ViewModelProviders.of(this)[ReportsCoreBeliefViewModel::class.java]
        viewModelMatchedDistanceReports = ViewModelProviders.of(this)[ReportsDistanceMatchedViewModel::class.java]
        viewModelCoreBeliefDistanceReports = ViewModelProviders.of(this)[ReportsCoreBeliefDistanceViewModel::class.java]
        viewModel1 = ViewModelProviders.of(this)[CompleteMatchViewModel::class.java]


        val profile_pic = intent.getStringExtra("profile_pic")
        val core_belief_graph = intent.getStringExtra("core_belief_graph")
        val user_name = intent.getStringExtra("user_name")
        val user_key = PrefsHelper().getPref<String>("matched_user")

        setupObservers()
        setupObserversNeuroReport()
        setupObserversCoreBeliefReport()
        setupObserversDistanceMatchReport()
        setupObserversCoreBeliefDistanceReport(profile_pic)



        neuro_report_matcheduser.setOnClickListener{
            PrefsHelper().savePref("sessionId_reports", null)
            PrefsHelper().savePref("report_type", 1.toString())
            viewModelNeuroReports.getreportsvm()
        }



        desire_report_matcheduser.setOnClickListener {
            val intent = Intent(this,MatchedUserCoreBeliefGraphicalResult::class.java)
            intent.putExtra("user_name", user_name)
            intent.putExtra("profile_pic", profile_pic)
            intent.putExtra("core_belief_graph", core_belief_graph)
            startActivity(intent)
        }


        distance_report_matcheduser.setOnClickListener {
            val partnerKey = PrefsHelper().getPref<String>("matched_user")
            PrefsHelper().savePref("partnerKey_reports", partnerKey)
            PrefsHelper().savePref("report_type", 4.toString())
            viewModelMatchedDistanceReports.getreportsvm()
        }


        corebelief_mat_report_matcheduser.setOnClickListener {
            PrefsHelper().savePref("sessionId_reports", null)
            PrefsHelper().savePref("report_type", 3.toString())
            viewModelCoreBeliefReports.getreportsvm()
        }



        cbdistance_matched_report_matcheduser.setOnClickListener {
            val partnerKey = PrefsHelper().getPref<String>("matched_user")
            PrefsHelper().savePref("partnerKey_reports", partnerKey)
            PrefsHelper().savePref("report_type", 5.toString())
            viewModelCoreBeliefDistanceReports.getreportsvm()
            /*val intent = Intent(this,CBDistanceReportMatchedUser::class.java)
            intent.putExtra("core_belief_distance_report", core_belief_distance_report)
            startActivity(intent)*/
        }



        download_report_matcheduser.setOnClickListener {
            val url = BuildConfig.BASE_URL5
            val partnerKey = PrefsHelper().getPref<String>("matched_user")
            val user_key = PrefsHelper().getPref<String>("userKey")
            PrefsHelper().savePref("partnerKey_reports", partnerKey)
            val intent =
                Intent(Intent.ACTION_VIEW).setData(Uri.parse(
                    url+"/report/download/v1/0/?user_key="+user_key+"&&partner_user_key="+partnerKey))
            startActivity(intent)
        }

        complete_match_button.setOnClickListener {
            viewModel1.completematchvm(user_key,null,null)
        }

    }

    private  fun setupObservers(){
        viewModel1.completematchResponseLiveData.observe(this, Observer {
            if(it!=null){
                if( it.message=="Success"){

                    Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                }
                else{
                    Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                }

            }
        })



        //observe API call status
        viewModel1.completematchAPICallStatus.observe(this, Observer {
            processStatus1(it)
        })

    }



    private fun processStatus1(resource: ResourceStatus) {

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
                //CommonUtils().showSnackbar(toggle_off.rootView,"Login failed")
                dismissDialog()
            }
            StatusType.LOADING_MORE -> {
                // CommonUtils().showSnackbar(toggle_off.rootView, "Loading more..")
            }
            StatusType.NO_NETWORK -> {
                Toast.makeText(this, "Please check internet connection", Toast.LENGTH_SHORT).show()
            }
            StatusType.SESSION_EXPIRED -> {
                //CommonUtils().showSnackbar(login_button_login.rootView,"session expired")
            }
        }
    }



    private fun setupObserversDistanceMatchReport() {
        viewModelMatchedDistanceReports.getreportsResponseLiveData.observe(this, Observer {
            if (it!= null){
                if (it.message== "Success"){
                    val intent = Intent(this,Reports::class.java)
                    intent.putExtra("response_type", 4)
                    intent.putExtra("distance_report",gson.toJson(it.data))
                    startActivity(intent)
                }
                else{
                    Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                }
            }
        })
        viewModelMatchedDistanceReports.getreportsAPICallStatus.observe(this, Observer {
            processStatus(it)
        })
    }



    private fun setupObserversCoreBeliefReport() {
        viewModelCoreBeliefReports.getreportsResponseLiveData.observe(this, Observer {
            if (it!= null){
                if (it.message== "Success"){
                    val intent = Intent(this,Reports::class.java)
                    intent.putExtra("response_type", 3)
                    intent.putExtra("corebelief_report",gson.toJson(it.data))
                    startActivity(intent)
                }
                else{
                    Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                }
            }
        })
        viewModelCoreBeliefReports.getreportsAPICallStatus.observe(this, Observer {
            processStatus(it)
        })
    }


    private fun setupObserversCoreBeliefDistanceReport(profile_pic: String) {
        viewModelCoreBeliefDistanceReports.getreportsResponseLiveData.observe(this, Observer {
            if (it!= null){
                if (it.message== "Success"){
                    val intent = Intent(this,Reports::class.java)
                    PrefsHelper().savePref("matched_profile_pic", profile_pic)
                    intent.putExtra("response_type", 5)
                    intent.putExtra("corebelief_distance_report",gson.toJson(it.data))
                    startActivity(intent)
                }
                else{
                    Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                }
            }
        })
        viewModelCoreBeliefDistanceReports.getreportsAPICallStatus.observe(this, Observer {
            processStatus(it)
        })
    }



    private fun setupObserversNeuroReport() {

        viewModelNeuroReports.getreportsResponseLiveData.observe(this, Observer {
            if (it!= null){
                if (it.message== "Success"){
                    val intent = Intent(this,Reports::class.java)
                    intent.putExtra("response_type", 1)
                    intent.putExtra("neuro_report",gson.toJson(it.data))
                    startActivity(intent)
                }
                else{
                    Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
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
                CommonUtils().showSnackbar(neuro_report_matcheduser.rootView, "Please try again. Server error")
                dismissDialog()


            }
            StatusType.LOADING_MORE -> {
                // CommonUtils().showSnackbar(binding.root, "Loading more..")
            }
            StatusType.NO_NETWORK -> {
                Toast.makeText(this, "Please check internet connection", Toast.LENGTH_SHORT).show()
            }
            StatusType.SESSION_EXPIRED -> {
                CommonUtils().showSnackbar(neuro_report_matcheduser.rootView,"session expired")
            }
        }

    }
}
