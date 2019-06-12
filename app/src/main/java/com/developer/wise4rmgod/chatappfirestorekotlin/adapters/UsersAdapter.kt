package com.developer.wise4rmgod.chatappfirestorekotlin.adapters


import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.developer.wise4rmgod.chatappfirestorekotlin.MessageActivity
import com.developer.wise4rmgod.chatappfirestorekotlin.R
import com.developer.wise4rmgod.chatappfirestorekotlin.model.UsersClass
import com.developer.wise4rmgod.chatappfirestorekotlin.ui.main.HomeActivity

class UsersAdapter (private val userList: ArrayList<UsersClass>, private val mContext: Context)
    : RecyclerView.Adapter<UsersAdapter.ViewHolder>()  {

    //this method is returning the view for each item in the list
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.listusers, parent, false)
        return ViewHolder(v)
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

        fun bindItems(user: UsersClass) {
            //  val title = itemView.findViewById(R.id.post) as TextView
            val username = itemView.findViewById(R.id.username) as TextView

            username.text = user.username

            itemView.setOnClickListener {
                var ty = Intent(itemView.context, MessageActivity::class.java)
                ty.putExtra("id", user.id)
                itemView.context.startActivity(ty)
            }



        }
    }
}