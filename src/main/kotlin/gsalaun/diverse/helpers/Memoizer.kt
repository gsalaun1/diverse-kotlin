package gsalaun.diverse.helpers

import java.util.SortedSet

class Memoizer {

    private val memoizedValues = mutableMapOf<MemoizerKey, SortedSet<Any>>()

    fun getAlreadyProvidedValues(memoizerKey: MemoizerKey): SortedSet<Any> {
        val alreadyProvidedValues: SortedSet<Any> = memoizedValues[memoizerKey] ?: run {
            val memoizedValue = sortedSetOf<Any>()
            memoizedValues[memoizerKey] = memoizedValue
            memoizedValue
        }
        return alreadyProvidedValues
    }
}
