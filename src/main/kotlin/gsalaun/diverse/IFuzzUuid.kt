package gsalaun.diverse

import java.util.UUID

/**
 * Fuzz {@Link UUID}
 *
 */
interface IFuzzUuid {

    /**
     * Generates a random {@Link UUID}
     *
     * @return A random {@Link UUID}
     */
    fun generateUuid(): UUID
}