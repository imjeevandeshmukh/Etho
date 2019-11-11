package com.bytelogs.etho

import android.text.TextUtils
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class AppUtilsTest{

    @Test
    fun givenEmptyStringWhenValidEmailShouldReturnFalse(){
        assertFalse(AppUtils.isValidEmail(""))
    }
    @Test
    fun givenEmailStringWhenValidEmailShouldReturnTrue(){
        assertTrue(AppUtils.isValidEmail("someemail@email.com"))
    }
    @Test
    fun givenStringWhenValidEmailShouldReturnFalse(){
        assertFalse(AppUtils.isValidEmail("someemail"))
    }
    @Test
    fun givenWhiteSpaceWhenValidEmailShouldReturnFalse(){
        assertFalse(AppUtils.isValidEmail("  "))
    }
}