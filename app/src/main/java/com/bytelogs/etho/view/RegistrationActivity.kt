package com.bytelogs.etho.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bytelogs.etho.AppConstants
import com.bytelogs.etho.BaseActivity
import com.bytelogs.etho.R
import com.bytelogs.etho.viewmodel.RegisterViewModel
import kotlinx.android.synthetic.main.activity_register.*

class RegistrationActivity : BaseActivity(),View.OnClickListener {


    private lateinit var registerViewModel:RegisterViewModel
    private lateinit var email:String
    private lateinit var password:String
    private lateinit var rePassword:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        registerViewModel = ViewModelProviders.of(this).get(RegisterViewModel::class.java)
        btRegister.setOnClickListener(this)
        btLogin.setOnClickListener(this)

    }

    override fun onStart() {
        super.onStart()
        if(registerViewModel.getFirebaseUser() !=null){
            startActivity(Intent(this,MainActivity::class.java))
            finish()
        }
    }
    override fun onClick(view: View?) {
        email = etEmail.text.toString()
        password = etPassword.text.toString()
        rePassword = etRepassword.text.toString()
        when(view?.id ){
            R.id.btRegister -> handleOnClickRegister()

            R.id.btLogin -> moveToLogin()
        }

    }

    private fun moveToLogin(){
        val intent = Intent(this,LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun handleOnClickRegister(){
        progressBar.visibility = View.VISIBLE
        registerViewModel.onClickRegister(email,password,rePassword).observe(this,
            Observer {
                progressBar.visibility = View.GONE
                when(it){
                    AppConstants.INVALIDEMAIL -> toast("Invalid email")
                    AppConstants.PASSWORDEMPTY -> toast("Password is Empty")
                    AppConstants.MISMATCHPASSWORD -> toast("Passwords are not same")
                    AppConstants.EMAILEMPTY -> toast("Email is empty")
                    AppConstants.REPASSWORDEMPTY -> toast("Verification password is empty")
                    AppConstants.INVALIDPASSWORD -> toast("Invalid password")
                    AppConstants.SUCCESS -> onSuccessVerificationCredentials()

                }

            })
    }
    private fun onSuccessVerificationCredentials(){
        registerViewModel.createAccount(email,password).observe(this, Observer {
            when(it){
                AppConstants.SUCCESS -> {
                    toast("Account created successfully")
                    val intent = Intent(this,ProfileActivity::class.java)
                    startActivity(intent)

                }
                AppConstants.FAILURE -> toast("Sign up failed")
            }
        })
    }
}
