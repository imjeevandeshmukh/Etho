package com.bytelogs.obvious.injection

import com.bytelogs.etho.view.LoginActivity
import com.bytelogs.etho.view.MainActivity
import com.bytelogs.etho.view.ProfileActivity
import com.bytelogs.etho.view.RegistrationActivity
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(modules = [AppModule::class,FirebaseModule::class])
interface AppComponent {
    fun inject(activity: MainActivity)
    fun inject(activity: LoginActivity)
    fun inject(activity: ProfileActivity)
    fun inject(activity: RegistrationActivity)
    @Component.Builder
    interface Builder {
        fun build(): AppComponent

        fun appModule(appModule: AppModule):Builder

        fun networkModule(networkModule: FirebaseModule): Builder
    }
}