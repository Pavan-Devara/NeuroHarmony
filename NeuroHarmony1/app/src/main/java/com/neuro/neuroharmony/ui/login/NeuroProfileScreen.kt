package com.neuro.neuroharmony.ui.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.neuro.neuroharmony.R
import com.neuro.neuroharmony.utils.PrefsHelper
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_neuro_profile_screen.*

class NeuroProfileScreen : BaseActivity() {

    private lateinit var viewModelgetinstructions: InstructionsViewModel
    private lateinit var viewModelSessionId: FixTestAsDefaultViewModel
    private lateinit var viewModelbaseline: BaselineResultViewModel
    var gson = Gson()
    var NEUROTEST = "1"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_neuro_profile_screen)

        viewModelgetinstructions = ViewModelProviders.of(this)[InstructionsViewModel::class.java]
        viewModelSessionId = ViewModelProviders.of(this)[FixTestAsDefaultViewModel::class.java]
        viewModelbaseline = ViewModelProviders.of(this)[BaselineResultViewModel::class.java]


        val intent = intent
        val userKey = intent.getStringExtra("userKey")

        setupObservers2(userKey)
        setupObserverViewReuslt()
        setupObserverbaseline()

        take_neuro_test_button.setOnClickListener {
            PrefsHelper().savePref("test_type",NEUROTEST)
            viewModelgetinstructions.instructions()
        }
        view_neuro_test_button.setOnClickListener {
            setupListenerViewReuslt()
        }

        view_neuro_profile_button.setOnClickListener {
            PrefsHelper().savePref("test_type",NEUROTEST)
            viewModelbaseline.baselineresultvm(null)
        }
    }



    override fun onStart() {
        super.onStart()
        if(PrefsHelper().getPref<Int>("NeuroBaselineValue")==1){
            take_neuro_test_button.visibility= View.GONE
            view_neuro_profile_button.visibility=View.VISIBLE
        }

        if(PrefsHelper().getPref<Int>("NeuroBaselineValue")==0){
            view_neuro_profile_button.visibility= View.GONE
            take_neuro_test_button.visibility=View.VISIBLE

        }
    }

    private fun setupObserverbaseline() {
        viewModelbaseline.loginResponseLiveData.observe(this, Observer {

            if (it != null){
                if (it.message == "Success"){

                    val data = it.data
                    PrefsHelper().savePref("sessionId", null)
                    val jsonArray = gson.toJson(data)
                    val intent = Intent(this, NeuroGraphResult::class.java)
                    intent.putExtra("jsonArray", jsonArray)
                    intent.putExtra("neuroCScore", it.data.neuroScore.cScore)
                    intent.putExtra("neuroPScore", it.data.neuroScore.pScore)
                    intent.putExtra("neuroIScore", it.data.neuroScore.iScore)
                    intent.putExtra("description", it.data.description)
                    intent.putExtra("distance", it.data.userDistanceReport)
                    startActivity(intent)
                }
            }
        })
        //observe API call status
        viewModelbaseline.loginAPICallStatus.observe(this, Observer {
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
                Toast.makeText(this, "Please try again", Toast.LENGTH_SHORT).show()
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
        PrefsHelper().savePref("test_type", NEUROTEST)
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
                    intent.putExtra("test_type", NEUROTEST)
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
                Toast.makeText(this, "Please try again", Toast.LENGTH_SHORT).show()
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
                    val intent = Intent(this, NeuroTestIntroScreen::class.java )
                    val jsonArray = gson.toJson(it.data.instructionsData)
                    val data = gson.toJson(it.data)
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
                Toast.makeText(this, "Please try again", Toast.LENGTH_SHORT).show()
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
        val intent = Intent(this,HomePage1::class.java)
        startActivity(intent)
    }

}
