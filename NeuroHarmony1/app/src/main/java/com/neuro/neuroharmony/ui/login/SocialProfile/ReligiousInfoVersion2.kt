package com.neuro.neuroharmony.ui.login.SocialProfile

import android.os.Bundle
import com.neuro.neuroharmony.R
import android.annotation.TargetApi
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.preference.PreferenceManager
import android.text.Editable
import android.util.Log
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.abdeveloper.library.MultiSelectDialog
import com.abdeveloper.library.MultiSelectModel
import com.google.gson.Gson
import com.neuro.neuroharmony.ui.login.*
import com.neuro.neuroharmony.utils.CommonUtils
import com.neuro.neuroharmony.utils.PrefsHelper
import kotlinx.android.synthetic.main.activity_religious_info_version2.casteSpinner
import kotlinx.android.synthetic.main.activity_religious_info_version2.religionSpinner
import kotlinx.android.synthetic.main.activity_religious_info_version2.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.json.JSONObject
import java.io.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt

class ReligiousInfoVersion2: BaseActivity(), AdapterView.OnItemSelectedListener {

    lateinit var viewModel: ReligiousInfoViewModel
    lateinit var viewModel1: ReligiousInfoSubmitViewModel
    private lateinit var viewModel2: FamilyInfoViewModel
    private lateinit var viewModelReligion: ReligionViewModel
    private lateinit var viewModelCaste: ReligionViewModel
    val gson = Gson()
    var jsonArray = ""
    var jsonArrayReligion = ""
    var jsonArrayCaste = ""
    var body: MultipartBody.Part? = null

