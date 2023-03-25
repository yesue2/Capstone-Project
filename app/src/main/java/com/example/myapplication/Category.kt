package com.example.myapplication

class Category {
    var filename:String? = ""
    var name:String? = ""
    constructor(filename:String?, name:String?){
        this.filename = filename
        this.name = name
    }

    override fun toString(): String{
//        return "Category{" +
//                "filename='" + filename + '\'' +
//                ", name='" + name + '\'' +
//                '}'
        return filename+","+name
    }
}