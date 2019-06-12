package com.developer.wise4rmgod.chatappfirestorekotlin

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import com.developer.wise4rmgod.chatappfirestorekotlin.ui.main.HomeActivity
import com.developer.wise4rmgod.chatappfirestorekotlin.ui.main.SectionsPagerAdapter
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val sectionsPagerAdapter = SectionsPagerAdapter( supportFragmentManager)
        sectionsPagerAdapter.addFragment(LoginFragment(), "Login")
        sectionsPagerAdapter.addFragment(RegisterFragment(), "Register")
        val viewPager: ViewPager = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        tabs.setupWithViewPager(viewPager)


    }
}