package com.neuro.neuroharmony.ui.login


/*import com.google.firebase.messaging.FirebaseMessagingService*/

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.telephony.TelephonyManager
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.facebook.*
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.neuro.neuroharmony.NeuroHarmonyApp.Companion.context
import com.neuro.neuroharmony.R
import com.neuro.neuroharmony.ui.login.AfilliateWorkFlow.AffiliateForms
import com.neuro.neuroharmony.ui.login.AfilliateWorkFlow.AffiliateHomePage
import com.neuro.neuroharmony.ui.login.LoginDirectory.GoogleFbSignInViewModel
import com.neuro.neuroharmony.ui.login.PaymentFlow.PaymentPackagesScreen
import com.neuro.neuroharmony.ui.login.SocialProfile.EducationAndProfession
import com.neuro.neuroharmony.ui.login.SocialProfile.FamilyInfo
import com.neuro.neuroharmony.ui.login.SocialProfile.LifeStyleInfo
import com.neuro.neuroharmony.ui.login.SocialProfile.ReligiousInfoVersion2
import com.neuro.neuroharmony.utils.CommonUtils
import com.neuro.neuroharmony.utils.PrefsHelper
import kotlinx.android.synthetic.main.activity_login.*
import org.json.JSONObject
import java.util.*
import kotlin.math.roundToInt


const val RC_SIGN_IN = 123
const val FB_SIGN_IN = 125
val TAG = Login::class.qualifiedName
//8820012946
//Admin@123

class Login :BaseActivity() {

    companion object{
        val FACEBOOK = 3
        val BASIC = 2
        val GOOGLE = 1
    }
    private var mIshowPass = false
    private lateinit var auth: FirebaseAuth
    private lateinit var viewModel: LoginViewModel
    private lateinit var viewModelgooglesignin: GoogleFbSignInViewModel
    private val EMAIL = "email"
    val callbackManager = CallbackManager.Factory.create();


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


        val present_country = getDetectedCountry(this, "IN")
//        val locale: String = this?.resources?.configuration?.locale!!.country
        var locale = present_country
        locale = locale.toUpperCase(Locale.getDefault())
        country_code_picker_login.setDefaultCountryUsingNameCode(locale)
        country_code_picker_login.resetToDefaultCountry()
        Log.d("country", locale)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Create channel to show notifications.
            val channelId = getString(R.string.default_notification_channel_id)
            val channelName = getString(R.string.default_notification_channel_name)
            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager?.createNotificationChannel(
                NotificationChannel(channelId,
                channelName, NotificationManager.IMPORTANCE_LOW)
            )
        }


        PrefsHelper().savePref("NeuroBaselineValue", 0)
        PrefsHelper().savePref("DesireBaselineValue", 0)
        PrefsHelper().savePref("CoreBaselineValue", 0)
        ivShowHidePass2.setOnClickListener {
            mIshowPass = !mIshowPass
            showPassword(mIshowPass)
        }
        showPassword(mIshowPass)

        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()



        LoginManager.getInstance().registerCallback(callbackManager,
            object : FacebookCallback<LoginResult?> {
                override fun onSuccess(loginResult: LoginResult?) { // App code

                    handleFacebookAccessToken(loginResult?.accessToken)
                }

                override fun onCancel() { // App code
                    Toast.makeText(this@Login, "Login Failed", Toast.LENGTH_SHORT).show()
                }

                override fun onError(exception: FacebookException) { // App code
                    Log.d("exception", exception.toString())
                    if (exception is FacebookAuthorizationException) {
                        if (AccessToken.getCurrentAccessToken() != null) {
                            LoginManager.getInstance().logOut();
                        }
                    }
                    Toast.makeText(this@Login, "Please try again", Toast.LENGTH_SHORT).show()
                }
        });

        fb.setOnClickListener {
            LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile", "email"));
        }


        viewModelgooglesignin = ViewModelProviders.of(this)[GoogleFbSignInViewModel::class.java]

        setupObserversGoogleFbSignin()



        // Configure sign-in to request the user's ID, email address, and basic
// profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        // Configure sign-in to request the user's ID, email address, and basic
// profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        val gso =
            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestId()
                .requestEmail()
                .build()


        // Build a GoogleSignInClient with the options specified by gso.
        GoogleSignIn.getClient(this, gso).signOut()
        val mGoogleSignInClient = GoogleSignIn.getClient(this, gso);


        //google_sign_in_button.setSize(SignInButton.SIZE_STANDARD)
        //setGooglePlusButtonText(google_sign_in_button, "Sign in with google");

        viewModel = ViewModelProviders.of(this)[LoginViewModel::class.java]


        setupObservers()

        google_sign_in_button.setOnClickListener{
            val signInIntent = mGoogleSignInClient.signInIntent
            startActivityForResult(signInIntent, RC_SIGN_IN)
        }

        new_user.setOnClickListener {
            val intent= Intent(this, MainActivity::class.java )
            startActivity(intent)
        }
        forgot_password.setOnClickListener{
            val intent=Intent(this, Mobile_verification::class.java)
            intent.putExtra("zero", "zero")
            startActivity(intent)
        }
        button_sign_in_login.setOnClickListener {

            val phone = edit_username_login.text.trim()

            if (Validator.isValidDigit(phone)){

                viewModel.login( 2,  "", edit_pwd_login.text.trim().toString(),edit_username_login.text.trim().toString(),country_code_picker_login.selectedCountryCodeAsInt)

            }
            else{

                Toast.makeText(this, "Please enter a valid mobile number", Toast.LENGTH_LONG).show()
            }

        }
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

    override fun onBackPressed() {
        val  a =  Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a)
        finish()
    }


    private fun handleFacebookAccessToken(token: AccessToken?) {
        Log.d(TAG, "handleFacebookAccessToken:${token?.token}")

        val credential = token?.token?.let { FacebookAuthProvider.getCredential(it) }
        credential?.let {
            auth.signInWithCredential(it)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "signInWithCredential:success")
                        val user = auth.currentUser
                        val email = user?.email
                        val id_token = user?.uid
                        Toast.makeText(baseContext, "Login Successful",
                            Toast.LENGTH_SHORT).show()
                        PrefsHelper().savePref("gmail", email)
                        PrefsHelper().savePref("fb_clientID", id_token)
                        PrefsHelper().savePref("signin_method", FACEBOOK)
                        if (email!=null) {
                            id_token?.let { it1 ->
                                email?.let { it2 ->
                                    viewModelgooglesignin.googlefbsignin(
                                        FACEBOOK, it1,
                                        it2
                                    )
                                }
                            }
                        }else{
                            Toast.makeText(baseContext, "No valid email",
                                Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "signInWithCredential:failure", task.exception)
                        Toast.makeText(baseContext, "Login failed",
                            Toast.LENGTH_SHORT).show()
                    }

                    // ...
                }
        }
    }

    /**
     * Write all LiveData observers in this method
     */
    private  fun setupObservers(){
        viewModel.loginResponseLiveData.observe(this, Observer {
            if(it!=null){
                if( it.message=="Success"){

                    var user_type = 0
                    if (it.data.userType is String){
                        user_type=0
                    }else{
                        user_type = (it.data.userType as Double).roundToInt()
                    }

                    PrefsHelper().savePref("Account_Status",it.data.accountStatus)
                    PrefsHelper().savePref("Reference",it.data.accountStatus)

                    PrefsHelper().savePref("user_pic", it.data.profilePic)
                    PrefsHelper().savePref("user_name", it.data.firstName)
                    PrefsHelper().savePref("last_name", it.data.lastName)
                    PrefsHelper().savePref("email", it.data.email)
                    PrefsHelper().savePref("Gmail", it.data.email)
                    PrefsHelper().savePref("Mobile", it.data.mobileNumber)
                    PrefsHelper().savePref("Token",it.token)
                    PrefsHelper().savePref("userKey", it.data.userKey.toString())
                    PrefsHelper().savePref("userType",user_type)
                    PrefsHelper().savePref("NeuroBaselineValue", it.data.baseline.neuro)
                    PrefsHelper().savePref("DesireBaselineValue", it.data.baseline.desire)
                    PrefsHelper().savePref("CoreBaselineValue", it.data.baseline.coreBelief)



                    if(user_type==3){
                        when (PrefsHelper().getPref<Int>("Account_Status")) {

                            10 -> intent = Intent(this, AffiliateHomePage::class.java)
                            3 -> intent = Intent(this, AffiliateForms::class.java)


                            else -> {
                                intent = Intent(this, Login::class.java)
                            }
                        }

                    }
                    else{


                        when (PrefsHelper().getPref<Int>("Account_Status")) {
                            1 -> intent = Intent(this, MobileEmailScreen::class.java)
                            2 -> intent = Intent(this, SelectUserTypeScreen::class.java)
                            3 -> intent = Intent(this, BioUpdateActivity::class.java)
                            4 -> intent = Intent(this, PaymentPackagesScreen::class.java)

                            5 -> intent = Intent(this, FamilyInfo::class.java)

                            12 -> intent = Intent(this, ReligiousInfoVersion2::class.java)

                            6 -> intent = Intent(this, EducationAndProfession::class.java)
                            7 -> intent = Intent(this, LifeStyleInfo::class.java)
                            8 -> intent = Intent(this, HomePage1::class.java)
                            9 -> intent = Intent(this, PolicyAndTermsScreen::class.java)
                            else -> {
                                intent = Intent(this, Login::class.java)
                            }
                        }


                    }
                    startActivity(intent)


                    // Success login.  Add the success scenario here ex: Move to next screen
                }
                else{
                    Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                }
            }
        })



        //observe API call status
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
                CommonUtils().showSnackbar(button_sign_in_login.rootView,"Login failed")
                dismissDialog()
            }
            StatusType.LOADING_MORE -> {
                CommonUtils().showSnackbar(button_sign_in_login.rootView, "Loading more..")
            }
            StatusType.NO_NETWORK -> {
                Toast.makeText(this, "Please check internet connection", Toast.LENGTH_SHORT).show()
            }
            StatusType.SESSION_EXPIRED -> {
                //CommonUtils().showSnackbar(login_button_login.rootView,"session expired")
            }
        }
    }

    private fun processStatusGoogleFb(resource: ResourceStatus) {

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
                CommonUtils().showSnackbar(google_sign_in_button.rootView,"Login failed")
                dismissDialog()
            }
            StatusType.LOADING_MORE -> {

                CommonUtils().showSnackbar(google_sign_in_button.rootView, "Loading more..")

            }
            StatusType.NO_NETWORK -> {
                Toast.makeText(this, "Please check internet connection", Toast.LENGTH_SHORT).show()
            }
            StatusType.SESSION_EXPIRED -> {
                //CommonUtils().showSnackbar(google_sign_in_button.rootView,"session expired")
            }
        }
    }






    //Google-signin Code
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) { // The Task returned from this call is always completed, no need to attach
// a listener.
            val task =
                GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }else{
            callbackManager.onActivityResult(requestCode, resultCode, data)
        }
    }
    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account =
                completedTask.getResult(ApiException::class.java)
            val idToken = account?.id;
            val idemail = account?.email

            if (account != null){
                PrefsHelper().savePref("gmail", idemail)
                PrefsHelper().savePref("gmail_clientID", idToken)
                PrefsHelper().savePref("signin_method", GOOGLE)
                idToken?.let { idemail?.let { it1 ->
                    viewModelgooglesignin.googlefbsignin(
                        GOOGLE, it,
                        it1
                    )
                } }
            }

        } catch (e: ApiException) { // The ApiException status code indicates the detailed failure reason.
// Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w(TAG, "signInResult:failed code=" + e.statusCode)
        }
    }

    private fun handleSignInResultFacebook(jsonObject: JSONObject?) {

        Toast.makeText(this, jsonObject?.getString("email"), Toast.LENGTH_SHORT).show()
    }


    private fun setupObserversGoogleFbSignin() {
        viewModelgooglesignin.googlefbloginResponseLiveData.observe(this, Observer {
            if (it!=null){
                if (it.data.social_login_status == 1){
                    if (it.message == "Success") {
                        var user_type = 0
                        if (it.data.user_type is String){
                            user_type=0
                        }else{
                            user_type = (it.data.user_type as Double).roundToInt()
                        }
                        PrefsHelper().savePref("Account_Status",it.data.account_status)
                        PrefsHelper().savePref("user_pic", it.data.profile_pic)
                        PrefsHelper().savePref("user_name", it.data.first_name)
                        PrefsHelper().savePref("email", it.data.email)
                        PrefsHelper().savePref("Mobile", it.data.mobile_number)
                        PrefsHelper().savePref("Token", it.token)
                        PrefsHelper().savePref("userType",user_type)
                        PrefsHelper().savePref("userKey", it.data.user_key.toString())
                        PrefsHelper().savePref("NeuroBaselineValue", it.data.baseline.neuro)
                        PrefsHelper().savePref("DesireBaselineValue", it.data.baseline.desire)
                        PrefsHelper().savePref("CoreBaselineValue", it.data.baseline.coreBelief)
                        var intent =  Intent(this, HomePage1::class.java)

                        if(user_type==3){
                            when (it.data.account_status) {

                                10 -> intent = Intent(this, AffiliateHomePage::class.java)
                                3 -> intent = Intent(this, AffiliateForms::class.java)


                                else -> {
                                    intent = Intent(this, Login::class.java)
                                }
                            }

                        }
                        else {
                            when (PrefsHelper().getPref<Int>("Account_Status")) {
                                1 -> {
                                    intent = Intent(this, MobileEmailScreen::class.java)
                                    intent.putExtra("password", "")
                                    PrefsHelper().savePref("signin", true)
                                }
                                2 -> intent = Intent(this, SelectUserTypeScreen::class.java)
                                3 -> intent = Intent(this, BioUpdateActivity::class.java)
                                12 -> intent = Intent(this, ReligiousInfoVersion2::class.java)
                                4 -> intent = Intent(this, PaymentPackagesScreen::class.java)
                                5 -> intent = Intent(this, FamilyInfo::class.java)
                                6 -> intent = Intent(this, EducationAndProfession::class.java)
                                7 -> intent = Intent(this, LifeStyleInfo::class.java)
                                8 -> intent = Intent(this, HomePage1::class.java)
                                9 -> intent = Intent(this, PolicyAndTermsScreen::class.java)
                                else -> {
                                    intent = Intent(this, Login::class.java)
                                }
                            }
                        }
                        startActivity(intent)
                    }
                }else{
                    PrefsHelper().savePref("signin", true)
                    val intent =  Intent(this, MobileEmailScreen::class.java)
                    intent.putExtra("password", "")
                    startActivity(intent)
                }
            }
        })
        viewModelgooglesignin.googlefbloginAPICallStatus.observe(this, Observer {
            processStatusGoogleFb(it)
        })

    }



    private fun showPassword(isShow: Boolean){
        if(isShow) {
            edit_pwd_login.transformationMethod = HideReturnsTransformationMethod.getInstance()
            ivShowHidePass2.setImageResource(R.drawable.visibility_off_black_24dp)

        } else {
            edit_pwd_login.transformationMethod = PasswordTransformationMethod.getInstance()
            ivShowHidePass2.setImageResource(R.drawable.password_visibility_24dp)

        }
        edit_pwd_login.setSelection(edit_pwd_login.text.toString().length)
    }




}
