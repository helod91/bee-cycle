package com.accenture.beecycle.data.repositories

import com.accenture.beecycle.domain.models.Bicycle
import com.accenture.beecycle.domain.models.RIDE_TYPE
import com.accenture.beecycle.domain.models.Team
import com.accenture.beecycle.domain.repositories.BicycleRepository

class RemoteBicycleRepository : BicycleRepository {
    companion object {
        val BIKES = ArrayList<Bicycle>().apply {
            add(Bicycle("Red Rocket", "Pegas", "20", "128", "64", "32", RIDE_TYPE.BICYCLE))
            add(Bicycle("Thor", "Xiaomi", "25", "256", "128", "64",RIDE_TYPE.E_SCOOTER))
            add(Bicycle("BiKing", "Scott", "40", "512", "256", "128",RIDE_TYPE.E_BICYCLE))
        }
    }

    override suspend fun getUserBicycles(): List<Bicycle> {
        return BIKES
    }

    override suspend fun getTeams(): List<Team> {
        return ArrayList<Team>().apply {
            add(Team("Football Club", ArrayList<String>().apply {
                add("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQeik6d5EHLTi89m_CKLXyShylk4L92YflpJQ&usqp=CAU")
                add("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTXntEHo8Ds-bCLZJEQzVWnAJwR5qdGnjnnlA&usqp=CAU")
                add("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTipha_cttrOgT3-_iJkmmJBQWMamZ1GnTDxg&usqp=CAU")
                add("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR1mKVoDmigf1J_RHiZvKEQB98mgYGBySuJPw&usqp=CAU")
            }))

            add(Team("Friends", ArrayList<String>().apply {
                add("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRij6dtiHizH96qpCOe8WeXXP3yLyQJkPdGVg&usqp=CAU")
                add("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTH3V7spuuOMybujLitJRqmYWW7RpUNlkedlg&usqp=CAU")
                add("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRI4nSX6toZdVgbLo8qsUD1GMRQk4kdH3Hshw&usqp=CAU")
            }))
        }
    }
}