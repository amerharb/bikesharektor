package se.hkr.bikesharektor.services

import se.hkr.bikesharektor.repos.BikeRepository

class BikeService(private val bikeRepository: BikeRepository) {
    fun getBike(id: Int) = bikeRepository.getById(id)

    fun getAllBikes() = bikeRepository.getAll()
}
