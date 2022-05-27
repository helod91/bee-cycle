package com.accenture.beecycle.ui.widgets

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import com.accenture.beecycle.R
import com.accenture.beecycle.databinding.LayoutWeatherBinding
import com.accenture.beecycle.domain.models.Weather
import java.util.*

class WeatherView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : FrameLayout(context, attrs, defStyle) {

    private val binding = LayoutWeatherBinding.inflate(
        LayoutInflater.from(context), this, true
    )

    fun loading() {
        binding.weatherContent.isInvisible = true
        binding.weatherLoadingAnimationView.isVisible = true
    }

    fun setWeather(weather: Weather?) {
        binding.weatherContent.isVisible = true
        binding.weatherLoadingAnimationView.isVisible = false

        weather?.let {
            binding.weatherTemperature.text =
                String.format(Locale.getDefault(), "%.0f°", weather.temperature)
            binding.weatherDescription.text = weather.title
            binding.weatherHumidity.text =
                String.format(Locale.getDefault(), "%d%%", weather.humidity)
            binding.weatherWind.text = String.format(
                Locale.getDefault(),
                resources.getString(R.string.wind_unit_label),
                weather.windSpeed
            )
            binding.weatherAnimationView.setAnimation(getWeatherAnimation(weather.imageId))
            binding.weatherAnimationView.playAnimation()
        }
    }

    private fun getWeatherAnimation(weatherCode: Int?): Int {
        return weatherCode?.let {
            when {
                weatherCode / 100 == 2 -> R.raw.storm_weather
                weatherCode / 100 == 3 -> R.raw.rainy_weather
                weatherCode / 100 == 5 -> R.raw.rainy_weather
                weatherCode / 100 == 6 -> R.raw.snow_weather
                weatherCode / 100 == 7 -> R.raw.unknown
                weatherCode == 800 -> R.raw.clear_day
                weatherCode == 801 -> R.raw.few_clouds
                weatherCode == 803 -> R.raw.broken_clouds
                weatherCode / 100 == 8 -> R.raw.cloudy_weather
                else -> R.raw.unknown
            }
        } ?: R.raw.unknown
    }
}