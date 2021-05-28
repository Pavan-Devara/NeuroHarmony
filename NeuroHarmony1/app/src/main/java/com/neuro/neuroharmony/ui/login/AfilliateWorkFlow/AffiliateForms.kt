package com.neuro.neuroharmony.ui.login.AfilliateWorkFlow

import android.Manifest
import android.annotation.TargetApi
import android.app.Activity
import android.app.AlertDialog
import android.content.ContentValues
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.preference.PreferenceManager
import android.provider.MediaStore
import android.text.Editable
import android.util.Log
import android.view.View
import android.view.ViewTreeObserver
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.core.content.FileProvider
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.abdeveloper.library.MultiSelectDialog
import com.abdeveloper.library.MultiSelectModel
import com.neuro.neuroharmony.R
import com.neuro.neuroharmony.data.model.AffiliateWorkflow.AffiliateType
import com.neuro.neuroharmony.data.model.AffiliateWorkflow.GetAffiliateDetailsResponse
import com.neuro.neuroharmony.ui.login.*
import com.neuro.neuroharmony.ui.login.Regions.RegionsViewModel
import com.neuro.neuroharmony.utils.CommonUtils
import com.neuro.neuroharmony.utils.PrefsHelper
import kotlinx.android.synthetic.main.activity_affiliate_form.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.roundToInt

class AffiliateForms : BaseActivity(), AdapterView.OnItemSelectedListener {

    var flag = ""
    var PIC_UPLOAD = false

    private lateinit var viewModel1: UploadProfilePhotoViewModel
    private lateinit var viewModel: GetAffiliateDetailsViewModel
    private lateinit var viewModelRegion: RegionsViewModel


    var selected_country: Any? = null
    var selected_state: Any? = null
    var selected_city: Any? = null

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
        setContentView(R.layout.activity_affiliate_form)

        val mySPrefs = PreferenceManager.getDefaultSharedPreferences(this)
        val editor = mySPrefs.edit()
        editor.remove("country_id")
        editor.remove("state_id")
        editor.remove("city_id")
        editor.apply()

        viewModel = ViewModelProviders.of(this)[GetAffiliateDetailsViewModel::class.java]
        viewModel1 = ViewModelProviders.of(this)[UploadProfilePhotoViewModel::class.java]
        viewModelRegion = ViewModelProviders.of(this)[RegionsViewModel::class.java]
        setupListenersUploadPhoto()
        setupObserversUploadPhoto()
        viewModel.getaffiliatevm()
        setupObservers()
        setupObserversStates()
        setupObserversCities()

        affiliateform_country_spinner.setOnClickListener {
            viewModelRegion.countriesvm()
        }

        affiliateform_state_spinner.setOnClickListener {
            if(mySPrefs.contains("country_id")) {
                viewModelRegion.statesvm()
            }else{
                Toast.makeText(this, "Please select a country to continue", Toast.LENGTH_SHORT).show()
            }
        }

