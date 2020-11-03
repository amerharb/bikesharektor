package se.hkr.bikesharektor.repos

import se.hkr.bikesharektor.models.Bike
import se.hkr.bikesharektor.models.Station

class StationRepository {
    fun save(station: Station) = MockDatabase.stations.add(station)

    fun delete(stationId: Int) = MockDatabase.stations.removeIf { it.id == stationId }

    fun getById(stationId: Int) = MockDatabase.stations.firstOrNull { it.id == stationId }

    fun getAll() = MockDatabase.stations

    init {
        // add mock data
        val bike104 = Bike(104)
        val bike105 = Bike(105)
        val bike106 = Bike(106)
        val bike107 = Bike(107)
        val bike108 = Bike(108)
        val bike109 = Bike(109)
        MockDatabase.bikes.add(bike104)
        MockDatabase.bikes.add(bike105)
        MockDatabase.bikes.add(bike106)
        MockDatabase.bikes.add(bike107)
        MockDatabase.bikes.add(bike108)
        MockDatabase.bikes.add(bike109)

        bike105.currentStationId = 1
        bike106.currentStationId = 1
        val currentBikesStation1 = mutableListOf(bike105, bike106)
        bike109.destinationStationId = 1
        val hiredBikedStation1 = mutableListOf(bike109)
        MockDatabase.stations.add(
            Station(1, "station 1", 10, 10, 12, currentBikesStation1, hiredBikedStation1)
        )

        bike107.currentStationId = 2
        bike108.currentStationId = 2
        val currentBikesStation2 = mutableListOf(bike107, bike108)
        bike104.destinationStationId = 2
        val hiredBikedStation2 = mutableListOf(bike104)
        MockDatabase.stations.add(
            Station(2, "station 2", 100, -10, 6, currentBikesStation2, hiredBikedStation2)
        )
        // end mocking
    }
}
