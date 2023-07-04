package gsalaun.diverse

object ArgumentHasher {

    fun hashArguments(vararg arguments: Any?) =
        arguments.fold(17) { acc, argument ->
            if (argument is Collection<*>) {
                val parameterHashCode = getByValueHashCode(argument)
                acc * 23 + parameterHashCode
            } else {
                val parameterHashCode = argument.hashCode()
                acc * 23 + parameterHashCode
            }
        }

    private fun getByValueHashCode(collection: Collection<*>) =
        collection.fold(17) { acc, element ->
            val parameterHashCode = element.hashCode()
            acc * 23 + parameterHashCode
        }

}
