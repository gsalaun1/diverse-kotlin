package gsalaun.diverse.persons

import java.lang.IllegalStateException
import java.lang.UnsupportedOperationException

object LastNames {

    /**
     * Gets a dictionary of all the last name per {@Link Continent}
     */
    val perContinent: Map<Continent, List<String>>
        get() {
            return _perContinent.removeDuplicates()
        }

    private fun Map<Continent, List<String>>.removeDuplicates() = this.map { Pair(it.key, it.value.distinct()) }.toMap()

    private val _perContinent = mapOf(
        Continent.AFRICA to listOf(
            "Soumbu", "Diak", "Keita", "Abbas", "Adomako", "Kasongo", "Abdallah", "Badu", "Chedjou",
            "Djetou", "N'go", "Selassie", "Nwadike", "Boujettif", "Benkacem", "Zidane", "Smah", "Belkhodja",
            "Ben Achour", "Ben Ayed", "Bouhageb", "Soua", "Smahi", "Soumbou", "Ewé", "Traore", "Ali",
            "Kamara", "Tesfaye", "Banda", "NGuema", "NGoma", "Phiri", "Mensah", "Drogba", "Ibrahim",
            "Sissoko", "Diabaté", "Samparé", "Diop", "Ba", "Mwangi", "Salem", "NDiaye", "Lulunga", "Diouf"
        ),


        Continent.ASIA to listOf(
            "Bhundoo", "Rughoobur", "Sauba", "Khatri", "Laghari", "Patel", "Acharya", "Balakrishnan",
            "Jain", "Dhar", "Kulkarni", "Madan", "Chandra", "Thakur", "Singh", "Dalal", "Chakrabarti",
            "Kohli", "Jha", "NQuoc", "NGuyen", "Han", "Lǐ", "Wang", "Zhāng", "Chén", "Zhào", "Zhōu", "Wú",
            "Gāo", "Liáng", "Féng", "Kim", "Lee", "Park", "Choi", "Lee", "Truong", "Jeong", "Cho", "Yoon",
            "Bak", "Suzuki", "Takahashi", "Tanaka", "Nakamura", "Watanabe", "Ito"
        ),

        Continent.ANTARCTICA to listOf(
            "Karlsen", "Johnsen", "Pettersen", "Eriksen", "Johannessen", "Jørgensen", "Jacobsen", "Iversen",
            "Solberg", "Bakken", "Rasmussen", "Sandvik", "Ruud", "Ødegård", "Strøm", "Myklebust", "Nygård",
            "Berntsen", "Thomassen", "Haugland", "Gulbrandsen", "Tveit", "Ødegård", "Madsen", "Abrahamsen",
            "Brekke", "Ruud", "Myhre", "Aas"
        ),
        Continent.AUSTRALIA to listOf(
            "Smith", "Wilson", "Brown", "Taylor", "Jones", "Thompson", "Campbell", "Wright", "Mills",
            "Shepherd", "Greenwood", "Boone", "Lee", "Kelly", "Robinson", "Walker", "Chen", "Harris",
            "Patel", "Reddy", "Prakash", "Khan", "Tudu", "Turner", "Martin", "Ryan", "Williams", "Wilson",
            "White", "Thomas", "King", "Falconer", "Forsythe", "Grath", "Hodge", "Ivey", "Kingston",
            "Rayner"
        ),
        Continent.EUROPE to listOf(
            "Di Biasio", "Di Maria", "Del Pozzo", "Zimmerman", "Pierrain", "Dupont", "Dupuy", "Marchand",
            "García", "Fernández", "Lopes", "Gomes", "Hendricks", "Thompson", "Müller", "Schneider",
            "Wagner", "Bauer", "Moreau", "Fournier", "Esposito", "Conti", "Rizzo", "Mancini", "Boucard",
            "Brandolini", "Tune", "Lemaire", "Lorphelin", "Berdot", "Bellec", "Kaiser", "Mateudi",
            "Roccaserra", "Mattei", "Dodelier", "Muraz", "Truchot", "De Funes", "Bernard", "Johnson",
            "Dubois", "Petit", "Kovacs", "Dupuydauby", "Mercier", "Girard", "Moreau", "Gonzales", "Lefevre"
        ),

        Continent.SOUTH_AMERICA to listOf(
            "Rodríguez", "González", "Muñoz", "Rojas", "Jimenez", "Rodríguez", "Sanchez", "Flores", "Pérez",
            "Mora", "Quispe", "Benítez", "Hernández", "Aburto", "Acuna", "Aimar", "Alcantara", "Allende",
            "Ambríz", "Araquistain", "Arellano", "Arrisola", "Borges", "Botelho", "Burruchaga",
            "Bustamante", "Caldera", "Canizales", "Clores", "Coutinho", "Costa", "De Jesus", "De la Hoya",
            "Denilson", "De Souza"
        ),
        Continent.NORTH_AMERICA to listOf(
            "West", "Wayne", "Smith", "Biden", "Williams", "Johnson", "Brown", "Jones", "Miller", "Davis",
            "Thomas", "Jackson", "Moore", "Clark", "Sanchez", "Robinson", "Young", "Perez", "Lee", "Allen",
            "King", "Beck", "Lewis", "Hill", "Baker", "Rivera", "Mitchell", "Carter", "Carhart", "Hughes",
            "Burgis", "Hoadley", "Moore", "Brand", "Acker", "Saint-John", "Talbert", "Wynn", "Swetel",
            "Postma", "Woskowski", "Panamera", "Winter", "Fantasia", "Barimore", "Jordan", "Rosenberg",
            "Gertz", "Nash"
        )
    )

    /**
     * Find which {@Link Continent} we relate any given last name.
     *
     * @param lastName The last name we want to find an associated {@Link Continent} for.
     * @return The {@Link Continent} we have associated with this last name.
     */
    fun findAssociatedContinent(lastName: String) =
        perContinent.map { Pair(it.key, it.value) }.firstOrNull { it.second.contains(lastName) }?.first
            ?: throw IllegalStateException("The lastname ($lastName) is not associated to any Continent in our lib.")

}