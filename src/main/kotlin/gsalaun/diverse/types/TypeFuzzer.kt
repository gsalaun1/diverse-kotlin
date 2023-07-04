package gsalaun.diverse.types

import gsalaun.diverse.IFuzz
import gsalaun.diverse.IFuzzTypes
import java.time.LocalDate
import java.time.LocalDateTime
import kotlin.reflect.KClass
import kotlin.reflect.KFunction
import kotlin.reflect.KMutableProperty
import kotlin.reflect.KType
import kotlin.reflect.KVisibility
import kotlin.reflect.full.memberProperties
import kotlin.reflect.jvm.isAccessible
import kotlin.reflect.jvm.jvmErasure

class TypeFuzzer(val fuzzer: IFuzz) : IFuzzTypes {

    companion object {
        const val MAX_RECURSION_ALLOWED_WHILE_FUZZING = 125
        const val MAX_COUNT_T0_FUZZ_IN_LISTS = 5
    }

    override fun generateInstanceOf(clazz: KClass<*>, type: KType) = generateInstanceOf(clazz, type, 0)

    private fun generateInstanceOf(clazz: KClass<*>, type: KType, recursionLevel: Int): Any? {
        val currentRecursionLevel = recursionLevel + 1
        if (currentRecursionLevel > MAX_RECURSION_ALLOWED_WHILE_FUZZING) {
            if (clazz.isOrAncesterIsCollection()) {
                return emptyList<Any>()
            }
            return null
        }

        val constructor = clazz.getConstructorWithBiggestNumberOfParameters()

        if (constructor == null || clazz.isCoveredByAFuzzer()) {
            val instance = fuzzAnyKotlinType(clazz, type, currentRecursionLevel)
            return instance
        }

        return if (constructor.isEmpty()) {
            instantiateAndFuzzViaPropertiesWhenTheyHaveSetters(
                constructor,
                currentRecursionLevel,
                clazz
            )
        } else {
            try {
                instantiateAndFuzzViaConstructorWithBiggestNumberOfParameters(
                    constructor,
                    currentRecursionLevel
                )
            } catch (_: Exception) {
                instantiateAndFuzzViaOtherConstructorIteratingOnAllThemUntilItWorks(
                    currentRecursionLevel,
                    clazz
                )
            }
        }
    }

    private fun fuzzAnyKotlinType(clazz: KClass<*>, type: KType, recursionLevel: Int): Any? {
        if (clazz.java.isEnum) {
            return fuzzEnumValue(clazz)
        }

        if (clazz.isOrAncesterIsCollection()) {
            return generateListOf(clazz as KClass<Any>, type, recursionLevel)
        }
        if (clazz == Map::class) {
            return generateMapOf(type, recursionLevel)
        }
        val result = when (clazz) {
            Boolean::class -> fuzzer.headsOrTails()
            Int::class -> fuzzer.generateInteger()
            Long::class -> fuzzer.generateLong()
            Double::class -> fuzzer.generatePositiveDouble()
            String::class -> fuzzer.generateFirstName()
            LocalDateTime::class -> fuzzer.generateDateTime()
            LocalDate::class -> fuzzer.generateDate()

            else ->
                wrapCallOfGenerateInstanceOf(clazz, type, recursionLevel)
        }
        return result
    }

    private fun fuzzEnumValue(clazz: KClass<*>): Any {
        val enumValues = Class.forName(clazz.qualifiedName).enumConstants
        return enumValues[fuzzer.random.nextInt(enumValues.size)]
    }

    private fun generateListOf(clazz: KClass<*>, type: KType, recursionLevel: Int): Collection<Any> {
        val elemType = type.arguments[0].type!!
        val items = (0 until fuzzer.generateInteger(0, MAX_COUNT_T0_FUZZ_IN_LISTS)).mapNotNull {
            generateInstanceOf(elemType.jvmErasure, elemType, recursionLevel)
        }
        return when (clazz) {
            List::class -> items
            Set::class -> items.toSet()
            else -> throw IllegalStateException("$clazz collection class is not generable")
        }
    }