        affiliateform_city_spinner.setOnClickListener {
            if (mySPrefs.contains("state_id")) {
                viewModelRegion.citiesvm()
            }else{
                Toast.makeText(this, "Please select a state to continue", Toast.LENGTH_SHORT).show()
            }
        }
    }


    @TargetApi(Build.VERSION_CODES.M)
    @RequiresApi(Build.VERSION_CODES.M)
    private fun setupListenersUploadPhoto() {
        upload_profile_photo_affiliate?.setOnClickListener {
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
                CommonUtils().showSnackbar(upload_profile_photo_affiliate.rootView,"Please Try again")
                dismissDialog()



            }
            StatusType.LOADING_MORE -> {
                // CommonUtils().showSnackbar(binding.root, "Loading more..")
            }
            StatusType.NO_NETWORK -> {
                CommonUtils().showSnackbar(upload_profile_photo_affiliate.rootView,"Please check internet connection")
            }
            StatusType.SESSION_EXPIRED -> {
                //CommonUtils().showSnackbar(upload_profile_photo.rootView,"session expired")
            }
        }
    }


    @TargetApi(Build.VERSION_CODES.KITKAT)
    @RequiresApi(Build.VERSION_CODES.KITKAT)
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) { super.onActivityResult(requestCode, resultCode, data)
        // Result code is RESULT_OK only if the user selects an Image
        if (resultCode == Activity.RESULT_OK) when (requestCode) {
            GALLERY_REQUEST_CODE -> {

                if (data != null) {
                    try {
                        val selectedImage: Uri? = data.data
                        val photoFile: File? = try {
                            createImageFile()
                        } catch (ex: IOException) {
                            // Error occurred while creating the File
                            null
                        }

                        var inputStream = this?.contentResolver?.openInputStream(data.data!!)
                        var fileOutputStream = FileOutputStream(photoFile)
                        BioUpdateActivity.copyBytes(inputStream!!, fileOutputStream)
                        fileOutputStream.close()
                        inputStream.close()

                        uploadFile()

                        val filename = FilePath.getFileName(this, selectedImage)
                        editTextupload_pic_affiliate.setText(filename)

                    } catch (e: Exception) {
                        Log.e("Eror", "Error while creating temp file", e)
                    }

                }

            }
            IMAGE_CAPTURE_CODE ->{
                uploadFile()
                val filename = fileTemp?.name
                editTextupload_pic_affiliate.setText(filename)

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




    private  fun setupObservers(){

        viewModel.getAffiliateDetailsResponseLiveData.observe(this, Observer {
            if(it!=null){
                if( it.message=="Success"){
                    new_function2(it.data.choice.affiliateTypes)
                    main(it)
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
                                            findViewById<Button>(R.id.affiliateform_country_spinner)
                                        button.text = p1?.get(i) ?: "Select"
                                        val buttonStates =
                                            findViewById<Button>(R.id.affiliateform_state_spinner)
                                        buttonStates.text = "Select"
                                        val buttonCities =
                                            findViewById<Button>(R.id.affiliateform_city_spinner)
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
                                            findViewById<Button>(R.id.affiliateform_state_spinner)
                                        button.text = p1?.get(i) ?: "Select"
                                        val buttonCities =
                                            findViewById<Button>(R.id.affiliateform_city_spinner)
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
                                            findViewById<Button>(R.id.affiliateform_city_spinner)
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


    private fun main(itAffliate: GetAffiliateDetailsResponse?) {

        if (PrefsHelper().getPref<Int>("Account_Status") != 10) {
            continue_to_bank_details.setOnClickListener {
                val country = findViewById<Button>(R.id.affiliateform_country_spinner)
                val state = findViewById<Button>(R.id.affiliateform_state_spinner)
                val city = findViewById<Button>(R.id.affiliateform_city_spinner)

                if (affiliateform_edittext.text.trim().toString().isNotEmpty()) {
                    if (affiliateform_lastname_edittext.text.trim().toString().isNotEmpty()) {
                        if (country.text!="Select") {
                            if (state.text !="Select") {
                                if (city.text!="Select") {
                                    if (affiliateform_street_edittext.text.trim().toString().isNotEmpty()) {
                                        if (affiliateform_pin_edittext.text.trim().toString().isNotEmpty()) {
                                            if (PIC_UPLOAD == true) {
                                                if (enroll_spinner.selectedItemPosition != 0) {

                                                    val intent = Intent(
                                                        this,
                                                        BankDetailsAffiliate::class.java
                                                    )
                                                    intent.putExtra(
                                                        "value_spinner",
                                                        enroll_spinner.selectedItemId.toString()
                                                    )
                                                    intent.putExtra(
                                                        "first_name_affiliate",
                                                        affiliateform_edittext.text.trim().toString()
                                                    )
                                                    intent.putExtra(
                                                        "last_name_affiliate",
                                                        affiliateform_lastname_edittext.text.trim().toString()
                                                    )
                                                    intent.putExtra(
                                                        "street_affiliate",
                                                        affiliateform_street_edittext.text.trim().toString()
                                                    )
                                                    intent.putExtra(
                                                        "city_affiliate",
                                                        city.text.toString()
                                                    )
                                                    intent.putExtra(
                                                        "pin_affiliate",
                                                        affiliateform_pin_edittext.text.trim().toString()
                                                    )
                                                    intent.putExtra(
                                                        "state_affiliate",
                                                        state.text.toString()
                                                    )
                                                    intent.putExtra(
                                                        "country_affiliate",
                                                        country.text.toString()
                                                    )

                                                    intent.putExtra(
                                                        "country_id",
                                                        selected_country.toString().toDouble().roundToInt()
                                                    )

                                                    intent.putExtra(
                                                        "state_id",
                                                        selected_state.toString().toDouble().roundToInt()
                                                    )

                                                    intent.putExtra(
                                                        "city_id",
                                                        selected_city.toString().toDouble().roundToInt()
                                                    )

                                                    startActivity(intent)

                                                } else {
                                                    Toast.makeText(
                                                        this,
                                                        "Please select how you would like to enroll",
                                                        Toast.LENGTH_SHORT
                                                    ).show()
                                                    //select spinner
                                                }

                                            } else{
                                                Toast.makeText(
                                                    this,
                                                    "Please select a profile picture",
                                                    Toast.LENGTH_SHORT
                                                ).show()
                                            }
                                            } else {
                                                Toast.makeText(
                                                    this,
                                                    "PIN cannot be empty",
                                                    Toast.LENGTH_SHORT
                                                ).show()
                                                //pin
                                            }


                                    } else {
                                        Toast.makeText(
                                            this,
                                            "Street cannot be empty",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                        //street
                                    }
                                } else {
                                    Toast.makeText(
                                        this,
                                        "Please select your city",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    //city
                                }
                            } else {
                                Toast.makeText(
                                    this,
                                    "Please select your state",
                                    Toast.LENGTH_SHORT
                                ).show()
                                //state
                            }
                        } else {
                            Toast.makeText(
                                this,
                                "Please select your country",
                                Toast.LENGTH_SHORT
                            ).show()
                            //country
                        }

                    } else {
                        Toast.makeText(
                            this,
                            "Last name cannot be empty",
                            Toast.LENGTH_SHORT
                        ).show()
                        //lastname
                    }

                } else {
                    Toast.makeText(
                        this,
                        "First name cannot be empty",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                    //name
                }
            }
        } else {

            val country = findViewById<Button>(R.id.affiliateform_country_spinner)
            val state = findViewById<Button>(R.id.affiliateform_state_spinner)
            val city = findViewById<Button>(R.id.affiliateform_city_spinner)

            if (itAffliate != null) {
                enroll_spinner.setSelection(itAffliate.data.userChoice.affiliateType)
                enroll_spinner.setEnabled(false)
                enroll_spinner.getViewTreeObserver()
                    .addOnGlobalLayoutListener(ViewTreeObserver.OnGlobalLayoutListener {
                        (enroll_spinner.getSelectedView() as TextView).setTextColor(
                            Color.parseColor("#555555")
                        )
                    })
                if (itAffliate.data.userChoice.ProfilePic != null) {
                    PIC_UPLOAD = true
                    val full_path = itAffliate.data.userChoice.ProfilePic
                    val pic_name: String? = full_path.substringAfterLast("/")
                    editTextupload_pic_affiliate.text =
                        Editable.Factory.getInstance().newEditable(pic_name)
                }
                if (itAffliate.data.userChoice.countryData!= null) {
                    selected_country = itAffliate.data.userChoice.countryData.id
                    PrefsHelper().savePref("country_id", selected_country)
                    selected_state = itAffliate.data.userChoice.stateData.id
                    PrefsHelper().savePref("state_id", selected_state)
                    selected_city = itAffliate.data.userChoice.cityData.id
                    country.text = itAffliate.data.userChoice.countryData.name
                    state.text = itAffliate.data.userChoice.stateData.name
                    city.text = itAffliate.data.userChoice.cityData.name
                }

                affiliateform_edittext.setText(itAffliate.data.userChoice.firstName)
                affiliateform_lastname_edittext.setText(itAffliate.data.userChoice.lastName)
                affiliateform_pin_edittext.setText(itAffliate.data.userChoice.pin)
                affiliateform_street_edittext.setText(itAffliate.data.userChoice.street)

                continue_to_bank_details.setOnClickListener {
                    if (affiliateform_edittext.text.trim().toString().isNotEmpty()) {
                        if (affiliateform_lastname_edittext.text.trim().toString().isNotEmpty()) {
                            if (country.text!="Select") {
                                if (state.text !="Select") {
                                    if (city.text!="Select") {
                                        if (affiliateform_street_edittext.text.trim().toString().isNotEmpty()) {
                                            if (affiliateform_pin_edittext.text.trim().toString().isNotEmpty()) {
                                                if (enroll_spinner.selectedItemPosition != 0) {

                                                    val intent = Intent(
                                                        this,
                                                        BankDetailsAffiliate::class.java
                                                    )
                                                    intent.putExtra(
                                                        "value_spinner",
                                                        enroll_spinner.selectedItemId.toString()
                                                    )
                                                    intent.putExtra(
                                                        "first_name_affiliate",
                                                        affiliateform_edittext.text.trim().toString()
                                                    )
                                                    intent.putExtra(
                                                        "last_name_affiliate",
                                                        affiliateform_lastname_edittext.text.trim().toString()
                                                    )
                                                    intent.putExtra(
                                                        "street_affiliate",
                                                        affiliateform_street_edittext.text.trim().toString()
                                                    )
                                                    intent.putExtra(
                                                        "city_affiliate",
                                                        city.text.toString()
                                                    )
                                                    intent.putExtra(
                                                        "pin_affiliate",
                                                        affiliateform_pin_edittext.text.trim().toString()
                                                    )
                                                    intent.putExtra(
                                                        "state_affiliate",
                                                        state.text.toString()
                                                    )

                                                    intent.putExtra(
                                                        "country_affiliate",
                                                        country.text.toString()
                                                    )

                                                    intent.putExtra(
                                                        "country_id",
                                                        selected_country.toString().toDouble().roundToInt()
                                                    )

                                                    intent.putExtra(
                                                        "state_id",
                                                        selected_state.toString().toDouble().roundToInt()
                                                    )

                                                    intent.putExtra(
                                                        "city_id",
                                                        selected_city.toString().toDouble().roundToInt()
                                                    )
                                                    Log.d("city_id", selected_country.toString())
                                                    Log.d("state_id", selected_state.toString())
                                                    Log.d("country_id", selected_city.toString())

                                                    startActivity(intent)

                                                } else {
                                                    Toast.makeText(
                                                        this,
                                                        "Please select how you would like to enroll",
                                                        Toast.LENGTH_SHORT
                                                    ).show()
                                                    //select spinner
                                                }


                                            } else {
                                                Toast.makeText(
                                                    this,
                                                    "PIN cannot be empty",
                                                    Toast.LENGTH_SHORT
                                                ).show()
                                                //pin
                                            }


                                        } else {
                                            Toast.makeText(
                                                this,
                                                "Street cannot be empty",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                            //street
                                        }
                                    } else {
                                        Toast.makeText(
                                            this,
                                            "Please select your city",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                        //city
                                    }
                                } else {
                                    Toast.makeText(
                                        this,
                                        "Please select your state",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    //state
                                }
                            } else {
                                Toast.makeText(
                                    this,
                                    "Please select your country",
                                    Toast.LENGTH_SHORT
                                ).show()
                                //country
                            }

                        } else {
                            Toast.makeText(
                                this,
                                "Last name cannot be empty",
                                Toast.LENGTH_SHORT
                            ).show()
                            //lastname
                        }

                    } else {
                        Toast.makeText(
                            this,
                            "First name cannot be empty",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                        //name
                    }
                }
            }
        }
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

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

    }

    override fun onNothingSelected(parent: AdapterView<*>?) {

    }

    private fun new_function2(full_list_edu2: List<AffiliateType>) {
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
        enroll_spinner?.setAdapter(adapter2)
    }


    override fun onBackPressed() {
        val intent = intent
        if (intent != null && intent.getExtras()!= null && intent.getExtras()!!.containsKey("edit_affiliate")){
            super.onBackPressed()
        }else{

        }

    }

}

