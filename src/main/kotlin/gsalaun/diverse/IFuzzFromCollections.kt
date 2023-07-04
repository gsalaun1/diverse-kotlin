package gsalaun.diverse

interface IFuzzFromCollections {
    /**
     * Randomly pick one element from a given collection.
     *
     * @param candidates
     * @return One of the elements from the candidates collection.
     */
    fun <T> pickOneFrom(candidates: List<T>): T
}
