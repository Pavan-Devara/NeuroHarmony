package com.neuro.neuroharmony.ui.login.Reports


import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RatingBar
import androidx.core.graphics.green
import androidx.core.graphics.luminance
import androidx.core.graphics.red
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.neuro.neuroharmony.R
import com.neuro.neuroharmony.data.model.ReportsNew.CoreBeliefDistanceReports.DataCoreBeliefDistance
import com.neuro.neuroharmony.ui.login.CircleTransform
import com.neuro.neuroharmony.utils.PrefsHelper
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_reports_core_belief_distance.*


/**
 * A simple [Fragment] subclass.
 */
class ReportsCoreBeliefDistanceFragment(val position: Int, val report: String?) : Fragment() {

    var gson = Gson()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_reports_core_belief_distance, container, false)
    }



    @SuppressLint("WrongConstant")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        progressBar_reports_corebelief_distance.max = 5
        progressBar_reports_corebelief_distance.progress = 5

        val data = gson.fromJson<DataCoreBeliefDistance>(report, DataCoreBeliefDistance::class.java)

        val recyclerView = view!!.findViewById(R.id.reports_recycler_view_corebelief_distance) as RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(activity, LinearLayout.VERTICAL, false)

        val recyclerView2 = view!!.findViewById(R.id.reports_recycler_view_corebelief_distance2) as RecyclerView
        recyclerView2.layoutManager = LinearLayoutManager(activity, LinearLayout.VERTICAL, false)

        rb.visibility = View.GONE

        val self_pic = PrefsHelper().getPref<String>("user_pic")
        val partner_pic  = PrefsHelper().getPref<String>("matched_profile_pic")

        if (position == 0) {
            recyclerView.visibility = View.GONE
            recyclerView2.visibility = View.GONE
            reports_heading_corebelief_distance.visibility = View.GONE
            reports_heading_corebelief_distance2.visibility = View.GONE
            heading_reports_corebelief_distance.text = "Overview of Core Belief Match"
            reports_description_corebelief_distance.text = data.overview_header
            reports_fragment_image_corebelief_distnace.setImageResource(R.drawable.cbdistanceoverview)

        }
        else if (position == 1){
            recyclerView2.visibility = View.GONE
            reports_heading_corebelief_distance.visibility = View.GONE
            reports_heading_corebelief_distance2.visibility = View.GONE
            heading_reports_corebelief_distance.text = data.user_sub_group_title
            reports_description_corebelief_distance.text = data.description.user_paragraph
            val dataKey = ArrayList<Reports.DataKeys>()
            for (i in 0 until data.description.user_data.size)
            {
                dataKey.add(Reports.DataKeys(data.description.user_data[i].text))
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
                    .into(reports_fragment_image_corebelief_distnace)
            }

        }
        else if (position ==2){
            recyclerView2.visibility = View.GONE
            reports_heading_corebelief_distance.visibility = View.GONE
            reports_heading_corebelief_distance2.visibility = View.GONE
            heading_reports_corebelief_distance.text = data.partner_sub_group_title
            reports_description_corebelief_distance.text = data.description.partner_paragraph
            val dataKey = ArrayList<Reports.DataKeys>()
            for (i in 0 until data.description.partner_data.size)
            {
                dataKey.add(Reports.DataKeys(data.description.partner_data[i].text))
            }
            val adapter_recycler = ReportsNeuroRecyclerViewAdapter(dataKey)
            recyclerView.adapter = adapter_recycler
            adapter_recycler.notifyDataSetChanged()

            if (partner_pic != ""){
                Picasso.get()
                    .load(partner_pic)
                    .resize(600,600)
                    .transform(CircleTransform())
                    .placeholder(R.mipmap.profile_pic_placeholder)
                    .centerCrop()
                    .into(reports_fragment_image_corebelief_distnace)
            }

        }
        else if (position == 3){
            heading_reports_corebelief_distance.text = "About this match"
            reports_description_corebelief_distance.visibility = View.GONE
            reports_heading_corebelief_distance.text = "Positive Aspects"
            val dataKey = ArrayList<Reports.DataKeys>()
            for (i in 0 until data.description.positive_aspects.size)
            {
                dataKey.add(Reports.DataKeys(data.description.positive_aspects[i].text))
            }

            val adapter_recycler = ReportsNeuroRecyclerViewAdapter(dataKey)
            recyclerView.adapter = adapter_recycler
            adapter_recycler.notifyDataSetChanged()
            reports_fragment_image_corebelief_distnace.setImageResource(R.drawable.cbdistanceaboutthismatch)

            reports_heading_corebelief_distance2.text = "Potential Challenges"
            val dataKey1 = ArrayList<Reports.DataKeys>()
            for (i in 0 until data.description.potential_challenges.size)
            {
                dataKey1.add(Reports.DataKeys(data.description.potential_challenges[i].text))
            }

            val adapter_recycler1 = ReportsNeuroRecyclerViewAdapter(dataKey1)
            recyclerView2.adapter = adapter_recycler1
            adapter_recycler.notifyDataSetChanged()

        }
        else if (position == 4){
            rb.visibility = View.VISIBLE
            rb.progress = 10
            recyclerView.visibility = View.GONE
            recyclerView2.visibility = View.GONE
            reports_heading_corebelief_distance.visibility = View.GONE
            reports_heading_corebelief_distance2.visibility = View.GONE
            heading_reports_corebelief_distance.text = "Relationship Recommendations"
            reports_description_corebelief_distance.text = data.overview_footer
            reports_fragment_image_corebelief_distnace.setImageResource(R.drawable.cbdistancerecomendations)
        }
    }

}
