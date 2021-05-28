package com.neuro.neuroharmony.ui.login.AfilliateWorkFlow

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.preference.PreferenceManager
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.neuro.neuroharmony.R
import com.neuro.neuroharmony.ui.login.*
import com.neuro.neuroharmony.utils.PrefsHelper
import kotlinx.android.synthetic.main.activity_affiliate_home_page.*
import android.widget.HorizontalScrollView
import androidx.core.app.ComponentActivity.ExtraData
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.widget.TextView


class AffiliateHomePage : BaseActivity() {
    private lateinit var viewModel: LogoutViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_affiliate_home_page)









        viewModel = ViewModelProviders.of(this)[LogoutViewModel::class.java]

        transactions_affiliate.setOnClickListener {
            val intent = Intent(this,AffiliateDashboard::class.java)
            startActivity(intent)
        }


        affiliate_details_edit.setOnClickListener {
            val intent = Intent(this,AffiliateForms::class.java)
            intent.putExtra("edit_affiliate", true)
            PrefsHelper().savePref("flag",1)
            startActivity(intent)
        }

        logout_affiliate.setOnClickListener {
            val dialogBuilder = AlertDialog.Builder(this)

            // set message of alert dialog
            dialogBuilder.setMessage("Do you want to Sign out?")
                // if the dialog is cancelable
                .setCancelable(false)
                // positive button text and action
                .setPositiveButton("Sign out", DialogInterface.OnClickListener {

                        dialog, id -> setupObservers()
                    viewModel.logoutuser()



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



    }


    private  fun setupObservers() {
        viewModel.loginResponseLiveData.observe(this, Observer {
            if(it!=null){
                if( it.message=="Success"){
                    val  mySPrefs = PreferenceManager.getDefaultSharedPreferences(this)
                    val editor = mySPrefs.edit()
                    editor.remove("Account_Status")
                    editor.remove("userType")
                    editor.apply()
                    PrefsHelper().savePref("userKey", null)
                    val intent = Intent(this, Login::class.java)
                    startActivity(intent)
                    finish()
                    super.onBackPressed()


                    // Success login.  Add the success scenario here ex: Move to next screen
                }
            }
        })
        //observe API call status
        viewModel.loginAPICallStatus.observe(this, Observer {
            processStatus(it)
        })


    }

    override fun onBackPressed() {
        val  a =  Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a)
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

            StatusType.LOADING_MORE -> {
                Toast.makeText(this, "Loading more...", Toast.LENGTH_SHORT).show()
            }

            StatusType.SESSION_EXPIRED -> {
                val intent = Intent(this, Login::class.java)
                startActivity(intent)
            }
        }
    }
}
