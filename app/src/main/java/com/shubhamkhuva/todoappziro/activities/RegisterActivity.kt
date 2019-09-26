package com.shubhamkhuva.todoappziro.activities

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseUser
import com.shubhamkhuva.todoappziro.R
import com.shubhamkhuva.todoappziro.core.registration.RegistrationContract
import com.shubhamkhuva.todoappziro.core.registration.RegistrationPresenter

class RegisterActivity:AppCompatActivity(),View.OnClickListener, RegistrationContract.View {

    internal lateinit var tvLogin: TextView
    internal lateinit var btnRegistration: Button
    internal lateinit var edtEmail: EditText
    internal lateinit var edtPassword:EditText
    private var mRegisterPresenter: RegistrationPresenter? = null
    internal lateinit var mPrgressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        initViews()

    }

    private fun initViews() {
        btnRegistration = findViewById(R.id.btn_register) as Button
        btnRegistration.setOnClickListener(this)
        tvLogin = findViewById(R.id.tv_login) as TextView
        tvLogin.setOnClickListener(this)
        edtEmail = findViewById(R.id.email_register) as EditText
        edtPassword = findViewById(R.id.password_register) as EditText

        mRegisterPresenter = RegistrationPresenter(this)

        mPrgressDialog = ProgressDialog(this)
        mPrgressDialog.setMessage("Please wait, Adding profile to database.")
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.btn_register -> checkRegistrationDetails()
            R.id.tv_login -> moveToLoginActivity()
        }
    }

    private fun moveToLoginActivity() {
        val intent = Intent(applicationContext, LoginActivity::class.java)
        startActivity(intent)
    }

    private fun checkRegistrationDetails() {
        if (!TextUtils.isEmpty(edtEmail.text.toString()) && !TextUtils.isEmpty(edtPassword.text.toString())) {
            initLogin(edtEmail.text.toString(), edtPassword.text.toString())
        } else {
            if (TextUtils.isEmpty(edtEmail.text.toString())) {
                edtEmail.error = "Please enter a valid email"
            }
            if (TextUtils.isEmpty(edtPassword.text.toString())) {
                edtPassword.error = "Please enter password"
            }
        }
    }

    private fun initLogin(email: String, password: String) {
        mPrgressDialog.show()
        mRegisterPresenter!!.register(this, email, password)
    }

    override fun onRegistrationSuccess(firebaseUser: FirebaseUser) {
        mPrgressDialog.dismiss()
        Toast.makeText(applicationContext, "Successfully Registered", Toast.LENGTH_SHORT).show()
    }

    override fun onRegistrationFailure(message: String) {
        mPrgressDialog.dismiss()
        Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
    }
}