package com.neuro.neuroharmony.ui.login.CompleteMatch

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.neuro.neuroharmony.R
import com.neuro.neuroharmony.ui.login.BaseActivity
import com.neuro.neuroharmony.ui.login.ResourceStatus
import com.neuro.neuroharmony.ui.login.StatusType
import com.neuro.neuroharmony.utils.PrefsHelper
import kotlinx.android.synthetic.main.activity_where_did_match.*

class WhereDidMatch : BaseActivity() {
    private lateinit var viewModel: WithinOrOutsideViewModel
    var checked =""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_where_did_match)

        back_where_did_match.setOnClickListener {
            onBackPressed()
        }


        viewModel = ViewModelProviders.of(this)[WithinOrOutsideViewModel::class.java]
setupObservers()
        ok_button_where_did_match.setOnClickListener {
            if (within_neuroharmony.isChecked)
            {
checked="1"
                viewModel.withinoroutsideneuroharmonyvm(true,false,false)

            }
            else {
                if (outside_neuroharmony.isChecked) {
                    checked ="2"
                    viewModel.withinoroutsideneuroharmonyvm(true, true, true)
                }
                else{
                    Toast.makeText(this, "Please select the source type", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private  fun setupObservers(){
        viewModel.withinoroutsideneuroharmonyResponseLiveData.observe(this, Observer {
            if(it!=null){
                if( it.message=="Success"){
                    if (checked=="1")
                    {
                        val intent = Intent(this,SearchPartner::class.java)
                        startActivity(intent)
                    }
                    else{
                        PrefsHelper().savePref("final_match",true)
                       onBackPressed()
                    }


                }
                else{
                    Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                }

            }
        })



        //observe API call status
        viewModel.withinoroutsideneuroharmonyAPICallStatus.observe(this, Observer {
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
}
