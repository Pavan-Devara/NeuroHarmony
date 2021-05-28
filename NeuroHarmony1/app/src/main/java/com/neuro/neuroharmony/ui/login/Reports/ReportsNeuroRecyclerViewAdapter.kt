package com.neuro.neuroharmony.ui.login.Reports

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.neuro.neuroharmony.R
import com.neuro.neuroharmony.ui.login.ChatMenu

class ReportsNeuroRecyclerViewAdapter(val dataKey: ArrayList<Reports.DataKeys>) : RecyclerView.Adapter<ReportsNeuroRecyclerViewAdapter.ViewHolder>() {


    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val text = v.findViewById<TextView>(R.id.reports_description_points)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.reports_recyclerview, parent, false )
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return dataKey.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val specs: Reports.DataKeys = dataKey[position]

        holder.text.text = specs.specs
    }

}