package com.bassjacob.ylj

import com.bassjacob.ylj.server.ChatApplication
import io.ktor.application.Application

fun Application.main() {
    ChatApplication().run { main() }
}
