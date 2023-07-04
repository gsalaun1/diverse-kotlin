package gsalaun.diverse.utils

import gsalaun.diverse.persons.Gender
import java.time.LocalDate

data class PropertyOfAllTypes(
    val name: String,
    val age: Int,
    val gender: Gender,
    val favoriteNumbers: List<Int>,
    val birthday: LocalDate
)