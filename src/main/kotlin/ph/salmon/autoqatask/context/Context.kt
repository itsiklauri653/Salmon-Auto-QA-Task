package ph.salmon.autoqatask.context

import io.qameta.allure.Step
import java.util.*
import java.util.concurrent.ConcurrentHashMap

class Context {
    private val MAP = ConcurrentHashMap<String, Any>()

    @Step("Saving value to the context")
    fun <T : Any> addToContext(key: String, value: T) {
        require(!Objects.isNull(value)) { "Value could not be null for key: $key" }
        MAP[key] = value
    }

    @Step("Fetching context to get value by key")
    fun <T> getValue(key: String): T? {
        val `val` = MAP[key]
        return if (Objects.isNull(`val`)) null else `val` as T?
    }
}