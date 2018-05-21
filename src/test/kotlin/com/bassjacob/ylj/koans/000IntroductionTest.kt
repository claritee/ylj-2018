package com.bassjacob.ylj.koans

import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec

class IntroductionTest: StringSpec({
    "addOne adds One" {
        addOne(0) shouldBe 1
    }

    "id returns what is passed" {
        id(0) shouldBe 0
        id("") shouldBe ""
        id(listOf(1, 2, 3)) shouldBe listOf(1, 2, 3)
    }

    "const returns the first parameter" {
        const(1, 2) shouldBe 1
        const("a", 3) shouldBe "a"
    }

    "filterGreaterThanThreeExtension strips out any numbers less than three" {
        listOf(1, 2, 3).filterGreaterThanThreeExtension() shouldBe listOf()
        listOf(1, 2, 3, 4, 5, 6).filterGreaterThanThreeExtension() shouldBe listOf(4, 5, 6)
    }
})
