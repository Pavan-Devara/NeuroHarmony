package com.neuro.neuroharmony.ui.login

import android.content.Intent
import android.os.Bundle
import com.neuro.neuroharmony.R
import kotlinx.android.synthetic.main.activity_social_profile_edit_tester_page.*

class social_profile_edit_tester_page : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_social_profile_edit_tester_page)
        tester.setOnClickListener {
            val intent = Intent(this, BioUpdateActivity::class.java)
            startActivity(intent)
        }
    }
}


//    lateinit var viewModel: ReligiousInfoViewModel
//    var gson = Gson()
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_social_profile_edit_tester_page)
//
//        viewModel = ViewModelProviders.of(this)[ReligiousInfoViewModel::class.java]
//
//        setupObservers()
//        tester.setOnClickListener{
//            setupListerners()
//        }
//    }
//
//    private fun setupListerners() {
//
//        viewModel.religiosInfoLiveData()
//    }
//
//    private fun setupObservers() {
//        viewModel.religiousInfoResponseLiveData.observe(this, Observer {
//            if (it != null) {
//                if (it.message == "Success") {
//                    val jsonArray = gson.toJson(it.data)
//                    PrefsHelper().savePref("jsonArray",jsonArray)
//                    val intent= Intent(this, ReligiousInfo::class.java)
//
//
//                    startActivity(intent)
//                }
//                else{
//                    Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
//                }
//            }
//        })
//        //observe API call status
//        viewModel.religiousInfoApiCallStatus.observe(this, Observer {
//            processStatus1(it)
//        })
//
//    }
//
//
//
//    private fun processStatus1(resource: ResourceStatus) {
//
//        when (resource.status) {
//            StatusType.SUCCESS -> {
//                dismissDialog()
//
//
//            }
//            StatusType.EMPTY_RESPONSE -> {
//                dismissDialog()
//
//            }
//            StatusType.PROGRESSING -> {
//                showDialog()
//
//            }
//            StatusType.SWIPE_RELOADING -> {
//
//            }
//            StatusType.ERROR -> {
//                CommonUtils().showSnackbar(button2.rootView, "Please try again")
//                dismissDialog()
//
//            }
//            StatusType.LOADING_MORE -> {
//                // CommonUtils().showSnackbar(binding.root, "Loading more..")
//            }
//            StatusType.NO_NETWORK -> {
//                CommonUtils().showSnackbar(
//                    button2.rootView,
//                    "Please check internet connection"
//                )
//            }
//            StatusType.SESSION_EXPIRED -> {
//                CommonUtils().showSnackbar(button2.rootView, "session expired")
//            }
//        }
//
//    }
//
//
//
//





































//    private lateinit var viewModel2: FamilyInfoViewModel
//    val gson = Gson()
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_social_profile_edit_tester_page)
//        viewModel2 = ViewModelProviders.of(this)[FamilyInfoViewModel::class.java]
//        viewModel2.familyInfoLiveData()
//        setupobserversFamilyinfo()
//    }
//
//
//    private fun setupobserversFamilyinfo(){
//        viewModel2.familyInfoResponseLiveData.observe(this, Observer {
//            if(it!=null){
//                if(it.message=="Success"){
//                    val intent = Intent(this, family_info::class.java)
//                    val response = gson.toJson(it)
//                    intent.putExtra("response",response)
//                    PrefsHelper().savePref("response",response)
//                    startActivity(intent)
//                }
//                else{
//                    Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
//                }
//            }
//        })
//        viewModel2.familyInfoApiCallStatus.observe(this, Observer {
//            processStatus(it)
//        })
//    }
//    private fun processStatus(resource: ResourceStatus) {
//
//        when (resource.status) {
//            StatusType.SUCCESS -> {
//                dismissDialog()
//
//
//            }
//            StatusType.EMPTY_RESPONSE -> {
//                dismissDialog()
//
//            }
//            StatusType.PROGRESSING -> {
//                showDialog()
//
//            }
//            StatusType.SWIPE_RELOADING -> {
//
//            }
//            StatusType.ERROR -> {
//                CommonUtils().showSnackbar(tester.rootView, "Please try again")
//                dismissDialog()
//
//
//            }
//            StatusType.LOADING_MORE -> {
//                // CommonUtils().showSnackbar(binding.root, "Loading more..")
//            }
//            StatusType.NO_NETWORK -> {
//                CommonUtils().showSnackbar(
//                    tester.rootView,
//                    "Please check internet connection"
//                )
//            }
//            StatusType.SESSION_EXPIRED -> {
//                CommonUtils().showSnackbar(tester.rootView, "session expired")
//            }
//        }
//
//    }

