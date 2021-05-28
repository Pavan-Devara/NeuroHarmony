package com.neuro.neuroharmony.ui.login

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.neuro.neuroharmony.FullProfilePic
import com.neuro.neuroharmony.R

import com.neuro.neuroharmony.ui.login.ViewPartnerProfile.ViewPartnerProfile

import com.neuro.neuroharmony.ui.login.CompleteMatch.CompleteMatchViewModel

import com.neuro.neuroharmony.utils.CommonUtils
import com.neuro.neuroharmony.utils.PrefsHelper
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_add_apartner.*
import kotlinx.android.synthetic.main.activity_matched_user_neuro_desire_graph_result_screen.*
import kotlinx.android.synthetic.main.chat_recycler_view_requests.*
import org.json.JSONObject

class MatchedUserNeuroDesireGraphResultScreen : BaseActivity() {

    private lateinit var viewModelGetMatchedReports: GetMatchedReportsViewModel
    private lateinit var viewModel: CompleteMatchViewModel

    var distance_report = ""
    var neuro_report = ""
    var desire_report = ""
    var core_belief_report = ""
    var core_belief_distance_report = ""
    var core_belief_graph = ""
    var user_neuro_score = JSONObject()
    var user_desire_score = JSONObject()
    var matched_user_neuro = JSONObject()
    var matched_user_desire = JSONObject()

    var gson = Gson()

    override fun onStart() {
        super.onStart()

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_matched_user_neuro_desire_graph_result_screen)
        viewModelGetMatchedReports = ViewModelProviders.of(this)[GetMatchedReportsViewModel::class.java]
        viewModel = ViewModelProviders.of(this)[CompleteMatchViewModel::class.java]


        viewModelGetMatchedReports.getreportsvm()

        setupObserversGetMatchedReports()
        //setupObservers()
        your_name.text = PrefsHelper().getPref("user_name")

        val prefs = PreferenceManager.getDefaultSharedPreferences(this)

        if (prefs.contains("group_match"))
        {
            matching_result_from_group.visibility=View.VISIBLE
            matching_result_from_group.setText("Matching result from" + " "+PrefsHelper().getPref("group_match"))

        }



        val self_profile_pic = PrefsHelper().getPref<String>("user_pic")
        if (self_profile_pic!=""){
            Picasso.get()
                .load(self_profile_pic)
                .resize(200,200)
                .transform(CircleTransform())
                .placeholder(R.mipmap.profile_pic_placeholder)
                .centerCrop()
                .into(boy)}


            Picasso.get()
                .load(self_profile_pic)
                .resize(20,20)
                .transform(CircleTransform())
                .placeholder(R.drawable.placeholder_pic_resize)
                .centerCrop()
                .into(user_plot_c12_neuro)

            Picasso.get()
                .load(self_profile_pic)
                .resize(20,20)
                .transform(CircleTransform())
                .placeholder(R.drawable.placeholder_pic_resize)
                .centerCrop()
                .into(user_plot_c11_neuro)

            Picasso.get()
                .load(self_profile_pic)
                .resize(20,20)
                .transform(CircleTransform())
                .placeholder(R.drawable.placeholder_pic_resize)
                .centerCrop()
                .into(user_plot_c21_neuro)

            Picasso.get()
                .load(self_profile_pic)
                .resize(20,20)
                .transform(CircleTransform())
                .placeholder(R.drawable.placeholder_pic_resize)
                .centerCrop()
                .into(user_plot_c22_neuro)


            Picasso.get()
                .load(self_profile_pic)
                .resize(20,20)
                .transform(CircleTransform())
                .placeholder(R.drawable.placeholder_pic_resize)
                .centerCrop()
                .into(user_plot_p12_neuro)

            Picasso.get()
                .load(self_profile_pic)
                .resize(20,20)
                .transform(CircleTransform())
                .placeholder(R.drawable.placeholder_pic_resize)
                .centerCrop()
                .into(user_plot_p11_neuro)

            Picasso.get()
                .load(self_profile_pic)
                .resize(20,20)
                .transform(CircleTransform())
                .placeholder(R.drawable.placeholder_pic_resize)
                .centerCrop()
                .into(user_plot_p21_neuro)

            Picasso.get()
                .load(self_profile_pic)
                .resize(20,20)
                .transform(CircleTransform())
                .placeholder(R.drawable.placeholder_pic_resize)
                .centerCrop()
                .into(user_plot_p22_neuro)


        Picasso.get()
            .load(self_profile_pic)
            .resize(20,20)
            .transform(CircleTransform())
            .placeholder(R.drawable.placeholder_pic_resize)
            .centerCrop()
            .into(user_plot_i12_neuro)

