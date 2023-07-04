package gsalaun.diverse.utils

import java.lang.IllegalStateException

class NotTrivialToInstantiatePerson(val name:String, val surname:String) {
    var age: Int? = null

    constructor(name:String,surname: String, age:Int) : this(name,surname) {
        throw IllegalStateException()
    }

}