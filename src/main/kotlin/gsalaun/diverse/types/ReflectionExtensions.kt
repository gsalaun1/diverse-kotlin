package gsalaun.diverse.types

import gsalaun.diverse.Types
import kotlin.reflect.KClass
import kotlin.reflect.KFunction
import kotlin.reflect.KVisibility
import kotlin.reflect.full.superclasses
import kotlin.reflect.jvm.isAccessible

/**
 * Gets a value indicating whether a given {@link KClass} is already covered by the lib for Fuzzing.
 *
 * @param T The {@link KClass} to check.
 * @return <b>true</b> if the {@link KClass} is already covered by the lib for Fuzzing, <b>false</b> otherwise.
 */
fun KClass<*>.isCoveredByAFuzzer() = Types.COVERED_BY_A_FUZZER.contains(this)

/**
 * Gets the constructor with the biggest number of Parameters of a {@link KClass}.
 *
 * @return The {@link Constructor} which has the biggest number of Parameters defined for this {@link KClass}.
 */
fun KClass<*>.getConstructorWithBiggestNumberOfParameters(): KFunction<*>? {
    val constructors = this.getConstructorsOrderedByNumberOfParametersDesc()

    if (constructors.isEmpty()) {
        return null
    }

    return constructors.first()
}

/**
 * Gets all the constructors of a {@link KClass} ordered by their number of parameters desc.
 *
 * @return All the constructors of a {@link KClass} ordered by their number of parameters desc.
 */
fun KClass<*>.getConstructorsOrderedByNumberOfParametersDesc() =
    this.constructors.sortedByDescending { it.parameters.size }

fun KClass<*>.isOrAncesterIsCollection() =
    (
        this.superclasses.flatMap { superclass ->
            superclass.superclasses.map { it } + superclass
        } + this::class
        ).contains(Collection::class)

/**
 * Gets a value indicated whether a given {@link Kfunction} has no parameter defined.
 *
 * @return <b>true</b> if the {@link Kfunction} has no parameter defined, <b>false</b> otherwise.
 */
fun KFunction<*>.isEmpty(): Boolean = this.parameters.isEmpty()
