package gsalaun.diverse

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

class ArgumentHasherShould {

    @Test
    fun `provide the same hash for two identical Lists`() {
        val rateCodes =
            listOf(
                "PRBA",
                "LRBAMC",
                "AVG1",
                "PRBB",
                "LRBA",
                "BBSP",
                "PRBA2",
                "LRBA2MC",
                "PRPH",
                "PBCITE_tes",
                "PRPH2",
                "PRP2N2",
                "PR3NS1",
                "PBHS",
                "PBSNWH",
                "PBTHE",
                "PBVPOM",
                "PBZOO",
                "PHBO",
                "PHBPP",
                "PAB01",
                "PHB01",
                "PH3P",
                "LH3PMC",
                "CAMI",
                "FRCAMIF",
                "GENERICRATECODE",
                "PBGGG",
                "PBPPBRAZIL",
                "PBSENIOR",
            )
        val identicalRateCodes =
            listOf(
                "PRBA",
                "LRBAMC",
                "AVG1",
                "PRBB",
                "LRBA",
                "BBSP",
                "PRBA2",
                "LRBA2MC",
                "PRPH",
                "PBCITE_tes",
                "PRPH2",
                "PRP2N2",
                "PR3NS1",
                "PBHS",
                "PBSNWH",
                "PBTHE",
                "PBVPOM",
                "PBZOO",
                "PHBO",
                "PHBPP",
                "PAB01",
                "PHB01",
                "PH3P",
                "LH3PMC",
                "CAMI",
                "FRCAMIF",
                "GENERICRATECODE",
                "PBGGG",
                "PBPPBRAZIL",
                "PBSENIOR",
            )

        val firstHash = ArgumentHasher.hashArguments(rateCodes)
        val secondHash = ArgumentHasher.hashArguments(identicalRateCodes)

        assertThat(secondHash).isEqualTo(firstHash)
    }
}
