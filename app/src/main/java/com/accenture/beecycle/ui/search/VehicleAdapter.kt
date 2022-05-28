package com.accenture.beecycle.ui.search

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import com.accenture.beecycle.common.BaseAdapter
import com.accenture.beecycle.databinding.ItemVehicleBinding
import com.accenture.beecycle.domain.models.Vehicle

class VehicleAdapter : BaseAdapter<ItemVehicleBinding, Vehicle>() {

    override fun presentBinding(parent: ViewGroup): ItemVehicleBinding =
        ItemVehicleBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder<ItemVehicleBinding>, position: Int) {
        val vehicle = data?.get(position)
        with(holder.binding) {
            ivVehicleAvatar.setImageResource(vehicle?.vehicleAvatar!!)
            tvVehicleName.text = vehicle.name
            tvVehicleCost.text = getTripCostMessage(vehicle)
            tvVehicleDuration.text = "${vehicle.tripDuration} min"
            tvVehicleEmission.text = getTripEmission(vehicle)
        }
    }

    private fun getTripEmission(vehicle: Vehicle?) = if (vehicle?.isBicycle == true) {
        "no emission"
    } else {
        "${vehicle?.tripEmission}g CO2"
    }

    private fun getTripCostMessage(vehicle: Vehicle?) = if (vehicle?.isBicycle == true) {
        "free ride"
    } else {
        "$${vehicle?.tripCost}"
    }

}
