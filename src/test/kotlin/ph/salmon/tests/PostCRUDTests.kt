package ph.salmon.tests

import com.github.javafaker.Faker
import io.qameta.allure.Description
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.slf4j.LoggerFactory
import ph.salmon.autoqatask.constants.ContextConstants.STATUS_CODE
import ph.salmon.autoqatask.model.CreatePostRequestModel
import ph.salmon.autoqatask.model.PostResponseModel
import ph.salmon.autoqatask.model.UpdatePostRequestModel
import ph.salmon.autoqatask.service.implementation.PostsService
import ph.salmon.autoqatask.service.interfaces.IPostsService
import java.util.*
import java.util.concurrent.ConcurrentHashMap

class PostCRUDTests : BaseTest() {
    private val log = LoggerFactory.getLogger(BaseTest::class.java)

    private val postsService: IPostsService
    private val faker: Faker

    init {
        postsService = PostsService(getHttpClient())
        faker = Faker()
    }

    @Test
    @Description("Calling GET post by id and validating that data is returned")
    fun validateGetPostById() {
        val id = Random().nextInt(1, 100)
        log.info("Calling GET post by id when id is: $id")
        val post: PostResponseModel = postsService.getPostById(id)
        log.info("GET post by id: $id: $post")
        Assertions.assertEquals(200, getContext().getValue(STATUS_CODE))
        Assertions.assertEquals(id, post.id)
        Assertions.assertNotNull(post.title)
        Assertions.assertNotNull(post.body)
        Assertions.assertNotNull(post.userId)
    }

    @Test
    @Description("Calling GET post by id with incorrect id and " +
            "validating that data is not returned and status code is correct")
    fun validatePostByIdNotFound() {
        log.info("Calling GET post by id: 0")
        val post: PostResponseModel = postsService.getPostById(0)
        log.info("GET post: $post")
        Assertions.assertEquals(404, getContext().getValue(STATUS_CODE))
        Assertions.assertNull(post.body)
        Assertions.assertNull(post.title)
        Assertions.assertNull(post.userId)
        Assertions.assertNull(post.id)
    }

    @Test
    @Description("Calling GET all posts and validating that data size correct")
    fun validateGetAllPosts() {
        log.info("Calling GET all posts")
        val posts: List<PostResponseModel> = postsService.getAllPosts(null)
        log.info("GET posts: $posts")
        Assertions.assertEquals(200, getContext().getValue(STATUS_CODE))
        Assertions.assertEquals(posts.size, 100)
    }

    @Test
    @Description("Calling GET all posts filtered with userId and " +
            "validating that data size is correct and status code is correct")
    fun validateGetAllPostsByUserId() {
        val queryParams = ConcurrentHashMap<String, Any>()
        queryParams["userId"] = 1

        log.info("Calling GET all posts filtered by userId: 1")
        val posts: List<PostResponseModel> = postsService.getAllPosts(queryParams)
        log.info("GET posts: $posts")
        Assertions.assertEquals(200, getContext().getValue(STATUS_CODE))

        val sizeAfterFilter = posts
                .filter { post -> post.userId == 1 }
                .size

        Assertions.assertEquals(posts.size, sizeAfterFilter)
    }

    @Test
    @Description("Calling CREATE post and validating that data is returned correctly")
    fun validateCreatePost() {
        val post = CreatePostRequestModel()
        post.body = faker.letterify("Lorem ipsum, ????!23 asd??? 1sadLorem ipsum, 12345")
        post.title = faker.letterify("Lorem ipsum")
        post.userId = 101

        log.info("Calling CREATE post with body: $post")
        val created: PostResponseModel = postsService.createPost(post)
        log.info("CREATED post: $created")

        Assertions.assertEquals(201, getContext().getValue(STATUS_CODE))
        Assertions.assertNotNull(created.id)
        Assertions.assertEquals(post.body, created.body)
        Assertions.assertEquals(post.title, created.title)
        Assertions.assertEquals(post.userId, created.userId)
    }

