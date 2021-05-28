package com.neuro.neuroharmony.ui.login

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.neuro.neuroharmony.R
import com.neuro.neuroharmony.data.model.AllSessionOfUserResponse
import com.neuro.neuroharmony.data.model.ItemX
import com.neuro.neuroharmony.utils.CommonUtils
import com.neuro.neuroharmony.utils.PrefsHelper
import kotlinx.android.synthetic.main.activity_fix_test_as_default.*
import kotlinx.android.synthetic.main.fix_as_defalut_test.*
import kotlinx.android.synthetic.main.package_purchase_recycler_view.*
import org.json.JSONArray


class FixTestAsDefault : BaseActivity() {

    var gson = Gson()
    var TEST = ""


    private lateinit var viewModel1: ScoreNeuroDesireViewModel
    private lateinit var viewModel2: ScoreNeuroDesireViewModel
    private lateinit var viewModel3: GetCoreBeliefScoreViewModel
    private lateinit var viewModel4: BaseLineViewModel

    private var coreBeliefResult:ArrayList<ItemX> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fix_test_as_default)

        back_neuro_text.setOnClickListener {
            onBackPressed()
        }


        viewModel1 = ViewModelProviders.of(this)[ScoreNeuroDesireViewModel::class.java]
        viewModel2 = ViewModelProviders.of(this)[ScoreNeuroDesireViewModel::class.java]
        viewModel3 = ViewModelProviders.of(this)[GetCoreBeliefScoreViewModel::class.java]
        viewModel4 = ViewModelProviders.of(this)[BaseLineViewModel::class.java]



        setUpListeners()

        setUpObservers(0)

        setUpObservers1()

        setUpObservers2()

        setUpObservers3()

        setUpObserversbaseneuro()

