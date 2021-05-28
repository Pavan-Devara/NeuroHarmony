package com.neuro.neuroharmony.ui.login

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.neuro.neuroharmony.R
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_add_data_to_firestore.*

class AddDataToFirestore : BaseActivity() {

    companion object {
        const val COLLECTION_KEY = "users"
        const val DOCUMENT_KEY = "user"
        const val NAME_FIELD = "Name"
        const val TEXT_FIELD = "message"
    }

    private val firestoreChat by lazy {
        FirebaseFirestore.getInstance().collection(COLLECTION_KEY).document(DOCUMENT_KEY)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_data_to_firestore)

        val db = FirebaseFirestore.getInstance()


        button_firestore.setOnClickListener{
            sendmessage()
        }

        // Create a new user with a first and last name
        val user = hashMapOf(
            "first" to "Ada",
            "last" to "Lovelace",
            "born" to 1815
        )
        val user1 = hashMapOf(
            "first" to "Pavan",
            "last" to "Devara",
            "born" to 19,
            "message" to "hello"
        )

        user1["info_add"] = user

// Add a new document with a generated ID
        db.collection("users1")
            .add(user)
            .addOnSuccessListener { documentReference ->
                Log.d("documentSnapshot", "DocumentSnapshot added with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Log.w("error", "Error adding document", e)
            }

        db.collection("users").document("user")
            .set(user1)
            .addOnSuccessListener {
                Log.d("documentSnapshot", "Document successfully overwritten")
            }
            .addOnFailureListener { e ->
                Log.w("error", "Error writing document", e)
            }


    }

    private fun sendmessage() {
        val newMessage = mapOf(
            TEXT_FIELD to text_message1.text.toString())
        firestoreChat.set(newMessage)
            .addOnSuccessListener( {
                Toast.makeText(this, "Message Sent", Toast.LENGTH_SHORT).show()
                realtimeUpdateListener()
            })
            .addOnFailureListener { e -> Log.e("ERROR", e.message) }
    }

    private fun realtimeUpdateListener() {
        firestoreChat.addSnapshotListener { documentSnapshot, e ->
            when {
                e != null -> Log.e("ERROR", e.message)
                documentSnapshot != null && documentSnapshot.exists() -> {
                    with(documentSnapshot) {
                        text2_firestore.text = "${data?.get(TEXT_FIELD)}"
                    }
                }
            }
        }
    }
}
