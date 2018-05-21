package com.bassjacob.ylj.koans

import arrow.Kind
import arrow.core.Tuple2
import arrow.instance

interface Monad<F> : Applicative<F> {
    fun <A, B> Kind<F, A>.flatMap(f: (A) -> Kind<F, B>): Kind<F, B>

    override fun <A, B> Kind<F, A>.map(f: (A) -> B): Kind<F, B>

    override fun <A, B> Kind<F, A>.ap(ff: Kind<F, (A) -> B>): Kind<F, B>

    fun <A> Kind<F, Kind<F, A>>.flatten(): Kind<F, A> = TODO()

    fun <A, B> Kind<F, A>.mproduct(f: (A) -> Kind<F, B>): Kind<F, Tuple2<A, B>> = TODO()
}

@instance(Id::class)
interface IdMonadInstance : Monad<ForId>, IdApplicativeInstance {
    override fun <A, B> Kind<ForId, A>.flatMap(f: (A) -> Kind<ForId, B>): Kind<ForId, B> {
        return Id.flatMap({ f(it).fix() }, this.fix())
    }

    override fun <A, B> Kind<ForId, A>.map(f: (A) -> B): Kind<ForId, B> {
        // see if you can implement this using only just and flatMap
        TODO()
    }

    override fun <A, B> Kind<ForId, A>.ap(ff: Kind<ForId, (A) -> B>): Kind<ForId, B> {
        // see if you can implement this using only Id.flatMap and just
        // this one is a little tricky
        TODO()
    }
}

fun <A, B> Id.Companion.flatMap(f: (A) -> Id<B>, Fa: Id<A>): Id<B> = TODO()

// Now do the same for Option

@instance(Option::class)
interface OptionMonadInstance<A>/*: Functor<ForOption>*/
