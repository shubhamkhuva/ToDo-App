package com.shubhamkhuva.todoappziro.core.registration

import android.app.Activity
import com.google.firebase.auth.FirebaseUser

interface RegistrationContract {
    interface View {
        fun onRegistrationSuccess(firebaseUser: FirebaseUser)
        fun onRegistrationFailure(message: String)
    }

    interface Presenter {
        fun register(activity: Activity, email: String, password: String)
    }

    interface Intractor {
        fun performFirebaseRegistration(activity: Activity, email: String, password: String)
    }

    interface onRegistrationListener {
        fun onSuccess(firebaseUser: FirebaseUser)
        fun onFailure(message: String)
    }
}
