package com.example.myapplication.data.repository

import com.example.myapplication.data.model.CarManufacturer
import com.example.myapplication.data.model.CarModel
import com.example.myapplication.data.model.CarSeries

class CarRepository {
    
    // მანქანების მწარმოებლები - მსოფლიოს ყველაზე პოპულარული
    fun getManufacturers(): List<CarManufacturer> {
        return listOf(
            CarManufacturer("1", "Toyota", "Japan", ""),
            CarManufacturer("2", "BMW", "Germany", ""),
            CarManufacturer("3", "Mercedes-Benz", "Germany", ""),
            CarManufacturer("4", "Audi", "Germany", ""),
            CarManufacturer("5", "Volkswagen", "Germany", ""),
            CarManufacturer("6", "Nissan", "Japan", ""),
            CarManufacturer("7", "Honda", "Japan", ""),
            CarManufacturer("8", "Hyundai", "South Korea", ""),
            CarManufacturer("9", "Kia", "South Korea", ""),
            CarManufacturer("10", "Ford", "USA", ""),
            CarManufacturer("11", "Chevrolet", "USA", ""),
            CarManufacturer("12", "Lexus", "Japan", ""),
            CarManufacturer("13", "Mazda", "Japan", ""),
            CarManufacturer("14", "Subaru", "Japan", ""),
            CarManufacturer("15", "Mitsubishi", "Japan", ""),
            CarManufacturer("16", "Peugeot", "France", ""),
            CarManufacturer("17", "Renault", "France", ""),
            CarManufacturer("18", "Fiat", "Italy", ""),
            CarManufacturer("19", "Volvo", "Sweden", ""),
            CarManufacturer("20", "Skoda", "Czech Republic", ""),
            CarManufacturer("21", "SEAT", "Spain", ""),
            CarManufacturer("22", "Opel", "Germany", ""),
            CarManufacturer("23", "Citroën", "France", ""),
            CarManufacturer("24", "Lada", "Russia", ""),
            CarManufacturer("25", "Daewoo", "South Korea", "")
        )
    }
    
    // Toyota-ს მოდელები
    fun getToyotaModels(): List<CarModel> {
        return listOf(
            CarModel("1", "1", "Camry", 1982, null, "Sedan", listOf("Petrol", "Hybrid")),
            CarModel("2", "1", "Corolla", 1966, null, "Sedan", listOf("Petrol", "Hybrid")),
            CarModel("3", "1", "RAV4", 1994, null, "SUV", listOf("Petrol", "Hybrid")),
            CarModel("4", "1", "Highlander", 2000, null, "SUV", listOf("Petrol", "Hybrid")),
            CarModel("5", "1", "Prius", 1997, null, "Hatchback", listOf("Hybrid")),
            CarModel("6", "1", "Land Cruiser", 1951, null, "SUV", listOf("Petrol", "Diesel")),
            CarModel("7", "1", "Avalon", 1994, null, "Sedan", listOf("Petrol", "Hybrid")),
            CarModel("8", "1", "C-HR", 2016, null, "Crossover", listOf("Petrol", "Hybrid")),
            CarModel("9", "1", "Yaris", 1999, null, "Hatchback", listOf("Petrol", "Hybrid")),
            CarModel("10", "1", "Sienna", 1997, null, "Minivan", listOf("Petrol", "Hybrid"))
        )
    }
    
    // BMW-ს მოდელები  
    fun getBMWModels(): List<CarModel> {
        return listOf(
            CarModel("11", "2", "3 Series", 1975, null, "Sedan", listOf("Petrol", "Diesel", "Electric")),
            CarModel("12", "2", "5 Series", 1972, null, "Sedan", listOf("Petrol", "Diesel", "Hybrid")),
            CarModel("13", "2", "X3", 2003, null, "SUV", listOf("Petrol", "Diesel")),
            CarModel("14", "2", "X5", 1999, null, "SUV", listOf("Petrol", "Diesel", "Hybrid")),
            CarModel("15", "2", "1 Series", 2004, null, "Hatchback", listOf("Petrol", "Diesel")),
            CarModel("16", "2", "7 Series", 1977, null, "Sedan", listOf("Petrol", "Diesel", "Hybrid")),
            CarModel("17", "2", "X1", 2009, null, "SUV", listOf("Petrol", "Diesel")),
            CarModel("18", "2", "i3", 2013, null, "Hatchback", listOf("Electric")),
            CarModel("19", "2", "i4", 2021, null, "Sedan", listOf("Electric")),
            CarModel("20", "2", "iX", 2021, null, "SUV", listOf("Electric"))
        )
    }
    
    fun searchManufacturers(query: String): List<CarManufacturer> {
        return getManufacturers().filter { 
            it.name.contains(query, ignoreCase = true)
        }
    }
    
    fun getModelsByManufacturer(manufacturerId: String): List<CarModel> {
        return when (manufacturerId) {
            "1" -> getToyotaModels()
            "2" -> getBMWModels()
            else -> emptyList()
        }
    }
    
    fun searchModels(query: String, manufacturerId: String? = null): List<CarModel> {
        val models = if (manufacturerId != null) {
            getModelsByManufacturer(manufacturerId)
        } else {
            getToyotaModels() + getBMWModels() // სრული ბაზისთვის ყველა მოდელი
        }
        
        return models.filter { 
            it.name.contains(query, ignoreCase = true)
        }
    }
}