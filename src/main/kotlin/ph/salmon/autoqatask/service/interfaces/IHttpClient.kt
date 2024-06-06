package ph.salmon.autoqatask.service.interfaces

import io.restassured.response.Response
import java.util.concurrent.ConcurrentHashMap

interface IHttpClient {
    fun get(url: String, headers: ConcurrentHashMap<String, String>?,
                     pathParams: ConcurrentHashMap<String, Any>?,
                     queryParams: ConcurrentHashMap<String, Any>?): Response

    fun post(url: String, body: Any, headers: ConcurrentHashMap<String, String>?,
             pathParams: ConcurrentHashMap<String, Any>?,
             queryParams: ConcurrentHashMap<String, Any>?): Response

    fun put(url: String, body: Any, headers: ConcurrentHashMap<String, String>?,
            pathParams: ConcurrentHashMap<String, Any>?,
            queryParams: ConcurrentHashMap<String, Any>?): Response

    fun patch(url: String, body: Any, headers: ConcurrentHashMap<String, String>?,
              pathParams: ConcurrentHashMap<String, Any>?,
              queryParams: ConcurrentHashMap<String, Any>?): Response

    fun delete(url: String, headers: ConcurrentHashMap<String, String>?,
               pathParams: ConcurrentHashMap<String, Any>?,
               queryParams: ConcurrentHashMap<String, Any>?): Response
}
