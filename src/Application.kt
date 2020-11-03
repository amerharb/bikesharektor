package se.hkr.bikesharektor

import io.ktor.application.*
import io.ktor.features.*
import io.ktor.gson.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*
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
    data class X(val p1:Int)

    install(ContentNegotiation) {
        gson {}
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
                }
                call.respond(HttpStatusCode.NotFound, "Station with id [$id] not found")
            }
        }
    }
}

