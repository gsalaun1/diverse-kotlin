package gsalaun.diverse.numbers

import gsalaun.diverse.ArgumentOutOfRangeException
import gsalaun.diverse.IFuzz
import gsalaun.diverse.IFuzzNumbers
import gsalaun.diverse.helpers.NumberExtensions
import java.nio.ByteBuffer

class NumberFuzzer(val fuzzer: IFuzz) : IFuzzNumbers {
    override fun generateInteger(minValue: Int, maxValue: Int): Int {
        // Adjust the inclusiveness of the Fuzzer API to the exclusiveness of the Random API.
        val retainedMaxValue =
            if (maxValue == Int.MAX_VALUE) {
                maxValue
            } else {
                maxValue + 1
            }
        return fuzzer.random.nextInt(minValue, retainedMaxValue)
    }

    override fun generatePositiveInteger(maxValue: Int) = generateInteger(0, maxValue)
    override fun generateDouble(minValue: Double, maxValue: Double): Double {
        throwIfMinGreaterThanMax(minValue, maxValue);

        var result = fuzzer.random.nextDouble()

        if (result < minValue || result > maxValue) {
            // fix
            result = takeAValueInBetweenOrARandomBound(minValue, maxValue);
        }

        return result;
    }

    override fun generatePositiveDouble(minValue: Double, maxValue: Double): Double {
        throwIfMinGreaterThanMax(minValue, maxValue);
        throwIfMinIsNegative(minValue);

        return generateDouble(minValue, maxValue);
    }

    override fun generateLong(minValue: Long, maxValue: Long): Long {
        require(maxValue > minValue) { "maxValue $maxValue, must be > minValue!" }

        val uRange = NumberExtensions.computeRange(minValue, maxValue)

        // Prevent a modolo bias; see https://stackoverflow.com/a/10984975/238419
        // for more information.
        // In the worst case, the expected number of calls is 2 (though usually it's
        // much closer to 1) so this loop doesn't really hurt performance at all.
        var ulongRand: Long
        val maxValue1 = Long.MAX_VALUE - ((Long.MAX_VALUE % uRange) + 1) % uRange
        do {
            val buf = ByteArray(8)
            fuzzer.random.nextBytes(buf)
            ulongRand = ByteBuffer.wrap(buf).getLong()
        } while (ulongRand > maxValue1)

        val modulo = (ulongRand % uRange)

        return modulo + minValue
    }

    private fun throwIfMinGreaterThanMax(minValue: Double, maxValue: Double) {
        if (minValue > maxValue) {
            throw ArgumentOutOfRangeException(
                parameterName = "maxValue should be greater than minValue. minValue: $minValue - maxValue: $maxValue"
            )
        }
    }

    private fun throwIfMinIsNegative(minValue: Double) {
        if (minValue < 0) {
            throw ArgumentOutOfRangeException(message = "minValue should be positive. minValue: $minValue", parameterName = "minValue")
        }
    }

    private fun takeAValueInBetweenOrARandomBound(minValue: Double, maxValue: Double): Double {
        val result: Double
        val maxIncrement = Math.abs(maxValue - minValue);
        var increment = (maxIncrement / fuzzer.generateInteger(2, 15));

        if (increment != 0.0) {
            if (fuzzer.headsOrTails()) {
                result = minValue + increment;
            } else {
                increment = (increment / fuzzer.generateInteger(2, 15));

                result = if (increment != 0.0) {
                    minValue + increment;
                } else {
                    if (fuzzer.headsOrTails()) {
                        minValue
                    } else {
                        maxValue
                    }
                }
            }
        } else {
            result = if (fuzzer.headsOrTails()) {
                minValue
            } else {
                maxValue
            }
        }

        return result;
    }

}
