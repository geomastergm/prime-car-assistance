package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.data.model.CarManufacturer
import com.example.myapplication.data.model.CarModel
import com.example.myapplication.data.model.Vehicle
import com.example.myapplication.data.repository.CarRepository
import com.example.myapplication.databinding.ActivityMyVehiclesBinding

class MyVehiclesActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityMyVehiclesBinding
    private lateinit var carRepository: CarRepository
    
    private var manufacturers = listOf<CarManufacturer>()
    private var models = listOf<CarModel>()
    private var selectedManufacturer: CarManufacturer? = null
    private var selectedModel: CarModel? = null
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyVehiclesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        carRepository = CarRepository()
        
        setupUI()
        loadData()
    }
    
    private fun setupUI() {
        // საწვავის ტიპები
        val fuelTypes = listOf("ბენზინი", "დიზელი", "ჰიბრიდი", "ელექტრო", "გაზი")
        val fuelAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, fuelTypes)
        fuelAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerFuelType.adapter = fuelAdapter
        
        // მწარმოებლის ძიება
        binding.actManufacturer.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                val query = s.toString()
                if (query.length >= 2) {
                    searchManufacturers(query)
                }
            }
        })
        
        // მოდელის ძიება
        binding.actModel.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                val query = s.toString()
                if (query.length >= 2 && selectedManufacturer != null) {
                    searchModels(query)
                }
            }
        })
        
        // მწარმოებლის არჩევა
        binding.actManufacturer.setOnItemClickListener { _, _, position, _ ->
            val adapter = binding.actManufacturer.adapter as ArrayAdapter<String>
            val manufacturerName = adapter.getItem(position)
            selectedManufacturer = manufacturers.find { it.name == manufacturerName }
            
            // მოდელების ჩატვირთვა ამ მწარმოებლისთვის
            selectedManufacturer?.let { manufacturer ->
                models = carRepository.getModelsByManufacturer(manufacturer.id)
                updateModelsAdapter("")
                binding.actModel.setText("")
                selectedModel = null
            }
        }
        
        // მოდელის არჩევა
        binding.actModel.setOnItemClickListener { _, _, position, _ ->
            val adapter = binding.actModel.adapter as ArrayAdapter<String>
            val modelName = adapter.getItem(position)
            selectedModel = models.find { it.name == modelName }
        }
        
        // ღილაკები
        binding.btnSaveCar.setOnClickListener {
            saveCar()
        }
        
        binding.btnViewMyCars.setOnClickListener {
            // TODO: ჩემი მანქანების სიის აქტივითი
            Toast.makeText(this, "ჩემი მანქანების სია - მალე დაემატება", Toast.LENGTH_SHORT).show()
        }
        
        binding.btnBack.setOnClickListener {
            finish()
        }
    }
    
    private fun loadData() {
        manufacturers = carRepository.getManufacturers()
    }
    
    private fun searchManufacturers(query: String) {
        val filteredManufacturers = carRepository.searchManufacturers(query)
        val manufacturerNames = filteredManufacturers.map { it.name }
        
        val adapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, manufacturerNames)
        binding.actManufacturer.setAdapter(adapter)
        binding.actManufacturer.showDropDown()
    }
    
    private fun searchModels(query: String) {
        val filteredModels = if (selectedManufacturer != null) {
            carRepository.searchModels(query, selectedManufacturer!!.id)
        } else {
            emptyList()
        }
        updateModelsAdapter(query, filteredModels)
    }
    
    private fun updateModelsAdapter(query: String, filteredModels: List<CarModel>? = null) {
        val modelsToShow = filteredModels ?: models
        val modelNames = modelsToShow.map { it.name }
        
        val adapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, modelNames)
        binding.actModel.setAdapter(adapter)
        
        if (query.isNotEmpty()) {
            binding.actModel.showDropDown()
        }
    }
    
    private fun saveCar() {
        val manufacturerName = binding.actManufacturer.text.toString().trim()
        val modelName = binding.actModel.text.toString().trim()
        val year = binding.etYear.text.toString().trim()
        val engine = binding.etEngine.text.toString().trim()
        val licensePlate = binding.etLicensePlate.text.toString().trim()
        val color = binding.etColor.text.toString().trim()
        val fuelType = binding.spinnerFuelType.selectedItem.toString()
        
        // ვალიდაცია
        if (manufacturerName.isEmpty()) {
            binding.actManufacturer.error = "მწარმოებელი საჭიროა"
            return
        }
        
        if (modelName.isEmpty()) {
            binding.actModel.error = "მოდელი საჭიროა"
            return
        }
        
        if (year.isEmpty() || year.toIntOrNull() == null) {
            binding.etYear.error = "სწორი წელი შეიყვანეთ"
            return
        }
        
        if (licensePlate.isEmpty()) {
            binding.etLicensePlate.error = "სახელმწიფო ნომერი საჭიროა"
            return
        }
        
        // მანქანის შენახვა (ეს მომავალში database-ში იქნება)
        val vehicle = Vehicle(
            id = System.currentTimeMillis().toString(),
            userId = "current_user", // ეს შემდეგ შეიცვლება
            make = manufacturerName,
            model = modelName,
            year = year.toInt(),
            licensePlate = licensePlate.uppercase(),
            color = color,
            fuelType = fuelType,
            isDefault = true
        )
        
        Toast.makeText(this, "მანქანა წარმატებით დაემატა!", Toast.LENGTH_LONG).show()
        
        // უკან გადასვლა
        setResult(RESULT_OK)
        finish()
    }
}