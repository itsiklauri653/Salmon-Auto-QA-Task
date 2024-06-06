package ph.salmon.autoqatask.service.implementation

import io.qameta.allure.Step
import io.qameta.allure.restassured.AllureRestAssured
import io.restassured.RestAssured
import io.restassured.builder.RequestSpecBuilder
import io.restassured.filter.log.LogDetail
import io.restassured.http.ContentType
import io.restassured.response.Response
import io.restassured.specification.RequestSpecification
import ph.salmon.autoqatask.constants.ContextConstants.STATUS_CODE
import ph.salmon.autoqatask.context.Context
import ph.salmon.autoqatask.service.interfaces.IHttpClient
import ph.salmon.autoqatask.utils.EnvUtils
import java.util.Objects
import java.util.concurrent.ConcurrentHashMap

class HttpClient(context: Context) : IHttpClient {
    private val context: Context

    init {
        this.context = context
    }

    private fun basicHttpRequest(basePath: String, headers: ConcurrentHashMap<String, String>?,
                                 pathParams: ConcurrentHashMap<String, Any>?,
                                 queryParams: ConcurrentHashMap<String, Any>?): RequestSpecification {
        val baseUrl = EnvUtils.getVariable(BASE_URL)
        val specBuilder = RequestSpecBuilder()
                .setBaseUri(baseUrl)
                .setBasePath(basePath)
                .setRelaxedHTTPSValidation()
                .log(LogDetail.ALL)
                .addFilter(AllureRestAssured())
                .setContentType(ContentType.JSON)
                .setAccept(ContentType.JSON)

        if(Objects.nonNull(headers))
            specBuilder.addHeaders(headers)
        if(Objects.nonNull(pathParams))
            specBuilder.addPathParams(pathParams)
        if(Objects.nonNull(queryParams))
            specBuilder.addQueryParams(queryParams)
        return specBuilder.build()
    }

    @Step("Calling HTTP GET method")
    override fun get(url: String, headers: ConcurrentHashMap<String, String>?,
                     pathParams: ConcurrentHashMap<String, Any>?,
                     queryParams: ConcurrentHashMap<String, Any>?): Response {
        val request = RestAssured.given()
                .spec(basicHttpRequest(url, headers, pathParams, queryParams))
        val response = request.get().then().log().all().extract().response()
        context.addToContext(STATUS_CODE, response.statusCode)
        return response
    }

    @Step("Calling HTTP POST method")
    override fun post(url: String, body: Any, headers: ConcurrentHashMap<String, String>?,
                      pathParams: ConcurrentHashMap<String, Any>?,
                      queryParams: ConcurrentHashMap<String, Any>?): Response {
        val request = RestAssured.given()
                .spec(basicHttpRequest(url, headers, pathParams, queryParams))
                .body(body)
        val response = request.post().then().log().all().extract().response()
        context.addToContext(STATUS_CODE, response.statusCode)
        return response
    }

    @Step("Calling HTTP PUT method")
    override fun put(url: String, body: Any, headers: ConcurrentHashMap<String, String>?,
                     pathParams: ConcurrentHashMap<String, Any>?,
                     queryParams: ConcurrentHashMap<String, Any>?): Response {
        val request = RestAssured.given()
                .spec(basicHttpRequest(url, headers, pathParams, queryParams))
                .body(body)
        val response = request.put().then().log().all().extract().response()
        context.addToContext(STATUS_CODE, response.statusCode)
        return response
    }

    @Step("Calling HTTP PATCH method")
    override fun patch(url: String, body: Any, headers: ConcurrentHashMap<String, String>?,
                       pathParams: ConcurrentHashMap<String, Any>?,
                       queryParams: ConcurrentHashMap<String, Any>?): Response {
        val request = RestAssured.given()
                .spec(basicHttpRequest(url, headers, pathParams, queryParams))
                .body(body)
        val response = request.patch().then().log().all().extract().response()
        context.addToContext(STATUS_CODE, response.statusCode)
        return response
    }

    @Step("Calling HTTP DELETE method")
    override fun delete(url: String, headers: ConcurrentHashMap<String, String>?,
                        pathParams: ConcurrentHashMap<String, Any>?,
                        queryParams: ConcurrentHashMap<String, Any>?): Response {
        val request = RestAssured.given()
                .spec(basicHttpRequest(url, headers, pathParams, queryParams))
        val response = request.delete().then().log().all().extract().response()
        context.addToContext(STATUS_CODE, response.statusCode)
        return response
    }

    companion object {
        private const val BASE_URL = "baseUrl"
    }
}
