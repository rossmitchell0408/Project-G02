package com.example.project_g02

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import com.example.project_g02.databinding.ActivityLessonDetailBinding
import com.example.project_g02.models.Lesson
import com.example.project_g02.models.User
import com.google.gson.Gson

class LessonDetailActivity : AppCompatActivity() {
    companion object {
        private const val PREFS_NAME = "LessonPrefs"
    }

    private lateinit var binding: ActivityLessonDetailBinding
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var gson: Gson

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLessonDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        gson = Gson()

        sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE)
        val lessonJson = intent.getStringExtra("lessonJson")
        val lesson: Lesson = gson.fromJson(lessonJson,Lesson::class.java)
        val position = intent.getIntExtra("lessonIndex",-1)

        if (!isLogged()) {
            //If the user somehow ended up here without being logged in, send back to login screen
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
        val userJson = sharedPreferences.getString("loggedUser", null)
        val user = userJson.let { gson.fromJson(it, User::class.java) }


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

        binding.btnWatch.setOnClickListener {

        }
        binding.btnComplete.setOnClickListener {
            user.completed[position] = true
            sharedPreferences.edit() {
                putString("loggedUser", gson.toJson(user))
            }
            val intent = Intent(this,LessonListActivity::class.java)
            startActivity(intent)
        }
        binding.btnBack.setOnClickListener {
            val intent = Intent(this,LessonListActivity::class.java)
            startActivity(intent)
        }

    }
    private fun isLogged(): Boolean {
        return sharedPreferences.getString("loggedUser", null) != null
    }
}