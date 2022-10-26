package com.accenture.beecycle.ui.search

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.accenture.beecycle.R
import com.accenture.beecycle.common.BaseActivity
import com.accenture.beecycle.databinding.ActivitySearchBinding
import com.accenture.beecycle.databinding.ContentSearchBinding
import com.accenture.beecycle.databinding.LayoutSearchResultBinding
import com.accenture.beecycle.domain.models.Vehicle
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.snackbar.Snackbar
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

    private var googleMap: GoogleMap? = null

    private lateinit var contentSearch: ContentSearchBinding

    override fun presentBinding(): ActivitySearchBinding {
        val binding = ActivitySearchBinding.inflate(LayoutInflater.from(this))
        contentSearch = binding.searchContent
        return binding
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        mapFragment = supportFragmentManager
            .findFragmentById(R.id.map_fragment) as SupportMapFragment
        contentSearch.etSearchLocation.setOnEditorActionListener { textView, actionId, _ ->
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
        contentSearch.searchClose.setOnClickListener {
            onBackPressed()
        }
        contentSearch.etSearchLocation.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                addMarker()
                this.currentFocus?.let { view ->
                    val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
                    imm?.hideSoftInputFromWindow(view.windowToken, 0)
                }
                contentSearch.searchLoading.isVisible = true
                Handler().postDelayed({
                    showResults(contentSearch.etSearchLocation.text?.toString())
                }, 2500)
                return@setOnEditorActionListener true
            }
            return@setOnEditorActionListener false
        }
    }

    private fun showResults(destination: String?) {
        contentSearch.searchLoading.isVisible = false

        val searchResultBottomSheet = BottomSheetDialog(this)

        val searchResultBinding = LayoutSearchResultBinding.inflate(LayoutInflater.from(this))
        searchResultBinding.searchResultDestination.text = "for $destination"
        searchResultBinding.searchResultRideWithBike.setOnClickListener {
            Toast.makeText(this, "Travel logged. Ride safe!", Toast.LENGTH_SHORT).show()
            searchResultBottomSheet.dismiss()
        }
        searchResultBinding.searchResultRideWithCar.setOnClickListener {
            Toast.makeText(this, "Travel logged. You suck!", Toast.LENGTH_SHORT).show()
            searchResultBottomSheet.dismiss()
        }

        searchResultBottomSheet.setContentView(searchResultBinding.root)
        searchResultBottomSheet.show()
    }

    private fun addMarker() {
        val coordinate = LatLng(
            46.7636947,
            23.5888902
        )
        //Store these lat lng values somewhere. These should be constant.

        val cameraFactory = CameraUpdateFactory.newLatLngZoom(
            coordinate, 17f
        )
        googleMap?.animateCamera(cameraFactory)
        val markerOption = MarkerOptions()
            .position(coordinate)
        googleMap?.addMarker(markerOption)
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
//            is SearchState.ResultVehicleTrips -> vehicleAdapter.data = state.vehicles
            is SearchState.ResultWeather -> {
                contentSearch.searchTemperature.text =
                    String.format(Locale.getDefault(), "%.0f°C", state.weather.temperature)
                contentSearch.searchWind.text = String.format(
                    Locale.getDefault(),
                    resources.getString(R.string.wind_unit_label),
                    state.weather.windSpeed
                )
                contentSearch.searchHumidity.text = "${state.weather.humidity}%"
            }
            else -> {}
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            RECORD_REQUEST_CODE -> {
                if (grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    //Permission has been denied by user
                    Snackbar.make(
                        contentSearch.clSearchActivity,
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
        googleMap = map

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

