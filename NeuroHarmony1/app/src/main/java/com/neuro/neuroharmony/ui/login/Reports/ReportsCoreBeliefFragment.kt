package com.neuro.neuroharmony.ui.login.Reports


import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson

import com.neuro.neuroharmony.R
import com.neuro.neuroharmony.data.model.ReportsNew.CoreBeliefReports.DataCoreBelief
import com.neuro.neuroharmony.ui.login.CircleTransform
import com.neuro.neuroharmony.utils.PrefsHelper
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_reports.*

/**
 * A simple [Fragment] subclass.
 */
class ReportsCoreBeliefFragment(val position: Int, val report: String?) : Fragment() {
    var gson = Gson()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_reports, container, false)
    }


    @SuppressLint("WrongConstant")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        progressBar_reports.max = 5
        progressBar_reports.progress = 5

        val data = gson.fromJson<DataCoreBelief>(report, DataCoreBelief::class.java)

        val recyclerView = view!!.findViewById(R.id.reports_recycler_view) as RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(activity, LinearLayout.VERTICAL, false)

        rb_reports.visibility = View.GONE

        val self_pic = PrefsHelper().getPref<String>("user_pic")

        if (position == 0) {
            heading_reports.text = "Overview of Core Beliefs"
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
            heading_reports.text = data.name
            reports_description.visibility = View.GONE
            val dataKey = ArrayList<Reports.DataKeys>()
            for (i in 0 until data.description.score_description.size)
            {
                dataKey.add(Reports.DataKeys(data.description.score_description[i].text))
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
            heading_reports.text = "Do's"
            reports_description.visibility = View.GONE
            val dataKey = ArrayList<Reports.DataKeys>()
            for (i in 0 until data.description.`do`.size)
            {
                dataKey.add(Reports.DataKeys(data.description.`do`[i].text))
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
            heading_reports.text = "Dont's"
            reports_description.visibility = View.GONE
            val dataKey = ArrayList<Reports.DataKeys>()
            for (i in 0 until data.description.dont.size)
            {
                dataKey.add(Reports.DataKeys(data.description.dont[i].text))
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
            heading_reports.text = "Focus of Energy"
            reports_description.visibility = View.GONE
            val dataKey = ArrayList<Reports.DataKeys>()
            for (i in 0 until data.description.attention_energy.size)
            {
                dataKey.add(Reports.DataKeys(data.description.attention_energy[i].text))
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
        else if (position == 5){
            heading_reports.text = "Focus of Attention"
            reports_description.visibility = View.GONE
            val dataKey = ArrayList<Reports.DataKeys>()
            for (i in 0 until data.description.attention_focus.size)
            {
                dataKey.add(Reports.DataKeys(data.description.attention_focus[i].text))
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
        else if (position == 6){
            heading_reports.text = "Preferred Communication Style"
            reports_description.visibility = View.GONE
            val dataKey = ArrayList<Reports.DataKeys>()
            for (i in 0 until data.description.communication_style.size)
            {
                dataKey.add(Reports.DataKeys(data.description.communication_style[i].text))
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
    }

}
