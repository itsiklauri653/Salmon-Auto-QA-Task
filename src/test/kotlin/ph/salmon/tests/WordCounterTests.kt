package ph.salmon.tests

import io.qameta.allure.Allure
import io.qameta.allure.Description
import org.junit.jupiter.api.Test
import org.slf4j.LoggerFactory
import ph.salmon.autoqatask.service.implementation.PostsService
import ph.salmon.autoqatask.service.interfaces.IPostsService

class WordCounterTests : BaseTest() {
    private val log = LoggerFactory.getLogger(WordCounterTests::class.java)

    private val postsService: IPostsService

    init {
        postsService = PostsService(getHttpClient())
    }

    @Test
    @Description("Find out all words which are present in body parameter and log")
    fun validateWordCounter() {
        log.info("Calling GET all posts request...")
        val posts = postsService.getAllPosts(null)
        log.info("GET all posts: $posts")

        log.info("Extracting top 10 words with highest counts")
        val topWords = postsService.getTopWords(posts)

        log.info("Adding words to the allure report")
        Allure.addAttachment("Top words", topWords
                .mapIndexed{ i, word -> "${i + 1}. $word" }
                .joinToString (separator = "\n"))
        log.info("Logging Top 10 words...")
        topWords.forEachIndexed { i, word -> log.info("${i + 1}. $word") }
    }
}