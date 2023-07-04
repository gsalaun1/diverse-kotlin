package gsalaun.diverse.utils

/**
 * Player for any game. Dummy class for testing purpose (for Constructor-based Fuzzing).
 * What matters here is to have at least one non-empty constructor.
 *
 */
internal open class PlayerWithProtectedConstructor {

    var firstName: String

    var lastName: String

    var age: Int

    init {
        firstName = ""
        lastName = ""
        age = 0
    }

    /**
     * Useless constructor but interesting for our tests.
     */
    private constructor()

    /**
     * Protected constructor interesting for our tests.
     */
    protected constructor(firstName: String, lastName: String, age: Int) {
        this.firstName = firstName
        this.lastName = lastName
        this.age = age
    }
}
