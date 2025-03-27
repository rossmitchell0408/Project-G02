package com.example.project_g02

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.project_g02.adapters.LessonAdapter
import com.example.project_g02.databinding.ActivityLessonListBinding
import com.example.project_g02.interfaces.ClickDetectInterface
import com.example.project_g02.models.User
import com.example.project_g02.singletons.Lessons
import com.google.gson.Gson

class LessonListActivity : AppCompatActivity(), ClickDetectInterface {

    companion object {
        private const val PREFS_NAME = "LessonPrefs"
    }

    private lateinit var binding: ActivityLessonListBinding
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var gson: Gson
    private val TAG = "LessonListActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLessonListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE)
        gson = Gson()

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
        val lessonAdapter = LessonAdapter(user.completed, this)

        binding.rvLesson.adapter = lessonAdapter
        binding.rvLesson.layoutManager = LinearLayoutManager(this)
        binding.rvLesson.addItemDecoration(
            DividerItemDecoration(
                this, LinearLayoutManager.VERTICAL
            )
        )

        ///On Click Handler///
        binding.btnBack.setOnClickListener {
            startActivity(Intent(this, WelcomeBackActivity::class.java))
            finish()
        }
    }

    override fun lessonClick(position: Int) {
        val lessonJson = gson.toJson(Lessons.lessonList[position])
        Log.d(TAG, "lessonJson: $lessonJson")
        val intent = Intent(this, LessonDetailActivity::class.java)
        intent.putExtra("lessonJson", lessonJson)
        intent.putExtra("lessonIndex", position)
        Log.d(TAG, "Starting new activity.")
        startActivity(intent)
    }

    private fun isLogged(): Boolean {
        return sharedPreferences.getString("loggedUser", null) != null
    }
}