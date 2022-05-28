package com.accenture.beecycle.ui.main

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import com.accenture.beecycle.R
import com.accenture.beecycle.common.BaseAdapter
import com.accenture.beecycle.databinding.ItemBicycleCardBinding
import com.accenture.beecycle.domain.models.Bicycle
import com.accenture.beecycle.domain.models.RIDE_TYPE
import com.accenture.beecycle.ui.search.SearchActivity
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class BicycleAdapter : BaseAdapter<ItemBicycleCardBinding, Bicycle>() {

    override fun presentBinding(parent: ViewGroup): ItemBicycleCardBinding =
        ItemBicycleCardBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

    override fun onBindViewHolder(holder: ViewHolder<ItemBicycleCardBinding>, position: Int) {
        val bicycle = data?.get(position)
        with(holder.binding) {
            bicycleName.text = bicycle?.name
            bicycleBrand.text = bicycle?.brand
            bicycleSpeedValue.text = "${bicycle?.avgSpeed} km/h"
            bicycleDistanceValue.text = "${bicycle?.distanceTraveled} km"
            bicycleImpactValue.text = "${bicycle?.impact} CO2"
            bicycleMoneySaved.text = "You saved ${bicycle?.moneySaved}RON this week!"

            bicycleAnimation.setAnimation(getRideAnimation(bicycle?.rideType))
            bicycleAnimation.playAnimation()

            if (bicycle?.rideType == RIDE_TYPE.ADD_BIKE) {
                hideViews(bicycleName, bicycleBrand, bicycleSpeed, bicycleDistance, bicycleImpact, bicycleMoneySaved, bicycleAnimation, bicycleOpenRoutePlanner)
                showViews(bicycleAddBikeLabel, bicycleAddBikeAnimation)
            } else {
                showViews(bicycleName, bicycleBrand, bicycleSpeed, bicycleDistance, bicycleImpact, bicycleMoneySaved, bicycleAnimation, bicycleOpenRoutePlanner)
                hideViews(bicycleAddBikeLabel, bicycleAddBikeAnimation)

                bicycleOpenRoutePlanner.setOnClickListener {
                    val openSearch = Intent(root.context, SearchActivity::class.java)
                    root.context.startActivity(openSearch)
                }
            }
        }
    }

    private fun getRideAnimation(rideType: RIDE_TYPE?) : Int {
        return when(rideType) {
            RIDE_TYPE.BICYCLE -> R.raw.ic_cycling
            RIDE_TYPE.E_BICYCLE -> R.raw.ic_ebike
            RIDE_TYPE.E_SCOOTER -> R.raw.ic_electric_scooter
            else -> R.raw.ic_cycling
        }
    }

    private fun hideViews(vararg view: View) {
        view.forEach { it.isInvisible = true }
    }

    private fun showViews(vararg view: View) {
        view.forEach { it.isVisible = true }
    }
}