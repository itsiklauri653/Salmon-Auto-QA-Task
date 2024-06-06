package ph.salmon.autoqatask.service.implementation


import io.qameta.allure.Step
import ph.salmon.autoqatask.constants.ApiConstants.GET_ALL_POSTS
import ph.salmon.autoqatask.constants.ApiConstants.GET_POST_BY_ID
import ph.salmon.autoqatask.model.CreatePostRequestModel
import ph.salmon.autoqatask.model.PostResponseModel
import ph.salmon.autoqatask.model.UpdatePostRequestModel
import ph.salmon.autoqatask.service.interfaces.IHttpClient
import ph.salmon.autoqatask.service.interfaces.IPostsService
import java.util.concurrent.ConcurrentHashMap

class PostsService(private val httpClient: IHttpClient) : IPostsService {
    companion object {
        private const val POST_ID = "postId"
    }
    @Step("Calling GET Post by Id")
    override fun getPostById(postId: Int): PostResponseModel {
        val pathParams = ConcurrentHashMap<String, Any>()
        pathParams[POST_ID] = postId
        return httpClient.get(GET_POST_BY_ID, null, pathParams, null)
                .`as`(PostResponseModel::class.java)
    }

    @Step("Calling GET All Posts")
    override fun getAllPosts(queryParams: ConcurrentHashMap<String, Any>?): List<PostResponseModel> {
        return httpClient.get(GET_ALL_POSTS, null, null, queryParams)
                .jsonPath().getList(".", PostResponseModel::class.java)
    }

    @Step("Calling CREATE Post")
    override fun createPost(post: CreatePostRequestModel): PostResponseModel {
        return httpClient.post(GET_ALL_POSTS, post, null, null, null)
                .`as`(PostResponseModel::class.java)
    }

    @Step("Calling UPDATE Post")
    override fun updatePost(post: UpdatePostRequestModel, postId: Int): PostResponseModel {
        val pathParams = ConcurrentHashMap<String, Any>()
        pathParams[POST_ID] = postId
        return httpClient.put(GET_POST_BY_ID, post, null, pathParams, null)
                .`as`(PostResponseModel::class.java)
    }

    @Step("Calling PATCH Post")
    override fun patchPost(post: UpdatePostRequestModel, postId: Int): PostResponseModel {
        val pathParams = ConcurrentHashMap<String, Any>()
        pathParams[POST_ID] = postId
        return httpClient.patch(GET_POST_BY_ID, post, null, pathParams, null)
                .`as`(PostResponseModel::class.java)
    }

    @Step("Calling DELETE Post")
    override fun deletePost(postId: Int): PostResponseModel {
        val pathParams = ConcurrentHashMap<String, Any>()
        pathParams[POST_ID] = postId
        return httpClient.delete(GET_POST_BY_ID, null, pathParams, null)
                .`as`(PostResponseModel::class.java)
    }

    @Step("Extracting Top Words Byy Count From Posts Response")
    override fun getTopWords(posts: List<PostResponseModel>): List<String> {
        val wordsWithCounts = HashMap<String, Int>()

        for (post: PostResponseModel in posts) {
            val words = post.body!!.replace("\n", " ").split(" ")
            for (word: String in words) {
                if (wordsWithCounts.containsKey(word)) {
                    val currentCount = wordsWithCounts[word]!!
                    wordsWithCounts[word] = currentCount + 1
                } else
                    wordsWithCounts[word] = 1
            }
        }
        return wordsWithCounts.entries.stream()
                .sorted(Comparator.comparing { e -> e.value })
                .map { e -> "${e.key} - ${e.value}" }
                .toList()
                .reversed()
                .slice(IntRange(0, 9))
    }
}
