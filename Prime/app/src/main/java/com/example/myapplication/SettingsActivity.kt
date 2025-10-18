package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.example.myapplication.databinding.ActivitySettingsBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class SettingsActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivitySettingsBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance("https://carassistent-e343a-default-rtdb.europe-west1.firebasedatabase.app/")
        
        setupUI()
        loadUserSettings()
    }
    
    private fun setupUI() {
        // თემის არჩევა
        val themes = arrayOf("ღია თემა", "მუქი თემა", "სისტემური")
        val themeAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, themes)
        themeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerTheme.adapter = themeAdapter
        
        binding.spinnerTheme.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                when (position) {
                    0 -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    1 -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    2 -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
                }
                saveThemePreference(position)
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
        
        // ენის არჩევა
        val languages = arrayOf("ქართული", "English", "Русский")
        val langAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, languages)
        langAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerLanguage.adapter = langAdapter
        
        // გადახდის მეთოდების მართვა
        binding.btnManagePaymentMethods.setOnClickListener {
            // TODO: Navigate to Payment Methods management
            android.widget.Toast.makeText(this, "გადახდის მეთოდების მართვა - მალე დაემატება", android.widget.Toast.LENGTH_SHORT).show()
        }
        
        // პროფილის რედაქტირება
        binding.btnEditProfile.setOnClickListener {
            enableProfileEditing(true)
        }
        
        binding.btnSaveProfile.setOnClickListener {
            saveProfileChanges()
        }
        
        binding.btnCancelEdit.setOnClickListener {
            enableProfileEditing(false)
            loadUserSettings()
        }
        
        // შეტყობინებები
        binding.switchNotifications.setOnCheckedChangeListener { _, isChecked ->
            saveNotificationPreference(isChecked)
        }
        
        // ლოკაცია
        binding.switchLocation.setOnCheckedChangeListener { _, isChecked ->
            saveLocationPreference(isChecked)
        }
        
        // გამოსვლა
        binding.btnLogout.setOnClickListener {
            logout()
        }
        
        // უკან დაბრუნება
        binding.btnBack.setOnClickListener {
            finish()
        }
    }
    
    private fun loadUserSettings() {
        val sharedPrefs = getSharedPreferences("UserSettings", MODE_PRIVATE)
        
        // თემა
        val theme = sharedPrefs.getInt("theme", 2)
        binding.spinnerTheme.setSelection(theme)
        
        // ენა
        val language = sharedPrefs.getInt("language", 0)
        binding.spinnerLanguage.setSelection(language)
        
        // შეტყობინებები
        val notifications = sharedPrefs.getBoolean("notifications", true)
        binding.switchNotifications.isChecked = notifications
        
        // ლოკაცია
        val location = sharedPrefs.getBoolean("location", true)
        binding.switchLocation.isChecked = location
        
        // პროფილის ინფო
        val userPrefs = getSharedPreferences("UserData", MODE_PRIVATE)
        binding.etFullName.setText(userPrefs.getString("fullName", ""))
        binding.etEmail.setText(userPrefs.getString("email", ""))
        binding.etPhone.setText(userPrefs.getString("phone", ""))
        
        enableProfileEditing(false)
    }
    
    private fun enableProfileEditing(enable: Boolean) {
        binding.etFullName.isEnabled = enable
        binding.etEmail.isEnabled = enable
        binding.etPhone.isEnabled = enable
        
        binding.btnSaveProfile.visibility = if (enable) View.VISIBLE else View.GONE
        binding.btnCancelEdit.visibility = if (enable) View.VISIBLE else View.GONE
        binding.btnEditProfile.visibility = if (enable) View.GONE else View.VISIBLE
    }
    
    private fun saveProfileChanges() {
        val userPrefs = getSharedPreferences("UserData", MODE_PRIVATE)
        userPrefs.edit().apply {
            putString("fullName", binding.etFullName.text.toString())
            putString("email", binding.etEmail.text.toString())
            putString("phone", binding.etPhone.text.toString())
            apply()
        }
        
        // Firebase-ში განახლება
        auth.currentUser?.let { user ->
            val updates = hashMapOf<String, Any>(
                "fullName" to binding.etFullName.text.toString(),
                "email" to binding.etEmail.text.toString(),
                "phone" to binding.etPhone.text.toString()
            )
            database.getReference("users").child(user.uid).updateChildren(updates)
        }
        
        enableProfileEditing(false)
        android.widget.Toast.makeText(this, "პროფილი განახლდა", android.widget.Toast.LENGTH_SHORT).show()
    }
    
    private fun saveThemePreference(theme: Int) {
        getSharedPreferences("UserSettings", MODE_PRIVATE)
            .edit()
            .putInt("theme", theme)
            .apply()
    }
    
    private fun saveNotificationPreference(enabled: Boolean) {
        getSharedPreferences("UserSettings", MODE_PRIVATE)
            .edit()
            .putBoolean("notifications", enabled)
            .apply()
    }
    
    private fun saveLocationPreference(enabled: Boolean) {
        getSharedPreferences("UserSettings", MODE_PRIVATE)
            .edit()
            .putBoolean("location", enabled)
            .apply()
    }
    
    private fun logout() {
        auth.signOut()
        getSharedPreferences("UserData", MODE_PRIVATE).edit().clear().apply()
        getSharedPreferences("UserSettings", MODE_PRIVATE).edit().clear().apply()
        
        val intent = Intent(this, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }
}
