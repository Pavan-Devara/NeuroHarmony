package com.neuro.neuroharmony.ui.login.AfilliateWorkFlow

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.telephony.TelephonyManager
import android.util.Log
import android.view.View
import android.view.ViewTreeObserver
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.gson.Gson
import com.neuro.neuroharmony.R
import com.neuro.neuroharmony.data.model.AffiliateWorkflow.AffiliateSource
import com.neuro.neuroharmony.ui.login.BaseActivity
import com.neuro.neuroharmony.ui.login.ResourceStatus
import com.neuro.neuroharmony.ui.login.StatusType
import com.neuro.neuroharmony.utils.CommonUtils
import com.neuro.neuroharmony.utils.PrefsHelper
import kotlinx.android.synthetic.main.activity_affiliate_source.*
import kotlinx.android.synthetic.main.activity_home_page1.*
import kotlinx.android.synthetic.main.activity_login.*
import org.json.JSONArray
import java.util.*
import kotlin.math.roundToInt

class AffiliateSource : BaseActivity(), AdapterView.OnItemSelectedListener {

    private lateinit var viewModel: GetAffiliateDetailsViewModel
    var first_namee = ""
    var last_namee = ""
    var city_name = ""
    var pin_name = ""
    var street_name = ""
    var state_name = ""
    var country_name = ""
    var country_id = 0
    var state_id = 0
    var city_id = 0
    var country_org_id = 0
    var state_org_id = 0
    var city_org_id = 0
    var value_spinner = ""
    var bank_name = ""
    var bank_number=""
    var bank_ifsc = ""
    var bank_type = 0
    var pan = ""
    var position_id =0
    var org_city= ""
    var org_country= ""
    var org_gst= ""
    var org_name= ""
    var org_state= ""
    var org_tan= ""
    var incoming_data = ""
    var gson = Gson()

    private lateinit var viewModel3: AffiliateOrgViewModel
    private lateinit var viewModel2: AffiliateFormViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_affiliate_source)

        source_spinner.setOnItemSelectedListener(this)
        viewModel3 = ViewModelProviders.of(this)[AffiliateOrgViewModel::class.java]
        viewModel2 = ViewModelProviders.of(this)[AffiliateFormViewModel::class.java]

        viewModel = ViewModelProviders.of(this)[GetAffiliateDetailsViewModel::class.java]
        viewModel.getaffiliatevm()

        setupObservers()

        setupObservers2()
        setupObservers3()


        val present_country = getDetectedCountry(this, "IN")
