package gsalaun.diverse

import gsalaun.diverse.Fuzzer
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import java.util.UUID

@ExtendWith(AllTestsFixtures::class)
class UuidFuzzerShould {

    @Test
    fun `Generate random but deterministic Uuid`() {
        val fuzzer = Fuzzer(1898737139)
        var guid = fuzzer.generateUuid()
        assertThat(guid).isEqualTo(UUID.fromString("b50343b5-fac9-31b4-a84a-c9d3e219f2f9"))
        guid = fuzzer.generateUuid()
        assertThat(guid).isEqualTo(UUID.fromString("3cf34246-d0e5-325a-97fc-1924974be06f"))
    }
}