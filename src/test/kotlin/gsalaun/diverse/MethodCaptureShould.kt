package gsalaun.diverse

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.lang.reflect.Method

class MethodCaptureShould {

    @Test
    fun `be able to capture a generic method`() {
        val helperForTestingPurpose = DummyHelperForTestingPurpose()

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
                "PBSENIOR"
            )

        val methodBase = helperForTestingPurpose.capturable(rateCodes);
        val secondCallMethodBase = helperForTestingPurpose.capturable(rateCodes);

        assertThat(secondCallMethodBase).isEqualTo(methodBase);
    }

    class DummyHelperForTestingPurpose {
        fun capturable(candidates: List<*>): Method {
            return object {}.javaClass.enclosingMethod
        }
    }
}