package com.neuro.neuroharmony.ui.login


import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.neuro.neuroharmony.utils.CommonUtils
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.confirmed_requests.*
import kotlinx.android.synthetic.main.matched_users_recycler_view.*
import kotlinx.android.synthetic.main.received_requests_users.*
import com.neuro.neuroharmony.R
import org.json.JSONArray

/**
 * A simple [Fragment] subclass.
 */
class RequestsFragment(var position: Int) : Fragment() {

    var dialog: Dialog? = null
    var dialog2: Dialog? = null
    var dialog1: Dialog? = null

    private lateinit var viewModelReceivedRequests: GetReceivedRequestViewModel
    private lateinit var viewModelSentRequests: USersSentRequestsViewModel
    private lateinit var viewModelConfirmedInterest: ConfirmedExpressInterestViewModel
    private lateinit var viewModelAcceptRequests: AcceptInterstRequestViewModel
    private lateinit var viewModelDeclineRequests: DeclineInterstRequestViewModel
    private lateinit var viewModelRevokeInterest: RevokeInterestRequestViewModel
    private lateinit var viewModelExpressChatInterest: RequestChatViewModel
    var gson = GsonBuilder().serializeNulls().create()
    var counter = 0

    @SuppressLint("WrongConstant")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        viewModelReceivedRequests = ViewModelProviders.of(this)[GetReceivedRequestViewModel::class.java]
        viewModelSentRequests = ViewModelProviders.of(this)[USersSentRequestsViewModel::class.java]
        viewModelConfirmedInterest = ViewModelProviders.of(this)[ConfirmedExpressInterestViewModel::class.java]
        viewModelAcceptRequests = ViewModelProviders.of(this)[AcceptInterstRequestViewModel::class.java]
        viewModelDeclineRequests = ViewModelProviders.of(this)[DeclineInterstRequestViewModel::class.java]
        viewModelRevokeInterest = ViewModelProviders.of(this)[RevokeInterestRequestViewModel::class.java]
        viewModelExpressChatInterest = ViewModelProviders.of(this)[RequestChatViewModel::class.java]


        val view = inflater.inflate(R.layout.fragment_requests, container, false)

        val recyclerView = view.findViewById(R.id.received_requests_recycler_view) as RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(activity, LinearLayout.VERTICAL, false)

