package se.hkr.bikesharektor.models

import java.util.*

data class Station(
        val id: Int,
        val name: String,
        val latitude: Int,
        val longitude: Int,
        val numberOfSlots: Int,
        val bikes: List<Bike> = emptyList(), // currently parked at the station
        val hiredBikesToReturn: List<Bike> = emptyList(),
)
