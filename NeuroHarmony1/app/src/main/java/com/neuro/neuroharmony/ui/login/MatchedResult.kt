package com.neuro.neuroharmony.ui.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import com.neuro.neuroharmony.R
import com.neuro.neuroharmony.utils.CommonUtils
import com.neuro.neuroharmony.utils.PrefsHelper
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_matched_result.*
import org.json.JSONArray

class MatchedResult : BaseActivity() {
    private lateinit var viewModel: MatchedUsersViewModel
    var gson = Gson()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_matched_result)
        viewModel = ViewModelProviders.of(this)[MatchedUsersViewModel::class.java]

        val intent = intent
        val array = intent.getStringExtra("jsonArray")
        val total_matched = intent.getIntExtra("totalMatched", 0)
        val social_filter_active = intent.getBooleanExtra("social_filter_active", false)

        back_match_results_menu.setOnClickListener {
            onBackPressed()
        }

        setUpListeners(total_matched, array, social_filter_active)
    }



    private fun setUpListeners(
        totalMatched: Int,
        array: String?,
        socialFilterActive: Boolean
    ) {
        val array = JSONArray(array)
        if (totalMatched>0){
            count_matched_results.text = totalMatched.toString()

            for (i in 0 until array.length()){
                when (array.getJSONObject(i).get("group").toString()){
                    "Group_1" -> {
                        available_count.text = array.getJSONObject(i).getInt("count").toString()
                        active_count.text = array.getJSONObject(i).getInt("active_users_count").toString()
                        if (socialFilterActive) {
                            filtered.visibility = View.VISIBLE
                            filtered_count.visibility = View.VISIBLE
                            filtered_count.text =
                                array.getJSONObject(i).getInt("social_filter_count").toString()
                        }
                        else {
                            filtered.visibility = View.GONE
                            filtered_count.visibility = View.GONE
                        }
                    }
                    "Group_2" -> {
                        available_count2.text = array.getJSONObject(i).getInt("count").toString()
                        active_count2.text = array.getJSONObject(i).getInt("active_users_count").toString()
                        if (socialFilterActive) {
                            filtered_2.visibility = View.VISIBLE
                            filtered_count2.visibility = View.VISIBLE
                            filtered_count2.text =
                                array.getJSONObject(i).getInt("social_filter_count").toString()
                        }
                        else {
                            filtered_2.visibility = View.GONE
                            filtered_count2.visibility = View.GONE
                        }}
                    "Group_3" -> {
                        available_count3.text = array.getJSONObject(i).getInt("count").toString()
                        active_count3.text = array.getJSONObject(i).getInt("active_users_count").toString()
                        if (socialFilterActive) {
                            filtered_3.visibility = View.VISIBLE
                            filtered_count3.visibility = View.VISIBLE
                            filtered_count3.text =
                                array.getJSONObject(i).getInt("social_filter_count").toString()
                        }
                        else {
                            filtered_3.visibility = View.GONE
                            filtered_count3.visibility = View.GONE
                        }}
                    "Group_4" -> {
                        available_count4.text = array.getJSONObject(i).getInt("count").toString()
                        active_count4.text = array.getJSONObject(i).getInt("active_users_count").toString()
                        if (socialFilterActive) {
                            filtered_4.visibility = View.VISIBLE
                            filtered_count4.visibility = View.VISIBLE
                            filtered_count4.text =
                                array.getJSONObject(i).getInt("social_filter_count").toString()
                        }
                        else {
                            filtered_4.visibility = View.GONE
                            filtered_count4.visibility = View.GONE
                        }}
                    "Group_5" -> {
                        available_count5.text = array.getJSONObject(i).getInt("count").toString()
                        active_count5.text = array.getJSONObject(i).getInt("active_users_count").toString()
                        if (socialFilterActive) {
                            filtered_5.visibility = View.VISIBLE
                            filtered_count5.visibility = View.VISIBLE
                            filtered_count5.text =
                                array.getJSONObject(i).getInt("social_filter_count").toString()
                        }
                        else {
                            filtered_5.visibility = View.GONE
                            filtered_count5.visibility = View.GONE
                        }}
                    "Group_6" -> {
                        available_count6.text = array.getJSONObject(i).getInt("count").toString()
                        active_count6.text = array.getJSONObject(i).getInt("active_users_count").toString()
                        if (socialFilterActive) {
                            filtered_6.visibility = View.VISIBLE
                            filtered_count6.visibility = View.VISIBLE
                            filtered_count6.text =
                                array.getJSONObject(i).getInt("social_filter_count").toString()
                        }
                        else {
                            filtered_6.visibility = View.GONE
                            filtered_count6.visibility = View.GONE
                        }}
                    "Group_7" -> {
                        available_count7.text = array.getJSONObject(i).getInt("count").toString()
                        active_count7.text = array.getJSONObject(i).getInt("active_users_count").toString()
                        if (socialFilterActive) {
                            filtered_7.visibility = View.VISIBLE
                            filtered_count7.visibility = View.VISIBLE
                            filtered_count7.text =
                                array.getJSONObject(i).getInt("social_filter_count").toString()
                        }
                        else {
                            filtered_7.visibility = View.GONE
                            filtered_count7.visibility = View.GONE
                        }}
                    "Group_8" -> {
                        available_count8.text = array.getJSONObject(i).getInt("count").toString()
                        active_count8.text = array.getJSONObject(i).getInt("active_users_count").toString()
                        if (socialFilterActive) {
                            filtered_8.visibility = View.VISIBLE
                            filtered_count8.visibility = View.VISIBLE
                            filtered_count8.text =
                                array.getJSONObject(i).getInt("social_filter_count").toString()
                        }
                        else {
                            filtered_8.visibility = View.GONE
                            filtered_count8.visibility = View.GONE
                        }}
                    "Group_9" -> {
                        available_count9.text = array.getJSONObject(i).getInt("count").toString()
                        active_count9.text = array.getJSONObject(i).getInt("active_users_count").toString()
                        if (socialFilterActive) {
                            filtered_9.visibility = View.VISIBLE
                            filtered_count9.visibility = View.VISIBLE
                            filtered_count9.text =
                                array.getJSONObject(i).getInt("social_filter_count").toString()
                        }
                        else {
                            filtered_9.visibility = View.GONE
                            filtered_count9.visibility = View.GONE
                        }}
                    "Group_10" -> {
                        available_count10.text = array.getJSONObject(i).getInt("count").toString()
                        active_count10.text = array.getJSONObject(i).getInt("active_users_count").toString()
                        if (socialFilterActive) {
                            filtered_10.visibility = View.VISIBLE
                            filtered_count10.visibility = View.VISIBLE
                            filtered_count10.text =
                                array.getJSONObject(i).getInt("social_filter_count").toString()
                        }
                        else {
                            filtered_10.visibility = View.GONE
                            filtered_count10.visibility = View.GONE
                        }}
                    else ->{available_count.text = "0"
                        available_count2.text = "0"
                        available_count3.text = "0"
                        available_count4.text = "0"
                        available_count5.text = "0"
                        available_count6.text = "0"
                        available_count7.text = "0"
                        available_count8.text = "0"
                        available_count9.text = "0"
                        available_count10.text = "0"
                        active_count.text = "0"
                        active_count2.text = "0"
                        active_count3.text = "0"
                        active_count4.text = "0"
                        active_count5.text = "0"
                        active_count6.text = "0"
                        active_count7.text = "0"
                        active_count8.text = "0"
                        active_count9.text = "0"
                        active_count10.text = "0"}
                }
            }

        }else{
            count_matched_results.text = totalMatched.toString()
        }

        val totalcount0 = array.getJSONObject(0).getInt("count")
        val totalcount1 = array.getJSONObject(1).getInt("count")
        val totalcount2 = array.getJSONObject(2).getInt("count")
        val totalcount3 = array.getJSONObject(3).getInt("count")
        val totalcount4 = array.getJSONObject(4).getInt("count")
        val totalcount5 = array.getJSONObject(5).getInt("count")
        val totalcount6 = array.getJSONObject(6).getInt("count")
        val totalcount7 = array.getJSONObject(7).getInt("count")
        val totalcount8 = array.getJSONObject(8).getInt("count")
        val totalcount9 = array.getJSONObject(9).getInt("count")

        viewMatchedUsersResults.setOnClickListener {
            setUpObservers()
        }
    }

    private fun setUpObservers() {

        val jsonArray = ""
        val intent = Intent(this, NeuroMatchedUsers::class.java)
        PrefsHelper().savePref("jsonArray", jsonArray)
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
                CommonUtils().showSnackbar(viewMatchedUsersResults.rootView,"Please try again. Server error")
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

    override fun onBackPressed() {
        val intent = Intent(this,HomePage1::class.java)
        startActivity(intent)
    }
}
