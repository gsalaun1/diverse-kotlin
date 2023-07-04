package gsalaun.diverse

import java.time.LocalDate
import java.time.LocalDateTime

object Types {

    val COVERED_BY_A_FUZZER = listOf(
        LocalDateTime::class,
        LocalDate::class,
        Int::class,
        Long::class,
        Boolean::class,
        Double::class,
        String::class,
    )
}
