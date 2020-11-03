package se.hkr.bikesharektor.repos

class BikeRepository {
    fun getById(bikeId: Int) = MockDatabase.bikes.firstOrNull { it.id == bikeId }

    fun getAll() = MockDatabase.bikes
}
