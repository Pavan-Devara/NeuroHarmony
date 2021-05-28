package com.neuro.neuroharmony.ui.login

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.neuro.neuroharmony.R
import com.neuro.neuroharmony.utils.PrefsHelper
import java.util.*


class ChatMessageAdapter(val chatlist:ArrayList<ChatWindow.Chatdetails>): RecyclerView.Adapter<ChatMessageAdapter.ViewHolder>() {


    val MESSAGE_TYPE_LEFT = 0
    val MESSAGE_TYPE_RIGHT = 1

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val displayMessage = itemView.findViewById<TextView>(R.id.text_message)
        val displayTime = itemView.findViewById<TextView>(R.id.timestamp)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        if (viewType==MESSAGE_TYPE_LEFT){
            val v = LayoutInflater.from(parent.context).inflate(R.layout.chat_left_view, parent, false )
            return ViewHolder(v)
        }else{
            val v = LayoutInflater.from(parent.context).inflate(R.layout.chat_right_view, parent, false )
            return ViewHolder(v)
        }
    }

    override fun getItemCount(): Int {
        return chatlist.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val message: ChatWindow.Chatdetails = chatlist[position]
        holder.setIsRecyclable(false)
        holder.displayMessage.text = message.message
        val timestamp = Date(message.timestamp?.seconds as Long*1000)

        if (timestamp.hours>12){
            val time = (timestamp.hours-12).toString() + ":" +
                    timestamp.minutes.toString()+" " +
                    "PM" +" " +
                    NeuroMatchedUsers.Month.values().find {it.number == timestamp.month} + " " +
                    timestamp.date.toString()
            holder.displayTime.setText(time.toString())
        }else{
            val time = timestamp.hours.toString() + ":" +
                    timestamp.minutes.toString()+ " " +
                    "AM"+ " " +
                    NeuroMatchedUsers.Month.values().find {it.number == timestamp.month}+ " " +
                    timestamp.date.toString()
            holder.displayTime.setText(time.toString())
        }

    }

    override fun getItemViewType(position: Int): Int {
        val user = PrefsHelper().getPref<String>("userKey")
        if (user == chatlist.get(position).senderKey){
            return MESSAGE_TYPE_RIGHT
        }else{
            return MESSAGE_TYPE_LEFT
        }
    }
}