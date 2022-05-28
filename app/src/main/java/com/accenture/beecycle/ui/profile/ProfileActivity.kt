package com.accenture.beecycle.ui.profile

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.accenture.beecycle.R
import com.accenture.beecycle.databinding.ActivityProfileBinding
import com.accenture.beecycle.ui.prodetails.ProDetailsActivity
import com.bumptech.glide.Glide
import com.db.williamchart.view.DonutChartView
import io.github.inflationx.viewpump.ViewPumpContextWrapper

class ProfileActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        with(ActivityProfileBinding.inflate(LayoutInflater.from(this))) {
            Glide.with(profileUserAvatar)
                .load("https://imagez.tmz.com/image/bf/4by3/2022/04/05/bf0bbec74a1a463f96cf1bacfa831049_md.jpg")
                .circleCrop()
                .into(profileUserAvatar)

            profileClose.setOnClickListener {
                onBackPressed()
            }
            profileWeatherSunny.setOnClickListener {
                setSelectedWeatherPreference(
                    profileWeatherSunny,
                    profileWeatherCloudy,
                    profileWeatherRainy
                )
            }
            profileWeatherCloudy.setOnClickListener {
                setSelectedWeatherPreference(
                    profileWeatherCloudy,
                    profileWeatherSunny,
                    profileWeatherRainy
                )
            }
            profileWeatherRainy.setOnClickListener {
                setSelectedWeatherPreference(
                    profileWeatherRainy,
                    profileWeatherCloudy,
                    profileWeatherSunny
                )
            }

            profileBikeData.donutColors = intArrayOf(Color.parseColor("#5cc87e"))
            profileBikeData.animate(listOf(65f))

            profileCarData.donutColors = intArrayOf(Color.parseColor("#5cc87e"))
            profileCarData.animate(listOf(35f))

            profilePro.setOnClickListener {
                openProDetails()
            }
            for (i in 0 until profileBikesContainer.childCount) {
                profileBikesContainer.getChildAt(i).setOnClickListener {
                    openProDetails()
                }
            }

            setContentView(root)
        }
    }

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase))
    }

    private fun setSelectedWeatherPreference(
        weatherPreference: ConstraintLayout,
        vararg unSelectableViews: ConstraintLayout
    ) {
        weatherPreference.setBackgroundResource(R.drawable.bg_selected_card)
        unSelectableViews.forEach { it.background = null }
    }

    private fun openProDetails() {
        val openProDetails = Intent(this@ProfileActivity, ProDetailsActivity::class.java)
        startActivity(openProDetails)
    }
}