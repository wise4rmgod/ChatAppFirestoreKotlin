package com.developer.wise4rmgod.chatappfirestorekotlin

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.developer.wise4rmgod.chatappfirestorekotlin.ui.main.HomeActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import java.util.HashMap


class RegisterFragment : Fragment() {
    private lateinit var mauth: FirebaseAuth
    lateinit var email:EditText
    lateinit var password:EditText
    lateinit var username:EditText
    val db = FirebaseFirestore.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mauth = FirebaseAuth.getInstance()

         val view = inflater.inflate(R.layout.fragment_register, container, false)
         email = view.findViewById(R.id.signupemail) as EditText
         password = view.findViewById(R.id.signuppassword) as EditText
         username = view.findViewById(R.id.username) as EditText
        val registerbtn = view.findViewById(R.id.registerbtn) as Button

          registerbtn.setOnClickListener {
              signup()
          }

        return view
    }


    fun signup(){
        Snackbar.make(this.view!!, "Loading....", Snackbar.LENGTH_LONG)
            .setAction("Action", null).show()
        mauth.createUserWithEmailAndPassword(email.text.toString(), password.text.toString())
            .addOnCompleteListener(this.activity!!, OnCompleteListener<AuthResult> { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    val Userid=mauth.currentUser!!.uid

                    val reg = HashMap<String, String>()
                    reg.put("id", Userid)
                    reg.put("email", mauth.currentUser!!.email!!)
                    reg.put("username", username.text.toString())

                    db.collection("Chatappusers").document(Userid)
                            .set(reg as Map<String, String>)
                        .addOnSuccessListener {

                            Toast.makeText(activity, "Registration Successful ", Toast.LENGTH_SHORT).show()
                            var ty = Intent(activity, HomeActivity::class.java)
                            ty.putExtra("id", mauth.currentUser!!.uid)
                            ty.putExtra("email", mauth.currentUser!!.email)
                            startActivity(ty)

                        }
                        .addOnFailureListener {
                            Toast.makeText(activity, "Error Saving ", Toast.LENGTH_SHORT).show()
                        }


                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(
                        activity, "Authentication failed.",
                        Toast.LENGTH_SHORT
                    ).show()

                }

            })

    }

}
