package com.neuro.neuroharmony.ui.login.CompleteMatch

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.neuro.neuroharmony.R
import com.neuro.neuroharmony.ui.login.*
import com.neuro.neuroharmony.ui.login.AfilliateWorkFlow.AffiliateForms
import com.neuro.neuroharmony.ui.login.AfilliateWorkFlow.AffiliateHomePage
import com.neuro.neuroharmony.ui.login.PaymentFlow.PaymentPackagesScreen
import com.neuro.neuroharmony.ui.login.SocialProfile.EducationAndProfession
import com.neuro.neuroharmony.ui.login.SocialProfile.LifeStyleInfo
import com.neuro.neuroharmony.ui.login.SocialProfile.FamilyInfo
import com.neuro.neuroharmony.utils.CommonUtils
import com.neuro.neuroharmony.utils.PrefsHelper
import kotlinx.android.synthetic.main.activity_blocked_list.*
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_settings_do_not_macth.*
import kotlinx.android.synthetic.main.fragment_requests.view.*

class SettingsDoNotMacthActivity : BaseActivity() {
    private lateinit var viewModel: WithinOrOutsideViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings_do_not_macth)


        viewModel = ViewModelProviders.of(this)[WithinOrOutsideViewModel::class.java]
        setupObservers()

        back_settings.setOnClickListener {
            onBackPressed()
        }
        if ((PrefsHelper().getPref<Boolean>("final_match")==true)){
            toggle_on.visibility= View.GONE
            toggle_off.visibility=View.GONE
            do_not_match.visibility=View.GONE
        }
        else{
            if (PrefsHelper().getPref<Boolean>("do_not_match")==true){

                toggle_on.visibility=View.VISIBLE
                toggle_off.visibility= View.GONE

            }
            else{
                toggle_on.visibility=View.GONE
                toggle_off.visibility= View.VISIBLE
            }
        }





        toggle_off.setOnClickListener {



            val dialogBuilder = AlertDialog.Builder(this)

            // set message of alert dialog
//                    dialogBuilder.setMessage("Feedback Submitted \n       Successfully")

            dialogBuilder.setMessage("Turning this on will cease you from being matched with other users and your profile won't appear in other user match list anymore")
                // if the dialog is cancelable
                .setCancelable(false)
                // positive button text and action
                .setPositiveButton("Proceed", DialogInterface.OnClickListener {
                        dialog, id -> dialog.cancel()
                    toggle_off.visibility=View.GONE
                    toggle_on.visibility=View.VISIBLE
                    viewModel.withinoroutsideneuroharmonyvm(true,true,true)
                })
                // negative button text and action
                .setNegativeButton("Cancel", DialogInterface.OnClickListener {
                        dialog, id -> dialog.cancel()
                })
            // create dialog box
            val alert = dialogBuilder.create()
            // set title for alert dialog box
            alert.setTitle("Message")
            // show alert dialog
            alert.show()


        }
        toggle_on.setOnClickListener {
            toggle_on.visibility=View.GONE
            toggle_off.visibility=View.VISIBLE
            viewModel.withinoroutsideneuroharmonyvm(false,false,false)
        }
    }

    private  fun setupObservers(){
        viewModel.withinoroutsideneuroharmonyResponseLiveData.observe(this, Observer {
            if(it!=null){
                if( it.message=="Success"){

                    //Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
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

    override fun onBackPressed() {
        val intent = Intent(this, HomePage1::class.java)
        startActivity(intent)
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
