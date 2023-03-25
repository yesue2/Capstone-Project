package com.example.myapplication.matching

data class MateData(
    var id: String,
    val userList: MutableList<WaitUserData>
)
