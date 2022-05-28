package com.accenture.beecycle.domain.repositories

import com.accenture.beecycle.domain.models.Bicycle
import com.accenture.beecycle.domain.models.Team

interface BicycleRepository {

    suspend fun getUserBicycles(): List<Bicycle>

    suspend fun getTeams(): List<Team>
}