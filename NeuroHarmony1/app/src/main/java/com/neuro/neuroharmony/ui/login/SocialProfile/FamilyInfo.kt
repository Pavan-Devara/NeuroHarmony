package com.neuro.neuroharmony.ui.login.SocialProfile

import android.content.Intent
import android.os.Bundle
import android.preference.PreferenceManager
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.neuro.neuroharmony.data.model.Social_profile.Familyinfo.FamilyInfoResponse
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.google.gson.Gson
import com.neuro.neuroharmony.data.model.Social_profile.Familyinfo.Data
import com.neuro.neuroharmony.data.model.Social_profile.Familyinfo.Familystatus
import com.neuro.neuroharmony.ui.login.*
import com.neuro.neuroharmony.utils.CommonUtils
import com.neuro.neuroharmony.utils.PrefsHelper
import kotlinx.android.synthetic.main.activity_family_info.*


class FamilyInfo : BaseActivity() {
    lateinit var viewModel:FamilyInfoViewModel
    val gson = Gson()
    private lateinit var viewModel2: EducationInfoViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.neuro.neuroharmony.R.layout.activity_family_info)


        viewModel = ViewModelProviders.of(this)[FamilyInfoViewModel::class.java]
        viewModel2 = ViewModelProviders.of(this)[EducationInfoViewModel::class.java]
        val intent = intent
        val json_response = PrefsHelper().getPref<String>("response")
        val response = gson.fromJson<FamilyInfoResponse>(json_response,FamilyInfoResponse::class.java)

        main_code(response)
        setupListernersSubmit(response.data)
        setupObserversSubmit()
        setupobserverseducation()


    }

    private fun setupobserverseducation(){
        viewModel2.educationprofResponseLiveData.observe(this, Observer {
            if(it!=null){
                if(it.message=="Success"){

                    val intent = Intent(this, EducationAndProfession::class.java)
                    val response = gson.toJson(it)
                    PrefsHelper().savePref("response",response)
                    intent.putExtra("response",response)
                    PrefsHelper().savePref("Account_Status",6)
                    startActivity(intent)
                }
                else{
                    Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                }
            }
        })
        viewModel2.educationprofApiCallStatus.observe(this, Observer {
            processStatus(it)
        })
    }

    private fun setupObserversSubmit() {
        viewModel.familyInfoSubmitResponseLiveData.observe(this, Observer {
            if(it!=null){
                if(it.message=="Success"){


                    Toast.makeText(this, "Family Info updated successfully", Toast.LENGTH_LONG).show()
                    viewModel2.educationProfLiveData(null)
                }
                else{
                    Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                }
            }
        })
        viewModel.familyInfoSubmitApiCallStatus.observe(this, Observer {
            processStatus(it)
        })
    }


    private fun id_to_be_sent(
        full_list: List<Familystatus>,
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
        full_list1: List<Familystatus>,
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

        SaveFamilyInfobutton?.setOnClickListener {
            if (fatherstatusSpinner.selectedItem != "Select") {
                if (fathercompanySpinner.selectedItem != "Select") {
                    if (fatherdesignationSpinner.selectedItem != "Select") {
                        if (motherstatusSpinner.selectedItem != "Select") {
                            if (mothercompanySpinner.selectedItem != "Select") {
                                if (motherdesignationSpinner.selectedItem != "Select") {
                                    if (nosibilingSpinner.selectedItem != "Select") {
                                        if (familytypeSpinner.selectedItem != "Select") {
                                            if (familyvalueSpinner.selectedItem != "Select") {
                                                if (familyincomeSpinner.selectedItem != "Select") {

                                                    var father_status_selected =
                                                        id_to_be_sent(
                                                            data.choice.fatherStatus,
                                                            fatherstatusSpinner.selectedItem
                                                        )
                                                    var father_company_selected =
                                                        id_to_be_sent(
                                                            data.choice.fatherCompany,
                                                            fathercompanySpinner.selectedItem
                                                        )
                                                    var father_designation_selected = id_to_be_sent(
                                                        data.choice.fatherDesignation,
                                                        fatherdesignationSpinner.selectedItem
                                                    )
                                                    var mother_status_selected =
                                                        id_to_be_sent(
                                                            data.choice.motherStatus,
                                                            motherstatusSpinner.selectedItem
                                                        )
                                                    var mother_company_selected =
                                                        id_to_be_sent(
                                                            data.choice.motherCompany,
                                                            mothercompanySpinner.selectedItem
                                                        )
                                                    var mother_designation_selected = id_to_be_sent(
                                                        data.choice.motherDesignation,
                                                        motherdesignationSpinner.selectedItem
                                                    )
                                                    var sibling_selected =
                                                        id_to_be_sent(
                                                            data.choice.siblings,
                                                            nosibilingSpinner.selectedItem
                                                        )
                                                    var family_type_selected =
                                                        id_to_be_sent(
                                                            data.choice.familyType,
                                                            familytypeSpinner.selectedItem
                                                        )
                                                    var faily_value_selected =
                                                        id_to_be_sent(
                                                            data.choice.familyValue,
                                                            familyvalueSpinner.selectedItem
                                                        )
                                                    var family_income_selected =
                                                        id_to_be_sent(
                                                            data.choice.familyIncome,
                                                            familyincomeSpinner.selectedItem
                                                        )

                                                    viewModel.familyInfoSubmitLiveData(
                                                        family_income_selected,
                                                        family_type_selected,
                                                        faily_value_selected,
                                                        father_company_selected,
                                                        father_designation_selected,
                                                        father_status_selected,
                                                        mother_company_selected,
                                                        mother_designation_selected,
                                                        mother_status_selected,
                                                        sibling_selected
                                                    )
                                                } else {
                                                    Toast.makeText(
                                                        this,
                                                        "Please select your family income",
                                                        Toast.LENGTH_SHORT
                                                    ).show()
                                                }
                                            } else {
                                                Toast.makeText(
                                                    this,
                                                    "Please select your family value",
                                                    Toast.LENGTH_SHORT
                                                ).show()
                                            }
                                        } else {
                                            Toast.makeText(
                                                this,
                                                "Please select your family type",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        }
                                    } else {
                                        Toast.makeText(
                                            this,
                                            "Please select your number of siblings",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                } else {
                                    Toast.makeText(
                                        this,
                                        "Please select your mother's designation",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            } else {

                                Toast.makeText(
                                    this,
                                    "Please select your mother's company",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        } else {
                            Toast.makeText(
                                this,
                                "Please select your mother's status",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    } else {
                        Toast.makeText(
                            this,
                            "Please select your father's designation",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } else {
                    Toast.makeText(this, "Please select your father's company", Toast.LENGTH_SHORT)
                        .show()
                }
            } else {
                Toast.makeText(this, "Please select the father's status", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun main_code(it: FamilyInfoResponse) {
        new_function(it.data.choice.fatherStatus,fatherstatusSpinner,it.data.userChoice.fatherStatus)
        new_function(it.data.choice.fatherCompany,fathercompanySpinner,it.data.userChoice.fatherCompany)
        new_function(it.data.choice.fatherDesignation,fatherdesignationSpinner,it.data.userChoice.fatherDesignation)
        new_function(it.data.choice.motherStatus,motherstatusSpinner,it.data.userChoice.motherStatus)
        new_function(it.data.choice.motherCompany,mothercompanySpinner,it.data.userChoice.motherCompany)
        new_function(it.data.choice.motherDesignation,motherdesignationSpinner,it.data.userChoice.motherDesignation)
        new_function(it.data.choice.siblings,nosibilingSpinner,it.data.userChoice.siblings)
        new_function(it.data.choice.familyType,familytypeSpinner,it.data.userChoice.familyType)
        new_function(it.data.choice.familyValue,familyvalueSpinner,it.data.userChoice.familyValue)
        new_function(it.data.choice.familyIncome,familyincomeSpinner,it.data.userChoice.familyIncome)
    }

    private fun new_function(
        familystatus: List<Familystatus>,
        spinner_id: Spinner,
        selected_option: Any
    ) {
        val  list_familyinfo:MutableList<String> = arrayListOf()
        if (selected_option == null)
        {
            list_familyinfo.add("Select")
            for (i in 0 until familystatus.size) {
                val obj = familystatus[i]
                list_familyinfo.add(obj.choice)
            }
        }
        else{
            var first_choice = id_to_recieved(familystatus,selected_option)
            list_familyinfo.add(first_choice.toString())
            for (i in 0 until familystatus.size) {
                val obj = familystatus[i]
                if (obj.id != selected_option){
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
