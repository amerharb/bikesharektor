package se.hkr.bikesharektor.services

import se.hkr.bikesharektor.models.Station
import se.hkr.bikesharektor.repos.StationRepository

class StationService(private val stationRepository: StationRepository) {
    fun addStation(station: Station) = stationRepository.save(station)

    fun removeStation(id: Int) = stationRepository.delete(id)

    fun getStation(id: Int) = stationRepository.getById(id)

    fun getAllStations() = stationRepository.getAll()
}
