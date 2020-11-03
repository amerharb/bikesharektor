package se.hkr.bikesharektor

import io.ktor.application.*
import io.ktor.features.*
import io.ktor.gson.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import se.hkr.bikesharektor.models.Bike
import se.hkr.bikesharektor.models.Station
import se.hkr.bikesharektor.repos.BikeRepository
import se.hkr.bikesharektor.repos.StationRepository
import se.hkr.bikesharektor.services.BikeService
import se.hkr.bikesharektor.services.StationService

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {
    val bikeService = BikeService(BikeRepository())
    val stationService = StationService(StationRepository())

    data class X(val p1: Int)

    install(ContentNegotiation) {
        gson {}
    }

    @Throws(Exception::class)
    fun takeBike(fromStationId: Int, bikeId: Int, destinationStationId: Int) {
        val fromStation: Station = stationService.getStation(fromStationId)
            ?:throw java.lang.Exception("can not find from station")
        val toStation: Station = stationService.getStation(destinationStationId)
            ?:throw java.lang.Exception("can not find destination station")

        fromStation.takeBike(bikeId, toStation)
    }

    routing {
        route("/bikes") {
            get {
                call.respond(HttpStatusCode.OK, bikeService.getAllBikes())
            }

            get("/{id}") {
                val id = call.parameters["id"]?.toIntOrNull()
                if (id != null) {
                    val bike = bikeService.getBike(id)
                    if (bike != null)
                        call.respond(HttpStatusCode.OK, bike)
                }
                call.respond(HttpStatusCode.NotFound, "Bike with id [$id] not found")
            }
        }

        route("/stations") {
            get {
                call.respond(HttpStatusCode.OK, stationService.getAllStations())
            }

            get("/{id}") {
                val id = call.parameters["id"]?.toIntOrNull()
                if (id != null) {
                    val station = stationService.getStation(id)
                    if (station != null)
                        call.respond(HttpStatusCode.OK, station)
                    return@get
                }
                call.respond(HttpStatusCode.NotFound, "Station with id [$id] not found")
            }

            post {
                // TODO: check unsupported body
                val station = call.receive<Station>()
                stationService.addStation(station)
                call.respond(HttpStatusCode.OK)
            }

            delete("/{id}") {
                val id = call.parameters["id"]?.toIntOrNull()
                if (id != null) {
                    if (stationService.removeStation(id)) {
                        call.respond(HttpStatusCode.OK)
                        return@delete
                    }
                }
                call.respond(HttpStatusCode.NotFound, "Station with id [$id] not found")
            }

            post("/{id}/addbike") {
                val id = call.parameters["id"]?.toIntOrNull()
                if (id == null) {
                    call.respond(HttpStatusCode.NotFound, "Station with id [$id] not found")
                    return@post
                }

                val station = stationService.getStation(id)
                if (station == null) {
                    call.respond(HttpStatusCode.NotFound, "Station with id [$id] not found")
                    return@post
                }

                val bike = call.receive<Bike>()
                // TODO: change this to service to update mock data about new bike
                station.addBike(bike)
            }

            post("/{id}/takebike/{bikeId}/to/{destinationStationId}") {
                val id = call.parameters["id"]?.toIntOrNull()
                val bikeId = call.parameters["bikeId"]?.toIntOrNull()
                val destinationStationId = call.parameters["destinationStationId"]?.toIntOrNull()

                if (id == null || bikeId == null || destinationStationId == null) {
                    call.respond(HttpStatusCode.BadRequest, "bike id, station id must be valid int")
                    return@post
                }

                val fromStation = stationService.getStation(id)
                if (fromStation == null) {
                    call.respond(HttpStatusCode.NotFound, "Station with id [$id] not found")
                    return@post
                }

                val toStation = stationService.getStation(destinationStationId)
                if (toStation == null) {
                    call.respond(HttpStatusCode.NotFound, "Station with id [$id] not found")
                    return@post
                }

                fromStation.takeBike(bikeId, toStation)
            }

        }
    }
}

