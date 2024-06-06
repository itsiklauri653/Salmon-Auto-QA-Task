package ph.salmon.autoqatask.utils

import io.qameta.allure.Step
import java.util.*

object EnvUtils {

    @Step("Getting environment variable")
    fun getVariable(name: String): String {
        val env = System.getenv(name)
        val property = System.getProperty(name)
        require(!(Objects.isNull(env) && Objects.isNull(property))) { "No such variable with a name: $name" }
        return if (Objects.nonNull(env)) env else property
    }
}
