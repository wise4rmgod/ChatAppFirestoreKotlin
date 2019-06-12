package com.developer.wise4rmgod.chatappfirestorekotlin.ui.main

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import android.view.Menu
import android.view.MenuItem
import com.developer.wise4rmgod.chatappfirestorekotlin.*
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {
    private lateinit var mauth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        setSupportActionBar(toolbar)
        val homePagerAdapter = HomeViewpagerAdapter( supportFragmentManager)
        homePagerAdapter.addFragment(UsersFragment(), "User")
        homePagerAdapter.addFragment(ChatsFragment(), "Chats")
        val viewPager: ViewPager = findViewById(R.id.view_pager)
        viewPager.adapter = homePagerAdapter
        val tabs: TabLayout = findViewById(R.id.hometabs)
        tabs.setupWithViewPager(viewPager)
        mauth = FirebaseAuth.getInstance()
        var getid =  intent.getStringExtra("id")
        var getemail = intent.getStringExtra("email")

    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_home, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.itemId == R.id.privacy) {

           // val i = Intent(this, PrivacyPolicyActivity::class.java)
            //startActivity(i)
        }



        if (item.itemId == R.id.logout) {
            mauth.signOut()
            val i = Intent(this, MainActivity::class.java)
            startActivity(i)
            finish()

        }

        return super.onOptionsItemSelected(item)
    }




}
