package com.neuro.neuroharmony.ui.login.ViewPartnerProfile


import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.gson.Gson

import com.neuro.neuroharmony.R
import com.neuro.neuroharmony.ui.login.BaselineResultViewModel
import com.neuro.neuroharmony.ui.login.ResourceStatus
import com.neuro.neuroharmony.ui.login.StatusType
import com.neuro.neuroharmony.utils.CommonUtils
import com.neuro.neuroharmony.utils.PrefsHelper

/**
 * A simple [Fragment] subclass.
 */
class  ViewPartnerProfileNeuroFragment : Fragment() {
    private lateinit var viewModelbaseline: BaselineResultViewModel

    var NEUROTEST = "1"
    var dialog2: Dialog? = null
    var gson = Gson()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_view_partner_profile_neuro, container, false)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val matched_user = PrefsHelper().getPref<String>("matched_user")
        viewModelbaseline = ViewModelProviders.of(this)[BaselineResultViewModel::class.java]
        setupObserverbaseline()
        PrefsHelper().savePref("test_type",NEUROTEST)
        viewModelbaseline.baselineresultvm(matched_user)
    }


    private fun setupObserverbaseline() {
        viewModelbaseline.loginResponseLiveData.observe(this, Observer {

            if (it != null){
                if (it.message == "Success"){

                    val data = it.data
                    PrefsHelper().savePref("sessionId", null)
                    val jsonArray = gson.toJson(data)
                    val neuroCScore = it.data.neuroScore.cScore
                    val neuroPScore = it.data.neuroScore.pScore
                    val neuroIScore = it.data.neuroScore.iScore

                    CGraph(neuroCScore)
                    PGraph(neuroPScore)
                    IGraph(neuroIScore)
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


    private fun IGraph(IScore: String?) {
        val plot_i11_neuro_user = view!!.findViewById<ImageView>(R.id.plot_i11_neuro_userPartnerProfileNeuroFragment)
        val plot_i12_neuro_user = view!!.findViewById<ImageView>(R.id.plot_i12_neuro_userPartnerProfileNeuroFragment)
        val plot_i21_neuro_user = view!!.findViewById<ImageView>(R.id.plot_i21_neuro_userPartnerProfileNeuroFragment)
        val plot_i22_neuro_user = view!!.findViewById<ImageView>(R.id.plot_i22_neuro_userPartnerProfileNeuroFragment)
        if (IScore != null) {
            when (IScore) {
                "I11" -> plot_i11_neuro_user.visibility = View.VISIBLE
                "I12" -> plot_i12_neuro_user.visibility = View.VISIBLE
                "I21" -> plot_i21_neuro_user.visibility = View.VISIBLE
                "I22" -> plot_i22_neuro_user.visibility = View.VISIBLE
                else -> Log.d("iscore", IScore)
            }

        }else Toast.makeText(activity, "isocre is null", Toast.LENGTH_SHORT).show()
    }

    private fun PGraph(PScore: String?) {
        val plot_p11_neuro_user = view!!.findViewById<ImageView>(R.id.plot_p11_neuro_userPartnerProfileNeuroFragment)
        val plot_p12_neuro_user = view!!.findViewById<ImageView>(R.id.plot_p12_neuro_userPartnerProfileNeuroFragment)
        val plot_p21_neuro_user = view!!.findViewById<ImageView>(R.id.plot_p21_neuro_userPartnerProfileNeuroFragment)
        val plot_p22_neuro_user = view!!.findViewById<ImageView>(R.id.plot_p22_neuro_userPartnerProfileNeuroFragment)
        if (PScore != null) {
            when (PScore) {
                "P11" -> plot_p11_neuro_user.visibility = View.VISIBLE
                "P12" -> plot_p12_neuro_user.visibility = View.VISIBLE
                "P21" -> plot_p21_neuro_user.visibility = View.VISIBLE
                "P22" -> plot_p22_neuro_user.visibility = View.VISIBLE
                else -> Log.d("iscore", PScore)
            }

        }else Toast.makeText(activity, "psocre is null", Toast.LENGTH_SHORT).show()

    }

    private fun CGraph(Cscore: String?) {
        val plot_c11_neuro_user = view!!.findViewById<ImageView>(R.id.plot_c11_neuro_userPartnerProfileNeuroFragment)
        val plot_c12_neuro_user = view!!.findViewById<ImageView>(R.id.plot_c12_neuro_userPartnerProfileNeuroFragment)
        val plot_c21_neuro_user = view!!.findViewById<ImageView>(R.id.plot_c21_neuro_userPartnerProfileNeuroFragment)
        val plot_c22_neuro_user = view!!.findViewById<ImageView>(R.id.plot_c22_neuro_userPartnerProfileNeuroFragment)
        if (Cscore != null) {
            when (Cscore) {
                "C11" -> plot_c11_neuro_user.visibility = View.VISIBLE
                "C12" -> plot_c12_neuro_user.visibility = View.VISIBLE
                "C21" -> plot_c21_neuro_user.visibility = View.VISIBLE
                "C22" -> plot_c22_neuro_user.visibility = View.VISIBLE
                else -> Log.d("iscore", Cscore)
            }

        }else Toast.makeText(activity, "csocre is null", Toast.LENGTH_SHORT).show()
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
