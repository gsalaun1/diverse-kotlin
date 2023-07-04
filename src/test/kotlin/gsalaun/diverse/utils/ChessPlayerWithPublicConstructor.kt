package gsalaun.diverse.utils

internal class ChessPlayerWithPublicConstructor : PlayerWithProtectedConstructor {

    var chessLevel: ChessLevel

    var favoriteOpponent: ChessPlayerWithPublicConstructor?

    var currentClub: ChessClub?

    var formerClubs: Set<ChessClub>

    init {
        chessLevel = ChessLevel.BEGINNER
        favoriteOpponent = null
        currentClub = null
        formerClubs = emptySet()
    }

    constructor() : super("obsolete", "should not be used", 0)

    constructor(firstName: String) : super(firstName, "obsolete constructor that should not be used", 0)

    constructor(
        firstName: String,
        lastName: String,
        age: Int,
        chessLevel: ChessLevel,
        favoriteOpponent: ChessPlayerWithPublicConstructor? = null,
        currentClub: ChessClub? = null,
        formerClubs: Set<ChessClub> = emptySet(),
    ) : super(firstName, lastName, age) {
        this.chessLevel = chessLevel
        this.favoriteOpponent = favoriteOpponent
        this.currentClub = currentClub
        this.formerClubs = formerClubs
    }
}

internal data class ChessClub(val name: String, val country: Country)

internal enum class Country {
    RUSSIA,
    FRANCE,
    USA,
    CANADA,
    SENEGAL,
    UKRAINE,
}
