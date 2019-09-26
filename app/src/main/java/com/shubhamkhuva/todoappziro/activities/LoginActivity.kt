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
import com.shubhamkhuva.todoappziro.R
import com.shubhamkhuva.todoappziro.core.login.LoginContract
import com.shubhamkhuva.todoappziro.core.login.LoginPresenter
import com.shubhamkhuva.todoappziro.utils.GeneralUtils

class LoginActivity: AppCompatActivity(),View.OnClickListener, LoginContract.View {
    internal lateinit var btnLogin: Button
    internal lateinit var tvRegister: TextView
    internal lateinit var edtEmail: EditText
    internal lateinit var edtPassword:EditText
    private var mLoginPresenter: LoginPresenter? = null
    internal lateinit var mProgressDialog: ProgressDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        initViews()

    }
    private fun initViews() {
        btnLogin = findViewById(R.id.button_login) as Button
        btnLogin.setOnClickListener(this)
        tvRegister = findViewById(R.id.tv_register) as TextView
        tvRegister.setOnClickListener(this)
        edtEmail = findViewById(R.id.email_login) as EditText
        edtPassword = findViewById(R.id.password_login) as EditText

        mLoginPresenter = LoginPresenter(this)

        mProgressDialog = ProgressDialog(this)
        mProgressDialog.setMessage("Please wait, Logging in..")
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.button_login -> checkLoginDetails()
            R.id.tv_register -> moveToRegisterActivity()
        }
    }

    private fun moveToRegisterActivity() {
        val intent = Intent(applicationContext, RegisterActivity::class.java)
        startActivity(intent)
    }
    private fun moveHome() {
        val intent = Intent(applicationContext, HomeActivity::class.java)
        startActivity(intent)
    }

    private fun checkLoginDetails() {
        if (!TextUtils.isEmpty(edtEmail.text.toString()) && !TextUtils.isEmpty(edtPassword.text.toString())) {
            if(GeneralUtils().isValidEmailId(edtEmail.text.toString())) {
                initLogin(edtEmail.text.toString(), edtPassword.text.toString())
            }else {
                edtEmail.error = "Please enter a valid email"
            }
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
        mProgressDialog.show()
        mLoginPresenter!!.login(this, email, password)
    }

    override fun onLoginSuccess(message: String) {
        mProgressDialog.dismiss()
        GeneralUtils().saveToSharedPerfBoolean(this,"login",true);
        Toast.makeText(applicationContext, "Successfully Logged in", Toast.LENGTH_SHORT).show()
        moveHome();
    }

    override fun onLoginFailure(message: String) {
        mProgressDialog.dismiss()
        Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
    }
}