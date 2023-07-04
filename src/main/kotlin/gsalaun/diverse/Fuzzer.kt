package gsalaun.diverse

import gsalaun.diverse.address.Address
import gsalaun.diverse.address.AddressFuzzer
import gsalaun.diverse.address.geography.Country
import gsalaun.diverse.collections.CollectionFuzzer
import gsalaun.diverse.datetimes.DateTimeFuzzer
import gsalaun.diverse.helpers.Memoizer
import gsalaun.diverse.helpers.MemoizerKey
import gsalaun.diverse.helpers.NumberExtensions
import gsalaun.diverse.numbers.NumberFuzzer
import gsalaun.diverse.persons.Gender
import gsalaun.diverse.persons.LastNames
import gsalaun.diverse.persons.Locations
import gsalaun.diverse.persons.Person
import gsalaun.diverse.persons.PersonFuzzer
import gsalaun.diverse.strings.Adjectives
import gsalaun.diverse.strings.Feeling
import gsalaun.diverse.strings.LoremFuzzer
import gsalaun.diverse.strings.StringFuzzer
import gsalaun.diverse.types.TypeFuzzer
import gsalaun.diverse.uuid.UuidFuzzer
import java.lang.reflect.Method
import java.time.LocalDateTime
import java.util.SortedSet
import java.util.UUID
import kotlin.random.Random
import kotlin.reflect.KClass
import kotlin.reflect.KType

