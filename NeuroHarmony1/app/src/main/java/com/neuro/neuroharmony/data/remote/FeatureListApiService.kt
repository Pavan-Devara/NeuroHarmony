package com.neuro.neuroharmony.data.remote

import android.content.Intent
import android.os.Looper
import android.preference.PreferenceManager
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.neuro.neuroharmony.BuildConfig
import com.neuro.neuroharmony.NeuroHarmonyApp
import com.neuro.neuroharmony.data.model.Feedback.FeaturesResponse
import com.neuro.neuroharmony.data.model.Feedback.SubmitFeedbackResponse
import com.neuro.neuroharmony.data.model.Feedback.SubmitFeedbackRequest
import com.neuro.neuroharmony.data.model.GenericData.GenericDataResponse
import com.neuro.neuroharmony.data.model.GenericData.GenericRequestWithMobileInfo
import com.neuro.neuroharmony.data.model.TermsAndPrivacyResponse
import com.neuro.neuroharmony.ui.login.Login
import com.neuro.neuroharmony.utils.PrefsHelper
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import java.util.concurrent.TimeUnit

interface FeatureListApiService {


    @GET("/user/feature-list/")
    fun getfeaturelist( @Header("Authorization") token: String = "Token " + PrefsHelper().getPref("Token")): Call<FeaturesResponse>

    @POST("/user/feedback/")
    fun submitfeedback(@Body NeuroHarmonyApp: SubmitFeedbackRequest, @Header("Authorization") token: String = "Token " + PrefsHelper().getPref("Token")): Call<SubmitFeedbackResponse>

    @GET("user/terms-privacy/")
    fun getterms( @Header("Authorization") token: String = "Token " + PrefsHelper().getPref("Token")): Call<TermsAndPrivacyResponse>

    @POST("user/generic-data/")
    fun genericdata(@Body NeuroHarmonyApp: GenericRequestWithMobileInfo, @Header("Authorization") token: String = "Token " + PrefsHelper().getPref("Token")): Call<GenericDataResponse>


    companion object {
        val instance: FeatureListApiService by lazy {

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
                .create(FeatureListApiService::class.java)
        }

    }
}