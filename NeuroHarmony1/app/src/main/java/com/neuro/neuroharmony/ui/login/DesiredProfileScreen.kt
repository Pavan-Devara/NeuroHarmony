package com.neuro.neuroharmony.ui.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.neuro.neuroharmony.R
import com.neuro.neuroharmony.utils.CommonUtils
import com.neuro.neuroharmony.utils.PrefsHelper
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_desired_profile_screen.*

class DesiredProfileScreen : BaseActivity() {
    private lateinit var viewModelgetinstructions: InstructionsViewModel
    private lateinit var viewModelbaselineresult: BaselineResultViewModel
    var gson = Gson()
    val DESIRETEST = "2"

    val X = PrefsHelper().getPref<Int>("NeuroBaselineValue")



    private lateinit var viewModelSessionId: FixTestAsDefaultViewModel



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_desired_profile_screen)

        viewModelSessionId = ViewModelProviders.of(this)[FixTestAsDefaultViewModel::class.java]
        viewModelgetinstructions = ViewModelProviders.of(this)[InstructionsViewModel::class.java]
        viewModelbaselineresult = ViewModelProviders.of(this)[BaselineResultViewModel::class.java]

        val intent = intent
        val userKey = intent.getStringExtra("userKey")

        setupObservers2(userKey)
        setupObservers()
        setupObserverViewReuslt()
        setupObserverbase()




        take_desire_test_button.setOnClickListener {

                PrefsHelper().savePref("test_type", DESIRETEST)

                viewModelgetinstructions.instructions()


            }


            vie_desire_test_button.setOnClickListener {
                setupListenerViewReuslt()
            }

            view_desire_profile_button.setOnClickListener {
                PrefsHelper().savePref("test_type", DESIRETEST)
                viewModelbaselineresult.baselineresultvm(null)
            }



    }

    override fun onResume() {


        super.onResume()

        if(PrefsHelper().getPref<Int>("DesireBaselineValue")==1){
            take_desire_test_button.visibility= View.GONE
            view_desire_profile_button.visibility=View.VISIBLE
        }

        if(PrefsHelper().getPref<Int>("DesireBaselineValue")==0){
            view_desire_profile_button.visibility= View.GONE
            take_desire_test_button.visibility=View.VISIBLE
        }


    }

    private fun setupObserverbase() {
        viewModelbaselineresult.loginResponseLiveData.observe(this, Observer {

            if (it != null){
                if (it.message == "Success"){

                    val data = it.data
                    PrefsHelper().savePref("sessionId", null)
                    val jsonArray = gson.toJson(data)
                    val intent = Intent(this, DesireGraphResult::class.java)
                    intent.putExtra("jsonArray", jsonArray)
                    intent.putExtra("test_type", DESIRETEST)
                    intent.putExtra("neuroCScore", it.data.neuroScore.cScore)
                    intent.putExtra("neuroPScore", it.data.neuroScore.pScore)
                    intent.putExtra("neuroIScore", it.data.neuroScore.iScore)
                    intent.putExtra("desireCScore", it.data.desireScore.cScore)
                    intent.putExtra("desirePScore", it.data.desireScore.pScore)
                    intent.putExtra("desireIScore", it.data.desireScore.iScore)
                    intent.putExtra("description", it.data.description)
                    intent.putExtra("distance", it.data.userDistanceReport)
                    intent.putExtra("neuro_report", it.data.neuroReport)
                    startActivity(intent)
                }
            }
        })
        //observe API call status
        viewModelbaselineresult.loginAPICallStatus.observe(this, Observer {
            processStatusbaseline(it)
        })
    }

    private fun processStatusbaseline(resource: ResourceStatus) {

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
                CommonUtils().showSnackbar(view_desire_profile_button.rootView,"Verification failed")
                dismissDialog()



            }
            StatusType.LOADING_MORE -> {
                // CommonUtils().showSnackbar(binding.root, "Loading more..")
            }
            StatusType.NO_NETWORK -> {
                Toast.makeText(this, "Please check internet connection", Toast.LENGTH_SHORT).show()
            }
            StatusType.SESSION_EXPIRED -> {
                //CommonUtils().showSnackbar(login_button_login.rootView,"session expired")
            }
        }
    }

    private fun setupListenerViewReuslt() {
        PrefsHelper().savePref("test_type", DESIRETEST)
        viewModelSessionId.getAllSessionIds()
    }

    private fun setupObserverViewReuslt() {
        viewModelSessionId.responseLiveData.observe(this, Observer {

            if (it != null){
                if (it.message == "Success"){

                    val data = it.data
                    val jsonArray = gson.toJson(data)
                    val intent = Intent(this, FixTestAsDefault::class.java)
                    intent.putExtra("jsonArray", jsonArray)
                    val bundle = Bundle()
                    bundle.putSerializable("mydata", it)
                    intent.putExtras(bundle)
                    intent.putExtra("test_type", DESIRETEST)
                    startActivity(intent)
                }
            }
        })
        //observe API call status
        viewModelSessionId.fixTestAPICallStatus.observe(this, Observer {
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
                CommonUtils().showSnackbar(vie_desire_test_button.rootView,"Verification failed")
                dismissDialog()



            }
            StatusType.LOADING_MORE -> {
                // CommonUtils().showSnackbar(binding.root, "Loading more..")
            }
            StatusType.NO_NETWORK -> {
                Toast.makeText(this, "Please check internet connection", Toast.LENGTH_SHORT).show()
            }
            StatusType.SESSION_EXPIRED -> {
                //CommonUtils().showSnackbar(login_button_login.rootView,"session expired")
            }
        }
    }



    private  fun setupObservers2(userKey: String?) {
        viewModelgetinstructions.loginResponseLiveData.observe(this, Observer {
            if(it!=null){
                if( it.message=="Success"){
                    val intent = Intent(this, DesireIntroScreen::class.java )
                    val data = gson.toJson(it.data)
                    val jsonArray = gson.toJson(it.data.instructionsData)
                    intent.putExtra("userKey", userKey)
                    intent.putExtra("jsonArray", jsonArray)
                    intent.putExtra("data", data)
                    startActivity(intent)

                }
                else{
                    Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                }
            }
        })
        //observe API call status
        viewModelgetinstructions.loginAPICallStatus.observe(this, Observer {
            processStatus(it)
        })

    }


    fun setupObservers(){


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
                CommonUtils().showSnackbar(take_desire_test_button.rootView,"Verification failed")
                dismissDialog()



            }
            StatusType.LOADING_MORE -> {
                // CommonUtils().showSnackbar(binding.root, "Loading more..")
            }
            StatusType.NO_NETWORK -> {
                Toast.makeText(this, "Please check internet connection", Toast.LENGTH_SHORT).show()
            }
            StatusType.SESSION_EXPIRED -> {
                //CommonUtils().showSnackbar(login_button_login.rootView,"session expired")
            }
        }

    }



    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this,HomePage1::class.java)
        startActivity(intent)
    }
}

