package com.developer.wise4rmgod.chatappfirestorekotlin


import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.Toast
import com.developer.wise4rmgod.chatappfirestorekotlin.adapters.MessageAdapter
import com.developer.wise4rmgod.chatappfirestorekotlin.model.ChatClass
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_message.*
import java.util.ArrayList
import java.util.HashMap

class MessageActivity : AppCompatActivity() {
    var db = FirebaseFirestore.getInstance()

    lateinit var listrecyclerview: RecyclerView

    private lateinit var mauth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_message)
        setSupportActionBar(messagetoolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.title = ""
        val userid = intent.getStringExtra("id")
        mauth = FirebaseAuth.getInstance()
        listrecyclerview = findViewById(R.id.messagerecyclerview)
        val mLayoutManager = LinearLayoutManager(this)
        listrecyclerview.layoutManager = mLayoutManager
        listrecyclerview.itemAnimator = DefaultItemAnimator()

        db.collection("Chats")
            .addSnapshotListener(EventListener(function = { documentSnapshots, e ->
                if (e != null) {
                    // Log.e("TAG", "Listen failed!", e)
                    return@EventListener
                }

                val users = ArrayList<ChatClass>()

                for (dc in documentSnapshots!!) {
                    if (dc.getString("receiver").equals(mauth.currentUser!!.uid) &&  dc.getString("sender").equals(userid)
                        ||  dc.getString("sender").equals(mauth.currentUser!!.uid) && dc.getString("receiver").equals(userid) ) {
                        users.add(

                            ChatClass(
                                dc.getString("sender")!!,
                                dc.getString("receiver")!!,
                                dc.getString("message")!!)
                        )

                        val adapter = MessageAdapter(users, applicationContext)
                        //now adding the adapter to recyclerview
                        listrecyclerview.adapter = adapter
                        adapter.notifyDataSetChanged()

                    } else {
                        Toast.makeText(this, "Message null", Toast.LENGTH_SHORT).show()

                    }

                }

            }))


        messagetoolbar.setNavigationOnClickListener {
            finish()
        }
        mauth = FirebaseAuth.getInstance()
       // val userid = intent.getStringExtra("id")
        db.collection("Chatappusers").document(userid)
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val doc = task.result
                    val username = doc!!.getString("username")
                    messageusername.text = username

                }
            }


        messagesendbtn.setOnClickListener {
            val messagebox = messagesendbox.text.toString()
            if (messagebox != "") {
                messagesendbox.text = null
                sendmessage(mauth.currentUser!!.uid, userid, messagebox)
            } else {
                Toast.makeText(this, "You cant send an Empty Message", Toast.LENGTH_SHORT).show()
            }
        }

        Toast.makeText(this, mauth.currentUser!!.uid, Toast.LENGTH_SHORT).show()

    }


    fun sendmessage(sender: String, receiver: String, message: String) {


        val reg = HashMap<String, String>()
        reg.put("sender", sender)
        reg.put("receiver", receiver)
        reg.put("message", message)

        db.collection("Chats")
            .add(reg as Map<String, String>)
            .addOnSuccessListener {


            }
            .addOnFailureListener {
                Toast.makeText(this, "Error Saving ", Toast.LENGTH_SHORT).show()
            }

    }
}
