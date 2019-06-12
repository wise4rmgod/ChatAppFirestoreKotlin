package com.developer.wise4rmgod.chatappfirestorekotlin


import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.developer.wise4rmgod.chatappfirestorekotlin.ui.main.HomeActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth


class LoginFragment : Fragment() {
    private lateinit var mauth: FirebaseAuth
    lateinit var email: EditText
    lateinit var password: EditText
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val firebaseUser = FirebaseAuth.getInstance().currentUser
        if (firebaseUser != null) {
            mauth = FirebaseAuth.getInstance()
            val intent = Intent(activity, HomeActivity::class.java)
            intent.putExtra("id", mauth.currentUser!!.uid)
            startActivity(intent)
            activity!!.finish()
        }
        // Inflate the layout for this fragment
        mauth = FirebaseAuth.getInstance()
        val view =inflater.inflate(R.layout.fragment_login, container, false)
        val loginbtn = view.findViewById<TextView>(R.id.loginbtn)
        email = view.findViewById(R.id.signupemail) as EditText
        password = view.findViewById(R.id.signuppassword) as EditText

        loginbtn.setOnClickListener {

           signin()

        }


        return view
    }



    fun signin(){

        mauth.signInWithEmailAndPassword(email.text.toString(), password.text.toString())
            .addOnCompleteListener(this.activity!!, OnCompleteListener<AuthResult> { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information

                    val user = mauth.currentUser
                    Snackbar.make(this.view!!, "SignIn Successful", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show()
                    var ty = Intent(activity, HomeActivity::class.java)
                    ty.putExtra("id", mauth.currentUser!!.uid)
                    ty.putExtra("email", mauth.currentUser!!.email)
                    startActivity(ty)

                } else {
                    // If sign in fails, display a message to the user.
                    // Log.w(FragmentActivity.TAG, "signInWithEmail:failure", task.exception)
                    Toast.makeText(
                        activity, "Authentication failed.",
                        Toast.LENGTH_SHORT
                    ).show()

                }

            })


    }


}
