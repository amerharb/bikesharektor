package se.hkr.bikesharektor.repos

import se.hkr.bikesharektor.models.Bike

class BikeRepository {
    fun getById(bikeId: Int): Bike? {
        return MockDatabase.bikes.firstOrNull { it.id == bikeId }
    }

    val all: List<Bike>
        get() = MockDatabase.bikes

}
