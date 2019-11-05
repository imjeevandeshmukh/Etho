package com.bytelogs.etho.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bytelogs.etho.AppConstants
import com.bytelogs.etho.AppUtils
import com.bytelogs.etho.repository.FirebaseRepository
import com.google.firebase.auth.FirebaseUser
import javax.inject.Inject

class LoginViewModel @Inject constructor(val firebaseRepository: FirebaseRepository) :ViewModel(){
    private val onClickCallback = MutableLiveData<Int>()


     fun onClickLogin(email:String,password:String):LiveData<Int>{
        if(email.isNullOrEmpty()){
            onClickCallback.postValue(AppConstants.EMAILEMPTY)
            return onClickCallback
        }
        if(password.isNullOrEmpty()){
            onClickCallback.postValue(AppConstants.PASSWORDEMPTY)
            return onClickCallback
        }
        if(!AppUtils.isValidEmail(email)){
            onClickCallback.postValue(AppConstants.INVALIDEMAIL)
            return onClickCallback
        }else{
            onClickCallback.postValue(AppConstants.SUCCESS)
            return onClickCallback
        }
    }
    fun onLogin(email: String,password: String): LiveData<Int> {
       return firebaseRepository.signInEmailPassword(email,password)
    }
    fun getFirebaseUser():FirebaseUser?{
        return firebaseRepository?.getUser()
    }


}