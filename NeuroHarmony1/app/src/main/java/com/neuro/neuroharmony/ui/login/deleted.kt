package com.neuro.neuroharmony.ui.login
/*
import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.lifecycle.Observer
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.qwqer.partner.R
import com.qwqer.partner.ui.base.BaseActivity
import com.qwqer.partner.ui.base.ResourceStatus
import com.qwqer.partner.ui.base.StatusType
import com.qwqer.partner.ui.otpverificaiton.OTPVerificationActivity
import com.qwqer.partner.ui.signup.SignUpViewModel
import com.qwqer.partner.utils.CommonUtils
import com.qwqer.partner.utils.Constant
import com.qwqer.partner.utils.PermissionUtil
import com.qwqer.partner.utils.PrefsHelper
import eu.janmuller.android.simplecropimage.CropImage
import kotlinx.android.synthetic.main.activity_photo_upload.*
import kotlinx.android.synthetic.main.profile_photo_selection_bottom_sheet_layout.*
import java.io.*
import java.text.SimpleDateFormat
import java.util.*

const val MY_PERMISSIONS_REQUEST_CAMERA_ACCESS = 1000
const val MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 1001

const val REQUEST_CAMERA_CODE = 120
const val REQUEST_GALLERY_CODE = 121
const val REQUEST_CROP_CODE = 122

@RequiresApi(Build.VERSION_CODES.M)
class PhotoUploadActivity : BaseActivity() {

    private val TAG: String = "PhotoUploadActivity"

    private val viewModel: SignUpViewModel by viewModels()
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photo_upload)

        setupListeners()
        setupObservers()
    }

    /**
     * Listeners
     */
    private fun setupListeners() {
        photo_imagebutton_pick_photo.setOnClickListener {
            pickPhoto()
        }

        photo_button_signup.setOnClickListener {
            if (fileTemp != null) {
                val name = intent.getStringExtra(Constant.NAME)
                val phone = intent.getStringExtra(Constant.PHONE)
                val email = intent.getStringExtra(Constant.EMAIL)
                val password = intent.getStringExtra(Constant.PASSWORD)
                viewModel.signup(fileTemp!!, name, phone, email, password)
            } else {
                Log.e(TAG, "fileTemp Null")
            }
        }
    }

    /**
     * Livedata observers
     */
    private fun setupObservers() {
        viewModel.signUpResponseLiveData.observe(this, Observer {
            if (it.isSuccess == true) {
                PrefsHelper().savePref(Constant.TOKEN,it.token)
                PrefsHelper().savePref(Constant.PHONE,it.data?.userPhone)
                moveToOTPVerification()
            } else {
                CommonUtils().showSnackbar(photo_button_signup.rootView,getString(R.string.signup_failed))
            }
        })

        viewModel.signUpAPICallStatus.observe(this, Observer {
            processStatus(it)
        })
    }

    private fun moveToOTPVerification() {
        val intent = Intent(this, OTPVerificationActivity::class.java)
        startActivity(intent)
        finish()
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
                if(resource.message!!.isNotEmpty()){
                    CommonUtils().showSnackbar(photo_button_signup.rootView,resource.message)
                }else{
                    CommonUtils().showSnackbar(photo_button_signup.rootView,getString(R.string.signup_failed))
                }

                dismissDialog()

            }
            StatusType.LOADING_MORE -> {
                // CommonUtils().showSnackbar(binding.root, "Loading more..")
            }
            StatusType.NO_NETWORK -> {
                CommonUtils().showSnackbar(photo_button_signup.rootView,getString(R.string.no_network_msg))
            }
            StatusType.SESSION_EXPIRED -> {
                //CommonUtils().showSnackbar(login_button_login.rootView,"session expired")
            }
        }
    }

    //Profile photo pick codes -       ------    //////
    /**
     * Handle the edit profile image click event
     * 1. Capture image using camera
     * 2. Select image from gallery
     */
    private fun pickPhoto() {
        val view =
            layoutInflater.inflate(R.layout.profile_photo_selection_bottom_sheet_layout, null)
        val dialogBottom = BottomSheetDialog(this)
        dialogBottom.setContentView(view)
        if (dialogBottom.show() != null) {
            dialogBottom.show()
        }
        dialogBottom.photo_linear_from_camera.setOnClickListener { view ->

            if (!PermissionUtil.hasSelfPermission(this, Manifest.permission.CAMERA)) {
                requestPermissions(
                    arrayOf(
                        Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                    ), MY_PERMISSIONS_REQUEST_CAMERA_ACCESS
                )
            } else {
                if (!PermissionUtil.hasSelfPermission(this, Manifest.permission.CAMERA)) {
                    startCamera()
                } else {
                    requestPermissions(
                        arrayOf(
                            Manifest.permission.CAMERA,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE
                        ), MY_PERMISSIONS_REQUEST_CAMERA_ACCESS
                    )

                }
            }
            dialogBottom.dismiss()

        }
        dialogBottom.photo_linear_from_gallery.setOnClickListener { view ->

            if (!PermissionUtil.hasSelfPermission(
                    this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                )
            ) {
                requestPermissions(
                    arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE
                )
            } else {
                if (!PermissionUtil.hasSelfPermission(
                        this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                    )
                ) {
                    openGallery()
                } else {
                    requestPermissions(
                        arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                        MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE
                    )

                }
            }
            dialogBottom.dismiss()
        }
    }


    /**
     * Start camera intent
     */
    private fun startCamera() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            // Ensure that there's a camera activity to handle the intent
            takePictureIntent.resolveActivity(this?.packageManager)?.also {
                // Create the File where the photo should go
                val photoFile: File? = try {
                    createImageFile()
                } catch (ex: IOException) {
                    // Error occurred while creating the File
                    //...
                    null
                }
                // Continue only if the File was successfully created
                photoFile?.also {
                    val photoURI: Uri = FileProvider.getUriForFile(
                        this!!,
                        "com.qwqer.partner.fileprovider",
                        it
                    )
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                    takePictureIntent.putExtra("return-data", true)
                    this.startActivityForResult(takePictureIntent, REQUEST_CAMERA_CODE)
                }
            }
        }
    }

    /**
     * Open Gallery for selecting images
     */
    private fun openGallery() {
        val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        this.startActivityForResult(galleryIntent, REQUEST_GALLERY_CODE)

    }

    /**
     * Create external image file, this file will be passed to Camera for saving the captured image
     */
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

    /**
     * Handle the permission request response
     * @author Shareef | Aug 07 2019
     */
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            MY_PERMISSIONS_REQUEST_CAMERA_ACCESS -> {
                // If request is cancelled, the result arrays are empty.
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    // permission was granted, access camera

                    if (!PermissionUtil.hasSelfPermission(
                            this,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE
                        )
                    ) {
                        requestPermissions(
                            arrayOf(
                                Manifest.permission.CAMERA,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE
                            ), MY_PERMISSIONS_REQUEST_CAMERA_ACCESS
                        )
                    } else {
                        startCamera()
                    }
                } else {
                    // permission denied
                    Toast.makeText(
                        this,
                        "Camera access denied!, Cannot update profile image",
                        Toast.LENGTH_LONG
                    ).show()

                }
                return
            }

            MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE -> {

                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    // permission was granted, access external storage
                    openGallery()

                } else {
                    // permission denied
                    Toast.makeText(
                        this,
                        "External storage access denied!, Cannot update profile image",
                        Toast.LENGTH_LONG
                    ).show()

                }
                return
            }
            else -> {
                // Ignore all other requests.
            }
        }
    }

    /**
     * Listen for the results from the activities opened for results.
     * 1. Gallery - Get the selected image file
     * 2. Camera - Get the captured image and initiate croping
     * 3. Crop image - Get the cropped image
     */
    @SuppressLint("MissingSuperCall")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        dismissDialog()
        //Log.e("Profile", "Request code:$requestCode")
        if (resultCode != Activity.RESULT_OK) return
        if (requestCode == REQUEST_GALLERY_CODE) {
            if (data != null) {
                try {

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

                    runCropImage()

                } catch (e: Exception) {
                    //Log.e("Eror", "Error while creating temp file", e)
                }

            }

        } else if (requestCode == REQUEST_CAMERA_CODE) {

            if (!PermissionUtil.hasSelfPermission(this, Manifest.permission.CAMERA)) {
                requestPermissions(
                    arrayOf(
                        Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                    ), MY_PERMISSIONS_REQUEST_CAMERA_ACCESS
                )
            } else {
                runCropImage()
            }


        } else if (requestCode == REQUEST_CROP_CODE) {
            if (resultCode != AppCompatActivity.RESULT_CANCELED && data != null) {
                cameraBitmap = BitmapFactory.decodeFile(fileTemp?.path)
                //Log.e("upload", "${fileTemp!!}")

                if (fileTemp != null && fileTemp?.exists() == true) {
                    //Log.e("fileTemp", "${fileTemp!!}")
                    // viewModel.uploadImage(fileTemp!!)
                    photo_imageview_preview.setImageBitmap(cameraBitmap)

                } else {
                    //Log.e("### fileTemp", "called")
                }
            }
        }
    }

    /**
     * Crop the captured images using CropImage activity
     */
    private fun runCropImage() {

        var intent = Intent(this, CropImage::class.java)

        var filePath = fileTemp?.path
        intent.putExtra(CropImage.IMAGE_PATH, filePath)

        // allow CropImage activity to rescale image
        intent.putExtra(CropImage.SCALE, true)

        // if the aspect ratio is fixed to ratio 2/2
        intent.putExtra(CropImage.ASPECT_X, 190)
        intent.putExtra(CropImage.ASPECT_Y, 200)
        // start activity CropImage with certain request code and listen
        // for result
        this.startActivityForResult(intent, REQUEST_CROP_CODE)
    }

}*///