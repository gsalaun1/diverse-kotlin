package gsalaun.diverse.uuid

import gsalaun.diverse.IFuzz
import gsalaun.diverse.IFuzzUuid
import java.util.UUID

class UuidFuzzer(val fuzzer: IFuzz) : IFuzzUuid {
    override fun generateUuid(): UUID {
        val uuid = ByteArray(16)
        fuzzer.random.nextBytes(uuid)
        return UUID.nameUUIDFromBytes(uuid)
    }
}