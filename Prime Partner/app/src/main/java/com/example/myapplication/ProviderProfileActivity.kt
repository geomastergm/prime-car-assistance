package com.example.myapplication

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.databinding.ActivityMyVehiclesBinding

class ProviderProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMyVehiclesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyVehiclesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupProviderProfile()
    }

    private fun setupProviderProfile() {
        // Provider-ის პროფილის სეტაპი
        supportActionBar?.title = "პროვაიდერის პროფილი"
        
        // Provider-ის ინფორმაცია
        Toast.makeText(this, "პროვაიდერის პროფილი - განვითარების პროცესშია", Toast.LENGTH_SHORT).show()
    }
}