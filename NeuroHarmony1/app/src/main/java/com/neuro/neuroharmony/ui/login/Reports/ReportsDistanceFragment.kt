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
import com.neuro.neuroharmony.data.model.ReportsNew.MatchReports.DataMatchedReports
import kotlinx.android.synthetic.main.fragment_reports.*
import kotlinx.android.synthetic.main.fragment_reports_core_belief_distance.*

/**
 * A simple [Fragment] subclass.
 */
class ReportsDistanceFragment(val position: Int, val report: String?) : Fragment() {

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

        val data = gson.fromJson<DataMatchedReports>(report, DataMatchedReports::class.java)

        rb_reports.visibility = View.GONE

        if (position == 0) {
            heading_reports.text = "Overview"
            reports_description.text = data.overview_header
            reports_fragment_image.setImageResource(R.drawable.distanceoverview)
        }

        else if (position == 1){
            heading_reports.text = "Worldview Alignment"
            reports_description.text = data.description.worldview.alignment
            reports_recycler_view.visibility = View.GONE
            reports_fragment_image.setImageResource(R.drawable.distanceworldviewalignment)
        }


        else if (position ==2){
            heading_reports.text = "Worldview Challenges"
            reports_description.text = data.description.worldview.challenge
            reports_recycler_view.visibility = View.GONE
            reports_fragment_image.setImageResource(R.drawable.distanceworldviewchallenges)
        }


        else if (position == 3){
            heading_reports.text = "Functional Alignment"
            reports_description.text = data.description.functional_approach.alignment
            reports_recycler_view.visibility = View.GONE
            reports_fragment_image.setImageResource(R.drawable.distancefunctionalalignment)
        }


        else if (position == 4){
            heading_reports.text = "Functional Challenges"
            reports_description.text = data.description.functional_approach.challenge
            reports_recycler_view.visibility = View.GONE
            reports_fragment_image.setImageResource(R.drawable.distancefunctionalchallenges)
        }


        else if (position == 5){
            heading_reports.text = "Perception Alignment"
            reports_description.text = data.description.perception.alignment
            reports_recycler_view.visibility = View.GONE
            reports_fragment_image.setImageResource(R.drawable.distanceperceptionalignment)
        }


        else if (position == 6){
            heading_reports.text = "Perception Challenges"
            reports_description.text = data.description.perception.challenge
            reports_recycler_view.visibility = View.GONE
            reports_fragment_image.setImageResource(R.drawable.distanceperceptionchallenges)
        }

        else if (position == 7){
            rb_reports.visibility = View.VISIBLE
            rb_reports.progress = 10
            heading_reports.text = "Relationship Recommendations"
            reports_description.text = data.overview_footer
            reports_recycler_view.visibility = View.GONE
            reports_fragment_image.setImageResource(R.drawable.distancerecomendations)
        }

    }

}
