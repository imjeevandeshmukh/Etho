package com.bytelogs.etho.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import androidx.lifecycle.Observer
import com.bytelogs.etho.repository.FirebaseRepository
import junit.framework.Assert.assertNotNull
import junit.framework.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.MockitoAnnotations

@RunWith(JUnit4::class)
class LoginViewModelTest {
    @Rule
    @JvmField
    public var instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    val firebaseRepository=  FirebaseRepository()
    @Mock
    var lifecycleOwner: LifecycleOwner? = null

    lateinit var lifecycle: Lifecycle
    private lateinit var loginViewModel: LoginViewModel
    @Mock
    lateinit var onClickLoginObserver: Observer<Int>

  


    @Before
    @Throws(Exception::class)
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        lifecycle = lifecycleOwner?.let { LifecycleRegistry(it) }!!
        loginViewModel = LoginViewModel(firebaseRepository)
        loginViewModel.onClickLogin("jeevan@some.com","12345678").observeForever(onClickLoginObserver)
    }

    @Test
    fun testNull() {
        assertNotNull(loginViewModel.onClickLogin("jeevan@some.com","12345678"))
        assertTrue(loginViewModel.onClickLogin("jeevan@some.com","12345678").hasObservers())
    }

}