class Fuzzer(
    val seed: Int? = null,
    val name: String = generateFuzzerName(),
    val noDuplication: Boolean = false
) : IFuzz {

    override val random: Random
        get() = internalRandom

    private val numberFuzzer: IFuzzNumbers
    private val loremFuzzer: IFuzzLorem;
    private val addressFuzzer: IFuzzAddress
    private val personFuzzer: IFuzzPersons
    private val dateTimeFuzzer: IFuzzDatesAndTime
    private val typeFuzzer: IFuzzTypes
    private val uuidFuzzer: IFuzzUuid
    private val collectionFuzzer: IFuzzFromCollections
    private val stringFuzzer: IFuzzStrings

    private val memoizer: Memoizer = Memoizer()
    private var sideEffectFreeFuzzer: IFuzz? = null

    /**
     * Gives easy access to the {@link IFuzz.Random} explicit implementation.
     */
    private val internalRandom: Random

    var maxFailingAttemptsForNoDuplication: Int = MAX_FAILING_ATTEMPTS_FOR_NODUPLICATION_DEFAULT_VALUE

    private val maxRangeSizeAllowedForMemoization: Int
        get() {
            return MAX_RANGE_SIZE_ALLOWED_FOR_MEMOIZATION_DEFAULT_VALUE
        }

    private val sideEffectFreeFuzzerWithDuplicationAllowed: IFuzz
        get() {
            return sideEffectFreeFuzzer ?: run {
                sideEffectFreeFuzzer = Fuzzer(seed = seed, noDuplication = false)
                return sideEffectFreeFuzzer as Fuzzer
            }
        }

    companion object {
        // For NoDuplication mode
        private const val MAX_FAILING_ATTEMPTS_FOR_NODUPLICATION_DEFAULT_VALUE = 100
        private const val MAX_RANGE_SIZE_ALLOWED_FOR_MEMOIZATION_DEFAULT_VALUE = 1000000

        /**
         * Sets the way you want to log or receive what the {@Link Fuzzer} has to say about every generated seeds used for every fuzzer instance and test.
         */
        var Log: ((String) -> Unit)? = null

    }

    init {
        val seedToConsider = seed ?: Random.nextInt()
        internalRandom = Random(seedToConsider);

        logSeedAndTestInformations(seedToConsider, seed != null, name);

        numberFuzzer = NumberFuzzer(this)
        loremFuzzer = LoremFuzzer(this);
        addressFuzzer = AddressFuzzer(this)
        personFuzzer = PersonFuzzer(this)
        dateTimeFuzzer = DateTimeFuzzer(this)
        typeFuzzer = TypeFuzzer(this)
        uuidFuzzer = UuidFuzzer(this)
        collectionFuzzer = CollectionFuzzer(this)
        stringFuzzer = StringFuzzer(this);
    }

    private fun logSeedAndTestInformations(seed: Int, seedWasProvided: Boolean, fuzzerName: String) {
        val testName = findTheNameOfTheTestInvolved();

        if (Log == null) {
            throw FuzzerException(buildErrorMessageForMissingLogRegistration());
        }

        Log?.let { it("----------------------------------------------------------------------------------------------------------------------") }

        if (seedWasProvided) {
            Log?.let { it("--- Fuzzer (\"$fuzzerName\") instantiated from a provided seed ($seed)") };
            Log?.let { it("--- from the test: $testName()") };
        } else {
            Log?.let { it("--- Fuzzer (\"$fuzzerName\") instantiated with the seed ($seed)") };
            Log?.let { it("--- from the test: $testName()") };
            Log?.let { it("--- Note: you can instantiate another Fuzzer with that very same seed in order to reproduce the exact test conditions") };
        }

        Log?.let { it("----------------------------------------------------------------------------------------------------------------------") };
    }

    private fun findTheNameOfTheTestInvolved(): String {
        val stackTrace = Thread.currentThread().stackTrace

        val testMethod = stackTrace.mapNotNull { stackTraceElement ->
            try {
                val clazz = Class.forName(stackTraceElement.className)
                val method = clazz.methods.firstOrNull { it.name == stackTraceElement.methodName }
                if (method == null) {
                    null
                } else {
                    Pair(clazz, method)
                }
            } catch (_: ClassNotFoundException) {
                null
            }
        }.firstOrNull { isATestMethod(it.second) }
        return testMethod?.let { "${it.first}.${it.second.name}" } ?: "not found"
    }

    private fun isATestMethod(method: Method): Boolean {
        val annotations = method.annotations.map { it.toString() }
        return annotations.any {
            it.contains("org.junit.jupiter.api.Test")
                    || it.contains("org.junit.jupiter.params.ParameterizedTest")
                    || it.contains("org.junit.jupiter.api.RepeatedTest")
        }
    }

    private fun buildErrorMessageForMissingLogRegistration() =
        """
            You must register (at least once) a log handler in your Test project for the Diverse library to be able to publish all the seeds used for every test (which is a prerequisite for deterministic test runs afterward).
        The only thing you have to do is to set a value for the static "Log" property of the Fuzzer type.

        The best location for this call is within a unique AllFixturesSetup class.
        e.g.: with JUnit:

        import org.junit.jupiter.api.extension.BeforeAllCallback
        import org.junit.jupiter.api.extension.ExtensionContext

        class AllTestsFixtures : BeforeAllCallback {
            override fun beforeAll(context: ExtensionContext) {
                Fuzzer.Log = {
                    println(it)
                }
            }
        }
        
        Extension should be used like this in your test class :
        @ExtendWith(AllTestsFixtures::class)
        """.trimIndent();


    override fun generateAddress(country: Country?): Address {
        return addressFuzzer.generateAddress(country)
    }


    override fun generateNoDuplicationFuzzer() = Fuzzer(seed = seed, noDuplication = true)
    override fun headsOrTails(): Boolean {
        return internalRandom.nextBoolean()
    }

    override fun generateInteger(minValue: Int, maxValue: Int): Int {
        if (noDuplication) {
            return generateWithoutDuplication(
                Int::class,
                object {}.javaClass.enclosingMethod,
                ArgumentHasher.hashArguments(minValue, maxValue),
                maxFailingAttemptsForNoDuplication,
                standardGenerationFunction = { fuzzerWithDuplicationAllowed ->
                    fuzzerWithDuplicationAllowed.generateInteger(
                        minValue,
                        maxValue,
                    )
                },
                lastChanceGenerationFunction = { fuzzerWithDuplicationAllowed, alreadyProvidedSortedSet ->
                    lastChanceToFindNotAlreadyProvidedInteger(
                        alreadyProvidedSortedSet,
                        minValue,
                        maxValue,
                        fuzzerWithDuplicationAllowed,
                    )
                },
            )
        }
        return numberFuzzer.generateInteger(minValue, maxValue)
    }

    private fun lastChanceToFindNotAlreadyProvidedInteger(
        alreadyProvidedValues: SortedSet<Any>,
        minValue: Int = Int.MIN_VALUE,
        maxValue: Int = Int.MAX_VALUE,
        fuzzer: IFuzz,
    ): Int? {
        val allPossibleValues = (minValue..maxValue)

        val remainingCandidates = allPossibleValues.minus(alreadyProvidedValues.map { it as Int }.toSet())

        if (remainingCandidates.isNotEmpty()) {
            val pickOneFrom = fuzzer.pickOneFrom(remainingCandidates)
            return pickOneFrom
        }

        return null
    }

    override fun <T> pickOneFrom(candidates: List<T>): T {
        if (noDuplication) {
            return generateWithoutDuplication(
                List::class,
                object {}.javaClass.enclosingMethod,
                ArgumentHasher.hashArguments(candidates),
                maxFailingAttemptsForNoDuplication,
                standardGenerationFunction = { fuzzerWithDuplicationAllowed ->
                    fuzzerWithDuplicationAllowed.pickOneFrom(
                        candidates
                    ) as Any
                },
                lastChanceGenerationFunction =
                { fuzzerWithDuplicationAllowed, alreadyProvidedSortedSet ->
                    lastChanceToFindNotAlreadyPickedValue(
                        alreadyProvidedSortedSet,
                        candidates,
                        fuzzerWithDuplicationAllowed
                    ) as Any
                }
            ) as T
        }
        return collectionFuzzer.pickOneFrom(candidates)
    }

    private fun <T> lastChanceToFindNotAlreadyPickedValue(
        alreadyProvidedValues: SortedSet<*>,
        candidates: List<T>,
        fuzzer: IFuzz
    ): T? {
        val remainingCandidates = candidates - alreadyProvidedValues.map { it as T }.toSet()

        if (remainingCandidates.isNotEmpty()) {
            val index = fuzzer.generateInteger(0, remainingCandidates.size - 1)
            return remainingCandidates[index]
        }

        return null
    }

    override fun generateLetter(): Char {
        if (noDuplication) {
            return generateWithoutDuplication(
                Char::class,
                object {}.javaClass.enclosingMethod,
                ArgumentHasher.hashArguments(),
                maxFailingAttemptsForNoDuplication,
                standardGenerationFunction = { fuzzerWithDuplicationAllowed -> fuzzerWithDuplicationAllowed.generateLetter() },
                lastChanceGenerationFunction = { fuzzerWithDuplicationAllowed,
                                                 alreadyProvidedSortedSet
                    ->
                    lastChanceToFindLetter(alreadyProvidedSortedSet, fuzzerWithDuplicationAllowed)
                })
        }

        return loremFuzzer.generateLetter();
    }

    private fun lastChanceToFindLetter(alreadyProvidedValues: SortedSet<Any>, fuzzer: IFuzz): Char? {
        val allPossibleValues = LoremFuzzer.alphabet

        val remainingCandidates = allPossibleValues - alreadyProvidedValues.map { it as Char }.toSet()

        if (remainingCandidates.isNotEmpty()) {
            return fuzzer.pickOneFrom(remainingCandidates)
        }

        return null
    }

    override fun generateWords(number: Int) = loremFuzzer.generateWords(number);

    override fun generateSentence(nbOfWords: Int) = loremFuzzer.generateSentence(nbOfWords)
    override fun generateParagraph(nbOfSentences: Int) = loremFuzzer.generateParagraph(nbOfSentences)
    override fun generateParagraphs(nbOfParagraphs: Int) = loremFuzzer.generateParagraphs(nbOfParagraphs)
    override fun generateText(nbOfParagraphs: Int) = loremFuzzer.generateText(nbOfParagraphs)

    /**
     * Methods to be used when {@link #noDuplication} is set to <b>true</b>
     * for any fuzzing method of this {@link Fuzzer} instance.
     * It encapsulates the logic of various attempts and retries before
     * falling back to a very specific @param lastChanceGenerationFunction
     * lambda associated to the considered fuzzing method.
     *
     * @param T : Type to be fuzzed/generated
     * @param currentMethod :
     *              The current Method calling us (e.g.: {@link #generateAge}).
     *              Used for memoization purpose.
     * @param argumentsHashCode : A hash for the current method call arguments. Used for memoization purpose.
     * @param maxFailingAttemptsBeforeLastChanceFunctionIsCalled :
     *              The maximum number of calls to the {@param standardGenerationFunction}
     *              we should try before we fall-back and call the
     *              {@param lastChanceGenerationFunction} lambda.
     * @param standardGenerationFunction
     *              The function to use in order to generate the thing(s) we want.
     *              It should be the same function that the one we call for the cases
     *              where {@link noDuplication} is set to <b>false</b>.
     * @param lastChanceGenerationFunction
     *              The function to use in order to generate the thing(s) we want when
     *              all the {@param standardGenerationFunction} attempts have failed.
     *              To do our job, we receive:
     *                  - A {@link SortedSet<T> } instance with all the previously generated values
     *                  - A side-effect free {@link IFuzz} instance to use if needed.
     *                  (one should not use the current instance of {@link Fuzzer}
     *                  to do the job since it may have side-effects
     *                  and lead to {@link StackOverflowException}).
     *  @return The thing(s) we want to generate.
     *
     */
    private fun <T : Any> generateWithoutDuplication(
        clazz: KClass<*>,
        currentMethod: Method,
        argumentsHashCode: Int,
        maxFailingAttemptsBeforeLastChanceFunctionIsCalled: Int,
        standardGenerationFunction: (IFuzz) -> T,
        lastChanceGenerationFunction: ((IFuzz, SortedSet<Any>) -> T?)? = null,
    ): T {
        return generateWithoutDuplicationBeingClass(
            currentMethod,
            argumentsHashCode,
            maxFailingAttemptsBeforeLastChanceFunctionIsCalled,
            standardGenerationFunction,
            lastChanceGenerationFunction,
            clazz
        )
    }

    private fun <T : Any> generateWithoutDuplicationBeingClass(
        currentMethod: Method,
        argumentsHashCode: Int,
        maxFailingAttemptsBeforeLastChanceFunctionIsCalled: Int,
        standardGenerationFunction: (IFuzz) -> Any,
        lastChanceGenerationFunction: ((IFuzz, SortedSet<Any>) -> T?)? = null,
        clazz: KClass<*>,
    ): T {
        val memoizerKey = MemoizerKey(currentMethod, argumentsHashCode)

        var (maybe, alreadyProvidedValues) = tryGetNonAlreadyProvidedValuesWithRegularGenerationFunction<T>(
            memoizerKey,
            standardGenerationFunction,
            maxFailingAttemptsBeforeLastChanceFunctionIsCalled,
        )
        if (maybe == null) {
            if (lastChanceGenerationFunction != null) {
                // last attempt, we randomly pick the missing bits from the memoizer
                maybe = lastChanceGenerationFunction(
                    sideEffectFreeFuzzerWithDuplicationAllowed,
                    memoizer.getAlreadyProvidedValues(memoizerKey),
                )
            }
            if (maybe == null) {
                val requestedType = clazz.qualifiedName ?: ""
                throw DuplicationException(
                    requestedType,
                    maxFailingAttemptsBeforeLastChanceFunctionIsCalled,
                    alreadyProvidedValues,
                )
            }
        }
        alreadyProvidedValues.add(maybe)
        return maybe
    }

    private fun <T> tryGetNonAlreadyProvidedValuesWithRegularGenerationFunction(
        memoizerKey: MemoizerKey,
        generationFunction: (IFuzz) -> Any,
        maxFailingAttempts: Int,
    ): Pair<T?, SortedSet<Any>> {
        val alreadyProvidedValues: SortedSet<Any> = memoizer.getAlreadyProvidedValues(memoizerKey)

        val maybe =
            generateNotAlreadyProvidedValue<T>(alreadyProvidedValues, maxFailingAttempts, generationFunction)

        return Pair(maybe, alreadyProvidedValues)
    }

    private fun <T> generateNotAlreadyProvidedValue(
        alreadyProvidedValues: Set<Any>,
        maxAttempts: Int,
        generationFunction: (IFuzz) -> Any,
    ): T? {
        var result: T?
        (0 until maxAttempts).forEach {
            result = generationFunction(sideEffectFreeFuzzerWithDuplicationAllowed) as T

            if (!alreadyProvidedValues.contains<Any?>(result)) {
                return result
            }
        }
        return null
    }

    override fun generatePositiveInteger(maxValue: Int): Int {
        if (noDuplication) {
            return generateWithoutDuplication(
                Int::class,
                object {}.javaClass.enclosingMethod,
                ArgumentHasher.hashArguments(maxValue),
                maxFailingAttemptsForNoDuplication,
                standardGenerationFunction = { fuzzerWithDuplicationAllowed ->
                    fuzzerWithDuplicationAllowed.generatePositiveInteger(
                        maxValue,
                    )
                },
            )
        }

        return numberFuzzer.generatePositiveInteger(maxValue)
    }

    override fun generateDouble(minValue: Double, maxValue: Double): Double {
        // No need to memoize decimals here since it is very unlikely that the lib generate twice the same decimal
        return numberFuzzer.generateDouble(minValue, maxValue);
    }

    override fun generatePositiveDouble(minValue: Double, maxValue: Double): Double {
        // No need to memoize decimals here since it is very unlikely that the lib generate twice the same decimal
        return numberFuzzer.generatePositiveDouble(minValue, maxValue);
    }

    override fun generateLong(minValue: Long, maxValue: Long): Long {
        if (noDuplication) {
            // We will only memoize if the range is not too wide
            val uRange = NumberExtensions.computeRange(minValue, maxValue)

            if (uRange <= maxRangeSizeAllowedForMemoization) {
                return generateWithoutDuplication(
                    Long::class,
                    object {}.javaClass.enclosingMethod,
                    ArgumentHasher.hashArguments(minValue, maxValue),
                    maxFailingAttemptsBeforeLastChanceFunctionIsCalled = maxFailingAttemptsForNoDuplication,
                    standardGenerationFunction = { fuzzerWithDuplicationAllowed ->
                        fuzzerWithDuplicationAllowed.generateLong(
                            minValue,
                            maxValue,
                        )
                    },
                    lastChanceGenerationFunction =
                    { fuzzerWithDuplicationAllowed, alreadyProvidedSortedSet ->
                        lastChanceToFindNotAlreadyProvidedLong(
                            minValue,
                            maxValue,
                            alreadyProvidedSortedSet,
                            fuzzerWithDuplicationAllowed,
                        )
                    },
                )
            }
        }

        return numberFuzzer.generateLong(minValue, maxValue)
    }

    private fun lastChanceToFindNotAlreadyProvidedLong(
        minValue: Long,
        maxValue: Long,
        alreadyProvidedValues: SortedSet<Any>,
        fuzzer: IFuzz,
    ): Long? {
        val allPossibleValues = generateAllPossibleOptions(minValue, maxValue)

        val remainingCandidates = allPossibleValues - alreadyProvidedValues

        if (remainingCandidates.isNotEmpty()) {
            val index = fuzzer.generateInteger(0, remainingCandidates.size - 1)
            val randomRemainingNumber = remainingCandidates.toList()[index]
            return randomRemainingNumber as Long
        }

        return null
    }

    private fun generateAllPossibleOptions(min: Long, max: Long) = (min..max).map { it }.toSortedSet()

    override fun generateFirstName(gender: Gender?): String {
        if (noDuplication) {
            return generateWithoutDuplication(
                String::class,
                object {}.javaClass.enclosingMethod,
                ArgumentHasher.hashArguments(gender),
                maxFailingAttemptsForNoDuplication,
                standardGenerationFunction = { fuzzerWithDuplicationAllowed ->
                    fuzzerWithDuplicationAllowed.generateFirstName(
                        gender,
                    )
                },
            )
        }

        return personFuzzer.generateFirstName(gender)
    }

    override fun generateDateTime() = dateTimeFuzzer.generateDateTime()

    override fun generateDate() = dateTimeFuzzer.generateDate()

    override fun generateDateTimeBetween(minValue: LocalDateTime, maxValue: LocalDateTime): LocalDateTime {
        return dateTimeFuzzer.generateDateTimeBetween(minValue, maxValue)
    }

    override fun generateDateTimeBetween(minDate: String, maxDate: String): LocalDateTime {
        return dateTimeFuzzer.generateDateTimeBetween(minDate, maxDate)
    }

    override fun generateAdjective(feeling: Feeling?): String {
        if (noDuplication) {
            return generateWithoutDuplication(
                String::class,
                object {}.javaClass.enclosingMethod,
                ArgumentHasher.hashArguments(feeling),
                maxFailingAttemptsForNoDuplication,
                standardGenerationFunction = { fuzzerWithDuplicationAllowed ->
                    fuzzerWithDuplicationAllowed.generateAdjective(
                        feeling
                    )
                },
                lastChanceGenerationFunction =
                { fuzzerWithDuplicationAllowed, alreadyProvidedSortedSet ->
                    lastChanceToFindAdjective(
                        feeling,
                        alreadyProvidedSortedSet,
                        fuzzerWithDuplicationAllowed
                    )
                })
        }

        return stringFuzzer.generateAdjective(feeling);
    }

    private fun lastChanceToFindAdjective(
        feeling: Feeling?,
        alreadyProvidedValues: SortedSet<Any>,
        fuzzer: IFuzz
    ): String? {
        val allPossibleValues = Adjectives.perFeeling[feeling] ?: emptyList()

        val remainingCandidates = allPossibleValues - alreadyProvidedValues.map { it as String }

        if (remainingCandidates.isNotEmpty()) {
            return fuzzer.pickOneFrom(remainingCandidates);
        }

        return null
    }

    override fun generateStringFromPattern(diverseFormat: String) =
        stringFuzzer.generateStringFromPattern(diverseFormat);

    override fun generateInstanceOf(clazz: KClass<*>, type: KType): Any? {
        return typeFuzzer.generateInstanceOf(clazz, type)
    }

    override fun generateEnum(clazz: KClass<*>): Enum<*> {
        if (noDuplication) {
            return generateWithoutDuplicationBeingClass<Enum<*>>(
                object {}.javaClass.enclosingMethod,
                ArgumentHasher.hashArguments(),
                maxFailingAttemptsForNoDuplication,
                { fuzzerWithDuplicationAllowed -> fuzzerWithDuplicationAllowed.generateEnum(clazz) },
                clazz = clazz,
            ) as Enum<*>
        }

        return typeFuzzer.generateEnum(clazz)
    }

    override fun generateUuid(): UUID {
        // No need to memoize Guids here since it is very unlikely that the lib generate twice the same value
        return uuidFuzzer.generateUuid();
    }

    /**
     * Generates a password following some common rules asked on the internet.
     *
     * @return The generated password
     */
    override fun generatePassword(
        minSize: Int?,
        maxSize: Int?,
        includeSpecialCharacters: Boolean
    ): String {
        if (noDuplication) {
            return generateWithoutDuplication(
                Boolean::class,
                object {}.javaClass.enclosingMethod,
                ArgumentHasher.hashArguments(minSize, maxSize, includeSpecialCharacters),
                maxFailingAttemptsForNoDuplication,
                standardGenerationFunction = { fuzzerWithDuplicationAllowed ->
                    fuzzerWithDuplicationAllowed.generatePassword(
                        minSize,
                        maxSize,
                        includeSpecialCharacters
                    )
                })
        }

        return personFuzzer.generatePassword(minSize, maxSize, includeSpecialCharacters);
    }

    override fun generateAge(): Int {
        if (noDuplication) {
            return generateWithoutDuplication(
                Int::class,
                object {}.javaClass.enclosingMethod,
                ArgumentHasher.hashArguments(),
                maxFailingAttemptsForNoDuplication,
                standardGenerationFunction = { fuzzerWithDuplicationAllowed -> fuzzerWithDuplicationAllowed.generateAge() },
                lastChanceGenerationFunction = { fuzzerWithDuplicationAllowed, alreadyProvidedSortedSet ->
                    lastChanceToFindAge(
                        alreadyProvidedSortedSet,
                        18,
                        97,
                        fuzzerWithDuplicationAllowed
                    )
                })

        }

        return personFuzzer.generateAge();
    }

    private fun lastChanceToFindAge(
        alreadyProvidedValues: SortedSet<Any>,
        minAge: Int,
        maxAge: Int,
        fuzzer: IFuzz
    ): Int? {
        val allPossibleValues = (minAge..(maxAge - minAge)).toList()

        val remainingCandidates = allPossibleValues - alreadyProvidedValues.map { it as Int }.toSet()

        if (remainingCandidates.isNotEmpty()) {
            return fuzzer.pickOneFrom(remainingCandidates);
        }

        return null
    }

    override fun generatePerson(gender: Gender?): Person {
        return personFuzzer.generatePerson(gender);
    }

    override fun generateLastName(firstName: String): String {
        if (noDuplication) {
            return generateWithoutDuplication(
                String::class,
                object {}.javaClass.enclosingMethod,
                ArgumentHasher.hashArguments(firstName),
                maxFailingAttemptsForNoDuplication,
                standardGenerationFunction = { fuzzerWithDuplicationAllowed ->
                    fuzzerWithDuplicationAllowed.generateLastName(
                        firstName
                    )
                },
                lastChanceGenerationFunction =
                { fuzzerWithDuplicationAllowed, alreadyProvidedSortedSet ->
                    lastChanceToFindLastName(
                        firstName,
                        alreadyProvidedSortedSet,
                        fuzzerWithDuplicationAllowed
                    )
                })
        }

        return personFuzzer.generateLastName(firstName);
    }

    private fun lastChanceToFindLastName(
        firstName: String,
        alreadyProvidedValues: SortedSet<Any>,
        fuzzer: IFuzz
    ): String? {
        val continent = Locations.findContinent(firstName);
        val allPossibleValues = LastNames.perContinent[continent] ?: emptyList()

        val remainingCandidates = allPossibleValues - alreadyProvidedValues.map { it.toString() }.toSet()

        return if (remainingCandidates.isNotEmpty()) {
            return fuzzer.pickOneFrom(remainingCandidates)
        } else {
            null
        }
    }

    override fun generateEMail(firstName: String?, lastName: String?): String {
        if (noDuplication) {
            return generateWithoutDuplication(
                String::class,
                object {}.javaClass.enclosingMethod,
                ArgumentHasher.hashArguments(firstName, lastName),
                maxFailingAttemptsForNoDuplication,
                standardGenerationFunction = { fuzzerWithDuplicationAllowed ->
                    fuzzerWithDuplicationAllowed.generateEMail(
                        firstName,
                        lastName
                    )
                })
        }

        return personFuzzer.generateEMail(firstName, lastName);
    }
}

fun generateFuzzerName(): String {
    // We are explicitly not using the Random field here to prevent from doing side effects
    // on the deterministic fuzzer instances (depending on whether or not we specified a name)
    val index = Random.nextInt(0, 1500);

    return "fuzzer$index";
}
