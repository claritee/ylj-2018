package com.bassjacob.ylj.koans

import arrow.Kind
import arrow.instance

/*
//This is an example of defining a very simple interface, for the Show typeclass. We define an interface with a single method, show. The interesting thing is we define it on the generic parameter A. This means that whenever we are operating in a "Show" context, any values of the same type as our A parameter are going to be extended with a method show.
 */

interface Show<A> {
    fun A.show(): String
}

/*
//We can define an instance of the typeclass using the special @instance decorator from the arrow library. In order to fully define the interface we must override the show method. Notice that we've defined Show as operating on Id<A>, which means that any values of type Id<A> will gain a show method with this implementation when operating inside a Show context.
 */

// NOTE: name of interface
@instance(Id::class)
interface IdShowInstance<A> : Show<A> {
    override fun A.show(): String {
//        return "Id( $this )"
        return "Id(${this.show()})"
    }
}

fun <A> showShowable(witness: Show<A>, value: A): String {
    return witness.run {
        value.show()
    }
}

interface StringShowInstance : Show<String> {
    override fun String.show(): String {
        return this
    }
}

//val res = showShowable(Int.show(), "")
////val res = showShowable(Int.show(), 1)
//interace IntShowInstance: Show<Int> {
//    override fun Int.show(): String {
//        return this.toString()
//    }
//}

fun String.Companion.show() = object : StringShowInstance {}
//fun String.Companion.show() = object : StringShowInstance {}

interface IdShowPrimeInstance<A> : Show<Id<A>> {
    fun SA(): Show<A>

    override fun Id<A>.show(): String {
        return SA().run { "Id( ${t.show()} )" }
    }
}

fun <A> Id.Companion.showPrime(SA: Show<A>): IdShowPrimeInstance<A> = object : IdShowPrimeInstance<A> {
    override fun SA() = SA
}

fun <A> showNestedId(aWitness: Show<A>, x: Id<Id<A>>): String {
//    Id.showPrime(Id.showPrime(aWitness)).run { x.show() }

    return Id.showPrime(Id.showPrime(aWitness)).run { x.show() }
}

fun<F, A>showN(fWitness: Show<Kind<F, A>>, aWitness: Show<A>, x: Kind<F, A>) {
    return fWitness.run {
        aWitness.run { x.show() }
    }
}
// now implement the second case for Option.

@instance(Option::class)
interface OptionShowInstance<A>/*: Show<ForOption>*/
