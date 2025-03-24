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
import com.example.project_g02.models.User
import com.example.project_g02.singletons.Lessons
import com.google.gson.Gson

class LessonListActivity : AppCompatActivity() {
    companion object {
        private const val PREFS_NAME = "LessonPrefs"
    }

    private lateinit var binding: ActivityLessonListBinding
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var gson: Gson

    override fun onCreate(savedInstanceState: Bundle?) {
        val TAG = "LessonListOnCreate"
        super.onCreate(savedInstanceState)
        binding = ActivityLessonListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val lessons = Lessons
        gson = Gson()

        sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE)
        if (!isLogged()) {
            //If the user somehow ended up here without being logged in, send back to login screen
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
        val userJson = sharedPreferences.getString("loggedUser", null)
        val user = userJson.let { gson.fromJson(it, User::class.java) }

        val lessonAdapter =
            LessonAdapter(lessons.lessonList, user.completed) { position ->
                //This lambda function will run when it receives the clicked position from the Adapter
                val lessonJson = gson.toJson(lessons.lessonList[position])
                val intent = Intent(this, LessonDetailActivity::class.java)
                intent.putExtra("lessonJson", lessonJson)
                intent.putExtra("lessonIndex", position)
                startActivity(intent)
            }
        binding.rvLesson.adapter = lessonAdapter
        binding.rvLesson.layoutManager = LinearLayoutManager(this)
        binding.rvLesson.addItemDecoration(
            DividerItemDecoration(
                this,
                LinearLayoutManager.VERTICAL
            )
        )
        Log.d(TAG, "After recycler view")

        binding.btnBack.setOnClickListener {
            startActivity(Intent(this, WelcomeBackActivity::class.java))
            finish()
        }

    }
    private fun isLogged(): Boolean {
        return sharedPreferences.getString("loggedUser", null) != null
    }
}