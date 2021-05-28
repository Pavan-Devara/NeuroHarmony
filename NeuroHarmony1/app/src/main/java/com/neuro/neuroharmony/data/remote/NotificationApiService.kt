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
import com.neuro.neuroharmony.data.model.CompleteMatch.*
import com.neuro.neuroharmony.ui.login.Login
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import java.util.concurrent.TimeUnit

interface NotificationApiService {

    @POST("/interest/express/")
    fun expressinterestinterface(@Body requestBodyMap: ExpressInterestRequest, @Header("Authorization") token: String = "Token " + PrefsHelper().getPref("Token")): Call<ExpressInterestResponse>

    @POST("/interest/action/")
    fun revokeinterestinterface(@Body requestBodyMap: RevokeInterestRequest,@Header("Authorization") token: String = "Token " + PrefsHelper().getPref("Token")): Call<RevokeInterestResponse>


    @POST("/chat/action/")
    fun revokechatinterestinterface(@Body requestBodyMap: RevokeInterestRequest,@Header("Authorization") token: String = "Token " + PrefsHelper().getPref("Token")): Call<RevokeInterestResponse>

    @GET("/interest/received-request/2/")
    fun usersentrequests( @Header("Authorization") token: String = "Token " + PrefsHelper().getPref("Token")): Call<UsersSentRequestsResponse>

    @GET("/interest/received-request/1/")
    fun receivedrequestsinterface( @Header("Authorization") token: String = "Token " + PrefsHelper().getPref("Token")): Call<UsersSentRequestsResponse>

    @POST("/interest/action/")
    fun acceptinterestrequest(@Body requestBodyMap: AcceptInterestRequest,@Header("Authorization") token: String = "Token " + PrefsHelper().getPref("Token")): Call<AcceptIntetrestResponse>


    @GET("/interest/received-request/3/")
    fun confirmedexpressinterstinterface( @Header("Authorization") token: String = "Token " + PrefsHelper().getPref("Token")): Call<ConfirmedExpressInterestResponse>

    @GET("chat/chat-request/2/")
    fun availablechatlistinterface( @Header("Authorization") token: String = "Token " + PrefsHelper().getPref("Token")): Call<ListofActiveAvailableUsersChatResponse>

    @GET("chat/active-chat/")
    fun activechatinterface( @Header("Authorization") token: String = "Token " + PrefsHelper().getPref("Token")): Call<ListofActiveAvailableUsersChatResponse>

    @POST("/interest/action/")
    fun declineinterestrequestinterface(@Body requestBodyMap: DeclineInterestRequestRequest,@Header("Authorization") token: String = "Token " + PrefsHelper().getPref("Token")): Call<DeclinInterestRequestResponse>

    @POST("/chat/action/")
    fun startchatinterface(@Body requestBodyMap: SuspendChatRequest,@Header("Authorization") token: String = "Token " + PrefsHelper().getPref("Token")): Call<SuspendChatResponse>

    @POST("/chat/action/")
    fun suspendchatinterface(@Body requestBodyMap: SuspendChatRequest,@Header("Authorization") token: String = "Token " + PrefsHelper().getPref("Token")): Call<SuspendChatResponse>

    @GET("chat/chat-request/1/")
    fun getreceivedchatrequestsinterface( @Header("Authorization") token: String = "Token " + PrefsHelper().getPref("Token")): Call<GetReceivedChatRequestResponse>

    @POST("/chat/action/")
    fun acceptchatrequestinterface(@Body requestBodyMap: AcceptChatRequestRequest,@Header("Authorization") token: String = "Token " + PrefsHelper().getPref("Token")): Call<AcceptChatRequestResponse>

    @POST("/chat/request/")
    fun requestchatinterface(@Body requestBodyMap: RequestChatRequest,@Header("Authorization") token: String = "Token " + PrefsHelper().getPref("Token")): Call<RequestChatResponse>

    @POST("/manualmatch/manual-match_v1/")
    fun addpartnerinterface(@Body requestBodyMap: ManualMatchAddPartnerRequest,@Header("Authorization") token: String = "Token " + PrefsHelper().getPref("Token")): Call<AddPartnerResponse>


    @GET("/final_match/interest-express/1/")
    fun finalizesentusers(@Header("Authorization") token: String = "Token " + PrefsHelper().getPref("Token")): Call<ResponseSentReceivedRequestFinalizeMatch>

    @GET("/final_match/interest-express/2/")
    fun finalizereceivedusers(@Header("Authorization") token: String = "Token " + PrefsHelper().getPref("Token")): Call<ResponseSentReceivedRequestFinalizeMatch>

    @GET("/final_match/interest-express/3/")
    fun finalizeconfirmedusers(@Header("Authorization") token: String = "Token " + PrefsHelper().getPref("Token")): Call<ResponseSentReceivedRequestFinalizeMatch>

    @POST("/final_match/interest-express/")
    fun completematchapiservice(@Body requestBodyMap: FinalMatchRequest,@Header("Authorization") token: String = "Token " + PrefsHelper().getPref("Token")): Call<FinalMatchResponse>

    @HTTP(method ="DELETE",path ="/final_match/interest-express/",hasBody =true)
    fun revokecompletematchapiservice(@Body requestBodyMap: RevokeCompleteMatchRequest,@Header("Authorization") token: String = "Token " + PrefsHelper().getPref("Token")): Call<RevokeCompleteMatchResponse>

    @POST("/final_match/action/")
    fun acceptcompletematchapiservice(@Body requestBodyMap: AcceptDeclineCompleteMatchRequest,@Header("Authorization") token: String = "Token " + PrefsHelper().getPref("Token")): Call<AcceptDeclineCompleteMatchResponse>

    @POST("/final_match/action/")
    fun declinecompletematchapiservice(@Body requestBodyMap: AcceptDeclineCompleteMatchRequest,@Header("Authorization") token: String = "Token " + PrefsHelper().getPref("Token")): Call<AcceptDeclineCompleteMatchResponse>

















    companion object {
        val instance: NotificationApiService by lazy {

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
                .baseUrl(BuildConfig.BASE_URL6)
                .addConverterFactory(GsonConverterFactory.create(gson)).addCallAdapterFactory(
                    CoroutineCallAdapterFactory()
                )
                .build()
                .create(NotificationApiService::class.java)
        }

    }
}