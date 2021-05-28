package com.neuro.neuroharmony.data.remote

import android.content.Intent
import android.os.Looper
import android.preference.PreferenceManager
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.neuro.neuroharmony.BuildConfig
import com.neuro.neuroharmony.data.model.*
import com.neuro.neuroharmony.utils.PrefsHelper
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.neuro.neuroharmony.NeuroHarmonyApp
import com.neuro.neuroharmony.data.model.ResetTest.ResetTestRequest
import com.neuro.neuroharmony.data.model.Social_profile.Lifestyleinfo.LifeStyleInfoSubmitRequest
import com.neuro.neuroharmony.data.model.Social_profile.Lifestyleinfo.LifestyleInfoSubmitResponse
import com.neuro.neuroharmony.ui.login.Login
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import java.util.concurrent.TimeUnit


interface TestNeuroHarmonyApiService {

    @POST("/test/cb-questions-v1/")
    fun corebeliefapiinterface(@Body NeuroHarmonyApp: TestsRequestBody, @Header("Authorization") token: String = "Token " + PrefsHelper().getPref("Token")): Call<CorebeliefFirstTwoSetsResponse>

    @POST("/test/nt-questions-v1/")
    fun neurotestquestions(@Body NeuroHarmonyApp: TestsRequestBody, @Header("Authorization") token: String = "Token " + PrefsHelper().getPref("Token")): Call<NeuroTestQuestionsResponse>

    @POST("/test/dt-questions-v1/")
    fun desirequestions(@Body NeuroHarmonyApp: TestsRequestBody, @Header("Authorization") token: String = "Token " + PrefsHelper().getPref("Token")): Call<DesireTestQuestionsResponse>

    @POST("/test/answer-v1/")
    fun submitanswer(@Body NeuroHarmonyApp: SubmitAnswerRequest, @Header("Authorization") token: String = "Token " + PrefsHelper().getPref("Token")): Call<SubmitAnswerResponse>

    @POST("/test/cb-final-questions-v1/")
    fun cbfinalsubmitanswer(@Body NeuroHarmonyApp: CoreBeliefFinalTwoQuestionsRequest, @Header("Authorization") token: String = "Token " + PrefsHelper().getPref("Token")): Call<CoreBeliefFinalTwoQuestionsResponse>

    @POST("/test/score/")
    fun neurodesirescore(@Body NeuroHarmonyApp: ScoreNeuroDesireRequest, @Header("Authorization") token: String = "Token " + PrefsHelper().getPref("Token")): Call<ScoreNeuroDesireResponse>

    @POST("/test/baseline-test/")
    fun baselineatest(@Body NeuroHarmonyApp: BaselineRequest, @Header("Authorization") token: String = "Token " + PrefsHelper().getPref("Token")): Call<BaselineResponse>

    @POST("test/reset_question_set/")
    fun resettest(@Body NeuroHarmonyApp: ResetTestRequest, @Header("Authorization") token: String = "Token " + PrefsHelper().getPref("Token")): Call<LifestyleInfoSubmitResponse>








    // sample for passing TOKEN in header
    /* @POST("GetMessageArticleVideoCounts")
     fun getMessgaeArticleVideoCounts(@Body AppName: AppName, @Header("Token") token: String = PrefsHelper().getPref("Token")): Call<ResponseFormat>*/


    /*
    * Service to call network Connection
    * */
    companion object {
        val instance: TestNeuroHarmonyApiService by lazy {

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
                .baseUrl(BuildConfig.BASE_URL3)
                .addConverterFactory(GsonConverterFactory.create(gson)).addCallAdapterFactory(
                    CoroutineCallAdapterFactory()
                )
                .build()
                .create(TestNeuroHarmonyApiService::class.java)
        }

    }
}