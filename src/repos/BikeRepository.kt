package se.hkr.bikesharektor.repos

import se.hkr.bikesharektor.models.Bike

class BikeRepository {
    fun getById(bikeId: Int): Bike? {
        return MockDatabase.bikes.firstOrNull { it.id == bikeId }
    }

    fun getAll() = MockDatabase.bikes
}
