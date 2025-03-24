package com.example.project_g02

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.project_g02.databinding.ActivityWelcomeBackBinding
import com.example.project_g02.models.User
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson

class WelcomeBackActivity : AppCompatActivity() {

    companion object {
        private const val PREFS_NAME = "LessonPrefs"
    }

    private lateinit var binding: ActivityWelcomeBackBinding
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var gson: Gson
    private val TAG = "WelcomeBackActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWelcomeBackBinding.inflate(layoutInflater)
        setContentView(binding.root)
        sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE)
        gson = Gson()

        if (!isLogged()) {
            Log.d(TAG, "User not logged in, sending back to login screen.")
            //If the user somehow ended up here without being logged in, send back to login screen
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        if (intent.hasExtra("errorMessage")) {
            //If an errorMessage was passed to the activity (only from LessonDetailActivity at the moment)
            //display a snackbar with the error message.
            Log.e(TAG,"Error received from: ${intent.getStringExtra("errorSource")}")
            Log.e(TAG,"Error message: ${intent.getStringExtra("errorMessage")}")
            Snackbar.make(binding.root, intent.getStringExtra("errorMessage").toString(), Snackbar.LENGTH_LONG).show()
        }

        val userJson = sharedPreferences.getString("loggedUser", null)
        val user = userJson.let { gson.fromJson(it, User::class.java) }

        binding.tvTitle.text = "Welcome back ${user.name}"

        var completed = 0
        for (lesson in user.completed) {
            if (lesson) {
                completed++
            }
        }

        //Casting complete to a Double in the top line to have the division properly resolve.
        //If completed stayed as an Int, the user would either have 0% or 100% progress, no in between.
        binding.tvCompletion.text = """
            You've completed ${(completed.toDouble()/user.completed.size)*100}% of the course!
            
            Lessons completed: ${completed}
            Lessons remaining: ${(user.completed.size-completed)}
        """.trimIndent()


        ///On Click Handlers///
        binding.btnContinue.setOnClickListener {
            val intent = Intent(this,LessonListActivity::class.java)
            intent.putExtra("userJson",userJson)
            startActivity(intent)
        }

        binding.btnDelete.setOnClickListener {
            //Clear the loggedUser string before sending user back to login page
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