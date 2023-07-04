package gsalaun.diverse.helpers

import java.lang.reflect.Method

data class MemoizerKey(val fuzzerMethod: Method, val argumentsHashCode: Int)
