package com.bassjacob.ylj.koans

import arrow.data.k
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec

class IdApplicativeTest : StringSpec({
    "just takes a value and returns it wrapped in an Id" {
        Id.applicative().run { Id.just(1) }.t shouldBe 1
    }

    "ap takes an Id(fn) and an Id(v) and return Id(v)" {
        Id.applicative().run { Id(1).ap<Int, Int>(just({ a -> a + 1 })) }.fix().t shouldBe 2
    }

    "map operates the same as before" {
        Id.applicative().run { Id(1).map { it + 1 } }.fix().t shouldBe 2
    }

    "product returns a tuple of value and next value" {
        Id.applicative().run { Id(1).product(Id(2)) }.fix().t.let {
            it.a shouldBe 1
            it.b shouldBe 2
        }
    }

    "map2 takes two applicative values, calls a function with their values and wraps the result" {
        Id.applicative().run { Id(1).map2(Id(2), { (a, b) -> a + b }) }.fix().t shouldBe 3
    }

    "sequence takes a list of Id and returns Id of a list" {
        Id.applicative().run { listOf(Id(1), Id(2), Id(3)).k().sequence() }.fix().t.list shouldBe listOf(1, 2, 3)
    }
})
