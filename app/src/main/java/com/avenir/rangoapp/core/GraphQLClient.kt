package com.avenir.rangoapp.core

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.network.okHttpClient
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GraphQLClient @Inject constructor(
    private val tokenManager: TokenManager
) {
    private val apolloClient: ApolloClient by lazy {
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor { chain ->
                val original = chain.request()
                val token = tokenManager.getToken()
                val requestBuilder = original.newBuilder()
                
                if (token != null) {
                    requestBuilder.addHeader("Authorization", "Bearer $token")
                }
                
                requestBuilder.addHeader("Content-Type", "application/json")
                chain.proceed(requestBuilder.build())
            }
            .build()

        ApolloClient.Builder()
            .serverUrl(Constants.GRAPHQL_API_URL)
            .okHttpClient(okHttpClient)
            .build()
    }

    fun getClient(): ApolloClient = apolloClient
}




