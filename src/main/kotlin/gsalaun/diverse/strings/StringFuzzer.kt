package gsalaun.diverse.strings

import gsalaun.diverse.IFuzz
import gsalaun.diverse.IFuzzStrings
import java.util.Locale

class StringFuzzer(val fuzzer: IFuzz) : IFuzzStrings {
    override fun generateAdjective(feeling: Feeling?): String {
        val consideredFeeling =
            if (feeling == null) {
                val isPositive = fuzzer.headsOrTails()
                if (isPositive) {
                    Feeling.POSITIVE
                } else {
                    Feeling.NEGATIVE
                }
            } else {
                feeling
            }

        val adjectives = Adjectives.perFeeling[consideredFeeling] ?: emptyList()

        val randomLocalIndex = fuzzer.random.nextInt(0, adjectives.size)

        return adjectives[randomLocalIndex]
    }

    override fun generateStringFromPattern(diverseFormat: String): String {
        val builder = StringBuilder(diverseFormat.length)
        diverseFormat.forEach { c ->
            when (c) {
                '#' -> builder.append(fuzzer.generateInteger(0, 9))
                'X' -> builder.append(fuzzer.generateLetter().toString().uppercase(Locale.getDefault()))
                'x' -> builder.append(fuzzer.generateLetter())
                else -> builder.append(c)
            }
        }

        return builder.toString()
    }
}