package com.shubhamkhuva.todoappziro.core.login

import android.app.Activity
import com.google.firebase.auth.FirebaseAuth

class LoginInteractor(private val mOnLoginListener: LoginContract.onLoginListener) :
    LoginContract.Intractor {
    override fun performFirebaseLogin(activity: Activity, email: String, password: String) {
        FirebaseAuth.getInstance()
            .signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    mOnLoginListener.onSuccess(task.result!!.toString())
                } else {
                    mOnLoginListener.onFailure(task.exception!!.toString())
                }
            }
    }
}
