package gsalaun.diverse

import java.lang.RuntimeException

class ArgumentOutOfRangeException(parameterName: String? = null, message: String? = null) : RuntimeException(
    if (message != null) {
        if (parameterName != null) {
            "$message (Parameter '$parameterName')"
        } else {
            message
        }
    } else if (parameterName == null) {
        "Specified argument was out of the range of valid values. ($message ?: '')"
    } else {
        "Specified argument was out of the range of valid values. (Parameter '$parameterName')"
    }
)
