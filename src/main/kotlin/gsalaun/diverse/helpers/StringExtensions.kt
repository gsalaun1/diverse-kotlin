package gsalaun.diverse.helpers

import java.text.Normalizer

fun String.removeDiacritics(): String {
    val regex = "\\p{InCombiningDiacriticalMarks}+".toRegex()
    val normalizedString = Normalizer.normalize(this, Normalizer.Form.NFD)
    return regex.replace(normalizedString, "")
}