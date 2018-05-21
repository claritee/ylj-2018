package com.bassjacob.ylj.koans

import arrow.core.getOrElse
import arrow.effects.DeferredK
import arrow.effects.k
import arrow.effects.monadError
import arrow.effects.unsafeAttemptSync
import arrow.effects.unsafeRunSync
import arrow.typeclasses.bindingCatch
import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.delay
import kotlinx.coroutines.experimental.runBlocking

fun deferredCalc(): List<Deferred<Int>> {
    return (1..1000).map { n ->
        val d = Math.random() * 1000
        async {
            delay(d.toInt())
            if (d > 500) {
                println("error $d")
                throw Exception("thrown $d")
            }

            println(n)
            n
        }
    }
}

fun slowlyConvertToString(x: Int): Deferred<String> {
    val d = Math.random() * 10

    return async {
        delay(d.toInt())
        x.toString()
    }
}

// run this commenting the exception in the function on and off
// notice how it doesn't block
fun usingCoroutine() {
    runBlocking { deferredCalc().sumBy { it.await() } }.let(::println)
}

// if we want to be sure it doesn't throw we need to catch around the await
fun usingCoroutineCatching() {
    runBlocking { deferredCalc().sumBy { try { it.await() } catch(e: Exception) { 0 } } }.let(::println)
}

// this time we decorate each deferred with the arrow DeferredK class
// this gives us a number of helper methods that abstract over
// the popular async libs, as well as the builtin coroutines
// this version will still throw
fun usingDeferred() {
    deferredCalc().fold(0, { i, d -> i + d.k().unsafeRunSync() })
}

// this time we introduce unsafeAttemptSync which returns a Try
// we can use getOrElse to either grab the returned value or some default
fun usingDeferredAttempt() {
    deferredCalc().fold(0, { i, d -> i + d.k().unsafeAttemptSync().getOrElse { 0 } })
}

// let's introduce what's known as a comprehension. these are similar to do-notation from Haskell
fun usingComprehension() {
    DeferredK.monadError().run {
        deferredCalc().map { bindingCatch { it.k().bind() }.unsafeAttemptSync().getOrElse { 0 } }.sumBy { it }
    }.let(::println)
}

// that doesn't seem much better, but it introduces an important concept. what happens if we have a pipeline of deferred operations to run. with the previous methods we are forced to handle any errors or exception in each attempt separately. with a comprehension, any of the events in the pipeline can throw and we can catch and handle without worrying about interrupting the rest of the pipeline

fun usingComprehensionsMany() {
    DeferredK.monadError().run {
        deferredCalc().map {
            bindingCatch {
                val x = it.k().bind()
                slowlyConvertToString(x).k().bind()
            }.unsafeAttemptSync().getOrElse { "error" }
        }.joinToString("|")
    }.let(::println)
}
