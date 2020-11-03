package se.hkr.bikesharektor.repos

import se.hkr.bikesharektor.models.Bike
import se.hkr.bikesharektor.models.Station
import se.hkr.bikesharektor.repos.MockDatabase

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
        val currentBikesStation1 = listOf(bike106, bike105)
        val hiredBikedStation1 = listOf(bike109)
        MockDatabase.stations.add(
                Station(1, "station 1", 10, 10, 12, currentBikesStation1, hiredBikedStation1)
        )
        val currentBikesStation2 = listOf(bike107, bike108)
        val hiredBikedStation2 = listOf(bike104)
        MockDatabase.stations.add(Station(2, "station 2", 100, -10, 6, currentBikesStation2, hiredBikedStation2))
        // end mocking
    }
}
