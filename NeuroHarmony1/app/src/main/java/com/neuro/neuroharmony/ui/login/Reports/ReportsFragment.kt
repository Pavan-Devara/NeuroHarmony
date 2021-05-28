package com.neuro.neuroharmony.ui.login.Reports


import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.facebook.appevents.codeless.internal.ViewHierarchy.setOnClickListener
import com.google.gson.Gson

import com.neuro.neuroharmony.R
import com.neuro.neuroharmony.data.model.ReportsNew.NeuroReports.NeuroDataKeys
import com.neuro.neuroharmony.ui.login.CircleTransform
import com.neuro.neuroharmony.utils.PrefsHelper
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_reports.*
import kotlinx.android.synthetic.main.fragment_reports_core_belief_distance.*

/**
 * A simple [Fragment] subclass.
 */
class ReportsFragment(val position: Int, val neuro_desire_val: Int ,val neuroReport: String?) : Fragment() {

    var gson = Gson()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_reports, container, false)

        return view
    }


    @SuppressLint("WrongConstant")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        progressBar_reports.max = 5
        progressBar_reports.progress = 5
        progressBar_reports.isClickable=false



        val data = gson.fromJson<NeuroDataKeys>(neuroReport, NeuroDataKeys::class.java)

        val recyclerView = view!!.findViewById(R.id.reports_recycler_view) as RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(activity, LinearLayout.VERTICAL, false)

        rb_reports.visibility = View.GONE

        val self_pic = PrefsHelper().getPref<String>("user_pic")

        if (position == 0) {
            heading_reports.text = "Overview"
            reports_description.text = data.overview_header

            if (self_pic != ""){
                Picasso.get()
                    .load(self_pic)
                    .resize(600,600)
                    .transform(CircleTransform())
                    .placeholder(R.mipmap.profile_pic_placeholder)
                    .centerCrop()
                    .into(reports_fragment_image)
            }
        }
        else if (position == 1){
            heading_reports.text = "Worldview"
            reports_description.text = data.description.worldview.footer
            val dataKey = ArrayList<Reports.DataKeys>()
            for (i in 0 until data.description.worldview.score_data.size)
            {
                dataKey.add(Reports.DataKeys(data.description.worldview.score_data[i].text))
            }
            val adapter_recycler = ReportsNeuroRecyclerViewAdapter(dataKey)
            recyclerView.adapter = adapter_recycler
            adapter_recycler.notifyDataSetChanged()

            if (self_pic != ""){
                Picasso.get()
                    .load(self_pic)
                    .resize(600,600)
                    .transform(CircleTransform())
                    .placeholder(R.mipmap.profile_pic_placeholder)
                    .centerCrop()
                    .into(reports_fragment_image)
            }

        }
        else if (position ==2){
            heading_reports.text = "Functional Approach"
            reports_description.text = data.description.functional_approach.footer
            val dataKey = ArrayList<Reports.DataKeys>()
            for (i in 0 until data.description.functional_approach.score_data.size)
            {
                dataKey.add(Reports.DataKeys(data.description.functional_approach.score_data[i].text))
            }
            val adapter_recycler = ReportsNeuroRecyclerViewAdapter(dataKey)
            recyclerView.adapter = adapter_recycler
            adapter_recycler.notifyDataSetChanged()

            if (self_pic != ""){
                Picasso.get()
                    .load(self_pic)
                    .resize(600,600)
                    .transform(CircleTransform())
                    .placeholder(R.mipmap.profile_pic_placeholder)
                    .centerCrop()
                    .into(reports_fragment_image)
            }
        }
        else if (position == 3){
            heading_reports.text = "Perception"
            reports_description.text = data.description.perception.footer
            val dataKey = ArrayList<Reports.DataKeys>()
            for (i in 0 until data.description.perception.score_data.size)
            {
                dataKey.add(Reports.DataKeys(data.description.perception.score_data[i].text))
            }

            val adapter_recycler = ReportsNeuroRecyclerViewAdapter(dataKey)
            recyclerView.adapter = adapter_recycler
            adapter_recycler.notifyDataSetChanged()


            if (self_pic != ""){
                Picasso.get()
                    .load(self_pic)
                    .resize(600,600)
                    .transform(CircleTransform())
                    .placeholder(R.mipmap.profile_pic_placeholder)
                    .centerCrop()
                    .into(reports_fragment_image)
            }
        }
        else if (position == 4){
            rb_reports.visibility = View.VISIBLE
            rb_reports.progress = 10
            if(neuro_desire_val == 1) {
                heading_reports.text = "Overview of what they mean when combined"
            }
            else{
                heading_reports.text = "Relationship Recommendations"
            }
            reports_description.text = data.combined_overview

            if (self_pic != ""){
                Picasso.get()
                    .load(self_pic)
                    .resize(600,600)
                    .transform(CircleTransform())
                    .placeholder(R.mipmap.profile_pic_placeholder)
                    .centerCrop()
                    .into(reports_fragment_image)
            }
        }


    }

}
