package com.bytelogs.etho.model




data class User(var name:String,var mobile:String,var age:String,var occupation :String,var profileImgUrl:String){

    constructor() : this("", "", "","","")
}

