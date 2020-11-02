package se.hkr.bikesharektor.models

data class Bike(val id: Int = 0,
                var currentStationId: Int = -1, // -1 during bike rent
                var destinationStationId: Int = -1, // -1 when parked
)
