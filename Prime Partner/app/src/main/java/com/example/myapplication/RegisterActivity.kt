package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityRegisterBinding
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        setupUI()
    }
    
    private fun setupUI() {
        binding.btnRegister.setOnClickListener {
            val firstName = binding.etFirstName.text.toString().trim()
            val lastName = binding.etLastName.text.toString().trim()
            val phone = binding.etPhone.text.toString().trim()
            val email = binding.etEmail.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()
            
            if (validateRegistration(firstName, lastName, phone, email, password)) {
                // Mock successful registration
                Toast.makeText(this, "წარმატებული რეგისტრაცია!", Toast.LENGTH_SHORT).show()
                
                // Navigate back to login
                finish()
            }
        }
        
        binding.btnBackToLogin.setOnClickListener {
            finish()
        }
    }
    
    private fun validateRegistration(
        firstName: String, 
        lastName: String, 
        phone: String, 
        email: String, 
        password: String
    ): Boolean {
        if (firstName.isEmpty()) {
            binding.etFirstName.error = "სახელი საჭიროა"
            return false
        }
        
        if (lastName.isEmpty()) {
            binding.etLastName.error = "გვარი საჭიროა"
            return false
        }
        
        if (phone.isEmpty()) {
            binding.etPhone.error = "ტელეფონის ნომერი საჭიროა"
            return false
        }
        
        if (phone.length < 9) {
            binding.etPhone.error = "არასწორი ტელეფონის ნომერი"
            return false
        }
        
        if (password.isEmpty()) {
            binding.etPassword.error = "პაროლი საჭიროა"
            return false
        }
        
        if (password.length < 6) {
            binding.etPassword.error = "პაროლი უნდა შეიცავდეს მინიმუმ 6 სიმბოლოს"
            return false
        }
        
        if (email.isNotEmpty() && !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.etEmail.error = "არასწორი ელ. ფოსტა"
            return false
        }
        
        return true
    }
}