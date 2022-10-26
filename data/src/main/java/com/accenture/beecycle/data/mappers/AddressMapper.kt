package com.accenture.beecycle.data.mappers

import android.location.Address
import com.accenture.beecycle.domain.models.GeoSearchResult
import org.koin.core.annotation.Single

@Single
class AddressMapper {
    fun toSingleLineAddress(currentAddresses: List<Address>): List<GeoSearchResult> {
        return currentAddresses.map {
            var displayAddress = it.getAddressLine(0).toString() + "\n"
            for (i in 1 until it.maxAddressLineIndex) {
                displayAddress += it.getAddressLine(i).toString() + ", "
            }
            displayAddress = displayAddress.substring(0, displayAddress.length - 2)

            GeoSearchResult(displayAddress)
        }
    }
}
