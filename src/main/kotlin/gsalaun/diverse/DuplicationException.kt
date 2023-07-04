package gsalaun.diverse

import java.lang.RuntimeException
import java.util.SortedSet

class DuplicationException(
    requestedType: String,
    maxAttemptsToFindNotAlreadyProvidedValue: Int,
    alreadyProvidedValues: SortedSet<Any>,
) :
    Exception(
        generateErrorMessage(
            requestedType,
            maxAttemptsToFindNotAlreadyProvidedValue,
            alreadyProvidedValues,
        ),
    ) {

    companion object {
        fun generateErrorMessage(
            requestedType: String,
            maxAttemptsToFindNotAlreadyProvidedValue: Int,
            alreadyProvidedValues: SortedSet<Any>,
        ): String {
            val generateNoDuplicationFuzzerMethodName = IFuzz::class.members.map { it.name }.first { it == "generateNoDuplicationFuzzer" }
            val maxFailingAttemptsForNoDuplicationAttributeName = Fuzzer::class.members.map { it.name }.first { it == "maxFailingAttemptsForNoDuplication" }
            val errorMessage =
                """
                Couldn't find a non-already provided value of $requestedType after $maxAttemptsToFindNotAlreadyProvidedValue attempts. Already provided values: ${
                    alreadyProvidedValues.joinToString(
                        ", ",
                    )
                }. You can either:
                - Generate a new specific fuzzer to ensure no duplication is provided for a sub-group of fuzzed values (anytime you want through the ${IFuzz::class.simpleName}.$generateNoDuplicationFuzzerMethodName() method of your current Fuzzer instance. E.g.: val tempFuzzer = fuzzer.$generateNoDuplicationFuzzerMethodName())
                - Increase the value of the ${Fuzzer::class.simpleName}.$maxFailingAttemptsForNoDuplicationAttributeName property for your ${IFuzz::class.simpleName} instance.
                """.trimIndent()
            return errorMessage
        }
    }
}
