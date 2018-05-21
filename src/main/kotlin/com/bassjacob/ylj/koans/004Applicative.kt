package com.bassjacob.ylj.koans

import arrow.Kind
import arrow.core.Tuple2
import arrow.data.ListK
import arrow.instance

interface Applicative<F> : Functor<F> {
    fun <A> just(a: A): Kind<F, A>

    fun <A, B> Kind<F, A>.ap(ff: Kind<F, (A) -> B>): Kind<F, B>

    override fun <A, B> Kind<F, A>.map(f: (A) -> B): Kind<F, B>

    fun <A, B> Kind<F, A>.product(fb: Kind<F, B>): Kind<F, Tuple2<A, B>> = TODO()

    fun <A, B, Z> Kind<F, A>.map2(fb: Kind<F, B>, f: (Tuple2<A, B>) -> Z): Kind<F, Z> = TODO()

    fun <A> ListK<Kind<F, A>>.sequence(): Kind<F, ListK<A>> = TODO()
}

@instance(Id::class)
interface IdApplicativeInstance : Applicative<ForId>, IdFunctorInstance {
    override fun <A> just(a: A): Kind<ForId, A> = Id.just(a)

    override fun <A, B> Kind<ForId, A>.ap(ff: Kind<ForId, (A) -> B>): Kind<ForId, B> = Id.ap(ff.fix(), this.fix())

    override fun <A, B> Kind<ForId, A>.map(f: (A) -> B): Kind<ForId, B> {
        // see if you can rewrite map only using Id.ap and Id.just
        TODO()
    }
}

fun <T> Id.Companion.just(t: T): Id<T> = TODO()

fun <T, B> Id.Companion.ap(f: Id<(T) -> B>, Fa: Id<T>): Id<B> = TODO()

// Now do the same for Option

@instance(Option::class)
interface OptionApplicativeInstance<A>/*: Functor<ForOption>*/
