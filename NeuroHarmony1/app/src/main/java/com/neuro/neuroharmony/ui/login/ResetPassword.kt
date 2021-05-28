package com.neuro.neuroharmony.ui.login

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.neuro.neuroharmony.R
import com.neuro.neuroharmony.utils.CommonUtils
import kotlinx.android.synthetic.main.activity_forgot_password__screen.*


class ResetPassword :BaseActivity() {

    var mobile_var = ""
    private lateinit var viewModel: ResetPasswordViewModel


    private var mIshowPass = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password__screen)


        viewModel = ViewModelProviders.of(this)[ResetPasswordViewModel::class.java]

        setupListeners()
        setupObservers()

        mobile_var = intent.getStringExtra("Forgot_Mobile")


        setupListener()
    }

    private fun setupListener() {

        forgot_password_ShowHidePass.setOnClickListener {
            mIshowPass = !mIshowPass
            showPassword(mIshowPass)
        }
        showPassword(mIshowPass)

        forgot_password_submit.setOnClickListener {
            if(forgot_password_editview_password.text.isNotEmpty()) {

                val password = forgot_password_editview_password.text.toString().trim()
                val confirmpassword =
                    forgot_password_editview_confirmpassword.text.toString().trim()

                if (password.equals(confirmpassword)) {
                    if (Validator.isValidPassword(password)) {
                        viewModel.resetpassword(mobile_var, confirmpassword)

                    } else {

                        Toast.makeText(
                            baseContext,
                            "Please choose a 8 character password containing atleast one uppercase, lowercase, digit and a special character",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } else {

                    Toast.makeText(
                        baseContext,
                        "Confirm password doesn't match new password",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            else{
                Toast.makeText(
                    baseContext,
                    "Please choose a 8 character password containing atleast one uppercase, lowercase, digit and a special character",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun showPassword(isShow: Boolean) {
        if (isShow) {
            forgot_password_editview_password.transformationMethod =
                HideReturnsTransformationMethod.getInstance()
            forgot_password_ShowHidePass.setImageResource(R.drawable.visibility_off_black_24dp)

        } else {
            forgot_password_editview_password.transformationMethod =
                PasswordTransformationMethod.getInstance()
            forgot_password_ShowHidePass.setImageResource(R.drawable.password_visibility_24dp)

        }
        forgot_password_editview_password.setSelection(forgot_password_editview_password.text.toString().length)
    }


    private fun setupListeners() {
        /* email_mobile_continue.setOnClickListener {
             //initiate API call using ViewModel method
             Log.e("SingupView","setOnClickListener")
             viewModel.signup( 1, "",signuppassword.text.trim().toString(),email_id_signup.text.trim().toString(),mobile_id_signup.text.trim().toString())
         }
 */
    }

    /**
     * Write all LiveData observers in this method
     */
    private fun setupObservers() {
        viewModel.loginResponseLiveData.observe(this, Observer {
            if (it != null) {
                if (it.message == "Success") {
                    val intent = Intent(this, Login::class.java)


                    val dialogBuilder = AlertDialog.Builder(this)
                    // set message of alert dialog
                    dialogBuilder.setMessage("Your password is updated. Please login using it")
                        // if the dialog is cancelable
                        // positive button text and action
                        .setPositiveButton(
                            "Okay",
                            DialogInterface.OnClickListener { dialog, id ->startActivity(intent)

                            })
                    // negative button text and action

                    // create dialog box
                    val alert = dialogBuilder.create()
                    // set title for alert dialog box
                    alert.setTitle("Message")
                    // show alert dialog
                    alert.show()


                    // Success login.  Add the success scenario here ex: Move to next screen
                }
                else{
                    Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                }
            }
        })


        //observe API call status
        viewModel.loginAPICallStatus.observe(this, Observer {
            processStatus(it)
        })
    }
    override fun onBackPressed() {

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
                CommonUtils().showSnackbar(forgot_password_submit.rootView, "Signup failed")
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
}