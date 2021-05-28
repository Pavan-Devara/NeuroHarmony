package com.neuro.neuroharmony.ui.login

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.neuro.neuroharmony.R

class Core_belief_test_adapter(val userlist1: ArrayList<CoreBeliefIntroScreen.User>) : RecyclerView.Adapter<Core_belief_test_adapter.ViewHolder>(){

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val recyler_view_text = itemView.findViewById(R.id.recycler_view_text) as TextView
        val instruction_number = itemView.findViewById<TextView>(R.id.instruction_number)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.recyclerviewlayout, parent, false )
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return userlist1.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user: CoreBeliefIntroScreen.User = userlist1[position]

        holder.recyler_view_text.text = user.name
        holder.instruction_number.text = user.num

    }

}