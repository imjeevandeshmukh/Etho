package com.bytelogs.etho

import android.content.Context
import android.text.TextUtils
import android.util.Patterns
import android.widget.Toast

object AppUtils{

    fun isValidEmail(email:String):Boolean{
        return (!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches())
    }
}