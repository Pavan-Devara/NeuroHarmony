package com.neuro.neuroharmony.ui.login.AfilliateWorkFlow

import android.content.Intent
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.view.View

import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.gson.Gson
import com.neuro.neuroharmony.R
import com.neuro.neuroharmony.data.model.AffiliateWorkflow.AccountTypeX

import com.neuro.neuroharmony.ui.login.*
import com.neuro.neuroharmony.utils.PrefsHelper
import kotlinx.android.synthetic.main.activity_bank_details_affiliate.*

import org.json.JSONArray
import android.view.ViewTreeObserver
import android.widget.*
import com.abdeveloper.library.MultiSelectDialog
import com.abdeveloper.library.MultiSelectModel
import com.neuro.neuroharmony.ui.login.Regions.RegionsViewModel
import kotlinx.android.synthetic.main.activity_affiliate_form.*
import kotlinx.android.synthetic.main.activity_affiliate_home_page.view.*
import kotlin.math.roundToInt

import android.widget.TextView



class BankDetailsAffiliate : BaseActivity() , AdapterView.OnItemSelectedListener {

    var incoming_data = ""
    var incoming_data2 = ""
    var gson = Gson()

    private lateinit var viewModel: GetAffiliateDetailsViewModel
    private lateinit var viewModel2: AffiliateFormViewModel
    private lateinit var viewModelRegion: RegionsViewModel

    var first_namee = ""
    var last_namee = ""
    var city_name = ""
    var pin_name = ""
    var street_name = ""
    var country_name = ""
    var country_id = 0
    var state_id = 0
    var city_id = 0
    var state_name = ""
    var value_spinner = ""

