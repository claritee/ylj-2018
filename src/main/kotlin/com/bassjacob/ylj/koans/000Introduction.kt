package com.bassjacob.ylj.koans

import arrow.higherkind

/*
Hi, thanks for checking out this workshop. This is an introduction to using the Arrow library with Kotlin.

In this tutorial we're going to go through the process of using the Arrow library to define a number of building blocks and in the process build a websocket-based chat program.

In this workshop, you can work through the 8 exercises in as much or as little detail as you like. If you're new to Kotlin or the JVM, or to FP I recommend working through as much as you can. Each chapter is split in two parts. The exercises and the required functions for the server.

The exercises are there to describe what the library does under the hood or shows how to utilise it. The required functions need to be completed in order for the server to function. The

Let's start by looking at how functions work.

Functions are defined by using the fun keyword. Let's start with a function that adds one to a number. The skeleton of the function is defined below.

Delete the
TODO()
statement and fill in the function body so that it returns the parameter plus one
 */

fun addOne(x: Int): Int {
    TODO()
}

/*
Functions can also be generic. We do this by defining any generic types in angle brackets '<>' before the function name. Let's define the id function.
 */

fun <A> id(a: A): A {
    TODO()
}

/*
Functions can take multiple parameters as you would expect. Let's define the const function.
 */

fun <A, B> const(a: A, b: B): A {
    TODO()
}

/*
We can even pass functions around as values. If the function is not a parameter you can reference it by prepending it with its enclosing scope and then two colons, like so: A::f. If the function is declared outside of an object or class, you can just reference it as ::f
 */

fun isGreaterThanThree(x: Int): Boolean {
    return x > 3
}

fun filterGreaterThanThree(l: List<Int>): List<Int> {
    return l.filter(::isGreaterThanThree)
}

/*
You can also define functions as extensions. This is a way to define function patches to existing instances at compile time.
 */

fun List<Int>.filterGreaterThanThreeExtension(): List<Int> {
    TODO()
}

//-----------------------------------------------------------

@higherkind
data class Id<A>(val t: A) : IdOf<A> {
    companion object
}

@higherkind
sealed class Option<A> : OptionOf<A> {
    companion object

    class None<A> : Option<A>()
    data class Some<A>(val t: A) : Option<A>()
}