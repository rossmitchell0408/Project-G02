package com.example.project_g02

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.project_g02.databinding.ActivityWelcomeBackBinding
import com.example.project_g02.models.User
import com.google.gson.Gson

class WelcomeBackActivity : AppCompatActivity() {

    companion object {
        private const val PREFS_NAME = "LessonPrefs"
    }

    private lateinit var binding: ActivityWelcomeBackBinding
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var gson: Gson

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWelcomeBackBinding.inflate(layoutInflater)
        setContentView(binding.root)
        sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE)
        gson = Gson()

        if (!isLogged()) {
            //If the user somehow ended up here without being logged in, send back to login screen
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
        val userJson = sharedPreferences.getString("loggedUser", null)
        val user = userJson.let { gson.fromJson(it, User::class.java) }

        binding.tvTitle.text = "Welcome back ${user.name}"

        var completed:Double = 0.0
        for (lesson in user.completed) {
            if (lesson) {
                completed++
            }
        }

        binding.tvCompletion.text = """
            You've completed ${(completed/user.completed.size)*100}% of the course!
            
            Lessons completed: ${completed}
            Lessons remaining: ${user.completed.size-completed}
        """.trimIndent()






        binding.btnContinue.setOnClickListener {
            val intent = Intent(this,LessonListActivity::class.java)
            intent.putExtra("userJson",userJson)
            startActivity(intent)
        }

        binding.btnDelete.setOnClickListener {
            with(sharedPreferences.edit()) {
                remove("loggedUser")
                apply()
            }
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }

    private fun isLogged(): Boolean {
        return sharedPreferences.getString("loggedUser", null) != null
    }
}