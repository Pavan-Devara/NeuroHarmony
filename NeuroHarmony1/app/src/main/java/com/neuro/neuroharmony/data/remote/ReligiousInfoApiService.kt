package com.neuro.neuroharmony.data.remote

import android.content.Intent
import android.os.Looper
import android.preference.PreferenceManager
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.neuro.neuroharmony.BuildConfig
import com.neuro.neuroharmony.data.model.ReligiousInfoResponse
import com.neuro.neuroharmony.utils.PrefsHelper
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.neuro.neuroharmony.NeuroHarmonyApp
import com.neuro.neuroharmony.data.model.BlockingList.BlockingListResponse
import com.neuro.neuroharmony.data.model.BlockingList.BlockingSubmitRequest
import com.neuro.neuroharmony.data.model.BlockingList.BlockingSubmitResponse
import com.neuro.neuroharmony.data.model.FAQs.FAQResponse
import com.neuro.neuroharmony.data.model.Social_profile.Educationinfo.EducationProfessionResponse
import com.neuro.neuroharmony.data.model.Social_profile.Educationinfo.EducationProfessionSubmitRequest
import com.neuro.neuroharmony.data.model.Social_profile.Educationinfo.EducationProfessionSubmitResponse
import com.neuro.neuroharmony.data.model.Social_profile.Familyinfo.FamilyInfoResponse
import com.neuro.neuroharmony.data.model.Social_profile.Lifestyleinfo.LifeStyleInfoSubmitRequest
import com.neuro.neuroharmony.data.model.Social_profile.Familyinfo.FamilyInfoSubmitRequest
import com.neuro.neuroharmony.data.model.Social_profile.Familyinfo.FamilyInfoSubmitResponse
import com.neuro.neuroharmony.data.model.Social_profile.Lifestyleinfo.LifestyleInfoResponse
import com.neuro.neuroharmony.data.model.Social_profile.Lifestyleinfo.LifestyleInfoSubmitResponse
import com.neuro.neuroharmony.data.model.Social_profile.PersonalInfo.PersonalInfoResponse
import com.neuro.neuroharmony.data.model.Social_profile.ReligiousInfo.CasteData.CasteDataResponse
import com.neuro.neuroharmony.data.model.Social_profile.ReligiousInfo.ReligionData.ReligionDataResponse
import com.neuro.neuroharmony.ui.login.Login

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import java.util.concurrent.TimeUnit

interface ReligiousInfoApiService {

    @GET("/user/personal-info_v1/")
    fun personalInfo(
        @Query("partner_key") partner_key: String?,
        @Header("Authorization") token: String = "Token " + PrefsHelper().getPref(
            "Token"
        )
    ): Call<PersonalInfoResponse>

    @GET("/user/blocked-users/")
    fun blockinglist(
        @Header("Authorization") token: String = "Token " + PrefsHelper().getPref(
            "Token"
        )
    ): Call<BlockingListResponse>


    @GET("/user/faq-data/")
    fun frequentlyAskedQuestions(
        @Header("Authorization") token: String = "Token " + PrefsHelper().getPref(
            "Token"
        )
    ): Call<FAQResponse>


    @GET("/user/religious-info_v1/")
    fun religiousInfo(
        @Query("partner_key") partner_key: String?,
        @Header("Authorization") token: String = "Token " + PrefsHelper().getPref(
            "Token"
        )
    ): Call<ReligiousInfoResponse>


    @GET("/user/religion-info_v2/")
    fun religionDataInfo(
        @Header("Authorization") token: String = "Token " + PrefsHelper().getPref(
            "Token"
        )
    ): Call<ReligionDataResponse>

    @GET("/user/religion-info_v2/")
    fun casteDataInfo(
        @Query("religion_id") religion_id:List<Int>,
        @Header("Authorization") token: String = "Token " + PrefsHelper().getPref(
            "Token"
        )
    ): Call<CasteDataResponse>


    @GET("/user/lifestyle-info/")
    fun lifestyleInfo(
        @Query("partner_key") partner_key: String?,
        @Header("Authorization") token: String = "Token " + PrefsHelper().getPref(
            "Token"
        )
    ): Call<LifestyleInfoResponse>

    @GET("/user/family-info/")
    fun familyInfo(
        @Query("partner_key") partner_key: String?,
        @Header("Authorization") token: String = "Token " + PrefsHelper().getPref(
            "Token"
        )
    ): Call<FamilyInfoResponse>

    @GET("/user/edu-prof-info/")
    fun educationprofession(
        @Query("partner_key") partner_key: String?,
        @Header("Authorization") token: String = "Token " + PrefsHelper().getPref(
            "Token"
        )
    ): Call<EducationProfessionResponse>

    @POST("/user/family-info/")
    fun familyInfoSubmit(
        @Body NeuroHarmonyApp: FamilyInfoSubmitRequest, @Header("Authorization") token: String = "Token " + PrefsHelper().getPref(
            "Token")
    ):Call<FamilyInfoSubmitResponse>

    @POST("/user/edu-prof-info/")
    fun EducationProfSubmit(
        @Body NeuroHarmonyApp: EducationProfessionSubmitRequest, @Header("Authorization") token: String = "Token " + PrefsHelper().getPref(
            "Token")
    ):Call<EducationProfessionSubmitResponse>

    @POST("/user/lifestyle-info/")
    fun lifestyleSubmitInfo(@Body NeuroHarmonyApp: LifeStyleInfoSubmitRequest,
                            @Header("Authorization") token: String = "Token " + PrefsHelper().getPref("Token")
    ): Call<LifestyleInfoSubmitResponse>

    @POST("/user/unblock/")
    fun BlockingListSubmit(@Body NeuroHarmonyApp: BlockingSubmitRequest,
                           @Header("Authorization") token: String = "Token " + PrefsHelper().getPref("Token")
    ): Call<BlockingSubmitResponse>


    companion object {
        val instance: ReligiousInfoApiService by lazy {

            val okHttpClientBuilder = OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)

            if (BuildConfig.DEBUG) {
                val logging = HttpLoggingInterceptor()
                logging.level = HttpLoggingInterceptor.Level.BODY
                val requestInterceptor = Interceptor { chain ->
                    val url = chain.request()
                        .url()
                        .newBuilder()
                        .build()
                    val request = chain.request()
                        .newBuilder()
                        .url(url)
                        .build()
                    val response = chain.proceed(request)
                    // todo deal with the issues the way you need to
                    if (response.code() == 401) {
                        PreferenceManager.getDefaultSharedPreferences(NeuroHarmonyApp.getCtx()).edit().clear().apply()
                        val intent =  Intent(
                            NeuroHarmonyApp.getCtx(),
                            Login::class.java
                        )
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        NeuroHarmonyApp.getCtx()?.let {
                            ContextCompat.startActivity(
                                it, intent, null
                            )
                        }
                        Looper.prepare()
                        Toast.makeText(NeuroHarmonyApp.getCtx(), "Session expired. Please login again to continue", Toast.LENGTH_LONG).show()
                        Looper.loop()
                        return@Interceptor response
                    }else{
                        return@Interceptor response
                    }
                }


                okHttpClientBuilder.addInterceptor(logging)
                okHttpClientBuilder.addInterceptor(requestInterceptor)

            }


            val okHttpClient = okHttpClientBuilder.build()

            val gson = GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
                .setLenient()
                .create()
            Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(BuildConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson)).addCallAdapterFactory(
                    CoroutineCallAdapterFactory()
                )
                .build()
                .create(ReligiousInfoApiService::class.java)
        }

    }
}