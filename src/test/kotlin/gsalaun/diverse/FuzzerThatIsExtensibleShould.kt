package gsalaun.diverse

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(AllTestsFixtures::class)
class FuzzerThatIsExtensibleShould {

    @Test
    fun `be able to have extension methods`() {
        val fuzzer = Fuzzer(seed = 1245650948)

        // we have access to all our extension methods on our Fuzzer
        // (here: generateAVerySpecificAge())
        val age: Age = fuzzer.generateAVerySpecificAge()
        assertThat(age.isConfidential).isTrue()
        assertThat(age.years).isEqualTo(54)
    }

    private fun IFuzz.generateAVerySpecificAge(): Age {
        // Here, we can have access to all the existing methods
        // exposed by the IFuzz interface

        val years: Int = this.generatePositiveInteger(97)

        // or this one (very useful)
        val isConfidential = this.headsOrTails()

        return Age(years, isConfidential)
    }

    data class Age(val years: Int, val isConfidential: Boolean)
}