    var selected_country: Any? = null
    var selected_state: Any? = null
    var selected_city: Any? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bank_details_affiliate)

        val mySPrefs = PreferenceManager.getDefaultSharedPreferences(this)
        val editor = mySPrefs.edit()
        editor.remove("country_id")
        editor.remove("state_id")
        editor.remove("city_id")
        editor.apply()

        first_namee = intent.getStringExtra("first_name_affiliate")
        last_namee = intent.getStringExtra("last_name_affiliate")
        city_name = intent.getStringExtra("city_affiliate")
        pin_name = intent.getStringExtra("pin_affiliate")
        street_name = intent.getStringExtra("street_affiliate")
        state_name = intent.getStringExtra("state_affiliate")
        value_spinner = intent.getStringExtra("value_spinner")
        country_name = intent.getStringExtra("country_affiliate")
        country_id = intent.getIntExtra("country_id", 0)
        state_id = intent.getIntExtra("state_id", 0)
        city_id = intent.getIntExtra("city_id", 0)


        if (value_spinner=="2") {
            pan_text_view.visibility = View.GONE
            pan_edittext.visibility = View.GONE
            org_linear.visibility = View.VISIBLE
        }

        viewModel = ViewModelProviders.of(this)[GetAffiliateDetailsViewModel::class.java]
        viewModelRegion = ViewModelProviders.of(this)[RegionsViewModel::class.java]
        viewModel.getaffiliatevm()
        setupObservers()
        setupObserversStates()
        setupObserversCities()

        val marquee1 = findViewById(R.id.marquee1) as TextView
        marquee1.setSelected(true);


        orgcountry_edittext.setOnClickListener {
            viewModelRegion.countriesvm()
        }

        ORGANIZATION_state_edittext.setOnClickListener {
            if(mySPrefs.contains("country_id")) {
                viewModelRegion.statesvm()
            }else{
                Toast.makeText(this, "Please select a country to continue", Toast.LENGTH_SHORT).show()
            }
        }

        orgcity_edittext.setOnClickListener {
            if (mySPrefs.contains("state_id")) {
                viewModelRegion.citiesvm()
            }else{
                Toast.makeText(this, "Please select a state to continue", Toast.LENGTH_SHORT).show()
            }
        }

    }

    private  fun setupObservers(){
        viewModel.getAffiliateDetailsResponseLiveData.observe(this, Observer {
            if(it!=null){
                if( it.message=="Success"){
                    new_function2(it.data.choice.accountTypes)
                    val gateway_account = it.data.userChoice.gateway_fund_account_id
                    val gateway_contact = it.data.userChoice.gateway_contact_id

                    if (gateway_account==null || gateway_contact==null)
                    {
                        marquee1.visibility=View.VISIBLE
                    }else{
                        marquee1.visibility = View.GONE
                    }

                    if (PrefsHelper().getPref<Int>("Account_Status")!=10){
                        //new
                        continue_bank_details.setOnClickListener {
                            val intent = Intent(this,AffiliateSource::class.java)

                            intent.putExtra("value_spinner",value_spinner)
                            intent.putExtra("first_name_affiliate",first_namee)
                            intent.putExtra("last_name_affiliate",last_namee)
                            intent.putExtra("street_affiliate",street_name)
                            intent.putExtra("city_affiliate",city_name)
                            intent.putExtra("pin_affiliate",pin_name)
                            intent.putExtra("state_affiliate",state_name)
                            intent.putExtra("country_affiliate", country_name)
                            intent.putExtra("country_id", country_id)
                            intent.putExtra("state_id", state_id)
                            intent.putExtra("city_id", city_id)
                            intent.putExtra("bank_name",bankname_edittext.text.trim().toString())
                            intent.putExtra("bank_ifsc",ifsc_edittext.text.trim().toString())
                            intent.putExtra("bank_number",account_number_edittext.text.trim().toString())
                            intent.putExtra("bank_type",account_type_spinner.selectedItemId.toInt())
                            Log.d("bankkkkk",account_type_spinner.selectedItemId.toString())

                            if (value_spinner=="2"){


                                if (bankname_edittext.text.trim().toString().isNotEmpty()){
                                    if (account_type_spinner.selectedItemId>0){
                                        if (account_number_edittext.text.length>=5 &&
                                            account_number_edittext.text.length<=35) {
                                            if (account_number_edittext.text.trim().toString()==account_number_edittext_confirm.text.trim().toString()) {
                                                if (ifsc_edittext.text.length==11) {
                                                    if (ORGANIZATION_name_edittext.text.trim().toString().isNotEmpty()) {
                                                        if (orgcountry_edittext.text.trim().toString() != "Select") {
                                                            if (ORGANIZATION_state_edittext.text.trim().toString() != "Select") {
                                                                if (orgcity_edittext.text.trim().toString() != "Select") {
                                                                    if (tan_edittext.text.trim().toString().isNotEmpty()) {
                                                                        if (gst_edittext.text.trim().toString().isNotEmpty()) {

                                                                            intent.putExtra(
                                                                                "org_city",
                                                                                orgcity_edittext.text.trim().toString()
                                                                            )
                                                                            intent.putExtra(
                                                                                "org_country",
                                                                                orgcountry_edittext.text.trim().toString()
                                                                            )
                                                                            intent.putExtra(
                                                                                "org_name",
                                                                                ORGANIZATION_name_edittext.text.trim().toString()
                                                                            )
                                                                            intent.putExtra(
                                                                                "org_country",
                                                                                orgcountry_edittext.text.trim().toString()
                                                                            )
                                                                            intent.putExtra(
                                                                                "org_name",
                                                                                ORGANIZATION_name_edittext.text.trim().toString()
                                                                            )
                                                                            intent.putExtra(
                                                                                "org_state",
                                                                                ORGANIZATION_state_edittext.text.trim().toString()
                                                                            )
                                                                            intent.putExtra(
                                                                                "org_city_id",
                                                                                selected_city.toString().toDouble().roundToInt()
                                                                            )
                                                                            intent.putExtra(
                                                                                "org_country_id",
                                                                                selected_country.toString().toDouble().roundToInt()
                                                                            )
                                                                            intent.putExtra(
                                                                                "org_state_id",
                                                                                selected_state.toString().toDouble().roundToInt()
                                                                            )
                                                                            intent.putExtra(
                                                                                "org_gst",
                                                                                gst_edittext.text.trim().toString()
                                                                            )
                                                                            intent.putExtra(
                                                                                "org_tan",
                                                                                tan_edittext.text.trim().toString()
                                                                            )

                                                                            startActivity(intent)
                                                                        } else {
                                                                            Toast.makeText(
                                                                                this,
                                                                                "Organization gst number cannot be empty",
                                                                                Toast.LENGTH_SHORT
                                                                            ).show()

                                                                            //orggst
                                                                        }
                                                                    } else {
                                                                        Toast.makeText(
                                                                            this,
                                                                            "Organization tan number cannot be empty",
                                                                            Toast.LENGTH_SHORT
                                                                        ).show()

                                                                        //orgtan
                                                                    }
                                                                } else {
                                                                    Toast.makeText(
                                                                        this,
                                                                        "Organization city cannot be empty",
                                                                        Toast.LENGTH_SHORT
                                                                    ).show()

                                                                    //orgcity
                                                                }

                                                            } else {
                                                                Toast.makeText(
                                                                    this,
                                                                    "Organization state cannot be empty",
                                                                    Toast.LENGTH_SHORT
                                                                ).show()

                                                                //orgstate
                                                            }
                                                        }else {
                                                                Toast.makeText(
                                                                    this,
                                                                    "Organization country cannot be empty",
                                                                    Toast.LENGTH_SHORT
                                                                ).show()

                                                                //orgcountry
                                                            }
                                                    } else {
                                                        Toast.makeText(
                                                            this,
                                                            "Organization name cannot be empty",
                                                            Toast.LENGTH_SHORT
                                                        ).show()

                                                        //orgname
                                                    }
                                                } else {
                                                    Toast.makeText(
                                                        this,
                                                        "IFSC code has to be 11 characters",
                                                        Toast.LENGTH_SHORT
                                                    ).show()

                                                    //bankifsc
                                                }

                                            }
                                            else{
                                                Toast.makeText(this, "Confirm bank account number entered does not match. Please enter again", Toast.LENGTH_SHORT).show()

                                            }
                                        }
                                        else{
                                            Toast.makeText(this, "Bank account number needs to be 5-35 characters in length", Toast.LENGTH_SHORT).show()

                                            //banknumbeer
                                        }
                                    }
                                    else{
                                        Toast.makeText(this, "Please select bank account type", Toast.LENGTH_SHORT).show()

                                        //banktype
                                    }

                                }
                                else{
                                    Toast.makeText(this, "Bank name cannot be empty", Toast.LENGTH_SHORT).show()

                                    //bank name
                                }



                            }
                            else{

                                intent.putExtra("pan_number",pan_edittext.text.trim().toString())
                                if (bankname_edittext.text.trim().toString().isNotEmpty()){
                                    if (account_type_spinner.selectedItemId>0){
                                        if (account_number_edittext.text.length>=5 &&
                                            account_number_edittext.text.length<=35) {
                                            if (account_number_edittext.text.trim().toString() == account_number_edittext_confirm.text.trim().toString()) {

                                                if (ifsc_edittext.text.length==11) {
                                                    if (pan_edittext.text.trim().toString().isNotEmpty()) {

                                                        startActivity(intent)
                                                    } else {
                                                        Toast.makeText(
                                                            this,
                                                            "Pan number cannot be empty",
                                                            Toast.LENGTH_SHORT
                                                        ).show()
                                                        //pan
                                                    }
                                                } else {
                                                    Toast.makeText(
                                                        this,
                                                        "IFSC code has to be 11 characters",
                                                        Toast.LENGTH_SHORT
                                                    ).show()
                                                    //bankifsc
                                                }

                                            }
                                            else{
                                                Toast.makeText(this, "Confirm bank account number entered does not match. Please enter again", Toast.LENGTH_SHORT).show()

                                            }
                                        }
                                        else{
                                            Toast.makeText(this, "Bank account number needs to be 5-35 characters in length", Toast.LENGTH_SHORT).show()
                                            //banknumbeer
                                        }
                                    }
                                    else{
                                        Toast.makeText(this, "Please select bank account type", Toast.LENGTH_SHORT).show()
                                        //banktype
                                    }

                                }
                                else{
                                    Toast.makeText(this, "Bank name cannot be empty", Toast.LENGTH_SHORT).show()
                                    //bank name
                                }


                            }


                            
                        }

                    }
                    else{
                         if (value_spinner=="1"){
                             bankname_edittext.setText(it.data.userChoice.bankDetails.bankName)
                             account_number_edittext_confirm.setText(it.data.userChoice.bankDetails.bankAccountNumber)
                             account_number_edittext.setText(it.data.userChoice.bankDetails.bankAccountNumber)
                             account_type_spinner.setSelection(it.data.userChoice.bankDetails.bankAccountType)
                             ifsc_edittext.setText(it.data.userChoice.bankDetails.bankIfsc)
                             pan_edittext.setText(it.data.userChoice.panNumber.toString())
                         }
                        else{
                             bankname_edittext.setText(it.data.userChoice.bankDetails.bankName)
                             account_number_edittext_confirm.setText(it.data.userChoice.bankDetails.bankAccountNumber)
                             account_number_edittext.setText(it.data.userChoice.bankDetails.bankAccountNumber)
                             account_type_spinner.setSelection(it.data.userChoice.bankDetails.bankAccountType)
                             ifsc_edittext.setText(it.data.userChoice.bankDetails.bankIfsc)

                             if (it.data.userChoice.orgDetails.cityDataOrg!=null) {
                                 selected_country = it.data.userChoice.orgDetails.countryDataOrg.id
                                 PrefsHelper().savePref("country_id", selected_country)
                                 selected_state = it.data.userChoice.orgDetails.stateDataOrg.id
                                 PrefsHelper().savePref("state_id", selected_state)
                                 selected_city = it.data.userChoice.orgDetails.cityDataOrg.id
                                 orgcity_edittext.text = (it.data.userChoice.orgDetails.cityDataOrg.name)
                                 orgcountry_edittext.setText(it.data.userChoice.orgDetails.countryDataOrg.name)
                                 ORGANIZATION_state_edittext.setText(it.data.userChoice.orgDetails.stateDataOrg.name)
                             }
                             ORGANIZATION_name_edittext.setText(it.data.userChoice.orgDetails.name)
                             gst_edittext.setText(it.data.userChoice.orgDetails.gst)
                             tan_edittext.setText(it.data.userChoice.orgDetails.tanNumber)
                         }


                        //edit
                        continue_bank_details.setOnClickListener {
                            val intent = Intent(this,AffiliateSource::class.java)

                            intent.putExtra("value_spinner",value_spinner)
                            intent.putExtra("first_name_affiliate",first_namee)
                            intent.putExtra("last_name_affiliate",last_namee)
                            intent.putExtra("street_affiliate",street_name)
                            intent.putExtra("city_affiliate",city_name)
                            intent.putExtra("pin_affiliate",pin_name)
                            intent.putExtra("state_affiliate",state_name)
                            intent.putExtra("country_affiliate", country_name)
                            intent.putExtra("country_id", country_id)
                            intent.putExtra("state_id", state_id)
                            intent.putExtra("city_id", city_id)
                            intent.putExtra("bank_name",bankname_edittext.text.trim().toString())
                            intent.putExtra("bank_ifsc",ifsc_edittext.text.trim().toString())
                            intent.putExtra("bank_number",account_number_edittext.text.trim().toString())
                            intent.putExtra("bank_type",account_type_spinner.selectedItemId.toInt())
                            Log.d("bankkkkk",account_type_spinner.selectedItemId.toString())

                            if (value_spinner=="2"){

                                if (bankname_edittext.text.trim().toString().isNotEmpty()){
                                    if (account_type_spinner.selectedItemId>0){
                                        if (account_number_edittext.text.length>=5 &&
                                            account_number_edittext.text.length<=35) {
                                            if (account_number_edittext.text.trim().toString()==account_number_edittext_confirm.text.trim().toString()) {
                                                if (ifsc_edittext.text.length==11) {
                                                    if (ORGANIZATION_name_edittext.text.trim().toString().isNotEmpty()) {
                                                        if (orgcountry_edittext.text.trim().toString()!="Select") {
                                                            if (ORGANIZATION_state_edittext.text.trim().toString()!="Select") {
                                                                if (orgcity_edittext.text.trim().toString()!="Select") {
                                                                    if (tan_edittext.text.trim().toString().isNotEmpty()) {
                                                                        if (gst_edittext.text.trim().toString().isNotEmpty()) {

                                                                            intent.putExtra("org_city",orgcity_edittext.text.trim().toString())
                                                                            intent.putExtra("org_country",orgcountry_edittext.text.trim().toString())
                                                                            intent.putExtra("org_name",ORGANIZATION_name_edittext.text.trim().toString())
                                                                            intent.putExtra("org_state",ORGANIZATION_state_edittext.text.trim().toString())
                                                                            intent.putExtra("org_city_id",selected_city.toString().toDouble().roundToInt())
                                                                            intent.putExtra("org_country_id",selected_country.toString().toDouble().roundToInt())
                                                                            intent.putExtra("org_state_id",selected_state.toString().toDouble().roundToInt())
                                                                            intent.putExtra("org_gst",gst_edittext.text.trim().toString())
                                                                            intent.putExtra("org_tan",tan_edittext.text.trim().toString())

                                                                            startActivity(intent)
                                                                        } else {
                                                                            Toast.makeText(
                                                                                this,
                                                                                "Organization gst number cannot be empty",
                                                                                Toast.LENGTH_SHORT
                                                                            ).show()

                                                                            //orggst
                                                                        }
                                                                    } else {
                                                                        Toast.makeText(
                                                                            this,
                                                                            "Organization tan number cannot be empty",
                                                                            Toast.LENGTH_SHORT
                                                                        ).show()

                                                                        //orgtan
                                                                    }
                                                                } else {
                                                                    Toast.makeText(
                                                                        this,
                                                                        "Organization city cannot be empty",
                                                                        Toast.LENGTH_SHORT
                                                                    ).show()

                                                                    //orgcity
                                                                }

                                                            } else {
                                                                Toast.makeText(
                                                                    this,
                                                                    "Organization state cannot be empty",
                                                                    Toast.LENGTH_SHORT
                                                                ).show()

                                                                //orgstate
                                                            }
                                                        } else {
                                                            Toast.makeText(
                                                                this,
                                                                "Organization country cannot be empty",
                                                                Toast.LENGTH_SHORT
                                                            ).show()

                                                            //orgcountry
                                                        }
                                                    } else {
                                                        Toast.makeText(
                                                            this,
                                                            "Organization name cannot be empty",
                                                            Toast.LENGTH_SHORT
                                                        ).show()

                                                        //orgname
                                                    }
                                                } else {
                                                    Toast.makeText(
                                                        this,
                                                        "IFSC code has to be 11 characters",
                                                        Toast.LENGTH_SHORT
                                                    ).show()

                                                    //bankifsc
                                                }

                                            }
                                            else{
                                                Toast.makeText(this, "Confirm bank account number entered does not match. Please enter again", Toast.LENGTH_SHORT).show()

                                            }
                                        }
                                        else{
                                            Toast.makeText(this, "Bank account number needs to be 5-35 characters in length", Toast.LENGTH_SHORT).show()

                                            //banknumbeer
                                        }
                                    }
                                    else{
                                        Toast.makeText(this, "Please select bank account type", Toast.LENGTH_SHORT).show()

                                        //banktype
                                    }

                                }
                                else{
                                    Toast.makeText(this, "Bank name cannot be empty", Toast.LENGTH_SHORT).show()

                                    //bank name
                                }


                            }
                            else{

                                intent.putExtra("pan_number",pan_edittext.text.trim().toString())
                                if (bankname_edittext.text.trim().toString().isNotEmpty()){
                                    if (account_type_spinner.selectedItemId>0){
                                        if (account_number_edittext.text.length>=5 &&
                                            account_number_edittext.text.length<=35) {
                                            if (account_number_edittext.text.trim().toString() == account_number_edittext_confirm.text.trim().toString()) {
                                                if (ifsc_edittext.text.length==11) {
                                                    if (pan_edittext.text.trim().toString().isNotEmpty()) {

                                                        startActivity(intent)

                                                    } else {
                                                        Toast.makeText(
                                                            this,
                                                            "Pan number cannot be empty",
                                                            Toast.LENGTH_SHORT
                                                        ).show()
                                                        //pan
                                                    }
                                                } else {
                                                    Toast.makeText(
                                                        this,
                                                        "IFSC code has to be 11 characters",
                                                        Toast.LENGTH_SHORT
                                                    ).show()
                                                    //bankifsc
                                                }

                                            }
                                            else{
                                                Toast.makeText(this, "Confirm bank account number entered does not match. Please enter again", Toast.LENGTH_SHORT).show()

                                            }
                                        }
                                        else{
                                            Toast.makeText(this, "Bank account number needs to be 5-35 characters in length", Toast.LENGTH_SHORT).show()
                                            //banknumbeer
                                        }
                                    }
                                    else{
                                        Toast.makeText(this, "Please select bank account type", Toast.LENGTH_SHORT).show()
                                        //banktype
                                    }

                                }
                                else{
                                    Toast.makeText(this, "Bank name cannot be empty", Toast.LENGTH_SHORT).show()
                                    //bank name
                                }

                            }



                        }

                    }
                    setupObserversCountries()
                }

            }
        })


        //observe API call status
        viewModel.getAffiliateDetailsAPICallStatus.observe(this, Observer {
            processStatus(it)
        })
    }



    private fun setupObserversCountries() {
        viewModelRegion.countriesResponseLiveData.observe(this, Observer {
            if (it!=null) {
                if (it.message == "Success") {
                    val listOfCountries= ArrayList<MultiSelectModel>()
                    for (i in 0 until it.data.size){
                        listOfCountries.add(MultiSelectModel(it.data[i].id,it.data[i].country));
                    }
                    val preselected = ArrayList<Int>()
                    if(selected_country!=null){
                        val selected_countries = selected_country.toString().toDouble().roundToInt()
                        preselected.add(selected_countries)
                    }
                    val multiSelectDialogCountry =  MultiSelectDialog()
                        .title("Countries") //setting title for dialog
                        .titleSize(25.toFloat())
                        .positiveText("Done")
                        .negativeText("Cancel")
                        .setMinSelectionLimit(0) //you can set minimum checkbox selection limit (Optional)
                        .setMaxSelectionLimit(1) //you can set maximum checkbox selection limit (Optional)
                        .multiSelectList(listOfCountries) // the multi select model list with ids and name
                        .preSelectIDsList(preselected)
                        .onSubmit(object : MultiSelectDialog.SubmitCallbackListener{
                            override fun onSelected(
                                p0: ArrayList<Int>,
                                p1: ArrayList<String>?,
                                p2: String?
                            ) {
                                for (i in 0 until p0.size) {
                                    if (p0[i] != 0) {
                                        PrefsHelper().savePref("country_id", p0[i])
                                        val button =
                                            findViewById<Button>(R.id.orgcountry_edittext)
                                        button.text = p1?.get(i) ?: "Select"
                                        val buttonStates =
                                            findViewById<Button>(R.id.ORGANIZATION_state_edittext)
                                        buttonStates.text = "Select"
                                        val buttonCities =
                                            findViewById<Button>(R.id.orgcity_edittext)
                                        buttonCities.text = "Select"

                                        selected_country = p0[i]
                                        selected_state = null
                                        selected_city = null
                                    }
                                }
                            }

                            override fun onCancel() {

                            }
                        })
                        .show(getSupportFragmentManager(), "multiSelectDialog")

                }
            }
        })
        viewModelRegion.countriesAPICallStatus.observe(this, Observer {
            processStatus(it)
        })
    }



    private fun setupObserversStates() {
        viewModelRegion.statesResponseLiveData.observe(this, Observer {
            if (it!=null) {
                if (it.message == "Success") {
                    val listOfStates= ArrayList<MultiSelectModel>()
                    for (i in 0 until it.data.size){
                        listOfStates.add(MultiSelectModel(it.data[i].id,it.data[i].state));
                    }

                    val preselected = ArrayList<Int>()
                    if(selected_state!=null){
                        val selected_states = selected_state.toString().toDouble().roundToInt()
                        preselected.add(selected_states)
                    }

                    val multiSelectDialogState =  MultiSelectDialog()
                        .title("States") //setting title for dialog
                        .titleSize(25.toFloat())
                        .positiveText("Done")
                        .negativeText("Cancel")
                        .setMinSelectionLimit(0) //you can set minimum checkbox selection limit (Optional)
                        .setMaxSelectionLimit(1) //you can set maximum checkbox selection limit (Optional)
                        .multiSelectList(listOfStates) // the multi select model list with ids and name
                        .preSelectIDsList(preselected)
                        .onSubmit(object : MultiSelectDialog.SubmitCallbackListener{
                            override fun onSelected(
                                p0: ArrayList<Int>,
                                p1: ArrayList<String>?,
                                p2: String?
                            ) {
                                for (i in 0 until p0.size) {
                                    if (p0[i] != 0) {
                                        PrefsHelper().savePref("state_id", p0[i])
                                        val button =
                                            findViewById<Button>(R.id.ORGANIZATION_state_edittext)
                                        button.text = p1?.get(i) ?: "Select"
                                        val buttonCities =
                                            findViewById<Button>(R.id.orgcity_edittext)
                                        buttonCities.text = "Select"

                                        selected_state = p0[i]
                                        selected_city = null
                                    }
                                }
                            }

                            override fun onCancel() {

                            }
                        })
                        .show(getSupportFragmentManager(), "multiSelectDialog")
                }
            }
        })
        viewModelRegion.stateesAPICallStatus.observe(this, Observer {
            processStatus(it)
        })
    }


    private fun setupObserversCities() {
        viewModelRegion.citiesResponseLiveData.observe(this, Observer {
            if (it!=null) {
                if (it.message == "Success") {
                    val listOfCities= ArrayList<MultiSelectModel>()
                    for (i in 0 until it.data.size){
                        listOfCities.add(MultiSelectModel(it.data[i].id,it.data[i].city));
                    }
                    val preselected = ArrayList<Int>()
                    if(selected_city!=null){
                        val selected_cities = selected_city.toString().toDouble().roundToInt()
                        preselected.add(selected_cities)
                    }
                    val multiSelectDialogCity =  MultiSelectDialog()
                        .title("Cities") //setting title for dialog
                        .titleSize(25.toFloat())
                        .positiveText("Done")
                        .negativeText("Cancel")
                        .setMinSelectionLimit(0) //you can set minimum checkbox selection limit (Optional)
                        .setMaxSelectionLimit(1) //you can set maximum checkbox selection limit (Optional)
                        .multiSelectList(listOfCities) // the multi select model list with ids and name
                        .preSelectIDsList(preselected)
                        .onSubmit(object : MultiSelectDialog.SubmitCallbackListener{
                            override fun onSelected(
                                p0: ArrayList<Int>,
                                p1: ArrayList<String>?,
                                p2: String?
                            ) {
                                for (i in 0 until p0.size) {
                                    if (p0[i] != 0) {
                                        val button =
                                            findViewById<Button>(R.id.orgcity_edittext)
                                        button.text = p1?.get(i) ?: "Select"

                                        selected_city = p0[i]
                                    }
                                }
                            }

                            override fun onCancel() {

                            }
                        })
                        .show(getSupportFragmentManager(), "multiSelectDialog")

                }
            }
        })
        viewModelRegion.citiesAPICallStatus.observe(this, Observer {
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



            }
            StatusType.LOADING_MORE -> {
                // CommonUtils().showSnackbar(binding.root, "Loading more..")
            }
            StatusType.NO_NETWORK -> {

            }
            StatusType.SESSION_EXPIRED -> {
                //CommonUtils().showSnackbar(login_button_login.rootView,"session expired")
            }
        }
    }



    private fun new_function2(full_list_edu2: List<AccountTypeX>) {
        val  list_org_name2:MutableList<String> = arrayListOf()
        val   list_org_id:MutableList<String> = arrayListOf()

        list_org_name2.add("Select")
        for (j in 0 until full_list_edu2.size) {
            val obj = full_list_edu2[j]
            list_org_name2.add(obj.name)
        }

        val adapter2 = ArrayAdapter(
            this,
            R.layout.color_spinner_layout,
            list_org_name2
        )
        adapter2.setDropDownViewResource(com.neuro.neuroharmony.R.layout.spinner_dropdown_layout)
        ///account_type_spinner.setEnabled(false)
        account_type_spinner?.setAdapter(adapter2)
    }
}
