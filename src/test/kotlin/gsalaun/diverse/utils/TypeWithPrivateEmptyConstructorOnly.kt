package gsalaun.diverse.utils


/**
 * Dummy class for testing purpose (for Property-based Fuzzing).
 * What matters here is to have one empty (private) constructor only.
 */
class TypeWithPrivateEmptyConstructorOnly private constructor() {
    private var _modifiableSecret: String

    var modifiableSecret: String
        get() {
            return _modifiableSecret
        }
        set(value) {
            _modifiableSecret = value
        }

    var favoriteNumer: Int
        private set  // Property Fuzzable since it has a Setter

    private val _consultableSecret: String? = null;
    val consultableSecret: String?
        get() {
            return _consultableSecret
        }

    val name: String? = null

    init {
        _modifiableSecret = ""
        favoriteNumer = 0
    }

}