package se.hkr.bikesharektor

import io.ktor.application.*
import io.ktor.response.*
import io.ktor.request.*
import io.ktor.routing.*
import io.ktor.http.*
import io.ktor.html.*
import kotlinx.html.*
import io.ktor.auth.*
import io.ktor.client.*
import io.ktor.client.features.auth.*

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {
//    install(Authentication) {
//        basic("myBasicAuth") {
//            realm = "Ktor Server"
//            validate { if (it.name == "test" && it.password == "password") UserIdPrincipal(it.name) else null }
//        }
//    }
//
//    val client = HttpClient() {
//        install(Auth) {
//        }
//    }

    routing {
        get("/") {
            call.respondText("HELLO WORLD!", contentType = ContentType.Text.Plain)
        }

        route("/bikes") {
            get {
                // return a array of json of bikes
                call.respond(HttpStatusCode.OK, "todo")
            }

            get("/{id}") {
                // return a array of json of bikes
                call.respond(HttpStatusCode.OK, "todo")
            }
        }

        get("/html-dsl") {
            call.respondHtml {
                body {
                    h1 { +"HTML" }
                    ul {
                        for (n in 1..10) {
                            li { +"$n" }
                        }
                    }
                }
            }
        }

//        authenticate("myBasicAuth") {
//            get("/protected/route/basic") {
//                val principal = call.principal<UserIdPrincipal>()!!
//                call.respondText("Hello ${principal.name}")
//            }
//        }
    }
}