//        val locale: String = this?.resources?.configuration?.locale!!.country
        var locale = present_country
        locale = locale.toUpperCase(Locale.getDefault())
        country_code_picker_affiliate.setDefaultCountryUsingNameCode(locale)
        country_code_picker_affiliate.resetToDefaultCountry()

    }


    fun getDetectedCountry(context: Context, defaultCountryIsoCode: String): String {
        detectSIMCountry(context)?.let {
            return it
        }

        detectNetworkCountry(context)?.let {
            return it
        }

        detectLocaleCountry(context)?.let {
            return it
        }

        return defaultCountryIsoCode
    }

    private fun detectSIMCountry(context: Context): String? {
        try {
            val telephonyManager = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
            Log.d(com.neuro.neuroharmony.ui.login.TAG, "detectSIMCountry: ${telephonyManager.simCountryIso}")
            return telephonyManager.simCountryIso
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    private fun detectNetworkCountry(context: Context): String? {
        try {
            val telephonyManager = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
            Log.d(com.neuro.neuroharmony.ui.login.TAG, "detectNetworkCountry: ${telephonyManager.simCountryIso}")
            return telephonyManager.networkCountryIso
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    private fun detectLocaleCountry(context: Context): String? {
        try {
            val localeCountryISO = context.getResources().getConfiguration().locale.getCountry()
            Log.d(com.neuro.neuroharmony.ui.login.TAG, "detectNetworkCountry: $localeCountryISO")
            return localeCountryISO
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }


    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        value_spinner = intent.getStringExtra("value_spinner")
        if (value_spinner=="1") {

            first_namee = intent.getStringExtra("first_name_affiliate")
            last_namee = intent.getStringExtra("last_name_affiliate")
            city_name = intent.getStringExtra("city_affiliate")
            pin_name = intent.getStringExtra("pin_affiliate")
            street_name = intent.getStringExtra("street_affiliate")
            state_name = intent.getStringExtra("state_affiliate")
            bank_name = intent.getStringExtra("bank_name")
            bank_ifsc = intent.getStringExtra("bank_ifsc")
            bank_number = intent.getStringExtra("bank_number")
            country_name = intent.getStringExtra("country_affiliate")
            country_id = intent.getIntExtra("country_id", 0)
            state_id = intent.getIntExtra("state_id", 0)
            city_id = intent.getIntExtra("city_id", 0)
            bank_type = intent.getIntExtra("bank_type", 0)
            pan = intent.getStringExtra("pan_number")
            if (position == 1) {

                save_button_affiliate.setOnClickListener {
                    if (agent_name_edittext.text.trim().toString().isNotEmpty()) {
                        if (agent_nmuber_edittext.text.trim().toString().isNotEmpty()) {
                            viewModel2.affiliateformvm(
                                "nh_agent",
                                1,
                                bank_number,
                                bank_type.toInt(),
                                bank_ifsc,
                                bank_name,
                                first_namee,
                                last_namee,
                                "",
                                agent_name_edittext.text.trim().toString(),
                                agent_nmuber_edittext.text.trim().toString(),
                                pan,
                                pin_name,
                                street_name,
                                country_id,
                                country_name,
                                state_id,
                                state_name,
                                city_id,
                                city_name
                            )

                        } else {
                            Toast.makeText(
                                this,
                                "Agent number cannot be empty",
                                Toast.LENGTH_SHORT
                            ).show()

                        }
                    } else {
                        Toast.makeText(this, "Agent name cannot be empty", Toast.LENGTH_SHORT)
                            .show()

                    }
                }

                agent_name_text_view.visibility = View.VISIBLE
                agent_name_edittext.visibility = View.VISIBLE
                agent_nmuber_edittext.visibility = View.VISIBLE
                agent_number_text_view.visibility = View.VISIBLE
                country_code_picker_affiliate.visibility=View.VISIBLE


            } else if (position == 0) {

                save_button_affiliate.setOnClickListener {
                    if (position==0)
                    Toast.makeText(this, "Please select the source", Toast.LENGTH_SHORT)
                        .show()
                }

            } else {

                agent_name_text_view.visibility = View.INVISIBLE
                agent_name_edittext.visibility = View.INVISIBLE
                agent_nmuber_edittext.visibility = View.INVISIBLE
                agent_number_text_view.visibility = View.INVISIBLE
                country_code_picker_affiliate.visibility=View.INVISIBLE
                save_button_affiliate.setOnClickListener {
                    val source_id = id_to_be_sent(
                        incoming_data,
                        source_spinner.selectedItem


                    )
                    viewModel2.affiliateformvm(
                        source_id as String,
                        1,
                        bank_number,
                        bank_type.toInt(),
                        bank_ifsc,
                        bank_name,
                        first_namee,
                        last_namee,
                        "",
                        null,
                        null,
                        pan,
                        pin_name,
                        street_name,
                        country_id,
                        country_name,
                        state_id,
                        state_name,
                        city_id,
                        city_name
                    )

                }

            }
        }
        else{

            first_namee = intent.getStringExtra("first_name_affiliate")
            last_namee = intent.getStringExtra("last_name_affiliate")
            city_name = intent.getStringExtra("city_affiliate")
            pin_name = intent.getStringExtra("pin_affiliate")
            street_name = intent.getStringExtra("street_affiliate")
            state_name = intent.getStringExtra("state_affiliate")
            bank_type=intent.getIntExtra("bank_type",0)
            bank_name = intent.getStringExtra("bank_name")
            bank_ifsc=intent.getStringExtra("bank_ifsc")
            bank_number = intent.getStringExtra("bank_number")
            org_city = intent.getStringExtra("org_city")
            org_country = intent.getStringExtra("org_country")
            org_name = intent.getStringExtra("org_name")
            org_state = intent.getStringExtra("org_state")
            org_gst = intent.getStringExtra("org_gst")
            org_tan = intent.getStringExtra("org_tan")
            country_name = intent.getStringExtra("country_affiliate")
            country_id = intent.getIntExtra("country_id", 0)
            state_id = intent.getIntExtra("state_id", 0)
            city_id = intent.getIntExtra("city_id", 0)
            country_org_id = intent.getIntExtra("org_country_id",0)
            state_org_id = intent.getIntExtra("org_state_id",0)
            city_org_id = intent.getIntExtra("org_city_id",0)

            if (position==1){
                agent_name_text_view.visibility= View.VISIBLE
                agent_name_edittext.visibility = View.VISIBLE
                agent_nmuber_edittext.visibility = View.VISIBLE
                agent_number_text_view.visibility = View.VISIBLE
                country_code_picker_affiliate.visibility = View.VISIBLE

                save_button_affiliate.setOnClickListener {

                    if (agent_name_edittext.text.trim().toString().isNotEmpty()){
                        if (agent_nmuber_edittext.text.trim().toString().isNotEmpty()){

                            viewModel3.affiliateformorgvm(
                                "nh_agent",
                                2,
                                bank_number,
                                bank_type.toInt(),
                                bank_ifsc,
                                bank_name,
                                city_name,
                                first_namee,
                                last_namee,
                                "",
                                agent_name_edittext.text.trim().toString(),
                                agent_nmuber_edittext.text.trim().toString(),
                                org_state,
                                org_city,
                                org_country,
                                org_gst,
                                org_name,
                                org_tan,
                                pin_name,
                                state_name,
                                street_name,
                                country_id,
                                country_name,
                                state_id,
                                city_id,
                                country_org_id,
                                state_org_id,
                                city_org_id
                            )


                        }
                        else
                        {
                            Toast.makeText(this, "Agent number cannot be empty", Toast.LENGTH_SHORT).show()

                        }
                    }
                    else{
                        Toast.makeText(this, "Agent name cannot be empty", Toast.LENGTH_SHORT).show()

                    }
                }
            }
            else if(position==0){

                agent_name_text_view.visibility = View.INVISIBLE
                agent_name_edittext.visibility = View.INVISIBLE
                agent_nmuber_edittext.visibility = View.INVISIBLE
                agent_number_text_view.visibility = View.INVISIBLE
                country_code_picker_affiliate.visibility= View.INVISIBLE
                save_button_affiliate.setOnClickListener {

                    Toast.makeText(this, "Please select the source", Toast.LENGTH_SHORT)
                        .show()
                }

            }
            else{

                agent_name_text_view.visibility = View.INVISIBLE
                agent_name_edittext.visibility = View.INVISIBLE
                agent_nmuber_edittext.visibility = View.INVISIBLE
                agent_number_text_view.visibility = View.INVISIBLE
                country_code_picker_affiliate.visibility= View.INVISIBLE
                save_button_affiliate.setOnClickListener {
                    val source_id = id_to_be_sent(
                        incoming_data,
                        source_spinner.selectedItem


                    )
                            viewModel3.affiliateformorgvm(
                                source_id as String,
                                2,
                                bank_number,
                                bank_type.toInt(),
                                bank_ifsc,
                                bank_name,
                                city_name,
                                first_namee,
                                last_namee,
                                "",
                                null,
                                null,
                                org_state,
                                org_city,
                                org_country,
                                org_gst,
                                org_name,
                                org_tan,
                                pin_name,
                                state_name,
                                street_name,
                                country_id,
                                country_name,
                                state_id,
                                city_id,
                                country_org_id,
                                state_org_id,
                                city_org_id
                            )


                }
            }

        }
    }
    override fun onNothingSelected(parent: AdapterView<*>?) {

    }

    private  fun setupObservers(){

        viewModel.getAffiliateDetailsResponseLiveData.observe(this, Observer {
            if(it!=null){
                if( it.message=="Success"){
                    incoming_data = gson.toJson(it.data.choice.affiliateSources)

                    new_function2(it.data.choice.affiliateSources)
                    if (PrefsHelper().getPref<Int>("Account_Status")==10){
                        val  list_familyinfo:MutableList<String> = arrayListOf()
                        for (i in 0 until it.data.choice.affiliateSources.size) {
                            val obj = it.data.choice.affiliateSources[i]
                           list_familyinfo.add(obj.key)
                        }
                        position_id = list_familyinfo.indexOf(it.data.userChoice.affiliateSourceKey)
                        source_spinner.setSelection(position_id+1)
                        source_spinner.setEnabled(false)
                        source_spinner.getViewTreeObserver()
                                .addOnGlobalLayoutListener(ViewTreeObserver.OnGlobalLayoutListener {
                                    (source_spinner.getSelectedView() as TextView).setTextColor(
                                            Color.parseColor("#555555"))
                                })
                        if (it.data.userChoice.nhAgentName!=""&&it.data.userChoice.nhAgentNumber!=""){
                            agent_name_edittext.setText(it.data.userChoice.nhAgentName)
                            agent_nmuber_edittext.setText(it.data.userChoice.nhAgentNumber)
                            agent_name_edittext.setFocusable(false);
                            agent_name_edittext.setTextColor(Color.parseColor("#555555"))
                            agent_name_edittext.setEnabled(false);
                            agent_name_edittext.setCursorVisible(false);
                            agent_name_edittext.setKeyListener(null);
                            agent_nmuber_edittext.setFocusable(false);
                            agent_nmuber_edittext.setTextColor(Color.parseColor("#555555"))
                            agent_nmuber_edittext.setEnabled(false);
                            agent_nmuber_edittext.setCursorVisible(false);
                            agent_nmuber_edittext.setKeyListener(null);


                        }




                        /*if (value_spinner=="1"){
                            if (it.data.userChoice.affiliateSourceKey=="nh_agent"){
                                source_spinner.setSelection(it.data.userChoice.affiliateSourceKey.toInt())
                                agent_name_edittext.setText(it.data.userChoice.nhAgentName)
                                agent_nmuber_edittext.setText(it.data.userChoice.nhAgentNumber)

                            }


                        }
                        else{

                            //agent_name_edittext.setText(it.data.userChoice.nhAgentName)
                            //agent_nmuber_edittext.setText(it.data.userChoice.nhAgentNumber)

                        }*/


                    }
                }else{
                    Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                }

            }
        })


        //observe API call status
        viewModel.getAffiliateDetailsAPICallStatus.observe(this, Observer {
            processStatus(it)
        })
    }

    private fun processStatus(resource: ResourceStatus) {

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

    private  fun setupObservers2(){

        viewModel2.loginResponseLiveData.observe(this, Observer {
            if(it!=null){
                if( it.message=="Success"){
                    PrefsHelper().savePref("Account_Status",10)
                    val intent = Intent(this,AffiliateHomePage::class.java)
                    startActivity(intent)
                }
            }
        })
        //observe API call status
        viewModel2.loginAPICallStatus.observe(this, Observer {
            processStatus2(it)
        })
    }







    private fun processStatus2(resource: ResourceStatus) {

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
                Toast.makeText(this, "Please Try again", Toast.LENGTH_SHORT).show()
                dismissDialog()

            }
            StatusType.LOADING_MORE -> {
                CommonUtils().showSnackbar(save_button_affiliate.rootView, "Loading more..")
            }
            StatusType.NO_NETWORK -> {
                Toast.makeText(this, "Please check internet connection", Toast.LENGTH_SHORT).show()
            }
            StatusType.SESSION_EXPIRED -> {
                CommonUtils().showSnackbar(save_button_affiliate.rootView, "session expired")
            }
        }
    }

    private  fun setupObservers3(){

        viewModel3.loginResponseLiveData.observe(this, Observer {
            if(it!=null){
                if( it.message=="Success"){
                    PrefsHelper().savePref("Account_Status",10)
                    val intent = Intent(this,AffiliateHomePage::class.java)
                    startActivity(intent)
                }
            }
        })
        //observe API call status
        viewModel3.loginAPICallStatus.observe(this, Observer {
            processStatus3(it)
        })
    }







    private fun processStatus3(resource: ResourceStatus) {

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
                Toast.makeText(this, "Please Try again", Toast.LENGTH_SHORT).show()
                dismissDialog()

            }
            StatusType.LOADING_MORE -> {
                CommonUtils().showSnackbar(save_button_affiliate.rootView, "Loading more..")
            }
            StatusType.NO_NETWORK -> {
                Toast.makeText(this, "Please check internet connection", Toast.LENGTH_SHORT).show()
            }
            StatusType.SESSION_EXPIRED -> {
                CommonUtils().showSnackbar(save_button_affiliate.rootView, "session expired")
            }
        }
    }



    private fun new_function2(full_list_edu2: List<AffiliateSource>) {
        val  list_org_name2:MutableList<String> = arrayListOf()
        val   list_org_id:MutableList<String> = arrayListOf()

        list_org_name2.add("Select")
        for (j in 0 until full_list_edu2.size) {
            val obj = full_list_edu2[j]
            list_org_name2.add(obj.description)
        }

        val adapter2 = ArrayAdapter(
            this,
            R.layout.color_spinner_layout,
            list_org_name2
        )
        adapter2.setDropDownViewResource(com.neuro.neuroharmony.R.layout.spinner_dropdown_layout)
        ///account_type_spinner.setEnabled(false)
        source_spinner?.setAdapter(adapter2)
    }

    private fun id_to_be_sent(full_list: String, selectedItem: Any?): Any {
        Log.d("incoming_data",incoming_data.toString())
        val array_org = JSONArray(incoming_data)

        val  list_choice:MutableList<String> = arrayListOf()
        val list_id:MutableList<String> = arrayListOf()
        for (i in 0 until array_org.length()) {

            val obj = array_org.getJSONObject(i)
            list_choice.add(obj["description"].toString())
            list_id.add(obj["key"].toString())
        }
        var positionn = list_choice.indexOf(selectedItem.toString())
        var selected_id = list_id.get(positionn)
        return selected_id

    }


}
