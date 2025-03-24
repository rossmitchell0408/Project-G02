package com.example.project_g02.models

data class Lesson(val name: String, val length: Int, val description: String, val link: String) {
    fun getTime(): String{
        //Small helper function to format time for output
        return "${(length % 3600)/60} minutes, ${length % 60} seconds"
    }
}
