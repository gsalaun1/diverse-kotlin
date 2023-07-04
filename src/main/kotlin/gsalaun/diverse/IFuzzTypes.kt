package gsalaun.diverse

import kotlin.reflect.KClass
import kotlin.reflect.KType
import kotlin.reflect.typeOf

interface IFuzzTypes {

    /**
     * Generates an instance of a type T.
     *
     * @param T
     * @param clazz Class of type T - necessary as T could not be reified in interfaces
     * @param type type of T
     * @return An instance of type T with some fuzzed properties.
     */
    fun generateInstanceOf(clazz: KClass<*>, type: KType): Any?

    /**
     * Generates an instance of an {@link Enum} type.
     *
     * @param T Type of the {@link Enum}
     * @param clazz KClass of type T - necessary as T could not be reified in interfaces
     * @return A random value of the specified {@link Enum} type.
     */
    fun generateEnum(clazz: KClass<*>): Enum<*>
}

inline fun <reified T> IFuzzTypes.generateEnum(): T = generateEnum(T::class) as T

inline fun <reified T> IFuzzTypes.generateInstanceOf(): T? = generateInstanceOf(T::class, typeOf<T>()) as T?
