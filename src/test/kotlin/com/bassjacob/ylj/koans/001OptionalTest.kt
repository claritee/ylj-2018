package com.bassjacob.ylj.koans

import arrow.core.None
import arrow.core.Some
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec

class OptionalTest : StringSpec({
    "takesAnOptionalString given null returns the default value" {
        takesAnOptionalString(null, "default") shouldBe "default"
    }

    "takesAnOptionalString given a value returns the value" {
        takesAnOptionalString("alternative", "default") shouldBe "alternative"
    }

    "takesAnOptionalString2 given null returns the default value" {
        takesAnOptionalString2(null, "default") shouldBe "default"
    }

    "takesAnOptionalString2 given a value returns the value" {
        takesAnOptionalString2("alternative", "default") shouldBe "alternative"
    }

    "takesAnOptionalString3 given null returns the default value" {
        takesAnOptionalString3(null, "default") shouldBe "default"
    }

    "takesAnOptionalString3 given a value returns the value" {
        takesAnOptionalString3("alternative", "default") shouldBe "alternative"
    }

    "takesAnOptionalString4 given null returns the default value" {
        takesAnOptionalString4(None, "default") shouldBe "default"
    }

    "takesAnOptionalString4 given a value returns the value" {
        takesAnOptionalString4(Some("alternative"), "default") shouldBe "alternative"
    }
})
