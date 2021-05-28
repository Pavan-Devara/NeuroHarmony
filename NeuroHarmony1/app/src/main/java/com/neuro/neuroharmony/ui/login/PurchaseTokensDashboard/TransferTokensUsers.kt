package com.neuro.neuroharmony.ui.login.PurchaseTokensDashboard

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.neuro.neuroharmony.R
import com.neuro.neuroharmony.ui.login.BaseActivity
import com.neuro.neuroharmony.ui.login.PaymentFlow.GetPackagesViewModel
import com.neuro.neuroharmony.ui.login.ResourceStatus
import com.neuro.neuroharmony.ui.login.StatusType
import com.neuro.neuroharmony.utils.PrefsHelper
import kotlinx.android.synthetic.main.activity_transfer_tokens_users.*
import kotlinx.android.synthetic.main.fragment_neuro_token_transactions.*

class TransferTokensUsers : BaseActivity() {

    private lateinit var viewModelSubmitTokens : GetPackagesViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transfer_tokens_users)

        viewModelSubmitTokens = ViewModelProviders.of(this)[GetPackagesViewModel::class.java]

        setupObserversTransferTokens()

        val intent = intent
        val availale_tokens = intent.getIntExtra("available_tokens", 0)
        available_tokens_number_transfer_tokens.text = availale_tokens.toString()


        transfer_tokens.setOnClickListener {
            if (availale_tokens!=0){


                if ((tokens_to_be_transferred.text.trim().isNotEmpty()) &&
                    tokens_to_be_transferred.text.trim().toString().toInt() > 0
                ) {
                    if ((availale_tokens > 0 &&
                                tokens_to_be_transferred.text.trim().toString().toInt() <= availale_tokens)
                    ) {
                        if (mobile_number_to_transfer.text.trim().isNotEmpty()) {
                            val own_mobile = PrefsHelper().getPref<String>("Mobile")
                            if (mobile_number_to_transfer.text.trim().toString() != own_mobile) {

                                val dialogBuilder = AlertDialog.Builder(this)

                                // set message of alert dialog
                                dialogBuilder.setMessage(
                                    "Please confirm the transfer of " + tokens_to_be_transferred.text.trim().toString() + " neuro tokens to " +
                                            mobile_number_to_transfer.text.trim().toString()
                                )
                                    // if the dialog is cancelable
                                    .setCancelable(true)
                                    // positive button text and action
                                    .setPositiveButton(
                                        "Confirm",
                                        DialogInterface.OnClickListener { dialog, id ->
                                            viewModelSubmitTokens.transfertokensvm(
                                                mobile_number_to_transfer.text.trim().toString(),
                                                tokens_to_be_transferred.text.trim().toString().toInt()
                                            )
                                            dialog.cancel()
                                        })
                                    // negative button text and action
                                    .setNegativeButton(
                                        "Cancel",
                                        DialogInterface.OnClickListener { dialog, id ->
                                            dialog.cancel()
                                        })
                                // create dialog box
                                val alert = dialogBuilder.create()
                                // set title for alert dialog box
                                alert.setTitle("Message")
                                // show alert dialog
                                alert.show()
                            } else {
                                Toast.makeText(
                                    this,
                                    "You can't transfer token to yourself",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }

                        } else {
                            Toast.makeText(
                                this,
                                "Please enter a valid mobile number",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    } else {
                        /*Toast.makeText(this, "Sorry! You have "+availale_tokens+" tokens, but you need "+
                            tokens_to_be_transferred.text.trim().toString()+" tokens to transfer", Toast.LENGTH_SHORT)
                        .show()*/

                        Toast.makeText(
                            this,
                            "Please enter a valid number of tokens to transfer",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                    }
                } else {
                    Toast.makeText(
                        this,
                        "Please enter a valid number of tokens to transfer",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }
            }
            else{
                Toast.makeText(
                    this,
                    "You don't have enough tokens to transfer",
                    Toast.LENGTH_SHORT
                ).show()
            }



        }


        back_transfer_tokens.setOnClickListener {
            onBackPressed()
        }

    }

    private fun setupObserversTransferTokens() {
        viewModelSubmitTokens.transferTokensResponseLiveData.observe(this, Observer {
            if (it!=null){
                if (it.message=="Success"){
                    val availale_tokens = intent.getIntExtra("available_tokens", 0)
                    Toast.makeText(this,
                        "Transferred "+
                                tokens_to_be_transferred.text.trim().toString()+" Tokens" +
                                " to "+mobile_number_to_transfer.text.trim().toString(),
                        Toast.LENGTH_SHORT).show()
                    val changed = availale_tokens - tokens_to_be_transferred.text.trim().toString().toInt()
                    available_tokens_number_transfer_tokens.text = changed.toString()
                    tokens_to_be_transferred.setText("")
                    mobile_number_to_transfer.setText("")
                }else{
                    Toast.makeText(this,
                        it.message,
                        Toast.LENGTH_SHORT).show()
                }
            }
        })
        viewModelSubmitTokens.transferTokensAPICallStatus.observe(this, Observer {
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
                Toast.makeText(this, "Loading more", Toast.LENGTH_SHORT).show()
            }
            StatusType.NO_NETWORK -> {
                Toast.makeText(this, "Please check internet connection", Toast.LENGTH_SHORT).show()
            }
            StatusType.SESSION_EXPIRED -> {
                Toast.makeText(this, "Session Expired", Toast.LENGTH_SHORT).show()
            }
        }

    }


    override fun onBackPressed() {
        val intent = Intent(this, TokenPurchaseTransactions::class.java)
        startActivity(intent)
    }
}
