package com.bytelogs.etho.view

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.ComponentNameMatchers.hasClassName
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.intent.rule.IntentsTestRule
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.filters.LargeTest
import androidx.test.runner.AndroidJUnit4
import com.bytelogs.etho.R
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
@LargeTest
class RegistrationActivityTest {

    //Run all test cases on emulator so that animation are off.

    lateinit var email:String
    lateinit var password:String
    lateinit var rePassword:String

    @get:Rule
    var activityRule: IntentsTestRule<RegistrationActivity> = IntentsTestRule(RegistrationActivity::class.java)

    @Before
    fun setUp() {
        //Please change email every time you run test
        email = "jeevand@outlook.com"
        password = "12345678"
        rePassword = "12345678"


    }

    @Test
    fun givenCredsWhenRegistrationShouldOpenProfileActivity() {
        onView(withId(R.id.etEmail)).perform(typeText(email), closeSoftKeyboard())
        onView(withId(R.id.etPassword)).perform(typeText(password), closeSoftKeyboard())
        onView(withId(R.id.etRepassword)).perform(typeText(rePassword), closeSoftKeyboard())
        onView(withId(R.id.btRegister)).perform(click())

        Thread.sleep(10000)

        intended(hasComponent(hasClassName(ProfileActivity::class.java!!.name)))

    }
    @Before
    fun tearDown(){
    }




}