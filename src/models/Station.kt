package se.hkr.bikesharektor.models

data class Station(
    val id: Int,
    val name: String,
    val latitude: Int,
    val longitude: Int,
    val numberOfSlots: Int,
    val bikes: MutableList<Bike> = mutableListOf(), // currently parked at the station
    val hiredBikesToReturn: MutableList<Bike> = mutableListOf(),
) {
    val availableSlotsTotal = numberOfSlots - bikes.size - hiredBikesToReturn.size

    @Throws(Exception::class)
    fun addBike(bike: Bike) {
        val findBike = hiredBikesToReturn.stream()
            .filter { b: Bike -> b.id == bike.id }
            .findFirst()
            .orElse(null)
        if (findBike != null) {
            throw Exception("can not add bike already exists")
        }
        if (availableSlotsTotal <= 0) {
            throw Exception("no slot available")
        }
        bike.currentStationId = id
        bike.destinationStationId = -1
        bikes.add(bike)
    }

    @Throws(Exception::class)
    fun takeBike(bikeId: Int, destinationStation: Station) {
        val findBike = bikes
            .filter { it.id == bikeId }
            .firstOrNull()
            ?: throw Exception("can not find bike")

        bikes.remove(findBike)
        findBike.currentStationId = -1
        findBike.destinationStationId = destinationStation.id
        destinationStation.bookSlot(findBike)
    }

    @Throws(Exception::class)
    private fun bookSlot(bike: Bike) {
        if (availableSlotsTotal <= 0) {
            throw Exception("there no available slot to book")
        }
        hiredBikesToReturn.add(bike)
    }

    @Throws(Exception::class)
    fun returnBike(bikeId: Int) {
        val findBike = hiredBikesToReturn
            .filter { it.id == bikeId }
            .firstOrNull()
            ?: throw Exception("can not return a bike not booked to return")
        hiredBikesToReturn.remove(findBike)
        findBike.currentStationId = id
        findBike.destinationStationId = -1
        bikes.add(findBike)
    }
}
