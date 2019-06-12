package com.developer.wise4rmgod.chatappfirestorekotlin.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.developer.wise4rmgod.chatappfirestorekotlin.R
import com.developer.wise4rmgod.chatappfirestorekotlin.model.ChatClass
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class MessageAdapter (private val userList: ArrayList<ChatClass>, private val mContext: Context)
    : RecyclerView.Adapter<MessageAdapter.ViewHolder>()  {

    val msg_left = 0
    val msg_right = 1
    private lateinit var mauth: FirebaseAuth
    lateinit var fbuser :FirebaseUser
    lateinit var user: ChatClass


    //this method is returning the view for each item in the list
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        if (viewType == msg_right) {
            val v = LayoutInflater.from(parent.context).inflate(R.layout.chat_item_right, parent, false)
            return ViewHolder(v)
        }
        else{
            val v = LayoutInflater.from(parent.context).inflate(R.layout.chat_item_left, parent, false)
            return ViewHolder(v)
        }
    }

    //this method is binding the data on the list
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.bindItems(userList[position])


    }

    //this method is giving the size of the list
    override fun getItemCount(): Int {
        return userList.size
    }

    //the class is hodling the list view
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindItems(user: ChatClass) {


            //  val title = itemView.findViewById(R.id.post) as TextView
            val showmessage = itemView.findViewById(R.id.showmessage) as TextView
            showmessage.text = user.message


        }
    }

    override fun getItemViewType(position: Int): Int {
        mauth = FirebaseAuth.getInstance()

        if (userList.get(position).sender.equals( mauth.currentUser!!.uid))
        {
            return msg_right
        }
        else{
            return msg_left
        }
       // return super.getItemViewType(position)
    }
}