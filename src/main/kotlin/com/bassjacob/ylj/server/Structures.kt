package com.bassjacob.ylj.server

import arrow.core.Option

data class Command(val command: String, val data: Option<String>) {
    companion object
}

data class Member(val id: String, val name: String) {
    companion object
}

sealed class Operation {
    companion object

    class Message(val sender: String, val message: String) : Operation()
    class Unknown(val sender: String) : Operation()
}
