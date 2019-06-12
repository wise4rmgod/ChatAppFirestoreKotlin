package com.developer.wise4rmgod.chatappfirestorekotlin


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.developer.wise4rmgod.chatappfirestorekotlin.adapters.UsersAdapter
import com.developer.wise4rmgod.chatappfirestorekotlin.model.UsersClass
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.EventListener
import java.text.SimpleDateFormat
import java.util.*


class UsersFragment : Fragment() {
    lateinit var listrecyclerview: RecyclerView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
       val view =inflater.inflate(R.layout.fragment_users, container, false)

        listrecyclerview = view.findViewById(R.id.usersrecyclerview)
        val db = FirebaseFirestore.getInstance()
        val mLayoutManager = LinearLayoutManager(activity)
        listrecyclerview.layoutManager = mLayoutManager
        listrecyclerview.itemAnimator = DefaultItemAnimator()

        val date = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
        Toast.makeText(activity, date, Toast.LENGTH_SHORT).show()

        db.collection("Chatappusers")
            .addSnapshotListener(EventListener(function = { documentSnapshots, e ->
                if (e != null) {
                    // Log.e("TAG", "Listen failed!", e)
                    return@EventListener
                }

                val users = ArrayList<UsersClass>()
                for (dc in documentSnapshots!!) {
                    if (dc["id"] == null ) {
                        Toast.makeText(activity, "Message null", Toast.LENGTH_SHORT).show()

                    } else {
                        //   listrentprogress.visibility= View.GONE
                        users.add(

                            UsersClass(
                                dc.getString("id")!!,
                                dc.getString("email")!!,
                                dc.getString("username")!!)

                        )

                        val adapter = UsersAdapter(users, view.context)
                        //now adding the adapter to recyclerview
                        listrecyclerview.adapter = adapter
                        adapter.notifyDataSetChanged()
                        //  Toast.makeText(applicationContext,dc.document.getString("email"), Toast.LENGTH_SHORT).show()
                    }

                }
                //creating our adapter
            }))


        return view
    }

}
