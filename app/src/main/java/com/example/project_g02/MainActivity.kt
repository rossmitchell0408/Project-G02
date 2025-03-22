package com.example.project_g02

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.project_g02.models.User
import com.example.project_g02.databinding.ActivityMainBinding
import com.example.project_g02.singletons.Lessons
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson

class MainActivity : AppCompatActivity() {

    companion object {
        private const val PREFS_NAME = "LessonPrefs"
    }

    private lateinit var binding: ActivityMainBinding
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var gson: Gson

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE)
        gson = Gson()
        val lessons = Lessons

        if (isLogged()) {
            //If the user is already logged in, send them to the welcome back screen and finish this activity
            startActivity(Intent(this, WelcomeBackActivity::class.java))
            finish()
        }

        binding.submitBtn.setOnClickListener {
            val userName = binding.etName.text.toString()
            if (userName.isNotBlank()) {
                val user = User(
                    name = userName,
                    completed = BooleanArray(lessons.lessonList.size)
                )
                val userJson = gson.toJson(user)
                with(sharedPreferences.edit()) {
                    putString("loggedUser", userJson)
                    apply()
                }
                binding.etName.text.clear()
                startActivity(Intent(this, WelcomeBackActivity::class.java))
                finish()
            } else {
                Snackbar.make(binding.root, "Please enter a username", Snackbar.LENGTH_LONG).show()
            }
        }
    }

    private fun isLogged(): Boolean {
        return sharedPreferences.getString("loggedUser", null) != null
    }
}