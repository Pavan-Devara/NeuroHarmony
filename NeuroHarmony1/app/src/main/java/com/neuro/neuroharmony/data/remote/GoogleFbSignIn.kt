package com.neuro.neuroharmony.data.remote

import android.content.Intent
import android.os.Looper
import android.preference.PreferenceManager
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.neuro.neuroharmony.BuildConfig
import com.neuro.neuroharmony.data.model.GoogleFbSignInRespone
import com.neuro.neuroharmony.data.model.GoogleFbSigninRequest
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.neuro.neuroharmony.NeuroHarmonyApp
import com.neuro.neuroharmony.ui.login.Login
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST
import java.util.concurrent.TimeUnit

interface GoogleFbSignIn {

    @POST("/user/social-login/")
    fun googlefbsignin(@Body requestBodyMap: GoogleFbSigninRequest): Call<GoogleFbSignInRespone>



    /*
   * Service to call network Connection
   * */
    companion object {
        val instance: GoogleFbSignIn by lazy {

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
                .baseUrl(BuildConfig.BASE_URL2)
                .addConverterFactory(GsonConverterFactory.create(gson)).addCallAdapterFactory(
                    CoroutineCallAdapterFactory()
                )
                .build()
                .create(GoogleFbSignIn::class.java)
        }

    }
}