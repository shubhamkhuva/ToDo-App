package com.shubhamkhuva.todoapp.core.registration

import android.app.Activity
import com.google.firebase.auth.FirebaseUser


class RegistrationPresenter(private val mRegisterView: RegistrationContract.View) : RegistrationContract.Presenter,
    RegistrationContract.onRegistrationListener {
    private val mRegistrationInteractor: RegistrationInteractor

    init {
        mRegistrationInteractor = RegistrationInteractor(this)
    }

    override fun register(activity: Activity, email: String, password: String) {
        mRegistrationInteractor.performFirebaseRegistration(activity, email, password)
    }

    override fun onSuccess(firebaseUser: FirebaseUser) {
        mRegisterView.onRegistrationSuccess(firebaseUser)
    }

    override fun onFailure(message: String) {
        mRegisterView.onRegistrationFailure(message)

    }
}
