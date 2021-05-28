package com.neuro.neuroharmony.ui.login

import android.annotation.TargetApi
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.text.Html
import android.util.Log
import android.view.View
import android.widget.CompoundButton
import android.widget.RadioButton
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProviders
import com.neuro.neuroharmony.R
import com.neuro.neuroharmony.data.model.GroupCoreBeliefFinalTwoQuestionsResponse
import com.neuro.neuroharmony.data.model.ItemX
import com.neuro.neuroharmony.utils.CommonUtils
import com.neuro.neuroharmony.utils.PrefsHelper
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_add_apartner.*
import kotlinx.android.synthetic.main.activity_core_belief_question_and_answers.*
import kotlinx.android.synthetic.main.questions_fragment.*
import kotlinx.android.synthetic.main.questions_fragment_core_belief.*
import kotlinx.android.synthetic.main.questions_fragment_core_belief.imageButton2
import org.json.JSONArray
import org.json.JSONException
import java.text.SimpleDateFormat
import java.time.OffsetDateTime
import java.time.ZoneOffset
import java.util.*
import kotlin.collections.ArrayList

@Suppress("DEPRECATION")
class CoreBeliefQuestionAndAnswers :BaseActivity(), CompoundButton.OnCheckedChangeListener {
    private lateinit var viewModelgetinstructions: InstructionsViewModel
    private lateinit var viewModel: SubmitAnswerViewModel
    private lateinit var viewModel1: CoreBeliefFinalTwoQuestionsViewModel
    private lateinit var viewModel2: GetCoreBeliefScoreViewModel
    private lateinit var viewModel1reset: NeuroTestQuestionsViewModel

    private var coreBeliefEndQuestionsArrayList:ArrayList<GroupCoreBeliefFinalTwoQuestionsResponse> = ArrayList()
    private var coreBeliefResult:ArrayList<ItemX> = ArrayList()
    var gson = Gson()
    val COREBELIEFTEST = "3"
    var startTime =""
    var session_id = ""
    var endTime = ""
    var question_counter = 1
    var group_counter = 0
    var group_question_counter=0
    var group_counter_max=0
    var group_counter_questions_max=0
    var questionId:Int? = 0
    var group_submit = false
    var totalQuestions = 6
    var testType = 0
    var groupId = 0
    var option = ""
    var last_question=0
    var jsonArray_last2= ""
    var twoMinuteCounter = 0
    var pauseBoolean = 0
    var warningBoolean = 0
    val PAUSETIMER = PrefsHelper().getPref<Int>("auto_pause_time")*60*1000
    val WARNINGTIMER = 10000
    var submit = false
    var oldCheck = 0
    var newCheck = 1
    var final_two = false
    var pauseTime = (PrefsHelper().getPref<Int>("configured_pause"))*60*1000
    var currentMillis:Long = (PrefsHelper().getPref<Int>("configured_pause")*60*1000).toLong()
    var initializetimer = false

    var timer = object : CountDownTimer(1, 1000) {
        override fun onTick(millisUntilFinished: Long) {

        }
        override fun onFinish() {

        }
    }


    lateinit var restarttimer: CountDownTimer



    override fun onStart() {
        super.onStart()
        val intent = intent
        val jsonArray = intent.getStringExtra("jsonArray")
        val array = JSONArray(jsonArray)
        group_counter_questions_max = array.getJSONObject(0).getJSONArray("questions").length()

        if (array.getJSONObject(0).getJSONArray("questions").length()<2 ){
            totalQuestions=6
        }else {
            totalQuestions = array.getJSONObject(0).getJSONArray("questions").length() +
                    array.getJSONObject(1).getJSONArray("questions").length()
        }

        val text: String =
            java.lang.String.format(
                "<font color='white'>Question</font><font color='yellow'> %s</font><font color='white'> of %s</font>",
                question_counter, totalQuestions+2
            )
        Question_number_core_belief.setText(Html.fromHtml(text), TextView.BufferType.SPANNABLE)

        questionId = questionToBeDisplayed(jsonArray,group_counter,group_question_counter)

        progressBar_corebelief.max = totalQuestions+2
        progressBar_corebelief.setProgress(question_counter)
    }

