package com.example.myapplication

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityLoginBinding
    private lateinit var sharedPreferences: SharedPreferences
    
    // Predefined valid users
    private val validUsers = mapOf(
        "555123456" to "password123",
        "555987654" to "mypass456",
        "555111222" to "secure789"
    )
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        sharedPreferences = getSharedPreferences("UserData", Context.MODE_PRIVATE)
        
        // Check if user is already logged in
        if (isUserLoggedIn()) {
            navigateToMainActivity()
            return
        }
        
        setupUI()
    }
    
    private fun setupUI() {
        binding.btnLogin.setOnClickListener {
            val phone = binding.etPhone.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()
            
            if (validateLogin(phone, password)) {
                Toast.makeText(this, "წარმატებული ავტორიზაცია!", Toast.LENGTH_SHORT).show()
                navigateToMainActivity()
            }
        }
        
        binding.btnRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }
    
    private fun validateLogin(phone: String, password: String): Boolean {
        if (phone.isEmpty()) {
            binding.etPhone.error = "მომხმარებლის სახელი საჭიროა"
            Toast.makeText(this, "შეიყვანეთ მომხმარებლის სახელი", Toast.LENGTH_SHORT).show()
            return false
        }
        
        if (password.isEmpty()) {
            binding.etPassword.error = "პაროლი საჭიროა"
            Toast.makeText(this, "შეიყვანეთ პაროლი", Toast.LENGTH_SHORT).show()
            return false
        }
        
        // Admin credentials check
        if (phone == "azrikunikatuno" && password == "azrikunikatuno") {
            saveLoginState(phone)
            return true
        }
        
        // TODO: Regular provider authentication system
        // For now, deny access to non-admin users
        Toast.makeText(this, "მხოლოდ ადმინისტრატორს აქვს წვდომა", Toast.LENGTH_LONG).show()
        return false
    }
    
    private fun isUserLoggedIn(): Boolean {
        return sharedPreferences.getBoolean("is_logged_in", false)
    }
    
    private fun saveLoginState(username: String) {
        with(sharedPreferences.edit()) {
            putBoolean("is_logged_in", true)
            putString("username", username)
            putString("user_phone", username) // backward compatibility
            putLong("login_timestamp", System.currentTimeMillis())
            apply()
        }
    }
    
    private fun navigateToMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }
}