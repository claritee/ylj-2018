package com.bassjacob.ylj.server

import arrow.core.Option
import arrow.core.Try
import arrow.data.ListK
import arrow.effects.DeferredK
import io.ktor.http.cio.websocket.Frame
import io.ktor.http.cio.websocket.WebSocketSession
import java.util.concurrent.ConcurrentHashMap

fun <A, B> ConcurrentHashMap<A, B>.safeGet(key: A): Option<B> {
    TODO()
}

// have a look at server/ChatRepository.kt for the potential operations
// an operation should contain all the information needed to perform it (who to message, who to say its from, what the operation is)
fun Operation.Companion.parse(id: String, input: String): Operation {
    TODO()
}

fun defaultL(): ListK<DeferredK<Unit>> {
    TODO()
}

fun ConcurrentHashMap<String, MutableList<WebSocketSession>>.broadcast(message: String): ListK<DeferredK<Unit>> {
    TODO()
}

fun List<WebSocketSession>.send(frame: Frame): ListK<DeferredK<Unit>> {
    TODO()
}

fun List<WebSocketSession>.sendList(frames: ListK<Frame>): ListK<DeferredK<Unit>> {
    TODO()
}

fun Operation.Companion.perform(o: Operation, repository: ChatRepository): ListK<Try<Unit>> {
    TODO()
}