    override fun onBackPressed() {
        Toast.makeText(this, "You can't go back in the middle of the test", Toast.LENGTH_LONG).show()
    }




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_core_belief_question_and_answers)
        viewModel = ViewModelProviders.of(this)[SubmitAnswerViewModel::class.java]
        viewModel2 = ViewModelProviders.of(this)[GetCoreBeliefScoreViewModel::class.java]
        viewModel1 = ViewModelProviders.of(this)[CoreBeliefFinalTwoQuestionsViewModel::class.java]
        viewModel1reset = ViewModelProviders.of(this)[NeuroTestQuestionsViewModel::class.java]
        viewModelgetinstructions = ViewModelProviders.of(this)[InstructionsViewModel::class.java]


        val intent = intent
        val jsonArray = intent.getStringExtra("jsonArray")
        val array = JSONArray(jsonArray)
        val sessionId = intent.getStringExtra("sessionId")
        session_id = sessionId
        val userKey = PrefsHelper().getPref<String>("userKey")

        if (intent.getIntExtra("groupId",0)==0&&
            intent.getIntExtra("questionId",0)==0 &&
            intent.getIntExtra("questionIndex", 0)==0) {
        }else{
            group_counter = intent.getIntExtra("groupId", 0) - 4
            group_question_counter = intent.getIntExtra("questionIndex", 0)
            if (intent.getIntExtra("groupId", 0) == 0) {
                group_counter = 0
            }

            if (array.getJSONObject(0).getJSONArray("questions").length() < 2) {
                final_two = true
                group_counter = group_counter - 2
                if (group_question_counter > 0) {
                    group_counter = group_counter + 1
                }
                group_question_counter = 0
            }

            if (group_question_counter > 2) {
                group_counter = group_counter + 1
                group_question_counter = 0
            }
            question_counter = (group_counter * 3) + group_question_counter + 1
            if (array.getJSONObject(0).getJSONArray("questions").length() < 2) {
                question_counter = intent.getIntExtra("groupId", 0) + group_counter + 1
            }
        }

        setupListeners(jsonArray, sessionId, userKey)
        setupObserversReset()
        setupObservers(jsonArray,sessionId)
        setupObservers2()
        setupObserversBack(userKey)

        imageButton2.setOnClickListener {
            initiatepause()
        }
        restarttimer = object : CountDownTimer(currentMillis, 1) {
            override fun onTick(millisUntilFinished: Long) {
                currentMillis = millisUntilFinished // <-- save value
            }

            override fun onFinish() {
                retest()
            }
        }
        newCheck = question_counter
        oldCheck = newCheck-1
        Handler().postDelayed({
            if (submit== false){
                if (pauseBoolean==0) {
                    pause()
                }
            }
        }, PAUSETIMER.toLong())
    }



    private fun questionToBeDisplayed(jsonArray: String, groupCounter: Int, groupQuestionCounter: Int): Int? {

        try {
            Log.d("check json array", jsonArray)
            val array = JSONArray(jsonArray)
            val group_counter = groupCounter
            val array_obj = array.getJSONObject(group_counter)
            val array_obj_questions = array_obj.getJSONArray("questions")
            val group_question_counter = groupQuestionCounter


            val array_obj_questions_obj = array_obj_questions.getJSONObject(group_question_counter)
            Log.d("question 2", array_obj_questions_obj.getString("question_a"))
            val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
            sdf.setTimeZone(TimeZone.getTimeZone("UTC"))
            val presentTime = sdf.format(Date())
            startTime = presentTime.toString()
            question_1_core_belief_test.text = array_obj_questions_obj.getString("question_a")
            val array_obj_questions_obj_options = array_obj_questions_obj.getJSONArray("options")
            option_A_corebelief_test.text = array_obj_questions_obj_options.getJSONObject(0).getString("option_label")
            Log.d("option1", array_obj_questions_obj_options.getJSONObject(0).getString("option_label"))
            option_B_corebelief_test.text = array_obj_questions_obj_options.getJSONObject(1).getString("option_label")
            option_C_corebelief_test.text = array_obj_questions_obj_options.getJSONObject(2).getString("option_label")
            Log.d("array length", array_obj.getString("id").toString())

            return array_obj_questions_obj.getInt("id")

        }catch (e:JSONException){
            return null
        }
    }


    private fun displayQuestion(questionCounter: Int): Int {
        var add = questionCounter+1
        return add

    }


    @TargetApi(Build.VERSION_CODES.M)
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {

        var rb1 = findViewById<RadioButton>(R.id.option_A_corebelief_test)
        var rb2 = findViewById<RadioButton>(R.id.option_B_corebelief_test)
        var rb3 = findViewById<RadioButton>(R.id.option_C_corebelief_test)

        if (isChecked) {
            if (buttonView!!.getId() == R.id.option_A_corebelief_test) {
                rb2.setChecked(false);
                rb3.setChecked(false);
                option_B_corebelief_test.setTextColor(Color.WHITE)
                option_C_corebelief_test.setTextColor(Color.WHITE)
                option_A_corebelief_test.setTextColor(getColor(R.color.background))
            }
            if (buttonView.getId() == R.id.option_B_corebelief_test) {
                rb1.setChecked(false);
                rb3.setChecked(false);
                option_C_corebelief_test.setTextColor(Color.WHITE)
                option_A_corebelief_test.setTextColor(Color.WHITE)
                option_B_corebelief_test.setTextColor(getColor(R.color.background))
            }
            if (buttonView.getId() == R.id.option_C_corebelief_test) {
                rb2.setChecked(false);
                rb1.setChecked(false);
                option_A_corebelief_test.setTextColor(Color.WHITE)
                option_B_corebelief_test.setTextColor(Color.WHITE)
                option_C_corebelief_test.setTextColor(getColor(R.color.background))
            }

        }

    }

    private fun setupListeners(
        jsonArray_listen: String,
        sessionId: String,
        userKey: String?
    ) {

        var rb1 = findViewById<RadioButton>(R.id.option_A_corebelief_test)
        var rb2 = findViewById<RadioButton>(R.id.option_B_corebelief_test)
        var rb3 = findViewById<RadioButton>(R.id.option_C_corebelief_test)

        rb1.setOnCheckedChangeListener(this)
        rb2.setOnCheckedChangeListener(this)
        rb3.setOnCheckedChangeListener(this)

        Corebelief_test_questions_continue.setOnClickListener {
            if (rb1.isChecked || rb2.isChecked || rb3.isChecked ) {
                val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
                sdf.setTimeZone(TimeZone.getTimeZone("UTC"))
                val presentTime = sdf.format(Date())
                endTime = presentTime.toString()
                    var jsonArray_listen1=jsonArray_listen
                    if ((question_counter > totalQuestions) && question_counter != 1){
                        if (!final_two) {
                            jsonArray_listen1 = jsonArray_last2
                        }
                        Log.d("jsonArray_last2",jsonArray_listen)
                    }
                    Log.d("jarray", jsonArray_listen1)
                    val array = JSONArray(jsonArray_listen1)
                    group_counter_max = array.length()
                    questionId = array.getJSONObject(group_counter).getJSONArray("questions").getJSONObject(group_question_counter).getInt("id")

                    if (rb1.isChecked) {
                        option = array.getJSONObject(group_counter).getJSONArray("questions")
                            .getJSONObject(group_question_counter).getJSONArray("options")
                            .getJSONObject(0).getString("option")
                    } else if (rb2.isChecked) {
                        option = array.getJSONObject(group_counter).getJSONArray("questions")
                            .getJSONObject(group_question_counter).getJSONArray("options")
                            .getJSONObject(1).getString("option")
                    } else if (rb3.isChecked) {
                        option = array.getJSONObject(group_counter).getJSONArray("questions")
                            .getJSONObject(group_question_counter).getJSONArray("options")
                            .getJSONObject(2).getString("option")
                    }
                    Log.d("option_selected", option)
                    Log.d("max_groups", group_counter_max.toString())
                    Log.d("max_questions", group_counter_questions_max.toString())
                    groupId = array.getJSONObject(group_counter).getJSONArray("questions").getJSONObject(group_question_counter).getInt("group")
                    Log.d("groupid", groupId.toString())
                    testType = array.getJSONObject(group_counter).getJSONArray("questions").getJSONObject(group_question_counter).getInt("test_type")

                    group_submit = false
                    if (group_question_counter + 1 == group_counter_questions_max) {
                        group_counter = displayQuestion(group_counter)
                        group_question_counter = -1
                        group_submit = true
                    }

                    if (question_counter == 7 || question_counter==8)
                    {
                        group_submit = true
                    }

                    sessionId?.let {
                        userKey?.let { it1 ->
                            viewModel.submitanswer(endTime,group_submit, questionId!!,option,
                                it,startTime,
                                it1,testType, groupId)
                        }
                    }

            }else {
                Toast.makeText(this, "Please select a choice to continue", Toast.LENGTH_SHORT).show()
            }
        }
    }


    private fun setupObservers(jsonArray_1: String, sessionId: String?) {
        viewModel.loginResponseLiveData.observe(this, Observer {
            if (it != null) {
                if (it.message == "Success") {
                    restarttimer.cancel()
                    oldCheck = newCheck
                    submit = true
                    pauseBoolean=0
                    twoMinuteCounter=0
                    if (initializetimer) {
                        timer.cancel()
                        initializetimer = false
                    }
                    Handler().postDelayed({
                        newCheck = displayQuestion(newCheck)
                        if (pauseBoolean==0) {
                            pause()
                        }
                    }, PAUSETIMER.toLong())
                    var jsonArray_used = jsonArray_1
                    if (question_counter != totalQuestions + 2){
                        if (question_counter  != totalQuestions)
                        {
                            if (question_counter > totalQuestions && question_counter != 1){
                                if (!final_two) {
                                    jsonArray_used = jsonArray_last2
                                    group_counter = displayQuestion(group_counter)
                                }
                                group_question_counter = -1
                            }
                            question_counter = displayQuestion(question_counter)
                            Log.d("question_counter", question_counter.toString())
                            Log.d("total questions", totalQuestions.toString())
                            progressBar_corebelief.setProgress(question_counter)
                            group_question_counter = displayQuestion(group_question_counter)
                            Log.d("group_question_counter2", group_question_counter.toString())
                            Log.d("specific group counter", group_counter.toString())

                            questionId =
                                questionToBeDisplayed(jsonArray_used,group_counter, group_question_counter)

                            var rb1 = findViewById<RadioButton>(R.id.option_A_corebelief_test)
                            var rb2 = findViewById<RadioButton>(R.id.option_B_corebelief_test)
                            var rb3 = findViewById<RadioButton>(R.id.option_C_corebelief_test)
                            rb1.setChecked(false);
                            rb1.setTextColor(Color.WHITE)
                            rb2.setChecked(false);
                            rb2.setTextColor(Color.WHITE)
                            rb3.setChecked(false);
                            rb3.setTextColor(Color.WHITE)


                            val text: String =
                                java.lang.String.format(
                                    "<font color='white'>Question</font><font color='yellow'> %s</font><font color='white'> of %s</font>",
                                    question_counter, totalQuestions + 2
                                )
                            Question_number_core_belief.setText(Html.fromHtml(text), TextView.BufferType.SPANNABLE)

                        }else {
                            sessionId?.let { it1 -> viewModel1.corebeliefviewmodel(it1) }

                        }
                    }else{
                        viewModel2.getcorebeliefscore()
                        viewModel2.loginResponseLiveData.observe(
                            this,
                            Observer {
                                if (it != null){
                                    if (it.message == "Success"){
                                        coreBeliefResult = it.data.items as ArrayList<ItemX>
                                        val jsonstring = gson.toJson(coreBeliefResult)
                                        Log.d("jsonstring", jsonstring)
                                        val intent = Intent(this, CoreBeliefGraphicalResults::class.java)
                                        intent.putExtra("jsonArray", jsonstring)
                                        intent.putExtra("userGroupScore", it.data.userScore.groupType)
                                        intent.putExtra("userOrderNumber", it.data.userScore.orderNumber)
                                        startActivity(intent)
                                    }
                                    else{
                                        Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                                    }
                                }
                            }
                        )
                        viewModel2.loginAPICallStatus.observe(this, Observer {
                            processStatus(it)
                        })
                    }

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



    private fun setupObservers2() {
        viewModel1.loginResponseLiveData.observe(this, Observer {
            if (it != null) {
                if (it.message == "Success") {
                    if (!it.data.reTest) {
                        group_counter = 0
                        coreBeliefEndQuestionsArrayList =
                            it.data.groups as ArrayList<GroupCoreBeliefFinalTwoQuestionsResponse>
                        jsonArray_last2 = gson.toJson(coreBeliefEndQuestionsArrayList)
                        question_counter = displayQuestion(question_counter)
                        Log.d("question_counter", question_counter.toString())
                        Log.d("total questions", totalQuestions.toString())
                        progressBar_corebelief.setProgress(question_counter)
                        group_question_counter = displayQuestion(group_question_counter)
                        Log.d("group_question_counter2", group_question_counter.toString())
                        Log.d("observer2", group_counter.toString())
                        questionId =
                            questionToBeDisplayed(
                                jsonArray_last2,
                                group_counter,
                                group_question_counter
                            )

                        var rb1 = findViewById<RadioButton>(R.id.option_A_corebelief_test)
                        var rb2 = findViewById<RadioButton>(R.id.option_B_corebelief_test)
                        var rb3 = findViewById<RadioButton>(R.id.option_C_corebelief_test)
                        rb1.setChecked(false);
                        rb1.setTextColor(Color.WHITE)
                        rb2.setChecked(false);
                        rb2.setTextColor(Color.WHITE)
                        rb3.setChecked(false);
                        rb3.setTextColor(Color.WHITE)


                        val text: String =
                            java.lang.String.format(
                                "<font color='white'>Question</font><font color='yellow'> %s</font><font color='white'> of %s</font>",
                                question_counter, totalQuestions + 2
                            )
                        Question_number_core_belief.setText(
                            Html.fromHtml(text),
                            TextView.BufferType.SPANNABLE
                        )
                    }else{
                        val dialogBuilder = AlertDialog.Builder(this)
                        // set message of alert dialog
                        dialogBuilder.setMessage(it.message)
                            // if the dialog is cancelable
                            .setCancelable(false)
                            // positive button text and action
                            .setPositiveButton(
                                "Okay",
                                DialogInterface.OnClickListener { dialog, id ->
                                    val intent = intent
                                    val jsonArray_instructions = intent.getStringExtra("jsonArray_instructions")
                                    val intent1 = Intent(this, CoreBeliefIntroScreen::class.java)
                                    intent1.putExtra("jsonArray",jsonArray_instructions)
                                    startActivity(intent1)
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
                else{
                    val dialogBuilder = AlertDialog.Builder(this)
                    // set message of alert dialog
                    dialogBuilder.setMessage("You are on a tie. You need to take the test again")
                        // if the dialog is cancelable
                        .setCancelable(false)
                        // positive button text and action
                        .setPositiveButton(
                            "Okay",
                            DialogInterface.OnClickListener { dialog, id ->
                                val intent = intent
                                val jsonArray_instructions = intent.getStringExtra("jsonArray_instructions")
                                val intent1 = Intent(this, CoreBeliefIntroScreen::class.java)
                                intent1.putExtra("jsonArray",jsonArray_instructions)
                                startActivity(intent1)
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
        })
        viewModel1.loginAPICallStatus.observe(this, Observer {
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
                CommonUtils().showSnackbar(Corebelief_test_questions_continue.rootView, "Please try again")
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


    fun pause() {
        if (newCheck == question_counter) {
            timer.cancel()
            warningBoolean = 0
            currentMillis =  pauseTime.toLong()
            timer = object : CountDownTimer(currentMillis, 1) {

                override fun onTick(millisUntilFinished: Long) {
                    initializetimer = true
                    currentMillis = millisUntilFinished // <-- save value
                    if (currentMillis.toFloat()<= (0.25*(pauseTime)).toFloat()){
                        if (warningBoolean==0) {
                            warning()
                        }
                    }
                }
                override fun onFinish() {
                    retest()
                }
            }
            timer.start()

            imageButton3_coreBelief.visibility= View.VISIBLE
            pauseBoolean = 1
            val dialogBuilder = AlertDialog.Builder(this)
            val remaining_time = (PrefsHelper().getPref<Int>("configured_pause"))

            // set message of alert dialog
            dialogBuilder.setMessage("You have  "+remaining_time +" mins to resume the test after which you will have to re-take the test")
                // if the dialog is cancelable
                .setCancelable(false)
                .setPositiveButton("Resume", { dialog, id ->
                    restarttimer.cancel()
                    imageButton3_coreBelief.visibility= View.INVISIBLE
                    dialog.cancel()
                })

            // negative button text and action

            // create dialog box
            val alert = dialogBuilder.create()
            // set title for alert dialog box
            alert.setTitle("Message")
            // show alert dialog
            alert.show()

            val warning_time = 0.75*(pauseTime).toLong()

            Handler().postDelayed({
                if (alert.isShowing){
                    alert.dismiss()
                    imageButton3_coreBelief.visibility= View.INVISIBLE
                }
            }, warning_time.toLong())
        }
    }



    fun initiatepause() {
        timer.cancel()
        warningBoolean = 0
        currentMillis =  pauseTime.toLong()
        timer = object : CountDownTimer(currentMillis, 1) {

            override fun onTick(millisUntilFinished: Long) {
                initializetimer = true
                currentMillis = millisUntilFinished // <-- save value
                if (currentMillis.toFloat()<= (0.25*(pauseTime)).toFloat()){
                    if (warningBoolean==0) {
                        warning()
                    }
                }
            }
            override fun onFinish() {
                retest()
            }
        }
        timer.start()

        imageButton3_coreBelief.visibility= View.VISIBLE
        pauseBoolean = 1
        val dialogBuilder = AlertDialog.Builder(this)
        val remaining_time = (PrefsHelper().getPref<Int>("configured_pause"))

        // set message of alert dialog
        dialogBuilder.setMessage("You have  "+remaining_time +" mins to resume the test after which you will have to re-take the test")
            // if the dialog is cancelable
            .setCancelable(false)
            .setPositiveButton("Resume", { dialog, id ->
                restarttimer.cancel()
                imageButton3_coreBelief.visibility= View.INVISIBLE
                dialog.cancel()
            })

        // negative button text and action

        // create dialog box
        val alert = dialogBuilder.create()
        // set title for alert dialog box
        alert.setTitle("Message")
        // show alert dialog
        alert.show()

        val warning_time = 0.75*(pauseTime).toLong()

        Handler().postDelayed({
            if (alert.isShowing){
                alert.dismiss()
                imageButton3_coreBelief.visibility= View.INVISIBLE
            }
        }, warning_time.toLong())
    }



    fun retest() {
        if (question_counter != totalQuestions + 2) {
            viewModel1reset.resettestvm(COREBELIEFTEST, session_id)
        }
    }


    fun warning(){
        warningBoolean = 1
        val dialogBuilder = AlertDialog.Builder(this)
        val warn_time = ((0.75*(pauseTime))/60000)
        val remaining_time = PrefsHelper().getPref<Int>("configured_pause")-warn_time

        // set message of alert dialog
        dialogBuilder.setMessage("You have only "+ remaining_time.toString()+
                " mins to resume the test after which you will have to re-take the test")
            // if the dialog is cancelable
            .setCancelable(false)
            .setPositiveButton("Resume", { dialog, id ->
                dialog.cancel()
            })

        // negative button text and action

        // create dialog box
        val alert = dialogBuilder.create()
        // set title for alert dialog box
        alert.setTitle("Message")
        // show alert dialog
        alert.show()

        val reset_time = 0.25*(pauseTime).toLong()

        Handler().postDelayed({
            if (alert.isShowing){
                alert.dismiss()
            }
        }, reset_time.toLong())
    }

    private fun setupObserversReset() {
        viewModel1reset.resetResponseLiveData.observe(this,
            Observer {
                if (it!=null){
                    if (it.message=="Success"){
                        val dialogBuilder = AlertDialog.Builder(this)
                        val pause_time = PrefsHelper().getPref<Int>("configured_pause")

                        // set message of alert dialog
                        dialogBuilder.setMessage("Due to inactivity, your test is being reset. Please take the test again to proceed.")
                            // if the dialog is cancelable
                            .setCancelable(false)
                            .setPositiveButton("Ok", { dialog, id ->
                                back()
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
            })
        viewModel1reset.resetAPICallStatus.observe(this,
            Observer {
                processStatus(it)
            })

    }


    private fun setupObserversBack(userKey: String?) {
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



    override fun onPause() {
        super.onPause()

        Log.d("pause", currentMillis.toString())
    }

    override fun onResume() {
        super.onResume()
        Log.d("resume", currentMillis.toString())
    }

    override fun onStop() {
        super.onStop()
        Log.d("stop", currentMillis.toString())
    }


    override fun onRestart() {
        super.onRestart()
    }


    fun back(){
        PrefsHelper().savePref("test_type", COREBELIEFTEST)
        viewModelgetinstructions.instructions()

    }
}
