package com.example.myapplication

class User {
    var nickname:String? = ""
    var uid:String? = ""

    constructor(uid:String?,nickname:String?){
        this.uid = uid
        this.nickname = nickname
    }
}