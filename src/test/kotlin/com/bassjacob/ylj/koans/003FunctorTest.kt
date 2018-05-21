package com.bassjacob.ylj.koans

import arrow.Kind
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec

class IdFunctorTest : StringSpec({
    "map runs the function in the Id.functor context" {
        runsMap(Id.functor(), { it + 1 }, Id(1)).fix().t shouldBe 2
    }

    "leftMap swaps the contained value with the provided param" {
        Id.functor().run { Id(1).leftMap("") }.fix().t shouldBe ""
    }

    "void replaces the contained value with Unit" {
        Id.functor().run { Id(1).void() }.fix().t shouldBe Unit
    }

    "fproduct returns a tuple of value and next value" {
        Id.functor().run { Id(1).fproduct { it.toString() } }.fix().t.let {
            it.a shouldBe 1
            it.b shouldBe "1"
        }
    }

    "tupleLeft returns a tuple of the value and param" {
        Id.functor().run { Id(1).tupleLeft("2") }.fix().let {
            it.t.a shouldBe "2"
            it.t.b shouldBe 1
        }
    }

    "tupleRight returns a tuple of the value and param" {
        Id.functor().run { Id(1).tupleRight("2") }.fix().let {
            it.t.a shouldBe 1
            it.t.b shouldBe "2"
        }
    }

    "widen changes the type of the value when it is a subtype of the value" {
        Id.functor().run { Id(1.0).widen<Number, Double>() }.fix().t shouldBe 1.0
    }
})

fun <F, A, B> runsMap(witness: Functor<F>, f: (A) -> B, v: Kind<F, A>): Kind<F, B> {
    return witness.run {
        v.map(f)
    }
}
