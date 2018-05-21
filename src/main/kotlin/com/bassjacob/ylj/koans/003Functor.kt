package com.bassjacob.ylj.koans

import arrow.Kind
import arrow.core.Tuple2
import arrow.instance

interface Functor<F> {
    fun <A, B> Kind<F, A>.map(f: (A) -> B): Kind<F, B>

    fun <A, B> Kind<F, A>.leftMap(a: B): Kind<F, B> = TODO()

    fun <A> Kind<F, A>.void(): Kind<F, Unit> = TODO()

    fun <A, B> Kind<F, A>.fproduct(f: (A) -> B): Kind<F, Tuple2<A, B>> = TODO()

    fun <A, B> Kind<F, A>.tupleLeft(b: B): Kind<F, Tuple2<B, A>> = TODO()

    fun <A, B> Kind<F, A>.tupleRight(b: B): Kind<F, Tuple2<A, B>> = TODO()

    fun <B, A : B> Kind<F, A>.widen(): Kind<F, B> = TODO()
}

@instance(Id::class)
interface IdFunctorInstance : Functor<ForId> {
    override fun <A, B> Kind<ForId, A>.map(f: (A) -> B): Kind<ForId, B> = Id.map(f, this.fix())
}

fun <A, B> Id.Companion.map(f: (A) -> B, Fa: Id<A>): Id<B> = Id(f(Fa.t))

// now do the same for Option
@instance(Option::class)
interface OptionFunctorInstance<A>/*: Functor<ForOption>*/
