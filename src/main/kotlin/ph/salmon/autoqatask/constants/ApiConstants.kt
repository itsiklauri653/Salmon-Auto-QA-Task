package ph.salmon.autoqatask.constants

object ApiConstants {
    private const val DASH = "/"
    const val GET_ALL_POSTS = DASH + "posts"
    const val GET_POST_BY_ID = "$GET_ALL_POSTS$DASH{postId}"
}
