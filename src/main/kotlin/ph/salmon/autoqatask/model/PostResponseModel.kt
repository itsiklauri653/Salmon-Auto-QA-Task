package ph.salmon.autoqatask.model

import io.qameta.allure.internal.shadowed.jackson.annotation.JsonInclude
import io.qameta.allure.internal.shadowed.jackson.annotation.JsonInclude.Include

@JsonInclude(Include.NON_NULL)
class PostResponseModel {
    var id: Int? = null
    val title: String? = null
    val body: String? = null
    val userId: Int? = null

    override fun toString(): String {
        return """PostResponseModel: {
            id: ${id},
            title: ${title},
            body: ${body},
            userId: ${userId}
        }
        """.trimMargin()
    }
}
