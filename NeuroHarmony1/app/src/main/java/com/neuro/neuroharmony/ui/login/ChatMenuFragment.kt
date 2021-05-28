package com.neuro.neuroharmony.ui.login


import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.neuro.neuroharmony.R
import com.google.gson.GsonBuilder
import com.neuro.neuroharmony.ui.login.CompleteMatch.SearchPartner
import com.neuro.neuroharmony.utils.CommonUtils
import kotlinx.android.synthetic.main.confirmed_requests.*
import kotlinx.android.synthetic.main.matched_users_recycler_view.*
import kotlinx.android.synthetic.main.received_requests_users.*
import org.json.JSONArray


/**
 * A simple [Fragment] subclass.
 */
class ChatMenuFragmnet(position: Int) : Fragment() {

    var dialog: Dialog? = null
    var dialog2: Dialog? = null
    var dialog1: Dialog? = null

    private lateinit var viewModelActiveChat: ActiveChatUsersListViewModel
    private lateinit var viewModelAvailableChat: AvailableChatUsersListViewModel
    private lateinit var viewModelReceivedChatRequests: GetReceivedChatRequestsViewModel
    private lateinit var viewModelStartChat : StartChatViewModel
    private lateinit var viewModelSuspendChat: SuspendChatViewModel
    private lateinit var viewModelAcceptChat: AcceptChatRequestViewModel
    val position = position
    var gson = GsonBuilder().serializeNulls().create()

    @SuppressLint("WrongConstant")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        viewModelActiveChat = ViewModelProviders.of(this)[ActiveChatUsersListViewModel::class.java]
        viewModelStartChat = ViewModelProviders.of(this)[StartChatViewModel::class.java]
        viewModelSuspendChat = ViewModelProviders.of(this)[SuspendChatViewModel::class.java]
        viewModelAvailableChat = ViewModelProviders.of(this)[AvailableChatUsersListViewModel::class.java]
        viewModelReceivedChatRequests = ViewModelProviders.of(this)[GetReceivedChatRequestsViewModel::class.java]
        viewModelAcceptChat = ViewModelProviders.of(this)[AcceptChatRequestViewModel::class.java]
        val view = inflater.inflate(R.layout.fragment_chat_menu_fragmnet, container, false)
        val textView = view.findViewById<TextView>(R.id.chat_matched_user_name)

        val recyclerView = view.findViewById(R.id.chat_menu_recycler_view) as RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(activity, LinearLayout.VERTICAL, false)

        val user = ArrayList<ChatMenu.Userdetails>()

        setupObserversActiveChat(view)
        setupObserversAvailableChat(view)
        setupObserversReceivedChat(view)

        if(position==0){
            viewModelActiveChat.activechatlistvm()

        }
        if(position==1){
            viewModelAvailableChat.availablechatusersvm()

        }
        if(position==2){
            viewModelReceivedChatRequests.getreceivedchatrequestsvm()

        }

