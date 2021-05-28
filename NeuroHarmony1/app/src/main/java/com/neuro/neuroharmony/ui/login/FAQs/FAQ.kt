package com.neuro.neuroharmony.ui.login.FAQs

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.neuro.neuroharmony.R
import com.neuro.neuroharmony.ui.login.BaseActivity
import com.neuro.neuroharmony.ui.login.ReligiousInfoViewModel
import com.neuro.neuroharmony.ui.login.ResourceStatus
import com.neuro.neuroharmony.ui.login.StatusType
import kotlinx.android.synthetic.main.activity_faq.*
import org.json.JSONArray

class FAQ : BaseActivity(), AdapterView.OnItemSelectedListener{

    private lateinit var viewModel: ReligiousInfoViewModel
    var incoming_data = ""
    var gson = Gson()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_faq)


        viewModel = ViewModelProviders.of(this)[ReligiousInfoViewModel::class.java]


        viewModel.faqLiveData()

        faq_spinner.setOnItemSelectedListener(this)
        val  list_faqcategories:MutableList<String> = arrayListOf()
        list_faqcategories.add("Select your category")
        setupObservers(list_faqcategories)


        back_faq.setOnClickListener{
            onBackPressed()
        }

    }



    @SuppressLint("WrongConstant")
    private fun main_code(item_id: Int) {
        if (item_id>0){
            val recyclerView = findViewById(R.id.faq_list_recyclerView) as RecyclerView
            recyclerView.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)
            val questionAnswers = ArrayList<FAQdata>()
            val data_faq = JSONArray(incoming_data)
            val data = data_faq.getJSONObject(item_id-1)
            val items_check = data.get("items")
            if (items_check is JSONArray){
                val items = data.getJSONArray("items")
                if (items.length()>0){
                    for (i in 0 until items.length()){
                        questionAnswers.add(FAQdata(items.getJSONObject(i).getString("question"),
                            items.getJSONObject(i).getString("answer")))
                    }
                }
            }
            val adapter_recycler = FAQRecyclerView(questionAnswers)
            recyclerView.adapter = adapter_recycler
            adapter_recycler.notifyDataSetChanged()
        }
    }





    private fun setupObservers(list_faqcategories: MutableList<String>) {
        viewModel.faqResponseLiveData.observe(this, Observer {
            if (it!=null){
                if (it.message=="Success"){
                    incoming_data = gson.toJson(it.data)
                    val data = JSONArray(gson.toJson(it.data))
                    for(i in 0 until data.length()){
                        list_faqcategories.add(data.getJSONObject(i).getString("category_title"))
                    }
                    val adapter = ArrayAdapter(
                        this,
                        R.layout.color_spinner_shaded_layout,
                        list_faqcategories
                    )
                    adapter.setDropDownViewResource(R.layout.spinner_dropdown_layout)
                    faq_spinner.setAdapter(adapter)

                }else{
                    Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                }
            }
        })
        viewModel.faqAPICallStatus.observe(this, Observer {
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
                Toast.makeText(this, "Please try again", Toast.LENGTH_SHORT).show()
                dismissDialog()


            }
            StatusType.LOADING_MORE -> {
                // CommonUtils().showSnackbar(binding.root, "Loading more..")
            }
            StatusType.NO_NETWORK -> {
                Toast.makeText(this, "Please check internet connection", Toast.LENGTH_SHORT).show()

            }
            StatusType.SESSION_EXPIRED -> {
                Toast.makeText(this, "session expired", Toast.LENGTH_SHORT).show()
            }
        }

    }

    override fun onNothingSelected(parent: AdapterView<*>?) {

    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        main_code(faq_spinner.selectedItemId.toInt())
    }
}

data class FAQdata(
    val question: String,
    val answer: String
)
