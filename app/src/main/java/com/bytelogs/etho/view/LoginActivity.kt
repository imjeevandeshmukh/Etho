package com.bytelogs.etho.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bytelogs.etho.AppConstants
import com.bytelogs.etho.BaseActivity
import com.bytelogs.etho.R
import com.bytelogs.etho.viewmodel.LoginViewModel
import com.bytelogs.etho.viewmodel.RegisterViewModel
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : BaseActivity(), View.OnClickListener {

    private lateinit var email: String
    private lateinit var password: String
    private lateinit var loginViewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        btLogin.setOnClickListener(this)
        btRegister.setOnClickListener(this)
        loginViewModel = ViewModelProviders.of(this).get(LoginViewModel::class.java)


    }

    override fun onStart() {
        super.onStart()
        if(loginViewModel.getFirebaseUser() != null){
            moveToMain()
        }

    }

    private fun moveToMain() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.btLogin -> onClickLogin()

            R.id.btRegister -> onClickRegister()

        }
    }

    private fun onClickLogin() {
        email = etEmail.text.toString()
        password = etPassword.text.toString()
        loginViewModel.onClickLogin(email, password).observe(this, Observer {
            when (it) {
                AppConstants.EMAILEMPTY -> toast("Email is empty")
                AppConstants.INVALIDEMAIL -> toast("Invalid email")
                AppConstants.PASSWORDEMPTY -> toast("Password is empty")
                AppConstants.SUCCESS -> onSuccessVerificationLoginCred()
            }
        })
    }

    private fun onSuccessVerificationLoginCred() {
        loginViewModel.onLogin(email, password).observe(this, Observer {
            when (it) {
                AppConstants.SUCCESS -> moveToMain()
                AppConstants.FAILURE -> toast("Login failed")
            }
        })
    }

    private fun onClickRegister() {
        startActivity(Intent(this, RegistrationActivity::class.java))
    }


}
