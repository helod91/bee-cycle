package com.accenture.beecycle.ui.search

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.accenture.beecycle.R
import com.accenture.beecycle.common.BaseActivity
import com.accenture.beecycle.databinding.ActivitySearchBinding
import com.accenture.beecycle.domain.models.Vehicle
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.content_search.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*

@ExperimentalCoroutinesApi
class SearchActivity :
    BaseActivity<ActivitySearchBinding, SearchState, SearchIntent, SearchAction, SearchViewModel>(),
    OnMapReadyCallback {

    companion object {
        private const val RECORD_REQUEST_CODE: Int = 100
    }

    private lateinit var mapFragment: SupportMapFragment

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private val vehicleAdapter = VehicleAdapter()

    override val viewModel: SearchViewModel by viewModel()

    override fun presentBinding(): ActivitySearchBinding =
        ActivitySearchBinding.inflate(LayoutInflater.from(this))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        mapFragment = supportFragmentManager
            .findFragmentById(R.id.map_fragment) as SupportMapFragment
        et_search_location.setOnEditorActionListener { textView, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                dispatchIntent(SearchIntent.GetSearchResults(textView.text.trim().toString()))
                return@setOnEditorActionListener true
            }
            false
        }
        getLocation()
        setViews()
    }

    private fun setViews() {
        vehicleAdapter.data = getTrips()
        binding.searchBottomSheet.mainVehicles.adapter = vehicleAdapter
        binding.searchBottomSheet.mainVehicles.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
    }

    private fun getTrips(): List<Vehicle> {
        return ArrayList<Vehicle>().apply {
            add(Vehicle(R.drawable.ic_small_bike, isBicycle = true, name = "my bicycle", tripDuration = 15, tripCost = 0.0, tripEmission = 0))
            add(Vehicle(R.drawable.ic_graph_vehicle, isBicycle = false, name = "my TDI", tripDuration = 9, tripCost = 23.7, tripEmission = 388))
        }
    }

    @SuppressLint("MissingPermission")
    private fun getLocation() {
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
                RECORD_REQUEST_CODE
            )
            return
        }
        mapFragment.getMapAsync(this)
    }

    override fun render(state: SearchState) {
        when (state) {
            SearchState.LoadingSearchResult -> binding.searchContent.searchResultView.loading()
            SearchState.NoSearchResult -> binding.searchContent.searchResultView.loading()
            is SearchState.ResultSearch -> binding.searchContent.searchResultView.setResults(state.results)
//            is SearchState.ResultVehicleTrips -> vehicleAdapter.data = state.vehicles
            is SearchState.ResultWeather -> {
                binding.searchContent.searchTemperature.text = String.format(Locale.getDefault(), "%.0f°C", state.weather.temperature)
                binding.searchContent.searchWind.text = String.format(
                    Locale.getDefault(),
                    resources.getString(R.string.wind_unit_label),
                    state.weather.windSpeed
                )
                binding.searchContent.searchHumidity.text = "${state.weather.humidity}%"
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            RECORD_REQUEST_CODE -> {
                if (grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    //Permission has been denied by user
                    Snackbar.make(
                        this@SearchActivity.cl_search_activity,
                        "Permissions has been denied by user",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    //Log.i(TAG, "Permission has been granted by user")
                    getLocation()
                }
            }
        }
    }

    @SuppressLint("MissingPermission")
    override fun onMapReady(map: GoogleMap) {
        if (ContextCompat.checkSelfPermission(
                this@SearchActivity,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            map.isMyLocationEnabled = true
        }
        moveMyLocationButton()
        fusedLocationClient.lastLocation
            .addOnSuccessListener(this) { location ->
                // Got last known location. In some rare situations this can be null.
                if (location != null) {
                    dispatchIntent(SearchIntent.GetCurrentWeather(location.latitude, location.longitude))

                    val coordinate = LatLng(
                        location.latitude,
                        location.longitude
                    ) //Store these lat lng values somewhere. These should be constant.

                    val cameraFactory = CameraUpdateFactory.newLatLngZoom(
                        coordinate, 18f
                    )
                    map.animateCamera(cameraFactory)
                }
            }
    }

    private fun moveMyLocationButton() {
        val locationButton = mapFragment.view?.findViewWithTag<View>("GoogleMapMyLocationButton")

        val rlp = locationButton?.layoutParams as (RelativeLayout.LayoutParams)
        // position on right bottom
        rlp.addRule(RelativeLayout.ALIGN_PARENT_TOP, 0)
        rlp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE)
        rlp.setMargins(0, 0, 0, 150)
    }
}

