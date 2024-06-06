package ph.salmon.autoqatask.service.interfaces

import ph.salmon.autoqatask.model.CreatePostRequestModel
import ph.salmon.autoqatask.model.PostResponseModel
import ph.salmon.autoqatask.model.UpdatePostRequestModel
import java.util.concurrent.ConcurrentHashMap

interface IPostsService {
    fun getPostById(postId: Int): PostResponseModel

    fun getAllPosts(queryParams: ConcurrentHashMap<String, Any>?): List<PostResponseModel>

    fun createPost(post: CreatePostRequestModel): PostResponseModel

    fun updatePost(post: UpdatePostRequestModel, postId: Int): PostResponseModel

    fun patchPost(post: UpdatePostRequestModel, postId: Int): PostResponseModel

    fun deletePost(postId: Int): PostResponseModel

    fun getTopWords(posts: List<PostResponseModel>): List<String>
}