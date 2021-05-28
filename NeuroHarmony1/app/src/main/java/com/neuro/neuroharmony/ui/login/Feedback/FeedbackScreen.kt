package com.neuro.neuroharmony.ui.login.Feedback

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.gson.Gson
import com.neuro.neuroharmony.R
import com.neuro.neuroharmony.ui.login.*
import kotlinx.android.synthetic.main.activity_feedback_screen.*
import org.json.JSONArray

class FeedbackScreen : BaseActivity() {

    private lateinit var viewModel1: FeatureListViewModel
    var gson = Gson()
    var list_feature_id = listOf<String>().toMutableList()
    var jsonArray = ""
    private lateinit var viewModel2: SubmitFeedbackViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feedback_screen)


        feedback_core_match_report3.setOnClickListener {
            onBackPressed()
        }

        viewModel1 = ViewModelProviders.of(this)[FeatureListViewModel::class.java]
        viewModel2 = ViewModelProviders.of(this)[SubmitFeedbackViewModel::class.java]

        setupListeners()
        setupObservers()
        setupListeners2()
        setupObservers2()




        val adapter4 = ArrayAdapter.createFromResource(
            this,
            R.array.feature_array,
            R.layout.color_spinner_layout
        );
        adapter4.setDropDownViewResource(R.layout.spinner_dropdown_layout)
        feedback_spinner.setAdapter(adapter4)



    }

    private fun setupListeners(){
        feedback_spinner.setOnItemClickListener(viewModel1.featurelistvm())

    }

    private fun setupListeners2(){

            submit_feedback.setOnClickListener {

                if(feedback_spinner.selectedItemId.toInt()>0) {
                    if (comment_feedback.text.length >= 3 && comment_feedback.text.length <= 1000) {
                        viewModel2.submitfeedbackvm(
                            list_feature_id[feedback_spinner.selectedItemId.toInt() - 1],
                            comment_feedback.text.trim().toString()
                        )
                    }else{
                        Toast.makeText(this,
                            "Comments must contain a minimum of 3 characters and maximum of 1000 characters",
                            Toast.LENGTH_LONG).show()
                    }
                }
                else{
                    Toast.makeText(this, "Please select a feature from the feature list to proceed!", Toast.LENGTH_LONG).show()
                }
        }




    }



    private  fun setupObservers(){

        viewModel1.loginResponseLiveData.observe(this, Observer {
            if(it!=null){
                if( it.message=="Success"){
                    val jsonArray = gson.toJson(it.data)
                    main_code(jsonArray)

                }
                else{
                    Toast.makeText(this,it.message, Toast.LENGTH_SHORT).show()
                }
            }
        })


        //observe API call status
        viewModel1.loginAPICallStatus.observe(this, Observer {
            processStatus(it)
        })
    }



    private fun processStatus(resource: ResourceStatus) {

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
                Toast.makeText(this, "Please try again. Server error", Toast.LENGTH_SHORT).show()
                dismissDialog()



            }
            StatusType.LOADING_MORE -> {
                // CommonUtils().showSnackbar(binding.root, "Loading more..")
            }
            StatusType.NO_NETWORK -> {
                Toast.makeText(this, "Please check internet connection", Toast.LENGTH_SHORT).show()
            }
            StatusType.SESSION_EXPIRED -> {
                //CommonUtils().showSnackbar(login_button_login.rootView,"session expired")
            }
        }
    }

    private fun main_code(jsonArray: String) {
        val array = JSONArray(jsonArray)


        val array_feature: Array<String> = resources.getStringArray(R.array.feature_array)
        val list_feature = array_feature.toMutableList()

        for (i in 0 until array.length()) {
            val obj = array.getJSONObject(i)
            list_feature.add(obj.getString("title"))
            list_feature_id.add(obj.getString("feature_id"))

        }
        val adapter_feature = ArrayAdapter(
            this,
            R.layout.color_spinner_layout,
            list_feature
        )
        adapter_feature.setDropDownViewResource(R.layout.spinner_dropdown_layout)
        feedback_spinner.setAdapter(adapter_feature)

    }



    private  fun setupObservers2(){

        viewModel2.loginResponseLiveData.observe(this, Observer {
            if(it!=null){
                if( it.message=="Success"){
                    Toast.makeText(this, "Feedback submitted successfully!", Toast.LENGTH_SHORT).show()

                    comment_feedback.setText("")
                    feedback_spinner.setSelection(0)


                }
                else{
                    Toast.makeText(this,"Please add the comment", Toast.LENGTH_SHORT).show()
                }
            }
        })


        //observe API call status
        viewModel2.loginAPICallStatus.observe(this, Observer {
            processStatus2(it)
        })
    }



    private fun processStatus2(resource: ResourceStatus) {

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
                Toast.makeText(this, "Please try again. Server error", Toast.LENGTH_SHORT).show()
                dismissDialog()



            }
            StatusType.LOADING_MORE -> {
                // CommonUtils().showSnackbar(binding.root, "Loading more..")
            }
            StatusType.NO_NETWORK -> {
                Toast.makeText(this, "Please check internet connection", Toast.LENGTH_SHORT).show()
            }
            StatusType.SESSION_EXPIRED -> {
                //CommonUtils().showSnackbar(login_button_login.rootView,"session expired")
            }
        }
    }






}

private fun Spinner.setOnItemClickListener(featurelistvm: Unit) {

}
