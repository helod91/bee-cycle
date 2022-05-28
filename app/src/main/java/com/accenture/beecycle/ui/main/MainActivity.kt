package com.accenture.beecycle.ui.main

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.accenture.beecycle.common.BaseActivity
import com.accenture.beecycle.databinding.ActivityMainBinding
import com.accenture.beecycle.domain.models.Bicycle
import com.accenture.beecycle.domain.models.RIDE_TYPE
import com.accenture.beecycle.ui.search.SearchActivity
import com.bumptech.glide.Glide
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.androidx.viewmodel.ext.android.viewModel

private const val REQ_LOCATION_PERMISSION = 0x1

@ExperimentalCoroutinesApi
class MainActivity :
    BaseActivity<ActivityMainBinding, MainState, MainIntent, MainAction, MainViewModel>() {

    override val viewModel: MainViewModel by viewModel()

    private val bicycleAdapter = BicycleAdapter()
    private val teamAdapter = TeamAdapter()

    private lateinit var locationClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        locationClient = LocationServices.getFusedLocationProviderClient(this)

        listenToLocationChanges()
        setView()
    }

    override fun onResume() {
        super.onResume()

        dispatchIntent(MainIntent.GetUserBicycles)
        dispatchIntent(MainIntent.GetUserTeams)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQ_LOCATION_PERMISSION) {
            if (grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                Snackbar.make(
                    this@MainActivity.main_container,
                    "Permissions has been denied by user",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                listenToLocationChanges()
            }
        }
    }

    override fun presentBinding(): ActivityMainBinding =
        ActivityMainBinding.inflate(LayoutInflater.from(this))

    override fun render(state: MainState) {
        when (state) {
            MainState.LoadingWeather -> binding.mainWeather.loading()
            MainState.NoWeather -> binding.mainWeather.loading()
            is MainState.ResultWeather -> binding.mainWeather.setWeather(state.weather)
            is MainState.ResultUserBicycles -> bicycleAdapter.data = ArrayList(state.bicycles).apply {
                add(Bicycle(rideType = RIDE_TYPE.ADD_BIKE))
            }
            is MainState.ResultUserTeams -> teamAdapter.data = state.teams
        }
    }

    private fun setView() {
        binding.mainBicycles.adapter = bicycleAdapter

        binding.mainTeams.adapter = teamAdapter
        binding.mainTeams.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        Glide.with(this)
            .load("https://imagez.tmz.com/image/bf/4by3/2022/04/05/bf0bbec74a1a463f96cf1bacfa831049_md.jpg")
            .circleCrop()
            .into(binding.mainAvatar)

        TabLayoutMediator(binding.mainBicyclesDots, binding.mainBicycles) { _, _ -> }.attach()
    }

    private fun listenToLocationChanges() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION),
                REQ_LOCATION_PERMISSION
            )
            return
        }
        locationClient.lastLocation
            .addOnSuccessListener(this) { location ->
                dispatchIntent(MainIntent.GetCurrentWeather(location?.latitude, location?.longitude))
            }
    }
}
