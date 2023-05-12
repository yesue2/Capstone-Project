package com.example.myapplication

data class MateModel(
    var id: String,
    val userList: MutableList<WaitUserModel>
)
