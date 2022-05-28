package com.accenture.beecycle.ui.main

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.accenture.beecycle.R
import com.accenture.beecycle.common.BaseActivity
import com.accenture.beecycle.data.repositories.RemoteBicycleRepository
import com.accenture.beecycle.data.repositories.RemoteBicycleRepository.Companion.BIKES
import com.accenture.beecycle.databinding.ActivityMainBinding
import com.accenture.beecycle.databinding.LayoutAddBikeBinding
import com.accenture.beecycle.domain.models.Bicycle
import com.accenture.beecycle.domain.models.RIDE_TYPE
import com.accenture.beecycle.ui.profile.ProfileActivity
import com.accenture.beecycle.ui.search.SearchActivity
import com.bumptech.glide.Glide
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.material.bottomsheet.BottomSheetDialog
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

    private var rideType: RIDE_TYPE = RIDE_TYPE.BICYCLE

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        locationClient = LocationServices.getFusedLocationProviderClient(this)

        listenToLocationChanges()
        setView()
        setAddBike()
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

        binding.mainAvatar.setOnClickListener {
            val openProfile = Intent(this, ProfileActivity::class.java)
            startActivity(openProfile)
        }

        Glide.with(this)
            .load("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSeqTF3M55Y384pBCWI9W88SriOEQrSUk5tHA&usqp=CAU")
            .circleCrop()
            .into(binding.mainAvatar)

        TabLayoutMediator(binding.mainBicyclesDots, binding.mainBicycles) { _, _ -> }.attach()
    }

    private fun setAddBike() {
        rideType = RIDE_TYPE.BICYCLE

        val addBikeBinding = LayoutAddBikeBinding.inflate(LayoutInflater.from(this))
        val bottomSheetDialog = BottomSheetDialog(this, R.style.TransparentDialog)

        addBikeBinding.addBikeBike.setOnClickListener {
            rideType = RIDE_TYPE.BICYCLE

            setSelectedWeatherPreference(
                addBikeBinding.addBikeBike,
                addBikeBinding.addBikeEscooter,
                addBikeBinding.addBikeEbike
            )
        }
        addBikeBinding.addBikeEbike.setOnClickListener {
            rideType = RIDE_TYPE.E_BICYCLE

            setSelectedWeatherPreference(
                addBikeBinding.addBikeEbike,
                addBikeBinding.addBikeBike,
                addBikeBinding.addBikeEscooter
            )
        }
        addBikeBinding.addBikeEscooter.setOnClickListener {
            rideType = RIDE_TYPE.E_SCOOTER

            setSelectedWeatherPreference(
                addBikeBinding.addBikeEscooter,
                addBikeBinding.addBikeEbike,
                addBikeBinding.addBikeBike
            )
        }

        addBikeBinding.addBikeSave.setOnClickListener {
            BIKES.add(Bicycle(addBikeBinding.addBikeName.text.toString(), addBikeBinding.addBikeBrand.text.toString(),
                rideType.baseSpeed, "0", "0", "0", rideType
            ))

            addBikeBinding.addBikeName.text = null
            addBikeBinding.addBikeBrand.text = null

            rideType = RIDE_TYPE.BICYCLE

            setSelectedWeatherPreference(
                addBikeBinding.addBikeBike,
                addBikeBinding.addBikeEscooter,
                addBikeBinding.addBikeEbike
            )

            bottomSheetDialog.dismiss()

            dispatchIntent(MainIntent.GetUserBicycles)
        }

        bicycleAdapter.setAddNewBikeListener {
            bottomSheetDialog.setContentView(addBikeBinding.root)
            bottomSheetDialog.show()
        }
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

    private fun setSelectedWeatherPreference(
        weatherPreference: ConstraintLayout,
        vararg unSelectableViews: ConstraintLayout
    ) {
        weatherPreference.setBackgroundResource(R.drawable.bg_selected_card)
        unSelectableViews.forEach { it.background = null }
    }
}