    val STORAGE_REQUEST_CODE = 121
    var selected_religion : Any? = null
    var selected_caste : Any? = null

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

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @RequiresApi(Build.VERSION_CODES.KITKAT)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_religious_info_version2)
        val mySPrefs = PreferenceManager.getDefaultSharedPreferences(this)
        val editor = mySPrefs.edit()
        editor.remove("religion_id")
        editor.remove("caste_id")
        editor.apply()
        PrefsHelper().savePref("Account_Status",12)
        viewModel = ViewModelProviders.of(this)[ReligiousInfoViewModel::class.java]
        viewModel1 = ViewModelProviders.of(this)[ReligiousInfoSubmitViewModel::class.java]
        viewModel2 = ViewModelProviders.of(this)[FamilyInfoViewModel::class.java]
        viewModelReligion = ViewModelProviders.of(this)[ReligionViewModel::class.java]
        viewModelCaste = ViewModelProviders.of(this)[ReligionViewModel::class.java]
        setupObservers()
        setupListerners()

        setupObserversReligionData()
        setupObserversCasteData()
        setupListernersSubmit()
        setupObserversSubmit()
        setupobserversFamilyinfo()
        religionSpinner.setOnClickListener {
            viewModelReligion.ReligionInfoLiveData()
        }
        casteSpinner.setOnClickListener {
            if(mySPrefs.contains("religion_id")) {
                val religion_id = PrefsHelper().getPref<Int>("religion_id")
                val list = ArrayList<Int>()
                list.add(religion_id)
                viewModelCaste.CasteInfoLiveData(list)
            }else{
                Toast.makeText(this, "Please select a religion", Toast.LENGTH_SHORT).show()
            }
        }
        UploadHorscopeButton.setOnClickListener {
            pickFromDevice()
        }

    }

    private fun pickFromDevice() {
        //Create an Intent with action as ACTION_PICK
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "application/pdf"
        //We pass an extra array with the accepted mime types. This will ensure only components with these MIME types as targeted.
        val mimeTypes =
            arrayOf("application/pdf")
        intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes)
        // Launching the Intent
        startActivityForResult(intent, STORAGE_REQUEST_CODE)
    }


    @TargetApi(Build.VERSION_CODES.KITKAT)
    @RequiresApi(Build.VERSION_CODES.KITKAT)
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) { super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) when (requestCode){
            STORAGE_REQUEST_CODE -> {
                //data.getData return the content URI for the selected Image
                try {
                    val selectedPdf: Uri? = data?.data
                    val filename = FilePath.getFileName(this, selectedPdf)
                    val photoFile: File? = try {
                        createImageFile(filename)
                    } catch (ex: IOException) {
                        // Error occurred while creating the File
                        null
                    }
                    var inputStream = this?.contentResolver?.openInputStream(data?.data!!)
                    var fileOutputStream = FileOutputStream(photoFile)
                    copyBytes(inputStream!!, fileOutputStream)
                    fileOutputStream.close()
                    inputStream.close()

                    uploadFile()

                    Toast.makeText(this, filename, Toast.LENGTH_SHORT).show()
                    UploadHoroscopeEditText?.setText(filename)
                }catch (e: Exception) {
                    Log.e("Eror", "Error while creating temp file", e)
                }
            }
        }
    }

    private fun uploadFile() {
        val requestFile = RequestBody.create(
            MediaType.parse("multipart/form-data"),
            fileTemp
        )
        // MultipartBody.Part is used to send also the actual file name
        body =
            MultipartBody.Part.createFormData("horoscope", fileTemp?.name, requestFile)
    }

    private fun setupObserversSubmit() {
        viewModel1.religiousInfoSubmitResponseLiveData.observe(this, Observer {
            if(it!=null){
                if( it.message=="Success"){

                    Toast.makeText(this,"Religious Info updated successfully", Toast.LENGTH_SHORT).show()
                    viewModel2.familyInfoLiveData(null)


                }
                else{
                    Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                }
            }
        })
        //observe API call status
        viewModel1.religiousInfoSubmitApiCallStatus.observe(this, Observer {
            processStatus(it)
        })

    }

    private fun setupobserversFamilyinfo(){
        viewModel2.familyInfoResponseLiveData.observe(this, Observer {
            if(it!=null){
                if(it.message=="Success"){



                    val intent = Intent(this, FamilyInfo::class.java)
                    val response = gson.toJson(it)
                    intent.putExtra("response",response)
                    PrefsHelper().savePref("response",response)
                    PrefsHelper().savePref("Account_Status",5)
                    startActivity(intent)
                }
                else{
                    Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                }
            }
        })
        viewModel2.familyInfoApiCallStatus.observe(this, Observer {
            processStatus(it)
        })
    }

    private fun setupListernersSubmit() {
        SaveReligiousInfobutton.setOnClickListener {
            val religion = findViewById<Button>(R.id.religionSpinner)
            val caste = findViewById<Button>(R.id.casteSpinner)

            val object_main1 = JSONObject(jsonArray)
            val object_main = object_main1.getJSONObject("choice")

            val deep_array = object_main.getJSONArray("deeply_religious")
            val deepreligion_name: MutableList<String> = arrayListOf()
            val deepreligion_id: MutableList<Int> = arrayListOf()
            for (i in 0 until deep_array.length()) {
                val deeptemp = deep_array.getJSONObject(i)
                deepreligion_name.add(deeptemp["deeply_religious"].toString())
                deepreligion_id.add(deeptemp["id"].toString().toInt())
            }

            //above is to get the deep religion
            val worship_array = object_main.getJSONArray("worship_place_visit")
            val worship_name: MutableList<String> = arrayListOf()
            val worship_id: MutableList<Int> = arrayListOf()
            for (i in 0 until worship_array.length()) {
                val worshiptemp = worship_array.getJSONObject(i)
                worship_name.add(worshiptemp["worship_place_visit"].toString())
                worship_id.add(worshiptemp["id"].toString().toInt())
            }

            if (religion.text!="Select") {
                if (caste.text != "Select") {
                    if(AgreeOnReligionSpinner.selectedItem != "Select" ) {
                        if(VisitingTempleSpinner.selectedItem != "Select") {
                            val selecteddeepreligion = AgreeOnReligionSpinner.selectedItem.toString()
                            val deeppos1 = deepreligion_name.indexOf(selecteddeepreligion)
                            val deepreligion_id_server = deepreligion_id.get(deeppos1)

                            val selectedworship = VisitingTempleSpinner.selectedItem.toString()
                            val worshippos1 = worship_name.indexOf(selectedworship)
                            val worship_id_server = worship_id.get(worshippos1)
                            viewModel1.religiosInfoSubmitLiveData(
                                body,
                                selected_religion.toString().replace(".0", "").toInt(),
                                selected_caste.toString().replace(".0", "").toInt(),
                                deepreligion_id_server,
                                worship_id_server,
                                PrefsHelper().getPref<String>("userKey").toInt(),
                                religion.text.toString().replace("\"",""),
                                caste.text.toString().replace("\"",""),
                                subCasteSpinner.text.trim().toString(),
                                selectedworship.replace("\"",""),
                                selecteddeepreligion.replace("\"","")
                            )
                        }else{
                            Toast.makeText(
                                this,
                                "Please select how often you visit your place of worship",
                                Toast.LENGTH_SHORT).show()
                        }
                    }
                    else{
                        Toast.makeText(
                            this,
                            "Please select how deeply religious you are",
                            Toast.LENGTH_SHORT).show()
                    }
                }else{
                    Toast.makeText(
                        this,
                        "Please select your caste",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }else{
                Toast.makeText(
                    this,
                    "Please select your religion",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun setupListerners() {
        viewModel.religiosInfoLiveData(null)

    }

    private fun setupObservers() {
        viewModel.religiousInfoResponseLiveData.observe(this, Observer {
            if (it != null) {
                if (it.message == "Success") {
                    jsonArray = gson.toJson(it.data)
                    val object_main1 = JSONObject(jsonArray)
                    val obj_main = object_main1.getJSONObject("user_choice")
                    if(obj_main.has("horoscope")) {
                        val full_path = obj_main["horoscope"].toString()
                        val pic_name: String? = full_path.substringAfterLast("/")
                        UploadHoroscopeEditText.text = Editable.Factory.getInstance().newEditable(pic_name)
                    }
                    deeply_religious()
                    visiting_temple()
                    if (obj_main.has("religion")) {
                        val religion = findViewById<Button>(R.id.religionSpinner)
                        religion.text = it.data.user_choice.religion_name.toString()
                        selected_religion = it.data.user_choice.religion
                        PrefsHelper().savePref("religion_id", it.data.user_choice.religion.toString().replace(".0", "").toInt())

                    }
                    if(it.data.user_choice.caste_name!=null) {
                        val state = findViewById<Button>(R.id.casteSpinner)
                        state.text = it.data.user_choice.caste_name.toString()
                        selected_caste = it.data.user_choice.caste
                        //to get religion data
                    }
                    if(it.data.user_choice.subCaste_text != null){
                        var subcaste = it.data.user_choice.subCaste_text.toString()
                        subCasteSpinner.text = Editable.Factory.getInstance().newEditable(subcaste)
                    }
                }
                else{
                    Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                }
            }
        })
        //observe API call status
        viewModel.religiousInfoApiCallStatus.observe(this, Observer {
            processStatus(it)
        })

    }

    private fun setupObserversCasteData() {
        viewModelReligion.CasteInfoResponseLiveData.observe(this, Observer {
            if (it != null) {
                if (it.message == "Success") {
                    val listOfCaste= ArrayList<MultiSelectModel>()
                    for (i in 0 until it.data.size){
                        listOfCaste.add(MultiSelectModel(it.data[i].id,it.data[i].caste));
                    }
                    val preselected = ArrayList<Int>()
                    if(selected_caste!=null){
                        val selected_countries = selected_caste.toString().toDouble().roundToInt()
                        preselected.add(selected_countries)
                    }
                    val multiSelectDialogCountry =  MultiSelectDialog()
                        .title("Caste") //setting title for dialog
                        .titleSize(25.toFloat())
                        .positiveText("Done")
                        .negativeText("Cancel")
                        .setMinSelectionLimit(0) //you can set minimum checkbox selection limit (Optional)
                        .setMaxSelectionLimit(1) //you can set maximum checkbox selection limit (Optional)
                        .multiSelectList(listOfCaste) // the multi select model list with ids and name
                        .preSelectIDsList(preselected)
                        .onSubmit(object : MultiSelectDialog.SubmitCallbackListener{
                            override fun onSelected(
                                p0: ArrayList<Int>,
                                p1: ArrayList<String>?,
                                p2: String?
                            ) {
                                for (i in 0 until p0.size) {
                                    if (p0[i] != 0) {
                                        PrefsHelper().savePref("caste_id", p0[i])
                                        val button =
                                            findViewById<Button>(R.id.casteSpinner)
                                        button.text = p1?.get(i) ?: "Select"
                                        selected_caste = p0[i]

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
        //observe API call status
        viewModelCaste.CasteInfoApiCallStatus.observe(this, Observer {
            processStatus(it)
        })
    }

    private fun setupObserversReligionData() {
        viewModelReligion.ReligionInfoResponseLiveData.observe(this, Observer {
            if (it != null) {
                if (it.message == "Success") {
                    val listOfReligion= ArrayList<MultiSelectModel>()
                    for (i in 0 until it.data.size){
                        listOfReligion.add(MultiSelectModel(it.data[i].id,it.data[i].religion));
                    }
                    val preselected = ArrayList<Int>()
                    if(selected_religion!=null){
                        val selected_countries = selected_religion.toString().toDouble().roundToInt()
                        preselected.add(selected_countries)
                    }
                    val multiSelectDialogCountry =  MultiSelectDialog()
                        .title("Religion") //setting title for dialog
                        .titleSize(25.toFloat())
                        .positiveText("Done")
                        .negativeText("Cancel")
                        .setMinSelectionLimit(0) //you can set minimum checkbox selection limit (Optional)
                        .setMaxSelectionLimit(1) //you can set maximum checkbox selection limit (Optional)
                        .multiSelectList(listOfReligion) // the multi select model list with ids and name
                        .preSelectIDsList(preselected)
                        .onSubmit(object : MultiSelectDialog.SubmitCallbackListener{
                            override fun onSelected(
                                p0: ArrayList<Int>,
                                p1: ArrayList<String>?,
                                p2: String?
                            ) {
                                for (i in 0 until p0.size) {
                                    if (p0[i] != 0) {
                                        PrefsHelper().savePref("religion_id", p0[i])
                                        val button =
                                            findViewById<Button>(R.id.religionSpinner)
                                        button.text = p1?.get(i) ?: "Select"
                                        val buttonStates =
                                            findViewById<Button>(R.id.casteSpinner)
                                        buttonStates.text = "Select"
                                        selected_religion = p0[i]
                                        selected_caste = null

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


        //observe API call status
        viewModelReligion.ReligionInfoApiCallStatus.observe(this, Observer {
            processStatus(it)
        })

    }






    private fun deeply_religious() {
        val object_main1 = JSONObject(jsonArray)
        val object_main = object_main1.getJSONObject("choice")
        val array = object_main.getJSONArray("deeply_religious")
        val list_deeply_religious: MutableList<String> = arrayListOf()
        val obj_main = object_main1.getJSONObject("user_choice")

// need to change it as 0
        if (obj_main.has("deeply_religious")) {
            val deep_array = object_main.getJSONArray("deeply_religious")
            val deepreligion_name: MutableList<String> = arrayListOf()
            val deepreligion_id: MutableList<Int> = arrayListOf()
            for (i in 0 until deep_array.length()) {
                val deeptemp = deep_array.getJSONObject(i)
                deepreligion_name.add(deeptemp["deeply_religious"].toString())
                deepreligion_id.add(deeptemp["id"].toString().toInt())
            }

            val selecteddeepreligion =
                obj_main["deeply_religious"].toString().replace(".0", "").toInt()
            val deeppos1 = deepreligion_id.indexOf(selecteddeepreligion)
            val deeply_religious_text = deepreligion_name.get(deeppos1)

            list_deeply_religious.add(deeply_religious_text)
            for (i in 0 until array.length()) {
                if ((obj_main["deeply_religious"].toString().replace(".0", "").toInt() < 1)) {
                    list_deeply_religious.add("Select")
                    for (i in 0 until array.length()) {
                        val obj = array.getJSONObject(i)
                        list_deeply_religious.add(obj.getString("deeply_religious"))

                    }
                } else {
                    val obj = array.getJSONObject(i)
                    if (obj.getString("deeply_religious") != deeply_religious_text) {
                        list_deeply_religious.add(obj.getString("deeply_religious"))
                    }
                }
            }

        } else {
            list_deeply_religious.add("Select")
            for (i in 0 until array.length()) {
                val obj = array.getJSONObject(i)
                list_deeply_religious.add(obj.getString("deeply_religious"))
            }


        }


        val adapter_deeply_religious = ArrayAdapter(
            this,
            R.layout.color_spinner_layout,
            list_deeply_religious
        )
        adapter_deeply_religious.setDropDownViewResource(R.layout.spinner_dropdown_layout)
        AgreeOnReligionSpinner.setAdapter(adapter_deeply_religious)


}


    private fun visiting_temple() {
        val object_main1 = JSONObject(jsonArray)
        val object_main = object_main1.getJSONObject("choice")
        val array = object_main.getJSONArray("worship_place_visit")
        val list_worship:MutableList<String> = arrayListOf()
        val obj_main = object_main1.getJSONObject("user_choice")

        if(obj_main.has("worship_place_visit")) {
            if ((obj_main["worship_place_visit"].toString().replace(".0", "").toInt() < 1)) {
                list_worship.add("Select")
                for (i in 0 until array.length()) {
                    val obj = array.getJSONObject(i)
                    list_worship.add(obj.getString("worship_place_visit"))
                }
            } else {
                val worship_array = object_main.getJSONArray("worship_place_visit")
                val worship_name: MutableList<String> = arrayListOf()
                val worship_id: MutableList<Int> = arrayListOf()
                for (i in 0 until worship_array.length()) {
                    val worshiptemp = worship_array.getJSONObject(i)
                    worship_name.add(worshiptemp["worship_place_visit"].toString())
                    worship_id.add(worshiptemp["id"].toString().toInt())
                }
                val selectedworship =
                    obj_main["worship_place_visit"].toString().replace(".0", "").toInt()
                val worshippos1 = worship_id.indexOf(selectedworship)
                val temple_visit_text = worship_name.get(worshippos1)
                list_worship.add(temple_visit_text)
                for (i in 0 until array.length()) {
                    val obj = array.getJSONObject(i)
                    if (obj.getString("worship_place_visit") != temple_visit_text) {
                        list_worship.add(obj.getString("worship_place_visit"))
                    }
                }
            }
        }

        else {
            list_worship.add("Select")
            for (i in 0 until array.length()) {
                val obj = array.getJSONObject(i)
                list_worship.add(obj.getString("worship_place_visit"))
            }
        }



        val adapter_worship = ArrayAdapter(
            this,
            R.layout.color_spinner_layout,
            list_worship
        )
        adapter_worship.setDropDownViewResource(R.layout.spinner_dropdown_layout)
        VisitingTempleSpinner.setAdapter(adapter_worship)
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
                    CommonUtils().showSnackbar(SaveReligiousInfobutton.rootView, "Please try again")
                    dismissDialog()
                }
                StatusType.LOADING_MORE -> {
                    // CommonUtils().showSnackbar(binding.root, "Loading more..")
                }
                StatusType.NO_NETWORK -> {
                    Toast.makeText(this, "Please check internet connection", Toast.LENGTH_SHORT).show()
                }
                StatusType.SESSION_EXPIRED -> {
                    CommonUtils().showSnackbar(SaveReligiousInfobutton.rootView, "session expired")
                }
            }
        }

    @Throws(IOException::class)
    private fun createImageFile(filename: String): File {
        // Create an image file name
//        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File = this?.getExternalFilesDir(Environment.DIRECTORY_PICTURES)!!
        return File.createTempFile(
            filename, /* prefix */
            ".pdf", /* suffix */
            storageDir /* directory */
        ).apply {
            // Save a file: path for use with ACTION_VIEW intents
            fileTemp = this
        }
    }

}



