package ph.salmon.autoqatask.model

class CreatePostRequestModel {
    var title: String? = null
    var body: String? = null
    var userId: Int? = null

    override fun toString(): String {
        return """CreatePostRequestModel: {
            title: ${title},
            body: ${body},
            userId: ${userId}
        }
        """.trimMargin()
    }
}