//        fix_as_default.setOnClickListener {
//            set_fix_as_default_button.visibility= View.VISIBLE
//            setUpObservers(1)
//        }
    }

    private fun setUpObservers1() {
        if (TEST == "1") {
            viewModel1.loginResponseLiveData.observe(
                this,
                Observer {
                    if (it != null) {
                        if (it.message == "Success") {

                            val intent = Intent(this, NeuroGraphResult::class.java)
                            intent.putExtra("fixAsDefaultScreen", 1)
                            intent.putExtra("neuroCScore", it.data.neuroScore.cScore)
                            intent.putExtra("neuroPScore", it.data.neuroScore.pScore)
                            intent.putExtra("neuroIScore", it.data.neuroScore.iScore)
                            intent.putExtra("description", it.data.Description)
                            intent.putExtra("distance", it.data.user_distance_report)
                            startActivity(intent)
                        }
                    }
                })
            //observe API call status
            viewModel1.loginAPICallStatus.observe(this, Observer {
                processStatus(it)
            })
        }
    }

    @SuppressLint("WrongConstant")
    private fun setUpObserversbaseneuro() {

            viewModel4.acceptInterestrequestResponseLiveData.observe(

                this,
                Observer {
                    if (it != null) {
                        if (it.message == "Success") {

                            val type = 0
                            if(TEST=="1") {
                                PrefsHelper().savePref("NeuroBaselineValue", 1)
                                val recyclerView = findViewById(R.id.recyclerView_fix_as_default_test) as RecyclerView
                                recyclerView.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)
                                val users = ArrayList<FixTestAsDefault.User_CoreBelief>()
                                val intent = intent
                                val jsonArray = intent.getStringExtra("jsonArray")
                                val bundle = intent.extras
                                val myData: AllSessionOfUserResponse? = bundle!!.getSerializable("mydata") as AllSessionOfUserResponse?
                                val array = JSONArray(jsonArray)


                                for (i in 0 until array.length()) {
                                    var date = myData!!.data[i].created_date
                                    if (date == null) {
                                        date = "NA"
                                    }
                                    users.add(User_CoreBelief((i + 1).toString(),
                                        array.getJSONObject(i).getString("session_id"),
                                        array.getJSONObject(i).getString("name"),
                                        date))
                                }



                                
                                PrefsHelper().savePref("neuro_baseline", it.data.sessionId)
                                recyclerView.adapter = FixAsDefaultNeuroRecyclerView(
                                    users,
                                    viewModel1,
                                    TEST,
                                    viewModel4,
                                    type,
                                    this
                                )
                            }

                            if(TEST=="2") {
                                PrefsHelper().savePref("DesireBaselineValue", 1)
                                PrefsHelper().savePref("desire_baseline", it.data.sessionId)
                                val recyclerView = findViewById(R.id.recyclerView_fix_as_default_test) as RecyclerView
                                recyclerView.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)

                                val users = ArrayList<FixTestAsDefault.User_CoreBelief>()

                                val intent = intent
                                val bundle = intent.extras
                                val myData: AllSessionOfUserResponse? = bundle!!.getSerializable("mydata") as AllSessionOfUserResponse?
                                val jsonArray = intent.getStringExtra("jsonArray")
                                val array = JSONArray(jsonArray)


                                for (i in 0 until array.length()) {
                                    var date = myData!!.data[i].created_date
                                    if (date == null) {
                                        date = "NA"
                                    }
                                    users.add(User_CoreBelief((i + 1).toString(),
                                        array.getJSONObject(i).getString("session_id"),
                                        array.getJSONObject(i).getString("name"),
                                        date))
                                }



                                recyclerView.adapter = FixAsDefaultDesireRecyclerView(
                                    users,
                                    viewModel2,
                                    TEST,
                                    viewModel4,
                                    type,
                                    this
                                )
                            }

                            if(TEST=="3") {
                                PrefsHelper().savePref("CoreBaselineValue", 1)
                                val recyclerView = findViewById(R.id.recyclerView_fix_as_default_test) as RecyclerView
                                recyclerView.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)

                                val users = ArrayList<FixTestAsDefault.User_CoreBelief>()
                                val intent = intent
                                val bundle = intent.extras
                                val myData: AllSessionOfUserResponse? = bundle!!.getSerializable("mydata") as AllSessionOfUserResponse?
                                val jsonArray = intent.getStringExtra("jsonArray")
                                val array = JSONArray(jsonArray)


                                for (i in 0 until array.length()) {
                                    var date = myData!!.data[i].created_date
                                    if (date == null) {
                                        date = "NA"
                                    }
                                    users.add(User_CoreBelief((i + 1).toString(),
                                        array.getJSONObject(i).getString("session_id"),
                                        array.getJSONObject(i).getString("name"),
                                        date))
                                }



                                PrefsHelper().savePref("core_baseline", it.data.sessionId)
                                recyclerView.adapter = FixAsDefaultRecylerView(
                                    users,
                                    viewModel3,
                                    TEST,
                                    viewModel4,
                                    type,
                                    this
                                )
                            }


                        }
                    }
                })
            //observe API call status
            viewModel4.acceptInterestrequestAPICallStatus.observe(this, Observer {
                processStatusbaseneuro(it)
            })



    }

    private fun processStatusbaseneuro(resource: ResourceStatus) {

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
                CommonUtils().showSnackbar(fix_as_default_button.rootView, "Please try again")
                dismissDialog()


            }
            StatusType.LOADING_MORE -> {
                // CommonUtils().showSnackbar(binding.root, "Loading more..")
            }
            StatusType.NO_NETWORK -> {
                CommonUtils().showSnackbar(
                    fix_as_default_button.rootView,
                    "Please check internet connection"
                )
            }
            StatusType.SESSION_EXPIRED -> {
                //CommonUtils().showSnackbar(login_button_login.rootView,"session expired")
            }
        }

    }






    private fun setUpObservers2() {
        if (TEST == "2") {
            viewModel2.loginResponseLiveData.observe(
                this,
                Observer {
                    if (it != null) {
                        if (it.message == "Success") {

                            val intent = Intent(this, DesireGraphResult::class.java)
                            intent.putExtra("fixAsDefaultScreen", 1)
                            intent.putExtra("desireCScore", it.data.desireScore.cScore)
                            intent.putExtra("desirePScore", it.data.desireScore.pScore)
                            intent.putExtra("desireIScore", it.data.desireScore.iScore)
                            intent.putExtra("neuroCScore", it.data.neuroScore.cScore)
                            intent.putExtra("neuroPScore", it.data.neuroScore.pScore)
                            intent.putExtra("neuroIScore", it.data.neuroScore.iScore)
                            intent.putExtra("description", it.data.Description)
                            intent.putExtra("distance", it.data.user_distance_report)
                            intent.putExtra("neuro_report",it.data.neuro_report)
                            startActivity(intent)
                        }
                    }
                })
            //observe API call status
            viewModel2.loginAPICallStatus.observe(this, Observer {
                processStatus(it)
            })
        }
    }







    private fun setUpObservers3() {
        viewModel3.loginResponseLiveData.observe(
            this,
            Observer {
                if (it != null){
                    if (it.message == "Success"){
                        coreBeliefResult = it.data.items as ArrayList<ItemX>
                        val jsonstring = gson.toJson(coreBeliefResult)
                        Log.d("jsonstring", jsonstring)
                        val intent = Intent(this, CoreBeliefGraphicalResults::class.java)
                        intent.putExtra("jsonArray", jsonstring)
                        intent.putExtra("fixAsDefaultScreen", 1)
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
        viewModel3.loginAPICallStatus.observe(this, Observer {
            processStatus(it)
        })
    }



    private fun setUpListeners() {
        TEST = intent.getStringExtra("test_type")
    }



    @SuppressLint("WrongConstant")
    private fun setUpObservers(type: Int) {

        val recyclerView = findViewById(R.id.recyclerView_fix_as_default_test) as RecyclerView

        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)
        val users = ArrayList<FixTestAsDefault.User_CoreBelief>()

        val intent = intent
        val bundle = intent.extras
        val myData: AllSessionOfUserResponse? = bundle!!.getSerializable("mydata") as AllSessionOfUserResponse?
        val jsonArray = intent.getStringExtra("jsonArray")
        val array = JSONArray(jsonArray)
        Log.d("array", jsonArray)

        for (i in 0 until array.length()) {
            var date = myData!!.data[i].created_date
            if (date == null) {
                date = "NA"
            }
            users.add(User_CoreBelief((i + 1).toString(),
                array.getJSONObject(i).getString("session_id"),
                array.getJSONObject(i).getString("name"),
                date))
        }


        if (TEST=="3") {
            Neuro_harmony_default_test_choice.text = "Core Belief Test"
            recyclerView.adapter = FixAsDefaultRecylerView(users, viewModel3, TEST,viewModel4, type, this)
            if (users.isEmpty()){
                recyclerView.setVisibility(View.GONE);
                nothinghere_tests.setVisibility(View.VISIBLE);
            }
        }else if (TEST == "2"){
            Neuro_harmony_default_test_choice.text = "Desire Test"
            recyclerView.adapter = FixAsDefaultDesireRecyclerView(users, viewModel2, TEST,viewModel4, type, this)
            if (users.isEmpty()){
                recyclerView.setVisibility(View.GONE);
                nothinghere_tests.setVisibility(View.VISIBLE);
            }
        }else if (TEST == "1"){
            Neuro_harmony_default_test_choice.text = "Neuro Test"
            recyclerView.adapter = FixAsDefaultNeuroRecyclerView(
                users,
                viewModel1,
                TEST,
                viewModel4, type, this
            )
            if (users.isEmpty()){
                recyclerView.setVisibility(View.GONE);
                nothinghere_tests.setVisibility(View.VISIBLE);
            }
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
                CommonUtils().showSnackbar(session_Id.rootView, "Please try again")
                dismissDialog()


            }
            StatusType.LOADING_MORE -> {
                // CommonUtils().showSnackbar(binding.root, "Loading more..")
            }
            StatusType.NO_NETWORK -> {
                CommonUtils().showSnackbar(
                    session_Id.rootView,
                    "Please check internet connection"
                )
            }
            StatusType.SESSION_EXPIRED -> {
                //CommonUtils().showSnackbar(login_button_login.rootView,"session expired")
            }
        }

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
                CommonUtils().showSnackbar(session_Id.rootView, "Please try again")
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

    data class User_CoreBelief(
        val num: String,
        val sessionId: String,
        val name: String,
        val created_date: Any
    )

    data class User_Desire(val num: String,val sessionId: String)

    data class User_Neuro(val num: String,val sessionId: String)

}
