package gsalaun.diverse

fun <T> Fuzzer.pickOneFrom(vararg candidates: T) = this.pickOneFrom(candidates.toList())
