package com.neuro.neuroharmony.ui.login

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import com.neuro.neuroharmony.R
import androidx.lifecycle.Observer
import com.neuro.neuroharmony.utils.CommonUtils
import com.neuro.neuroharmony.utils.PrefsHelper
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_core_belief_screen.*
import kotlinx.android.synthetic.main.activity_neuro_profile_screen.*

class CoreBeliefScreen : BaseActivity() {
    private lateinit var viewModelgetinstructions: InstructionsViewModel
    private lateinit var viewModel2: GetCoreBeliefScoreViewModel
    private lateinit var viewModel3: BaselineCoreResultViewModel
    private lateinit var viewModelSessionId: FixTestAsDefaultViewModel
    var gson = Gson()
    val COREBELIEFTEST = "3"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_core_belief_screen)

        viewModelgetinstructions = ViewModelProviders.of(this)[InstructionsViewModel::class.java]
        viewModel2 = ViewModelProviders.of(this)[GetCoreBeliefScoreViewModel::class.java]
        viewModelSessionId = ViewModelProviders.of(this)[FixTestAsDefaultViewModel::class.java]
        viewModel3 = ViewModelProviders.of(this)[BaselineCoreResultViewModel::class.java]


        val intent = intent
        val userKey = intent.getStringExtra("userKey")

        setupObservers2(userKey)
        setupListeners()
        setupObserverViewReuslt()
        setupObserverbaseline()

        view_core_test_button.setOnClickListener { setupListenerViewReuslt() }

        take_core_test_button.setOnClickListener {
                PrefsHelper().savePref("test_type", COREBELIEFTEST)
                viewModelgetinstructions.instructions()

        }

        view_core_profile_button.setOnClickListener {
            PrefsHelper().savePref("test_type",COREBELIEFTEST)
            viewModel3.baselinecoreresultvm(null)
        }
    }

    override fun onStart() {
        super.onStart()
        if(PrefsHelper().getPref<Int>("CoreBaselineValue")==1){
            take_core_test_button.visibility= View.GONE
            view_core_profile_button.visibility= View.VISIBLE
        }

        if(PrefsHelper().getPref<Int>("CoreBaselineValue")==0){
            view_core_profile_button.visibility= View.GONE
            take_core_test_button.visibility= View.VISIBLE
        }
    }


    private fun setupObserverbaseline() {
        viewModel3.loginResponseLiveData.observe(this, Observer {

            if (it != null){
                if (it.message == "Success"){

                    val data = it.data.items
                    PrefsHelper().savePref("sessionId", null)
                    val jsonstring = gson.toJson(data)
                    val intent = Intent(this, CoreBeliefGraphicalResults::class.java)

                    intent.putExtra("test_type", COREBELIEFTEST)
                    intent.putExtra("jsonArray", jsonstring)
                    intent.putExtra("userGroupScore", it.data.userScore.groupType)
                    intent.putExtra("userOrderNumber", it.data.userScore.orderNumber)
                    startActivity(intent)
                }
            }
        })
        //observe API call status
        viewModel3.loginAPICallStatus.observe(this, Observer {
            processStatusbase(it)
        })
    }

    private fun processStatusbase(resource: ResourceStatus) {

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
                Toast.makeText(this, "Please try again. Server error", Toast.LENGTH_SHORT).show()
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
        PrefsHelper().savePref("test_type", COREBELIEFTEST)
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
                    intent.putExtra("test_type", COREBELIEFTEST)
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
                Toast.makeText(this, "Please try again. Server error", Toast.LENGTH_SHORT).show()
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

    private fun setupObservers2(userKey: String?) {
        viewModelgetinstructions.loginResponseLiveData.observe(this, Observer {
            if (it != null) {
                if (it.message == "Success") {
                    val intent = Intent(this, CoreBeliefIntroScreen::class.java)
                    val jsonArray = gson.toJson(it.data.instructionsData)
                    intent.putExtra("userKey", userKey)
                    intent.putExtra("jsonArray", jsonArray)
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

    private fun setupListeners() {
        view_core_profile_button.setOnClickListener {
            val intent = Intent(this, FixTestAsDefault::class.java)
            intent.putExtra("test_type", COREBELIEFTEST)
            startActivity(intent)
        }
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
                Toast.makeText(this, "Please try again. Server error", Toast.LENGTH_SHORT).show()
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
