package com.bassjacob.ylj.koans

import arrow.core.Option

/*
    we can define values as optional, which means that they can optionally be null
    val nullable = null

    we can also define a variable as optional but with a type, which will allow it to be passed to functions
    note the question mark after String. that marks a type as optional in kotlin
    val typedNullable: String? = null

    you can check if a nullable value is null with a simple predicate check
    val isNull = typedNullable == null

    define a function takesAnOptionalString that takes a string and a default value
    and returns the default if the first string is null, and returns the first string otherwise
 */

fun takesAnOptionalString(string: String?, default: String): String {
//    TODO("define a function that takes an optional string and returns it or a default string")
    return string ?: default
}

/*
    ?. and ?:
 */

fun takesAnOptionalString2(string: String?, default: String): String {
    return string ?.let { string } ?: default
}

/*
    .let and ?.let
 */

fun takesAnOptionalString3(string: String?, default: String): String {
//    return string.getOrElse { default }
    TODO()
}

/*
    The Optional type
    we'll look at defining our own later
 */

fun takesAnOptionalString4(string: Option<String>, default: String): String {
    TODO("define a function that takes an optional string and returns it or a default string")
}

/*
    what if we're trying to call a function on the value
    strings have a method called plus which will cast the provided value to string and join it to the string
    "".plus("1") == "1"

    if we have a nullableString
    val nullableString: String? = null

    how about multiple functions? are we doomed to checking for null in every function in a pipeline?
    let's first examine one of the syntax sugars in the language, the nullable chain operator: ?.
    but we can do even better. kotlin ships a method `.let` on every object
    let takes the object and injects it into a function
    similar to ruby, it takes a block and evaluates it
    it refers to the object being injected as `it`
    "".let { println(it) }

    if you try this with an optional value, you'll see that the `it` inside the block is also of type String?
    fun addOne(x: Int): Int {
        return x + 1
    }

    fun addTwo(x: Int): Int {
        return x + 2
    }

    3.let(::addOne).let(::addTwo)
 */
