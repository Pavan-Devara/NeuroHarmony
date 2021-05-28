package com.neuro.neuroharmony.ui.login

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.icu.text.Transliterator
import android.os.Build
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Base64
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.core.view.get
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.navigation.NavigationView
import com.neuro.neuroharmony.R
import com.neuro.neuroharmony.ui.login.manualMatching.ManualMatchingIntroScreen
import com.neuro.neuroharmony.utils.CommonUtils
import com.neuro.neuroharmony.utils.PrefsHelper
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_home_page1.*

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.iid.FirebaseInstanceId
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.neuro.neuroharmony.BuildConfig
import com.neuro.neuroharmony.FullProfilePic
import com.neuro.neuroharmony.NeuroHarmonyApp
import com.neuro.neuroharmony.ui.login.BlockList.BlockedList
import com.neuro.neuroharmony.ui.login.CompleteMatch.FinalizeMatchScreen
import com.neuro.neuroharmony.ui.login.CompleteMatch.SettingsDoNotMacthActivity
import com.neuro.neuroharmony.ui.login.FAQs.FAQ
import com.neuro.neuroharmony.ui.login.Feedback.FeedbackScreen
import com.neuro.neuroharmony.ui.login.PurchaseTokensDashboard.TokenPurchaseTransactions
import com.neuro.neuroharmony.ui.login.PaymentFlow.GetPackagesViewModel
import com.neuro.neuroharmony.ui.login.PaymentFlow.PaymentPackagesRecycleAdapter
import com.neuro.neuroharmony.ui.login.PaymentFlow.PaymentPackagesScreen
import com.neuro.neuroharmony.ui.login.PushNotification.PushNotificationViewModel
import com.neuro.neuroharmony.ui.login.PushNotification.PushNotifications
import com.neuro.neuroharmony.ui.login.SocialPreferences.SocialPreference
import kotlinx.android.synthetic.main.activity_navigation_menu.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header
import java.io.File
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.util.concurrent.TimeUnit
import kotlin.math.max


class HomePage1 : BaseActivity() , NavigationView.OnNavigationItemSelectedListener {

    lateinit var toolbar: Toolbar
    lateinit var drawerLayout: DrawerLayout
    lateinit var navView: NavigationView

    private lateinit var viewModel: LogoutViewModel

    private lateinit var viewModelInitiateMatch: InitiateMatchViewModel

    private lateinit var viewModelNM: InitailNeuroMatchViewModel

    private lateinit var viewModelPushNotification: PushNotificationViewModel


    private lateinit var viewModelGenericData: TermsAndPrivacyViewModel

    private lateinit var viewModelMatchedUsers: MatchedUsersViewModel

    var gson = Gson()

    var matched_users_status = false



    @TargetApi(Build.VERSION_CODES.M)
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val time1 = System.currentTimeMillis()
        setContentView(com.neuro.neuroharmony.R.layout.activity_navigation_menu)
        val time2 = System.currentTimeMillis()



