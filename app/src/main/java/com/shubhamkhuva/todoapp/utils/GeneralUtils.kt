package com.shubhamkhuva.todoapp.utils

import android.app.Activity
import android.content.SharedPreferences
import java.util.regex.Pattern

class GeneralUtils {
    private lateinit var sharedPref: SharedPreferences
    private var PRIVATE_MODE = 0
    private val PREF_NAME = "ToDoApp"

    fun isValidEmailId(emailId:String):Boolean{
        return Pattern.compile("^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$").matcher(emailId).matches()
    }
    fun saveToSharedPerfBoolean(activity:Activity,key:String,value:Boolean){
        sharedPref = activity.getSharedPreferences(PREF_NAME, PRIVATE_MODE)
        val editor = sharedPref.edit()
        editor.putBoolean(key,value)
        editor.apply()
    }
    fun getFromSharedPerfBoolean(activity:Activity,key:String):Boolean{
        sharedPref = activity.getSharedPreferences(PREF_NAME, PRIVATE_MODE)
        return sharedPref.getBoolean(key,false)
    }
}