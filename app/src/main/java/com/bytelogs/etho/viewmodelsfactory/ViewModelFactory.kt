package com.bytelogs.etho.viewmodelsfactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bytelogs.etho.repository.FirebaseRepository
import com.bytelogs.etho.viewmodel.LoginViewModel
import com.bytelogs.etho.viewmodel.ProfileViewModel
import com.bytelogs.etho.viewmodel.RegisterViewModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton

class ViewModelFactory @Inject constructor(val firebaseRepository: FirebaseRepository): ViewModelProvider.Factory{


    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return  LoginViewModel(firebaseRepository) as T
        } else if (modelClass.isAssignableFrom(RegisterViewModel::class.java)) {
            return  RegisterViewModel(firebaseRepository) as T
        }
        else if (modelClass.isAssignableFrom(ProfileViewModel::class.java)) {
            return  ProfileViewModel(firebaseRepository) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")

    }
}