        nav_signout.setOnClickListener {
            val dialogBuilder = AlertDialog.Builder(this)

            // set message of alert dialog
            dialogBuilder.setMessage("Do you want to Sign out?")
                // if the dialog is cancelable
                .setCancelable(false)
                // positive button text and action
                .setPositiveButton("Sign out", DialogInterface.OnClickListener {

                        dialog, id ->
                    setupObservers()
                    viewModel.logoutuser()


                })
                // negative button text and action
                .setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, id ->
                    dialog.cancel()
                })

            // create dialog box
            val alert = dialogBuilder.create()
            // set title for alert dialog box
            alert.setTitle("Message")
            // show alert dialog
            alert.show()
        }

        notifications_home_page.setOnClickListener {
            val intent = Intent(this, PushNotifications::class.java)
            startActivity(intent)
        }

        val intent1 = this.getIntent();
        if (intent1 != null && intent1.getExtras() != null && intent.getExtras()!!.containsKey("JOBID")) {
            //val jobID = this.getIntent().getExtras()!!.getInt("JOBID");
            val intent = Intent(this, PushNotifications::class.java)
            startActivity(intent)
        } else {
            viewModelGenericData = ViewModelProviders.of(this)[TermsAndPrivacyViewModel::class.java]
            val osType = 2
            val osVersion = Build.VERSION.RELEASE
            val modelNumber = Build.MODEL
            val modelName = android.os.Build.PRODUCT
            val serialNumber = Build.SERIAL
            val totalSize =
                File(getApplicationContext().getFilesDir().getAbsoluteFile().toString()).getTotalSpace();
            val storageTotal = (totalSize / (1024 * 1024 * 1024)).toString() + " GB"
            val baseBandVersion = ""
            val cpu = ""
            val isRootedOrJailBroken = ""
            val kernelVersion = ""
            val ram = ""
            val securityPatchLevel = ""
            val totalfree =
                File(getApplicationContext().getFilesDir().getAbsoluteFile().toString()).getFreeSpace();
            val storageAvailable = (totalfree / (1024 * 1024 * 1024)).toString() + " GB"
            val vendorOsVersion = ""

            ///Toast.makeText(baseContext, modelName +" "+ modelNumber+ " "+ storageTotal + " " + serialNumber,
            // Toast.LENGTH_SHORT).show()
            setupObserversGenericData()

            viewModelGenericData.genericDataVM(
                baseBandVersion,
                cpu,
                isRootedOrJailBroken,
                kernelVersion,
                modelName,
                modelNumber,
                osType,
                osVersion,
                ram,
                securityPatchLevel,
                serialNumber,
                storageAvailable,
                storageTotal,
                vendorOsVersion
            )





            if (PrefsHelper().getPref<Int>("userType") == 2) {

                linear_layout_3.visibility=View.GONE
                linear_layout_4.visibility=View.GONE

                manual_match_button.text = "  Couple\nMatch"
            }




            toolbar = findViewById(com.neuro.neuroharmony.R.id.toolbar)
            setSupportActionBar(toolbar)

            drawerLayout = findViewById(com.neuro.neuroharmony.R.id.drawer_layout)
            navView = findViewById(com.neuro.neuroharmony.R.id.nav_view)

            val toggle = ActionBarDrawerToggle(
                this, drawerLayout, toolbar, 0, 0

            )
            drawerLayout.addDrawerListener(toggle)
            toggle.syncState()
            supportActionBar?.setHomeButtonEnabled(true);
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.setHomeAsUpIndicator(R.drawable.bar_resize)

            navView.setNavigationItemSelectedListener(this)
            window.setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN,
                WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN
            )


            val navigationView =
                findViewById(com.neuro.neuroharmony.R.id.nav_view) as NavigationView
            val headerView = navigationView.getHeaderView(0)
            val navUsername =
                headerView.findViewById(com.neuro.neuroharmony.R.id.name_side_menu) as TextView
            val navImageButton =
                headerView.findViewById(com.neuro.neuroharmony.R.id.imageView_sidemenu) as ImageView
            navUsername.text = PrefsHelper().getPref("user_name")

            val profile_pic = PrefsHelper().getPref<String>("user_pic")
            if (profile_pic != "") {
                Picasso.get()
                    .isLoggingEnabled
                Picasso.get()
                    .load(profile_pic)
                    .resize(200, 200)
                    .transform(CircleTransform())
                    .placeholder(R.mipmap.profile_pic_placeholder)
                    .centerCrop()
                    .into(navImageButton)
            }

            navImageButton.setOnClickListener{
                if (profile_pic != "") {
                    val intent = Intent(this, FullProfilePic::class.java)
                    intent.putExtra(
                        "profile_pic_url",
                        profile_pic
                    )
                    startActivity(intent)
                }
                else{
                    Toast.makeText(this
                        , "You haven't uploaded the profile picture", Toast.LENGTH_LONG).show()
                }
            }

            viewModelMatchedUsers = ViewModelProviders.of(this)[MatchedUsersViewModel::class.java]

            viewModel = ViewModelProviders.of(this)[LogoutViewModel::class.java]
            viewModelInitiateMatch = ViewModelProviders.of(this)[InitiateMatchViewModel::class.java]
            viewModelNM = ViewModelProviders.of(this)[InitailNeuroMatchViewModel::class.java]
            val userKey = PrefsHelper().getPref<String>("userKey")
            viewModelPushNotification =
                ViewModelProviders.of(this)[PushNotificationViewModel::class.java]



            FirebaseInstanceId.getInstance().instanceId
                .addOnCompleteListener(OnCompleteListener { task ->
                    if (!task.isSuccessful) {
                        Log.w(TAG, "getInstanceId failed", task.exception)
                        return@OnCompleteListener
                    }

                    // Get new Instance ID token
                    val token = task.result?.token

                    // Log and toast
                    val msg = token
                    Log.d(TAG, msg)
                    PrefsHelper().savePref("device_token", msg)
                    //Toast.makeText(baseContext, msg, Toast.LENGTH_SHORT).show()
                    if (msg != null) {
                        viewModelPushNotification.profileinfo(msg, 2)
                    }
                })






            setupObserversPushNotification()
            setupListeners1(userKey)
            setupObservers1()
            setupObservers()
            setupListeners2()
            setupObservers2()
            setUpObserversListGroup()




            notification_button_main.setOnClickListener {
                Toast.makeText(this, "In Development", Toast.LENGTH_SHORT).show()
            }


            neuro_profile_button.setOnClickListener {
                val intent = Intent(this, NeuroProfileScreen::class.java)
                intent.putExtra("userKey", userKey)
                startActivity(intent)
            }

            social_preference_button.setOnClickListener {
                PrefsHelper().savePref("Flag_for_edit_signup", 1)
                val intent = Intent(this, BioUpdateActivity::class.java)

                startActivity(intent)
            }

            requests_button.setOnClickListener {
                val intent = Intent(this, Chat_Requests::class.java)
                startActivity(intent)
            }

            complete_match.setOnClickListener {
                val intent = Intent(this, FinalizeMatchScreen::class.java)
                startActivity(intent)
            }

            buy_tokens_button.setOnClickListener {
                val intent = Intent(this, PaymentPackagesScreen::class.java)
                startActivity(intent)
            }

            manual_match_button.setOnClickListener {

                if (PrefsHelper().getPref<Int>("CoreBaselineValue") == 1) {
                    val intent = Intent(this, ManualMatchingIntroScreen::class.java)
                    startActivity(intent)
                } else {
                    val dialogBuilder = AlertDialog.Builder(this)

                    // set message of alert dialog
                    dialogBuilder.setMessage("Please complete and finalize your core-belief test to proceed to match making")
                        // if the dialog is cancelable
                        .setCancelable(false)
                        // positive button text and action
                        .setPositiveButton("Okay", DialogInterface.OnClickListener { dialog, id ->
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

            my_chat_button.setOnClickListener {
                val intent = Intent(this, ChatMenu::class.java)
                startActivity(intent)
            }

            partner_preference_button.setOnClickListener {
                if (PrefsHelper().getPref<Int>("NeuroBaselineValue") == 0) {
                    val dialogBuilder = AlertDialog.Builder(this)

                    // set message of alert dialog
                    dialogBuilder.setMessage("Please complete and finalize your neuro test to continue.")
                        // if the dialog is cancelable
                        .setCancelable(false)
                        // positive button text and action
                        .setPositiveButton("Okay", DialogInterface.OnClickListener { dialog, id ->
                            dialog.cancel()
                        })
                    // negative button text and action

                    // create dialog box
                    val alert = dialogBuilder.create()
                    // set title for alert dialog box
                    alert.setTitle("Message")
                    // show alert dialog
                    alert.show()
                } else {
                    val intent = Intent(this, DesiredProfileScreen::class.java)
                    intent.putExtra("userKey", userKey)
                    startActivity(intent)
                }
            }
            core_belief_button.setOnClickListener {
                if (PrefsHelper().getPref<Int>("DesireBaselineValue") == 0) {
                    val dialogBuilder = AlertDialog.Builder(this)

                    // set message of alert dialog
                    dialogBuilder.setMessage("Please complete and finalize your desire test to continue.")
                        // if the dialog is cancelable
                        .setCancelable(false)
                        // positive button text and action
                        .setPositiveButton("Okay", DialogInterface.OnClickListener { dialog, id ->
                            dialog.cancel()
                        })
                    // negative button text and action

                    // create dialog box
                    val alert = dialogBuilder.create()
                    // set title for alert dialog box
                    alert.setTitle("Message")
                    // show alert dialog
                    alert.show()

                } else {
                    val intent = Intent(this, CoreBeliefScreen::class.java)
                    intent.putExtra("userKey", userKey)
                    startActivity(intent)
                }
            }


            /*notification_button_main.setOnClickListener {
                val intent = Intent(this, SocialPreference::class.java)
                startActivity(intent)
            }*/


        }
    }


    private fun setupObserversGenericData() {
        viewModelGenericData.genericResponseLiveData.observe(this, Observer {
            if (it != null) {
                if (it.message == "Success") {
                    PrefsHelper().savePref(
                        "matched_user_status",
                        it.data.is_initial_matchmaking_done
                    )
                    PrefsHelper().savePref(
                        "page_size",
                        it.data.match_pagination
                    )
                    PrefsHelper().savePref(
                        "express_interest_status",
                        it.data.express_interest_status
                    )

                    PrefsHelper().savePref("do_not_match",it.data.do_not_match)
                    PrefsHelper().savePref("final_match",it.data.final_match)

                    PrefsHelper().savePref("NeuroBaselineValue", it.data.baseline.neuro)
                    PrefsHelper().savePref("DesireBaselineValue", it.data.baseline.desire)
                    PrefsHelper().savePref("CoreBaselineValue", it.data.baseline.core_belief)
                    PrefsHelper().savePref("unblocking_cost", it.data.token_debit_actions.unblock)
                    PrefsHelper().savePref("configured_pause", it.data.qs_time_limit)
                    PrefsHelper().savePref("auto_pause_time", it.data.auto_pause_time)

                    PrefsHelper().savePref("express_request_debit_action",it.data.token_debit_actions.express_request)
                    PrefsHelper().savePref("neuro_retest_debit_action", it.data.token_debit_actions.neuro_retest)
                    PrefsHelper().savePref("desire_retest_debit_action",it.data.token_debit_actions.desire_retest)
                    PrefsHelper().savePref("cb_retest_debit_action", it.data.token_debit_actions.cb_retest)
                    PrefsHelper().savePref("request_manual_match_debit_action", it.data.token_debit_actions.request_manual_match)
                    unreadnotification.setText(it.data.unread_notifications.toString())
//                    val a=10
//                    if(a > 99) {
//                        unreadnotification.setText(a.toString())
//                        unreadnotification.setTextSize(10.0F)
//                    }
//                    else{
//                        unreadnotification.setText(a.toString())
//                    }
                    matched_users_status = it.data.is_initial_matchmaking_done



                    if (PrefsHelper().getPref<Boolean>("final_match")==true){
                        linear_layout_3.visibility=View.GONE
                        linear_layout_4.visibility=View.GONE
                        linear_layout_5.visibility=View.GONE
                        linear_layout_6.visibility=View.GONE
                        linear_layout_7.visibility=View.GONE

                   }

                }
            }
        })
        //observe API call status
        viewModelGenericData.genericdataAPICallStatus.observe(this, Observer {
            processStatusFindNeuroMatch(it)
        })
    }


    private fun setupObserversPushNotification() {
        viewModelPushNotification.loginResponseLiveData.observe(this, Observer {
            if (it != null) {
                if (it.message == "Success") {

                }
            }
        })
        //observe API call status
        viewModelPushNotification.loginAPICallStatus.observe(this, Observer {
            //processStatuspackages(it)
        })
    }


    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_home -> {

            }
            R.id.nav_transactions -> {
                val intent = Intent(this, TokenPurchaseTransactions::class.java)
                startActivity(intent)
            }
            R.id.nav_faq -> {
                val intent = Intent(this, FAQ::class.java)
                startActivity(intent)
            }
            R.id.nav_feedback -> {
                val intent = Intent(this, FeedbackScreen::class.java)
                startActivity(intent)
            }

            R.id.nav_blocklist -> {
                val intent = Intent(this, BlockedList::class.java)
                startActivity(intent)

            }
            R.id.nav_settings -> {
                val intent = Intent(this, SettingsDoNotMacthActivity::class.java)
                startActivity(intent)
            }


        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }


    private fun setupObservers() {
        viewModel.loginResponseLiveData.observe(this, Observer {
            if (it != null) {
                if (it.message == "Success") {
                    /*val mySPrefs = PreferenceManager.getDefaultSharedPreferences(this)
                    val editor = mySPrefs.edit()
                    editor.remove("Account_Status")
                    editor.remove("userType")
                    editor.remove("Reference")
                    editor.apply()*/
                    PreferenceManager.getDefaultSharedPreferences(NeuroHarmonyApp.getCtx()).edit().clear().apply()
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


    private fun setupListeners1(userKey: String) {


        Log.e("UserActivate", "Success")
        val userKey = userKey.toInt()
        find_neuro_match_button.setOnClickListener {


            if (PrefsHelper().getPref<Int>("CoreBaselineValue") == 1) {
                viewModelInitiateMatch.initiatematchvm(userKey)
            } else {
                val dialogBuilder = AlertDialog.Builder(this)

                // set message of alert dialog
                dialogBuilder.setMessage("Please complete and finalize your core-belief test to proceed to match making")
                    // if the dialog is cancelable
                    .setCancelable(false)
                    // positive button text and action
                    .setPositiveButton("Okay", DialogInterface.OnClickListener { dialog, id ->
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

    }


    private fun setupObservers1() {
        viewModelInitiateMatch.loginResponseLiveData.observe(this, Observer {
            if (it != null) {
                if (it.message == "Success") {
                    val dialogBuilder = AlertDialog.Builder(this)

                    // set message of alert dialog
                    dialogBuilder.setMessage(it.data.matchStatus)
                        // if the dialog is cancelable
                        .setCancelable(false)
                        // positive button text and action
                        .setPositiveButton("Okay", DialogInterface.OnClickListener { dialog, id ->
                            dialog.cancel()
                        })
                    // negative button text and action

                    // create dialog box
                    val alert = dialogBuilder.create()
                    // set title for alert dialog box
                    alert.setTitle("Message")
                    // show alert dialog
                    alert.show()
                } else {
                    Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                }
            }
        })

        //observe API call status
        viewModelInitiateMatch.loginAPICallStatus.observe(this, Observer {
            processStatusFindNeuroMatch(it)
        })

    }


    /**
     * Write all LiveData observers in this method
     */


    private fun processStatusFindNeuroMatch(resource: ResourceStatus?) {

        when (resource?.status) {
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
                CommonUtils().showSnackbar(
                    find_neuro_match_button.rootView,
                    "Please take all the tests first"
                )
                dismissDialog()


            }
            StatusType.LOADING_MORE -> {
                CommonUtils().showSnackbar(find_neuro_match_button.rootView, "Loading more..")
            }
            StatusType.NO_NETWORK -> {
                Toast.makeText(this, "Please check internet connection", Toast.LENGTH_SHORT).show()
            }
            StatusType.SESSION_EXPIRED -> {
                CommonUtils().showSnackbar(find_neuro_match_button.rootView, "session expired")
            }
        }
    }


    private fun setupListeners2() {

        Log.e("UserActivate", "Success")
        show_neuro_match.setOnClickListener {
//            if (matched_users_status) {
//                val userKey = PrefsHelper().getPref<String>("userKey")
//                viewModelMatchedUsers.getmatchedusersvm(
//                    0, 0, 0, 0, 0,
//                    0, 0, 0, 0, 0, user_key = userKey.toInt()
//                )
//            } else {

                viewModelNM.getinitailmatchvm()
        }


    }

    private fun setupObservers2() {
        viewModelNM.loginResponseLiveData.observe(this, Observer {

            if(it!=null){
                if( it.message=="Success") {


                    val obj_string = gson.toJson(it.data)
                    val obj = JSONObject(obj_string)
                    if (!obj.has("match_status")) {
                        if (it.data.totalMatchedUserCount == 0) {
                            val dialogBuilder = AlertDialog.Builder(this)

                            // set message of alert dialog
                            dialogBuilder.setMessage("There are no matches found")
                                // if the dialog is cancelable
                                .setCancelable(false)
                                // positive button text and action
                                .setPositiveButton(
                                    "Okay",
                                    DialogInterface.OnClickListener { dialog, id ->
                                        dialog.cancel()
                                    })
                            // negative button text and action

                            // create dialog box
                            val alert = dialogBuilder.create()
                            // set title for alert dialog box
                            alert.setTitle("0 Matches")
                            // show alert dialog
                            alert.show()
                        } else {
                            val groups_size = it.data.groupSize
                            val jsonArray = gson.toJson(groups_size)
                            val total_match = it.data.totalMatchedUserCount
                            val intent = Intent(this, MatchedResult::class.java)
                            intent.putExtra("jsonArray", jsonArray)
                            intent.putExtra("totalMatched", total_match)
                            intent.putExtra("social_filter_active", it.data.social_filter_active)
                            startActivity(intent)
                        }
                    }
                    else {
                        val dialogBuilder = AlertDialog.Builder(this)

                        // set message of alert dialog
                        dialogBuilder.setMessage(it.data.match_status)
                            // if the dialog is cancelable
                            .setCancelable(false)
                            // positive button text and action
                            .setPositiveButton(
                                "Okay",
                                DialogInterface.OnClickListener { dialog, id ->
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

                    // Success login.  Add the success scenario here ex: Move to next screen
                } else {
                    Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                }
            }
        })
        //observe API call status
        viewModelNM.loginAPICallStatus.observe(this, Observer {
            processStatusShowNeuroMatch(it)
        })

    }


    private fun setUpObserversListGroup() {
        viewModelMatchedUsers.loginResponseLiveData.observe(this,
            Observer {
                if (it != null) {
                    if (it.message == "Success") {
                        val data = it.data
                        val jsonArray = gson.toJson(data)
                        Log.d("success", "successful")
                        val intent = Intent(this, NeuroMatchedUsers::class.java)
                        PrefsHelper().savePref("jsonArray", jsonArray)
                        startActivity(intent)
                        // Success login.  Add the success scenario here ex: Move to next screen
                    } else {
                        Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                    }
                }
            })

        //observe API call status
        viewModelMatchedUsers.loginAPICallStatus.observe(this, Observer {
            processStatusShowNeuroMatch(it)
        })
    }

    override fun onBackPressed() {
        val a = Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a)
    }


    private fun processStatusShowNeuroMatch(resource: ResourceStatus?) {

        when (resource?.status) {
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
                CommonUtils().showSnackbar(show_neuro_match.rootView, "Please Try again")
                dismissDialog()


            }
            StatusType.LOADING_MORE -> {
                CommonUtils().showSnackbar(show_neuro_match.rootView, "Loading more..")
            }
            StatusType.NO_NETWORK -> {
                Toast.makeText(this, "Please check internet connection", Toast.LENGTH_SHORT).show()
            }
            StatusType.SESSION_EXPIRED -> {
                CommonUtils().showSnackbar(show_neuro_match.rootView, "session expired")
            }
        }
    }
}


interface TestPushNotification {
    @GET("/notification/push-notification-test/?")
    fun testPush(@Header("Authorization") token: String = "Token " + PrefsHelper().getPref("Token")): Call<ResponseBody>


    companion object {
        val instance: TestPushNotification by lazy {

            val okHttpClientBuilder = OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)

            if (BuildConfig.DEBUG) {
                val logging = HttpLoggingInterceptor()
                logging.level = HttpLoggingInterceptor.Level.BODY
                val requestInterceptor = Interceptor { chain ->
                    val url = chain.request()
                        .url()
                        .newBuilder()
                        .addQueryParameter("device_token", PrefsHelper().getPref("device_token"))
                        .addQueryParameter("os_type", "android")
                        .addQueryParameter("message", "nothing")
                        .addQueryParameter("title", "empty")
                        .build()
                    val request = chain.request()
                        .newBuilder()
                        .url(url)
                        .build()

                    return@Interceptor chain.proceed(request)
                }

                okHttpClientBuilder.addInterceptor(logging)
                okHttpClientBuilder.addInterceptor(requestInterceptor)

            }


            val okHttpClient = okHttpClientBuilder.build()

            val gson = GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
                .setLenient()
                .create()
            Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(BuildConfig.BASE_URL7)
                .addConverterFactory(GsonConverterFactory.create(gson)).addCallAdapterFactory(
                    CoroutineCallAdapterFactory()
                )
                .build()
                .create(TestPushNotification::class.java)
        }

    }
}


