package com.bassjacob.ylj.server

import io.ktor.application.Application
import io.ktor.application.ApplicationCallPipeline
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.content.defaultResource
import io.ktor.content.resources
import io.ktor.content.static
import io.ktor.features.CallLogging
import io.ktor.features.DefaultHeaders
import io.ktor.routing.routing
import io.ktor.sessions.Sessions
import io.ktor.sessions.cookie
import io.ktor.sessions.get
import io.ktor.sessions.sessions
import io.ktor.sessions.set
import io.ktor.util.nextNonce
import io.ktor.websocket.WebSockets
import io.ktor.websocket.webSocket
import java.time.Duration

data class ChatSession(val id: String)

class ChatApplication {
    private val server = ChatRepository()

    fun Application.main() {
        install(DefaultHeaders)
        install(CallLogging)
        install(WebSockets) {
            pingPeriod = Duration.ofMinutes(1)
        }

        routing {
            install(Sessions) {
                cookie<ChatSession>("SESSION")
            }

            intercept(ApplicationCallPipeline.Infrastructure) {
                if (call.sessions.get<ChatSession>() == null) {
                    call.sessions.set(ChatSession(nextNonce()))
                }
            }

            webSocket("/ws") { websocketHandler(server, this, call) }

            static {
                defaultResource("index.html", "web")
                resources("web")
            }
        }
    }
}
