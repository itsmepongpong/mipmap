package com.example.mipmap

import android.os.Bundle
import android.util.Patterns
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import com.google.android.material.button.MaterialButton
import com.google.android.material.checkbox.MaterialCheckBox
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class SignUpActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        val tilName = findViewById<TextInputLayout>(R.id.til_name)
        val etName = findViewById<TextInputEditText>(R.id.et_name)
        val tilEmail = findViewById<TextInputLayout>(R.id.til_email)
        val etEmail = findViewById<TextInputEditText>(R.id.et_email)
        val tilPassword = findViewById<TextInputLayout>(R.id.til_password)
        val etPassword = findViewById<TextInputEditText>(R.id.et_password)
        val cbTerms = findViewById<MaterialCheckBox>(R.id.cb_terms)
        val btnSignup = findViewById<MaterialButton>(R.id.btn_signup)
        val tvLogin = findViewById<TextView>(R.id.tv_login)

        tvLogin.setOnClickListener {
            finish() // Go back to LoginActivity
        }

        // Disable button until terms are checked
        cbTerms.setOnCheckedChangeListener { _, isChecked ->
            btnSignup.isEnabled = isChecked
        }
        btnSignup.isEnabled = false // Initial state

        // Clear errors when user starts typing
        etName.doOnTextChanged { _, _, _, _ -> tilName.error = null }
        etEmail.doOnTextChanged { _, _, _, _ -> tilEmail.error = null }
        etPassword.doOnTextChanged { _, _, _, _ -> tilPassword.error = null }

        btnSignup.setOnClickListener {
            var isValid = true

            if (etName.text.isNullOrBlank()) {
                tilName.error = "Name is required"
                isValid = false
            }

            if (etEmail.text.isNullOrBlank()) {
                tilEmail.error = "Email is required"
                isValid = false
            } else if (!Patterns.EMAIL_ADDRESS.matcher(etEmail.text.toString()).matches()) {
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

            if (isValid && cbTerms.isChecked) {
                Toast.makeText(this, "Account Created!", Toast.LENGTH_SHORT).show()
                // TODO: Navigate to Home or Login
            }
        }
    }
}