        Picasso.get()
            .load(self_profile_pic)
            .resize(20,20)
            .transform(CircleTransform())
            .placeholder(R.drawable.placeholder_pic_resize)
            .centerCrop()
            .into(user_plot_i11_neuro)

        Picasso.get()
            .load(self_profile_pic)
            .resize(20,20)
            .transform(CircleTransform())
            .placeholder(R.drawable.placeholder_pic_resize)
            .centerCrop()
            .into(user_plot_i21_neuro)

        Picasso.get()
            .load(self_profile_pic)
            .resize(20,20)
            .transform(CircleTransform())
            .placeholder(R.drawable.placeholder_pic_resize)
            .centerCrop()
            .into(user_plot_i22_neuro)



        val profile_pic = PrefsHelper().getPref<String>("matched_user_pic")
        val user_name = PrefsHelper().getPref<String>("matched_user_name")

        val age = PrefsHelper().getPref<String>("matched_user_pic")

        Picasso.get()
            .load(profile_pic)
            .resize(200,200)
            .transform(CircleTransform())
            .placeholder(R.drawable.placeholder_pic_resize)
            .centerCrop()
            .into(girl)

        Picasso.get()
            .load(profile_pic)
            .resize(19,19)
            .transform(CircleTransform())
            .placeholder(R.drawable.placeholder_pic_resize)
            .centerCrop()
            .into(match_plot_c12_desire)

        Picasso.get()
            .load(profile_pic)
            .resize(19,19)
            .transform(CircleTransform())
            .placeholder(R.drawable.placeholder_pic_resize)
            .centerCrop()
            .into(match_plot_c11_desire)

        Picasso.get()
            .load(profile_pic)
            .resize(19,19)
            .transform(CircleTransform())
            .placeholder(R.drawable.placeholder_pic_resize)
            .centerCrop()
            .into(match_plot_c21_desire)

        Picasso.get()
            .load(profile_pic)
            .resize(19,19)
            .transform(CircleTransform())
            .placeholder(R.drawable.placeholder_pic_resize)
            .centerCrop()
            .into(match_plot_c22_desire)


        Picasso.get()
            .load(profile_pic)
            .resize(19,19)
            .transform(CircleTransform())
            .placeholder(R.drawable.placeholder_pic_resize)
            .centerCrop()
            .into(match_plot_p12_desire)

        Picasso.get()
            .load(profile_pic)
            .resize(19,19)
            .transform(CircleTransform())
            .placeholder(R.drawable.placeholder_pic_resize)
            .centerCrop()
            .into(match_plot_p11_desire)

        Picasso.get()
            .load(profile_pic)
            .resize(19,19)
            .transform(CircleTransform())
            .placeholder(R.drawable.placeholder_pic_resize)
            .centerCrop()
            .into(match_plot_p21_desire)

        Picasso.get()
            .load(profile_pic)
            .resize(19,19)
            .transform(CircleTransform())
            .placeholder(R.drawable.placeholder_pic_resize)
            .centerCrop()
            .into(match_plot_p22_desire)

        Picasso.get()
            .load(profile_pic)
            .resize(19,19)
            .transform(CircleTransform())
            .placeholder(R.drawable.placeholder_pic_resize)
            .centerCrop()
            .into(match_plot_i12_desire)

        Picasso.get()
            .load(profile_pic)
            .resize(19,19)
            .transform(CircleTransform())
            .placeholder(R.drawable.placeholder_pic_resize)
            .centerCrop()
            .into(match_plot_i11_desire)

        Picasso.get()
            .load(profile_pic)
            .resize(19,19)
            .transform(CircleTransform())
            .placeholder(R.drawable.placeholder_pic_resize)
            .centerCrop()
            .into(match_plot_i21_desire)

        Picasso.get()
            .load(profile_pic)
            .resize(19,19)
            .transform(CircleTransform())
            .placeholder(R.drawable.placeholder_pic_resize)
            .centerCrop()
            .into(match_plot_i22_desire)