    private fun generateMapOf(
        type: KType,
        recursionLevel: Int
    ): Map<*, *> {
        val keyElemType = type.arguments[0].type!!
        val valueElemType = type.arguments[1].type!!
        return (0 until fuzzer.generateInteger(0, MAX_COUNT_T0_FUZZ_IN_LISTS)).mapNotNull {
            val key = generateInstanceOf(keyElemType.jvmErasure, keyElemType, recursionLevel)
            val value = generateInstanceOf(valueElemType.jvmErasure, keyElemType, recursionLevel)
            if (key != null && value != null) {
                Pair(key, value)
            } else {
                null
            }
        }.toMap()
    }

    private fun wrapCallOfGenerateInstanceOf(clazz: KClass<*>, type: KType, recursionLevel: Int): Any? {
        return callPrivateGenericMethod("generateInstanceOf", clazz, type, recursionLevel)
    }

    private fun callPrivateGenericMethod(privateMethodName: String, vararg parameters: Any): Any? {
        val method = TypeFuzzer::class.members.first {
            it.name == privateMethodName && it.visibility == KVisibility.PRIVATE
        }.apply { isAccessible = true }

        return method.call(this, *parameters)
    }

    private fun instantiateAndFuzzViaPropertiesWhenTheyHaveSetters(
        constructor: KFunction<*>,
        recursionLevel: Int,
        clazz: KClass<*>,
        instance: Any? = null,
    ): Any? {
        val concernedInstance = instance ?: constructor.apply { isAccessible = true }.call()

        val mutableFields = clazz.memberProperties
            .filter { it is KMutableProperty<*> }
            .map { it as KMutableProperty<*> }
            .map { it.apply { isAccessible = true } }


        mutableFields.forEach { field ->
            val fieldValue = fuzzAnyKotlinType(
                field.returnType.classifier as KClass<Any>,
                field.returnType,
                recursionLevel,
            )
            field.setter.call(concernedInstance, fieldValue)
        }

        return concernedInstance
    }

    private fun instantiateAndFuzzViaConstructorWithBiggestNumberOfParameters(
        constructor: KFunction<*>,
        recursionLevel: Int
    ): Any? {
        constructor.apply {
            isAccessible = true
        }
        val constructorParameters =
            prepareFuzzedParametersForThisConstructor(constructor, recursionLevel).toTypedArray()
        return constructor.call(*constructorParameters)
    }

    private fun prepareFuzzedParametersForThisConstructor(
        constructor: KFunction<*>,
        recursionLevel: Int
    ): List<Any?> {
        val parameterInfos = constructor.parameters
        val parameters = parameterInfos.map { parameter ->
            val nullParameter = if (parameter.type.isMarkedNullable) {
                fuzzer.headsOrTails()
            } else {
                false
            }
            if (nullParameter) {
                null
            } else {
                val parameterClazz =
                    when (val classifier = parameter.type.classifier) {
                        is KClass<*> -> classifier
                        else -> throw IllegalStateException()
                    }
                fuzzAnyKotlinType(
                    parameterClazz,
                    parameter.type,
                    recursionLevel,
                )
            }
        }

        return parameters
    }

    private fun instantiateAndFuzzViaOtherConstructorIteratingOnAllThemUntilItWorks(
        recursionLevel: Int,
        clazz: KClass<*>
    ): Any? {
        var instance: Any?
        // Some constructor are complicated to use (e.g. those accepting abstract classes as input)
        // Try other constructors until it works
        val constructors = clazz.getConstructorsOrderedByNumberOfParametersDesc().filterIndexed { idx, _ -> idx > 0 }
        for (constructor in constructors) {
            try {
                instance =
                    instantiateAndFuzzViaConstructorWithBiggestNumberOfParameters(
                        constructor,
                        recursionLevel
                    )
                return instance
            } catch (exception: Exception) {
                continue
            }
        }

        // We couldn't use any of its Constructor. Let's return a default instance (degraded mode)
        instance = null

        return instance
    }

    override fun generateEnum(clazz: KClass<*>): Enum<*> {
        val enumValues = Class.forName(clazz.qualifiedName).enumConstants
        return enumValues[fuzzer.random.nextInt(enumValues.size)] as Enum<*>
    }
}
