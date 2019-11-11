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
class LoginActivityTest {

    //Please clear cache before running the test chances are that you may have loggined in before and activity moves to other screen onStart

    lateinit var email:String
    lateinit var password:String

    @get:Rule
    var activityRule: IntentsTestRule<LoginActivity>
            = IntentsTestRule(LoginActivity::class.java)

    @Before
    fun setUp() {
        email = "jeevandeshmukh@outlook.com"
        password = "12345678"

    }

    @Test
    fun givenCredsWhenLoginShouldOpenMainActivity() {
        onView(withId(R.id.etEmail)).perform(typeText(email), closeSoftKeyboard())
        onView(withId(R.id.etPassword)).perform(typeText(password), closeSoftKeyboard())
        onView(withId(R.id.btLogin)).perform(click())

        Thread.sleep(4000)

        intended(hasComponent(hasClassName(MainActivity::class.java!!.name)))

    }
    @Before
    fun tearDown(){
    }




}