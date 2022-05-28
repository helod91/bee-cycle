package com.accenture.beecycle.ui.widgets

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.accenture.beecycle.databinding.LayoutSearchResultBinding
import com.accenture.beecycle.domain.models.GeoSearchResult

class SearchResultView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : FrameLayout(context, attrs, defStyle) {

    private val binding = LayoutSearchResultBinding.inflate(
        LayoutInflater.from(context), this, true
    )

    fun loading() {
//        binding.weatherContent.isInvisible = true
//        binding.weatherLoadingAnimationView.isVisible = true
    }

    fun setResults(weather: List<GeoSearchResult>) {
        /*binding.weatherContent.isVisible = true
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
            binding.weatherAnimationView.playAnimation()*/
    }
}

