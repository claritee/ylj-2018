package com.bassjacob.ylj.server

import arrow.core.None
import arrow.core.getOrElse
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import java.util.concurrent.ConcurrentHashMap

class OptionalRequiredFunctionsTest : StringSpec({
    "ConcurrentHashMap safeGet returns None if no value for key" {
        ConcurrentHashMap<String, String>().safeGet("any") shouldBe None
    }

    "ConcurrentHashMap safeGet returns Some(val) if value for key" {
        ConcurrentHashMap<String, String>().apply {
            set("any", "value")
            safeGet("any").getOrElse { throw Exception("should be a value") } shouldBe "value"
        }
    }

    "Operation parse, when input doesn't start with a slash" {
        (Operation.parse("id", "message") is Operation.Message) shouldBe true
    }

    "Operation parse, when input begins with a slash but has no body" {
        (Operation.parse("id", "message") is Operation.Unknown) shouldBe true
    }
})