    @Test
    @Description("Calling UPDATE post and validating that data is returned correctly")
    fun validateUpdatePost() {
        val post = UpdatePostRequestModel()
        post.id = 1
        post.body = faker.letterify("Lorem ipsum, ????!23 asd??? 1sadLorem ipsum, 12345")
        post.title = faker.letterify("Lorem ipsum")
        post.userId = 101

        log.info("Calling UPDATE post with body: $post")
        val updated: PostResponseModel = postsService.updatePost(post, post.id!!)
        log.info("UPDATED post: $updated")

        Assertions.assertEquals(200, getContext().getValue(STATUS_CODE))
        Assertions.assertNotNull(updated.id)
        Assertions.assertEquals(post.body, updated.body)
        Assertions.assertEquals(post.title, updated.title)
        Assertions.assertEquals(post.userId, updated.userId)
    }

    //Failing because status code is 500 but should be not found because there is no such resource to update
    @Test
    @Description("Calling UPDATE post with incorrect id and " +
            "validating that data is not returned and status code is correct")
    fun validateUpdateIncorrectPost() {
        val post = UpdatePostRequestModel()
        post.id = 1000
        post.body = faker.letterify("Lorem ipsum, ????!23 asd??? 1sadLorem ipsum, 12345")
        post.title = faker.letterify("Lorem ipsum")
        post.userId = 101

        log.info("Calling UPDATE post with body: $post")
        val updated: PostResponseModel = postsService.updatePost(post, post.id!!)
        log.info("UPDATED post: $updated")
        Assertions.assertEquals(404, getContext().getValue(STATUS_CODE) )
        Assertions.assertNull(updated.body)
        Assertions.assertNull(updated.title)
        Assertions.assertNull(updated.userId)
        Assertions.assertNull(updated.id)
    }

    @Test
    @Description("Calling PATCH post and validating that data is returned correctly")
    fun validatePatchPost() {
        val post = UpdatePostRequestModel()
        post.body = faker.letterify("Lorem ipsum, ????!23 asd??? 1sadLorem ipsum, 12345")

        log.info("Calling PATCH post with body: $post")
        val updated: PostResponseModel = postsService.patchPost(post, 1)
        log.info("PATCHED post: $updated")

        Assertions.assertEquals(getContext().getValue(STATUS_CODE), 200)
        Assertions.assertNotNull(updated.id)
        Assertions.assertNotNull(updated.title)
        Assertions.assertNotNull(updated.userId)
        Assertions.assertEquals(post.body, updated.body)
    }

    //Failing because status code is 200 but should be not found because there is no such resource to patch
    @Test
    @Description("Calling PATCH post with incorrect id and " +
            "validating that data is not returned and status code is correct")
    fun validatePatchIncorrectPost() {
        val post = UpdatePostRequestModel()
        post.body = faker.letterify("Lorem ipsum, ????!23 asd??? 1sadLorem ipsum, 12345")

        log.info("Calling PATCH post with body: $post")
        val updated: PostResponseModel = postsService.patchPost(post, 0)
        log.info("PATCHED post: $updated")

        Assertions.assertEquals(404, getContext().getValue(STATUS_CODE))
        Assertions.assertNull(updated.body)
        Assertions.assertNull(updated.title)
        Assertions.assertNull(updated.userId)
        Assertions.assertNull(updated.id)
    }

    @Test
    @Description("Calling DELETE post and validating that data is not returned")
    fun validateDeletePost() {
        val postId = 1
        log.info("Calling DELETE post with id: $postId")
        val deleted: PostResponseModel = postsService.deletePost(postId)
        log.info("DELETED post: $deleted")

        Assertions.assertEquals(200, getContext().getValue(STATUS_CODE))
        Assertions.assertNull(deleted.id)
        Assertions.assertNull(deleted.body)
        Assertions.assertNull(deleted.title)
        Assertions.assertNull(deleted.userId)
    }

    //Failing because status code is 200 but should be not found because there is no such resource to delete
    @Test
    @Description("Calling DELETE post with incorrect id and " +
            "validating that data is not returned and status code is correct")
    fun validateDeleteIncorrectPost() {
        val postId = 0
        log.info("Calling DELETE post with id: $postId")
        val deleted: PostResponseModel = postsService.deletePost(postId)
        log.info("DELETED post: $deleted")

        Assertions.assertEquals(404, getContext().getValue(STATUS_CODE) )
        Assertions.assertNull(deleted.body)
        Assertions.assertNull(deleted.title)
        Assertions.assertNull(deleted.userId)
        Assertions.assertNull(deleted.id)
    }
}
