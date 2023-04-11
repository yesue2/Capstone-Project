package com.example.myapplication

import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class BrandRef {
    companion object {
        private val database = Firebase.database

        val brandRef = database.getReference("Brand")
    }
}