package com.example.mipmap.ui.theme

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import com.example.login.SignUp.SignUpActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val tilEmail = findViewById<TextInputLayout>(R.id.til_email)
        val etEmail = findViewById<TextInputEditText>(R.id.et_email)
        val tilPassword = findViewById<TextInputLayout>(R.id.til_password)
        val etPassword = findViewById<TextInputEditText>(R.id.et_password)
        val btnLogin = findViewById<MaterialButton>(R.id.btn_login)
        val tvSignup = findViewById<android.widget.TextView>(R.id.tv_signup)

        tvSignup.setOnClickListener {
            val intent = android.content.Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }

        // Clear errors when user starts typing
        etEmail.doOnTextChanged { _, _, _, _ -> tilEmail.error = null }
        etPassword.doOnTextChanged { _, _, _, _ -> tilPassword.error = null }

        btnLogin.setOnClickListener {
            var isValid = true

            if (etEmail.text.isNullOrBlank()) {
                tilEmail.error = "Email is required"
                isValid = false
            } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(etEmail.text.toString()).matches()) {
                tilEmail.error = "Enter a valid email"
                isValid = false
            }

            if (etPassword.text.isNullOrBlank()) {
                tilPassword.error = "Password is required"
                isValid = false
            } else if (etPassword.text.toString().length < 6) {
                tilPassword.error = "Min 6 characters"
                isValid = false
            }

            if (isValid) {
                Toast.makeText(this, "Login Successful!", Toast.LENGTH_SHORT).show()
                // TODO: Navigate to Home
            }
        }
    }
}