            boy.setOnClickListener{
                if (self_profile_pic != "") {
                    val intent = Intent(this, FullProfilePic::class.java)
                    intent.putExtra(
                        "profile_pic_url",
                        self_profile_pic
                    )
                    startActivity(intent)
                }
                else{
                    Toast.makeText(this
                        , "You haven't uploaded the profile picture", Toast.LENGTH_LONG).show()
                }
            }
            girl.setOnClickListener{
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
                        , "This user hasn't uploaded the profile picture", Toast.LENGTH_LONG).show()
                }
            }

        partner_name.text = user_name

        view_partener_profile.setOnClickListener {
            val intent = Intent(this, ViewPartnerProfile::class.java)
            intent.putExtra("profile_pic", profile_pic)
            intent.putExtra("user_name", user_name)
            intent.putExtra("user_age", age)
            startActivity(intent)
        }


        view_six_report.setOnClickListener {
            val intent = Intent(this, ViewMatchReportScreen::class.java)
            intent.putExtra("profile_pic", profile_pic)
            intent.putExtra("user_name", user_name)
            intent.putExtra("core_belief_graph", core_belief_graph)
            startActivity(intent)
        }

        back_neuro_match_report.setOnClickListener {
            super.onBackPressed();
        }





    }

    private fun displayFirst() {

        CGraph(user_desire_score.getString("c_score"), "user_desire")
        CGraph(user_neuro_score.getString("c_score"), "user_neuro")
        CGraph(matched_user_desire.getString("c_score"), "match_desire")
        CGraph(matched_user_neuro.getString("c_score"), "match_neuro")

        PGraph(user_desire_score.getString("p_score"), "user_desire")
        PGraph(user_neuro_score.getString("p_score"), "user_neuro")
        PGraph(matched_user_desire.getString("p_score"), "match_desire")
        PGraph(matched_user_neuro.getString("p_score"), "match_neuro")

        IGraph(user_desire_score.getString("i_score"), "user_desire")
        IGraph(user_neuro_score.getString("i_score"), "user_neuro")
        IGraph(matched_user_desire.getString("i_score"), "match_desire")
        IGraph(matched_user_neuro.getString("i_score"), "match_neuro")
    }

    private fun IGraph(Iscore: String?, type: String) {
        if (Iscore!= null) {
            if (type == "user_desire") {
                when (Iscore) {
                    "I11" -> user_plot_i11_desire.visibility = View.VISIBLE
                    "I12" -> user_plot_i12_desire.visibility = View.VISIBLE
                    "I21" -> user_plot_i21_desire.visibility = View.VISIBLE
                    "I22" -> user_plot_i22_desire.visibility = View.VISIBLE
                    else -> Log.d("iscore_desire_user", Iscore.toString())
                }
            }
            else if (type == "user_neuro"){
                when (Iscore) {
                    "I11" -> user_plot_i11_neuro.visibility = View.VISIBLE
                    "I12" -> user_plot_i12_neuro.visibility = View.VISIBLE
                    "I21" -> user_plot_i21_neuro.visibility = View.VISIBLE
                    "I22" -> user_plot_i22_neuro.visibility = View.VISIBLE
                    else -> Log.d("iscore_user_neuro", Iscore.toString())
                }

            }
            else if (type == "match_desire"){
                when (Iscore) {
                    "I11" -> match_plot_i11_neuro.visibility = View.VISIBLE
                    "I12" -> match_plot_i12_neuro.visibility = View.VISIBLE
                    "I21" -> match_plot_i21_neuro.visibility = View.VISIBLE
                    "I22" -> match_plot_i22_neuro.visibility = View.VISIBLE

                    else -> Log.d("iscore_match_desire", Iscore.toString())
                }

            }
            else if (type == "match_neuro"){
                when (Iscore) {
                    "I11" -> match_plot_i11_desire.visibility = View.VISIBLE
                    "I12" -> match_plot_i12_desire.visibility = View.VISIBLE
                    "I21" -> match_plot_i21_desire.visibility = View.VISIBLE
                    "I22" -> match_plot_i22_desire.visibility = View.VISIBLE
                    else -> Log.d("iscore_match_neuro", Iscore.toString())
                }

            }
        }
    }

    private fun PGraph(Pscore: String?, type: String) {
        if (Pscore!= null) {
            if (type == "user_desire") {
                when (Pscore) {
                    "P11" -> user_plot_p11_desire.visibility = View.VISIBLE
                    "P12" -> user_plot_p12_desire.visibility = View.VISIBLE
                    "P21" -> user_plot_p21_desire.visibility = View.VISIBLE
                    "P22" -> user_plot_p22_desire.visibility = View.VISIBLE
                    else -> Log.d("pscore_desire_user", Pscore.toString())
                }
            }
            else if (type == "user_neuro"){
                when (Pscore) {
                    "P11" -> user_plot_p11_neuro.visibility = View.VISIBLE
                    "P12" -> user_plot_p12_neuro.visibility = View.VISIBLE
                    "P21" -> user_plot_p21_neuro.visibility = View.VISIBLE
                    "P22" -> user_plot_p22_neuro.visibility = View.VISIBLE
                    else -> Log.d("pscore_user_neuro", Pscore.toString())
                }

            }
            else if (type == "match_desire"){
                when (Pscore) {
                    "P11" -> match_plot_p11_neuro.visibility = View.VISIBLE
                    "P12" -> match_plot_p12_neuro.visibility = View.VISIBLE
                    "P21" -> match_plot_p21_neuro.visibility = View.VISIBLE
                    "P22" -> match_plot_p22_neuro.visibility = View.VISIBLE
                    else -> Log.d("pscore_match_desire", Pscore.toString())
                }

            }
            else if (type == "match_neuro"){
                when (Pscore) {
                    "P11" -> match_plot_p11_desire.visibility = View.VISIBLE
                    "P12" -> match_plot_p12_desire.visibility = View.VISIBLE
                    "P21" -> match_plot_p21_desire.visibility = View.VISIBLE
                    "P22" -> match_plot_p22_desire.visibility = View.VISIBLE
                    else -> Log.d("pscore_match_neuro", Pscore.toString())
                }

            }
        }
    }

    private fun CGraph(Cscore: String?, type: String) {
        if (Cscore!= null) {
            if (type == "user_desire") {
                when (Cscore) {
                    "C11" -> user_plot_c11_desire.visibility = View.VISIBLE
                    "C12" -> user_plot_c12_desire.visibility = View.VISIBLE
                    "C21" -> user_plot_c21_desire.visibility = View.VISIBLE
                    "C22" -> user_plot_c22_desire.visibility = View.VISIBLE
                    else -> Log.d("cscore_desire_user", Cscore.toString())
                }
            }
            else if (type == "user_neuro"){
                when (Cscore) {
                    "C11" -> user_plot_c11_neuro.visibility = View.VISIBLE
                    "C12" -> user_plot_c12_neuro.visibility = View.VISIBLE
                    "C21" -> user_plot_c21_neuro.visibility = View.VISIBLE
                    "C22" -> user_plot_c22_neuro.visibility = View.VISIBLE
                    else -> Log.d("cscore_user_neuro", Cscore.toString())
                }

            }
            else if (type == "match_desire"){
                when (Cscore) {
                    "C11" -> match_plot_c11_neuro.visibility = View.VISIBLE
                    "C12" -> match_plot_c12_neuro.visibility = View.VISIBLE
                    "C21" -> match_plot_c21_neuro.visibility = View.VISIBLE
                    "C22" -> match_plot_c22_neuro.visibility = View.VISIBLE
                    else -> Log.d("cscore_match_desire", Cscore.toString())
                }

            }
            else if (type == "match_neuro"){
                when (Cscore) {
                    "C11" -> match_plot_c11_desire.visibility = View.VISIBLE
                    "C12" -> match_plot_c12_desire.visibility = View.VISIBLE
                    "C21" -> match_plot_c21_desire.visibility = View.VISIBLE
                    "C22" -> match_plot_c22_desire.visibility = View.VISIBLE
                    else -> Log.d("cscore_match_neuro", Cscore.toString())
                }

            }
        }
    }




    fun setupObserversGetMatchedReports()
    {

        var gson = Gson()

        this?.let {
            viewModelGetMatchedReports.getreportsResponseLiveData.observe(it, Observer {
                if (it != null) {
                    if (it.message == "Success") {


                        core_belief_graph = gson.toJson(it.data.scores.core_belief_graph)

                        user_neuro_score = JSONObject(gson.toJson(it.data.scores.match_report.user_neuro_score))

                        user_desire_score =JSONObject(gson.toJson(it.data.scores.match_report.user_desire_score))

                        matched_user_neuro = JSONObject(gson.toJson(it.data.scores.match_report.matched_user_neuro))

                        matched_user_desire = JSONObject(gson.toJson(it.data.scores.match_report.matched_user_desire))

                        displayFirst()


                    }else{
                       Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                    }
                }
            })
        }
        this?.let {
            viewModelGetMatchedReports.getreportsAPICallStatus.observe(it, Observer {
                processStatus(it)
            })
        }
    }




    private fun processStatus(resource: ResourceStatus?) {

        val resource = resource
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
                    val dialogBuilder = AlertDialog.Builder(this)

                    // set message of alert dialog
                    dialogBuilder.setMessage("Server Error. Please try again")
                        // if the dialog is cancelable
                        .setCancelable(false)
                        // positive button text and action
                        .setPositiveButton(
                            "Okay",
                            DialogInterface.OnClickListener { dialog, id ->
                                onBackPressed()
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
                StatusType.LOADING_MORE -> {
                    // CommonUtils().showSnackbar(bhhasbdsb.root, "Loading more..")
                }
                StatusType.NO_NETWORK -> {
                    Toast.makeText(this, "Please check internet connection", Toast.LENGTH_SHORT).show()

                }
                StatusType.SESSION_EXPIRED -> {
                    //CommonUtils().showSnackbar(bhhasbdsb.rootView,"session expired")
                }
            }

    }
    private  fun setupObservers(){
        viewModel.completematchResponseLiveData.observe(this, Observer {
            if(it!=null){
                if( it.message=="Success"){

                    Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                }
                else{
                    Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                }

            }
        })



        //observe API call status
        viewModel.completematchAPICallStatus.observe(this, Observer {
            processStatus1(it)
        })

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
