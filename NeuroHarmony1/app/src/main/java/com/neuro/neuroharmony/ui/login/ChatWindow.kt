package com.neuro.neuroharmony.ui.login

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.widget.LinearLayout
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.neuro.neuroharmony.R
import com.neuro.neuroharmony.utils.PrefsHelper
import com.google.firebase.Timestamp
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.Query
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_chat_window.*
import kotlinx.android.synthetic.main.chat_toolbar.*
import okio.Utf8
import org.cryptonode.jncryptor.AES256JNCryptor
import org.cryptonode.jncryptor.InvalidHMACException
import java.lang.Error
import java.util.*
import kotlin.collections.ArrayList
import kotlin.reflect.jvm.internal.impl.renderer.DescriptorRenderer
import kotlin.text.Charsets.UTF_8


class ChatWindow : BaseActivity() {

    private var result: ListenerRegistration? = null
    private lateinit var viewModelSuspendChat: SuspendChatViewModel
    val password = "6dpC295ei9"

    companion object {
        const val COLLECTION_KEY = "Conversations"
        const val MESSAGE_COLLECTION = "Messages"
        const val NAME_FIELD = "SenderKey"
        const val TEXT_FIELD = "Content"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_window)
        val intent = intent
        val profile_pic = intent.getStringExtra("profile_pic")
        viewModelSuspendChat = ViewModelProviders.of(this)[SuspendChatViewModel::class.java]

        back_chat_window.setOnClickListener{
            onBackPressed()
        }

        if (profile_pic != ""){
            Picasso.get()
                .load(profile_pic)
                .resize(100,100)
                .transform(CircleTransform())
                .placeholder(R.mipmap.profile_pic_placeholder)
                .centerCrop()
                .into(chat_matched_user_photo)}

        val user_name = intent.getStringExtra("user_name")
        chat_matched_user_name.text = user_name


        setupObserversSuspendChat()

        val matched_user = intent.getStringExtra("user_key")
        val matched_key = matched_user.toInt()
        var DOCUMENT_KEY = ""
        val user_key = PrefsHelper().getPref<String>("userKey")
        val user = user_key.toInt()
        if(matched_key < user){
            DOCUMENT_KEY = matched_user + "_" + user_key
        }else{
            DOCUMENT_KEY = user_key + "_" + matched_user
        }
        val firestoreChat = FirebaseFirestore.getInstance().collection(COLLECTION_KEY).document(DOCUMENT_KEY).collection(
            MESSAGE_COLLECTION)
        realtimeUpdateListener(firestoreChat)



        button_send.setOnClickListener{
            if (!text_to_send.text.trim().isEmpty()) {
                sendmessage(firestoreChat)
            }
            text_to_send.setText("")
        }


