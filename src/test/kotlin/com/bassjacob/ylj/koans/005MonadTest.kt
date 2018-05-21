package com.bassjacob.ylj.koans

import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec

class IdMonadTest : StringSpec({
    "just takes a value and returns it wrapped in an Id" {
        Id.monad().run { Id.just(1) }.t shouldBe 1
    }

    "flatMap takes a function that returns an Id and returns an Id" {
        Id.monad().run { Id.just(1).flatMap { Id(2) } }.fix().t shouldBe 2
    }

    "map still maps" {
        Id.monad().run { Id.just(1).map { it + 1 } }.fix().t shouldBe 2
    }

    "ap still aps" {
        Id.monad().run { Id.just(1).ap<Int, Int>(just({ a -> a + 1 })) }.fix().t shouldBe 2
    }

    "flatten takes a nested monad and returns it flattened" {
        Id.monad().run { Id.just(Id.just(1)).flatten() }.fix().t shouldBe 1
    }
})
