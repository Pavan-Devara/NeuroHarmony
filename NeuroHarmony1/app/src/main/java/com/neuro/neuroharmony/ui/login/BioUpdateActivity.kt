package com.neuro.neuroharmony.ui.login

import android.Manifest
import android.annotation.TargetApi
import android.app.Activity
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.ContentValues
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.preference.PreferenceManager
import android.provider.MediaStore
import android.telephony.TelephonyManager
import android.text.Editable
import android.util.Log
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.core.content.FileProvider
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.abdeveloper.library.MultiSelectDialog
import com.abdeveloper.library.MultiSelectModel
import com.google.gson.Gson
import com.neuro.neuroharmony.R
import com.neuro.neuroharmony.data.model.Social_profile.PersonalInfo.PersonalInfoResponse
import com.neuro.neuroharmony.data.model.Social_profile.PersonalInfo.Statu
import com.neuro.neuroharmony.ui.login.PaymentFlow.PaymentPackagesScreen
import com.neuro.neuroharmony.ui.login.Regions.RegionsPresentResidenceViewModel
import com.neuro.neuroharmony.ui.login.Regions.RegionsViewModel
import com.neuro.neuroharmony.ui.login.SocialProfile.PersonalInfoViewModel
import com.neuro.neuroharmony.ui.login.SocialProfile.ReligiousInfoVersion2
import com.neuro.neuroharmony.utils.CommonUtils
import com.neuro.neuroharmony.utils.PrefsHelper
import kotlinx.android.synthetic.main.activity_add_apartner.*
import kotlinx.android.synthetic.main.activity_bioinfo.*
import kotlinx.android.synthetic.main.activity_bioinfo.gender
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt


class BioUpdateActivity : BaseActivity(), AdapterView.OnItemSelectedListener{
    var mobile_var =""
    var email_var = ""
    var male_female_value = 0
    var PIC_UPLOAD = false

    private lateinit var viewModel: ProfileInfoViewModel
    private lateinit var viewModel1: UploadProfilePhotoViewModel
    private lateinit var viewModel2: PersonalInfoViewModel
    private lateinit var viewModel3: ReligiousInfoViewModel
    private lateinit var viewModelRegionNative: RegionsViewModel
    private lateinit var viewModelRegionPresent: RegionsPresentResidenceViewModel

    var selectedNativeCountry = ""
    var selectedNativeState = ""
    var selectedNativeCity = ""
    var selectedPresentCountry = ""
    var selectedPresentState = ""
    var selectedPresentCity = ""

    var selectedNativeCountryId = 0
    var selectedNativeStateId = 0
    var selectedNativeCityId = 0
    var selectedPresentCountryId = 0
    var selectedPresentStateId = 0
    var selectedPresentCityId = 0

    var country = true
    var state = true
    var city = true

    val gson = Gson()
    val GALLERY_REQUEST_CODE = 1
    private val PERMISSION_CODE = 1000
    private val GALLERY_PERMISSION= 121
    private val IMAGE_CAPTURE_CODE = 1001
    var image_uri: Uri? = null


    var fileTemp: File? = null

