package com.shubhamkhuva.todoapp.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import com.shubhamkhuva.todoapp.R
import com.shubhamkhuva.todoapp.utils.GeneralUtils

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