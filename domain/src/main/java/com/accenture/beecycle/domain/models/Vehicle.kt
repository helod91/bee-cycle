package com.accenture.beecycle.domain.models

data class Vehicle(
    val vehicleAvatar: Int,
    val isBicycle: Boolean,
    val name: String?,
    val tripDuration: Int,
    val tripCost: Double,
    val tripEmission: Int
)
