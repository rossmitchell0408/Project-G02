package com.example.project_g02

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.project_g02.databinding.ActivityLessonDetailBinding
import com.example.project_g02.models.Lesson
import com.google.gson.Gson

class LessonDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLessonDetailBinding
    private lateinit var gson: Gson

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLessonDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        gson = Gson()

        val lessonJson = intent.getStringExtra("lessonJson")
        val lesson: Lesson = gson.fromJson(lessonJson,Lesson::class.java)
        val position = intent.getIntExtra("lessonIndex",-1)

        binding.tvTitle.text = lesson.name
        binding.tvTime.text = lesson.getTime()
        binding.tvDescription.text = lesson.description

    //TODO
    //Mark complete in user info (pull loggedUser from sharedPrefs,
    //  convert to User, set proper completed flag, convert back to json, saved to sharedPrefs)
    //
    //Intent to open link on btn click (pull link from lesson)
    //
    //Handle what to do if position is somehow -1 (Shouldnt ever happen? Might be more eloquent way of handling this)

    }
}