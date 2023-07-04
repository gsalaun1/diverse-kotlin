package gsalaun.diverse.utils

import java.time.LocalDate

data class SelfReferencingTypeWithACollectionOfItself(
    val name: String,
    val birthday: LocalDate,
    val friends: Set<SelfReferencingTypeWithACollectionOfItself>
)