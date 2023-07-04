package gsalaun.diverse

import gsalaun.diverse.helpers.removeDiacritics
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class StringExtensionShould {

    @Test
    fun `remove diacritics`(){
        val name = "Saïd Ef Ben"
        val nameWithoutDiacritics = name.removeDiacritics()
        assertThat(nameWithoutDiacritics).isEqualTo("Said Ef Ben")
    }
}