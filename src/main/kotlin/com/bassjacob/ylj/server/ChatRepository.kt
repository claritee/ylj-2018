package com.bassjacob.ylj.server

import arrow.core.Option
import arrow.core.Try
import arrow.core.getOrElse
import arrow.data.ListK
import arrow.data.k
import arrow.effects.DeferredK
import io.ktor.application.ApplicationCall
import io.ktor.http.cio.websocket.CloseReason
import io.ktor.http.cio.websocket.Frame
import io.ktor.http.cio.websocket.WebSocketSession
import io.ktor.http.cio.websocket.close
import io.ktor.http.cio.websocket.readText
import io.ktor.sessions.get
import io.ktor.sessions.sessions
import io.ktor.websocket.DefaultWebSocketServerSession
import kotlinx.coroutines.experimental.channels.consumeEach
import java.util.LinkedList
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.CopyOnWriteArrayList
import java.util.concurrent.atomic.AtomicInteger

fun receiveMessage(repository: ChatRepository, id: String, command: String): ListK<Try<Unit>> {
    return Operation.parse(id, command).let { Operation.perform(it, repository) }
}

suspend fun websocketHandler(repository: ChatRepository, websocketSession: DefaultWebSocketServerSession, call: ApplicationCall) {
    with(websocketSession) {
        val session = call.sessions.get<ChatSession>()

        if (session == null) {
            close(CloseReason(CloseReason.Codes.VIOLATED_POLICY, "No session"))
            return
        }

        repository.memberJoin(session.id, this)

        try {
            incoming.consumeEach {
                when (it) {
                    is Frame.Text -> receiveMessage(repository, session.id, it.readText())
                    else -> DeferredK.just(Unit)
                }
            }
        } finally {
            repository.memberLeft(session.id, this)
        }
    }
}

class ChatRepository {
    val usersCounter = AtomicInteger()
    val memberNames = ConcurrentHashMap<String, String>()
    val members = ConcurrentHashMap<String, MutableList<WebSocketSession>>()
    val lastMessages = LinkedList<String>()

    fun formatMessage(sender: String, message: String): String {
        val name = memberNames.safeGet(sender).getOrElse { sender }
        return "[$name] $message"
    }

    fun memberJoin(id: String, socket: WebSocketSession): ListK<DeferredK<Unit>> {
        val name = memberNames.computeIfAbsent(id) { "user${usersCounter.incrementAndGet()}" }
        val list = members.computeIfAbsent(id) { CopyOnWriteArrayList<WebSocketSession>() }

        list.add(socket)

        if (list.size == 1) {
            broadcast("server", "Member joined: $name.")
        }

        val messages = synchronized(lastMessages) { lastMessages.toList() }.k().map { Frame.Text(it) }

        return MutableList(1, { _ -> socket }).sendList(messages)
    }

    fun memberRenamed(member: String, to: String): ListK<DeferredK<Unit>> {
        val oldName = memberNames.put(member, to) ?: member
        return broadcast("server", "Member renamed from $oldName to $to")
    }

    fun memberLeft(member: String, socket: WebSocketSession): Option<ListK<DeferredK<Unit>>> {
        val connections = members.safeGet(member)

        return connections.map {
            it.remove(socket)

            if (it.isEmpty()) {
                val name = memberNames[member] ?: member
                broadcast("server", "Member left: $name.")
            } else {
                listOf(DeferredK.just(Unit)).k()
            }
        }
    }

    fun sendToMember(memberId: String, text: Frame.Text): Option<ListK<DeferredK<Unit>>> {
        return members.safeGet(memberId).map { it.send(text) }
    }

    fun who(sender: String): Option<ListK<DeferredK<Unit>>> {
        val text = Frame.Text(memberNames.values.joinToString(prefix = "[server::who] "))
        return sendToMember(sender, text)
    }

    fun help(sender: String): Option<ListK<DeferredK<Unit>>> {
        val text = Frame.Text("[server::help] Possible commands are: /user, /help and /who")
        return sendToMember(sender, text)
    }

    fun sendTo(recipient: String, sender: String, message: String): Option<ListK<DeferredK<Unit>>> {
        val text = Frame.Text(formatMessage(sender, message))
        return sendToMember(recipient, text)
    }

    fun message(sender: String, message: String): ListK<DeferredK<Unit>> {
        synchronized(lastMessages) {
            lastMessages.add(formatMessage(sender, message))
            if (lastMessages.size > 100) {
                lastMessages.removeFirst()
            }
        }

        return broadcast(sender, message)
    }

    fun broadcast(sender: String, message: String): ListK<DeferredK<Unit>> {
        return members.broadcast(formatMessage(sender, message))
    }
}