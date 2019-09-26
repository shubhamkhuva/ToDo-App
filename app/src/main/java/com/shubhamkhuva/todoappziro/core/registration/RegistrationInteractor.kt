package com.shubhamkhuva.todoappziro.core.registration

import android.app.Activity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth

class RegistrationInteractor(private val mOnRegistrationListener: RegistrationContract.onRegistrationListener) :
    RegistrationContract.Intractor {
    override fun performFirebaseRegistration(activity: Activity, email: String, password: String) {
        FirebaseAuth.getInstance()
            .createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (!task.isSuccessful) {
                    task.exception!!.message?.let { mOnRegistrationListener.onFailure(it) }
                } else {
                    mOnRegistrationListener.onSuccess(task.result!!.user)
                }
            }
    }

    companion object {

        private val TAG = RegistrationInteractor::class.java.simpleName
    }
}
