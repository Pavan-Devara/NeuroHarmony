package com.neuro.neuroharmony.data.remote

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.os.Looper
import android.preference.PreferenceManager
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.facebook.FacebookSdk
import com.facebook.login.Login
import com.neuro.neuroharmony.BuildConfig
import com.neuro.neuroharmony.data.model.*
import com.neuro.neuroharmony.utils.PrefsHelper
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.neuro.neuroharmony.NeuroHarmonyApp
import com.neuro.neuroharmony.data.model.Push_notifications.ListofNotificationsResponse
import kotlinx.android.synthetic.main.activity_add_apartner.*
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

interface LoginNeuroHarmonyApiService {


    @POST("/user/login/")
    fun loginUser(@Body requestBodyMap: LoginRequest): Call<LoginResponse>

    @POST("/user/request-reset-password/")
    fun forgototprequest(@Body requestBodyMap: ResetPasswordRequestOtpRequest): Call<ResetPasswordRequestOtpResponse>

    @POST("/user/reset-password/")
    fun resetpassword(@Body requestBodyMap: ResetPasswordRequest): Call<ResetPasswordResponse>

    @POST("/user/logout/")
    fun logoutuser(@Header("Authorization") token: String = "Token " + PrefsHelper().getPref("Token") ): Call<UserLogoutResponse>

    @GET("/user/account-status-update/")
    fun accountstatusapiservice(@Header("Authorization") token: String = "Token " + PrefsHelper().getPref("Token")): Call<AccountStatusResponse>



    // sample for passing TOKEN in header
    /* @POST("GetMessageArticleVideoCounts")
     fun getMessgaeArticleVideoCounts(@Body AppName: AppName, @Header("Token") token: String = PrefsHelper().getPref("Token")): Call<ResponseFormat>*/


    /*
    * Service to call network Connection
    * */
    companion object {
        val instance: LoginNeuroHarmonyApiService by lazy {

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

                        //context?.getSharedPreferences("YOUR_PREFS", 0)?.edit()?.clear()?.commit();
                        PreferenceManager.getDefaultSharedPreferences(NeuroHarmonyApp.getCtx()).edit().clear().apply()

                        val intent =  Intent(
                            NeuroHarmonyApp.getCtx(),
                            com.neuro.neuroharmony.ui.login.Login::class.java
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
                .baseUrl(BuildConfig.BASE_URL2)
                .addConverterFactory(GsonConverterFactory.create(gson)).addCallAdapterFactory(
                    CoroutineCallAdapterFactory()
                )
                .build()
                .create(LoginNeuroHarmonyApiService::class.java)
        }

    }
}