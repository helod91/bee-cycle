package com.accenture.beecycle.domain.models

data class Bicycle(
    val name: String? = null,
    val brand: String? = null,
    val avgSpeed: String? = null,
    val distanceTraveled: String? = null,
    val impact: String? = null,
    val moneySaved: String? = null,
    val rideType: RIDE_TYPE
)

enum class RIDE_TYPE {
    BICYCLE, E_BICYCLE, E_SCOOTER, ADD_BIKE
}