        return view
        // Inflate the layout for this fragment
    }




    @SuppressLint("WrongConstant")
    private fun setupObserversReceivedChat(view: View) {
        val recyclerView = view.findViewById(R.id.chat_menu_recycler_view) as RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(activity, LinearLayout.VERTICAL, false)
        val nothing_here = view?.findViewById(R.id.nothinghere) as TextView
        val user = ArrayList<ChatMenu.Userdetails>()

        viewModelReceivedChatRequests.receivedchatrequesResponseLiveData.observe(this, Observer {
            if (it!= null){
                if (it.message == "Success"){
                    if (it.data.isEmpty()){
                        recyclerView.setVisibility(View.GONE);
                        nothing_here.setVisibility(View.VISIBLE);
                    }


                    val array = JSONArray(gson.toJson(it.data))
                    for (i in 0 until array.length()){
                        val obj = array.getJSONObject(i)
                        val specs = listOf<Any>().toMutableList()
                        specs.add(it.data[i].first_name)
                        specs.add(it.data[i].user_key)
                        specs.add(it.data[i].profile_pic)
                        specs.add(it.data[i].age)
                        specs.add(it.data[i].status)
                        specs.add(it.data[i].gender)
                        specs.add(it.data[i].religion_name)
                        specs.add(it.data[i].caste_name)
                        specs.add(it.data[i].profession)
                        specs.add(it.data[i].present_city)
                        specs.add(it.data[i].marital_status_name)
                        specs.add(it.data[i].gender_choice)
                        specs.add(it.data[i].present_state)
                        user.add(ChatMenu.Userdetails(specs))
                    }
                    val adapter_recycler = ChatMenuRecycleViewRequest( activity,user, viewModelAcceptChat)
                    recyclerView.adapter = adapter_recycler
                    adapter_recycler.notifyDataSetChanged()
                }
                else{
                    Toast.makeText(activity, it.message, Toast.LENGTH_SHORT).show()
                }
            }
        })
        viewModelReceivedChatRequests.receivedchatrequesAPICallStatus.observe(this, Observer {
            processStatus2(it)
        })

    }

    @SuppressLint("WrongConstant")
    private fun setupObserversAvailableChat(view: View) {
        val recyclerView = view.findViewById(R.id.chat_menu_recycler_view) as RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(activity, LinearLayout.VERTICAL, false)
        val nothing_here = view?.findViewById(R.id.nothinghere) as TextView
        val user = ArrayList<ChatMenu.Userdetails>()

        viewModelAvailableChat.AvailableChatUsersListResponseLiveData.observe(this, Observer {
            if (it!= null){
                if (it.message == "Success"){
                    if (it.data.isEmpty()){
                        recyclerView.setVisibility(View.GONE);
                        nothing_here.setVisibility(View.VISIBLE);
                    }
                    val array = JSONArray(gson.toJson(it.data))
                    for (i in 0 until array.length()){
                        val specs = listOf<Any>().toMutableList()
                        specs.add(it.data[i].first_name)
                        specs.add(it.data[i].user_key)
                        specs.add(it.data[i].profile_pic)
                        specs.add(it.data[i].age)
                        specs.add(it.data[i].status)
                        specs.add(it.data[i].gender)
                        specs.add(it.data[i].religion_name)
                        specs.add(it.data[i].caste_name)
                        specs.add(it.data[i].profession)
                        specs.add(it.data[i].present_city)
                        specs.add(it.data[i].marital_status_name)
                        specs.add(it.data[i].gender_choice)
                        specs.add(it.data[i].present_state)
                        user.add(ChatMenu.Userdetails(specs))
                    }

                    val adapter_recycler = ChatMenuRecyclerViewAvailable( activity,user, viewModelStartChat)
                    recyclerView.adapter = adapter_recycler
                    adapter_recycler.notifyDataSetChanged()
                }
                else{
                    Toast.makeText(activity, it.message, Toast.LENGTH_SHORT).show()
                }
            }
        })
        viewModelAvailableChat.AvailableChatUsersListAPICallStatus.observe(this, Observer {
            processStatus1(it)
        })

    }


    @SuppressLint("WrongConstant")
    private fun setupObserversActiveChat(view: View) {

        val recyclerView = view.findViewById(R.id.chat_menu_recycler_view) as RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(activity, LinearLayout.VERTICAL, false)
        val nothing_here = view?.findViewById(R.id.nothinghere) as TextView
        val user = ArrayList<ChatMenu.Userdetails>()
        viewModelActiveChat.ActiveChatUsersListResponseLiveData.observe(this, Observer {
            if (it!= null){
                if (it.message == "Success"){
                    if (it.data.isEmpty()){
                        recyclerView.setVisibility(View.GONE);
                        nothing_here.setVisibility(View.VISIBLE);
                    }
                    val array = JSONArray(gson.toJson(it.data))
                    for (i in 0 until array.length()){
                        val obj = array.getJSONObject(i)
                        val specs = listOf<Any>().toMutableList()
                        specs.add(it.data[i].first_name)
                        specs.add(it.data[i].user_key)
                        specs.add(it.data[i].profile_pic)
                        specs.add(it.data[i].age)
                        specs.add(it.data[i].status)
                        specs.add(it.data[i].gender)
                        specs.add(it.data[i].religion_name)
                        specs.add(it.data[i].caste_name)
                        specs.add(it.data[i].profession)
                        specs.add(it.data[i].present_city)
                        specs.add(it.data[i].marital_status_name)
                        specs.add(it.data[i].gender_choice)
                        specs.add(it.data[i].present_state)
                        user.add(ChatMenu.Userdetails(specs))
                    }

                    val adapter_recycler = ChatMenuRecyclerView( activity,user, viewModelSuspendChat)
                    recyclerView.adapter = adapter_recycler
                    adapter_recycler.notifyDataSetChanged()
                }
                else{
                    Toast.makeText(activity, it.message, Toast.LENGTH_SHORT).show()
                }
            }
        })
        viewModelActiveChat.ActiveChatUsersListAPICallStatus.observe(this, Observer {
            processStatus(it)
        })
    }


    private fun processStatus(
        resource: ResourceStatus
    ){
        when (resource.status) {
            StatusType.SUCCESS -> {
                dialog?.let {
                    it.dismiss()
                }


            }
            StatusType.EMPTY_RESPONSE -> {
                dialog?.let {
                    it.dismiss()
                }

            }
            StatusType.PROGRESSING -> {
                activity?.runOnUiThread {
                    dialog = CommonUtils().showDialog(activity!!)
                }
            }
            StatusType.SWIPE_RELOADING -> {


            }
            StatusType.ERROR -> {
                val dialogBuilder = AlertDialog.Builder(activity)

                // set message of alert dialog
                dialogBuilder.setMessage("Server Error. Please try again")
                    // if the dialog is cancelable
                    .setCancelable(false)
                    // positive button text and action
                    .setPositiveButton(
                        "Okay",
                        DialogInterface.OnClickListener { dialogue, id ->
                            dialog!!.dismiss()
                            dialogue.cancel()
                        })
                // negative button text and action

                // create dialog box
                val alert = dialogBuilder.create()
                // set title for alert dialog box
                alert.setTitle("Message")
                // show alert dialog
                alert.show()
                dialog?.let {
                    it.dismiss()
                }


            }
            StatusType.LOADING_MORE -> {
                // CommonUtils().showSnackbar(binding.root, "Loading more..")
            }
            StatusType.NO_NETWORK -> {
                            dialog?.dismiss()
                            Toast.makeText(activity, "Please check internet connection", Toast.LENGTH_SHORT).show()
            }
            StatusType.SESSION_EXPIRED -> {
                //CommonUtils().showSnackbar(revoke_interest_button.rootView,"session expired")
            }
        }
    }




    private fun processStatus1(
        resource: ResourceStatus
    ){
        when (resource.status) {
            StatusType.SUCCESS -> {
                dialog1?.let {
                    it.dismiss()
                }
            }
            StatusType.EMPTY_RESPONSE -> {
                dialog1?.let {
                    it.dismiss()
                }

            }
            StatusType.PROGRESSING -> {
                activity?.runOnUiThread {
                    dialog1 = CommonUtils().showDialog(activity!!)
                }
            }
            StatusType.SWIPE_RELOADING -> {


            }
            StatusType.ERROR -> {
                val dialogBuilder = AlertDialog.Builder(activity)

                // set message of alert dialog
                dialogBuilder.setMessage("Server Error. Please try again")
                    // if the dialog is cancelable
                    .setCancelable(false)
                    // positive button text and action
                    .setPositiveButton(
                        "Okay",
                        DialogInterface.OnClickListener { dialog, id ->
                            dialog1!!.dismiss()
                            dialog.cancel()
                        })
                // negative button text and action

                // create dialog box
                val alert = dialogBuilder.create()
                // set title for alert dialog box
                alert.setTitle("Message")
                // show alert dialog
                alert.show()
                dialog1?.let {
                    it.dismiss()
                }


            }
            StatusType.LOADING_MORE -> {
                // CommonUtils().showSnackbar(binding.root, "Loading more..")
            }
            StatusType.NO_NETWORK -> {
                            dialog1?.dismiss()
                            Toast.makeText(activity, "Please check internet connection", Toast.LENGTH_SHORT).show()
            }
            StatusType.SESSION_EXPIRED -> {
                //CommonUtils().showSnackbar(revoke_interest_button.rootView,"session expired")
            }
        }
    }



    private fun processStatus2(
        resource: ResourceStatus
    ){
        when (resource.status) {
            StatusType.SUCCESS -> {
                dialog2?.let {
                    it.dismiss()
                }
            }
            StatusType.EMPTY_RESPONSE -> {
                dialog2?.let {
                    it.dismiss()
                }

            }
            StatusType.PROGRESSING -> {
                activity?.runOnUiThread {
                    dialog2 = CommonUtils().showDialog(activity!!)
                }
            }
            StatusType.SWIPE_RELOADING -> {


            }
            StatusType.ERROR -> {
                val dialogBuilder = AlertDialog.Builder(activity)

                // set message of alert dialog
                dialogBuilder.setMessage("Server Error. Please try again")
                    // if the dialog is cancelable
                    .setCancelable(false)
                    // positive button text and action
                    .setPositiveButton(
                        "Okay",
                        DialogInterface.OnClickListener { dialog, id ->
                            dialog2!!.dismiss()
                            dialog.cancel()
                        })
                // negative button text and action

                // create dialog box
                val alert = dialogBuilder.create()
                // set title for alert dialog box
                alert.setTitle("Message")
                // show alert dialog
                alert.show()
                dialog2?.let {
                    it.dismiss()
                }


            }
            StatusType.LOADING_MORE -> {
                // CommonUtils().showSnackbar(binding.root, "Loading more..")
            }
            StatusType.NO_NETWORK -> {
                            dialog2?.dismiss()
                Toast.makeText(activity, "Please check internet connection", Toast.LENGTH_SHORT).show()
            }
            StatusType.SESSION_EXPIRED -> {
                //CommonUtils().showSnackbar(revoke_interest_button.rootView,"session expired")
            }
        }
    }

    override fun onPause() {
        super.onPause()
        if (dialog != null && dialog!!.isShowing()) {
            dialog!!.dismiss();
        }
        if (dialog1 != null && dialog1!!.isShowing()) {
            dialog1!!.dismiss();
        }
        if (dialog2 != null && dialog2!!.isShowing()) {
            dialog2!!.dismiss();
        }
    }


}
