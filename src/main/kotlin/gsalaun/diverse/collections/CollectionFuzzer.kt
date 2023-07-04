package gsalaun.diverse.collections

import gsalaun.diverse.IFuzz
import gsalaun.diverse.IFuzzFromCollections

class CollectionFuzzer(val fuzzer:IFuzz) : IFuzzFromCollections {

    override fun <T> pickOneFrom(candidates: List<T>): T {
        require(candidates.isNotEmpty()) { "Candidates list must not be empty." }
        val randomIndex = fuzzer.random.nextInt(0, candidates.size);
        return candidates[randomIndex]
    }
}
