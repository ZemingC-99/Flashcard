package com.cs501.flashcard

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var usernameEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var loginButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        usernameEditText = findViewById(R.id.usernameText)
        passwordEditText = findViewById(R.id.passwordText)
        loginButton = findViewById(R.id.loginButton)

        loginButton.setOnClickListener {
            if (isValid(
                    usernameEditText.text.toString(),
                    passwordEditText.text.toString()
                )
            ) {
                val intent = Intent(this, FlashcardActivity::class.java)
                intent.putExtra("USERNAME", usernameEditText.text.toString())
                startActivity(intent)
            } else {
                Toast.makeText(this, "Invalid Username or Password!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun isValid(username: String, password: String): Boolean {
        return username == "user" && password == "password"
    }
}