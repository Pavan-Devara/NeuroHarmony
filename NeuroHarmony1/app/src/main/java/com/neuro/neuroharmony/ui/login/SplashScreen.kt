package com.neuro.neuroharmony.ui.login


import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.neuro.neuroharmony.utils.PrefsHelper
import android.R
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import android.preference.PreferenceManager
import com.neuro.neuroharmony.ui.login.AfilliateWorkFlow.AffiliateForms
import com.neuro.neuroharmony.ui.login.AfilliateWorkFlow.AffiliateHomePage
import com.neuro.neuroharmony.ui.login.PaymentFlow.PaymentPackagesScreen
import com.neuro.neuroharmony.ui.login.SocialProfile.EducationAndProfession
import com.neuro.neuroharmony.ui.login.SocialProfile.LifeStyleInfo
import com.neuro.neuroharmony.ui.login.SocialProfile.ReligiousInfoVersion2
import com.neuro.neuroharmony.ui.login.SocialProfile.FamilyInfo


class SplashScreen : AppCompatActivity() {

    private val SPLASH_SCREEN_TIME_OUT:Long = 400


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(com.neuro.neuroharmony.R.layout.activity_splash_screen)

        if (!isTaskRoot()
            && getIntent().hasCategory(Intent.CATEGORY_LAUNCHER)
            && getIntent().getAction() != null
            && getIntent().getAction().equals(Intent.ACTION_MAIN)) {
            finish();
            return;
        }



        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Create the NotificationChannel
            val name = getString(R.string.defaultMsisdnAlphaTag)
            val descriptionText = "Messages"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val mChannel = NotificationChannel("2342342325", name, importance)
            mChannel.description = descriptionText
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(mChannel)
        }




        Handler().postDelayed({

            //val preferences = getSharedPreferences(NeuroHarmonyApp.getInstance()._context!!.packageName, 0)
            // val value = preferences.contains("Account_Status")

            val prefs = PreferenceManager.getDefaultSharedPreferences(this)


            if(prefs.contains("Account_Status")) {
                if (prefs.contains("userType")){
                    if (PrefsHelper().getPref<Int>("userType")==3){
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

                            2 -> intent = Intent(this, SelectUserTypeScreen::class.java)
                            9 -> intent = Intent(this, PolicyAndTermsScreen::class.java)
                            3 -> intent = Intent(this, BioUpdateActivity::class.java)
                            4 -> intent = Intent(this, PaymentPackagesScreen::class.java)

                            12 -> intent = Intent(this, ReligiousInfoVersion2::class.java)
                            5 -> intent = Intent(this, FamilyInfo::class.java)


                            6 -> intent = Intent(this, EducationAndProfession::class.java)
                            7 -> intent = Intent(this, LifeStyleInfo::class.java)
                            8 -> intent = Intent(this, HomePage1::class.java)



                            else -> {
                                intent = Intent(this, Login::class.java)
                            }
                        }
                    }
                }

                else{
                    when (PrefsHelper().getPref<Int>("Account_Status")) {

                        2 -> intent = Intent(this, SelectUserTypeScreen::class.java)
                        9 -> intent = Intent(this, PolicyAndTermsScreen::class.java)
                        3 -> intent = Intent(this, BioUpdateActivity::class.java)
                        4 -> intent = Intent(this, PaymentPackagesScreen::class.java)

                        12 -> intent = Intent(this, ReligiousInfoVersion2::class.java)
                        5 -> intent = Intent(this, FamilyInfo::class.java)

                        6 -> intent = Intent(this, EducationAndProfession::class.java)
                        7 -> intent = Intent(this, LifeStyleInfo::class.java)
                        8 -> intent = Intent(this, HomePage1::class.java)



                        else -> {
                            intent = Intent(this, Login::class.java)
                        }
                    }
                }






            }
            else {
                intent = Intent(this, Login::class.java)
            }
            startActivity(intent)
            finish()


            /*if (PrefsHelper().getPref<String>("userKey")!=null) {
                intent = Intent(
                    this,
                    HomePage1::class.java
                )
                intent = Intent(this, Login::class.java)
                //Intent is used to switch from one activity to another.
            }else{
                intent = Intent(this, Login::class.java)
            }
            //invoke the SecondActivity.
                startActivity(intent)
                finish()*/
            //the current activity will get finished.
        }, SPLASH_SCREEN_TIME_OUT)

    }
}