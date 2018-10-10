package com.example.priya.expensa

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val isAppFirstLaunch = SharedPref.getInstance(this).isFirstLaunch()
        val onBoardingIntent = Intent(this, OnBoardingActivity::class.java)
        val signUpIntent = Intent(this, SignUpActivity::class.java)

        if(isAppFirstLaunch){
            startActivity(onBoardingIntent)
        } else {
            startActivity(signUpIntent)
        }
    }
}
