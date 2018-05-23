package com.bassjacob.ylj.koans

import arrow.Kind
import arrow.core.*
//import arrow.core.ForId
import arrow.core.Tuple2
//import arrow.core.fix
import arrow.instance

interface Functor<F> {
    fun <A, B> Kind<F, A>.map(f: (A) -> B): Kind<F, B> //F is a functor through a witness - fn takes from A -> B

    fun <A, B> Kind<F, A>.leftMap(b: B): Kind<F, B> = map { b } // only way to produce value of type B is via the parameter

    fun <A> Kind<F, A>.void(): Kind<F, Unit> = /*map { Unit }*/ leftMap(Unit) // composability, once define shape, get functions for free

    fun <A, B> Kind<F, A>.fproduct(f: (A) -> B): Kind<F, Tuple2<A, B>> = TODO()

    fun <A, B> Kind<F, A>.tupleLeft(b: B): Kind<F, Tuple2<B, A>> = TODO()

    fun <A, B> Kind<F, A>.tupleRight(b: B): Kind<F, Tuple2<A, B>> = TODO()

    fun <B, A : B> Kind<F, A>.widen(): Kind<F, B> = TODO()
}

@instance(Id::class)
interface IdFunctorInstance : Functor<ForId> {
    override fun <A, B> Kind<ForId, A>.map(f: (A) -> B): Kind<ForId, B> = Id.map(f, this.fix())
}

fun <A, B> Id.Companion.map(f: (A) -> B, Fa: Id<A>): Id<B> = Id(f(Fa.t)) //Campanion object - static set of functions available, Id has class param t

// now do the same for Option
// then can re-use optionMap instead of Option.Campanion.map
@instance(Option::class)
interface OptionFunctorInstance<A>: Functor<ForOption> {
    override fun <A, B> Kind<ForOption, A>.map(f: (A) -> B): Kind<ForOption, B> = Option.map(f, this.fix())
}

//sealed class
// can have optionMap instead of Option.Campanion.map
private fun <A, B> Option.Companion.map(f: (A) -> B, Fa: Option<A>): Option<B> {
    return when(Fa) {
        is Option.None -> Option.None()
        is Option.Some -> Option.Some(f(Fa.t)) //Smart cast - super type and prove that is of subtype, then referenceable as subtype => Fa is of Option.Some
    }
//    if (Fa is Option.None) {
//        return Option.None()
//    } else {
//        return Option.Some(f(Fa.t))
//    }

}

//NOTE: list does not have a campanion
//fun <A, B> List.Companion.map(f: (A) -> B, Fa: List<A>) : {
//    if (Fa.size == 1) {
//       return listOf(f(Fa.head))
//    } else {
//       return f(Fa.get(0)).plus(List.Map(f, Fa.subList(1, Fa.size)))
//    }
//}