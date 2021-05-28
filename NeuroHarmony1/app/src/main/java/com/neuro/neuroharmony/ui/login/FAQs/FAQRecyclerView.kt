package com.neuro.neuroharmony.ui.login.FAQs

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.neuro.neuroharmony.R

class FAQRecyclerView(val questionAnswers: ArrayList<FAQdata>) : RecyclerView.Adapter<FAQRecyclerView.ViewHolder>(){


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val question = itemView.findViewById<TextView>(R.id.faq_question)
        val answer = itemView.findViewById<TextView>(R.id.faq_answer)
        val plus = itemView.findViewById<ImageView>(R.id.faq_plus)
        val minus = itemView.findViewById<ImageView>(R.id.faq_minus)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.faq_recycler_view, parent, false )

        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return questionAnswers.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user: FAQdata = questionAnswers[position]
        holder.setIsRecyclable(false)

        holder.run{
            question.text = user.question
        }

        holder.run {
            answer.text = user.answer
        }

        holder.plus.setOnClickListener{
            holder.plus.visibility = View.INVISIBLE
            holder.answer.visibility = View.VISIBLE
            holder.minus.visibility = View.VISIBLE
        }

        holder.minus.setOnClickListener{
            holder.plus.visibility = View.VISIBLE
            holder.answer.visibility = View.GONE
            holder.minus.visibility = View.INVISIBLE
        }
    }
}