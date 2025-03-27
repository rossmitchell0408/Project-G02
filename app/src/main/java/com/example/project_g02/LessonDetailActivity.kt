package com.example.project_g02

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import com.example.project_g02.databinding.ActivityLessonDetailBinding
import com.example.project_g02.models.User
import com.example.project_g02.singletons.Lessons
import com.google.gson.Gson

class LessonDetailActivity : AppCompatActivity() {
    companion object {
        private const val PREFS_NAME = "LessonPrefs"
    }

    private lateinit var binding: ActivityLessonDetailBinding
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var gson: Gson
    private val TAG = "LessonDetailActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLessonDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE)
        gson = Gson()

        val position = intent.getIntExtra("lessonIndex", -1)

        if (position < 0 || position >= Lessons.lessonList.size) {
            //If position is somehow out of the range of the lessonList,
            //log the error before sending the user back to Welcome Back Activity
            Log.e(TAG, "Invalid position received: $position")
            val intent = Intent(this, WelcomeBackActivity::class.java)
            intent.putExtra(
                "errorMessage",
                "Invalid lesson position received. If this problem persists, please contact the application's author."
            )
            intent.putExtra("errorSource", TAG)
            startActivity(intent)
            finish()
        }

        val lesson = Lessons.lessonList[position]

        if (!isLogged()) {
            //If the user somehow ended up here without being logged in, send back to login screen
            Log.e(TAG, "User not logged in, sending back to login screen.")
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("errorMessage", "You must be logged in to access this page.")
            intent.putExtra("errorSource", TAG)
            startActivity(intent)
            finish()
        }

        val userJson = sharedPreferences.getString("loggedUser", null)
        val user = userJson.let { gson.fromJson(it, User::class.java) }

        binding.tvTitle.text = lesson.name
        binding.tvTime.text = lesson.getTime()
        binding.tvDescription.text = lesson.description

        ///On Click Handlers///
        binding.btnWatch.setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW, lesson.link.toUri()))
        }

        binding.btnComplete.setOnClickListener {
            //Set the appropriate lesson to complete before storing the user data as a JSON string
            user.completed[position] = true
            with(sharedPreferences.edit()) {
                putString("loggedUser", gson.toJson(user))
                apply()
            }
            val intent = Intent(this, LessonListActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.btnBack.setOnClickListener {
            val intent = Intent(this, LessonListActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun isLogged(): Boolean {
        return sharedPreferences.getString("loggedUser", null) != null
    }
}