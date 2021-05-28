package com.neuro.neuroharmony.ui.login

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent

import android.graphics.Color
import android.graphics.drawable.ColorDrawable

import android.os.Build

import android.os.Bundle
import android.util.Log
import android.view.Window
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.neuro.neuroharmony.R
import com.neuro.neuroharmony.data.model.GroupX
import com.neuro.neuroharmony.utils.CommonUtils
import com.neuro.neuroharmony.utils.PrefsHelper
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_desire_intro_screen.*
import kotlinx.android.synthetic.main.custom_dialog_for_testname.*
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.Serializable
import java.text.SimpleDateFormat
import java.time.Instant
import java.util.*
import kotlin.collections.ArrayList

class DesireIntroScreen : BaseActivity(), Serializable {

    private lateinit var viewModel: DesireTestQuestionsViewModel
    private lateinit var viewModel1reset: NeuroTestQuestionsViewModel
    private var desireQuestionsArrayList:ArrayList<GroupX> = ArrayList()
    var gson = Gson()
    val DESIRETEST = "2"
    var sessionId = ""

    private val TAG = "MyActivity"

    @SuppressLint("WrongViewCast", "WrongConstant")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_desire_intro_screen)

        viewModel = ViewModelProviders.of(this)[DesireTestQuestionsViewModel::class.java]
        viewModel1reset = ViewModelProviders.of(this)[NeuroTestQuestionsViewModel::class.java]
        val intent = intent
        val userKey = PrefsHelper().getPref<String>("userKey")
        val jsonArray = intent.getStringExtra("jsonArray")
        val array = JSONArray(jsonArray)

        setupListeners()
        setupObservers(userKey)
        setupObserversReset()


        val recyclerView = findViewById(R.id.recyclerView_desire_test) as RecyclerView

        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false) as RecyclerView.LayoutManager?

        val users = ArrayList<DesireIntroScreen.User>()

        try { // get JSONObject from JSON file
            for (i in 0 until array.length()){
                val obj = array.getJSONObject(i)
                users.add(
                    DesireIntroScreen.User(
                        obj.getString("instruction_number").toString(),
                        obj.getString("instruction")
                    )
                )
            }
        } catch (e: JSONException) {
            e.printStackTrace()
        }


        val adapter = Desire_test_adapter(users)

        recyclerView.adapter = adapter

    }



    private fun setupListeners() {
        Desire_instructions_continue.setOnClickListener {

            PrefsHelper().savePref("sessionId", sessionId)
            PrefsHelper().savePref("test_type", DESIRETEST)
            val desire_retest_debit_action = PrefsHelper().getPref<Int>("desire_retest_debit_action")

            val dialogBuilder = AlertDialog.Builder(this)

            // set message of alert dialog
//                    dialogBuilder.setMessage("Feedback Submitted \n       Successfully")

            dialogBuilder.setMessage("Your first test is free, any successive test taken will deduct "+desire_retest_debit_action.toString()+" tokens from your account")
                // if the dialog is cancelable
                .setCancelable(false)
                // positive button text and action
                .setPositiveButton("Proceed", DialogInterface.OnClickListener {
                        dialog, id -> dialog.cancel()
                    val intent = intent
                    if (intent != null && intent.getExtras() != null && intent.getExtras()!!.containsKey("data")) {
                        val data = intent.getStringExtra("data")
                        val obj = JSONObject(data)
                        if (!obj.has("pending_test")) {
                            val dialog = Dialog(this)
                            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
                            dialog.setCancelable(false)
                            dialog.setContentView(R.layout.custom_dialog_for_testname)
                            dialog.getWindow()?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
                            val heading = dialog.findViewById(R.id.alret_1) as TextView
                            heading.text = "Message"
                            val yesBtn = dialog.findViewById(R.id.yes_proceed) as Button
                            val noBtn = dialog.findViewById(R.id.no_cancel) as Button
                            val name_editext_testnamee = dialog.findViewById(R.id.name_editext_testname) as EditText

                            yesBtn.setOnClickListener {
                                if (name_editext_testnamee.text.trim().toString().isEmpty()){
                                    Toast.makeText(this, "Please enter a valid name", Toast.LENGTH_SHORT).show()
                                }else {
                                    viewModel.desiretestquestionsvm(
                                        name_editext_testnamee.text.trim().toString(),
                                        ""
                                    )
                                }

                            }
                            noBtn.setOnClickListener { dialog.dismiss() }
                            dialog.show()


                        } else {
                            val last_time = obj.get("last_qs_time")
                            val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
                            sdf.setTimeZone(TimeZone.getTimeZone("UTC"))
                            val presentTime = sdf.format(Date())
                            val parser = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                            val output = parser.parse(last_time.toString())
                            val present = parser.parse(presentTime.toString())
                            val diff = present.time - output.time
                            val seconds = diff / 1000
                            val minutes = seconds / 60
                            val hours = minutes / 60
                            val days = hours / 24
                            if (minutes>=(PrefsHelper().getPref<Int>("configured_pause"))){
                                val session_id1 = obj.getJSONObject("pending_test").get("session_id")
                                viewModel1reset.resettestvm(DESIRETEST, session_id1.toString())

                            }else {
                                val dialogBuilder = AlertDialog.Builder(this)
                                val name = obj.getJSONObject("pending_test").get("name")
                                val session_id1 = obj.getJSONObject("pending_test").get("session_id")
                                PrefsHelper().savePref("sessionId", session_id1)
                                // set message of alert dialog
                                dialogBuilder.setMessage("Please complete the pending test named " + name)
                                    // if the dialog is cancelable
                                    .setCancelable(true)
                                    // positive button text and action
                                    .setPositiveButton(
                                        "Okay",
                                        DialogInterface.OnClickListener { dialog, id ->

                                            viewModel.desiretestquestionsvm(
                                                name.toString(),
                                                session_id1.toString()
                                            )
                                            dialog.cancel()
                                        })
                                // negative button text and action

                                // create dialog box
                                val alert = dialogBuilder.create()
                                // set title for alert dialog box
                                alert.setTitle("Message")
                                // show alert dialog
                                alert.show()
                            }

                        }
                    }else{
                        val dialog = Dialog(this)
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
                        dialog.setCancelable(false)
                        dialog.setContentView(R.layout.custom_dialog_for_testname)
                        dialog.getWindow()?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
                        val heading = dialog.findViewById(R.id.alret_1) as TextView
                        heading.text = "Message"
                        val yesBtn = dialog.findViewById(R.id.yes_proceed) as Button
                        val noBtn = dialog.findViewById(R.id.no_cancel) as Button
                        val name_editext_testnamee = dialog.findViewById(R.id.name_editext_testname) as EditText
                        yesBtn.setOnClickListener {
                            if (name_editext_testnamee.text.trim().toString().isEmpty()){
                                Toast.makeText(this, "Please enter a valid name", Toast.LENGTH_SHORT).show()
                            }else {
                                viewModel.desiretestquestionsvm(
                                    name_editext_testnamee.text.trim().toString(),
                                    ""
                                )
                            }
                        }
                        noBtn.setOnClickListener { dialog.dismiss() }
                        dialog.show()
                    }

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

    /**
     * Write all LiveData observers in this method
     */
    private fun setupObservers(userKey: String?) {
        viewModel.loginResponseLiveData.observe(this, Observer {
            if (it != null) {
                if (it.message == "Success") {
                    desireQuestionsArrayList = it.data.groups
                    val jsonstring = gson.toJson(desireQuestionsArrayList)
                    sessionId = it.data.sessionId
                    PrefsHelper().savePref("desire_sessionId",sessionId)
                    PrefsHelper().savePref("sessionId",sessionId)
                    PrefsHelper().savePref("test_type",DESIRETEST)
                    Log.d("converted String", jsonstring)

                    val intent = Intent(this, DesireTestQuestions::class.java)
                    intent.putExtra("jsonArray", jsonstring.toString())
                    intent.putExtra("sessionId", sessionId)
                    intent.putExtra("groupId", it.data.groupId)
                    intent.putExtra("questionId", it.data.question_id)
                    intent.putExtra("questionIndex", it.data.questionIndex)
                    intent.putExtra("userKey", userKey)
                    startActivity(intent)
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
                CommonUtils().showSnackbar(Desire_instructions_continue.rootView, "Please try again")
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


    private fun setupObserversReset() {
        viewModel1reset.resetResponseLiveData.observe(this,
            Observer {
                if (it!=null){
                    if (it.message=="Success"){

                        val dialog = Dialog(this)
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
                        dialog.setCancelable(false)
                        dialog.setContentView(R.layout.custom_dialog_for_testname)
                        dialog.getWindow()?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
                        val heading = dialog.findViewById(R.id.alret_1) as TextView
                        heading.text = "Message"
                        val alert_message = dialog.findViewById(R.id.alert_text) as TextView
                        alert_message.text = "Your previous test has expired. Please name the test to continue"
                        val yesBtn = dialog.findViewById(R.id.yes_proceed) as Button
                        val noBtn = dialog.findViewById(R.id.no_cancel) as Button
                        val name_editext_testnamee = dialog.findViewById(R.id.name_editext_testname) as EditText

                        yesBtn.setOnClickListener {
                            if (name_editext_testnamee.text.trim().toString().isEmpty()){
                                Toast.makeText(this, "Please enter a valid name", Toast.LENGTH_SHORT).show()
                            }else {
                                viewModel.desiretestquestionsvm(
                                    name_editext_testnamee.text.trim().toString(),
                                    ""
                                )
                            }

                        }
                        noBtn.setOnClickListener { dialog.dismiss() }
                        dialog.show()

                    }
                }
            })
        viewModel1reset.resetAPICallStatus.observe(this,
            Observer {
                processStatus(it)
            })

    }


    override fun onBackPressed() {
        val intent = Intent(this, DesiredProfileScreen::class.java)
        startActivity(intent)
    }

    data class User(val num: String,val name: String)


}
