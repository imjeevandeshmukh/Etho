package com.bytelogs.etho.viewmodel

import android.net.Uri
import android.text.TextUtils
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bytelogs.etho.AppConstants
import com.bytelogs.etho.model.User
import com.bytelogs.etho.repository.FirebaseRepository

class ProfileViewModel :ViewModel(){

    private val onClickCallback = MutableLiveData<Int>()
    private val firebaseRepository = FirebaseRepository()

    fun onClickSave(user: User):LiveData<Int>{
        if(TextUtils.isEmpty(user.name) && TextUtils.isEmpty(user.age) && TextUtils.isEmpty(user.mobile) && TextUtils.isEmpty(user.occupation)){
            onClickCallback.postValue(AppConstants.FAILURE)
            return onClickCallback
        }else{
            firebaseRepository.saveUserProfileInfo(user)
             onClickCallback.postValue(AppConstants.SUCCESS)
            return onClickCallback
        }

    }
    fun uploadFile(uri: Uri):LiveData<Int>{
        return  firebaseRepository.uploadFile(uri)
    }
    fun getUserDetail():LiveData<User>{
        return firebaseRepository.getUserDetail()
    }

}