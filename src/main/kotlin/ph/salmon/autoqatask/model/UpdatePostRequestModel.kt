package ph.salmon.autoqatask.model

import com.fasterxml.jackson.annotation.JsonInclude


@JsonInclude(JsonInclude.Include.NON_NULL)
class UpdatePostRequestModel {
    var id: Int? = null
    var title: String? = null
    var body: String? = null
    var userId: Int? = null

    override fun toString(): String {
        return """UpdatetPostRequestModel: {
            id: ${id},
            title: ${title},
            body: ${body},
            userId: ${userId}
        }
        """.trimMargin()
    }
}