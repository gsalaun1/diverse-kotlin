package gsalaun.diverse

import org.junit.jupiter.api.extension.BeforeAllCallback
import org.junit.jupiter.api.extension.ExtensionContext

class AllTestsFixtures : BeforeAllCallback {
    override fun beforeAll(context: ExtensionContext) {
        Fuzzer.Log = {
            println(it)
        }
    }
}