    companion object {
        lateinit var cameraBitmap: Bitmap

        @Throws(IOException::class)
        fun copyBytes(input: InputStream, output: OutputStream) {
            //Creating byte array
            val buffer = ByteArray(1024)
            var length = input.read(buffer)

            //Transferring data
            while (length != -1) {
                output.write(buffer, 0, length)
                length = input.read(buffer)
            }
            //Finalizing
            output.flush()
            output.close()
            input.close()
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bioinfo)


        val mySPrefs = PreferenceManager.getDefaultSharedPreferences(this)
        val editor = mySPrefs.edit()
        editor.remove("country_id")
        editor.remove("state_id")
        editor.remove("country_id_present")
        editor.remove("state_id_present")
        editor.apply()

        viewModel = ViewModelProviders.of(this)[ProfileInfoViewModel::class.java]
        viewModel2 = ViewModelProviders.of(this)[PersonalInfoViewModel::class.java]
        viewModel1 = ViewModelProviders.of(this)[UploadProfilePhotoViewModel::class.java]
        viewModel3 = ViewModelProviders.of(this)[ReligiousInfoViewModel::class.java]
        viewModelRegionNative = ViewModelProviders.of(this)[RegionsViewModel::class.java]
        viewModelRegionPresent = ViewModelProviders.of(this)[RegionsPresentResidenceViewModel::class.java]
        viewModel2.personalInfoLiveData(null)

        setupobserverspersonalinfo()
        setupListernersForReligious()
        setupListenersUploadPhoto()
        setupObserversUploadPhoto()
        val from_which_page =PrefsHelper().getPref("Flag_for_edit_signup",2)
        setupObservers(from_which_page)

        setupObserversNativeCountries()
        setupObserversNativeStates()
        setupObserversNativeCities()
        setupObserversPresentCountries()
        setupObserversPresentStates()
        setupObserversPresentCities()


        nativecountryspinner.setOnClickListener {
            country = true
            viewModelRegionNative.countriesvm()
        }
        nativestatespinner.setOnClickListener {
            if (nativecountryspinner.text != "Select") {
                state = true
                viewModelRegionNative.statesvm()
            }else{
                Toast.makeText(this, "Please select your native country", Toast.LENGTH_SHORT).show()
            }
        }
        nativecityspinner.setOnClickListener {
            if (nativestatespinner.text != "Select") {
                city = true
                viewModelRegionNative.citiesvm()
            }else{
                Toast.makeText(this, "Please select your native state", Toast.LENGTH_SHORT).show()
            }
        }
        presentresidencecountryspinner.setOnClickListener {
            country = false
            viewModelRegionPresent.countriesvm()
        }
        presentresidencestatespinner.setOnClickListener {
            if (presentresidencecountryspinner.text != "Select") {
                state = false
                viewModelRegionPresent.statesvm()
            }else{
                Toast.makeText(this, "Please select your present residence country", Toast.LENGTH_SHORT).show()
            }
        }
        presentresidencecityspinner.setOnClickListener {
            if (presentresidencestatespinner.text != "Select") {
                city = false
                viewModelRegionPresent.citiesvm()
            }else{
                Toast.makeText(this, "Please select your residence present state", Toast.LENGTH_SHORT).show()
            }
        }

        val present_country = getDetectedCountry(this, "IN")
//        val locale: String = this?.resources?.configuration?.locale!!.country
        var locale = present_country
        locale = locale.toUpperCase(Locale.getDefault())
        country_code_picker_alt_num.setDefaultCountryUsingNameCode(locale)
        country_code_picker_alt_num.resetToDefaultCountry()
        Log.d("country", locale)
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
            Log.d(TAG, "detectSIMCountry: ${telephonyManager.simCountryIso}")
            return telephonyManager.simCountryIso
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    private fun detectNetworkCountry(context: Context): String? {
        try {
            val telephonyManager = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
            Log.d(TAG, "detectNetworkCountry: ${telephonyManager.simCountryIso}")
            return telephonyManager.networkCountryIso
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    private fun detectLocaleCountry(context: Context): String? {
        try {
            val localeCountryISO = context.getResources().getConfiguration().locale.getCountry()
            Log.d(TAG, "detectNetworkCountry: $localeCountryISO")
            return localeCountryISO
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }


    private fun setupObserversNativeCountries() {
        viewModelRegionNative.countriesResponseLiveData.observe(this, Observer {
            if (it!=null) {
                if (it.message == "Success") {
                    if (country) {
                        val listOfCountries = ArrayList<MultiSelectModel>()
                        for (i in 0 until it.data.size) {
                            listOfCountries.add(
                                MultiSelectModel(
                                    it.data[i].id,
                                    it.data[i].country
                                )
                            );
                        }
                        val preselected = ArrayList<Int>()
                        if (selectedNativeCountryId != 0) {
                            val selected_countries =
                                selectedNativeCountryId.toString().toDouble().roundToInt()
                            preselected.add(selected_countries)
                        }
                        val multiSelectDialogCountry = MultiSelectDialog()
                            .title("Countries") //setting title for dialog
                            .titleSize(25.toFloat())
                            .positiveText("Done")
                            .negativeText("Cancel")
                            .setMinSelectionLimit(0) //you can set minimum checkbox selection limit (Optional)
                            .setMaxSelectionLimit(1) //you can set maximum checkbox selection limit (Optional)
                            .multiSelectList(listOfCountries) // the multi select model list with ids and name
                            .preSelectIDsList(preselected)
                            .onSubmit(object : MultiSelectDialog.SubmitCallbackListener {
                                override fun onSelected(
                                    p0: ArrayList<Int>,
                                    p1: ArrayList<String>?,
                                    p2: String?
                                ) {
                                    for (i in 0 until p0.size) {
                                        if (p0[i] != 0) {
                                            PrefsHelper().savePref("country_id", p0[i])
                                            val button =
                                                findViewById<Button>(R.id.nativecountryspinner)
                                            button.text = p1?.get(i) ?: "Select"
                                            val buttonStates =
                                                findViewById<Button>(R.id.nativestatespinner)
                                            buttonStates.text = "Select"
                                            val buttonCities =
                                                findViewById<Button>(R.id.nativecityspinner)
                                            buttonCities.text = "Select"

                                            selectedNativeCountry = p1!![i]
                                            selectedNativeState = "Select"
                                            selectedNativeCity = "Select"

                                            selectedNativeCountryId = p0[i]
                                            selectedNativeStateId = 0
                                            selectedNativeCityId = 0
                                        }
                                    }
                                }

                                override fun onCancel() {

                                }
                            })
                            .show(getSupportFragmentManager(), "multiSelectDialog")
                    }else{
//                        val listOfCountries = ArrayList<MultiSelectModel>()
//                        for (i in 0 until it.data.size) {
//                            listOfCountries.add(
//                                MultiSelectModel(
//                                    it.data[i].id,
//                                    it.data[i].country
//                                )
//                            );
//                        }
//                        val preselected = ArrayList<Int>()
//                        if (selectedPresentCountryId != 0) {
//                            val selected_countries =
//                                selectedPresentCountryId.toString().toDouble().roundToInt()
//                            preselected.add(selected_countries)
//                        }
//                        val multiSelectDialogCountry = MultiSelectDialog()
//                            .title("Countries") //setting title for dialog
//                            .titleSize(25.toFloat())
//                            .positiveText("Done")
//                            .negativeText("Cancel")
//                            .setMinSelectionLimit(0) //you can set minimum checkbox selection limit (Optional)
//                            .setMaxSelectionLimit(1) //you can set maximum checkbox selection limit (Optional)
//                            .multiSelectList(listOfCountries) // the multi select model list with ids and name
//                            .preSelectIDsList(preselected)
//                            .onSubmit(object : MultiSelectDialog.SubmitCallbackListener {
//                                override fun onSelected(
//                                    p0: ArrayList<Int>,
//                                    p1: ArrayList<String>?,
//                                    p2: String?
//                                ) {
//                                    for (i in 0 until p0.size) {
//                                        if (p0[i] != 0) {
//                                            PrefsHelper().savePref("country_id", p0[i])
//                                            val button =
//                                                findViewById<Button>(R.id.presentresidencecountryspinner)
//                                            button.text = p1?.get(i) ?: "Select"
//                                            val buttonStates =
//                                                findViewById<Button>(R.id.presentresidencestatespinner)
//                                            buttonStates.text = "Select"
//                                            val buttonCities =
//                                                findViewById<Button>(R.id.presentresidencecityspinner)
//                                            buttonCities.text = "Select"
//
//
//                                            selectedPresentCountry = p1!![i]
//                                            selectedPresentState = "Select"
//                                            selectedPresentCity = "Select"
//
//                                            selectedPresentCountryId = p0[i]
//                                            selectedPresentStateId = 0
//                                            selectedPresentCityId = 0
//                                        }
//                                    }
//                                }
//
//                                override fun onCancel() {
//
//                                }
//                            })
//                            .show(getSupportFragmentManager(), "multiSelectDialog")
                    }
                }
            }
        })
        viewModelRegionNative.countriesAPICallStatus.observe(this, Observer {
            processStatus(it)
        })
    }


    private fun setupObserversNativeStates() {
        viewModelRegionNative.statesResponseLiveData.observe(this, Observer {
            if (it!=null) {
                if (it.message == "Success") {
                    if (state) {
                        val listOfStates = ArrayList<MultiSelectModel>()
                        for (i in 0 until it.data.size) {
                            listOfStates.add(MultiSelectModel(it.data[i].id, it.data[i].state));
                        }

                        val preselected = ArrayList<Int>()
                        if (selectedNativeStateId != 0) {
                            val selected_states =
                                selectedNativeStateId.toString().toDouble().roundToInt()
                            preselected.add(selected_states)
                        }

                        val multiSelectDialogState = MultiSelectDialog()
                            .title("States") //setting title for dialog
                            .titleSize(25.toFloat())
                            .positiveText("Done")
                            .negativeText("Cancel")
                            .setMinSelectionLimit(0) //you can set minimum checkbox selection limit (Optional)
                            .setMaxSelectionLimit(1) //you can set maximum checkbox selection limit (Optional)
                            .multiSelectList(listOfStates) // the multi select model list with ids and name
                            .preSelectIDsList(preselected)
                            .onSubmit(object : MultiSelectDialog.SubmitCallbackListener {
                                override fun onSelected(
                                    p0: ArrayList<Int>,
                                    p1: ArrayList<String>?,
                                    p2: String?
                                ) {
                                    for (i in 0 until p0.size) {
                                        if (p0[i] != 0) {
                                            PrefsHelper().savePref("state_id", p0[i])
                                            val button =
                                                findViewById<Button>(R.id.nativestatespinner)
                                            button.text = p1?.get(i) ?: "Select"
                                            val buttonCities =
                                                findViewById<Button>(R.id.nativecityspinner)
                                            buttonCities.text = "Select"

                                            selectedNativeState = p1!![i]
                                            selectedNativeCity = "Select"

                                            selectedNativeStateId = p0[i]
                                            selectedNativeCityId = 0
                                        }
                                    }
                                }

                                override fun onCancel() {

                                }
                            })
                            .show(getSupportFragmentManager(), "multiSelectDialog")
                    }else{
//                        val listOfStates = ArrayList<MultiSelectModel>()
//                        for (i in 0 until it.data.size) {
//                            listOfStates.add(MultiSelectModel(it.data[i].id, it.data[i].state));
//                        }
//
//                        val preselected = ArrayList<Int>()
//                        if (selectedPresentStateId != 0) {
//                            val selected_states =
//                                selectedPresentStateId.toString().toDouble().roundToInt()
//                            preselected.add(selected_states)
//                        }
//
//                        val multiSelectDialogState = MultiSelectDialog()
//                            .title("States") //setting title for dialog
//                            .titleSize(25.toFloat())
//                            .positiveText("Done")
//                            .negativeText("Cancel")
//                            .setMinSelectionLimit(0) //you can set minimum checkbox selection limit (Optional)
//                            .setMaxSelectionLimit(1) //you can set maximum checkbox selection limit (Optional)
//                            .multiSelectList(listOfStates) // the multi select model list with ids and name
//                            .preSelectIDsList(preselected)
//                            .onSubmit(object : MultiSelectDialog.SubmitCallbackListener {
//                                override fun onSelected(
//                                    p0: ArrayList<Int>,
//                                    p1: ArrayList<String>?,
//                                    p2: String?
//                                ) {
//                                    for (i in 0 until p0.size) {
//                                        if (p0[i] != 0) {
//                                            PrefsHelper().savePref("state_id", p0[i])
//                                            val button =
//                                                findViewById<Button>(R.id.presentresidencestatespinner)
//                                            button.text = p1?.get(i) ?: "Select"
//                                            val buttonCities =
//                                                findViewById<Button>(R.id.presentresidencecityspinner)
//                                            buttonCities.text = "Select"
//
//                                            selectedPresentState = p1!![i]
//                                            selectedPresentCity = "Select"
//
//                                            selectedPresentStateId = p0[i]
//                                            selectedPresentCityId = 0
//                                        }
//                                    }
//                                }
//
//                                override fun onCancel() {
//
//                                }
//                            })
//                            .show(getSupportFragmentManager(), "multiSelectDialog")
                    }
                }
            }
        })
        viewModelRegionNative.stateesAPICallStatus.observe(this, Observer {
            processStatus(it)
        })
    }


    private fun setupObserversNativeCities() {
        viewModelRegionNative.citiesResponseLiveData.observe(this, Observer {
            if (it!=null) {
                if (it.message == "Success") {
                    if (city) {
                        val listOfCities = ArrayList<MultiSelectModel>()
                        for (i in 0 until it.data.size) {
                            listOfCities.add(MultiSelectModel(it.data[i].id, it.data[i].city));
                        }
                        val preselected = ArrayList<Int>()
                        if (selectedNativeCityId != 0) {
                            val selected_cities =
                                selectedNativeCityId.toString().toDouble().roundToInt()
                            preselected.add(selected_cities)
                        }
                        val multiSelectDialogCity = MultiSelectDialog()
                            .title("Cities") //setting title for dialog
                            .titleSize(25.toFloat())
                            .positiveText("Done")
                            .negativeText("Cancel")
                            .setMinSelectionLimit(0) //you can set minimum checkbox selection limit (Optional)
                            .setMaxSelectionLimit(1) //you can set maximum checkbox selection limit (Optional)
                            .multiSelectList(listOfCities) // the multi select model list with ids and name
                            .preSelectIDsList(preselected)
                            .onSubmit(object : MultiSelectDialog.SubmitCallbackListener {
                                override fun onSelected(
                                    p0: ArrayList<Int>,
                                    p1: ArrayList<String>?,
                                    p2: String?
                                ) {
                                    for (i in 0 until p0.size) {
                                        if (p0[i] != 0) {
                                            val button =
                                                findViewById<Button>(R.id.nativecityspinner)
                                            button.text = p1?.get(i) ?: "Select"

                                            selectedNativeCity = p1!![i]

                                            selectedNativeCityId = p0[i]
                                        }
                                    }
                                }

                                override fun onCancel() {

                                }
                            })
                            .show(getSupportFragmentManager(), "multiSelectDialog")
                    }else{
//                        val listOfCities = ArrayList<MultiSelectModel>()
//                        for (i in 0 until it.data.size) {
//                            listOfCities.add(MultiSelectModel(it.data[i].id, it.data[i].city));
//                        }
//                        val preselected = ArrayList<Int>()
//                        if (selectedPresentCityId != 0) {
//                            val selected_cities =
//                                selectedPresentCityId.toString().toDouble().roundToInt()
//                            preselected.add(selected_cities)
//                        }
//                        val multiSelectDialogCity = MultiSelectDialog()
//                            .title("Cities") //setting title for dialog
//                            .titleSize(25.toFloat())
//                            .positiveText("Done")
//                            .negativeText("Cancel")
//                            .setMinSelectionLimit(0) //you can set minimum checkbox selection limit (Optional)
//                            .setMaxSelectionLimit(1) //you can set maximum checkbox selection limit (Optional)
//                            .multiSelectList(listOfCities) // the multi select model list with ids and name
//                            .preSelectIDsList(preselected)
//                            .onSubmit(object : MultiSelectDialog.SubmitCallbackListener {
//                                override fun onSelected(
//                                    p0: ArrayList<Int>,
//                                    p1: ArrayList<String>?,
//                                    p2: String?
//                                ) {
//                                    for (i in 0 until p0.size) {
//                                        if (p0[i] != 0) {
//                                            val button =
//                                                findViewById<Button>(R.id.presentresidencecityspinner)
//                                            button.text = p1?.get(i) ?: "Select"
//
//                                            selectedPresentCity = p1!![i]
//
//                                            selectedPresentCityId = p0[i]
//                                        }
//                                    }
//                                }
//
//                                override fun onCancel() {
//
//                                }
//                            })
//                            .show(getSupportFragmentManager(), "multiSelectDialog")
                    }
                }
            }
        })
        viewModelRegionNative.citiesAPICallStatus.observe(this, Observer {
            processStatus(it)
        })
    }



    private fun setupObserversPresentCountries() {
        viewModelRegionPresent.countriesResponseLiveData.observe(this, Observer {
            if (it!=null) {
                if (it.message == "Success") {
                    if (!country) {
                        val listOfCountries = ArrayList<MultiSelectModel>()
                        for (i in 0 until it.data.size) {
                            listOfCountries.add(
                                MultiSelectModel(
                                    it.data[i].id,
                                    it.data[i].country
                                )
                            );
                        }
                        val preselected = ArrayList<Int>()
                        if (selectedPresentCountryId != 0) {
                            val selected_countries =
                                selectedPresentCountryId.toString().toDouble().roundToInt()
                            preselected.add(selected_countries)
                        }
                        val multiSelectDialogCountry = MultiSelectDialog()
                            .title("Countries") //setting title for dialog
                            .titleSize(25.toFloat())
                            .positiveText("Done")
                            .negativeText("Cancel")
                            .setMinSelectionLimit(0) //you can set minimum checkbox selection limit (Optional)
                            .setMaxSelectionLimit(1) //you can set maximum checkbox selection limit (Optional)
                            .multiSelectList(listOfCountries) // the multi select model list with ids and name
                            .preSelectIDsList(preselected)
                            .onSubmit(object : MultiSelectDialog.SubmitCallbackListener {
                                override fun onSelected(
                                    p0: ArrayList<Int>,
                                    p1: ArrayList<String>?,
                                    p2: String?
                                ) {
                                    for (i in 0 until p0.size) {
                                        if (p0[i] != 0) {
                                            PrefsHelper().savePref("country_id_present", p0[i])
                                            val button =
                                                findViewById<Button>(R.id.presentresidencecountryspinner)
                                            button.text = p1?.get(i) ?: "Select"
                                            val buttonStates =
                                                findViewById<Button>(R.id.presentresidencestatespinner)
                                            buttonStates.text = "Select"
                                            val buttonCities =
                                                findViewById<Button>(R.id.presentresidencecityspinner)
                                            buttonCities.text = "Select"


                                            selectedPresentCountry = p1!![i]
                                            selectedPresentState = "Select"
                                            selectedPresentCity = "Select"

                                            selectedPresentCountryId = p0[i]
                                            selectedPresentStateId = 0
                                            selectedPresentCityId = 0
                                        }
                                    }
                                }

                                override fun onCancel() {

                                }
                            })
                            .show(getSupportFragmentManager(), "multiSelectDialog")
                    }

                }
            }
        })
        viewModelRegionPresent.countriesAPICallStatus.observe(this, Observer {
            processStatus(it)
        })
    }


    private fun setupObserversPresentStates() {
        viewModelRegionPresent.statesResponseLiveData.observe(this, Observer {
            if (it!=null) {
                if (it.message == "Success") {
                    if (!state) {
                        val listOfStates = ArrayList<MultiSelectModel>()
                        for (i in 0 until it.data.size) {
                            listOfStates.add(MultiSelectModel(it.data[i].id, it.data[i].state));
                        }

                        val preselected = ArrayList<Int>()
                        if (selectedPresentStateId != 0) {
                            val selected_states =
                                selectedPresentStateId.toString().toDouble().roundToInt()
                            preselected.add(selected_states)
                        }

                        val multiSelectDialogState = MultiSelectDialog()
                            .title("States") //setting title for dialog
                            .titleSize(25.toFloat())
                            .positiveText("Done")
                            .negativeText("Cancel")
                            .setMinSelectionLimit(0) //you can set minimum checkbox selection limit (Optional)
                            .setMaxSelectionLimit(1) //you can set maximum checkbox selection limit (Optional)
                            .multiSelectList(listOfStates) // the multi select model list with ids and name
                            .preSelectIDsList(preselected)
                            .onSubmit(object : MultiSelectDialog.SubmitCallbackListener {
                                override fun onSelected(
                                    p0: ArrayList<Int>,
                                    p1: ArrayList<String>?,
                                    p2: String?
                                ) {
                                    for (i in 0 until p0.size) {
                                        if (p0[i] != 0) {
                                            PrefsHelper().savePref("state_id_present", p0[i])
                                            val button =
                                                findViewById<Button>(R.id.presentresidencestatespinner)
                                            button.text = p1?.get(i) ?: "Select"
                                            val buttonCities =
                                                findViewById<Button>(R.id.presentresidencecityspinner)
                                            buttonCities.text = "Select"

                                            selectedPresentState = p1!![i]
                                            selectedPresentCity = "Select"

                                            selectedPresentStateId = p0[i]
                                            selectedPresentCityId = 0
                                        }
                                    }
                                }

                                override fun onCancel() {

                                }
                            })
                            .show(getSupportFragmentManager(), "multiSelectDialog")
                    }
                }
            }
        })
        viewModelRegionPresent.stateesAPICallStatus.observe(this, Observer {
            processStatus(it)
        })
    }


    private fun setupObserversPresentCities() {
        viewModelRegionPresent.citiesResponseLiveData.observe(this, Observer {
            if (it!=null) {
                if (it.message == "Success") {
                    if (!city) {
                        val listOfCities = ArrayList<MultiSelectModel>()
                        for (i in 0 until it.data.size) {
                            listOfCities.add(MultiSelectModel(it.data[i].id, it.data[i].city));
                        }
                        val preselected = ArrayList<Int>()
                        if (selectedPresentCityId != 0) {
                            val selected_cities =
                                selectedPresentCityId.toString().toDouble().roundToInt()
                            preselected.add(selected_cities)
                        }
                        val multiSelectDialogCity = MultiSelectDialog()
                            .title("Cities") //setting title for dialog
                            .titleSize(25.toFloat())
                            .positiveText("Done")
                            .negativeText("Cancel")
                            .setMinSelectionLimit(0) //you can set minimum checkbox selection limit (Optional)
                            .setMaxSelectionLimit(1) //you can set maximum checkbox selection limit (Optional)
                            .multiSelectList(listOfCities) // the multi select model list with ids and name
                            .preSelectIDsList(preselected)
                            .onSubmit(object : MultiSelectDialog.SubmitCallbackListener {
                                override fun onSelected(
                                    p0: ArrayList<Int>,
                                    p1: ArrayList<String>?,
                                    p2: String?
                                ) {
                                    for (i in 0 until p0.size) {
                                        if (p0[i] != 0) {
                                            val button =
                                                findViewById<Button>(R.id.presentresidencecityspinner)
                                            button.text = p1?.get(i) ?: "Select"

                                            selectedPresentCity = p1!![i]

                                            selectedPresentCityId = p0[i]
                                        }
                                    }
                                }

                                override fun onCancel() {

                                }
                            })
                            .show(getSupportFragmentManager(), "multiSelectDialog")
                    }

                }
            }
        })
        viewModelRegionPresent.citiesAPICallStatus.observe(this, Observer {
            processStatus(it)
        })
    }




    private fun main_code(it: PersonalInfoResponse) {
        val height: MutableList<Int> = arrayListOf()
        for (i in 100 until 221){
            height.add(i)
        }
        val adapter_height = ArrayAdapter(
            this,
            R.layout.color_spinner_layout,
            height
        )
        adapter_height.setDropDownViewResource(R.layout.spinner_dropdown_layout)
        height_edit.setAdapter(adapter_height)
        height_edit.setSelection(0)


        val weight: MutableList<Int> = arrayListOf()
        for (i in 30 until 151){
            weight.add(i)
        }
        val adapter_age = ArrayAdapter(
            this,
            R.layout.color_spinner_layout,
            weight
        )
        adapter_age.setDropDownViewResource(R.layout.spinner_dropdown_layout)
        weight_edit.setAdapter(adapter_age)
        weight_edit.setSelection(0)



        if(it.data.userChoice.firstName != ""){
            first_name_edit.text= Editable.Factory.getInstance().newEditable(it.data.userChoice.firstName)
        }

        if(it.data.userChoice.middleName != ""){
            middle_name_edit.text= Editable.Factory.getInstance().newEditable(it.data.userChoice.middleName)
        }

        if(it.data.userChoice.lastName != ""){
            last_name_edit.text= Editable.Factory.getInstance().newEditable(it.data.userChoice.lastName)
        }


        if(it.data.userChoice.alternativeMobileNumber != ""){
            alternate_mobile_edittext.text= Editable.Factory.getInstance().newEditable(it.data.userChoice.alternativeMobileNumber)
        }

        if(it.data.userChoice.gender != 0){
            val  list_choice:MutableList<String> = arrayListOf()
            val list_id:MutableList<String> = arrayListOf()
            val gender = it.data.choice.gender
            for (i in 0 until gender.size) {
                val obj = gender[i]
                list_choice.add(obj.choice)
                list_id.add(obj.id.toString())
            }
            var position = list_id.indexOf(it.data.userChoice.gender.toString())
            var selected_id = list_choice.get(position)
            if (selected_id == "Male"){
                checkBoxmale1.isChecked = true
                            }
            else if(selected_id=="Female"){
                checkBoxfemale1.isChecked = true
                male_female_value = 2
            }
        }


        if(it.data.userChoice.fathersName != ""){
            father_name_edit.text= Editable.Factory.getInstance().newEditable(it.data.userChoice.fathersName)
        }

        if (it.data.userChoice.height != null){
            var height = it.data.userChoice.height.toString().replace(".0", "")
            val new_height = height.toInt()
            height_edit?.setSelection(new_height - 100)
        }
        if (it.data.userChoice.weight !=null) {
            var weight =it.data.userChoice.weight.toString().replace(".0","")
            val new_weight = weight.toInt()
            weight_edit?.setSelection(new_weight-30)
        }

        if(it.data.userChoice.profilePic != null){
            PIC_UPLOAD = true
            val full_path = it.data.userChoice.profilePic
            val pic_name: String? = full_path.substringAfterLast("/")
            editTextupload_pic.text= Editable.Factory.getInstance().newEditable(pic_name)
        }

        if(it.data.userChoice.native_country!= null){
            if (it.data.userChoice.native_country.country!="") {
                nativecountryspinner.text = Editable.Factory.getInstance()
                    .newEditable(it.data.userChoice.native_country.country)
                nativestatespinner.text = Editable.Factory.getInstance()
                    .newEditable(it.data.userChoice.native_state.state)
                nativecityspinner.text =
                    Editable.Factory.getInstance().newEditable(it.data.userChoice.native_city.city)

                selectedNativeCountry = it.data.userChoice.native_country.country
                selectedNativeState = it.data.userChoice.native_state.state
                selectedNativeCity = it.data.userChoice.native_city.city
                selectedNativeCountryId = it.data.userChoice.native_country.id
                PrefsHelper().savePref("country_id", selectedNativeCountryId.toString().toDouble().roundToInt())
                selectedNativeStateId = it.data.userChoice.native_state.id
                PrefsHelper().savePref("state_id", selectedNativeStateId.toString().toDouble().roundToInt())
                selectedNativeCityId = it.data.userChoice.native_city.id
            }
        }

        if(it.data.userChoice.present_country!= null){
            if (it.data.userChoice.present_country.country!="") {
                presentresidencecountryspinner.text = Editable.Factory.getInstance()
                    .newEditable(it.data.userChoice.present_country.country)
                presentresidencestatespinner.text = Editable.Factory.getInstance()
                    .newEditable(it.data.userChoice.present_state.state)
                presentresidencecityspinner.text =
                    Editable.Factory.getInstance().newEditable(it.data.userChoice.present_city.city)

                selectedPresentCountryId = it.data.userChoice.present_country.id
                PrefsHelper().savePref("country_id_present", selectedPresentCountryId.toString().toDouble().roundToInt())
                selectedPresentStateId = it.data.userChoice.present_state.id
                PrefsHelper().savePref("state_id_present", selectedPresentStateId.toString().toDouble().roundToInt())
                selectedPresentCityId = it.data.userChoice.present_city.id
                selectedPresentCountry = it.data.userChoice.present_country.country
                selectedPresentState = it.data.userChoice.present_state.state
                selectedPresentCity = it.data.userChoice.present_city.city
            }
        }

        if(it.data.userChoice.motherTongue != ""){
            mother_t_text.text= Editable.Factory.getInstance().newEditable(it.data.userChoice.motherTongue)
        }
        if(it.data.userChoice.altCountryCode != null){
            country_code_picker_alt_num.setCountryForPhoneCode(it.data.userChoice.altCountryCode)
        }
        if(it.data.userChoice.dob != null){
            var dob = it.data.userChoice.dob
//            dob = dob.replace("-","/")
            pickDateBtn.text= Editable.Factory.getInstance().newEditable(dob)
        }

        new_function(it.data.choice.status,status_spinner,it.data.userChoice.status)
        setupListeners1(it)
    }

    private fun id_to_recieved(
        full_list1: List<Statu>,
        selectedOption: Int
    ): String
    {
        val  list_choice:MutableList<String> = arrayListOf()
        val list_id:MutableList<String> = arrayListOf()
        for (i in 0 until full_list1.size) {
            val obj = full_list1[i]
            list_choice.add(obj.choice)
            list_id.add(obj.id.toString())
        }
        var position = list_id.indexOf(selectedOption.toString())
        var selected_id = list_choice.get(position)
        return selected_id
    }

    private fun new_function(
        full_list_edu: List<Statu>,
        spinner_id: Spinner,
        selected_option: Int
    ) {
        val  list_familyinfo:MutableList<String> = arrayListOf()
        if (selected_option == 0)
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

    private fun setupobserverspersonalinfo(){
        viewModel2.personalInfoResponseLiveData.observe(this, Observer {
            if(it!=null){
                if(it.message=="Success"){
                    main_code(it)

                }
                else{
                    Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                }
            }
        })
        viewModel2.personalInfoApiCallStatus.observe(this, Observer {
            processStatus(it)
        })
    }



    private fun setupListeners1(it: PersonalInfoResponse) {
        val full_data = it.data.choice
        val selected_data = it.data.userChoice
        val c=Calendar.getInstance()
        val year1 = c.get(Calendar.YEAR)
        val year = (year1-18)

        val month = c.get(Calendar.MONTH)
        val month1 = month+1
        val day= c.get(Calendar.DAY_OF_MONTH)

        val df=year.toString().plus(".").plus(month1.toString()).plus(".").plus(day.toString())
        val df1 = SimpleDateFormat("yyyy.MM.dd")
var flag = 0
        pickDateBtn.setOnClickListener{
            val dpd = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view,  year, monthOfYear, dayOfMonth ->
                if(monthOfYear+1>9) {
                    if(dayOfMonth>9)
                    pickDateBtn.setText("" + year + "-" + (monthOfYear + 1) + "-" + dayOfMonth)
                    else
                        pickDateBtn.setText("" + year + "-" + (monthOfYear + 1) + "-0" + dayOfMonth)
                }
                else{
                    if(dayOfMonth>9)
                    pickDateBtn.setText("" + year + "-0" + (monthOfYear + 1) + "-" + dayOfMonth)
                    else
                        pickDateBtn.setText("" + year + "-0" + (monthOfYear + 1) + "-0" + dayOfMonth)
                }
                flag = 1
            },year , month, day)
            dpd.getDatePicker().setMaxDate(df1.parse(df).time)
            dpd.show()
        }
        submitbio.setOnClickListener {
            val firstname = first_name_edit.text.trim()
            val lastname = last_name_edit.text.trim()
            val fathername = father_name_edit.text.trim()
            val alt_mobile = alternate_mobile_edittext.text.trim()
            var height =  height_edit.selectedItem.toString().toInt()
            var weight = weight_edit.selectedItem.toString().toInt()

            if (checkBoxmale1.isChecked == true) {
                male_female_value = 1
            } else if (checkBoxfemale1.isChecked == true) {
                male_female_value = 2
            }
            if (Validator.isValidName(firstname)) {
                if (Validator.isValidName(lastname)) {
                    if (gender.getCheckedRadioButtonId() != -1) {
                        if (!fathername.isEmpty()) {
                            if(flag == 1 ||selected_data.dob != null ) {
                                if (height>99 && height<221) {
                                    if (weight>29 && weight<151) {
                                        if (PIC_UPLOAD == true) {
                                            if(status_spinner.selectedItem != "Select") {
                                                if (male_female_value != 0) {
                                                    if (presentresidencecountryspinner.text.toString()!="Select") {
                                                        if (presentresidencestatespinner.text.toString()!="Select") {
                                                            if (presentresidencecityspinner.text.toString()!="Select") {
                                                                if (nativecountryspinner.text.toString()!="Select") {
                                                                    if (nativestatespinner.text.toString()!="Select") {
                                                                        if (nativecityspinner.text.toString()!="Select") {
                                                                            if (mother_t_text.text.trim().isNotEmpty()) {
                                                                                var status =
                                                                                    id_to_be_sent(
                                                                                        full_data.status,
                                                                                        status_spinner.selectedItem
                                                                                    )
                                                                                val status1 =
                                                                                    status.toString()
                                                                                val status2 =
                                                                                    status1.toInt()
                                                                                viewModel.profileinfo(
                                                                                    alternate_mobile_edittext.text.trim().toString(),
                                                                                    pickDateBtn.text.trim().toString(),
                                                                                    first_name_edit.text.trim().toString(),
                                                                                    male_female_value,
                                                                                    height_edit.selectedItem.toString(),
                                                                                    last_name_edit.text.trim().toString(),
                                                                                    middle_name_edit.text.trim().toString(),
                                                                                    mother_t_text.text.trim().toString(),
                                                                                    1,
                                                                                    nativecountryspinner.text.toString(),
                                                                                    presentresidencecountryspinner.text.toString(),
                                                                                    status2,
                                                                                    weight_edit.selectedItem.toString(),
                                                                                    father_name_edit.text.trim().toString(),
                                                                                    country_code_picker_alt_num.selectedCountryCodeAsInt,
                                                                                    selectedNativeCountry,
                                                                                    selectedNativeCountryId,
                                                                                    selectedNativeState,
                                                                                    selectedNativeStateId,
                                                                                    selectedNativeCity,
                                                                                    selectedNativeCityId,
                                                                                    selectedPresentCountry,
                                                                                    selectedPresentCountryId,
                                                                                    selectedPresentState,
                                                                                    selectedPresentStateId,
                                                                                    selectedPresentCity,
                                                                                    selectedPresentCityId
                                                                                )
                                                                            } else {
                                                                                Toast.makeText(
                                                                                    applicationContext,
                                                                                    "Please enter your mother tongue",
                                                                                    Toast.LENGTH_SHORT
                                                                                ).show()
                                                                            }
                                                                        }else{
                                                                            Toast.makeText(
                                                                                applicationContext,
                                                                                "Please select your native city",
                                                                                Toast.LENGTH_SHORT
                                                                            ).show()
                                                                        }
                                                                    }else{
                                                                        Toast.makeText(
                                                                            applicationContext,
                                                                            "Please select your native state",
                                                                            Toast.LENGTH_SHORT
                                                                        ).show()
                                                                    }
                                                                }else{
                                                                    Toast.makeText(
                                                                        applicationContext,
                                                                        "Please select your native country",
                                                                        Toast.LENGTH_SHORT
                                                                    ).show()
                                                                }
                                                            }else{
                                                                Toast.makeText(
                                                                    applicationContext,
                                                                    "Please select your present residence city",
                                                                    Toast.LENGTH_SHORT
                                                                ).show()
                                                            }
                                                        }else{
                                                            Toast.makeText(
                                                                applicationContext,
                                                                "Please select your present residence state",
                                                                Toast.LENGTH_SHORT
                                                            ).show()
                                                        }
                                                    }else{
                                                        Toast.makeText(
                                                            applicationContext,
                                                            "Please select your present residence country",
                                                            Toast.LENGTH_SHORT
                                                        ).show()
                                                    }
                                                } else {
                                                    Toast.makeText(
                                                        applicationContext,
                                                        "Please select a gender",
                                                        Toast.LENGTH_SHORT
                                                    ).show()
                                                }

                                            }else{
                                                Toast.makeText(
                                                    applicationContext,
                                                    "Please select your martial status",
                                                    Toast.LENGTH_LONG
                                                ).show()
                                            }
                                        } else {
                                            Toast.makeText(
                                                applicationContext,
                                                "Please select a profile picture",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        }
                                    } else {

                                        Toast.makeText(this, "Please select a weight between 30 & 150 kgs", Toast.LENGTH_LONG).show()

                                    }
                                } else {
                                    Toast.makeText(this, "Please select a height between 100 & 220 cms", Toast.LENGTH_LONG).show()

                                }
                            }else{
                                Toast.makeText(
                                    applicationContext,
                                    "Please select your date of birth",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        } else {

                            Toast.makeText(this, "Please enter your father's name", Toast.LENGTH_LONG).show()

                        }

                    } else {
                        Toast.makeText(applicationContext,"Please select a gender", Toast.LENGTH_LONG).show()
                    }
                } else {

                    Toast.makeText(this, "Please enter your last name", Toast.LENGTH_LONG).show()

                }
            } else {

                Toast.makeText(this, "Please enter your first name", Toast.LENGTH_LONG).show()

            }
        }
    }

    private fun id_to_be_sent(full_list: List<Statu>, selectedItem: Any?): Any {

        val  list_choice:MutableList<String> = arrayListOf()
        val list_id:MutableList<Int> = arrayListOf()
        for (i in 0 until full_list.size) {
            val obj = full_list[i]
            list_choice.add(obj.choice)
            list_id.add(obj.id)
        }
        var position = list_choice.indexOf(selectedItem.toString())
        var selected_id = list_id.get(position)
        return selected_id
    }

    private fun setupListernersForReligious() {

        viewModel3.religiosInfoLiveData(null)
    }
    /**
     * Write all LiveData observers in this method
     */
    private fun setupObservers(type: Int) {

        viewModel.loginResponseLiveData.observe(this, Observer {
            if (it != null) {
                if (it.message == "Success") {
                    Toast.makeText(this, "Personal Info updated successfully", Toast.LENGTH_LONG).show()
                    PrefsHelper().savePref("user_name", it.data.firstName)
                    PrefsHelper().savePref("email", it.data.email)
                    PrefsHelper().savePref("mobile_number", it.data.mobileNumber)
                    PrefsHelper().savePref("userKey", it.data.userKey.toString())

                    if(type != 1) {
                        PrefsHelper().savePref("Account_Status",4)
                        val intent = Intent(this, PaymentPackagesScreen::class.java)
                        startActivity(intent)
                    }
                    else{

                        PrefsHelper().savePref("Account_Status",8)
                        PrefsHelper().savePref("Reference",8)

                        viewModel3.religiousInfoResponseLiveData.observe(this, Observer {
                            if (it != null) {
                                if (it.message == "Success") {
                                    val jsonArray = gson.toJson(it.data)
                                    PrefsHelper().savePref("jsonArray",jsonArray)
                                    val intent= Intent(this, ReligiousInfoVersion2::class.java)


                                    startActivity(intent)
                                }
                                else{
                                    Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                                }
                            }
                        })
                        //observe API call status
                        viewModel3.religiousInfoApiCallStatus.observe(this, Observer {
                            processStatus1(it)
                        })



                    }

                    // Success login.  Add the success scenario here ex: Move to next screen
                }
                else{
                    Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                }
            }
        })
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
            StatusType.ERROR -> {
                CommonUtils().showSnackbar(submitbio.rootView,"Please Try again")
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
                CommonUtils().showSnackbar(upload_profile_photo.rootView,"Please Try again")
                dismissDialog()



            }
            StatusType.LOADING_MORE -> {
                // CommonUtils().showSnackbar(binding.root, "Loading more..")
            }
            StatusType.NO_NETWORK -> {
                Toast.makeText(this, "Please check internet connection", Toast.LENGTH_SHORT).show()
            }
            StatusType.SESSION_EXPIRED -> {
                //CommonUtils().showSnackbar(upload_profile_photo.rootView,"session expired")
            }
        }
    }



    @TargetApi(Build.VERSION_CODES.M)
    @RequiresApi(Build.VERSION_CODES.M)
    private fun setupListenersUploadPhoto() {
        upload_profile_photo.setOnClickListener {
            selectImage()
        }
    }

    private fun setupObserversUploadPhoto() {
        viewModel1.loginResponseLiveData.observe(this, Observer {
            if (it!=null){
                if (it.message=="Success"){
                    PrefsHelper().savePref("user_pic", it.data.profile_pic)
                    val pic = it.data.profile_pic
                    if (pic!= ""){
                        PIC_UPLOAD = true
//                        Toast.makeText(this, "Upload successful", Toast.LENGTH_SHORT).show()
                    }else{
//                        Toast.makeText(this, "Upload failed", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })

        //observe API call status
        viewModel1.loginAPICallStatus.observe(this, Observer {
            processStatus1(it)
        })
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @RequiresApi(Build.VERSION_CODES.KITKAT)
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) { super.onActivityResult(requestCode, resultCode, data)
        // Result code is RESULT_OK only if the user selects an Image
        if (resultCode == Activity.RESULT_OK) when (requestCode) {
            GALLERY_REQUEST_CODE -> {

                if (data != null) {
                    try {
                        val selectedImage: Uri? = data?.data
                        val photoFile: File? = try {
                            createImageFile()
                        } catch (ex: IOException) {
                            // Error occurred while creating the File
                            null
                        }

                        var inputStream = this?.contentResolver?.openInputStream(data.data!!)
                        var fileOutputStream = FileOutputStream(photoFile)
                        copyBytes(inputStream!!, fileOutputStream)
                        fileOutputStream.close()
                        inputStream.close()

                        uploadFile()

                        val filename = FilePath.getFileName(this, selectedImage)
                        editTextupload_pic.setText(filename)

                    } catch (e: Exception) {
                        Log.e("Eror", "Error while creating temp file", e)
                    }

                }

            }
            IMAGE_CAPTURE_CODE ->{
                uploadFile()
                val filename = fileTemp?.name
                editTextupload_pic.setText(filename)

            }
        }
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @RequiresApi(Build.VERSION_CODES.KITKAT)
    private fun uploadFile() { // create upload service client
// use the FileUtils to get the actual file by uri
        //val file = image_decodable_string
        // create RequestBody instance from file
        //val ss = MediaType.parse(contentResolver.getType(file))
        val requestFile = RequestBody.create(
            MediaType.parse("multipart/form-data"),
            fileTemp
        )
        // MultipartBody.Part is used to send also the actual file name
        val body =
            MultipartBody.Part.createFormData("file", fileTemp?.name, requestFile)
        Log.d("Body", body.toString())
        val name = "Profile Pic 1"
        val nameRequestBody = RequestBody.create(MediaType.parse("text/plain"), name)
        //finally, execute the request
        viewModel1.uploadPhotoViewModel(body, nameRequestBody)
    }




    @TargetApi(Build.VERSION_CODES.KITKAT)
    @RequiresApi(Build.VERSION_CODES.KITKAT)
    private fun pickFromGallery() {
        val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        this.startActivityForResult(galleryIntent, GALLERY_REQUEST_CODE)
    }


    @TargetApi(Build.VERSION_CODES.M)
    @RequiresApi(Build.VERSION_CODES.M)
    private fun selectImage() {
        val options =
            arrayOf<CharSequence>("Open Camera", "Choose from Gallery", "Cancel")
        val builder: AlertDialog.Builder =
            AlertDialog.Builder(this)
        builder.setTitle("Upload Image")
        builder.setItems(options, DialogInterface.OnClickListener { dialog, item ->
            if (options[item] == "Open Camera") {
                if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED ||
                    checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED){
                    val permission = arrayOf(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)

                    requestPermissions(permission, PERMISSION_CODE)
                }else{
                    openCamera()
                }
            }

            else if (options[item] == "Choose from Gallery") {
                pickFromGallery()


            } else if (options[item] == "Cancel") {
                dialog.dismiss()
            }
        })
        builder.show()
    }




    private fun openCamera() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            // Ensure that there's a camera activity to handle the intent
            takePictureIntent.resolveActivity(this?.packageManager)?.also {
                // Create the File where the photo should go
                val photo_file = try {
                    createImageFile()
                } catch (ex: IOException) {
                    // Error occurred while creating the File
                    //...
                    null
                }
                // Continue only if the File was successfully created
                val values = ContentValues()
                values.put(MediaStore.Images.Media.TITLE, "New Picture")
                values.put(MediaStore.Images.Media.DESCRIPTION, "From the Camera")
                photo_file?.also {
                    val photoURI: Uri = FileProvider.getUriForFile(
                        this!!,
                        "com.neuro.neuroharmony.fileprovider",
                        it
                    )
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                    takePictureIntent.putExtra("return-data", true)
                    this.startActivityForResult(takePictureIntent, IMAGE_CAPTURE_CODE)
                }

            }
        }
        /*val values = ContentValues()
        values.put(MediaStore.Images.Media.TITLE, "New Picture")
        values.put(MediaStore.Images.Media.DESCRIPTION, "From the Camera")
        image_uri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)

        val camera_intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        camera_intent.putExtra(MediaStore.EXTRA_OUTPUT, image_uri)
        startActivityForResult(camera_intent, IMAGE_CAPTURE_CODE)*/

    }




    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when(requestCode){
            PERMISSION_CODE -> {
                if (grantResults.size > 0 && grantResults[0]== PackageManager.PERMISSION_GRANTED&&grantResults[1]== PackageManager.PERMISSION_GRANTED){
                    openCamera()
                }else{
                    Toast.makeText(this,"Permission Denied", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }



    @Throws(IOException::class)
    private fun createImageFile(): File {
        // Create an image file name
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File = this?.getExternalFilesDir(Environment.DIRECTORY_PICTURES)!!
        return File.createTempFile(
            "JPEG_${timeStamp}_", /* prefix */
            ".jpg", /* suffix */
            storageDir /* directory */
        ).apply {
            // Save a file: path for use with ACTION_VIEW intents
            fileTemp = this
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


