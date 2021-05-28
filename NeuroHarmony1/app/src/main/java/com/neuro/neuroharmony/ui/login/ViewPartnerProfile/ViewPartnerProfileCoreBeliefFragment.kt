package com.neuro.neuroharmony.ui.login.ViewPartnerProfile


import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat.getDrawable
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.gson.Gson

import com.neuro.neuroharmony.R
import com.neuro.neuroharmony.ui.login.*
import com.neuro.neuroharmony.utils.CommonUtils
import com.neuro.neuroharmony.utils.PrefsHelper
import kotlinx.android.synthetic.main.activity_core_belief_graphical_results.*
import kotlinx.android.synthetic.main.fragment_view_partner_profile_core_belief.*
import org.json.JSONArray

/**
 * A simple [Fragment] subclass.
 */
class ViewPartnerProfileCoreBeliefFragment : Fragment() {

    private lateinit var viewModelbaseline: BaselineCoreResultViewModel
    var gson = Gson()
    var dialog2: Dialog? = null
    val COREBELIEFTEST = "3"
    val IDEALIST = 1
    val POWERITE = 2
    val GROUPIE = 3

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(
            R.layout.fragment_view_partner_profile_core_belief,
            container,
            false
        )

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val matched_user = PrefsHelper().getPref<String>("matched_user")
        viewModelbaseline = ViewModelProviders.of(this)[BaselineCoreResultViewModel::class.java]
        setupObserverbaseline()
        PrefsHelper().savePref("test_type",COREBELIEFTEST)
        viewModelbaseline.baselinecoreresultvm(matched_user)
    }


    private fun setupObserverbaseline() {
        viewModelbaseline.loginResponseLiveData.observe(this, Observer {

            if (it != null){
                if (it.message == "Success"){

                    val data = it.data.items
                    PrefsHelper().savePref("sessionId", null)
                    val jsonstring = gson.toJson(data)

                    val array = JSONArray(jsonstring)
                    val userGroupScore = it.data.userScore.groupType
                    val userOrderNumber = it.data.userScore.orderNumber

                    for (i in 0 until array.length()){
                        val obj = array.getJSONObject(i)
                        val group_type = obj.getInt("group_type")
                        val subItems = obj.getJSONArray("sub_items")
                        val button1 = subItems.getJSONObject(0).getString("name")
                        val button2 = subItems.getJSONObject(1).getString("name")
                        val button3 = subItems.getJSONObject(2).getString("name")
                        val order1 = subItems.getJSONObject(0).getInt("order_number")
                        val order2 = subItems.getJSONObject(1).getInt("order_number")
                        val order3 = subItems.getJSONObject(2).getInt("order_number")
                        if(group_type==IDEALIST){
                            if (group_type.toString() == userGroupScore) {
                                when (userOrderNumber.toInt()) {
                                    order1 -> idealisticCreativityPartnerProfileCoreBeliefFragment.background =
                                        activity?.let { it1 -> getDrawable(it1, R.drawable.rounded_button_square) }
                                    order2 -> idealisticPerfectionistPartnerProfileCoreBeliefFragment.background =
                                        activity?.let { it1 -> getDrawable(it1, R.drawable.rounded_button_square) }
                                    order3 -> idealisticVisionaryPartnerProfileCoreBeliefFragment.background =
                                        activity?.let { it1 -> getDrawable(it1, R.drawable.rounded_button_square) }
                                }
                            }
                            idealisticCreativityPartnerProfileCoreBeliefFragment.text = button1
                            idealisticPerfectionistPartnerProfileCoreBeliefFragment.text = button2
                            idealisticVisionaryPartnerProfileCoreBeliefFragment.text = button3

                        }else if (group_type==POWERITE){

                            if (group_type.toString() == userGroupScore) {
                                when (userOrderNumber.toInt()) {
                                    order1 -> PoweriteAnalystPartnerProfileCoreBeliefFragment.background =
                                        activity?.let { it1 -> getDrawable(it1, R.drawable.rounded_button_square) }
                                    order2 -> PoweriteHelperPartnerProfileCoreBeliefFragment.background =
                                        activity?.let { it1 -> getDrawable(it1, R.drawable.rounded_button_square) }
                                    order3 -> PoweriteBossPartnerProfileCoreBeliefFragment.background =
                                        activity?.let { it1 -> getDrawable(it1, R.drawable.rounded_button_square) }
                                }
                            }
                            PoweriteAnalystPartnerProfileCoreBeliefFragment.text = button1
                            PoweriteHelperPartnerProfileCoreBeliefFragment.text = button2
                            PoweriteBossPartnerProfileCoreBeliefFragment.text = button3

                        }else if (group_type == GROUPIE){

                            if (group_type.toString() == userGroupScore) {
                                when (userOrderNumber.toInt()) {
                                    order1 -> GroupiePeacemakerPartnerProfileCoreBeliefFragment.background =
                                        activity?.let { it1 -> getDrawable(it1,R.drawable.rounded_button_square) }
                                    order2 -> GroupieLoyalSkeptiePartnerProfileCoreBeliefFragment.background =
                                        activity?.let { it1 -> getDrawable(it1, R.drawable.rounded_button_square) }
                                    order3 -> GroupieAchieverPartnerProfileCoreBeliefFragment.background =
                                        activity?.let { it1 -> getDrawable(it1, R.drawable.rounded_button_square) }
                                }
                            }
                            GroupiePeacemakerPartnerProfileCoreBeliefFragment.text = button1
                            GroupieLoyalSkeptiePartnerProfileCoreBeliefFragment.text = button2
                            GroupieAchieverPartnerProfileCoreBeliefFragment.text = button3
                        }else{
                            Toast.makeText(activity, "No proper Group", Toast.LENGTH_SHORT).show()
                        }
                    }
                }else{
                    Toast.makeText(activity, it.message, Toast.LENGTH_SHORT).show()
                }
            }
        })
        //observe API call status
        viewModelbaseline.loginAPICallStatus.observe(this, Observer {
            processStatus2(it)
        })
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
        if (dialog2 != null && dialog2!!.isShowing()) {
            dialog2!!.dismiss();
        }
    }

}
