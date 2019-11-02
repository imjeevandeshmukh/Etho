package com.bytelogs.etho.viewmodel

import android.text.TextUtils
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bytelogs.etho.AppConstants
import com.bytelogs.etho.AppUtils
import com.bytelogs.etho.repository.FirebaseRepository
import com.google.firebase.auth.FirebaseUser


class RegisterViewModel: ViewModel() {
    private val onClickCallback = MutableLiveData<Int>()
    private val firebaseRepository = FirebaseRepository()


    fun onClickRegister(email:String,password:String,rePassword:String):LiveData<Int>{
        if(email.isNullOrEmpty()){
            onClickCallback.postValue(AppConstants.EMAILEMPTY)
            return onClickCallback
        }
        if(password.isNullOrEmpty()){
            onClickCallback.postValue(AppConstants.PASSWORDEMPTY)
            return onClickCallback
        }
        if(rePassword.isNullOrEmpty()){
            onClickCallback.postValue(AppConstants.REPASSWORDEMPTY)
            return onClickCallback
        }
        if (!AppUtils.isValidEmail(email)){
            onClickCallback.postValue(AppConstants.INVALIDEMAIL)
            return onClickCallback
        }
        if(!TextUtils.equals(password,rePassword)){
            onClickCallback.postValue(AppConstants.MISMATCHPASSWORD)
            return onClickCallback
        }else{
            onClickCallback.postValue(AppConstants.SUCCESS)
            return onClickCallback
        }


    }
    fun getFirebaseUser(): FirebaseUser {
        return firebaseRepository.getUser()
    }
    fun createAccount(email: String,password: String):LiveData<Int>{
       return firebaseRepository.createUserWithEmailPassword(email,password)
    }



}