        setupObserversReceivedRequests(view)
        setupObserversConfirmedRequests(view)
        setupObserversSentRequests(view)
        if (position==1) {
            viewModelReceivedRequests.getrequestrequestsvm()

        }else if (position==0){
            viewModelConfirmedInterest.usersentrequestsvm()


        }else if (position == 2){
            viewModelSentRequests.usersentrequestsvm()

        }
        return view
    }


    @SuppressLint("WrongConstant")
    private fun setupObserversSentRequests(view: View) {
        val recyclerView = view.findViewById(R.id.received_requests_recycler_view) as RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(activity, LinearLayout.VERTICAL, false)
        val nothing_here = view?.findViewById(R.id.nothinghere) as TextView

        val user = ArrayList<Chat_Requests.Userdetails>()
        var trackerCard = listOf<Int>().toMutableList()
        viewModelSentRequests.UsersSentRequestResponseLiveData.observe(this, Observer {
            if (it != null){
                if (it.message == "Success") {
                    if (it.data.isEmpty()){
                        recyclerView.setVisibility(View.GONE);
                        nothing_here.setVisibility(View.VISIBLE);
                    }

                        val array = JSONArray(gson.toJson(it.data))
                        for (i in 0 until array.length()) {
                            trackerCard.add(0)
                            val specs = listOf<Any>().toMutableList()
                            val obj = array.getJSONObject(i)
                            specs.add(it.data[i].firstName)
                            specs.add(it.data[i].age)
                            specs.add(it.data[i].presentResidence)
                            specs.add(it.data[i].religionName)
                            specs.add(it.data[i].status)
                            specs.add(it.data[i].gender)
                            specs.add(it.data[i].userKey)
                            specs.add(it.data[i].casteName)
                            specs.add(it.data[i].profilePic)
                            specs.add(it.data[i].profession)
                            specs.add(it.data[i].maritialStatus)
                            specs.add(it.data[i].gender_choice)
                            user.add(Chat_Requests.Userdetails(specs, trackerCard))
                        }
                    val adapter_recycler = SentRequestsRecyclerAdapter(activity, user, viewModelRevokeInterest)
                    recyclerView.adapter = adapter_recycler
                    adapter_recycler.notifyDataSetChanged()
                }
                else{
                    Toast.makeText(activity, it.message, Toast.LENGTH_SHORT).show()
                }
            }
        })
        viewModelSentRequests.UsersSentRequestAPICallStatus.observe(this, Observer {
            processStatus2(it)
        })

    }





    @SuppressLint("WrongConstant")
    private fun setupObserversConfirmedRequests(view: View) {
        val recyclerView = view.findViewById(R.id.received_requests_recycler_view) as RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(activity, LinearLayout.VERTICAL, false)
        val nothing_here = view?.findViewById(R.id.nothinghere) as TextView

        val user = ArrayList<RequestsFragment.ConfirmUserRequests>()
        var trackerCard = listOf<Int>().toMutableList()
        viewModelConfirmedInterest.UsersSentRequestResponseLiveData.observe(this, Observer {
            if (it != null){
                if (it.message == "Success"){
                    if (it.data.isEmpty()){
                        recyclerView.setVisibility(View.GONE);
                        nothing_here.setVisibility(View.VISIBLE);
                    }
                        val array = JSONArray(gson.toJson(it.data))
                        Log.d("array", array.toString())
                        for (i in 0 until array.length()) {
                            trackerCard.add(0)
                            val specs = listOf<Any>().toMutableList()
                            val obj = array.getJSONObject(i)
                            specs.add(it.data[i].firstName)
                            specs.add(it.data[i].age)
                            specs.add(it.data[i].presentResidence)
                            specs.add(it.data[i].religionName)
                            specs.add(it.data[i].status)
                            specs.add(it.data[i].gender)
                            specs.add(it.data[i].requestStatus)
                            specs.add(it.data[i].chatStatus)
                            specs.add(it.data[i].userKey)
                            specs.add(it.data[i].casteName)
                            specs.add(it.data[i].profilePic)
                            specs.add(it.data[i].profession)
                            specs.add(it.data[i].maritialStatus)
                            specs.add(it.data[i].gender_choice)
                            var chat_status = obj.getInt("chat_status")
                            user.add(ConfirmUserRequests(specs, chat_status))
                        }

                    val adapter_recycler = ConfirmedRequestsRecyclerAdapter(activity, user, viewModelExpressChatInterest)
                    recyclerView.adapter = adapter_recycler
                    adapter_recycler.notifyDataSetChanged()
                }
                else{
                    Toast.makeText(activity, it.message, Toast.LENGTH_SHORT).show()
                }
            }
        })
        viewModelConfirmedInterest.UsersSentRequestAPICallStatus.observe(this, Observer {
            processStatus(it)
        })

    }





    @SuppressLint("WrongConstant")
    private fun setupObserversReceivedRequests(view: View) {
        val recyclerView = view.findViewById(R.id.received_requests_recycler_view) as RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(activity, LinearLayout.VERTICAL, false)
        val nothing_here = view?.findViewById(R.id.nothinghere) as TextView

        val user = ArrayList<Chat_Requests.Userdetails>()
        var trackerCard = listOf<Int>().toMutableList()
        viewModelReceivedRequests.ReceivedRequestResponseLiveData.observe(this, Observer {
            counter = counter +1
            if (it != null){
                if (it.message == "Success") {
                    if (it.data.isEmpty()){
                        recyclerView.setVisibility(View.GONE);
                        nothing_here.setVisibility(View.VISIBLE);
                    }
                        val array1 = gson.toJson(it.data)
                        val array = JSONArray(gson.toJson(it.data))
                        Log.d("jsonArray", array.toString())
                        for (i in 0 until array.length()) {
                            trackerCard.add(0)
                            val specs = listOf<Any>().toMutableList()
                            val obj = array.getJSONObject(i)
                            specs.add(it.data[i].firstName)
                            specs.add(it.data[i].age)
                            specs.add(it.data[i].presentResidence)
                            specs.add(it.data[i].religionName)
                            specs.add(it.data[i].casteName)
                            specs.add(it.data[i].status)
                            specs.add(it.data[i].gender)
                            specs.add(it.data[i].userKey)
                            specs.add(it.data[i].profilePic)
                            specs.add(it.data[i].profession)
                            specs.add(it.data[i].maritialStatus)
                            specs.add(it.data[i].gender_choice)
                            user.add(Chat_Requests.Userdetails(specs, trackerCard))
                        }
                    val adapter_recycler = ReceivedRequestsRecyclerAdapter(
                        activity,
                        user,
                        viewModelAcceptRequests,
                        viewModelDeclineRequests
                    )
                    recyclerView.adapter = adapter_recycler
                    adapter_recycler.notifyDataSetChanged()
                }
                else{
                    Toast.makeText(activity, it.message, Toast.LENGTH_SHORT).show()
                }
            }
        })
        viewModelReceivedRequests.ReceivedRequestAPICallStatus.observe(this, Observer {
            processStatus1(it)
        })
    }



    data class ConfirmUserRequests(
        val specs: MutableList<Any>,
        var chatStatus: Int
    )


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
