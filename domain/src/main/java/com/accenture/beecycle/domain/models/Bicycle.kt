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

enum class RIDE_TYPE(val baseSpeed: String) {
    BICYCLE("20"), E_BICYCLE("40"), E_SCOOTER("25"), ADD_BIKE("-")
}