        chat_suspend_button_chat_window.setOnClickListener {
            viewModelSuspendChat.suspendchatvm(matched_user.toString(), 4)
        }


    }

    private fun sendmessage(firestoreChat: CollectionReference) {
        val userKey = PrefsHelper().getPref<String>("userKey")
        val userName = PrefsHelper().getPref<String>("user_name")
        val cryptor = AES256JNCryptor();
        val text = text_to_send.text.toString().toByteArray(charset("UTF-8"))
        val encrypt =  cryptor.encryptData(text, password.toCharArray())
        val encode = Base64.encodeToString(encrypt, Base64.NO_WRAP)
        Log.d("encode", encode)
        Log.d("encrypt", encrypt.toString())

        val newMessage = mapOf(
            TEXT_FIELD to encode,
            "TimeStamp" to Timestamp(Date()),
            NAME_FIELD to userKey,
            "SenderName" to userName)
        firestoreChat
            .add(newMessage)
            .addOnSuccessListener{documentReference ->
                Log.d("documentSnapshot", "DocumentSnapshot added with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Log.e("ERROR", e.message)
            }
    }



    private fun realtimeUpdateListener(firestoreChat: CollectionReference) {
        val firestoreChat = firestoreChat.orderBy("TimeStamp", Query.Direction.ASCENDING)

        val cryptor = AES256JNCryptor();

        result = firestoreChat.addSnapshotListener { documentSnapshot, e ->
            when {
                e != null -> Log.e("ERROR", e.message)
                documentSnapshot != null && !documentSnapshot.isEmpty() -> {
                    val messages = ArrayList<Chatdetails>()
                    for (doc in documentSnapshot!!){
                        val decode = Base64.decode(doc.getString(TEXT_FIELD), Base64.NO_WRAP)
                        try {
                            val decrypted =
                                String(cryptor.decryptData(decode, password.toCharArray()))
                            doc.getString(NAME_FIELD).let {
                                it?.let { it1 ->
                                    decrypted?.let { it2 ->
                                        Chatdetails(
                                            it1,
                                            it2, doc.getTimestamp("TimeStamp")
                                        )
                                    }?.let { it3 -> messages.add(it3) }
                                }
                            }
                        }catch (e: InvalidHMACException){
                            Log.d("error", e.toString())
                        }
                    }
                    createChat(messages)
                }
            }
        }
    }

    @SuppressLint("WrongConstant")
    private fun createChat(messages: ArrayList<Chatdetails>) {

        val recyclerView = findViewById<RecyclerView>(R.id.chat_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)
        recyclerView.scrollToPosition(messages.size - 1)

        val adapter = ChatMessageAdapter(messages)
        recyclerView.adapter = adapter

    }


    private fun setupObserversSuspendChat() {
        this?.let {
            viewModelSuspendChat.suspendchatResponseLiveData.observe(it, androidx.lifecycle.Observer {
                if (it != null) {
                    if (it.message == "Success") {

                        val dialogBuilder = AlertDialog.Builder(this)

                        // set message of alert dialog
                        dialogBuilder.setMessage("Your Chat has been suspended")
                            // if the dialog is cancelable
                            .setCancelable(false)
                            // positive button text and action
                            .setPositiveButton(
                                "Okay",
                                DialogInterface.OnClickListener { dialog, id ->
                                    onBackPressed()
                                    dialog.cancel()
                                })
                        // negative button text and action

                        // create dialog box
                        val alert = dialogBuilder.create()
                        // set title for alert dialog box
                        alert.setTitle("Message")
                        // show alert dialog
                        alert.show()

                    } else {
                        Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                    }
                }
            })
            viewModelSuspendChat.suspendchatAPICallStatus.observe(this, androidx.lifecycle.Observer{
                processStatus(it)
            })
        }
    }


    private fun processStatus(
        resource: ResourceStatus
    ){
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
                val dialogBuilder = AlertDialog.Builder(this)

                // set message of alert dialog
                dialogBuilder.setMessage("Server Error. Please try again")
                    // if the dialog is cancelable
                    .setCancelable(false)
                    // positive button text and action
                    .setPositiveButton(
                        "Okay",
                        DialogInterface.OnClickListener { dialog, id ->
                            onBackPressed()
                            dialog.cancel()
                        })
                // negative button text and action

                // create dialog box
                val alert = dialogBuilder.create()
                // set title for alert dialog box
                alert.setTitle("Message")
                // show alert dialog
                alert.show()
                dismissDialog()


            }
            StatusType.LOADING_MORE -> {
                // CommonUtils().showSnackbar(binding.root, "Loading more..")
            }
            StatusType.NO_NETWORK -> {
                val dialogBuilder = AlertDialog.Builder(this)

                // set message of alert dialog
                dialogBuilder.setMessage("Please check the internet condition")
                    // if the dialog is cancelable
                    .setCancelable(false)
                    // positive button text and action
                    .setPositiveButton(
                        "Okay",
                        DialogInterface.OnClickListener { dialog, id ->
                            onBackPressed()
                            dialog.cancel()
                        })
                // negative button text and action

                // create dialog box
                val alert = dialogBuilder.create()
                // set title for alert dialog box
                alert.setTitle("Message")
                // show alert dialog
                alert.show()

                dismissDialog()
            }
            StatusType.SESSION_EXPIRED -> {
                //CommonUtils().showSnackbar(revoke_interest_button.rootView,"session expired")
            }
        }
    }


    data class Chatdetails(
        val senderKey: String,
        val message: String,
        val timestamp: Timestamp?
    )

    override fun onBackPressed() {

        result?.remove()
        val intent = Intent(this, ChatMenu::class.java)
        startActivity(intent)
    }
}
