package com.bassjacob.ylj.koans

import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec

class ShowTest : StringSpec({
    "Id show stringifies an id" {
        Id.show<Id<Int>>().run { Id(1).show() } shouldBe "Id( 1 )"
    }

    "Id is showable" {
        showShowable(Id.show(), Id("foo")) shouldBe "Id( foo )"
    }

    "nested Id is showable" {
        showNestedId(String.show(), Id(Id("foo"))) shouldBe "Id( Id( foo ) )"
    }
})