package com.example.saqib.kotlinchatproject

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.text.Editable
import android.text.TextWatcher
import android.widget.LinearLayout
import com.example.saqib.kotlinchatproject.R.id.msg_et
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_chat.*

class ChatActivity : AppCompatActivity() {

    lateinit var msgsRef:DatabaseReference
    lateinit var msgsList:ArrayList<MessageItem>
    lateinit var msgsAdapter:MessageAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        msgsRef = FirebaseDatabase.getInstance().getReference("messages")
        msgsList = arrayListOf()
        msgsAdapter = MessageAdapter(msgsList)
        recyclerview.layoutManager = LinearLayoutManager(this)
        recyclerview.adapter = msgsAdapter

        msg_et.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(charSequence: CharSequence, start: Int, before: Int, count: Int) {
                if (charSequence.toString().trim().length > 0) {
                    send_button.isEnabled = true
                } else {
                    send_button.isEnabled = false
                }
            }
        })

        msgsRef.addChildEventListener(object :ChildEventListener {
            override fun onCancelled(p0: DatabaseError?) {}

            override fun onChildMoved(p0: DataSnapshot?, p1: String?) {}

            override fun onChildChanged(p0: DataSnapshot?, p1: String?) {}

            override fun onChildAdded(snapshot: DataSnapshot?, p1: String?) {
                if(snapshot != null) {
                    val msg:MessageItem = snapshot.getValue(MessageItem::class.java)!!
                    msgsList.add(msg)
                    msgsAdapter.notifyDataSetChanged()
                    recyclerview.scrollToPosition(msgsList.size-1)
                }
            }

            override fun onChildRemoved(snapshot: DataSnapshot) {
                if (snapshot != null) {
                    val msg:MessageItem = snapshot.getValue(MessageItem::class.java)!!
                    if (msgsList.contains(msg)){
                        msgsList.remove(msg)
                        msgsAdapter.notifyDataSetChanged()
                    }
                }
            }

        })
        send_button.setOnClickListener() {
            if (!msg_et.text.toString().equals("")) {
                sendMsg(MessageItem(msg_et.text.toString(), FirebaseAuth.getInstance().currentUser?.uid!!))
            }
        }
    }
    fun sendMsg(msg:MessageItem) {
        msgsRef.push().setValue(msg)
        msg_et.setText("")
    }

}