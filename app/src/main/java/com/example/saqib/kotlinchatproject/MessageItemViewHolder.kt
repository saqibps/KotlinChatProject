package com.example.saqib.kotlinchatproject

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView

class MessageItemViewHolder(myView:View): RecyclerView.ViewHolder(myView) {

    val msgsTextView : TextView = itemView.findViewById(R.id.msgTextTv)

    fun bindMsg(msg:MessageItem){
        msgsTextView.text = msg.messageText
    }
}
