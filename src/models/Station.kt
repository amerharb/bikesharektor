package se.hkr.bikesharektor.models

data class Station(
    val id: Int,
    val name: String,
    val latitude: Int,
    val longitude: Int,
    val numberOfSlots: Int,
    val bikes: MutableList<Bike> = mutableListOf(), // currently parked at the station
    val hiredBikesToReturn: MutableList<Bike> = mutableListOf(),
){
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
}
