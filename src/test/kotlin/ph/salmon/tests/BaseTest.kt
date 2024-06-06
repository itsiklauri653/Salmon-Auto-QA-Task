package ph.salmon.tests

import ph.salmon.autoqatask.context.Context
import ph.salmon.autoqatask.service.implementation.HttpClient
import ph.salmon.autoqatask.service.interfaces.IHttpClient

open class BaseTest {
    private val httpClient: IHttpClient
    private val context: Context = Context()

    init {
        httpClient = HttpClient(context)
    }

    fun getHttpClient(): IHttpClient {
        return httpClient
    }

    fun getContext(): Context {
        return context
    }
}
