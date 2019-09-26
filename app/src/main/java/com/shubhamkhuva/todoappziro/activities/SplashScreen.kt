package com.shubhamkhuva.todoappziro.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import com.shubhamkhuva.todoappziro.R
import com.shubhamkhuva.todoappziro.activities.HomeActivity
import com.shubhamkhuva.todoappziro.activities.LoginActivity
import com.shubhamkhuva.todoappziro.utils.GeneralUtils

class SplashScreen: Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splashscreen)

        getWindow().setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN)

        Handler().postDelayed({
            if(GeneralUtils().getFromSharedPerfBoolean(this,"login")){
                val intent = Intent(applicationContext, HomeActivity::class.java)
                startActivity(intent)
            }else{
                val intent = Intent(applicationContext, LoginActivity::class.java)
                startActivity(intent)
            }

        }, 1000)

    }

}