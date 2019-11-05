package com.bytelogs.obvious.injection


import com.bytelogs.etho.repository.FirebaseRepository
import com.bytelogs.etho.viewmodelsfactory.ViewModelFactory
import dagger.Module
import dagger.Provides
import dagger.Reusable


@Suppress("unused")
@Module
class FirebaseModule{

    @Provides
    @Reusable
     fun provideFirebaseRepository():FirebaseRepository{
        return FirebaseRepository()
    }
    @Provides
    @Reusable
    fun provideViewModelFactory(firebaseRepository: FirebaseRepository):ViewModelFactory{
        return ViewModelFactory(firebaseRepository)
    }









}