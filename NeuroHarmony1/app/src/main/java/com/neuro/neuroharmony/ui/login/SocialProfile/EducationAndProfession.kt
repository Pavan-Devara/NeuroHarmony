package com.neuro.neuroharmony.ui.login.SocialProfile

import android.content.Intent
import android.os.Bundle
import android.preference.PreferenceManager
import android.text.Editable
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.gson.Gson
import com.neuro.neuroharmony.R
import com.neuro.neuroharmony.data.model.Social_profile.Educationinfo.Data
import com.neuro.neuroharmony.data.model.Social_profile.Educationinfo.EducationProfessionResponse
import com.neuro.neuroharmony.data.model.Social_profile.Educationinfo.EducationProfStatus
import com.neuro.neuroharmony.ui.login.*
import com.neuro.neuroharmony.utils.CommonUtils
import com.neuro.neuroharmony.utils.PrefsHelper
import kotlinx.android.synthetic.main.activity_education_and_profession.*
import kotlinx.android.synthetic.main.activity_family_info.*

class EducationAndProfession : BaseActivity() {
    lateinit var viewModel:EducationInfoViewModel
    private lateinit var viewModel2: LifestyleInfoViewModel
    val gson = Gson()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_education_and_profession)

        viewModel = ViewModelProviders.of(this)[EducationInfoViewModel::class.java]
        viewModel2 = ViewModelProviders.of(this)[LifestyleInfoViewModel::class.java]

        val intent = intent
        val json_response = PrefsHelper().getPref<String>("response")
        val response = gson.fromJson<EducationProfessionResponse>(json_response, EducationProfessionResponse::class.java)
        if (response.data.userChoice.employmentStatusDescription != null) {
            employmentstatusedittext.text = Editable.Factory.getInstance().newEditable(response.data.userChoice.employmentStatusDescription.toString())
        }
        main_code(response)
        setupListernersSubmit(response.data)
        setupObserversSubmit()
        setupobserverslifestyle()
        setupobserversEducationprog()
    }

    private fun setupobserversEducationprog() {

    }


    private fun id_to_be_sent(
        full_list: List<EducationProfStatus>,
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

    private fun id_to_recieved(
        full_list1: List<EducationProfStatus>,
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


    private fun setupListernersSubmit(data: Data) {

        SaveEducationprofInfobutton?.setOnClickListener {
            if(profqualificationSpinner.selectedItem != "Select") {
                if (employmentstatusSpinner.selectedItem != "Select") {
                    if (employmentstatusedittext.text.trim().isNotEmpty()) {
                        if (presentprofSpinner.selectedItem != "Select") {
                            if (presentemployerSpinner.selectedItem != "Select") {
                                if (totalyrSpinner.selectedItem != "Select") {
                                    if (salaryrangeSpinner.selectedItem != "Select") {
                                        var employmentstatus = id_to_be_sent(
                                            data.choice.employmentStatus,
                                            employmentstatusSpinner.selectedItem
                                        )
                                        var qualification = id_to_be_sent(
                                            data.choice.qualification,
                                            profqualificationSpinner.selectedItem
                                        )
                                        var presentprofession = id_to_be_sent(
                                            data.choice.presentProfession,
                                            presentprofSpinner.selectedItem
                                        )
                                        var presentemployer = id_to_be_sent(
                                            data.choice.presentEmployer,
                                            presentemployerSpinner.selectedItem
                                        )
                                        var workexperience = id_to_be_sent(
                                            data.choice.workExperience,
                                            totalyrSpinner.selectedItem
                                        )
                                        var salaryrange = id_to_be_sent(
                                            data.choice.salaryRange,
                                            salaryrangeSpinner.selectedItem
                                        )

                                        viewModel.educationProfSubmitInfoLiveData(
                                            employmentstatus,
                                            employmentstatusedittext.text.trim().toString(),
                                            presentemployer,
                                            presentprofession,
                                            qualification,
                                            salaryrange,
                                            workexperience
                                        )
                                    } else {
                                        Toast.makeText(this,"Please select your salary range", Toast.LENGTH_SHORT).show()
                                    }
                                } else {
                                    Toast.makeText(this,"Please select your work experience", Toast.LENGTH_SHORT).show()
                                }
                            } else {
                                Toast.makeText(this,"Please select your present employer", Toast.LENGTH_SHORT).show()
                            }
                        } else {
                            Toast.makeText(this,"Please select your present profession", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        Toast.makeText(this,"Please enter your employment status description", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this,"Please select your employment status", Toast.LENGTH_SHORT).show()
                }
            }
            else{
                Toast.makeText(this, "Please select your professional qualification", Toast.LENGTH_SHORT).show()
            }
        }
    }


    private fun main_code(it: EducationProfessionResponse) {
        new_function(it.data.choice.employmentStatus,employmentstatusSpinner,it.data.userChoice.employmentStatus)
        new_function(it.data.choice.qualification,profqualificationSpinner,it.data.userChoice.qualification)
        new_function(it.data.choice.presentProfession,presentprofSpinner,it.data.userChoice.presentProfession)
        new_function(it.data.choice.presentEmployer,presentemployerSpinner,it.data.userChoice.presentEmployer)
        new_function(it.data.choice.workExperience,totalyrSpinner,it.data.userChoice.workExperience)
        new_function(it.data.choice.salaryRange,salaryrangeSpinner,it.data.userChoice.salaryRange)
    }

    private fun new_function(
        full_list_edu: List<EducationProfStatus>,
        spinner_id: Spinner,
        selected_option: Any
    ) {
        val  list_familyinfo:MutableList<String> = arrayListOf()
        if (selected_option == null)
        {
            list_familyinfo.add("Select")
            for (i in 0 until full_list_edu.size) {
                val obj = full_list_edu[i]
                list_familyinfo.add(obj.choice)
            }
        }
        else{
            var first_choice = id_to_recieved(full_list_edu,selected_option)
            list_familyinfo.add(first_choice)
            for (i in 0 until full_list_edu.size) {
                val obj = full_list_edu[i]
                if (obj.id != selected_option){
                    list_familyinfo.add(obj.choice)
                }
            }

        }
        val adapter = ArrayAdapter(
            this,
            R.layout.color_spinner_layout,
            list_familyinfo
        )
        adapter.setDropDownViewResource(com.neuro.neuroharmony.R.layout.spinner_dropdown_layout)
        spinner_id?.setAdapter(adapter)
    }


    private fun setupobserverslifestyle(){
        viewModel2.lifestyleInfoResponseLiveData.observe(this, Observer {
            if(it!=null){
                if(it.message=="Success"){


                    val intent = Intent(this, LifeStyleInfo::class.java)
                    val response = gson.toJson(it)
                    intent.putExtra("response",response)
                    PrefsHelper().savePref("response",response)
                    PrefsHelper().savePref("Account_Status",7)
                    startActivity(intent)
                }
                else{
                    Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                }
            }
        })
        viewModel2.lifestyleInfoApiCallStatus.observe(this, Observer {
            processStatus(it)
        })
    }





    private fun setupObserversSubmit() {
        viewModel.educationprofsubmitResponseLiveData.observe(this, Observer {
            if(it!=null){
                if(it.message=="Success"){

                    Toast.makeText(this, "Education and Profession Info updated successfully", Toast.LENGTH_LONG).show()
                    viewModel2.lifestyleInfoLiveData(null)
                }
                else{
                    Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                }
            }
        })
        viewModel.educationprofsubmitApiCallStatus.observe(this, Observer {
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
                CommonUtils().showSnackbar(SaveFamilyInfobutton.rootView, "Please try again")
                dismissDialog()


            }
            StatusType.LOADING_MORE -> {
                // CommonUtils().showSnackbar(binding.root, "Loading more..")
            }
            StatusType.NO_NETWORK -> {
                Toast.makeText(this, "Please check internet connection", Toast.LENGTH_SHORT).show()
            }
            StatusType.SESSION_EXPIRED -> {
                CommonUtils().showSnackbar(SaveFamilyInfobutton.rootView, "session expired")
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
