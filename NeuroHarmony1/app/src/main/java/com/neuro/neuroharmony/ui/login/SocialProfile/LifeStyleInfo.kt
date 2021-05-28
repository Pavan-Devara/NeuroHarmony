package com.neuro.neuroharmony.ui.login.SocialProfile

import android.content.Intent
import android.os.Bundle
import android.preference.PreferenceManager
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.gson.Gson
import com.neuro.neuroharmony.R
import com.neuro.neuroharmony.data.model.Social_profile.Lifestyleinfo.Data
import com.neuro.neuroharmony.data.model.Social_profile.Lifestyleinfo.Habits
import com.neuro.neuroharmony.data.model.Social_profile.Lifestyleinfo.LifestyleInfoResponse
import com.neuro.neuroharmony.ui.login.*
import com.neuro.neuroharmony.utils.CommonUtils
import com.neuro.neuroharmony.utils.PrefsHelper
import kotlinx.android.synthetic.main.activity_life_style_info.*


class LifeStyleInfo : BaseActivity() {

    private lateinit var viewModel: LifestyleInfoViewModel
    val gson = Gson()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_life_style_info)

        viewModel = ViewModelProviders.of(this)[LifestyleInfoViewModel::class.java]

        val intent = intent
        val json_response = PrefsHelper().getPref<String>("response")
        val response = gson.fromJson<LifestyleInfoResponse>(json_response, LifestyleInfoResponse::class.java)
        main_code(response)

        setupListernersSubmit(response.data)
        setupObserverssSubmit()
    }


    private fun id_to_be_sent(
        full_list: List<Habits>,
        selectedItem: Any
    ): String {
        val  list_choice:MutableList<String> = arrayListOf()
        val list_id:MutableList<String> = arrayListOf()
        for (i in 0 until full_list.size) {
            val obj = full_list[i]
            list_choice.add(obj.choice)
            list_id.add(obj.id)
        }
        var position = list_choice.indexOf(selectedItem.toString())
        var selected_id = list_id.get(position)
        return selected_id
    }

    private fun main_code(it: LifestyleInfoResponse) {

        lifestyle_function(alcoholSpinner, it.data.choice.alcohol,it.data.userChoice.alcohol)
        lifestyle_function(dietSpinner, it.data.choice.dietary,it.data.userChoice.dietary)
        lifestyle_function(smokeSpinner, it.data.choice.smoke,it.data.userChoice.smoke)
        lifestyle_function(petSpinner, it.data.choice.pet,it.data.userChoice.pet)
        lifestyle_function(sunglassSpinner, it.data.choice.sunglasses,it.data.userChoice.sunglasses)
        lifestyle_function(workpostmarriageSpinner, it.data.choice.postWorking,it.data.userChoice.postWorking)
    }

    private fun id_to_recieved(
        full_list1: List<Habits>,
        selectedOption: Any
    ): String {
        val  list_choice:MutableList<String> = arrayListOf()
        val list_id:MutableList<String> = arrayListOf()
        for (i in 0 until full_list1.size) {
            val obj = full_list1[i]
            list_choice.add(obj.choice)
            list_id.add(obj.id)
        }
        var position = list_id.indexOf(selectedOption.toString())
        var selected_id = list_choice.get(position)
        return selected_id
    }

    private fun lifestyle_function(
        spinner_id: Spinner,
        lifestyle_full: List<Habits>,
        selected_option: Any
    ) {
        val list_familyinfo: MutableList<String> = arrayListOf()
        if (selected_option == null) {
            list_familyinfo.add("Select")
            for (i in 0 until lifestyle_full.size) {
                val obj = lifestyle_full[i]
                list_familyinfo.add(obj.choice)
            }
        } else {
            var first_choice = id_to_recieved(lifestyle_full,selected_option)
            list_familyinfo.add(first_choice)
            for (i in 0 until lifestyle_full.size) {
                val obj = lifestyle_full[i]
                if (obj.id != selected_option) {
                    list_familyinfo.add(obj.choice)
                }
            }

        }
        val adapter = ArrayAdapter(
            this,
            com.neuro.neuroharmony.R.layout.color_spinner_layout,
            list_familyinfo
        )
        adapter.setDropDownViewResource(com.neuro.neuroharmony.R.layout.spinner_dropdown_layout)
        spinner_id?.setAdapter(adapter)
    }



    private fun setupListernersSubmit(data: Data) {
        SaveLifeStyleInfobutton?.setOnClickListener {
            if (dietSpinner.selectedItem != "Select") {
                if (smokeSpinner.selectedItem != "Select") {
                    if (alcoholSpinner.selectedItem != "Select") {
                        if (petSpinner.selectedItem != "Select") {
                            if (sunglassSpinner.selectedItem != "Select") {
                                if (workpostmarriageSpinner.selectedItem != "Select") {

                                    var alochol = id_to_be_sent(
                                        data.choice.alcohol,
                                        alcoholSpinner.selectedItem
                                    )
                                    var dietary =
                                        id_to_be_sent(data.choice.dietary, dietSpinner.selectedItem)
                                    var pet =
                                        id_to_be_sent(data.choice.pet, petSpinner.selectedItem)
                                    var postwork = id_to_be_sent(
                                        data.choice.postWorking,
                                        workpostmarriageSpinner.selectedItem
                                    )
                                    var smoke =
                                        id_to_be_sent(data.choice.smoke, smokeSpinner.selectedItem)
                                    var sunglass = id_to_be_sent(
                                        data.choice.sunglasses,
                                        sunglassSpinner.selectedItem
                                    )

                                    viewModel.lifestyleSubmitInfoLiveData(
                                        alochol,
                                        dietary,
                                        pet, postwork, smoke, sunglass
                                    )
                                } else {
                                    Toast.makeText(
                                        this,
                                        "Please select your working preference post marriage",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            } else {
                                Toast.makeText(
                                    this,
                                    "Please select your glasses preference",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        } else {
                            Toast.makeText(
                                this,
                                "Please select your pets preference",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    } else {
                        Toast.makeText(
                            this,
                            "Please select your drinking preference",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } else {
                    Toast.makeText(
                        this,
                        "Please select your smoking preference",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } else {
                Toast.makeText(this, "Please select your dietary preference",
                    Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }




    private fun setupObserverssSubmit() {
        viewModel.lifestyleSubmitInfoResponseLiveData.observe(this, Observer {
            if (it!=null){
                if (it.message == "Success"){
                    PrefsHelper().savePref("Account_Status",8)
                    PrefsHelper().savePref("Reference",8)
                    Toast.makeText(this, "LifeStyle Info updated successfully", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, HomePage1::class.java)
                    startActivity(intent)
                } else{
                    Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                }
            }
        })
        viewModel.lifestyleSubmitInfoApiCallStatus.observe(this, Observer {
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
                CommonUtils().showSnackbar(SaveLifeStyleInfobutton.rootView, "Please try again")
                dismissDialog()


            }
            StatusType.LOADING_MORE -> {
                // CommonUtils().showSnackbar(binding.root, "Loading more..")
            }
            StatusType.NO_NETWORK -> {
                Toast.makeText(this, "Please check internet connection", Toast.LENGTH_SHORT).show()
            }
            StatusType.SESSION_EXPIRED -> {
                CommonUtils().showSnackbar(SaveLifeStyleInfobutton.rootView, "session expired")
            }
        }

    }

    override fun onBackPressed() {

        val prefs = PreferenceManager.getDefaultSharedPreferences(this)
        if (prefs.contains("Reference")){
            if (PrefsHelper().getPref<Int>("Reference")!=8) {

            }
            else{
                super.onBackPressed()
            }
        }
        else{

        }

    }
}
