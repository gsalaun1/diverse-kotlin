package gsalaun.diverse

import gsalaun.diverse.persons.Female
import gsalaun.diverse.persons.Male
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class NamesShould {

    @Test
    public fun `have no duplication within female name`() {
        val femaleNames = Female.firstNames.toSortedSet()

        assertThat(femaleNames.size).isEqualTo(Female.firstNames.size);
    }

    @Test
    fun `have 357 female names`() {
        assertThat(Female.firstNames).hasSize(357);
    }

    @Test
    fun `have 330 male names`()
    {
        assertThat(Male.firstNames).hasSize(336);
    }

    @Test
    fun `have no duplication within male name`() {
        val maleNames = Male.firstNames.toSortedSet()

        assertThat(maleNames.size).isEqualTo(Male.firstNames.size);
    }
}