package gsalaun.diverse

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(AllTestsFixtures::class)
class CollectionFuzzerShould {

    @Test
    fun `be able to pickOneFrom`() {
        val fuzzer = Fuzzer()
        val candidates = listOf("one", "two", "three", "four", "five")
        (0 until 10).forEach { _ ->
            val chosenOne = fuzzer.pickOneFrom(candidates)
            assertThat(chosenOne).isIn(candidates)
        }
    }

    @Test
    fun `be able to pickOneFrom using params api`() {
        val fuzzer = Fuzzer()
        (0 until 10).forEach { _ ->
            val chosenOne = fuzzer.pickOneFrom("one", "two", "three", "four", "five")
            assertThat(chosenOne).isIn("one", "two", "three", "four", "five")
        }
    }

    @Test
    fun `throw an Exception when list of candidates is empty`() {
        val fuzzer = Fuzzer()

        assertThatThrownBy {
            fuzzer.pickOneFrom(emptyList())
        }.isInstanceOf(IllegalArgumentException::class.java)
            .hasMessage("Candidates list must not be empty.")
    }
}
