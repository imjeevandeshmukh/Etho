package com.bytelogs.etho.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.bytelogs.etho.AppConstants
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class FirebaseRepository {

    private val auth: FirebaseAuth
    private  val signInStatus = MutableLiveData<Int>()
    private  val createUserStatus = MutableLiveData<Int>()
    init {
        auth = FirebaseAuth.getInstance()
    }

    fun signInEmailPassword(email:String,password:String):MutableLiveData<Int>{
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(OnCompleteListener {
            if (it.isSuccessful) {
                 signInStatus.postValue(AppConstants.SUCCESS)

            } else {
                signInStatus.postValue(AppConstants.FAILURE)
            }

        })
        return signInStatus
    }
    fun getUser():FirebaseUser{
        return auth.currentUser!!
    }
    fun createUserWithEmailPassword(email: String,password: String):MutableLiveData<Int>{
        auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(OnCompleteListener {
            if(it.isSuccessful){
                createUserStatus.value = AppConstants.SUCCESS
            } else{
                createUserStatus.value = AppConstants.FAILURE
            }
        })
        return createUserStatus
    }
}